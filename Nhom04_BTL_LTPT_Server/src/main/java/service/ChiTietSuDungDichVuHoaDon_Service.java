package service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import dao.ChiTietSuDungDichVuHoaDon_DAO;
import entity.ChiTietHoaDon;
import entity.ChiTietPhieuDatPhong;
import entity.ChiTietSuDungDichVuHoaDon;
import entity.DichVu;
import entity.HoaDon;
import entity.Phong;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class ChiTietSuDungDichVuHoaDon_Service extends UnicastRemoteObject implements ChiTietSuDungDichVuHoaDon_DAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2832687323254536734L;
	private EntityManager entityManager;

	public ChiTietSuDungDichVuHoaDon_Service(EntityManager entityManager) throws RemoteException {
		// TODO Auto-generated constructor stub
		this.entityManager = entityManager;
	}

	public boolean create(ChiTietSuDungDichVuHoaDon ctsddvhd) throws RemoteException {
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();

			// Tạo đối tượng ChiTietSuDungDichVuHoaDon mới và persist vào cơ sở dữ liệu
			entityManager.persist(ctsddvhd);

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

	public boolean update(ChiTietSuDungDichVuHoaDon ctsddvhd) throws RemoteException {
		EntityTransaction transaction = null;
		try {
			transaction = entityManager.getTransaction();
			transaction.begin();

			// Cập nhật thông tin của đối tượng trực tiếp với thông tin mới từ đối tượng
			// ctsddvhd
			entityManager.merge(ctsddvhd);

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

	public ArrayList<ChiTietSuDungDichVuHoaDon> getCTSDDVHDTheoMaHD(String maHD) throws RemoteException {
		ArrayList<ChiTietSuDungDichVuHoaDon> dsctsddvhd = new ArrayList<ChiTietSuDungDichVuHoaDon>();
		try {
			String jpql = "SELECT c FROM ChiTietSuDungDichVuHoaDon c WHERE c.hd.maHoaDon = :maHD";
			TypedQuery<ChiTietSuDungDichVuHoaDon> query = entityManager.createQuery(jpql,
					ChiTietSuDungDichVuHoaDon.class);
			query.setParameter("maHD", maHD);
			List<ChiTietSuDungDichVuHoaDon> resultList = query.getResultList();
			dsctsddvhd.addAll(resultList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dsctsddvhd;
	}

//	public boolean create(ChiTietSuDungDichVuHoaDon ctsddvhd) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			stmt = con.prepareStatement("insert into " + "ChiTietSuDungDichVuHoaDon values(?,?,?,?,?,?)");
//			stmt.setString(1, ctsddvhd.getHd().getMaHoaDon());
//
//			if (ctsddvhd.getDv().getMaDichVu() != null)
//				stmt.setString(2, ctsddvhd.getDv().getMaDichVu());
//			else
//				stmt.setNull(2, Types.VARCHAR);
//			if (ctsddvhd.getTenDichVu() != null)
//				stmt.setString(3, ctsddvhd.getTenDichVu());
//			else
//				stmt.setNull(3, Types.VARCHAR);
//			if (ctsddvhd.getSoLuong() != 0)
//				stmt.setInt(4, ctsddvhd.getSoLuong());
//			else
//				stmt.setNull(4, Types.INTEGER);
//			if (ctsddvhd.getDonGia() != 0)
//				stmt.setDouble(5, ctsddvhd.getDonGia());
//			else
//				stmt.setNull(5, Types.FLOAT);
//			if (ctsddvhd.getDonVi() != null)
//				stmt.setString(6, ctsddvhd.getDonVi());
//			else
//				stmt.setNull(6, Types.VARCHAR);
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
//	public boolean update(ChiTietSuDungDichVuHoaDon ctsddvhd) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement stmt = null;
//		int n = 0;
//		try {
//			stmt = con.prepareStatement(
//					"update ChiTietSuDungDichVuHoaDon set maDV=?, tenDV=?, SoLuong=?, DonGia=?, DonVi=? "
//							+ "where MaHD=?");
//			stmt.setString(1, ctsddvhd.getDv().getMaDichVu());
//			stmt.setString(2, ctsddvhd.getTenDichVu());
//			stmt.setInt(3, ctsddvhd.getSoLuong());
//			stmt.setDouble(4, ctsddvhd.getDonGia());
//			stmt.setString(5, ctsddvhd.getDonVi());
//			stmt.setString(6, ctsddvhd.getHd().getMaHoaDon());
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
//	public ArrayList<ChiTietSuDungDichVuHoaDon> getCTSDDVHDTheoMaHD(String maHD) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<ChiTietSuDungDichVuHoaDon> dsctsddvhd = new ArrayList<ChiTietSuDungDichVuHoaDon>();
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from ChiTietSuDungDichVuHoaDon where mahd=? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maHD);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				HoaDon hd = new HoaDon(rs.getString(1));
//				DichVu dv = new DichVu(rs.getString(2));
//				String tenDV = rs.getString(3);
//				int soLuong = rs.getInt(4);
//				double donGia = rs.getDouble(5);
//				String donVi = rs.getString(6);
//
//				ChiTietSuDungDichVuHoaDon ctsddvhd = new ChiTietSuDungDichVuHoaDon(hd, dv, tenDV, soLuong, donGia,
//						donVi);
//				dsctsddvhd.add(ctsddvhd);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return dsctsddvhd;
//	}
}
