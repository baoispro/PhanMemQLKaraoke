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
import java.util.List;

import dao.Phong_DAO;
import entity.LoaiPhong;
import entity.Phong;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;

public class Phong_Service extends UnicastRemoteObject implements Phong_DAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7354276257925822547L;
	private EntityManager entityManager;

	public Phong_Service(EntityManager entityManager) throws RemoteException {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}

	public ArrayList<Phong> getAlltbPhong() throws RemoteException {
		String jpql = "SELECT p FROM Phong p";
		TypedQuery<Phong> query = entityManager.createQuery(jpql, Phong.class);
		List<Phong> resultList = query.getResultList();
		return new ArrayList<Phong>(resultList);
	}

	public Phong getPhongMoiNhat() throws RemoteException {
		String jpql = "SELECT p FROM Phong p ORDER BY p.maPhong DESC";
		TypedQuery<Phong> query = entityManager.createQuery(jpql, Phong.class);
		query.setMaxResults(1); // Chỉ lấy một kết quả duy nhất
		List<Phong> resultList = query.getResultList();
		if (!resultList.isEmpty()) {
			return resultList.get(0);
		} else {
			return null;
		}
	}

	public Phong getPhongTheoMa(String maPhong) throws RemoteException {
		String jpql = "SELECT p FROM Phong p WHERE p.maPhong = :maPhong";
		TypedQuery<Phong> query = entityManager.createQuery(jpql, Phong.class);
		query.setParameter("maPhong", maPhong);
		List<Phong> resultList = query.getResultList();
		if (!resultList.isEmpty()) {
			return resultList.get(0);
		} else {
			return null;
		}
	}

	public Phong getPhongTheoTen(String tenPhong) throws RemoteException {
		String jpql = "SELECT p FROM Phong p WHERE p.tenPhong = :tenPhong";
		TypedQuery<Phong> query = entityManager.createQuery(jpql, Phong.class);
		query.setParameter("tenPhong", tenPhong);
		List<Phong> resultList = query.getResultList();
		if (!resultList.isEmpty()) {
			return resultList.get(0);
		} else {
			return null;
		}
	}

	public boolean create(Phong p) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			// Thực hiện gọi stored procedure để phát sinh ID
			StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("PhatSinhIDP");
			storedProcedure.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
			storedProcedure.execute();
			String maP = (String) storedProcedure.getOutputParameterValue(1);

			// Tạo đối tượng Phong mới và persist vào cơ sở dữ liệu
			p.setMaPhong(maP);
			entityManager.persist(p);

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

	public boolean delete(String maP) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			// Tìm phòng cần xóa
			Phong phongToDelete = entityManager.find(Phong.class, maP);
			if (phongToDelete != null) {
				entityManager.remove(phongToDelete);
				transaction.commit();
				return true;
			} else {
				System.out.println("Không tìm thấy phòng với mã: " + maP);
				return false;
			}
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(Phong p) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			// Tìm phòng cần cập nhật
			Phong phongToUpdate = entityManager.find(Phong.class, p.getMaPhong());
			if (phongToUpdate != null) {
				// Cập nhật thông tin của phòng
				phongToUpdate.setTenPhong(p.getTenPhong());
				phongToUpdate.setTinhTrang(p.getTinhTrang());
				phongToUpdate.setLp(p.getLp());

				// Lưu thay đổi vào cơ sở dữ liệu
				entityManager.merge(phongToUpdate);
				transaction.commit();
				return true;
			} else {
				System.out.println("Không tìm thấy phòng cần cập nhật");
				return false;
			}
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<Phong> timKiemPhongOGiaoDienDP(String ten, String tinhTrang, String loaiPhong, int soNguoi)
			throws RemoteException {
		String jpql = "SELECT p FROM Phong p " + "WHERE (:ten IS NULL OR p.tenPhong LIKE CONCAT('%', :ten, '%')) "
				+ "AND (:tinhTrang IS NULL OR p.tinhTrang LIKE CONCAT('%', :tinhTrang, '%')) "
				+ "AND (:loaiPhong IS NULL OR p.lp.tenLoaiPhong LIKE CONCAT('%', :loaiPhong, '%')) "
				+ "AND (:soNguoi = 0 OR p.lp.soLuongNguoiChuaDuoc = :soNguoi)";

		TypedQuery<Phong> query = entityManager.createQuery(jpql, Phong.class);
		query.setParameter("ten", ten);
		query.setParameter("tinhTrang", tinhTrang);
		query.setParameter("loaiPhong", loaiPhong);
		query.setParameter("soNguoi", soNguoi);

		List<Phong> ds = query.getResultList();
		return new ArrayList<Phong>(ds);
	}

	public ArrayList<Phong> timKiemPhong(String ten, String tinhTrang, String loaiPhong, String ma)
			throws RemoteException {
		String jpql = "SELECT p FROM Phong p " + "WHERE (:ten IS NULL OR p.tenPhong LIKE CONCAT('%', :ten, '%')) "
				+ "AND (:tinhTrang IS NULL OR p.tinhTrang LIKE CONCAT('%', :tinhTrang, '%')) "
				+ "AND (:loaiPhong IS NULL OR p.lp.tenLoaiPhong LIKE CONCAT('%', :loaiPhong, '%')) "
				+ "AND (:ma IS NULL OR p.maPhong LIKE CONCAT('%', :ma, '%'))";

		TypedQuery<Phong> query = entityManager.createQuery(jpql, Phong.class);
		query.setParameter("ten", ten);
		query.setParameter("tinhTrang", tinhTrang);
		query.setParameter("loaiPhong", loaiPhong);
		query.setParameter("ma", ma);

		List<Phong> ds = query.getResultList();
		return new ArrayList<Phong>(ds);
	}

	public boolean capNhatTrangThaiPhong(String ma) throws RemoteException {
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();

			Phong phong = entityManager.find(Phong.class, ma);
			if (phong != null) {
				phong.setTinhTrang("Phòng trống");
				entityManager.merge(phong);
				transaction.commit();
				return true;
			}
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}

	public boolean capNhatTrangThaiPhong(String ma, String trangThai) throws RemoteException {
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();

			Phong phong = entityManager.find(Phong.class, ma);
			if (phong != null) {
				phong.setTinhTrang(trangThai);
				entityManager.merge(phong);
				transaction.commit();
				return true;
			}
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
		return false;
	}

