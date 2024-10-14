package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.query.Query;

import dao.PhieuDatPhong_DAO;
import entity.KhachHang;
import entity.NhanVien;
import entity.PhieuDatPhong;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureQuery;
import jakarta.persistence.TypedQuery;

public class PhieuDatPhong_Service extends UnicastRemoteObject implements PhieuDatPhong_DAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -811362095701454428L;
	private EntityManager entityManager;

	public PhieuDatPhong_Service(EntityManager entityManager) throws RemoteException {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}

//	SELECT * FROM PhieuDatPhong WHERE MaPDP IN (SELECT MaPDP FROM ChiTietPhieuDatPhong WHERE MaP = ?)";
	public PhieuDatPhong getPhieuDatPhongMoiNhat() throws RemoteException {
		TypedQuery<PhieuDatPhong> query = entityManager
				.createQuery("SELECT pdp FROM PhieuDatPhong pdp ORDER BY pdp.ngayLapPhieu DESC", PhieuDatPhong.class);
		query.setMaxResults(1);

		List<PhieuDatPhong> resultList = query.getResultList();
		if (!resultList.isEmpty()) {
			return resultList.get(0);
		} else {
			return null;
		}
	}

	public PhieuDatPhong getPhieuDatPhongTheoMaPhong(String maPhong) throws RemoteException {
		TypedQuery<PhieuDatPhong> query = entityManager.createQuery(
				"SELECT pdp FROM PhieuDatPhong pdp WHERE pdp.maPDP IN (SELECT ct.pdp.maPDP FROM ChiTietPhieuDatPhong ct WHERE ct.phong.maPhong = :maPhong)",
				PhieuDatPhong.class);
		query.setParameter("maPhong", maPhong);

		try {
		    PhieuDatPhong pdp = query.getSingleResult();
		    // Xử lý kết quả ở đây nếu cần
		    return pdp;
		} catch (NoResultException e) {
		    // Xử lý khi không tìm thấy kết quả
		    System.out.println("Không tìm thấy phiếu đặt phòng cho mã phòng: " + maPhong);
		    // Hoặc trả về giá trị mặc định hoặc null, hoặc làm bất kỳ điều gì phù hợp với logic của bạn
		    return null;
		}

	}

	public ArrayList<PhieuDatPhong> getDSPhieuDatPhongTheoMaPhong(String maPhong) throws RemoteException {
		List<PhieuDatPhong> dspdp = new ArrayList<PhieuDatPhong>();
		try {
			String jpql = "SELECT pdp FROM PhieuDatPhong pdp where pdp.maPDP in (Select ct.pdp.maPDP from ChiTietPhieuDatPhong ct WHERE ct.maP = :maPhong)";
			TypedQuery<PhieuDatPhong> query = entityManager.createQuery(jpql, PhieuDatPhong.class);
			query.setParameter("maPhong", maPhong);
			dspdp = query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<PhieuDatPhong>(dspdp);
	}

	public PhieuDatPhong getPhieuDatPhongTheoMaPhongVaTinhTrang(String maPhong, String tinhTrang)
			throws RemoteException {
		PhieuDatPhong pdp = null;
		try {
			String jpql = "SELECT pdp FROM PhieuDatPhong pdp WHERE pdp.maPDP IN (SELECT ct.pdp.maPDP FROM ChiTietPhieuDatPhong ct WHERE ct.phong.maPhong = :maPhong) AND pdp.tinhTrang = :tinhTrang";
			TypedQuery<PhieuDatPhong> query = entityManager.createQuery(jpql, PhieuDatPhong.class);
			query.setParameter("maPhong", maPhong);
			query.setParameter("tinhTrang", tinhTrang);
			pdp = query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdp;
	}

	public boolean create(PhieuDatPhong pdp) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			// Gọi stored procedure để phát sinh mã PDP
			StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery("phatSinhIDPDP");
			storedProcedure.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);
			storedProcedure.execute();
			String maPDP = (String) storedProcedure.getOutputParameterValue(1);

			// Set mã PDP cho đối tượng pdp
			pdp.setMaPDP(maPDP);

			// Lưu đối tượng pdp vào cơ sở dữ liệu
			entityManager.persist(pdp);

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

	public boolean deleteTheoMaPDP(String maPDP) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			PhieuDatPhong pdp = entityManager.find(PhieuDatPhong.class, maPDP);
			if (pdp != null) {
				entityManager.remove(pdp);
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

	public boolean deleteTheoMaPhieuDatPhong(String maPDP) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			// Xóa tất cả các chi tiết phiếu đặt phòng có mã PDP tương ứng
			Query query = (Query) entityManager
					.createQuery("DELETE FROM ChiTietPhieuDatPhong c WHERE c.pdp.maPDP = :maPDP");
			query.setParameter("maPDP", maPDP);
			int deletedChiTietCount = query.executeUpdate();

			// Xóa phiếu đặt phòng có mã PDP tương ứng
			PhieuDatPhong pdp = entityManager.find(PhieuDatPhong.class, maPDP);
			if (pdp != null) {
				entityManager.remove(pdp);
			}

			transaction.commit();

			// Trả về true nếu ít nhất một bản ghi đã được xóa thành công
			return deletedChiTietCount > 0 || pdp != null;
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(String maPDP, Date ngayNhan, LocalTime gioNhan, LocalTime gioTra) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			// Tìm phiếu đặt phòng cần cập nhật
			PhieuDatPhong pdp = entityManager.find(PhieuDatPhong.class, maPDP);
			if (pdp != null) {
				// Cập nhật các trường thông tin của phiếu đặt phòng
				pdp.setNgayNhanPhong(ngayNhan);
				pdp.setGioNhanPhong(gioNhan);
				pdp.setGioTraPhong(gioTra);

				// Cập nhật phiếu đặt phòng trong cơ sở dữ liệu
				entityManager.merge(pdp);
			}

			transaction.commit();
			return pdp != null;
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(String maPDP, LocalTime gioNhan) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();

			// Tìm phiếu đặt phòng cần cập nhật
			PhieuDatPhong pdp = entityManager.find(PhieuDatPhong.class, maPDP);
			if (pdp != null) {
				// Cập nhật thời gian nhận phòng
				pdp.setGioNhanPhong(gioNhan);

				// Cập nhật phiếu đặt phòng trong cơ sở dữ liệu
				entityManager.merge(pdp);
			}

			transaction.commit();
			return pdp != null;
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<PhieuDatPhong> getAlltbPhieuDatPhong() throws RemoteException {
		ArrayList<PhieuDatPhong> dspdp = new ArrayList<PhieuDatPhong>();
		try {
			// Sử dụng JPQL để lấy danh sách phiếu đặt phòng từ cơ sở dữ liệu
			String jpql = "SELECT pdp FROM PhieuDatPhong pdp ORDER BY pdp.maPDP DESC";
			TypedQuery<PhieuDatPhong> query = entityManager.createQuery(jpql, PhieuDatPhong.class);
			List<PhieuDatPhong> resultList = query.getResultList();
			dspdp.addAll(resultList);
		} catch (Exception e) {
			// Xử lý ngoại lệ nếu có
			e.printStackTrace();
		}
		return dspdp;
	}

	public boolean deleteSauKhiThanhToan(String maPDP) throws RemoteException {
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();

			// Xóa các bản ghi từ bảng ChiTietSuDungDichVu
			Query deleteChiTietDichVuQuery = (Query) entityManager
					.createQuery("DELETE FROM ChiTietSuDungDichVu ctsddv WHERE ctsddv.phieuDatPhong.maPDP = :maPDP");
			deleteChiTietDichVuQuery.setParameter("maPDP", maPDP);
			int n1 = deleteChiTietDichVuQuery.executeUpdate();

			// Xóa các bản ghi từ bảng ChiTietPhieuDatPhong
			Query deleteChiTietPhieuDatPhongQuery = (Query) entityManager
					.createQuery("DELETE FROM ChiTietPhieuDatPhong ctpdp WHERE ctpdp.pdp.maPDP = :maPDP");
			deleteChiTietPhieuDatPhongQuery.setParameter("maPDP", maPDP);
			int n2 = deleteChiTietPhieuDatPhongQuery.executeUpdate();

			// Xóa bản ghi từ bảng PhieuDatPhong
			Query deletePhieuDatPhongQuery = (Query) entityManager
					.createQuery("DELETE FROM PhieuDatPhong pdp WHERE pdp.maPDP = :maPDP");
			deletePhieuDatPhongQuery.setParameter("maPDP", maPDP);
			int n3 = deletePhieuDatPhongQuery.executeUpdate();

			transaction.commit();

			// Kiểm tra xem có bất kỳ bản ghi nào đã được xóa không
			return (n1 + n2 + n3) > 0;
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<PhieuDatPhong> getDSPhieuDatPhongTheoKhachHang(String tenKhachHang) throws RemoteException {
		ArrayList<PhieuDatPhong> dspdp = new ArrayList<PhieuDatPhong>();
		try {
			String jpql = "SELECT pdp FROM PhieuDatPhong pdp WHERE pdp.kh.tenKhachHang = :tenKhachHang";
			TypedQuery<PhieuDatPhong> query = entityManager.createQuery(jpql, PhieuDatPhong.class);
			query.setParameter("tenKhachHang", tenKhachHang);
			dspdp = (ArrayList<PhieuDatPhong>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dspdp;
	}

//	public PhieuDatPhong getPhieuDatPhongMoiNhat() {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PhieuDatPhong pdp = null;
//
//		try {
//			Statement statement = con.createStatement();
//			String sql = "Select top 1 * from phieudatphong ORDER BY MaPDP DESC ";
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery(sql);
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maPDP = rs.getString(1);
//				NhanVien nv = new NhanVien(rs.getString(2));
//				KhachHang kh = new KhachHang(rs.getString(3));
//				LocalDateTime ngayLapHoaDon = rs.getTimestamp(4).toLocalDateTime();
//				Date NgayNhanPhong = rs.getDate(5);
//				LocalTime gioNhanPhong = rs.getTime(6).toLocalTime();
//				LocalTime gioTraPhong = rs.getTime(7).toLocalTime();
//				String tinhTrang = rs.getString(8);
//
//				pdp = new PhieuDatPhong(maPDP, nv, kh, ngayLapHoaDon, NgayNhanPhong, gioNhanPhong, gioTraPhong,
//						tinhTrang);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return pdp;
//	}
//
//	public PhieuDatPhong getPhieuDatPhongTheoMaPhong(String maPhong) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		PhieuDatPhong pdp = null;
//
//		try {
//			String sql = "SELECT * FROM PhieuDatPhong WHERE MaPDP IN (SELECT MaPDP FROM ChiTietPhieuDatPhong WHERE MaP = ?)";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maPhong);
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maPDP = rs.getString(1);
//				NhanVien nv = new NhanVien(rs.getString(2));
//				KhachHang kh = new KhachHang(rs.getString(3));
//				LocalDateTime ngayLapHoaDon = rs.getTimestamp(4).toLocalDateTime();
//				Date NgayNhanPhong = rs.getDate(5);
//				LocalTime gioNhanPhong = rs.getTime(6).toLocalTime();
//				LocalTime gioTraPhong = rs.getTime(7).toLocalTime();
//				String tinhTrang = rs.getString(8);
//
//				pdp = new PhieuDatPhong(maPDP, nv, kh, ngayLapHoaDon, NgayNhanPhong, gioNhanPhong, gioTraPhong,
//						tinhTrang);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return pdp;
//	}
//
//	public ArrayList<PhieuDatPhong> getDSPhieuDatPhongTheoMaPhong(String maPhong) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ArrayList<PhieuDatPhong> dspdp = new ArrayList<>();
//
//		try {
//			String sql = "SELECT * FROM PhieuDatPhong WHERE MaPDP IN (SELECT MaPDP FROM ChiTietPhieuDatPhong WHERE MaP = ?)";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maPhong);
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maPDP = rs.getString(1);
//				NhanVien nv = new NhanVien(rs.getString(2));
//				KhachHang kh = new KhachHang(rs.getString(3));
//				LocalDateTime ngayLapHoaDon = rs.getTimestamp(4).toLocalDateTime();
//				Date NgayNhanPhong = rs.getDate(5);
//				LocalTime gioNhanPhong = rs.getTime(6).toLocalTime();
//				LocalTime gioTraPhong = rs.getTime(7).toLocalTime();
//				String tinhTrang = rs.getString(8);
//
//				PhieuDatPhong pdp = new PhieuDatPhong(maPDP, nv, kh, ngayLapHoaDon, NgayNhanPhong, gioNhanPhong,
//						gioTraPhong, tinhTrang);
//				dspdp.add(pdp);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return dspdp;
//	}
//
//	public PhieuDatPhong getPhieuDatPhongTheoMaPhongVaTinhTrang(String maPhong, String tinhTrang) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		PhieuDatPhong pdp = null;
//
//		try {
//			String sql = "SELECT * FROM PhieuDatPhong WHERE MaPDP IN (SELECT MaPDP FROM ChiTietPhieuDatPhong WHERE MaP = ?) AND TinhTrang=?";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maPhong);
//			statement.setString(2, tinhTrang);
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maPDP = rs.getString(1);
//				NhanVien nv = new NhanVien(rs.getString(2));
//				KhachHang kh = new KhachHang(rs.getString(3));
//				LocalDateTime ngayLapHoaDon = rs.getTimestamp(4).toLocalDateTime();
//				Date NgayNhanPhong = rs.getDate(5);
//				LocalTime gioNhanPhong = rs.getTime(6).toLocalTime();
//				LocalTime gioTraPhong = rs.getTime(7).toLocalTime();
//				String tinhTrangPhieu = rs.getString(8);
//
//				pdp = new PhieuDatPhong(maPDP, nv, kh, ngayLapHoaDon, NgayNhanPhong, gioNhanPhong, gioTraPhong,
//						tinhTrangPhieu);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return pdp;
//	}
//
//	public boolean create(PhieuDatPhong pdp) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			// stored procedures tự động phát sinh
//			CallableStatement myCall = con.prepareCall("{call phatSinhIDPDP(?)}");
//			myCall.registerOutParameter(1, Types.VARCHAR);
//			myCall.execute();
//			String maPDP = myCall.getString(1);
//
//			stmt = con.prepareStatement("insert into " + "PhieuDatPhong values(?,?,?,?,?,?,?,?)");
//			stmt.setString(1, maPDP);
//			stmt.setString(2, pdp.getNv().getMaNV());
//			stmt.setString(3, pdp.getKh().getMaKH());
//			stmt.setTimestamp(4, Timestamp.valueOf(pdp.getNgayLapPhieu()));
//			stmt.setDate(5, new java.sql.Date(pdp.getNgayNhanPhong().getTime()));
//			stmt.setTime(6, java.sql.Time.valueOf(pdp.getGioNhanPhong()));
//			stmt.setTime(7, java.sql.Time.valueOf(pdp.getGioTraPhong()));
//			stmt.setString(8, pdp.getTinhTrang());
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
//	public boolean deleteTheoMaPDP(String maPDP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		int n = 0;
//		try {
//			String deleteQuery = "DELETE FROM PhieuDatPhong WHERE MaPDP = ?";
//			PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);
//			deleteStatement.setString(1, maPDP);
//			n = deleteStatement.executeUpdate();
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return n > 0;
//	}
//
//	public boolean deleteTheoMaPhieuDatPhong(String maPDP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		int n = 0;
//		try {
//			String deleteQuery = "DELETE FROM ChiTietPhieuDatPhong WHERE MaPDP = ?";
//			PreparedStatement deleteStatement = con.prepareStatement(deleteQuery);
//			deleteStatement.setString(1, maPDP);
//			String deleteQuery1 = "DELETE FROM PhieuDatPhong WHERE MaPDP = ?";
//			PreparedStatement deleteStatement1 = con.prepareStatement(deleteQuery1);
//			deleteStatement1.setString(1, maPDP);
//			n = deleteStatement.executeUpdate();
//			n += deleteStatement1.executeUpdate();
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return n > 0;
//	}
//
//	public boolean update(String maPDP, Date ngayNhan, LocalTime gioNhan, LocalTime gioTra) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			stmt = con.prepareStatement("update phieuDatPhong set ngayNhanPhong=?,"
//					+ "gioNhanPhong=?,giotraphongdukien=? " + "where maPDP=?");
//
//			stmt.setDate(1, new java.sql.Date(ngayNhan.getTime()));
//			stmt.setTime(2, java.sql.Time.valueOf(gioNhan));
//			stmt.setTime(3, java.sql.Time.valueOf(gioTra));
//			stmt.setString(4, maPDP);
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
//	public boolean update(String maPDP, LocalTime gioNhan) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			stmt = con.prepareStatement("update phieuDatPhong set " + "gioNhanPhong=? " + "where maPDP=?");
//
//			stmt.setTime(1, java.sql.Time.valueOf(gioNhan));
//			stmt.setString(2, maPDP);
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
//	// Đạt
//	public ArrayList<PhieuDatPhong> getAlltbPhieuDatPhong() {
//		ArrayList<PhieuDatPhong> dspdp = new ArrayList<PhieuDatPhong>();
//		try {
//			ConnectDB.getInstance();
//			Connection con = ConnectDB.getConnection();
//			String sql = "Select * from phieudatphong ORDER BY MaPDP DESC";
//			Statement statement = con.createStatement();
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery(sql);
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maPDP = rs.getString(1);
//				NhanVien nv = new NhanVien(rs.getString(2));
//				KhachHang kh = new KhachHang(rs.getString(3));
//				LocalDateTime ngayLapHoaDon = rs.getTimestamp(4).toLocalDateTime();
//				Date NgayNhanPhong = rs.getDate(5);
//				LocalTime gioNhanPhong = rs.getTime(6).toLocalTime();
//				LocalTime gioTraPhong = rs.getTime(7).toLocalTime();
//				String tinhtrang = rs.getString(8);
//				PhieuDatPhong pdp = new PhieuDatPhong(maPDP, nv, kh, ngayLapHoaDon, NgayNhanPhong, gioNhanPhong,
//						gioTraPhong, tinhtrang);
//				dspdp.add(pdp);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return dspdp;
//
//	}
//
////		Đạt
//	public boolean deleteSauKhiThanhToan(String maPDP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		int n = 0;
//
//		try {
//			// Delete records from ChiTietSuDungDichVu
//			String deleteChiTietDichVuQuery = "DELETE FROM ChiTietSuDungDichVu WHERE MaPDP = ?";
//			PreparedStatement deleteChiTietDichVuStatement = con.prepareStatement(deleteChiTietDichVuQuery);
//			deleteChiTietDichVuStatement.setString(1, maPDP);
//			n += deleteChiTietDichVuStatement.executeUpdate();
//
//			// Delete records from ChiTietPhieuDatPhong
//			String deleteChiTietPhieuDatPhongQuery = "DELETE FROM ChiTietPhieuDatPhong WHERE MaPDP = ?";
//			PreparedStatement deleteChiTietPhieuDatPhongStatement = con
//					.prepareStatement(deleteChiTietPhieuDatPhongQuery);
//			deleteChiTietPhieuDatPhongStatement.setString(1, maPDP);
//			n += deleteChiTietPhieuDatPhongStatement.executeUpdate();
//
//			// Delete record from PhieuDatPhong
//			String deletePhieuDatPhongQuery = "DELETE FROM PhieuDatPhong WHERE MaPDP = ?";
//			PreparedStatement deletePhieuDatPhongStatement = con.prepareStatement(deletePhieuDatPhongQuery);
//			deletePhieuDatPhongStatement.setString(1, maPDP);
//			n += deletePhieuDatPhongStatement.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return n > 0;
//	}
//
////		Đạt
//	public ArrayList<PhieuDatPhong> getDSPhieuDatPhongTheoKhachHang(String tenKhachHang) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ArrayList<PhieuDatPhong> dspdp = new ArrayList<>();
//
//		try {
//			String sql = "SELECT * FROM PhieuDatPhong WHERE MaKH=?";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, tenKhachHang);
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maPDP = rs.getString(1);
//				NhanVien nv = new NhanVien(rs.getString(2));
//				KhachHang kh = new KhachHang(rs.getString(3));
//				LocalDateTime ngayLapHoaDon = rs.getTimestamp(4).toLocalDateTime();
//				Date NgayNhanPhong = rs.getDate(5);
//				LocalTime gioNhanPhong = rs.getTime(6).toLocalTime();
//				LocalTime gioTraPhong = rs.getTime(7).toLocalTime();
//				String tinhTrang = rs.getString(8);
//
//				PhieuDatPhong pdp = new PhieuDatPhong(maPDP, nv, kh, ngayLapHoaDon, NgayNhanPhong, gioNhanPhong,
//						gioTraPhong, tinhTrang);
//				dspdp.add(pdp);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return dspdp;
//	}
}
