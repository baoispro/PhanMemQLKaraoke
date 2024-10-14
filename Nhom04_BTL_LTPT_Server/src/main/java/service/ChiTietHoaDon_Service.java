package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Time;
import java.sql.Types;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import dao.ChiTietHoaDon_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.Phong;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class ChiTietHoaDon_Service extends UnicastRemoteObject implements ChiTietHoaDon_DAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2778652827421922691L;
	private EntityManager entityManager;

	public ChiTietHoaDon_Service(EntityManager entityManager) throws RemoteException {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}

	public ChiTietHoaDon getCTHDTheoTenPhong(String tenPhong) throws RemoteException {
		try {
			Query query = entityManager.createQuery("SELECT c FROM ChiTietHoaDon c WHERE c.tenPhong = :tenPhong");
			query.setParameter("tenPhong", tenPhong);
			List<ChiTietHoaDon> resultList = query.getResultList();
			if (!resultList.isEmpty()) {
				// Trả về phần tử đầu tiên nếu có
				return resultList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean create(ChiTietHoaDon cthd) throws RemoteException {
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();

			entityManager.persist(cthd);

			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public boolean update(ChiTietHoaDon cthd) throws RemoteException {
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();

			ChiTietHoaDon updatedCTHD = entityManager.merge(cthd);

			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public ChiTietHoaDon check(String maHD, String maP) throws RemoteException {
		try {
			TypedQuery<ChiTietHoaDon> query = entityManager.createQuery(
					"SELECT c FROM ChiTietHoaDon c WHERE c.hd.maHoaDon = :maHD AND c.p.maPhong = :maP",
					ChiTietHoaDon.class);
			query.setParameter("maHD", maHD);
			query.setParameter("maP", maP);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public ArrayList<ChiTietHoaDon> getCTHDTheoMaHD(String maHD) throws RemoteException {
		TypedQuery<ChiTietHoaDon> query = entityManager
				.createQuery("SELECT c FROM ChiTietHoaDon c WHERE c.hd.maHoaDon = :maHD", ChiTietHoaDon.class);
		query.setParameter("maHD", maHD);
		return (ArrayList<ChiTietHoaDon>) query.getResultList();
	}

//	public ChiTietHoaDon getCTHDTheoTenPhong(String tenPhong) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ChiTietHoaDon cthd = null;
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from chitiethoadon where tenP=? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, tenPhong);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				HoaDon hd = new HoaDon(rs.getString(1));
//				Phong phong = new Phong(rs.getString(2));
//				String ten = rs.getString(3);
//				LocalTime thoiGianSD = rs.getTime(4).toLocalTime();
//				double donGia = rs.getDouble(5);
//				String donVi = rs.getString(6);
//				cthd = new ChiTietHoaDon(hd, phong, tenPhong, donGia, donVi, thoiGianSD);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return cthd;
//	}
//
//	public boolean create(ChiTietHoaDon cthd) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			stmt = con.prepareStatement("insert into " + "chitiethoadon values(?,?,?,?,?,?)");
//			stmt.setString(1, cthd.getHd().getMaHoaDon());
//			if (cthd.getP().getMaPhong() != null)
//				stmt.setString(2, cthd.getP().getMaPhong());
//			else
//				stmt.setNull(2, Types.VARCHAR);
//			if (cthd.getTenPhong() != null)
//				stmt.setString(3, cthd.getTenPhong());
//			else
//				stmt.setNull(3, Types.VARCHAR);
//			if (cthd.getThoiGianSuDung() != null)
//				stmt.setTime(4, java.sql.Time.valueOf(cthd.getThoiGianSuDung()));
//			else
//				stmt.setNull(4, Types.TIME);
//			if (cthd.getDonGia() != 0)
//				stmt.setDouble(5, cthd.getDonGia());
//			else
//				stmt.setNull(5, Types.FLOAT);
//			if (cthd.getDonVi() != null)
//				stmt.setString(6, cthd.getDonVi());
//			else
//				stmt.setNull(6, Types.VARCHAR);
//			n += stmt.executeUpdate();
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
//	public boolean update(ChiTietHoaDon cthd) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			stmt = con.prepareStatement(
//					"update chiTietHoaDon set maP=?,tenP=?,ThoiGianSuDungPhong=?,DonGia=?,DonVi=? " + "where maHD=?");
//			stmt.setString(1, cthd.getP().getMaPhong());
//			stmt.setString(2, cthd.getTenPhong());
//			stmt.setTime(3, Time.valueOf(cthd.getThoiGianSuDung()));
//			stmt.setDouble(4, cthd.getDonGia());
//			stmt.setString(5, cthd.getDonVi());
//			stmt.setString(6, cthd.getHd().getMaHoaDon());
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
//	public ChiTietHoaDon check(String maHD, String maP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ChiTietHoaDon cthd = null;
//		PreparedStatement statement = null;
//		try {
//			String sql = "SELECT * FROM chitiethoadon WHERE maHD = ? AND maP = ?";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maHD);
//			statement.setString(2, maP);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String ma = rs.getString(1);
//				Phong maPhong = new Phong(rs.getString(2));
//				String tenPhong = rs.getString(3);
//				LocalTime thoiGianSD = rs.getTime(4).toLocalTime();
//				double dongia = rs.getDouble(5);
//				String donvi = rs.getString(6);
//
//				cthd = new ChiTietHoaDon(null, maPhong, tenPhong, dongia, donvi, thoiGianSD);
//
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return cthd;
//	}
//
//	public ArrayList<ChiTietHoaDon> getCTHDTheoMaHD(String maHD) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<ChiTietHoaDon> dscthd = new ArrayList<ChiTietHoaDon>();
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from chitiethoadon where mahd=? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maHD);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				HoaDon hd = new HoaDon(rs.getString(1));
//				Phong phong = new Phong(rs.getString(2));
//				String tenPhong = rs.getString(3);
//				LocalTime thoiGianSD = rs.getTime(4).toLocalTime();
//				double donGia = rs.getDouble(5);
//				String donVi = rs.getString(6);
//
//				ChiTietHoaDon cthd = new ChiTietHoaDon(hd, phong, tenPhong, donGia, donVi, thoiGianSD);
//				dscthd.add(cthd);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return dscthd;
//	}
}
