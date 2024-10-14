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

import dao.TaiKhoan_DAO;
//import connectDB.ConnectDB;
import entity.NhanVien;
import entity.TaiKhoan;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class TaiKhoan_Service extends UnicastRemoteObject implements TaiKhoan_DAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3194350053296887102L;
	private EntityManager entityManager;

	public TaiKhoan_Service(EntityManager entityManager) throws RemoteException {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}

	public ArrayList<TaiKhoan> getAlltbTaiKhoan() throws RemoteException {
		String sql = "SELECT t FROM TaiKhoan t"; // TaiKhoan là tên Entity tương ứng với bảng "taikhoan"
		TypedQuery<TaiKhoan> query = entityManager.createQuery(sql, TaiKhoan.class);
		List<TaiKhoan> resultList = query.getResultList();
		return new ArrayList<TaiKhoan>(resultList);
	}

	public TaiKhoan getTaiKhoanTheoMa(String maNhanVien) throws RemoteException {
		String jpql = "SELECT t FROM TaiKhoan t WHERE t.nv.maNV = :maNhanVien";
		TypedQuery<TaiKhoan> query = entityManager.createQuery(jpql, TaiKhoan.class);
		query.setParameter("maNhanVien", maNhanVien);
		return query.getSingleResult();
	}

	public TaiKhoan getTaiKhoanMoiNhat() throws RemoteException {
		String jpql = "SELECT t FROM TaiKhoan t ORDER BY t.maTK DESC";
		TypedQuery<TaiKhoan> query = entityManager.createQuery(jpql, TaiKhoan.class);
		query.setMaxResults(1); // Chỉ lấy một kết quả duy nhất
		return query.getSingleResult();
	}

	public TaiKhoan checkTaiKhoan(String tenDangNhap, String matKhau) throws RemoteException {
		String jpql = "SELECT t FROM TaiKhoan t WHERE t.tenDangNhap = :tenDangNhap AND t.matKhau = :matKhau";
		TypedQuery<TaiKhoan> query = entityManager.createQuery(jpql, TaiKhoan.class);
		query.setParameter("tenDangNhap", tenDangNhap);
		query.setParameter("matKhau", matKhau);
		TaiKhoan taiKhoan = null;
		try {
			taiKhoan = query.getSingleResult();
		} catch (Exception ex) {
			// Xử lý khi không tìm thấy tài khoản hoặc có nhiều hơn một kết quả
			ex.printStackTrace();
		}
		return taiKhoan;
	}

	public boolean create(TaiKhoan tk) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			// Thực hiện stored procedure để phát sinh ID tài khoản
			entityManager.createNativeQuery("CALL PhatSinhIDTK(:maTK)").setParameter("maTK", null) // null hoặc giá trị
																									// tùy thuộc vào
																									// stored procedure
					.executeUpdate();

			// Lưu tài khoản vào cơ sở dữ liệu
			entityManager.persist(tk);

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

	public boolean delete(String maTK) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			// Tìm và xóa tài khoản từ cơ sở dữ liệu
			TaiKhoan taiKhoan = entityManager.find(TaiKhoan.class, maTK);
			if (taiKhoan != null) {
				entityManager.remove(taiKhoan);
				transaction.commit();
				return true;
			} else {
				System.out.println("Không tìm thấy tài khoản với mã: " + maTK);
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

	public boolean update(TaiKhoan tk) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			// Lấy tài khoản từ cơ sở dữ liệu và cập nhật thông tin
			TaiKhoan existingTaiKhoan = entityManager.find(TaiKhoan.class, tk.getMaTK());
			if (existingTaiKhoan != null) {
				existingTaiKhoan.setTenDangNhap(tk.getTenDangNhap());
				existingTaiKhoan.setMatKhau(tk.getMatKhau());
				existingTaiKhoan.setNhanVien(tk.getNhanVien());

				entityManager.merge(existingTaiKhoan);
				transaction.commit();
				return true;
			} else {
				System.out.println("Không tìm thấy tài khoản với mã: " + tk.getMaTK());
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

	public boolean updateMatKhau(String matKhau, String maTK) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			// Lấy tài khoản từ cơ sở dữ liệu và cập nhật mật khẩu
			TaiKhoan existingTaiKhoan = entityManager.find(TaiKhoan.class, maTK);
			if (existingTaiKhoan != null) {
				existingTaiKhoan.setMatKhau(matKhau);

				entityManager.merge(existingTaiKhoan);
				transaction.commit();
				return true;
			} else {
				System.out.println("Không tìm thấy tài khoản với mã: " + maTK);
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

//	public ArrayList<TaiKhoan> getAlltbTaiKhoan() {
//		ArrayList<TaiKhoan> dstk = new ArrayList<TaiKhoan>();
//		try {
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			String sql = "Select * from taikhoan";
//			Statement statement = con.createStatement();
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery(sql);
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maTK = rs.getString(1);
//				String tenDN = rs.getString(2);
//				String mk = rs.getString(3);
//				String maNV = rs.getString(4);
//				NhanVien nv = new NhanVien(maNV);
//
//				TaiKhoan tk = new TaiKhoan(maTK, tenDN, mk, nv);
//				dstk.add(tk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return dstk;
//	}
//
//	public TaiKhoan getTaiKhoanTheoMa(String maNhanVien) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		TaiKhoan tk = null;
//		try {
//			String sql = "Select * from taiKhoan where maNV=? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maNhanVien);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maTK = rs.getString(1);
//				String tenDN = rs.getString(2);
//				String mk = rs.getString(3);
//				String maNV = rs.getString(4);
//				NhanVien nhanvien = new NhanVien(maNV);
//				tk = new TaiKhoan(maTK, tenDN, mk, nhanvien);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return tk;
//	}
//
//	public TaiKhoan getTaiKhoanMoiNhat() {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		TaiKhoan tk = null;
//
//		try {
//			Statement statement = con.createStatement();
//			String sql = "Select top 1 * from taiKhoan ORDER BY MaTK DESC ";
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery(sql);
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maTK = rs.getString(1);
//				String tenDN = rs.getString(2);
//				String matKhau = rs.getString(3);
//				String maNV = rs.getString(4);
//				NhanVien nv = new NhanVien(maNV);
//				tk = new TaiKhoan(maTK, tenDN, matKhau, nv);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return tk;
//	}
//
//	public TaiKhoan checkTaiKhoan(String tenDangNhap, String matKhau) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		TaiKhoan tk = null;
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from taikhoan where tendangnhap=? and matkhau=? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, tenDangNhap);
//			statement.setString(2, matKhau);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maTK = rs.getString(1);
//				String tenDN = rs.getString(2);
//				String mk = rs.getString(3);
//				String maNV = rs.getString(4);
//				NhanVien nv = new NhanVien(maNV);
//				tk = new TaiKhoan(maTK, tenDN, mk, nv);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return tk;
//	}
//
//	public boolean create(TaiKhoan tk) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			// stored procedures tự động phát sinh
//			CallableStatement myCall = con.prepareCall("{call PhatSinhIDTK(?)}");
//			myCall.registerOutParameter(1, Types.VARCHAR);
//			myCall.execute();
//			String maTK = myCall.getString(1);
//
//			stmt = con.prepareStatement("insert into " + "TaiKhoan values(?,?,?,?)");
//			stmt.setString(1, maTK);
//			stmt.setString(2, tk.getTenDangNhap());
//			stmt.setString(3, tk.getMatKhau());
//			stmt.setString(4, tk.getNhanVien().getMaNV());
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
//	public boolean delete(String maTK) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		int n = 0;
//		try {
//			String deleteQuery = "DELETE FROM TaiKhoan WHERE MaTK = ?";
//			PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);
//			deleteStatement.setString(1, maTK);
//			n = deleteStatement.executeUpdate();
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return n > 0;
//	}
//
//	public boolean update(TaiKhoan tk) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			stmt = con.prepareStatement("update taikhoan set tenDangNhap=?," + "MatKhau=?,MaNV=? " + "where maTK=?");
//
//			stmt.setString(1, tk.getTenDangNhap());
//			stmt.setString(2, tk.getMatKhau());
//			stmt.setString(3, tk.getNhanVien().getMaNV());
//			stmt.setString(4, tk.getMaTK());
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
//	public boolean updateMatKhau(String matKhau, String maTK) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			stmt = con.prepareStatement("update taikhoan set " + "MatKhau=? " + "where maTK=?");
//
//			stmt.setString(1, matKhau);
//			stmt.setString(2, maTK);
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
}
