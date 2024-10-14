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

import entity.NhanVien;

public interface NhanVien_DAO extends Remote {

	public ArrayList<NhanVien> getAlltbNhanVien() throws RemoteException;

	public NhanVien getNhanVienMoiNhat() throws RemoteException;

	public NhanVien getNhanVienTheoMa(String maNhanVien) throws RemoteException;

	public boolean create(NhanVien nv) throws RemoteException;

	public boolean delete(String maNV) throws RemoteException;

	public boolean update(NhanVien nv) throws RemoteException;

	public ArrayList<NhanVien> timKiemNV(String ma, String ten, Date ngaySinh, boolean gioiTinh, String sdt, String dc,
			String cv, String tt) throws RemoteException;

	public boolean kiemTraSDT(String sdt) throws RemoteException;

	public NhanVien getNhanVienTheoSDT(String sdt) throws RemoteException;
}
