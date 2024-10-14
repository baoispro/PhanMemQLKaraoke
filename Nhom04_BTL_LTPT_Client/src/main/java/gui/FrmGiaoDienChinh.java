/**
 * Người làm: Nguyễn Lê Gia Bảo
 * MSSV:21122241
 */
package gui;

import java.awt.*;
import javax.swing.*;

import entity.NhanVien;

//import entity.NhanVien;

import java.awt.event.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FrmGiaoDienChinh extends JFrame implements ActionListener {
	// Khai báo các biến
	private JButton btnDangXuat;
	private JMenuBar mnu;
	private JMenu menuNV, menuKH, menuDV, menuPhong, menuHD, menuThongKe, menuHeThong, menuKhuyenMai, menuUserProfile;
	// menuItem của hệ thống
	private JMenuItem mniTrangChu, mniTroGiup, mniThoat, mniDangXuat;
	// menuItem của nhân viên
	private JMenuItem mniCapNhatNhanVien, mniTimKiemNV, mniTaiKhoan;
	// menuItem của khách hàng
	private JMenuItem mniCapNhatKhachHang, mniTimKiemKhachHang;
	// menuItem của dịch vụ
	private JMenuItem mniCapNhatDichVu, mniTimKiemDichVu, mniLoaiDichVu;
	// menuItem của phòng
	private JMenuItem mniCapNhatPhong, mniCapNhatLoaiPhong, mniTimKiemPhong, mniDatPhong;
	// menuItem của khuyến mãi
	private JMenuItem mniCapNhatKhuyenMai, mniTimKiemKhuyenMai;
	// menuItem của hóa đơn
	private JMenuItem mniTimKiemHoaDon, mniLapHoaDon;
	// menuItem của thống kê
	private JMenuItem mniThongKeDoanhThu, mniThongKeKhachHang;
	// menuItem của user profile
	private JMenuItem mniThongTinCaNhan, mniDoiMatKhau, mniDangXuatTK;

	private JPanel pnlMain;
	private JPanel pnlGiaoDienTrangChu;
	private JPanel pnlGiaoDienCapNhatNV, pnlGiaoDienTimKiemNV, pnlGiaoDienTaiKhoan;
	private JPanel pnlGiaoDienCapNhatKH, pnlGiaoDienTimKiemKH;
	private JPanel pnlGiaoDienCapNhatDV, pnlGiaoDienTimKiemDV, pnlGiaoDienLoaiDV;
	private JPanel pnlGiaoDienCapNhatKhuyenMai, pnlGiaoDienTimKiemKhuyenMai;
	private JPanel pnlGiaoDienCapNhatP, pnlGiaoDienTimKiemP, pnlGiaoDienCapNhatLP, pnlGiaoDienDP;
	private JPanel pnlGiaoDienLapHoaDon, pnlGiaoDienTimKiemHoaDon;
	private JPanel pnlGiaoDienThongKeDT, pnlGiaoDienThongKeKH;
	private JPanel pnlGiaoDienThongTinCaNhan, pnlGiaoDienDoiMatKhau;

	private JButton btnThoat;

//	tiêu đề header
	private JLabel lblTitle;

	// tạo 1 biến nhân viên đang đăng nhập
	private NhanVien nvThucThi;
	private boolean khachDN;

	// Constructor
	public FrmGiaoDienChinh(NhanVien nv, boolean khachDangNhap) throws RemoteException, NotBoundException {
		super("Quản lí Karaoke Nice ");
		nvThucThi = nv;
		khachDN = khachDangNhap;
		gui();
		// Thiết lập JFrame
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
//		setSize(1300, 740);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void gui() throws RemoteException, NotBoundException {
		// TODO Auto-generated method stub
		mnu = new JMenuBar();
		// Tạo menu hệ thống
		menuHeThong = new JMenu("Hệ thống");
		menuHeThong.setIcon(getIcon("data/images/setting.png", 30, 30));
		mnu.add(menuHeThong);
		menuHeThong.setFont(new Font("Time New Roman", Font.BOLD, 16));

		// Tạo menu item hệ thống
		mniTrangChu = new JMenuItem("Trang chủ");
		mniTrangChu.addActionListener(this);
		menuHeThong.add(mniTrangChu);

		mniTroGiup = new JMenuItem("Trợ giúp");
		mniTroGiup.addActionListener(this);
		menuHeThong.add(mniTroGiup);

		mniThoat = new JMenuItem("Thoát");
		mniThoat.addActionListener(this);
		menuHeThong.add(mniThoat);

		mniDangXuat = new JMenuItem("Đăng xuất");
		mniDangXuat.addActionListener(this);
		menuHeThong.add(mniDangXuat);

		// Tạo menu nhân viên
		menuNV = new JMenu("Nhân Viên");
		menuNV.setIcon(getIcon("data/images/nhanvien.png", 30, 30));
		mnu.add(menuNV);
		menuNV.setFont(new Font("Time New Roman", Font.BOLD, 16));

		// Ngăn không cho nhân viên thay đổi, chỉ cho quản lí thay đổi
		if (khachDN == true || nvThucThi.getChucVu().equalsIgnoreCase("Nhân viên")) {
			menuNV.setEnabled(false);
		}

		// Tạo menu item nhân viên
		mniCapNhatNhanVien = new JMenuItem("Cập nhật nhân viên");
		mniCapNhatNhanVien.addActionListener(this);
		menuNV.add(mniCapNhatNhanVien);

		mniTimKiemNV = new JMenuItem("Tìm kiếm");
		mniTimKiemNV.addActionListener(this);
		menuNV.add(mniTimKiemNV);

		mniTaiKhoan = new JMenuItem("Tài khoản");
		mniTaiKhoan.addActionListener(this);
		menuNV.add(mniTaiKhoan);

		// Tạo menu khách hàng
		menuKH = new JMenu("Khách Hàng");
		menuKH.setFont(new Font("Time New Roman", Font.BOLD, 16));
		menuKH.setIcon(getIcon("data/images/shopping.png", 30, 30));
		mnu.add(menuKH);
		if (khachDN == true) {
			menuKH.setEnabled(false);
		}

		// Tạo menu item khách hàng
		mniCapNhatKhachHang = new JMenuItem("Cập nhật khách hàng");
		mniCapNhatKhachHang.addActionListener(this);
		menuKH.add(mniCapNhatKhachHang);

		mniTimKiemKhachHang = new JMenuItem("Tìm kiếm");
		mniTimKiemKhachHang.addActionListener(this);
		menuKH.add(mniTimKiemKhachHang);

		// Tạo menu dịch vụ
		menuDV = new JMenu("Dịch Vụ");
		menuDV.setFont(new Font("Time New Roman", Font.BOLD, 16));
		menuDV.setIcon(getIcon("data/images/service.png", 32, 32));
		mnu.add(menuDV);

		// Tạo menu item dịch vụ
		mniCapNhatDichVu = new JMenuItem("Cập nhật dịch vụ");
		mniCapNhatDichVu.addActionListener(this);
		menuDV.add(mniCapNhatDichVu);

		mniTimKiemDichVu = new JMenuItem("Tìm kiếm");
		mniTimKiemDichVu.addActionListener(this);
		menuDV.add(mniTimKiemDichVu);

		mniLoaiDichVu = new JMenuItem("Loại Dịch vụ");
		mniLoaiDichVu.addActionListener(this);
		menuDV.add(mniLoaiDichVu);
		if (khachDN == true) {
			mniCapNhatDichVu.setEnabled(false);
			mniLoaiDichVu.setEnabled(false);
		}

		// Tạo menu Phòng
		menuPhong = new JMenu("Phòng");
		menuPhong.setFont(new Font("Time New Roman", Font.BOLD, 16));
		menuPhong.setIcon(getIcon("data/images/room.png", 32, 32));
		mnu.add(menuPhong);

		// Tạo menu item phòng
		mniCapNhatPhong = new JMenuItem("Cập nhật phòng");
		mniCapNhatPhong.addActionListener(this);
		menuPhong.add(mniCapNhatPhong);

		mniCapNhatLoaiPhong = new JMenuItem("Cập nhật loại phòng");
		mniCapNhatLoaiPhong.addActionListener(this);
		menuPhong.add(mniCapNhatLoaiPhong);

		mniTimKiemPhong = new JMenuItem("Tìm kiếm");
		mniTimKiemPhong.addActionListener(this);
		menuPhong.add(mniTimKiemPhong);

		mniDatPhong = new JMenuItem("Đặt phòng");
		mniDatPhong.addActionListener(this);
		menuPhong.add(mniDatPhong);
		if (khachDN == true) {
			mniCapNhatPhong.setEnabled(false);
			mniDatPhong.setEnabled(false);
			mniCapNhatLoaiPhong.setEnabled(false);
		}

		// Tạo menu khuyến mãi
		menuKhuyenMai = new JMenu("Khuyến mãi");
		menuKhuyenMai.setIcon(getIcon("data/images/tag.png", 30, 30));
		mnu.add(menuKhuyenMai);
		menuKhuyenMai.setFont(new Font("Time New Roman", Font.BOLD, 16));
		if (khachDN == true || nvThucThi.getChucVu().equalsIgnoreCase("Nhân viên")) {
			menuKhuyenMai.setEnabled(false);
		}

		// Tạo menu item khuyến mãi
		mniCapNhatKhuyenMai = new JMenuItem("Cập nhật khuyễn mãi");
		mniCapNhatKhuyenMai.addActionListener(this);
		menuKhuyenMai.add(mniCapNhatKhuyenMai);

		mniTimKiemKhuyenMai = new JMenuItem("Tìm kiếm");
		mniTimKiemKhuyenMai.addActionListener(this);
		menuKhuyenMai.add(mniTimKiemKhuyenMai);

		// Tạo menu hóa đơn
		menuHD = new JMenu("Hóa Đơn");
		menuHD.setFont(new Font("Time New Roman", Font.BOLD, 16));
		menuHD.setIcon(getIcon("data/images/hoadon.png", 32, 32));
		mnu.add(menuHD);
		if (khachDN == true) {
			menuHD.setEnabled(false);
		}

		// Tạo menu item hóa đơn
		mniLapHoaDon = new JMenuItem("Lập hóa đơn");
		mniLapHoaDon.addActionListener(this);
		menuHD.add(mniLapHoaDon);

		mniTimKiemHoaDon = new JMenuItem("Tìm kiếm hóa đơn");
		mniTimKiemHoaDon.addActionListener(this);
		menuHD.add(mniTimKiemHoaDon);
		if (nvThucThi != null && nvThucThi.getChucVu().equalsIgnoreCase("Nhân viên")) {
			mniTimKiemHoaDon.setEnabled(false);
		}

		// Tạo menu thống kê
		menuThongKe = new JMenu("Thống Kê");
		menuThongKe.setFont(new Font("Time New Roman", Font.BOLD, 16));
		menuThongKe.setIcon(getIcon("data/images/thongke.png", 32, 32));
		mnu.add(menuThongKe);
		if (khachDN == true) {
			menuThongKe.setEnabled(false);
		}

		// Tạo menu item thống kê
		mniThongKeDoanhThu = new JMenuItem("Thống kê doanh thu");
		mniThongKeDoanhThu.addActionListener(this);
		menuThongKe.add(mniThongKeDoanhThu);

		mniThongKeKhachHang = new JMenuItem("Thống kê khách hàng");
		mniThongKeKhachHang.addActionListener(this);
		menuThongKe.add(mniThongKeKhachHang);

		// User profile
		mnu.add(Box.createHorizontalGlue());
		if (nvThucThi != null) {
			menuUserProfile = new JMenu("Xin chào, " + nvThucThi.getTenNV());
		} else {
			menuUserProfile = new JMenu("Xin chào, khách vãng lai");
		}
		menuUserProfile.setIcon(getIcon("data/images/profile.png", 22, 22));
		mnu.add(menuUserProfile);
		menuUserProfile.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				menuUserProfile.setForeground(new Color(0x1da1f2));
			}

			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				menuUserProfile.setForeground(Color.black);
			}

			
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				menuUserProfile.setForeground(new Color(0x1da1f2));
			}

			
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});

		// Tạo menu item user profile
		mniThongTinCaNhan = new JMenuItem("Thông tin cá nhân");
		mniThongTinCaNhan.addActionListener(this);
		menuUserProfile.add(mniThongTinCaNhan);

		mniDoiMatKhau = new JMenuItem("Đổi mật khẩu");
		mniDoiMatKhau.addActionListener(this);
		menuUserProfile.add(mniDoiMatKhau);

		mniDangXuatTK = new JMenuItem("Đăng xuất");
		mniDangXuatTK.addActionListener(this);
		menuUserProfile.add(mniDangXuatTK);
		if (khachDN == true) {
			mniThongTinCaNhan.setEnabled(false);
			mniDoiMatKhau.setEnabled(false);
		}

		// Tạo các panel chính
		pnlMain = new JPanel();
		pnlMain.setLayout(new BorderLayout());

		// Tạo panel header luôn xuất hiện trong mọi giao diện
		JPanel pnlHeader = new JPanel() {
			
			protected void paintComponent(Graphics g) {
				// TODO Auto-generated method stub
				super.paintComponent(g);
				Paint p = new GradientPaint(0.0f, 0.0f, new Color(0xff005b), getWidth(), getHeight(),
						new Color(0xffe53b), true);
				Graphics2D g2d = (Graphics2D) g;
				g2d.setPaint(p);
				g2d.fillRect(0, 0, getWidth(), getHeight());

			}
		};
		pnlHeader.setLayout(new BorderLayout());
		lblTitle = new JLabel("HỆ THỐNG QUẢN LÍ KARAOKE NICE", SwingConstants.CENTER);
		lblTitle.setForeground(Color.white);
		lblTitle.setFont(new Font("Time New Roman", Font.BOLD, 40));
		JLabel lbLogo = new JLabel(getIcon("data/images/logo.png", 150, 100));
		final JLabel lbltime = new JLabel("", SwingConstants.CENTER);
		lbltime.setFont(new Font("Calibri", Font.BOLD, 30));

		int delay = 100;
		Timer timer = new Timer(delay, new ActionListener() {

			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LocalDateTime now = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss a");
				String formattedDateTime = now.format(formatter);
				lbltime.setText(formattedDateTime);
			}
		});

		timer.start();
		lbltime.setForeground(Color.white);
		btnDangXuat = new JButton("Đăng xuất", getIcon("data/images/dangxuat.png", 18, 24));
		btnThoat = new JButton("   Thoát   ", getIcon("data/images/shutdown.png", 24, 24));
		btnThoat.addActionListener(this);
		JPanel pnlSubEast = new JPanel();
		pnlSubEast.setOpaque(false);
		pnlSubEast.setLayout(new BoxLayout(pnlSubEast, BoxLayout.Y_AXIS));
		Box b = Box.createVerticalBox();
		Box b1 = Box.createHorizontalBox();
		b1.add(Box.createHorizontalStrut(20));
		b1.add(lbltime);
		b1.add(Box.createHorizontalStrut(20));
		b.add(b1);
		pnlSubEast.add(b);

		pnlHeader.add(lblTitle, BorderLayout.CENTER);
		pnlHeader.add(lbLogo, BorderLayout.WEST);
		pnlHeader.add(pnlSubEast, BorderLayout.EAST);

		pnlMain.add(pnlHeader, BorderLayout.NORTH);

		// panel chứa giao diện cập nhật nhân viên
		pnlGiaoDienCapNhatNV = new PnGiaoDienCapNhatNhanVien(this);
		pnlMain.add(pnlGiaoDienCapNhatNV, BorderLayout.CENTER);

		// panel chứa giao diện tìm kiếm nhân viên
		pnlGiaoDienTimKiemNV = new PnGiaoDienTimKiemNhanVien();
		pnlMain.add(pnlGiaoDienTimKiemNV, BorderLayout.CENTER);

		// panel chứa giao diện tài khoản
		pnlGiaoDienTaiKhoan = new PnGiaoDienTaiKhoan(null);
		pnlMain.add(pnlGiaoDienTaiKhoan, BorderLayout.CENTER);

		// panel chứa giao diện cập nhật khách hàng
		pnlGiaoDienCapNhatKH = new PnGiaoDienCapNhatKhachHang();
		pnlMain.add(pnlGiaoDienCapNhatKH, BorderLayout.CENTER);

		// panel chứa giao diện tìm kiếm khách hàng
		pnlGiaoDienTimKiemKH = new PnGiaoDienTimKiemKhachHang();
		pnlMain.add(pnlGiaoDienTimKiemKH, BorderLayout.CENTER);

		// panel chứa giao diện cập nhật dịch vụ
		pnlGiaoDienCapNhatDV = new PnGiaoDienCapNhatDichVu();
		pnlMain.add(pnlGiaoDienCapNhatDV, BorderLayout.CENTER);

		// panel chứa giao diện tìm kiếm dịch vụ
		pnlGiaoDienTimKiemDV = new PnGiaoDienTimKiemDichVu();
		pnlMain.add(pnlGiaoDienTimKiemDV, BorderLayout.CENTER);

		// panel chứa giao diện loại dịch vụ
		pnlGiaoDienLoaiDV = new PnGiaoDienLoaiDichVu();
		pnlMain.add(pnlGiaoDienLoaiDV, BorderLayout.CENTER);

		// panel chứa giao diện cập nhật khuyến mãi
		pnlGiaoDienCapNhatKhuyenMai = new PnGiaoDienCapNhatKhuyenMai();
		pnlMain.add(pnlGiaoDienCapNhatKhuyenMai, BorderLayout.CENTER);

		// panel chứa giao diện tìm kiếm khuyến mãi
		pnlGiaoDienTimKiemKhuyenMai = new PnGiaoDienTimKiemKhuyenMai();
		pnlMain.add(pnlGiaoDienTimKiemKhuyenMai, BorderLayout.CENTER);

		// panel chứa giao diện cập nhật phòng
		pnlGiaoDienCapNhatP = new PnGiaoDienCapNhatPhong();
		pnlMain.add(pnlGiaoDienCapNhatP, BorderLayout.CENTER);

		// panel chứa giao diện cập nhật loại phòng
		pnlGiaoDienCapNhatLP = new PnGiaoDienCapNhatLoaiPhong();
		pnlMain.add(pnlGiaoDienCapNhatLP, BorderLayout.CENTER);

		// panel chứa giao diện tìm kiếm phòng
		pnlGiaoDienTimKiemP = new PnGiaoDienTimKiemPhong();
		pnlMain.add(pnlGiaoDienTimKiemP, BorderLayout.CENTER);

		// panel chứa giao diện đặt phòng
		pnlGiaoDienDP = new PnGiaoDienDatPhong(nvThucThi);
		pnlMain.add(pnlGiaoDienDP, BorderLayout.CENTER);

		// panel chứa giao diện lập hóa đơn
		pnlGiaoDienLapHoaDon = new PnGiaoDienLapHoaDon();
		pnlMain.add(pnlGiaoDienLapHoaDon, BorderLayout.CENTER);

		// panel chứa giao diện tìm kiếm hóa đơn
		pnlGiaoDienTimKiemHoaDon = new PnGiaoDienTimKiemHoaDon();
		pnlMain.add(pnlGiaoDienTimKiemHoaDon, BorderLayout.CENTER);

		// panel chứa giao diện thống kê doanh thu
		pnlGiaoDienThongKeDT = new PnGiaoDienThongKeDoanhThu();
		pnlMain.add(pnlGiaoDienThongKeDT, BorderLayout.CENTER);

		// panel chứa giao diện thống kê khách hàng
		pnlGiaoDienThongKeKH = new PnGiaoDienThongKeKhachHang();
		pnlMain.add(pnlGiaoDienThongKeKH, BorderLayout.CENTER);

		// panel chứa giao diện thông tin cá nhân
		pnlGiaoDienThongTinCaNhan = new PnGiaoDienThongTinCaNhan(nvThucThi);
		pnlMain.add(pnlGiaoDienThongTinCaNhan, BorderLayout.CENTER);

		// panel chứa giao diện đổi mật khẩu
		pnlGiaoDienDoiMatKhau = new PnGiaoDienDoiMatKhau(nvThucThi);
		pnlMain.add(pnlGiaoDienDoiMatKhau, BorderLayout.CENTER);

		// panel chứa trang chủ(giao diện chính)
		pnlGiaoDienTrangChu = new PnGiaoDienTrangChu();
		pnlMain.add(pnlGiaoDienTrangChu, BorderLayout.CENTER);

		this.setJMenuBar(mnu);
		this.add(pnlMain);

	}

