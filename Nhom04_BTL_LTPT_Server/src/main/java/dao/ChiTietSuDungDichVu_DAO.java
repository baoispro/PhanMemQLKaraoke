package dao;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import entity.ChiTietSuDungDichVu;
import entity.DichVu;
import entity.PhieuDatPhong;

public interface ChiTietSuDungDichVu_DAO extends Remote {

	public ArrayList<ChiTietSuDungDichVu> getCTSDDVTheoMa(String maPDP) throws RemoteException;

	public boolean deleteTheoMaPDP(String maPDP) throws RemoteException;

	public boolean create(ChiTietSuDungDichVu ctdv) throws RemoteException;
}
