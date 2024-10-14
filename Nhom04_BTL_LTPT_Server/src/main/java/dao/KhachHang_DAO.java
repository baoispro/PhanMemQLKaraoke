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
//import connectDB.ConnectDB;
//import entity.DS_ThongKeKhachHang;
import entity.KhachHang;

public interface KhachHang_DAO extends Remote {
	public ArrayList<KhachHang> getAllKhachHang() throws RemoteException;

	public KhachHang getKhachHangMoiNhat() throws RemoteException;

	public KhachHang getKhachHangTheoMa(String maKH) throws RemoteException;

//	public KhachHang getKhachHangTheo(String CCCD) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		KhachHang kh = null;
//		PreparedStatement statement = null;
//
//		try {
//			String sql = "Select * from khachhang where CCCD=? ";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, CCCD);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			ResultSet rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinh = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String email = rs.getString(5);
//				String sdt = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinh = rs.getDate(8);
//
//				kh = new KhachHang(maKH, tenKH, gioiTinh, diaChi, email, sdt, cccd, ngaySinh);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return kh;
//	}

	public boolean create(KhachHang kh) throws RemoteException;

	public boolean delete(String maKH) throws RemoteException;

	public boolean update(KhachHang kh) throws RemoteException;

	public ArrayList<KhachHang> timKiemKH(String ma, String ten, boolean gioiTinh, String dc, String email, String sdt,
			String CCCD, Date ngaySinh) throws RemoteException;

	public boolean kiemTraSDT(String sdt) throws RemoteException;

	public KhachHang getKhachHangTheoSDT(String sdt) throws RemoteException;

