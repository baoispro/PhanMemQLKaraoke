package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;

import entity.KhachHang;
import entity.NhanVien;
import entity.PhieuDatPhong;

public interface PhieuDatPhong_DAO extends Remote {
	

	public PhieuDatPhong getPhieuDatPhongMoiNhat() throws RemoteException;

	public PhieuDatPhong getPhieuDatPhongTheoMaPhong(String maPhong) throws RemoteException;

	public ArrayList<PhieuDatPhong> getDSPhieuDatPhongTheoMaPhong(String maPhong) throws RemoteException;

	public PhieuDatPhong getPhieuDatPhongTheoMaPhongVaTinhTrang(String maPhong, String tinhTrang) throws RemoteException;

	public boolean create(PhieuDatPhong pdp) throws RemoteException;

	public boolean deleteTheoMaPDP(String maPDP) throws RemoteException;
	
	public boolean deleteTheoMaPhieuDatPhong(String maPDP) throws RemoteException;

	public boolean update(String maPDP, Date ngayNhan, LocalTime gioNhan, LocalTime gioTra)throws RemoteException; 

	public boolean update(String maPDP, LocalTime gioNhan) throws RemoteException;

	// Đạt
	public ArrayList<PhieuDatPhong> getAlltbPhieuDatPhong() throws RemoteException;

//		Đạt
	public boolean deleteSauKhiThanhToan(String maPDP) throws RemoteException;
//		Đạt
	public ArrayList<PhieuDatPhong> getDSPhieuDatPhongTheoKhachHang(String tenKhachHang) throws RemoteException;
}
