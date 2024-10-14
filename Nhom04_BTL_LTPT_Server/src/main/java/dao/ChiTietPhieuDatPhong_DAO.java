package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Types;
import java.time.LocalTime;
import java.util.ArrayList;

import entity.ChiTietPhieuDatPhong;
import entity.PhieuDatPhong;
import entity.Phong;

public interface ChiTietPhieuDatPhong_DAO extends Remote {

	public boolean create(ChiTietPhieuDatPhong ctpdp) throws RemoteException;

	public boolean updateThoiGianSuDungPhong(LocalTime thoiGianSuDung, String maPhong, String maPDP)
			throws RemoteException;

	public ChiTietPhieuDatPhong check(String maPDP, String maP) throws RemoteException;

	public ChiTietPhieuDatPhong getChiTietPhieuDatPhongTheoMa(String maPDP) throws RemoteException;

	public ChiTietPhieuDatPhong getChiTietPhieuDatPhongTheoMaPhong(String maPhong) throws RemoteException;

	public boolean capNhatThoiGianSuDungPhong(String maPDP, LocalTime thoiGianTraPhong) throws RemoteException;

	public ArrayList<ChiTietPhieuDatPhong> getALLPhieuDatPhongTheoMa(String ma) throws RemoteException;

	public boolean checkThoiGianSuDung(String maPDP, String maP) throws RemoteException;
}