	public KhachHang getKhachHangTheoTen(String tenKhachHang) throws RemoteException;

//	// KHÔI THÊM
//
//	public class KhachHangThongKe {
//		private KhachHang khachHang;
//		private int tongTheoTieuChi;
//
//		public KhachHangThongKe(KhachHang khachHang, int tongTheoTieuChi) {
//			this.khachHang = khachHang;
//			this.tongTheoTieuChi = tongTheoTieuChi;
//		}
//
//		public KhachHang getKhachHang() {
//			return khachHang;
//		}
//
//		public int getTongTheoTieuChi() {
//			return tongTheoTieuChi;
//		}
//
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKhachTheoNgay(Date ngay) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//
//		try {
//			String sql = "SELECT KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND NgayLapHD = ?\r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setDate(1, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int tongHoaDon = rs.getInt(9);
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, tongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKhachTheoThang(int thang, int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//
//		try {
//			String sql = "SELECT KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND MONTH(NgayLapHD) = ? AND YEAR(NgayLapHD) = ?\r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, thang);
//			statement.setInt(2, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKhachTheoNam(int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//
//		try {
//			String sql = "SELECT KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND YEAR(NgayLapHD) = ?\r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHMoiTheoNgay(Date ngay) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n" + "    HoaDon.NgayLapHD = ?\r\n"
//					+ "    AND NOT EXISTS (\r\n" + "        SELECT 1\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND NgayLapHD < ?\r\n" + "    )\r\n" + "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setDate(1, new java.sql.Date(ngay.getTime()));
//			statement.setDate(2, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHMoiTheoThang(int thang, int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ?  AND MONTH(HoaDon.NgayLapHD) = ?\r\n" + "    AND NOT EXISTS (\r\n"
//					+ "        SELECT 1\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) = ?  AND MONTH(HoaDon.NgayLapHD) < ?\r\n"
//					+ "    )\r\n" + "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, thang);
//			statement.setInt(3, nam);
//			statement.setInt(4, thang);
//
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHMoiTheoNam(int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ?\r\n" + "    AND NOT EXISTS (\r\n" + "        SELECT 1\r\n"
//					+ "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?\r\n" + "    )\r\n"
//					+ "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCuTheoNgay(Date ngay) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    HoaDon.NgayLapHD = ? -- Ngày hôm nay\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n"
//					+ "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND NgayLapHD < ? -- Từ hôm qua trở về trước\r\n"
//					+ "    ) >= 1\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND NgayLapHD < ? -- Từ hôm qua trở về trước\r\n"
//					+ "    ) < 10\r\n" + "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setDate(1, new java.sql.Date(ngay.getTime()));
//			statement.setDate(2, new java.sql.Date(ngay.getTime()));
//			statement.setDate(3, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				int soLuongHoaDon = rs.getInt(9);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCuTheoThang(int thang, int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ? AND MONTH(HoaDon.NgayLapHD) = ?-- Ngày hôm nay\r\n"
//					+ "    AND (\r\n" + "        SELECT COUNT(*)\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) = ? AND MONTH(HoaDon.NgayLapHD) < ? -- Từ hôm qua trở về trước\r\n"
//					+ "    ) >= 1\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) = ? AND MONTH(HoaDon.NgayLapHD) < ? -- Từ hôm qua trở về trước\r\n"
//					+ "    ) < 10\r\n" + "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, thang);
//			statement.setInt(3, nam);
//			statement.setInt(4, thang);
//			statement.setInt(5, nam);
//			statement.setInt(6, thang);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCuTheoNam(int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ?-- Ngày hôm nay\r\n" + "    AND (\r\n"
//					+ "        SELECT COUNT(*)\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?  -- Từ hôm qua trở về trước\r\n"
//					+ "    ) >= 1\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?  -- Từ hôm qua trở về trước\r\n"
//					+ "    ) < 10\r\n" + "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, nam);
//			statement.setInt(3, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHQuenTheoNgay(Date ngay) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    HoaDon.NgayLapHD = ? -- Ngày hôm nay\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n"
//					+ "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND NgayLapHD < ? -- Từ hôm qua trở về trước\r\n"
//					+ "    ) >= 10\r\n" + "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setDate(1, new java.sql.Date(ngay.getTime()));
//			statement.setDate(2, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHQuenTheoThang(int thang, int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ? AND MONTH(HoaDon.NgayLapHD) = ? -- Ngày hôm nay\r\n"
//					+ "    AND (\r\n" + "        SELECT COUNT(*)\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) = ? AND MONTH(HoaDon.NgayLapHD) < ? -- Từ hôm qua trở về trước\r\n"
//					+ "    ) >= 10\r\n" + "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, thang);
//			statement.setInt(3, nam);
//			statement.setInt(4, thang);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHQuenTheoNam(int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh,\r\n"
//					+ "    COUNT(HoaDon.MaHD) AS SoLuongHoaDon\r\n" + "FROM \r\n" + "    KhachHang\r\n" + "JOIN \r\n"
//					+ "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ?\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n"
//					+ "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?\r\n" + "    ) >= 10\r\n"
//					+ "GROUP BY \r\n"
//					+ "    KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCoHDNhieuNhatTheoNgay(Date ngay) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT TOP 10 KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND NgayLapHD = ?\r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh";
//			statement = con.prepareStatement(sql);
//			statement.setDate(1, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCoHDNhieuNhatTheoThang(int thang, int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT TOP 10 KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND YEAR(HoaDon.NgayLapHD) = ? AND MONTH(HoaDon.NgayLapHD) = ?\r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh\r\n"
//					+ "Order by COUNT(*) desc;\r\n" + "";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, thang);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCoHDNhieuNhatTheoNam(int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT TOP 10 KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND YEAR(HoaDon.NgayLapHD) = ? \r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh\r\n"
//					+ "Order by COUNT(*) desc";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCoHDItNhatTheoNgay(Date ngay) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT TOP 10 KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND HoaDon.NgayLapHD = ? \r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh\r\n"
//					+ "Order by COUNT(*) asc";
//			statement = con.prepareStatement(sql);
//			statement.setDate(1, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCoHDItNhatTheoThang(int thang, int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT TOP 10 KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND YEAR(HoaDon.NgayLapHD) = ? AND MONTH(HoaDon.NgayLapHD) = ?\r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh\r\n"
//					+ "Order by COUNT(*) asc";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			statement.setInt(2, thang);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHCoHDItNhatTheoNam(int nam) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT TOP 10 KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh, COUNT(*) AS SoLuongHoaDon\r\n"
//					+ "FROM KhachHang\r\n" + "JOIN HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n"
//					+ "WHERE HoaDon.MaHD IS NOT NULL AND YEAR(HoaDon.NgayLapHD) = ?\r\n"
//					+ "GROUP BY KhachHang.MaKH, KhachHang.HoVaTen, KhachHang.GioiTinh, KhachHang.DiaChi, KhachHang.Email, KhachHang.SoDienThoai, KhachHang.CCCD, KhachHang.NgaySinh\r\n"
//					+ "Order by COUNT(*) asc";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soLuongHoaDon = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soLuongHoaDon);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHTheoLoaiPhongTheoNgay(Date ngay, String maLP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT\r\n" + "    KH.*,\r\n"
//					+ "    SUM(DATEDIFF(MINUTE, '00:00:00', CTHD.ThoiGianSuDungPhong)) AS TongThoiGianSuDung,\r\n"
//					+ "    LP.TenLoaiPhong\r\n" + "FROM\r\n" + "    ChiTietHoaDon CTHD\r\n" + "JOIN\r\n"
//					+ "    HoaDon HD ON CTHD.MaHD = HD.MaHD\r\n" + "JOIN\r\n" + "    Phong P ON CTHD.MaP = P.MaP\r\n"
//					+ "JOIN\r\n" + "    LoaiPhong LP ON P.MaLP = LP.MaLP AND LP.MaLP = ?\r\n" + "JOIN\r\n"
//					+ "    KhachHang KH ON HD.MaKH = KH.MaKH\r\n" + "Where\r\n" + "	NgayLapHD = ?\r\n" + "GROUP BY\r\n"
//					+ "    KH.MaKH, KH.HoVaTen, LP.TenLoaiPhong, Kh.GioiTinh, kh.DiaChi, kh.Email, kh.SoDienThoai, kh.CCCD, kh.NgaySinh\r\n"
//					+ "Order By \r\n" + "	TongThoiGianSuDung DESC;";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maLP);
//			statement.setDate(2, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soGioSuDung = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soGioSuDung);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHTheoLoaiPhongTheoThang(int thang, int nam, String maLP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT\r\n" + "    KH.*,\r\n"
//					+ "    SUM(DATEDIFF(MINUTE, '00:00:00', CTHD.ThoiGianSuDungPhong)) AS TongThoiGianSuDung,\r\n"
//					+ "    LP.TenLoaiPhong\r\n" + "FROM\r\n" + "    ChiTietHoaDon CTHD\r\n" + "JOIN\r\n"
//					+ "    HoaDon HD ON CTHD.MaHD = HD.MaHD\r\n" + "JOIN\r\n" + "    Phong P ON CTHD.MaP = P.MaP\r\n"
//					+ "JOIN\r\n" + "    LoaiPhong LP ON P.MaLP = LP.MaLP AND LP.MaLP = ?\r\n" + "JOIN\r\n"
//					+ "    KhachHang KH ON HD.MaKH = KH.MaKH\r\n" + "Where\r\n"
//					+ "	MONTH(NgayLapHD) = ? and YEAR(NgayLapHD) = ?\r\n" + "GROUP BY\r\n"
//					+ "    KH.MaKH, KH.HoVaTen, LP.TenLoaiPhong, Kh.GioiTinh, kh.DiaChi, kh.Email, kh.SoDienThoai, kh.CCCD, kh.NgaySinh\r\n"
//					+ "Order By \r\n" + "	TongThoiGianSuDung DESC;";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maLP);
//			statement.setInt(2, thang);
//			statement.setInt(3, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soGioSuDung = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soGioSuDung);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHTheoLoaiPhongTheoNam(int nam, String maLP) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT\r\n" + "    KH.*,\r\n"
//					+ "    SUM(DATEDIFF(MINUTE, '00:00:00', CTHD.ThoiGianSuDungPhong)) AS TongThoiGianSuDung,\r\n"
//					+ "    LP.TenLoaiPhong\r\n" + "FROM\r\n" + "    ChiTietHoaDon CTHD\r\n" + "JOIN\r\n"
//					+ "    HoaDon HD ON CTHD.MaHD = HD.MaHD\r\n" + "JOIN\r\n" + "    Phong P ON CTHD.MaP = P.MaP\r\n"
//					+ "JOIN\r\n" + "    LoaiPhong LP ON P.MaLP = LP.MaLP AND LP.MaLP = ?\r\n" + "JOIN\r\n"
//					+ "    KhachHang KH ON HD.MaKH = KH.MaKH\r\n" + "Where\r\n" + "		YEAR(NgayLapHD) = ?\r\n"
//					+ "GROUP BY\r\n"
//					+ "    KH.MaKH, KH.HoVaTen, LP.TenLoaiPhong, Kh.GioiTinh, kh.DiaChi, kh.Email, kh.SoDienThoai, kh.CCCD, kh.NgaySinh\r\n"
//					+ "Order By \r\n" + "	TongThoiGianSuDung DESC;";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maLP);
//			statement.setInt(2, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int soGioSuDung = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, soGioSuDung);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHTheoDichVuTheoNgay(Date ngay, String maDV) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n" + "    KH.*,\r\n" + "    SUM(CSDV.SoLuong) AS SoLuongSuDung\r\n" + "FROM \r\n"
//					+ "    KhachHang KH\r\n" + "INNER JOIN \r\n" + "    HoaDon HD ON KH.MaKH = HD.MaKH\r\n"
//					+ "INNER JOIN \r\n" + "    ChiTietSuDungDichVuHoaDon CSDV ON HD.MaHD = CSDV.MaHD\r\n"
//					+ "INNER JOIN \r\n" + "    DichVu DV ON CSDV.MaDV = DV.MaDV and CSDV.MaDV = ?\r\n" + "Where\r\n"
//					+ "	NgayLapHD = ?\r\n" + "GROUP BY \r\n"
//					+ "    KH.MaKH, KH.HoVaTen, KH.GioiTinh, KH.DiaChi, KH.Email, KH.SoDienThoai, KH.CCCD, KH.NgaySinh\r\n"
//					+ "ORDER BY \r\n" + "    SUM(CSDV.SoLuong);";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maDV);
//			statement.setDate(2, new java.sql.Date(ngay.getTime()));
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int luotSuDung = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, luotSuDung);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHTheoDichVuTheoThang(int thang, int nam, String maDV) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n" + "    KH.*,\r\n" + "    SUM(CSDV.SoLuong) AS SoLuongSuDung\r\n" + "FROM \r\n"
//					+ "    KhachHang KH\r\n" + "INNER JOIN \r\n" + "    HoaDon HD ON KH.MaKH = HD.MaKH\r\n"
//					+ "INNER JOIN \r\n" + "    ChiTietSuDungDichVuHoaDon CSDV ON HD.MaHD = CSDV.MaHD\r\n"
//					+ "INNER JOIN \r\n" + "    DichVu DV ON CSDV.MaDV = DV.MaDV and CSDV.MaDV = ?\r\n" + "Where\r\n"
//					+ "	MONTH(NgayLapHD) = ? and YEAR(NgayLapHD) = ?\r\n" + "GROUP BY \r\n"
//					+ "    KH.MaKH, KH.HoVaTen, KH.GioiTinh, KH.DiaChi, KH.Email, KH.SoDienThoai, KH.CCCD, KH.NgaySinh\r\n"
//					+ "ORDER BY \r\n" + "    SUM(CSDV.SoLuong);";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maDV);
//			statement.setInt(2, thang);
//			statement.setInt(3, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int luotSuDung = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, luotSuDung);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public ArrayList<KhachHangThongKe> thongKeKHTheoDichVuTheoNam(int nam, String maDV) {
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		ArrayList<KhachHangThongKe> dskh = new ArrayList<KhachHangThongKe>();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n" + "    KH.*,\r\n" + "    SUM(CSDV.SoLuong) AS SoLuongSuDung\r\n" + "FROM \r\n"
//					+ "    KhachHang KH\r\n" + "INNER JOIN \r\n" + "    HoaDon HD ON KH.MaKH = HD.MaKH\r\n"
//					+ "INNER JOIN \r\n" + "    ChiTietSuDungDichVuHoaDon CSDV ON HD.MaHD = CSDV.MaHD\r\n"
//					+ "INNER JOIN \r\n" + "    DichVu DV ON CSDV.MaDV = DV.MaDV and CSDV.MaDV = ?\r\n" + "Where\r\n"
//					+ "	YEAR(NgayLapHD) = ?\r\n" + "GROUP BY \r\n"
//					+ "    KH.MaKH, KH.HoVaTen, KH.GioiTinh, KH.DiaChi, KH.Email, KH.SoDienThoai, KH.CCCD, KH.NgaySinh\r\n"
//					+ "ORDER BY \r\n" + "    SUM(CSDV.SoLuong);";
//			statement = con.prepareStatement(sql);
//			statement.setString(1, maDV);
//			statement.setInt(2, nam);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				// Lấy thông tin từ ResultSet và tạo đối tượng KhachHang
//				String maKH = rs.getString(1);
//				String tenKH = rs.getString(2);
//				boolean gioiTinhKH = rs.getBoolean(3);
//				String diaChi = rs.getString(4);
//				String emailKH = rs.getString(5);
//				String sdtKH = rs.getString(6);
//				String cccd = rs.getString(7);
//				Date ngaySinhKH = rs.getDate(8);
//				int luotSuDung = rs.getInt(9);
//
//				KhachHang khachHang = new KhachHang(maKH, tenKH, gioiTinhKH, diaChi, emailKH, sdtKH, cccd, ngaySinhKH);
//				KhachHangThongKe khtk = new KhachHangThongKe(khachHang, luotSuDung);
//				dskh.add(khtk);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		} finally {
//			// Đóng connection, statement và resultSet ở đây
//		}
//		return dskh;
//	}
//
//	public int[] thongKeBieuDoLineTongKH(int namTK) {
//		int[] intArray = new int[12];
//		for (int i = 0; i < intArray.length; i++) {
//			intArray[i] = 0;
//		}
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n" + "    MONTH(NgayLapHD) AS Thang,\r\n" + "    YEAR(NgayLapHD) AS Nam,\r\n"
//					+ "    COUNT(DISTINCT KhachHang.MaKH) AS TongSoKH\r\n" + "FROM \r\n" + "    KhachHang\r\n"
//					+ "LEFT JOIN \r\n" + "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(NgayLapHD) = ?\r\n" + "GROUP BY \r\n" + "    MONTH(NgayLapHD), YEAR(NgayLapHD)\r\n"
//					+ "ORDER BY \r\n" + "    YEAR(NgayLapHD) DESC, MONTH(NgayLapHD) DESC;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, namTK);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				intArray[rs.getInt(1) - 1] = rs.getInt(3);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return intArray;
//	}
//
//	public int[] thongKeBieuDoLineTongKHMoi(int namTK) {
//		int[] intArray = new int[12];
//		for (int i = 0; i < intArray.length; i++) {
//			intArray[i] = 0;
//		}
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n" + "    MONTH(NgayLapHD) AS Thang,\r\n" + "    YEAR(NgayLapHD) AS Nam,\r\n"
//					+ "    COUNT(DISTINCT KhachHang.MaKH) AS SoKhachHangMoi\r\n" + "FROM \r\n" + "    KhachHang\r\n"
//					+ "JOIN \r\n" + "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ?\r\n" + "    AND NOT EXISTS (\r\n" + "        SELECT 1\r\n"
//					+ "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?\r\n" + "    )\r\n"
//					+ "group by MONTH(NgayLapHD), YEAR(NgayLapHD)\r\n"
//					+ "order by  YEAR(NgayLapHD) DESC, MONTH(NgayLapHD) DESC;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, namTK);
//			statement.setInt(2, namTK);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				intArray[rs.getInt(1) - 1] = rs.getInt(3);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return intArray;
//	}
//
//	public int[] thongKeBieuDoLineTongKHCu(int namTK) {
//		int[] intArray = new int[12];
//		for (int i = 0; i < intArray.length; i++) {
//			intArray[i] = 0;
//		}
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n" + "    MONTH(NgayLapHD) AS Thang,\r\n" + "    YEAR(NgayLapHD) AS Nam,\r\n"
//					+ "    COUNT(DISTINCT KhachHang.MaKH) AS SoKhachHangMoi\r\n" + "FROM \r\n" + "    KhachHang\r\n"
//					+ "JOIN \r\n" + "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ?\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n"
//					+ "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?\r\n" + "    ) >= 1\r\n"
//					+ "    AND (\r\n" + "        SELECT COUNT(*)\r\n" + "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?\r\n" + "    ) < 10\r\n"
//					+ "GROUP BY \r\n" + "    MONTH(NgayLapHD), YEAR(NgayLapHD)\r\n" + "ORDER BY \r\n"
//					+ "    YEAR(NgayLapHD) DESC, MONTH(NgayLapHD) DESC;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, namTK);
//			statement.setInt(2, namTK);
//			statement.setInt(3, namTK);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				intArray[rs.getInt(1) - 1] = rs.getInt(3);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//
//		return intArray;
//	}
//
//	public int[] thongKeBieuDoLineTongKHQuen(int namTK) {
//		int[] intArray = new int[12];
//		for (int i = 0; i < intArray.length; i++) {
//			intArray[i] = 0;
//		}
//		ConnectDB.getInstance();
//		Connection con = ConnectDB.getConnection();
//		PreparedStatement statement = null;
//		ResultSet rs;
//		try {
//			String sql = "SELECT \r\n" + "    MONTH(NgayLapHD) AS Thang,\r\n" + "    YEAR(NgayLapHD) AS Nam,\r\n"
//					+ "    COUNT(DISTINCT KhachHang.MaKH) AS SoKhachHangMoi\r\n" + "FROM \r\n" + "    KhachHang\r\n"
//					+ "JOIN \r\n" + "    HoaDon ON KhachHang.MaKH = HoaDon.MaKH\r\n" + "WHERE \r\n"
//					+ "    YEAR(HoaDon.NgayLapHD) = ?\r\n" + "    AND (\r\n" + "        SELECT COUNT(*)\r\n"
//					+ "        FROM HoaDon\r\n"
//					+ "        WHERE MaKH = KhachHang.MaKH AND YEAR(HoaDon.NgayLapHD) < ?\r\n" + "    ) >= 10\r\n"
//					+ "GROUP BY \r\n" + "    MONTH(NgayLapHD), YEAR(NgayLapHD)\r\n" + "ORDER BY \r\n"
//					+ "    YEAR(NgayLapHD) DESC, MONTH(NgayLapHD) DESC;";
//			statement = con.prepareStatement(sql);
//			statement.setInt(1, namTK);
//			statement.setInt(2, namTK);
//			// Thực hiện câu lệnh sql trả về đối tượng ResultSet
//			rs = statement.executeQuery();
//			// Duyệt kết quả trả về
//			while (rs.next()) {// Di chuyển con trỏ xuống bản ghi kế tiếp
//				intArray[rs.getInt(1) - 1] = rs.getInt(3);
//			}
//		} catch (SQLException e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//		return intArray;
//	}
}
