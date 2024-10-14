package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dao.ChiTietSuDungDichVu_DAO;
import entity.ChiTietSuDungDichVu;
import entity.DichVu;
import entity.PhieuDatPhong;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class ChiTietSuDungDichVu_Service extends UnicastRemoteObject implements ChiTietSuDungDichVu_DAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2420841989941219404L;
	private EntityManager entityManager;

	public ChiTietSuDungDichVu_Service(EntityManager entityManager) throws RemoteException {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}

	public ArrayList<ChiTietSuDungDichVu> getCTSDDVTheoMa(String maPDP) throws RemoteException {
		ArrayList<ChiTietSuDungDichVu> dsctdv = new ArrayList<ChiTietSuDungDichVu>();
		try {
			String jpql = "SELECT c FROM ChiTietSuDungDichVu c WHERE c.pdp.maPDP = :maPDP";
			TypedQuery<ChiTietSuDungDichVu> query = entityManager.createQuery(jpql, ChiTietSuDungDichVu.class);
			query.setParameter("maPDP", maPDP);
			List<ChiTietSuDungDichVu> resultList = query.getResultList();
			dsctdv.addAll(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsctdv;
	}

	public boolean deleteTheoMaPDP(String maPDP) throws RemoteException {
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();

			String jpql = "DELETE FROM ChiTietSuDungDichVu c WHERE c.pdp.maPDP = :maPDP";
			int deletedCount = entityManager.createQuery(jpql).setParameter("maPDP", maPDP).executeUpdate();

			transaction.commit();
			return deletedCount > 0;
		} catch (Exception e) {
			if (transaction != null && transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	public boolean create(ChiTietSuDungDichVu ctdv) throws RemoteException {
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();

			entityManager.persist(ctdv);

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

//	public ArrayList<ChiTietSuDungDichVu> getCTSDDVTheoMa(String maPDP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ArrayList<ChiTietSuDungDichVu> dsctdv = new ArrayList<ChiTietSuDungDichVu>();
//		try {
//			String sql = "Select * from chitietsudungdichvu where maPDP=? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maPDP);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				PhieuDatPhong pdp = new PhieuDatPhong(rs.getString(1));
//				DichVu dv = new DichVu(rs.getString(2));
//				String tenDV = rs.getString(3);
//				int soLuong = rs.getInt(4);
//				double donGia = rs.getDouble(5);
//				String donVi = rs.getString(6);
//				ChiTietSuDungDichVu ctdv = new ChiTietSuDungDichVu(pdp, dv, tenDV, soLuong, donGia, donVi);
//				dsctdv.add(ctdv);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return dsctdv;
//	}
//
//	public boolean deleteTheoMaPDP(String maPDP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		int n = 0;
//		try {
//			String deleteQuery = "DELETE FROM chitietsudungdichvu WHERE MaPDP = ?";
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
//	public boolean create(ChiTietSuDungDichVu ctdv) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			stmt = con.prepareStatement("insert into " + "chitietsudungdichvu values(?,?,?,?,?,?)");
//			stmt.setString(1, ctdv.getPdp().getMaPDP());
//			stmt.setString(2, ctdv.getDichVu().getMaDichVu());
//			stmt.setString(3, ctdv.getTenDV());
//			stmt.setInt(4, ctdv.getSoLuong());
//			stmt.setDouble(5, ctdv.getDonGia());
//			stmt.setString(6, ctdv.getDonVi());
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
}
