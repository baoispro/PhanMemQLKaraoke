package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import dao.NhanVien_DAO;
import entity.NhanVien;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;

public class NhanVien_Service extends UnicastRemoteObject implements NhanVien_DAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6776433598662556701L;
	private EntityManager entityManager;

	public NhanVien_Service(EntityManager entityManager) throws RemoteException {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}

	public ArrayList<NhanVien> getAlltbNhanVien() throws RemoteException {
		ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
		try {
			String jpql = "SELECT nv FROM NhanVien nv";
			TypedQuery<NhanVien> query = entityManager.createQuery(jpql, NhanVien.class);
			dsnv = (ArrayList<NhanVien>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsnv;
	}

	public NhanVien getNhanVienMoiNhat() throws RemoteException {
		NhanVien nv = null;
		try {
			String jpql = "SELECT nv FROM NhanVien nv ORDER BY nv.maNV DESC";
			TypedQuery<NhanVien> query = entityManager.createQuery(jpql, NhanVien.class);
			query.setMaxResults(1);
			nv = query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nv;
	}

	public NhanVien getNhanVienTheoMa(String maNhanVien) throws RemoteException {
		NhanVien nv = null;
		try {
			String jpql = "SELECT nv FROM NhanVien nv WHERE nv.maNV = :maNV";
			TypedQuery<NhanVien> query = entityManager.createQuery(jpql, NhanVien.class);
			query.setParameter("maNV", maNhanVien);
			nv = query.getSingleResult();
		} catch (NoResultException e) {
			// Không tìm thấy kết quả
			return null;
		} catch (NonUniqueResultException e) {
			// Có nhiều hơn một kết quả
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nv;
	}

	public boolean create(NhanVien nv) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			// Gọi stored procedure để phát sinh mã nhân viên
			StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("phatSinhID");
			storedProcedure.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
			storedProcedure.execute();
			String maNV = (String) storedProcedure.getOutputParameterValue(1);

			// Thiết lập mã nhân viên cho đối tượng nv
			nv.setMaNV(maNV);

			// Persist đối tượng nv vào cơ sở dữ liệu
			entityManager.persist(nv);

			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete(String maNV) throws RemoteException {
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();

			// Cập nhật trạng thái của nhân viên thành "Đã nghỉ"
			NhanVien nv = entityManager.find(NhanVien.class, maNV);
			if (nv != null) {
				nv.setTrangThai("Đã nghỉ");
			}

			// Xóa tài khoản của nhân viên
			String deleteQuery = "DELETE FROM TaiKhoan WHERE MaNV = :maNV";
			Query query = (Query) entityManager.createQuery(deleteQuery);
			query.setParameter("maNV", maNV);
			int n = query.executeUpdate();

			transaction.commit();
			return n > 0;
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	public boolean update(NhanVien nv) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			entityManager.merge(nv);

			transaction.commit();
			return true;
		} catch (Exception ex) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			ex.printStackTrace();
			return false;
		}
	}

	public ArrayList<NhanVien> timKiemNV(String ma, String ten, Date ngaySinh, boolean gioiTinh, String sdt, String dc,
			String cv, String tt) throws RemoteException {
		StringBuilder jpql = new StringBuilder("SELECT nv FROM NhanVien nv WHERE 1 = 1");

		if (ma != null) {
			jpql.append(" AND nv.maNV LIKE :ma");
		}
		if (ten != null) {
			jpql.append(" AND nv.tenNV LIKE :ten");
		}
		if (ngaySinh != null) {
			jpql.append(" AND nv.ngaySinh = :ngaySinh");
		}
		jpql.append(" AND nv.gioiTinh = :gioiTinh");
		if (sdt != null) {
			jpql.append(" AND nv.soDienThoai LIKE :sdt");
		}
		if (dc != null) {
			jpql.append(" AND nv.diaChi LIKE :dc");
		}
		if (cv != null) {
			jpql.append(" AND nv.chucVu LIKE :cv");
		}
		if (tt != null) {
			jpql.append(" AND nv.trangThai LIKE :tt");
		}

		TypedQuery<NhanVien> query = entityManager.createQuery(jpql.toString(), NhanVien.class);

		if (ma != null) {
			query.setParameter("ma", "%" + ma + "%");
		}
		if (ten != null) {
			query.setParameter("ten", "%" + ten + "%");
		}
		if (ngaySinh != null) {
			query.setParameter("ngaySinh", ngaySinh);
		}
		query.setParameter("gioiTinh", gioiTinh);
		if (sdt != null) {
			query.setParameter("sdt", "%" + sdt + "%");
		}
		if (dc != null) {
			query.setParameter("dc", "%" + dc + "%");
		}
		if (cv != null) {
			query.setParameter("cv", "%" + cv + "%");
		}
		if (tt != null) {
			query.setParameter("tt", "%" + tt + "%");
		}

		return new ArrayList<NhanVien>(query.getResultList());
	}

	public boolean kiemTraSDT(String sdt) throws RemoteException {
		TypedQuery<Long> query = entityManager
				.createQuery("SELECT COUNT(nv) FROM NhanVien nv WHERE nv.soDienThoai = :sdt", Long.class);
		query.setParameter("sdt", sdt);

		Long count = query.getSingleResult();
		return count > 0;
	}

	public NhanVien getNhanVienTheoSDT(String sdt) throws RemoteException {
		TypedQuery<NhanVien> query = entityManager.createQuery("SELECT nv FROM NhanVien nv WHERE nv.soDienThoai = :sdt",
				NhanVien.class);
		query.setParameter("sdt", sdt);

		List<NhanVien> results = query.getResultList();
		if (!results.isEmpty()) {
			return results.get(0); // Trả về đối tượng đầu tiên trong danh sách kết quả
		}
		return null;
	}

//	public ArrayList<NhanVien> getAlltbNhanVien() {
//		ArrayList<NhanVien> dsnv = new ArrayList<NhanVien>();
//		try {
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			String sql = "Select * from nhanvien";
//			Statement statement = con.createStatement();
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery(sql);
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maNV = rs.getString(1);
//				String tenNV = rs.getString(2);
//				Date ngaySinh = rs.getDate(3);
//				boolean gioiTinh = rs.getBoolean(4);
//				String sdt = rs.getString(5);
//				String diaChi = rs.getString(6);
//				String chucVu = rs.getString(7);
//				String trangThai = rs.getString(8);
//				String hinhAnh = rs.getString(9);
//
//				NhanVien nv = new NhanVien(maNV, tenNV, ngaySinh, gioiTinh, sdt, diaChi, chucVu, trangThai, hinhAnh);
//				dsnv.add(nv);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return dsnv;
//	}
//
//	public NhanVien getNhanVienMoiNhat() {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		NhanVien nv = null;
//
//		try {
//			Statement statement = con.createStatement();
//			String sql = "Select top 1 * from nhanvien ORDER BY MaNV DESC ";
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery(sql);
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maNV = rs.getString(1);
//				String tenNV = rs.getString(2);
//				Date ngaySinh = rs.getDate(3);
//				boolean gioiTinh = rs.getBoolean(4);
//				String sdt = rs.getString(5);
//				String diaChi = rs.getString(6);
//				String chucVu = rs.getString(7);
//				String trangThai = rs.getString(8);
//				String hinhAnh = rs.getString(9);
//				nv = new NhanVien(maNV, tenNV, ngaySinh, gioiTinh, sdt, diaChi, chucVu, trangThai, hinhAnh);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return nv;
//	}
//
//	public NhanVien getNhanVienTheoMa(String maNhanVien) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		NhanVien nv = null;
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from nhanvien where maNV=? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maNhanVien);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maNV = rs.getString(1);
//				String tenNV = rs.getString(2);
//				Date ngaySinh = rs.getDate(3);
//				boolean gioiTinh = rs.getBoolean(4);
//				String sdt = rs.getString(5);
//				String diaChi = rs.getString(6);
//				String chucVu = rs.getString(7);
//				String trangThai = rs.getString(8);
//				String hinhAnh = rs.getString(9);
//				nv = new NhanVien(maNV, tenNV, ngaySinh, gioiTinh, sdt, diaChi, chucVu, trangThai, hinhAnh);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return nv;
//	}
//
//	public boolean create(NhanVien nv) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			// stored procedures tự động phát sinh
//			CallableStatement myCall = con.prepareCall("{call phatSinhID(?)}");
//			myCall.registerOutParameter(1, Types.VARCHAR);
//			myCall.execute();
//			String maNV = myCall.getString(1);
//
//			stmt = con.prepareStatement("insert into " + "NhanVien values(?,?,?,?,?,?,?,?,?)");
//			stmt.setString(1, maNV);
//			stmt.setString(2, nv.getTenNV());
//			stmt.setDate(3, new java.sql.Date(nv.getNgaySinh().getTime()));
//			stmt.setBoolean(4, nv.isGioiTinh());
//			stmt.setString(5, nv.getSoDienThoai());
//			stmt.setString(6, nv.getDiaChi());
//			stmt.setString(7, nv.getChucVu());
//			stmt.setString(8, nv.getTrangThai());
//			stmt.setString(9, nv.getHinhAnh());
//
//			n = stmt.executeUpdate();
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			try {
//				stmt.close();
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//		return n > 0;
//	}
//
//	public boolean delete(String maNV) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		int n = 0;
//		try {
//			String updateQuery = "UPDATE NhanVien SET TrangThai = N'Đã nghỉ' WHERE MaNV = ?";
//			PreparedStatement updateStatement = con.prepareStatement(updateQuery);
//			updateStatement.setString(1, maNV);
//			n = updateStatement.executeUpdate();
//			String deleteQuery = "DELETE FROM TaiKhoan WHERE MaNV = ?";
//			PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);
//			deleteStatement.setString(1, maNV);
//			n += deleteStatement.executeUpdate();
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return n > 0;
//	}
//
//	public boolean update(NhanVien nv) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			stmt = con.prepareStatement("update NhanVien set HoVaTen=?,"
//					+ "NgaySinh=?,GioiTinh=?,sodienthoai=?,DiaChi=?,chucvu=?,trangthai=?,hinhanh=? " + "where maNV=?");
//
//			stmt.setString(1, nv.getTenNV());
//			stmt.setDate(2, new java.sql.Date(nv.getNgaySinh().getTime()));
//			stmt.setBoolean(3, nv.isGioiTinh());
//			stmt.setString(4, nv.getSoDienThoai());
//			stmt.setString(5, nv.getDiaChi());
//			stmt.setString(6, nv.getChucVu());
//			stmt.setString(7, nv.getTrangThai());
//			stmt.setString(8, nv.getHinhAnh());
//			stmt.setString(9, nv.getMaNV());
//			n = stmt.executeUpdate();
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			try {
//				stmt.close();
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//		return n > 0;
//	}
//
//	public ArrayList<NhanVien> timKiemNV(String ma, String ten, Date ngaySinh, boolean gioiTinh, String sdt, String dc,
//			String cv, String tt) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ArrayList<NhanVien> ds = new ArrayList<NhanVien>();
//		String sql = "SELECT *" + "FROM NhanVien " + "WHERE (? IS NULL OR MaNV like '%'+?+'%') "
//				+ "And (? IS NULL OR HovaTen like '%'+?+'%') " + "AND (? IS NULL OR ngaySinh = ?) "
//				+ "AND (GioiTinh like ?)" + "AND(? is null or soDienThoai like '%'+?+'%')"
//				+ "AND(? is null or diachi like '%'+?+'%')" + "AND(? is null or chucvu like '%'+?+'%')"
//				+ "AND(? is null or trangthai like '%'+?+'%')";
//		try {
//			statement = con.prepareStatement(sql);
//			if (ma == null) {
//				statement.setNull(1, Types.NVARCHAR);
//				statement.setNull(2, Types.NVARCHAR);
//			} else {
//				statement.setString(1, "%" + ma + "%");
//				statement.setString(2, "%" + ma + "%");
//			}
//			if (ten == null) {
//				statement.setNull(3, Types.NVARCHAR);
//				statement.setNull(4, Types.NVARCHAR);
//			} else {
//				statement.setString(3, "%" + ten + "%");
//				statement.setString(4, "%" + ten + "%");
//			}
//			if (ngaySinh == null) {
//				statement.setNull(5, Types.DATE);
//				statement.setNull(6, Types.DATE);
//			} else {
//				statement.setDate(5, new java.sql.Date(ngaySinh.getTime()));
//				statement.setDate(6, new java.sql.Date(ngaySinh.getTime()));
//			}
//			statement.setBoolean(7, gioiTinh);
//			if (sdt == null) {
//				statement.setNull(8, Types.NVARCHAR);
//				statement.setNull(9, Types.NVARCHAR);
//			} else {
//				statement.setString(8, "%" + sdt + "%");
//				statement.setString(9, "%" + sdt + "%");
//			}
//			if (dc == null) {
//				statement.setNull(10, Types.NVARCHAR);
//				statement.setNull(11, Types.NVARCHAR);
//			} else {
//				statement.setString(10, "%" + dc + "%");
//				statement.setString(11, "%" + dc + "%");
//			}
//			if (cv == null) {
//				statement.setNull(12, Types.NVARCHAR);
//				statement.setNull(13, Types.NVARCHAR);
//			} else {
//				statement.setString(12, "%" + cv + "%");
//				statement.setString(13, "%" + cv + "%");
//			}
//			if (tt == null) {
//				statement.setNull(14, Types.NVARCHAR);
//				statement.setNull(15, Types.NVARCHAR);
//			} else {
//				statement.setString(14, "%" + tt + "%");
//				statement.setString(15, "%" + tt + "%");
//			}
//			ResultSet rs = statement.executeQuery();
//			while (rs.next()) {
//				String maNV = rs.getString(1);
//				String tenNV = rs.getString(2);
//				Date ns = rs.getDate(3);
//				boolean gt = rs.getBoolean(4);
//				String soDienThoai = rs.getString(5);
//				String diaChi = rs.getString(6);
//				String chucVu = rs.getString(7);
//				String trangThai = rs.getString(8);
//				String hinhAnh = rs.getString(9);
//
//				NhanVien nv = new NhanVien(maNV, tenNV, ns, gt, soDienThoai, diaChi, chucVu, trangThai, hinhAnh);
//				ds.add(nv);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			try {
//				statement.close();
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//		return ds;
//	}
//
//	public boolean kiemTraSDT(String sdt) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		boolean isValid = false;
//		try {
//			stmt = con.prepareStatement("Select * from nhanVien where SoDienThoai=?");
//			stmt.setString(1, sdt);
//
//			return true;
//
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			try {
//				stmt.close();
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//		return isValid;
//	}
//
//	public NhanVien getNhanVienTheoSDT(String sdt) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		NhanVien nv = null;
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from nhanvien where soDienThoai=? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, sdt);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maNV = rs.getString(1);
//				String tenNV = rs.getString(2);
//				Date ngaySinh = rs.getDate(3);
//				boolean gioiTinh = rs.getBoolean(4);
//				String soDienThoai = rs.getString(5);
//				String diaChi = rs.getString(6);
//				String chucVu = rs.getString(7);
//				String trangThai = rs.getString(8);
//				String hinhAnh = rs.getString(9);
//				nv = new NhanVien(maNV, tenNV, ngaySinh, gioiTinh, soDienThoai, diaChi, chucVu, trangThai, hinhAnh);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return nv;
//	}
}