//	Để tạo ra hình
	private ImageIcon getIcon(String path, int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon iconEmployee = new ImageIcon(path);
		Image scaledImage = iconEmployee.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		return scaledIcon;
	}

	// Xử lý sự kiện khi người dùng click vào menu item
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mniCapNhatNhanVien) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			pnlMain.add(pnlGiaoDienCapNhatNV, BorderLayout.CENTER);
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("CẬP NHẬT NHÂN VIÊN");
		} else if (e.getSource() == mniTrangChu) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			pnlMain.add(pnlGiaoDienTrangChu = new PnGiaoDienTrangChu(), BorderLayout.CENTER);
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("HỆ THỐNG QUẢN LÍ KARAOKE NICE");
		} else if (e.getSource() == mniCapNhatPhong) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			try {
				pnlMain.add(pnlGiaoDienCapNhatP = new PnGiaoDienCapNhatPhong(), BorderLayout.CENTER);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("CẬP NHẬT PHÒNG");
		} else if (e.getSource() == mniCapNhatKhachHang) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			try {
				pnlMain.add(pnlGiaoDienCapNhatKH = new PnGiaoDienCapNhatKhachHang(), BorderLayout.CENTER);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("CẬP NHẬT KHÁCH HÀNG");
		} else if (e.getSource() == mniCapNhatDichVu) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			try {
				pnlMain.add(pnlGiaoDienCapNhatDV = new PnGiaoDienCapNhatDichVu(), BorderLayout.CENTER);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("CẬP NHẬT DỊCH VỤ");
		} else if (e.getSource() == mniTimKiemNV) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			try {
				pnlMain.add(pnlGiaoDienTimKiemNV = new PnGiaoDienTimKiemNhanVien(), BorderLayout.CENTER);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("TÌM KIẾM NHÂN VIÊN");
		} else if (e.getSource() == mniTimKiemKhachHang) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			try {
				pnlMain.add(pnlGiaoDienTimKiemKH = new PnGiaoDienTimKiemKhachHang(), BorderLayout.CENTER);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("TÌM KIẾM KHÁCH HÀNG");
		} else if (e.getSource() == mniTimKiemDichVu) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			try {
				pnlMain.add(pnlGiaoDienTimKiemDV = new PnGiaoDienTimKiemDichVu(), BorderLayout.CENTER);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("TÌM KIẾM DỊCH VỤ");
		} else if (e.getSource() == mniTaiKhoan) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			try {
				pnlMain.add(pnlGiaoDienTaiKhoan = new PnGiaoDienTaiKhoan(null), BorderLayout.CENTER);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("TÀI KHOẢN");
		} else if (e.getSource() == mniLoaiDichVu) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			pnlMain.add(pnlGiaoDienLoaiDV, BorderLayout.CENTER);
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("LOẠI DỊCH VỤ");
		} else if (e.getSource() == mniTimKiemPhong) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			try {
				pnlMain.add(pnlGiaoDienTimKiemP = new PnGiaoDienTimKiemPhong(), BorderLayout.CENTER);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("TÌM KIẾM PHÒNG");
		} else if (e.getSource() == mniCapNhatLoaiPhong) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			pnlMain.add(pnlGiaoDienCapNhatLP, BorderLayout.CENTER);
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("CẬP NHẬT LOẠI PHÒNG");
		} else if (e.getSource() == mniDatPhong) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			try {
				pnlMain.add(pnlGiaoDienDP = new PnGiaoDienDatPhong(nvThucThi), BorderLayout.CENTER);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("ĐẶT PHÒNG");
		} else if (e.getSource() == mniTimKiemHoaDon) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			try {
				pnlMain.add(pnlGiaoDienLapHoaDon = new PnGiaoDienTimKiemHoaDon(), BorderLayout.CENTER);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("TÌM KIẾM HÓA ĐƠN");
		} else if (e.getSource() == mniLapHoaDon) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			try {
				pnlMain.add(pnlGiaoDienTimKiemHoaDon = new PnGiaoDienLapHoaDon(), BorderLayout.CENTER);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("LẬP HÓA ĐƠN");
		} else if (e.getSource() == mniThongKeDoanhThu) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			pnlMain.add(pnlGiaoDienThongKeDT = new PnGiaoDienThongKeDoanhThu(), BorderLayout.CENTER);
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("THỐNG KÊ DOANH THU");
		} else if (e.getSource() == mniThongKeKhachHang) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.add(pnlGiaoDienThongKeKH = new PnGiaoDienThongKeKhachHang(), BorderLayout.CENTER);
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("THỐNG KÊ KHÁCH HÀNG");
		} else if (e.getSource() == mniCapNhatKhuyenMai) {
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			try {
				pnlMain.add(pnlGiaoDienCapNhatKhuyenMai = new PnGiaoDienCapNhatKhuyenMai(), BorderLayout.CENTER);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("CẬP NHẬT KHUYẾN MÃI");
		} else if (e.getSource() == mniTimKiemKhuyenMai) {
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			try {
				pnlMain.add(pnlGiaoDienTimKiemKhuyenMai = new PnGiaoDienTimKiemKhuyenMai(), BorderLayout.CENTER);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("TÌM KIẾM KHUYẾN MÃI");
		} else if (e.getSource() == mniThongTinCaNhan) {
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienDoiMatKhau);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			pnlMain.add(pnlGiaoDienThongTinCaNhan = new PnGiaoDienThongTinCaNhan(nvThucThi), BorderLayout.CENTER);
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("THÔNG TIN CÁ NHÂN");
		} else if (e.getSource() == mniDoiMatKhau) {
			pnlMain.remove(pnlGiaoDienCapNhatKhuyenMai);
			pnlMain.remove(pnlGiaoDienThongTinCaNhan);
			pnlMain.remove(pnlGiaoDienTimKiemKhuyenMai);
			pnlMain.remove(pnlGiaoDienCapNhatDV);
			pnlMain.remove(pnlGiaoDienTaiKhoan);
			pnlMain.remove(pnlGiaoDienCapNhatKH);
			pnlMain.remove(pnlGiaoDienTimKiemKH);
			pnlMain.remove(pnlGiaoDienCapNhatP);
			pnlMain.remove(pnlGiaoDienTimKiemNV);
			pnlMain.remove(pnlGiaoDienTimKiemDV);
			pnlMain.remove(pnlGiaoDienTrangChu);
			pnlMain.remove(pnlGiaoDienCapNhatNV);
			pnlMain.remove(pnlGiaoDienLoaiDV);
			pnlMain.remove(pnlGiaoDienCapNhatLP);
			pnlMain.remove(pnlGiaoDienDP);
			pnlMain.remove(pnlGiaoDienLapHoaDon);
			pnlMain.remove(pnlGiaoDienTimKiemHoaDon);
			pnlMain.remove(pnlGiaoDienThongKeDT);
			pnlMain.remove(pnlGiaoDienThongKeKH);
			pnlMain.remove(pnlGiaoDienTimKiemP);
			try {
				pnlMain.add(pnlGiaoDienDoiMatKhau = new PnGiaoDienDoiMatKhau(nvThucThi), BorderLayout.CENTER);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			pnlMain.revalidate();
			pnlMain.repaint();
			lblTitle.setText("ĐỔI MẬT KHẨU");
		} else if (e.getSource().equals(mniThoat)) {
			System.exit(0);
		} else if (e.getSource().equals(mniDangXuat)) {
			FrmDangNhap frmDN = null;
			try {
				frmDN = new FrmDangNhap();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frmDN.setVisible(true);
			setVisible(false);
		} else if (e.getSource().equals(mniDangXuatTK)) {
			FrmDangNhap frmDN = null;
			try {
				frmDN = new FrmDangNhap();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			frmDN.setVisible(true);
			setVisible(false);
		}
	}

	public void chuyenSangTrangTaiKhoan(String maNV) throws RemoteException, NotBoundException {
		pnlMain.remove(pnlGiaoDienCapNhatNV);
		pnlMain.add(pnlGiaoDienTaiKhoan = new PnGiaoDienTaiKhoan(maNV), BorderLayout.CENTER);
		pnlMain.revalidate();
		pnlMain.repaint();
		lblTitle.setText("TÀI KHOẢN");
	}
}
