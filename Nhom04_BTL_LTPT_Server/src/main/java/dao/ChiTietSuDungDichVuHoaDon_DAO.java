package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalTime;
import java.util.ArrayList;

//import connectDB.ConnectDB;
import entity.ChiTietHoaDon;
import entity.ChiTietPhieuDatPhong;
import entity.ChiTietSuDungDichVuHoaDon;
import entity.DichVu;
import entity.HoaDon;
import entity.Phong;

public interface ChiTietSuDungDichVuHoaDon_DAO extends Remote {

	public boolean create(ChiTietSuDungDichVuHoaDon ctsddvhd) throws RemoteException;

	public boolean update(ChiTietSuDungDichVuHoaDon ctsddvhd) throws RemoteException;

	public ArrayList<ChiTietSuDungDichVuHoaDon> getCTSDDVHDTheoMaHD(String maHD) throws RemoteException;
}