//	public ArrayList<Phong> getAlltbPhong() {
//		ArrayList<Phong> dsp = new ArrayList<Phong>();
//		try {
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			String sql = "Select * from phong";
//			Statement statement = con.createStatement();
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery(sql);
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maP = rs.getString(1);
//				String tenP = rs.getString(2);
//				String tinhTrang = rs.getString(3);
//				LoaiPhong lp = new LoaiPhong(rs.getString(4));
//
//				Phong p = new Phong(maP, tenP, lp, tinhTrang);
//				dsp.add(p);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return dsp;
//	}
//
//	public Phong getPhongMoiNhat() {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		Phong p = null;
//
//		try {
//			Statement statement = con.createStatement();
//			String sql = "Select top 1 * from phong ORDER BY MaP DESC ";
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery(sql);
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maP = rs.getString(1);
//				String tenP = rs.getString(2);
//				String tinhTrang = rs.getString(3);
//				LoaiPhong lp = new LoaiPhong(rs.getString(4));
//
//				p = new Phong(maP, tenP, lp, tinhTrang);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return p;
//	}
//
//	public Phong getPhongTheoMa(String maPhong) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		Phong p = null;
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from phong where maP=? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maPhong);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maP = rs.getString(1);
//				String tenP = rs.getString(2);
//				String tinhTrang = rs.getString(3);
//				LoaiPhong lp = new LoaiPhong(rs.getString(4));
//
//				p = new Phong(maP, tenP, lp, tinhTrang);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return p;
//	}
//
//	public Phong getPhongTheoTen(String tenPhong) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		Phong p = null;
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from phong where tenPhong=? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, tenPhong);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maP = rs.getString(1);
//				String tenP = rs.getString(2);
//				String tinhTrang = rs.getString(3);
//				LoaiPhong lp = new LoaiPhong(rs.getString(4));
//
//				p = new Phong(maP, tenP, lp, tinhTrang);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return p;
//	}
//
//	public boolean create(Phong p) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			// stored procedures tự động phát sinh
//			CallableStatement myCall = con.prepareCall("{call PhatSinhIDP(?)}");
//			myCall.registerOutParameter(1, Types.VARCHAR);
//			myCall.execute();
//			String maP = myCall.getString(1);
//
//			stmt = con.prepareStatement("insert into " + "Phong values(?,?,?,?)");
//			stmt.setString(1, maP);
//			stmt.setString(2, p.getTenPhong());
//			stmt.setString(3, p.getTinhTrang());
//			stmt.setString(4, p.getLp().getMaLP());
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
//	public boolean delete(String maP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		int n = 0;
//		try {
//			String deleteQuery = "DELETE FROM Phong WHERE MaP = ?";
//			PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);
//			deleteStatement.setString(1, maP);
//			n = deleteStatement.executeUpdate();
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return n > 0;
//	}
//
//	public boolean update(Phong p) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			stmt = con.prepareStatement("update phong set tenPhong=?," + "TrangThai=?,MaLP=? " + "where maP=?");
//
//			stmt.setString(1, p.getTenPhong());
//			stmt.setString(2, p.getTinhTrang());
//			stmt.setString(3, p.getLp().getMaLP());
//			stmt.setString(4, p.getMaPhong());
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
//	public ArrayList<Phong> timKiemPhongOGiaoDienDP(String ten, String tinhTrang, String loaiPhong, int soNguoi) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ArrayList<Phong> ds = new ArrayList<Phong>();
//		String sql = "SELECT * FROM Phong " + "WHERE (? IS NULL OR TenPhong LIKE '%' + ? + '%') "
//				+ "AND (? IS NULL OR TrangThai LIKE '%' + ? + '%') "
//				+ "AND (MaLP IN (SELECT MaLP FROM LoaiPhong WHERE (? IS NULL OR TenLoaiPhong LIKE '%' + ? + '%'))) "
//				+ "AND (MaLP IN (SELECT MaLP FROM LoaiPhong WHERE (? IS NULL OR SoLuongNguoiChuaDuoc = ?)))";
//		try {
//			statement = con.prepareStatement(sql);
//			if (ten == null) {
//				statement.setNull(1, Types.NVARCHAR);
//				statement.setNull(2, Types.NVARCHAR);
//			} else {
//				statement.setString(1, "%" + ten + "%");
//				statement.setString(2, "%" + ten + "%");
//			}
//			if (tinhTrang == null) {
//				statement.setNull(3, Types.NVARCHAR);
//				statement.setNull(4, Types.NVARCHAR);
//			} else {
//				statement.setString(3, "%" + tinhTrang + "%");
//				statement.setString(4, "%" + tinhTrang + "%");
//			}
//			if (loaiPhong == null) {
//				statement.setNull(5, Types.NVARCHAR);
//				statement.setNull(6, Types.NVARCHAR);
//			} else {
//				statement.setString(5, "%" + loaiPhong + "%");
//				statement.setString(6, "%" + loaiPhong + "%");
//			}
//			if (soNguoi == 0) {
//				statement.setNull(7, Types.INTEGER);
//				statement.setNull(8, Types.INTEGER);
//			} else {
//				statement.setInt(7, soNguoi);
//				statement.setInt(8, soNguoi);
//			}
//			ResultSet rs = statement.executeQuery();
//			while (rs.next()) {
//				String maP = rs.getString(1);
//				String tenP = rs.getString(2);
//				String tt = rs.getString(3);
//				LoaiPhong lp = new LoaiPhong(rs.getString(4));
//
//				Phong p = new Phong(maP, tenP, lp, tt);
//				ds.add(p);
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
//	public ArrayList<Phong> timKiemPhong(String ten, String tinhTrang, String loaiPhong, String ma) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ArrayList<Phong> ds = new ArrayList<Phong>();
//		String sql = "SELECT * FROM Phong " + "WHERE (? IS NULL OR TenPhong LIKE '%' + ? + '%') "
//				+ "AND (? IS NULL OR TrangThai LIKE '%' + ? + '%') "
//				+ "AND (MaLP IN (SELECT MaLP FROM LoaiPhong WHERE (? IS NULL OR TenLoaiPhong LIKE '%' + ? + '%'))) "
//				+ "AND (? is null or maP like '%' + ? + '%')";
//		try {
//			statement = con.prepareStatement(sql);
//			if (ten == null) {
//				statement.setNull(1, Types.NVARCHAR);
//				statement.setNull(2, Types.NVARCHAR);
//			} else {
//				statement.setString(1, "%" + ten + "%");
//				statement.setString(2, "%" + ten + "%");
//			}
//			if (tinhTrang == null) {
//				statement.setNull(3, Types.NVARCHAR);
//				statement.setNull(4, Types.NVARCHAR);
//			} else {
//				statement.setString(3, "%" + tinhTrang + "%");
//				statement.setString(4, "%" + tinhTrang + "%");
//			}
//			if (loaiPhong == null) {
//				statement.setNull(5, Types.NVARCHAR);
//				statement.setNull(6, Types.NVARCHAR);
//			} else {
//				statement.setString(5, "%" + loaiPhong + "%");
//				statement.setString(6, "%" + loaiPhong + "%");
//			}
//			if (ma == null) {
//				statement.setNull(7, Types.NVARCHAR);
//				statement.setNull(8, Types.NVARCHAR);
//			} else {
//				statement.setString(7, ma);
//				statement.setString(8, ma);
//			}
//			ResultSet rs = statement.executeQuery();
//			while (rs.next()) {
//				String maP = rs.getString(1);
//				String tenP = rs.getString(2);
//				String tt = rs.getString(3);
//				LoaiPhong lp = new LoaiPhong(rs.getString(4));
//
//				Phong p = new Phong(maP, tenP, lp, tt);
//				ds.add(p);
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
//	public boolean capNhatTrangThaiPhong(String ma) throws SQLException {
//		Connection con = ConnectDB.getConnection();
//		String sql = "update phong set trangThai = N'Phòng trống' where maP = '" + ma + "'";
//		try {
//			PreparedStatement ps = con.prepareStatement(sql);
//			return ps.executeUpdate() > 0;
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	public boolean capNhatTrangThaiPhong(String ma, String trangThai) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmtUpdate = null;
//		int n = 0;
//		try {
//			stmtUpdate = con.prepareStatement("update Phong set TrangThai=? " + "where MaP=?");
//			stmtUpdate.setString(1, trangThai);
//			stmtUpdate.setString(2, ma);
//			n += stmtUpdate.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				stmtUpdate.close();
//			} catch (SQLException e) {
//				// TODO: handle exception
//				e.printStackTrace();
//			}
//		}
//		return n > 0;
//	}
}
