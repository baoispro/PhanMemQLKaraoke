package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

import entity.TaiKhoan;

public interface TaiKhoan_DAO extends Remote {

	public ArrayList<TaiKhoan> getAlltbTaiKhoan() throws RemoteException;

	public TaiKhoan getTaiKhoanTheoMa(String maNhanVien) throws RemoteException;

	public TaiKhoan getTaiKhoanMoiNhat() throws RemoteException;

	public TaiKhoan checkTaiKhoan(String tenDangNhap, String matKhau) throws RemoteException;

	public boolean create(TaiKhoan tk) throws RemoteException;

	public boolean delete(String maTK) throws RemoteException;

	public boolean update(TaiKhoan tk) throws RemoteException;

	public boolean updateMatKhau(String matKhau, String maTK) throws RemoteException;
}
