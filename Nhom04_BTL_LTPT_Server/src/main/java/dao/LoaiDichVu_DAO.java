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

import entity.LoaiDichVu;

public interface LoaiDichVu_DAO extends Remote {

	public ArrayList<LoaiDichVu> getAlltbLoaiDichVu() throws RemoteException;

	public LoaiDichVu getLoaiDichVuMoiNhat() throws RemoteException;

	public LoaiDichVu getLoaiDichVuTheoMa(String maLoaiDichVu) throws RemoteException;

	public LoaiDichVu getLoaiDichVuTheoTen(String tenLoaiDichVu) throws RemoteException;

	public boolean create(LoaiDichVu ldv) throws RemoteException;

	public boolean delete(String maLoaiDichVu) throws RemoteException;

	public boolean update(LoaiDichVu ldv) throws RemoteException;
}
