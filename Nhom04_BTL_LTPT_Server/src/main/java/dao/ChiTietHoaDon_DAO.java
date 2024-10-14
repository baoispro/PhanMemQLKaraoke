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

import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.Phong;

public interface ChiTietHoaDon_DAO extends Remote {

	public ChiTietHoaDon getCTHDTheoTenPhong(String tenPhong) throws RemoteException;

	public boolean create(ChiTietHoaDon cthd) throws RemoteException;

	public boolean update(ChiTietHoaDon cthd) throws RemoteException;

	public ChiTietHoaDon check(String maHD, String maP) throws RemoteException;

	public ArrayList<ChiTietHoaDon> getCTHDTheoMaHD(String maHD) throws RemoteException;
}
