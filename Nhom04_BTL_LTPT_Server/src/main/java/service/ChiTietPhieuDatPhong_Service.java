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

import dao.ChiTietPhieuDatPhong_DAO;
import entity.ChiTietPhieuDatPhong;
import entity.PhieuDatPhong;
import entity.Phong;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class ChiTietPhieuDatPhong_Service extends UnicastRemoteObject implements ChiTietPhieuDatPhong_DAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3742269536542225162L;
	private EntityManager entityManager;

	public ChiTietPhieuDatPhong_Service(EntityManager entityManager) throws RemoteException {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}

	public boolean create(ChiTietPhieuDatPhong ctpdp) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			entityManager.persist(ctpdp);
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

	public boolean updateThoiGianSuDungPhong(LocalTime thoiGianSuDung, String maPhong, String maPDP)
			throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			Query query = entityManager.createQuery(
					"SELECT c FROM ChiTietPhieuDatPhong c WHERE c.phong.maPhong = :maPhong AND c.pdp.maPDP = :maPDP");
			query.setParameter("maPhong", maPhong);
			query.setParameter("maPDP", maPDP);
			List<ChiTietPhieuDatPhong> results = query.getResultList();
			if (!results.isEmpty()) {
				ChiTietPhieuDatPhong ctpdp = results.get(0);
				ctpdp.setThoiGianSuDung(thoiGianSuDung);
				entityManager.merge(ctpdp);
				transaction.commit();
				return true;
			} else {
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

	public ChiTietPhieuDatPhong check(String maPDP, String maP) throws RemoteException {
		try {
			Query query = entityManager.createQuery(
					"SELECT c FROM ChiTietPhieuDatPhong c WHERE c.pdp.maPDP = :maPDP AND c.phong.maPhong = :maP");
			query.setParameter("maPDP", maPDP);
			query.setParameter("maP", maP);
			return (ChiTietPhieuDatPhong) query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ChiTietPhieuDatPhong getChiTietPhieuDatPhongTheoMa(String maPDP) throws RemoteException {
		try {
			TypedQuery<ChiTietPhieuDatPhong> query = entityManager.createQuery(
					"SELECT c FROM ChiTietPhieuDatPhong c WHERE c.pdp.maPDP = :maPDP", ChiTietPhieuDatPhong.class);
			query.setParameter("maPDP", maPDP);
			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public ChiTietPhieuDatPhong getChiTietPhieuDatPhongTheoMaPhong(String maPhong) throws RemoteException {
		try {
			TypedQuery<ChiTietPhieuDatPhong> query = entityManager.createQuery(
					"SELECT c FROM ChiTietPhieuDatPhong c WHERE c.phong.maPhong = :maPhong",
					ChiTietPhieuDatPhong.class);
			query.setParameter("maPhong", maPhong);
			return query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean capNhatThoiGianSuDungPhong(String maPDP, LocalTime thoiGianTraPhong) throws RemoteException {
		EntityTransaction transaction = entityManager.getTransaction();
		try {
			transaction.begin();
			Query query = entityManager.createQuery(
					"UPDATE ChiTietPhieuDatPhong c SET c.thoiGianSuDung = :thoiGianTraPhong WHERE c.pdp.maPDP = :maPDP and c.thoiGianSuDung IS NULL");
			query.setParameter("thoiGianTraPhong", thoiGianTraPhong);
			query.setParameter("maPDP", maPDP);
			int updatedCount = query.executeUpdate();
			transaction.commit();
			return updatedCount > 0;
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<ChiTietPhieuDatPhong> getALLPhieuDatPhongTheoMa(String ma) throws RemoteException {
		try {
			Query query = entityManager.createQuery("SELECT c FROM ChiTietPhieuDatPhong c WHERE c.pdp.maPDP = :ma");
			query.setParameter("ma", ma);
			return (ArrayList<ChiTietPhieuDatPhong>) query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<ChiTietPhieuDatPhong>();
		}
	}

	public boolean checkThoiGianSuDung(String maPDP, String maP) throws RemoteException {
		try {
			Query query = entityManager.createQuery("SELECT COUNT(c) FROM ChiTietPhieuDatPhong c "
					+ "WHERE c.pdp.maPDP = :maPDP AND c.phong.maPhong = :maP AND c.thoiGianSuDung IS NULL");
			query.setParameter("maPDP", maPDP);
			query.setParameter("maP", maP);
			Long count = (Long) query.getSingleResult();
			return count > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

//	public boolean create(ChiTietPhieuDatPhong ctpdp) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			stmt = con.prepareStatement("insert into " + "chitietphieudatphong values(?,?,?)");
//			stmt.setString(1, ctpdp.getPdp().getMaPDP());
//			stmt.setString(2, ctpdp.getPhong().getMaPhong());
//			if (ctpdp.getThoiGianSuDung() != null)
//				stmt.setTime(3, java.sql.Time.valueOf(ctpdp.getThoiGianSuDung()));
//			else
//				stmt.setNull(3, Types.TIME);
//
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
//	public boolean updateThoiGianSuDungPhong(LocalTime thoiGianSuDung, String maPhong, String maPDP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			stmt = con.prepareStatement(
//					"update chitietphieudatphong set thoigiansudungphong=? " + "where maP=? and maPDP=?");
//
//			stmt.setTime(1, java.sql.Time.valueOf(thoiGianSuDung));
//			stmt.setString(2, maPhong);
//			stmt.setString(3, maPDP);
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
//	public ChiTietPhieuDatPhong check(String maPDP, String maP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ChiTietPhieuDatPhong ctpdp = null;
//		PreparedStatement statement = null;
//		try {
//			String sql = "Select * from chitietphieudatphong where maPDP = ? and maP=?";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maPDP);
//			statement.setString(1, maP);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maPhieuDatPhong = rs.getString(1);
//				String maPhong = rs.getString(2);
//				LocalTime thoiGianSD = rs.getTime(3).toLocalTime();
//
//				ctpdp = new ChiTietPhieuDatPhong(new Phong(maPhong), new PhieuDatPhong(maPhieuDatPhong), thoiGianSD);
//
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return ctpdp;
//	}
//
//	public ChiTietPhieuDatPhong getChiTietPhieuDatPhongTheoMa(String maPDP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ChiTietPhieuDatPhong ctpdp = null;
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "select * from ChiTietPhieuDatPhong where MaPDP =? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maPDP);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				PhieuDatPhong maPhieu = new PhieuDatPhong(rs.getString(1));
//				Phong phong = new Phong(rs.getString(2));
//				LocalTime thoiGianSuDung = (rs.getTime(3) != null) ? rs.getTime(3).toLocalTime() : null;
//				ctpdp = new ChiTietPhieuDatPhong(phong, maPhieu, thoiGianSuDung);
//
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return ctpdp;
//	}
//
//	public ChiTietPhieuDatPhong getChiTietPhieuDatPhongTheoMaPhong(String maPhong) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ChiTietPhieuDatPhong ctpdp = null;
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "select * from ChiTietPhieuDatPhong where MaP =? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maPhong);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				PhieuDatPhong maPhieu = new PhieuDatPhong(rs.getString(1));
//				Phong phong = new Phong(rs.getString(2));
//				LocalTime thoiGianSuDung = (rs.getTime(3) != null) ? rs.getTime(3).toLocalTime() : LocalTime.MIN;
//				ctpdp = new ChiTietPhieuDatPhong(phong, maPhieu, thoiGianSuDung);
//
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return ctpdp;
//	}
//
//	public boolean capNhatThoiGianSuDungPhong(String maPDP, LocalTime thoiGianTraPhong) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//
//		try {
//			stmt = con.prepareStatement(
//					"UPDATE chiTietPhieuDatPhong SET thoiGianSuDungPhong = ? WHERE maPDP = ? and thoiGianSuDungPhong IS NULL");
//			stmt.setTime(1, Time.valueOf(thoiGianTraPhong));
//			stmt.setString(2, maPDP);
//			stmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return n > 0;
//	}
//
//	public ArrayList<ChiTietPhieuDatPhong> getALLPhieuDatPhongTheoMa(String ma) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ArrayList<ChiTietPhieuDatPhong> dsctpdp = new ArrayList<ChiTietPhieuDatPhong>();
//		try {
//			String sql = "select * from ChiTietPhieuDatPhong where MaPDP =?";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, ma);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				PhieuDatPhong maPhieu = new PhieuDatPhong(rs.getString(1));
//				Phong phong = new Phong(rs.getString(2));
//				LocalTime thoiGianSuDung = (rs.getTime(3) != null) ? rs.getTime(3).toLocalTime() : LocalTime.MIN;
//				ChiTietPhieuDatPhong ctpdp = new ChiTietPhieuDatPhong(phong, maPhieu, thoiGianSuDung);
//				dsctpdp.add(ctpdp);
//
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return dsctpdp;
//	}
//
//	public boolean checkThoiGianSuDung(String maPDP, String maP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//
//		try {
//			String sql = "SELECT * FROM chitietphieudatphong WHERE maPDP = ? AND maP = ? AND thoiGianSudungphong IS NULL";
//			stmt = con.prepareStatement(sql);
//			stmt.setString(1, maPDP);
//			stmt.setString(2, maP);
//			ResultSet rs = stmt.executeQuery();
//
//			// Check if ResultSet has any records
//			return rs.next();
//		} catch (SQLException e) {
//			// Handle exceptions
//			e.printStackTrace();
//			return false;
//		}
//	}
}
