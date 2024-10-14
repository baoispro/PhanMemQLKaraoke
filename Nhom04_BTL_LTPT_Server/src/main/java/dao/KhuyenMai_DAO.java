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
import java.util.Date;


import entity.KhuyenMai;


public interface KhuyenMai_DAO extends Remote{
	public ArrayList<KhuyenMai> getAlltbKhuyenMai() throws RemoteException;

	public KhuyenMai getKhuyenMaiMoiNhat() throws RemoteException;

	public KhuyenMai getKhuyenMaiTheoMa(String maKhuyenMai) throws RemoteException;

	public KhuyenMai getKhuyenMaiTheoTen(String tenKhuyenMai) throws RemoteException;

	public boolean create(KhuyenMai km) throws RemoteException;

	public boolean delete(String maKM) throws RemoteException;

	public boolean update(KhuyenMai km) throws RemoteException;

	public ArrayList<KhuyenMai> timKiemKH(String ma, String ten, String dieuKienApDung, float GiaTriKm, Date ngayBatDau, Date ngayKetThuc) throws RemoteException;

}
