package app;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import dao.ChiTietHoaDon_DAO;
import dao.ChiTietPhieuDatPhong_DAO;
import dao.ChiTietSuDungDichVuHoaDon_DAO;
import dao.ChiTietSuDungDichVu_DAO;
import dao.DichVu_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.KhuyenMai_DAO;
import dao.LoaiDichVu_DAO;
import dao.LoaiPhong_DAO;
import dao.NhanVien_DAO;
import dao.PhieuDatPhong_DAO;
import dao.Phong_DAO;
import dao.TaiKhoan_DAO;
import jakarta.persistence.EntityManager;
import service.ChiTietHoaDon_Service;
import service.ChiTietPhieuDatPhong_Service;
import service.ChiTietSuDungDichVuHoaDon_Service;
import service.ChiTietSuDungDichVu_Service;
import service.DichVu_Service;
import service.EntityManagerFactoryUtil;
import service.HoaDon_Service;
import service.KhachHang_Service;
import service.KhuyenMai_Service;
import service.LoaiDichVu_Service;
import service.LoaiPhong_Service;
import service.NhanVien_Service;
import service.PhieuDatPhong_Service;
import service.Phong_Service;
import service.TaiKhoan_Service;

public class Server {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		Registry registry = LocateRegistry.createRegistry(2024);

		EntityManagerFactoryUtil entityManagerFactory = new EntityManagerFactoryUtil();
		EntityManager entityManager = entityManagerFactory.getEnManager();

		TaiKhoan_DAO tk_dao = new TaiKhoan_Service(entityManager);
		registry.bind("tk_dao", tk_dao);
		
		Phong_DAO p_dao = new Phong_Service(entityManager);
		registry.bind("p_dao", p_dao);
		
		PhieuDatPhong_DAO pdp_dao = new PhieuDatPhong_Service(entityManager);
		registry.bind("pdp_dao", pdp_dao);
		
		NhanVien_DAO nv_dao = new NhanVien_Service(entityManager);
		registry.bind("nv_dao", nv_dao);
		
		LoaiPhong_DAO lp_dao = new LoaiPhong_Service(entityManager);
		registry.bind("lp_dao",lp_dao);
		
		LoaiDichVu_DAO ldv_dao = new LoaiDichVu_Service(entityManager);
		registry.bind("ldv_dao", ldv_dao);
		
		KhuyenMai_DAO km_dao = new KhuyenMai_Service(entityManager);
		registry.bind("km_dao", km_dao);
		
		KhachHang_DAO kh_dao = new KhachHang_Service(entityManager);
		registry.bind("kh_dao", kh_dao);
		
		HoaDon_DAO hd_dao = new HoaDon_Service(entityManager);
		registry.bind("hd_dao", hd_dao);
		
		DichVu_DAO dv_dao = new DichVu_Service(entityManager);
		registry.bind("dv_dao", dv_dao);
		
		ChiTietSuDungDichVuHoaDon_DAO ctsddvhd_dao = new ChiTietSuDungDichVuHoaDon_Service(entityManager);
		registry.bind("ctsddvhd_dao", ctsddvhd_dao);
		
		ChiTietSuDungDichVu_DAO ctsddv_dao = new ChiTietSuDungDichVu_Service(entityManager);
		registry.bind("ctsddv_dao", ctsddv_dao);
		
		ChiTietPhieuDatPhong_DAO ctpdp_dao = new ChiTietPhieuDatPhong_Service(entityManager);
		registry.bind("ctpdp_dao", ctpdp_dao);
		
		ChiTietHoaDon_DAO cthd_dao = new ChiTietHoaDon_Service(entityManager);
		registry.bind("cthd_dao",cthd_dao);

		System.out.println("Server ready");
	}
}
