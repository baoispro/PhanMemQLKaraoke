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

//import connectDB.ConnectDB;
import entity.LoaiPhong;
import entity.Phong;

public interface Phong_DAO extends Remote {
	public ArrayList<Phong> getAlltbPhong() throws RemoteException;

	public Phong getPhongMoiNhat() throws RemoteException;

	public Phong getPhongTheoMa(String maPhong) throws RemoteException;

	public Phong getPhongTheoTen(String tenPhong) throws RemoteException;

	public boolean create(Phong p) throws RemoteException;

	public boolean delete(String maP) throws RemoteException;

	public boolean update(Phong p) throws RemoteException;

	public ArrayList<Phong> timKiemPhongOGiaoDienDP(String ten, String tinhTrang, String loaiPhong, int soNguoi)
			throws RemoteException;

	public ArrayList<Phong> timKiemPhong(String ten, String tinhTrang, String loaiPhong, String ma)
			throws RemoteException;

	public boolean capNhatTrangThaiPhong(String ma) throws RemoteException;

	public boolean capNhatTrangThaiPhong(String ma, String trangThai) throws RemoteException;
}
