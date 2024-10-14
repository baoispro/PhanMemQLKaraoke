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
import java.util.Map;


import entity.LoaiPhong;

public interface LoaiPhong_DAO extends Remote{
	

	public ArrayList<LoaiPhong> getAlltbLoaiPhong() throws RemoteException;

	public LoaiPhong getLoaiPhongMoiNhat() throws RemoteException;

	public LoaiPhong getLoaiPhongTheoMa(String maLoaiPhong) throws RemoteException;

	public LoaiPhong getLoaiPhongTheoTen(String tenLoaiPhong) throws RemoteException;

	public boolean create(LoaiPhong lp) throws RemoteException;

	public boolean delete(String maLP) throws RemoteException;

	public boolean update(LoaiPhong lp) throws RemoteException;

//	// Khôi thêm
//	public Map<String, String> getAllLoaiPhong() {
//		Map<String, String> list = new HashMap<String, String>();
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "select MaLP, TenLoaiPhong\r\n" + "from LoaiPhong\r\n" + "group by MaLP, TenLoaiPhong";
//			statement = con.prepareStatement(sql);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				list.put(rs.getString("MaLP"), rs.getString("TenLoaiPhong"));
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return list;
//	}
}
