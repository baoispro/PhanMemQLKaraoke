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

import dao.KhachHang_DAO;
import entity.KhachHang;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;

public class KhachHang_Service extends UnicastRemoteObject implements KhachHang_DAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8022500167355170464L;
	private EntityManager entityManager;

	public KhachHang_Service(EntityManager entityManager) throws RemoteException {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}

	public ArrayList<KhachHang> getAllKhachHang() throws RemoteException {
		ArrayList<KhachHang> ds = new ArrayList<KhachHang>();
		List<KhachHang> resultList = entityManager.createQuery("SELECT kh FROM KhachHang kh", KhachHang.class)
				.getResultList();
		ds.addAll(resultList);
		return ds;
	}

	public KhachHang getKhachHangMoiNhat() throws RemoteException {
		return entityManager.createQuery("SELECT kh FROM KhachHang kh ORDER BY kh.maKH DESC", KhachHang.class)
				.setMaxResults(1).getSingleResult();
	}

	public KhachHang getKhachHangTheoMa(String maKH) throws RemoteException {
		// TODO Auto-generated method stub
		return entityManager.find(KhachHang.class, maKH);
	}

	public boolean create(KhachHang kh) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			// Thực hiện gọi stored procedure để phát sinh ID
			StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("PhatSinhIDKH");
			storedProcedure.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
			storedProcedure.execute();
			String maKH = (String) storedProcedure.getOutputParameterValue(1);

			// Tạo đối tượng KhachHang mới và persist vào cơ sở dữ liệu
			kh.setMaKH(maKH);
			entityManager.persist(kh);

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

	public boolean delete(String maKH) throws RemoteException {
		try {
			KhachHang kh = entityManager.find(KhachHang.class, maKH);
			if (kh != null) {
				entityManager.remove(kh);
				return true;
			} else {
				System.out.println("Không tìm thấy khách hàng với mã: " + maKH);
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(KhachHang kh) throws RemoteException {
		try {
			KhachHang existingKhachHang = entityManager.find(KhachHang.class, kh.getMaKH());
			if (existingKhachHang != null) {
				existingKhachHang.setTenKH(kh.getTenKH());
				existingKhachHang.setGioiTinh(kh.isGioiTinh());
				existingKhachHang.setDiaChi(kh.getDiaChi());
				existingKhachHang.setEmail(kh.getEmail());
				existingKhachHang.setSoDienThoai(kh.getSoDienThoai());
				existingKhachHang.setCccd(kh.getCccd());
				existingKhachHang.setNgaySinh(kh.getNgaySinh());
				entityManager.merge(existingKhachHang);
				return true;
			} else {
				System.out.println("Không tìm thấy khách hàng với mã: " + kh.getMaKH());
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<KhachHang> timKiemKH(String ma, String ten, boolean gioiTinh, String dc, String email, String sdt,
			String CCCD, Date ngaySinh) throws RemoteException {
		List<KhachHang> ds = new ArrayList<KhachHang>();
		try {
			StringBuilder jpql = new StringBuilder("SELECT kh FROM KhachHang kh WHERE 1 = 1");
			if (ma != null) {
				jpql.append(" AND kh.maKH LIKE :ma");
			}
			if (ten != null) {
				jpql.append(" AND kh.tenKH LIKE :ten");
			}
			jpql.append(" AND kh.gioiTinh = :gioiTinh");
			if (dc != null) {
				jpql.append(" AND kh.diaChi LIKE :dc");
			}
			if (email != null) {
				jpql.append(" AND kh.email LIKE :email");
			}
			if (sdt != null) {
				jpql.append(" AND kh.soDienThoai LIKE :sdt");
			}
			if (CCCD != null) {
				jpql.append(" AND kh.cccd LIKE :CCCD");
			}
			if (ngaySinh != null) {
				jpql.append(" AND kh.ngaySinh = :ngaySinh");
			}

			TypedQuery<KhachHang> query = entityManager.createQuery(jpql.toString(), KhachHang.class);
			if (ma != null) {
				query.setParameter("ma", "%" + ma + "%");
			}
			if (ten != null) {
				query.setParameter("ten", "%" + ten + "%");
			}
			query.setParameter("gioiTinh", gioiTinh);
			if (dc != null) {
				query.setParameter("dc", "%" + dc + "%");
			}
			if (email != null) {
				query.setParameter("email", "%" + email + "%");
			}
			if (sdt != null) {
				query.setParameter("sdt", "%" + sdt + "%");
			}
			if (CCCD != null) {
				query.setParameter("CCCD", "%" + CCCD + "%");
			}
			if (ngaySinh != null) {
				query.setParameter("ngaySinh", ngaySinh);
			}

			ds = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (ArrayList<KhachHang>) ds;
	}

	public boolean kiemTraSDT(String sdt) throws RemoteException {
		try {
			String jpql = "SELECT COUNT(kh) FROM KhachHang kh WHERE kh.soDienThoai = :sdt";
			TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
			query.setParameter("sdt", sdt);

			Long count = query.getSingleResult();
			return count > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public KhachHang getKhachHangTheoSDT(String sdt) throws RemoteException {
		try {
			String jpql = "SELECT kh FROM KhachHang kh WHERE kh.soDienThoai = :sdt";
			TypedQuery<KhachHang> query = entityManager.createQuery(jpql, KhachHang.class);
			query.setParameter("sdt", sdt);

			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public KhachHang getKhachHangTheoTen(String tenKhachHang) throws RemoteException {
		try {
			String jpql = "SELECT kh FROM KhachHang kh WHERE kh.tenKH LIKE :ten";
			TypedQuery<KhachHang> query = entityManager.createQuery(jpql, KhachHang.class);
			query.setParameter("ten", "%" + tenKhachHang + "%");

			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

//	public ArrayList<KhachHang> getAllKhachHang() {
//		ArrayList<KhachHang> ds = new ArrayList<KhachHang>();
//		try {
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			String sql = "Select * from khachhang";
//			Statement statement = con.createStatement();
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery(sql);
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinh = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String email = rs.getString(5);
//				String sdt = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinh = rs.getDate(8);
//
//				KhachHang kh = new KhachHang(maKH, tenKH, gioiTinh, diaChi, email, sdt, cccd, ngaySinh);
//				ds.add(kh);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return ds;
//	}
//
//	public KhachHang getKhachHangMoiNhat() {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		KhachHang kh = null;
//
//		try {
//			Statement statement = con.createStatement();
//			String sql = "Select top 1 * from khachhang ORDER BY MaKH DESC ";
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery(sql);
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinh = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String email = rs.getString(5);
//				String sdt = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinh = rs.getDate(8);
//
//				kh = new KhachHang(maKH, tenKH, gioiTinh, diaChi, email, sdt, cccd, ngaySinh);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return kh;
//	}
//
//	public KhachHang getKhachHangTheoMa(String maKH) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		KhachHang kh = null;
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from khachhang where MaKH=? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maKH);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinh = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String email = rs.getString(5);
//				String sdt = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinh = rs.getDate(8);
//
//				kh = new KhachHang(maKH, tenKH, gioiTinh, diaChi, email, sdt, cccd, ngaySinh);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return kh;
//	}
//
////	public KhachHang getKhachHangTheo(String CCCD) {
////		ConnectDB.getInstance();
////		Connection con = ConnectDB.getConnection();
////		KhachHang kh = null;
////		PreparedStatement statement = null;
////
////		try {
////			String sql = "Select * from khachhang where CCCD=? ";
////			statement = con.prepareStatement(sql);
////			statement.setString(1, CCCD);
////			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
////			ResultSet rs = statement.executeQuery();
////			// Duyệt kết quả trả về
////			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
////				String maKH = rs.getString(1);
////				String tenKH = rs.getString(2);
////				boolean gioiTinh = rs.getBoolean(3);
////				String diaChi = rs.getString(4);
////				String email = rs.getString(5);
////				String sdt = rs.getString(6);
////				String cccd = rs.getString(7);
////				Date ngaySinh = rs.getDate(8);
////
////				kh = new KhachHang(maKH, tenKH, gioiTinh, diaChi, email, sdt, cccd, ngaySinh);
////			}
////		} catch (SQLException e) {
////			// TODO: handle exception
////			e.printStackTrace();
////		}
////		return kh;
////	}
//
//	public boolean create(KhachHang kh) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			// stored procedures tự động phát sinh
//			CallableStatement myCall = con.prepareCall("{call phatSinhIDKH(?)}");
//			myCall.registerOutParameter(1, Types.VARCHAR);
//			myCall.execute();
//			String maKH = myCall.getString(1);
//
//			stmt = con.prepareStatement("insert into " + "KhachHang values(?,?,?,?,?,?,?,?)");
//			stmt.setString(1, maKH);
//			stmt.setString(2, kh.getTenKH());
//			stmt.setBoolean(3, kh.isGioiTinh());
//			stmt.setString(4, kh.getDiaChi());
//			stmt.setString(5, kh.getEmail());
//			stmt.setString(6, kh.getSoDienThoai());
//			stmt.setString(7, kh.getCccd());
//			stmt.setDate(8, new java.sql.Date(kh.getNgaySinh().getTime()));
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
//	public boolean delete(String maKH) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		int n = 0;
//		try {
//			String deleteQuery = "DELETE FROM KhachHang WHERE MaKH = ?";
//			PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);
//			deleteStatement.setString(1, maKH);
//			n += deleteStatement.executeUpdate();
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return n > 0;
//	}
//
//	public boolean update(KhachHang kh) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			stmt = con.prepareStatement("update KhachHang set MaKH=?,"
//					+ "HoVaTen=?,GioiTinh=?,DiaChi=?,Email=?,SoDienThoai=?,CCCD=?,NgaySinh=? " + "where MaKH=?");
//
//			stmt.setString(1, kh.getMaKH());
//			stmt.setString(2, kh.getTenKH());
//			stmt.setBoolean(3, kh.isGioiTinh());
//			stmt.setString(4, kh.getDiaChi());
//			stmt.setString(5, kh.getEmail());
//			stmt.setString(6, kh.getSoDienThoai());
//			stmt.setString(7, kh.getCccd());
//			stmt.setDate(8, new java.sql.Date(kh.getNgaySinh().getTime()));
//			stmt.setString(9, kh.getMaKH());
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
//	public ArrayList<KhachHang> timKiemKH(String ma, String ten, boolean gioiTinh, String dc, String email, String sdt,
//			String CCCD, Date ngaySinh) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ArrayList<KhachHang> ds = new ArrayList<KhachHang>();
//		String sql = "SELECT *" + "FROM NhanVien " + "WHERE (? IS NULL OR MaKH like '%'+?+'%') "
//				+ "And (? IS NULL OR HoVaTen like '%'+?+'%') " + "AND (GioiTinh like ?)"
//				+ "AND(? is null or DiaChi like '%'+?+'%')" + "AND(? is null or Email like '%'+?+'%'"
//				+ "AND(? is null or soDienThoai like '%'+?+'%')" + "AND(? is null or CCCD like '%'+?+'%')"
//				+ "AND(? is null or ngaySinh = ?) ";
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
//			statement.setBoolean(5, gioiTinh);
//			if (dc == null) {
//				statement.setNull(6, Types.NVARCHAR);
//				statement.setNull(7, Types.NVARCHAR);
//			} else {
//				statement.setString(6, "%" + dc + "%");
//				statement.setString(7, "%" + dc + "%");
//			}
//			if (email == null) {
//				statement.setNull(8, Types.NVARCHAR);
//				statement.setNull(9, Types.NVARCHAR);
//			} else {
//				statement.setString(8, "%" + email + "%");
//				statement.setString(9, "%" + email + "%");
//			}
//			if (sdt == null) {
//				statement.setNull(10, Types.NVARCHAR);
//				statement.setNull(11, Types.NVARCHAR);
//			} else {
//				statement.setString(10, "%" + sdt + "%");
//				statement.setString(11, "%" + sdt + "%");
//			}
//			if (CCCD == null) {
//				statement.setNull(12, Types.NVARCHAR);
//				statement.setNull(13, Types.NVARCHAR);
//			} else {
//				statement.setString(12, "%" + CCCD + "%");
//				statement.setString(13, "%" + CCCD + "%");
//			}
//			if (ngaySinh == null) {
//				statement.setNull(14, Types.DATE);
//				statement.setNull(15, Types.DATE);
//			} else {
//				statement.setDate(14, new java.sql.Date(ngaySinh.getTime()));
//				statement.setDate(15, new java.sql.Date(ngaySinh.getTime()));
//			}
//			ResultSet rs = statement.executeQuery();
//			while (rs.next()) {
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//
//				KhachHang kh = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				ds.add(kh);
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
//			stmt = con.prepareStatement("Select * from khachhang where SoDienThoai=?");
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
//	public KhachHang getKhachHangTheoSDT(String sdt) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		KhachHang kh = null;
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from khachhang where SoDienThoai=? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, sdt);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String CCCD = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//
//				kh = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, CCCD, ngaySinhKH);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return kh;
//	}
//
//	public KhachHang getKhachHangTheoTen(String tenKhachHang) {
//		Connection connection = null;
//		PreparedStatement statement = null;
//		ResultSet rs = null;
//		KhachHang khachHang = null;
//
//		try {
//			connection = ConnectDB.getInstance().getConnection();
//			String query = "SELECT * FROM khachhang WHERE hoVaTen LIKE N'%'+?+'%'";
//			statement = connection.prepareStatement(query);
//			statement.setString(1, tenKhachHang);
//			rs = statement.executeQuery();
//			if (rs.next()) {
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//
//				khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//
//		return khachHang;
//	}
//
//	// KHÔI THÊM
//
//	public class KhachHangThongKe {
//		private KhachHang khachHang;
//		private int tongTheoTieuChi;
//
//		public KhachHangThongKe(KhachHang khachHang, int tongTheoTieuChi) {
//			this.khachHang = khachHang;
//			this.tongTheoTieuChi = tongTheoTieuChi;
//		}
//
//		public KhachHang getKhachHang() {
//			return khachHang;
//		}
//
//		public int getTongTheoTieuChi() {
//			return tongTheoTieuChi;
//		}
//
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKhachTheoNgay(Date ngay) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//
//		try {
//			String sql = "SELECT KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND NgayLapHD = ?\r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setDate(1, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int tongHoaDon = rs.getInt(9);
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, tongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKhachTheoThang(int thang, int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//
//		try {
//			String sql = "SELECT KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND MONTH(NgayLapHD) = ? AND YEAR(NgayLapHD) = ?\r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, thang);
//			statement.setInt(2, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKhachTheoNam(int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//
//		try {
//			String sql = "SELECT KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND YEAR(NgayLapHD) = ?\r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHMoiTheoNgay(Date ngay) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n" + "    HoaDon.NgayLapHD = ?\r\n"
//					+ "    AND NOT EXISTS (\r\n" + "        SELECT 1\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND NgayLapHD < ?\r\n" + "    )\r\n" + "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setDate(1, new java.sql.Date(ngay.getTime()));
//			statement.setDate(2, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHMoiTheoThang(int thang, int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ?  AND MONTH(HoaDon.NgayLapHD) = ?\r\n" + "    AND NOT EXISTS (\r\n"
//					+ "        SELECT 1\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) = ?  AND MONTH(HoaDon.NgayLapHD) < ?\r\n"
//					+ "    )\r\n" + "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, thang);
//			statement.setInt(3, nam);
//			statement.setInt(4, thang);
//
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHMoiTheoNam(int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ?\r\n" + "    AND NOT EXISTS (\r\n" + "        SELECT 1\r\n"
//					+ "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?\r\n" + "    )\r\n"
//					+ "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCuTheoNgay(Date ngay) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    HoaDon.NgayLapHD = ? -- Ngày hôm nay\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n"
//					+ "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND NgayLapHD < ? -- Từ hôm qua trở về trước\r\n"
//					+ "    ) >= 1\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND NgayLapHD < ? -- Từ hôm qua trở về trước\r\n"
//					+ "    ) < 10\r\n" + "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setDate(1, new java.sql.Date(ngay.getTime()));
//			statement.setDate(2, new java.sql.Date(ngay.getTime()));
//			statement.setDate(3, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				int soLuongHoaDon = rs.getInt(9);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCuTheoThang(int thang, int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ? AND MONTH(HoaDon.NgayLapHD) = ?-- Ngày hôm nay\r\n"
//					+ "    AND (\r\n" + "        SELECT COUNT(*)\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) = ? AND MONTH(HoaDon.NgayLapHD) < ? -- Từ hôm qua trở về trước\r\n"
//					+ "    ) >= 1\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) = ? AND MONTH(HoaDon.NgayLapHD) < ? -- Từ hôm qua trở về trước\r\n"
//					+ "    ) < 10\r\n" + "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, thang);
//			statement.setInt(3, nam);
//			statement.setInt(4, thang);
//			statement.setInt(5, nam);
//			statement.setInt(6, thang);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCuTheoNam(int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ?-- Ngày hôm nay\r\n" + "    AND (\r\n"
//					+ "        SELECT COUNT(*)\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?  -- Từ hôm qua trở về trước\r\n"
//					+ "    ) >= 1\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?  -- Từ hôm qua trở về trước\r\n"
//					+ "    ) < 10\r\n" + "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, nam);
//			statement.setInt(3, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHQuenTheoNgay(Date ngay) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    HoaDon.NgayLapHD = ? -- Ngày hôm nay\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n"
//					+ "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND NgayLapHD < ? -- Từ hôm qua trở về trước\r\n"
//					+ "    ) >= 10\r\n" + "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setDate(1, new java.sql.Date(ngay.getTime()));
//			statement.setDate(2, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHQuenTheoThang(int thang, int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ? AND MONTH(HoaDon.NgayLapHD) = ? -- Ngày hôm nay\r\n"
//					+ "    AND (\r\n" + "        SELECT COUNT(*)\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) = ? AND MONTH(HoaDon.NgayLapHD) < ? -- Từ hôm qua trở về trước\r\n"
//					+ "    ) >= 10\r\n" + "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, thang);
//			statement.setInt(3, nam);
//			statement.setInt(4, thang);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHQuenTheoNam(int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ?\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n"
//					+ "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?\r\n" + "    ) >= 10\r\n"
//					+ "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCoHDNhieuNhatTheoNgay(Date ngay) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT TOP 10 KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND NgayLapHD = ?\r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh";
//			statement = con.prepareStatement(sql);
//			statement.setDate(1, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCoHDNhieuNhatTheoThang(int thang, int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT TOP 10 KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND YEAR(HoaDon.NgayLapHD) = ? AND MONTH(HoaDon.NgayLapHD) = ?\r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh\r\n"
//					+ "Order by COUNT(*) desc;\r\n" + "";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, thang);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCoHDNhieuNhatTheoNam(int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT TOP 10 KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND YEAR(HoaDon.NgayLapHD) = ? \r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh\r\n"
//					+ "Order by COUNT(*) desc";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCoHDItNhatTheoNgay(Date ngay) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT TOP 10 KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND HoaDon.NgayLapHD = ? \r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh\r\n"
//					+ "Order by COUNT(*) asc";
//			statement = con.prepareStatement(sql);
//			statement.setDate(1, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCoHDItNhatTheoThang(int thang, int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT TOP 10 KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND YEAR(HoaDon.NgayLapHD) = ? AND MONTH(HoaDon.NgayLapHD) = ?\r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh\r\n"
//					+ "Order by COUNT(*) asc";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, thang);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCoHDItNhatTheoNam(int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT TOP 10 KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND YEAR(HoaDon.NgayLapHD) = ?\r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh\r\n"
//					+ "Order by COUNT(*) asc";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHTheoLoaiPhongTheoNgay(Date ngay, String maLP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT\r\n" + "    KH.*,\r\n"
//					+ "    SUM(DATEDIFF(MINUTE, '00:00:00', CTHD.ThoiGianSuDungPhong)) AS TongThoiGianSuDung,\r\n"
//					+ "    LP.TenLoaiPhong\r\n" + "FROM\r\n" + "    ChiTietHoaDon CTHD\r\n" + "JOIN\r\n"
//					+ "    HoaDon HD ON CTHD.MaHD = HD.MaHD\r\n" + "JOIN\r\n" + "    Phong P ON CTHD.MaP = P.MaP\r\n"
//					+ "JOIN\r\n" + "    LoaiPhong LP ON P.MaLP = LP.MaLP AND LP.MaLP = ?\r\n" + "JOIN\r\n"
//					+ "    KhachHang KH ON HD.MaKH = KH.MaKH\r\n" + "Where\r\n" + "	NgayLapHD = ?\r\n" + "GROUP BY\r\n"
//					+ "    KH.MaKH, KH.HoVaTen, LP.TenLoaiPhong, Kh.GioiTinh, kh.DiaChi, kh.Email, kh.SoDienThoai, kh.CCCD, kh.NgaySinh\r\n"
//					+ "Order By \r\n" + "	TongThoiGianSuDung DESC;";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maLP);
//			statement.setDate(2, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soGioSuDung = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soGioSuDung);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHTheoLoaiPhongTheoThang(int thang, int nam, String maLP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT\r\n" + "    KH.*,\r\n"
//					+ "    SUM(DATEDIFF(MINUTE, '00:00:00', CTHD.ThoiGianSuDungPhong)) AS TongThoiGianSuDung,\r\n"
//					+ "    LP.TenLoaiPhong\r\n" + "FROM\r\n" + "    ChiTietHoaDon CTHD\r\n" + "JOIN\r\n"
//					+ "    HoaDon HD ON CTHD.MaHD = HD.MaHD\r\n" + "JOIN\r\n" + "    Phong P ON CTHD.MaP = P.MaP\r\n"
//					+ "JOIN\r\n" + "    LoaiPhong LP ON P.MaLP = LP.MaLP AND LP.MaLP = ?\r\n" + "JOIN\r\n"
//					+ "    KhachHang KH ON HD.MaKH = KH.MaKH\r\n" + "Where\r\n"
//					+ "	MONTH(NgayLapHD) = ? and YEAR(NgayLapHD) = ?\r\n" + "GROUP BY\r\n"
//					+ "    KH.MaKH, KH.HoVaTen, LP.TenLoaiPhong, Kh.GioiTinh, kh.DiaChi, kh.Email, kh.SoDienThoai, kh.CCCD, kh.NgaySinh\r\n"
//					+ "Order By \r\n" + "	TongThoiGianSuDung DESC;";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maLP);
//			statement.setInt(2, thang);
//			statement.setInt(3, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soGioSuDung = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soGioSuDung);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHTheoLoaiPhongTheoNam(int nam, String maLP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT\r\n" + "    KH.*,\r\n"
//					+ "    SUM(DATEDIFF(MINUTE, '00:00:00', CTHD.ThoiGianSuDungPhong)) AS TongThoiGianSuDung,\r\n"
//					+ "    LP.TenLoaiPhong\r\n" + "FROM\r\n" + "    ChiTietHoaDon CTHD\r\n" + "JOIN\r\n"
//					+ "    HoaDon HD ON CTHD.MaHD = HD.MaHD\r\n" + "JOIN\r\n" + "    Phong P ON CTHD.MaP = P.MaP\r\n"
//					+ "JOIN\r\n" + "    LoaiPhong LP ON P.MaLP = LP.MaLP AND LP.MaLP = ?\r\n" + "JOIN\r\n"
//					+ "    KhachHang KH ON HD.MaKH = KH.MaKH\r\n" + "Where\r\n" + "		YEAR(NgayLapHD) = ?\r\n"
//					+ "GROUP BY\r\n"
//					+ "    KH.MaKH, KH.HoVaTen, LP.TenLoaiPhong, Kh.GioiTinh, kh.DiaChi, kh.Email, kh.SoDienThoai, kh.CCCD, kh.NgaySinh\r\n"
//					+ "Order By \r\n" + "	TongThoiGianSuDung DESC;";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maLP);
//			statement.setInt(2, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soGioSuDung = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soGioSuDung);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHTheoDichVuTheoNgay(Date ngay, String maDV) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n" + "    KH.*,\r\n" + "    SUM(CSDV.SoLuong) AS SoLuongSuDung\r\n" + "FROM \r\n"
//					+ "    KhachHang KH\r\n" + "INNER JOIN \r\n" + "    HoaDon HD ON KH.MaKH = HD.MaKH\r\n"
//					+ "INNER JOIN \r\n" + "    ChiTietSuDungDichVuHoaDon CSDV ON HD.MaHD = CSDV.MaHD\r\n"
//					+ "INNER JOIN \r\n" + "    DichVu DV ON CSDV.MaDV = DV.MaDV and CSDV.MaDV = ?\r\n" + "Where\r\n"
//					+ "	NgayLapHD = ?\r\n" + "GROUP BY \r\n"
//					+ "    KH.MaKH, KH.HoVaTen, KH.GioiTinh, KH.DiaChi, KH.Email, KH.SoDienThoai, KH.CCCD, KH.NgaySinh\r\n"
//					+ "ORDER BY \r\n" + "    SUM(CSDV.SoLuong);";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maDV);
//			statement.setDate(2, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int luotSuDung = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, luotSuDung);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHTheoDichVuTheoThang(int thang, int nam, String maDV) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n" + "    KH.*,\r\n" + "    SUM(CSDV.SoLuong) AS SoLuongSuDung\r\n" + "FROM \r\n"
//					+ "    KhachHang KH\r\n" + "INNER JOIN \r\n" + "    HoaDon HD ON KH.MaKH = HD.MaKH\r\n"
//					+ "INNER JOIN \r\n" + "    ChiTietSuDungDichVuHoaDon CSDV ON HD.MaHD = CSDV.MaHD\r\n"
//					+ "INNER JOIN \r\n" + "    DichVu DV ON CSDV.MaDV = DV.MaDV and CSDV.MaDV = ?\r\n" + "Where\r\n"
//					+ "	MONTH(NgayLapHD) = ? and YEAR(NgayLapHD) = ?\r\n" + "GROUP BY \r\n"
//					+ "    KH.MaKH, KH.HoVaTen, KH.GioiTinh, KH.DiaChi, KH.Email, KH.SoDienThoai, KH.CCCD, KH.NgaySinh\r\n"
//					+ "ORDER BY \r\n" + "    SUM(CSDV.SoLuong);";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maDV);
//			statement.setInt(2, thang);
//			statement.setInt(3, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int luotSuDung = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, luotSuDung);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHTheoDichVuTheoNam(int nam, String maDV) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n" + "    KH.*,\r\n" + "    SUM(CSDV.SoLuong) AS SoLuongSuDung\r\n" + "FROM \r\n"
//					+ "    KhachHang KH\r\n" + "INNER JOIN \r\n" + "    HoaDon HD ON KH.MaKH = HD.MaKH\r\n"
//					+ "INNER JOIN \r\n" + "    ChiTietSuDungDichVuHoaDon CSDV ON HD.MaHD = CSDV.MaHD\r\n"
//					+ "INNER JOIN \r\n" + "    DichVu DV ON CSDV.MaDV = DV.MaDV and CSDV.MaDV = ?\r\n" + "Where\r\n"
//					+ "	YEAR(NgayLapHD) = ?\r\n" + "GROUP BY \r\n"
//					+ "    KH.MaKH, KH.HoVaTen, KH.GioiTinh, KH.DiaChi, KH.Email, KH.SoDienThoai, KH.CCCD, KH.NgaySinh\r\n"
//					+ "ORDER BY \r\n" + "    SUM(CSDV.SoLuong);";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maDV);
//			statement.setInt(2, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int luotSuDung = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, luotSuDung);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public int[] thongKeBieuDoLineTongKH(int namTK) {
//		int[] intArray = new int[12];
//		for (int i = 0; i < intArray.length; i++) {
//			intArray[i] = 0;
//		}
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n" + "    MONTH(NgayLapHD) AS Thang,\r\n" + "    YEAR(NgayLapHD) AS Nam,\r\n"
//					+ "    COUNT(DISTINCT KhachHang.MaKH) AS TongSoKH\r\n" + "FROM \r\n" + "    KhachHang\r\n"
//					+ "LEFT JOIN \r\n" + "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(NgayLapHD) = ?\r\n" + "GROUP BY \r\n" + "    MONTH(NgayLapHD), YEAR(NgayLapHD)\r\n"
//					+ "ORDER BY \r\n" + "    YEAR(NgayLapHD) DESC, MONTH(NgayLapHD) DESC;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, namTK);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				intArray[rs.getInt(1) - 1] = rs.getInt(3);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return intArray;
//	}
//
//	public int[] thongKeBieuDoLineTongKHMoi(int namTK) {
//		int[] intArray = new int[12];
//		for (int i = 0; i < intArray.length; i++) {
//			intArray[i] = 0;
//		}
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n" + "    MONTH(NgayLapHD) AS Thang,\r\n" + "    YEAR(NgayLapHD) AS Nam,\r\n"
//					+ "    COUNT(DISTINCT KhachHang.MaKH) AS SoKhachHangMoi\r\n" + "FROM \r\n" + "    KhachHang\r\n"
//					+ "JOIN \r\n" + "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ?\r\n" + "    AND NOT EXISTS (\r\n" + "        SELECT 1\r\n"
//					+ "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?\r\n" + "    )\r\n"
//					+ "group by MONTH(NgayLapHD), YEAR(NgayLapHD)\r\n"
//					+ "order by  YEAR(NgayLapHD) DESC, MONTH(NgayLapHD) DESC;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, namTK);
//			statement.setInt(2, namTK);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				intArray[rs.getInt(1) - 1] = rs.getInt(3);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return intArray;
//	}
//
//	public int[] thongKeBieuDoLineTongKHCu(int namTK) {
//		int[] intArray = new int[12];
//		for (int i = 0; i < intArray.length; i++) {
//			intArray[i] = 0;
//		}
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n" + "    MONTH(NgayLapHD) AS Thang,\r\n" + "    YEAR(NgayLapHD) AS Nam,\r\n"
//					+ "    COUNT(DISTINCT KhachHang.MaKH) AS SoKhachHangMoi\r\n" + "FROM \r\n" + "    KhachHang\r\n"
//					+ "JOIN \r\n" + "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ?\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n"
//					+ "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?\r\n" + "    ) >= 1\r\n"
//					+ "    AND (\r\n" + "        SELECT COUNT(*)\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?\r\n" + "    ) < 10\r\n"
//					+ "GROUP BY \r\n" + "    MONTH(NgayLapHD), YEAR(NgayLapHD)\r\n" + "ORDER BY \r\n"
//					+ "    YEAR(NgayLapHD) DESC, MONTH(NgayLapHD) DESC;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, namTK);
//			statement.setInt(2, namTK);
//			statement.setInt(3, namTK);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				intArray[rs.getInt(1) - 1] = rs.getInt(3);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//
//		return intArray;
//	}
//
//	public int[] thongKeBieuDoLineTongKHQuen(int namTK) {
//		int[] intArray = new int[12];
//		for (int i = 0; i < intArray.length; i++) {
//			intArray[i] = 0;
//		}
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n" + "    MONTH(NgayLapHD) AS Thang,\r\n" + "    YEAR(NgayLapHD) AS Nam,\r\n"
//					+ "    COUNT(DISTINCT KhachHang.MaKH) AS SoKhachHangMoi\r\n" + "FROM \r\n" + "    KhachHang\r\n"
//					+ "JOIN \r\n" + "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ?\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n"
//					+ "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?\r\n" + "    ) >= 10\r\n"
//					+ "GROUP BY \r\n" + "    MONTH(NgayLapHD), YEAR(NgayLapHD)\r\n" + "ORDER BY \r\n"
//					+ "    YEAR(NgayLapHD) DESC, MONTH(NgayLapHD) DESC;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, namTK);
//			statement.setInt(2, namTK);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				intArray[rs.getInt(1) - 1] = rs.getInt(3);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return intArray;
//	}
}
