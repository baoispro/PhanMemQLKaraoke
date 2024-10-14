package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import entity.DichVu;
import entity.LoaiDichVu;

public interface DichVu_DAO extends Remote {
	

	public ArrayList<DichVu> getAlltbDichVu() throws RemoteException;
	public ArrayList<DichVu> getAllDichVuTheoTenLoaiDV(String tenLoaiDV) throws RemoteException;

	public List<DichVu> getDichVuTheoLoai(String maLDV) throws RemoteException;

	public DichVu getDichVuTheoMa(String maDV) throws RemoteException;

	public DichVu getDichVuTheoTen(String tenDV) throws RemoteException;

	public boolean create(DichVu dv) throws RemoteException;

	public boolean delete(String maDV) throws RemoteException;

	public boolean update(DichVu dv) throws RemoteException;

	public boolean capNhapSoLuongTonKho(DichVu dv) throws RemoteException;

	public ArrayList<DichVu> timKiemDichVu(String ma, String ten, String giaBD, String giaKT, String ldv) throws RemoteException;

//	// KHÔI thêm
//	public Map<String, String> getAllDichVu() {
//		Map<String, String> list = new HashMap<String, String>();
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "select MaDV, TenDV\r\n" + "from DichVu\r\n" + "group by MaDV, TenDV";
//			statement = con.prepareStatement(sql);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				list.put(rs.getString("MaDV"), rs.getString("TenDV"));
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return list;
//	}

}
