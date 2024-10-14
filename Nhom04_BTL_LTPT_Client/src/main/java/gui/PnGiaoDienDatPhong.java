package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import dao.ChiTietPhieuDatPhong_DAO;
import dao.LoaiPhong_DAO;
import dao.PhieuDatPhong_DAO;
import dao.Phong_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.Phong;

public class PnGiaoDienDatPhong extends JPanel implements ActionListener {
	private JLabel lblTenPhong, lblSoNguoi, lblLoaiPhong, lblTinhTrang, lblPhongTrong, lblPhongCho, lblPhongDangSuDung,
			lblPhongDangSuDungVaCho, lblPhongDangBaoTri;
	private JTextField txtTenPhong;
	private JComboBox cmbSoNguoi, cmbLoaiPhong, cmbTinhTrang;
	private JButton btnTimKiem, btnXoaTrang, btnDatPhongNgay, btnDatPhongCho, btnNhanPhong, btnHuyPhongCho, btnXemTT,
			btnChuyenPhong, btnDatDichVu;
	private JPanel pnlNorth, pnlSouth, pnlPhong;
	private Phong_DAO p_dao;
	private LoaiPhong_DAO lp_dao;
	private Map<JPanel, String> danhSachPhongDuocChon;
	private Map<Integer, JPanel> dspht_gui;
	private ArrayList<Phong> dspht;
	private NhanVien nvThucThi;
	private PhieuDatPhong_DAO pdp_dao;

	public PnGiaoDienDatPhong(NhanVien nv) throws RemoteException, NotBoundException {
		// TODO Auto-generated constructor stub
		Registry registry = LocateRegistry.getRegistry(2024);
		nvThucThi = nv;
		pdp_dao = (PhieuDatPhong_DAO) registry.lookup("pdp_dao");
		p_dao = (Phong_DAO) registry.lookup("p_dao");
		lp_dao = (LoaiPhong_DAO) registry.lookup("lp_dao");
		dspht = new ArrayList<>();
		danhSachPhongDuocChon = new HashMap<>();
		dspht_gui = new HashMap<>();
		pnlNorth = new JPanel();
		pnlNorth.setLayout(new BorderLayout());
		JPanel pnlButton = new JPanel();
		pnlButton.setBorder(new EmptyBorder(0, 0, 0, 10));
		JPanel pnlButtonBackGround = new JPanel();
		pnlButtonBackGround.setBorder(new EmptyBorder(10, 10, 10, 10));
		pnlButtonBackGround.setBackground(Color.white);
		pnlButtonBackGround.setLayout(new BoxLayout(pnlButtonBackGround, BoxLayout.Y_AXIS));
		pnlButton.add(pnlButtonBackGround);

//		Đặt phòng
		btnDatPhongNgay = new JButton("Đặt phòng ngay");
		btnDatPhongNgay.setMaximumSize(new Dimension(300, 120));
		btnDatPhongNgay.setPreferredSize(new Dimension(200, 40));
		pnlButtonBackGround.add(btnDatPhongNgay);
		pnlButtonBackGround.add(Box.createVerticalStrut(30));

//		Đặt phòng chờ
		btnDatPhongCho = new JButton("Đặt phòng chờ");
		btnDatPhongCho.setMaximumSize(new Dimension(300, 120));
		btnDatPhongCho.setPreferredSize(new Dimension(100, 40));
		pnlButtonBackGround.add(btnDatPhongCho);
		pnlButtonBackGround.add(Box.createVerticalStrut(30));

//		Nhận phòng chờ
		btnNhanPhong = new JButton("Nhận phòng chờ");
		btnNhanPhong.setMaximumSize(new Dimension(300, 120));
		btnNhanPhong.setPreferredSize(new Dimension(100, 40));
		pnlButtonBackGround.add(btnNhanPhong);
		pnlButtonBackGround.add(Box.createVerticalStrut(30));

//		Hủy phòng chờ
		btnHuyPhongCho = new JButton("Hủy phòng chờ");
		btnHuyPhongCho.setMaximumSize(new Dimension(300, 120));
		btnHuyPhongCho.setPreferredSize(new Dimension(100, 40));
		pnlButtonBackGround.add(btnHuyPhongCho);
		pnlButtonBackGround.add(Box.createVerticalStrut(30));

//		Chuyển phòng
		btnChuyenPhong = new JButton("Chuyển phòng");
		btnChuyenPhong.setMaximumSize(new Dimension(300, 120));
		btnChuyenPhong.setPreferredSize(new Dimension(100, 40));
		pnlButtonBackGround.add(btnChuyenPhong);
		pnlButtonBackGround.add(Box.createVerticalStrut(30));

//		Xem thông tin
		btnXemTT = new JButton("Xem thông tin phòng");
		btnXemTT.setMaximumSize(new Dimension(300, 120));
		btnXemTT.setPreferredSize(new Dimension(100, 40));
		pnlButtonBackGround.add(btnXemTT);
		pnlButtonBackGround.add(Box.createVerticalStrut(30));

//		Đặt dịch vụ
		btnDatDichVu = new JButton("Đặt dịch vụ");
		btnDatDichVu.setMaximumSize(new Dimension(300, 120));
		btnDatDichVu.setPreferredSize(new Dimension(100, 40));
		pnlButtonBackGround.add(btnDatDichVu);

		pnlNorth.add(pnlButton, BorderLayout.WEST);

//		Panel chứa ô nhập và table phòng
		JPanel pnlCenTer = new JPanel();
		pnlCenTer.setBorder(new EmptyBorder(5, 0, 5, 0));
		pnlCenTer.setLayout(new BorderLayout());
		pnlNorth.add(pnlCenTer, BorderLayout.CENTER);
		JPanel pnlNhapTT = new JPanel();
		pnlNhapTT.setBorder(new EmptyBorder(5, 10, 5, 10));
		pnlNhapTT.setBackground(Color.white);
		pnlNhapTT.setLayout(new BoxLayout(pnlNhapTT, BoxLayout.Y_AXIS));
		Box b1 = Box.createHorizontalBox();
		lblTenPhong = new JLabel("Tên phòng:");
		txtTenPhong = new JTextField();
		txtTenPhong.setPreferredSize(new Dimension(20, 20));
		lblSoNguoi = new JLabel("Số người:");
		cmbSoNguoi = new JComboBox<>();
		cmbSoNguoi.addItem("Tất cả");
		ArrayList<LoaiPhong> dslp = lp_dao.getAlltbLoaiPhong();
		for (LoaiPhong loaiPhong : dslp) {
			cmbSoNguoi.addItem(loaiPhong.getSoLuongNguoi());
		}
		btnTimKiem = new JButton("Tìm kiếm", getIcon("data/images/icon_btnTK.png", 16, 16));
		cmbSoNguoi.setPreferredSize(new Dimension(200, 20));
		b1.add(lblTenPhong);
		b1.add(Box.createHorizontalStrut(6));
		b1.add(txtTenPhong);
		b1.add(Box.createHorizontalStrut(185));
		b1.add(lblSoNguoi);
		b1.add(Box.createHorizontalStrut(20));
		b1.add(cmbSoNguoi);
		b1.add(Box.createHorizontalStrut(169));
		b1.add(btnTimKiem);
		pnlNhapTT.add(b1);
		pnlNhapTT.add(Box.createVerticalStrut(10));

		Box b2 = Box.createHorizontalBox();
		lblTinhTrang = new JLabel("Tình trạng:");
		cmbTinhTrang = new JComboBox<>();
		cmbTinhTrang.addItem("Tất cả");
		cmbTinhTrang.addItem("Phòng trống");
		cmbTinhTrang.addItem("Phòng đang sử dụng");
		cmbTinhTrang.addItem("Phòng chờ");
		cmbTinhTrang.addItem("Phòng đang bảo trì");
		cmbTinhTrang.addItem("Phòng đang sử dụng và chờ");
		lblLoaiPhong = new JLabel("Loại phòng:");
		cmbLoaiPhong = new JComboBox<>();
		cmbLoaiPhong.addItem("Tất cả");
		for (LoaiPhong loaiPhong : dslp) {
			cmbLoaiPhong.addItem(loaiPhong.getTenLP());
		}
		btnXoaTrang = new JButton("Xóa trắng", getIcon("data/images/icon_btnXoaTrang.png", 16, 16));
		b2.add(lblTinhTrang);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(cmbTinhTrang);
		b2.add(Box.createHorizontalStrut(100));
		b2.add(lblLoaiPhong);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(cmbLoaiPhong);
		b2.add(Box.createHorizontalStrut(168));
		b2.add(btnXoaTrang);
		pnlNhapTT.add(b2);
		pnlCenTer.add(pnlNhapTT, BorderLayout.NORTH);

//		Chứa danh sách phòng
		JPanel pnlTablePhong = new JPanel();
		pnlTablePhong.setBackground(Color.white);
//		pnlTablePhong.setPreferredSize(new Dimension(100, 400));
//		pnlTablePhong.setBorder(new EmptyBorder(10, 0, 10, 0));
		JScrollPane sp = new JScrollPane(pnlTablePhong, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//		Panel chứa phòng
		pnlPhong = new JPanel();
		pnlPhong.setBackground(Color.white);
		pnlPhong.setLayout(new GridLayout(0, 7, 0, 0));

		pnlTablePhong.add(pnlPhong);
		sp.setPreferredSize(new Dimension(100, 400));
		pnlCenTer.add(sp, BorderLayout.SOUTH);

//		Panel chú thích
		pnlSouth = new JPanel();
		pnlSouth.setBackground(Color.white);
		pnlSouth.setBorder(new EmptyBorder(15, 20, 10, 20));
		pnlSouth.setLayout(new BoxLayout(pnlSouth, BoxLayout.X_AXIS));
		JLabel lblIconPhongTrong = new JLabel(getIcon("data/images/phongTrong.png", 20, 20));
		JLabel lblIconPhongCho = new JLabel(getIcon("data/images/phongCho.png", 20, 20));
		JLabel lblIconPhongDaSuDung = new JLabel(getIcon("data/images/phongDaSuDung.png", 20, 20));
		JLabel lblIconPhongDaSuDungVaCho = new JLabel(getIcon("data/images/phongDaSuDungVaCho.png", 20, 20));
		JLabel lblIconPhongDangBaoTri = new JLabel(getIcon("data/images/phongDangBaoTri.png", 20, 20));
		lblPhongTrong = new JLabel(" :Phòng trống");
		lblPhongCho = new JLabel(" :Phòng chờ");
		lblPhongDangSuDung = new JLabel(" :Phòng đang sử dụng");
		lblPhongDangSuDungVaCho = new JLabel(" :Phòng đang sử dụng và chờ");
		lblPhongDangBaoTri = new JLabel(" :Phòng đang bảo trì");
		pnlSouth.add(lblIconPhongTrong);
		pnlSouth.add(lblPhongTrong);
		pnlSouth.add(Box.createHorizontalStrut(30));
		pnlSouth.add(lblIconPhongCho);
		pnlSouth.add(lblPhongCho);
		pnlSouth.add(Box.createHorizontalStrut(30));
		pnlSouth.add(lblIconPhongDaSuDung);
		pnlSouth.add(lblPhongDangSuDung);
		pnlSouth.add(Box.createHorizontalStrut(30));
		pnlSouth.add(lblIconPhongDaSuDungVaCho);
		pnlSouth.add(lblPhongDangSuDungVaCho);
		pnlSouth.add(Box.createHorizontalStrut(30));
		pnlSouth.add(lblIconPhongDangBaoTri);
		pnlSouth.add(lblPhongDangBaoTri);

		voHieuHoaBtn();

		btnTimKiem.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnDatPhongNgay.addActionListener(this);
		btnDatPhongCho.addActionListener(this);
		btnNhanPhong.addActionListener(this);
		btnHuyPhongCho.addActionListener(this);
		btnXemTT.addActionListener(this);
		btnChuyenPhong.addActionListener(this);
		btnDatDichVu.addActionListener(this);
		docDuLieuVaoBang(p_dao.getAlltbPhong());

		this.setBorder(new EmptyBorder(10, 10, 10, 10));
		this.setLayout(new BorderLayout());
		this.add(pnlNorth, BorderLayout.NORTH);
		this.add(pnlSouth, BorderLayout.SOUTH);
		this.setVisible(true);
	}

	private ImageIcon getIcon(String path, int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon iconEmployee = new ImageIcon(path);
		Image scaledImage = iconEmployee.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		return scaledIcon;
	}

	private JPanel createBoxPhong(String pathImage, final String tenPhong) {
		final JPanel o = new JPanel();
		o.setLayout(new BoxLayout(o, BoxLayout.Y_AXIS));
		o.setBorder(new EmptyBorder(20, 20, 20, 20));
		JLabel lblHinhPhong = new JLabel(getIcon(pathImage, 100, 100));
		JLabel lblTen = new JLabel("        " + tenPhong);
		o.add(lblHinhPhong);
		o.add(lblTen);
		o.setOpaque(true);
		o.setBackground(Color.white);
		o.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (o.getBackground().equals(Color.white)) {
					o.setBackground(new Color(0xb8cfe5));
					JLabel lblTenP = (JLabel) o.getComponent(1);
					String tenPhong = lblTenP.getText().trim();
					if (danhSachPhongDuocChon.isEmpty()) {
						danhSachPhongDuocChon.put(o, tenPhong);
						try {
							dspht.add(p_dao.getPhongTheoTen(tenPhong));
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							if (p_dao.getPhongTheoTen(tenPhong).getTinhTrang().equalsIgnoreCase("phòng trống")) {
								btnDatPhongNgay.setEnabled(true);
								btnDatPhongCho.setEnabled(true);
								btnXemTT.setEnabled(true);
							} else if (p_dao.getPhongTheoTen(tenPhong).getTinhTrang()
									.equalsIgnoreCase("phòng đang sử dụng")) {
								btnDatPhongCho.setEnabled(true);
								btnChuyenPhong.setEnabled(true);
								btnXemTT.setEnabled(true);
								btnDatDichVu.setEnabled(true);
							} else if (p_dao.getPhongTheoTen(tenPhong).getTinhTrang()
									.equalsIgnoreCase("phòng đang bảo trì")) {
								voHieuHoaBtn();
							} else if (p_dao.getPhongTheoTen(tenPhong).getTinhTrang().equalsIgnoreCase("phòng chờ")) {
								btnDatPhongNgay.setEnabled(true);
								btnNhanPhong.setEnabled(true);
								btnHuyPhongCho.setEnabled(true);
								btnXemTT.setEnabled(true);
								btnDatPhongCho.setEnabled(true);
							} else {
								btnDatPhongCho.setEnabled(true);
								btnChuyenPhong.setEnabled(true);
								btnXemTT.setEnabled(true);
								btnDatDichVu.setEnabled(true);
								btnHuyPhongCho.setEnabled(true);
							}
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						for (String j : danhSachPhongDuocChon.values()) {
							try {
								if (!p_dao.getPhongTheoTen(j).getTinhTrang()
										.equalsIgnoreCase(p_dao.getPhongTheoTen(tenPhong).getTinhTrang())) {
									o.setBackground(Color.white);
									JOptionPane.showMessageDialog(null,
											"Không được chọn chung những phòng khác tình trạng");
									break;
								} else {
									o.setBackground(new Color(0xb8cfe5));
									danhSachPhongDuocChon.put(o, tenPhong);
									dspht.add(p_dao.getPhongTheoTen(tenPhong));
									break;
								}
							} catch (HeadlessException | RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}

				} else {
					o.setBackground(Color.white);
					for (Phong phong : dspht) {
						if (phong.getTenPhong().equals(tenPhong)) {
							dspht.remove(phong);
							break;
						}
					}
					danhSachPhongDuocChon.remove(o);
					if (danhSachPhongDuocChon.isEmpty()) {
						voHieuHoaBtn();
						dspht.clear();
					}
				}

			}
		});
		return o;
	}

	public void docDuLieuVaoBang(ArrayList<Phong> ds) {
		for (int i = 0; i < ds.size(); i++) {
			Phong phong = ds.get(i);
			JPanel boxPhong = null;

			if (phong.getTinhTrang().equalsIgnoreCase("phòng trống")) {
				boxPhong = createBoxPhong("data/images/phongTrong.png", phong.getTenPhong());
			} else if (phong.getTinhTrang().equalsIgnoreCase("phòng chờ")) {
				boxPhong = createBoxPhong("data/images/phongCho.png", phong.getTenPhong());
			} else if (phong.getTinhTrang().equalsIgnoreCase("phòng đang sử dụng")) {
				boxPhong = createBoxPhong("data/images/phongDaSuDung.png", phong.getTenPhong());
			} else if (phong.getTinhTrang().equalsIgnoreCase("phòng đang bảo trì")) {
				boxPhong = createBoxPhong("data/images/phongDangBaoTri.png", phong.getTenPhong());
			} else if (phong.getTinhTrang().equalsIgnoreCase("phòng đang sử dụng và chờ")) {
				boxPhong = createBoxPhong("data/images/phongDaSuDungVaCho.png", phong.getTenPhong());
			}

			if (boxPhong != null) {
				pnlPhong.add(boxPhong);
				dspht_gui.put(i, boxPhong);
			}
		}
		pnlPhong.revalidate();
		pnlPhong.repaint();
	}

//	Xóa hết phòng trong gui
	public void xoaHetPhong() {
		for (int i = 0; i < dspht_gui.size(); i++) {
			pnlPhong.remove(dspht_gui.get(i));
		}
		dspht_gui.clear();
		danhSachPhongDuocChon.clear();
		dspht.clear();
		pnlPhong.revalidate();
		pnlPhong.repaint();
		voHieuHoaBtn();
	}

	private void voHieuHoaBtn() {
		btnDatPhongNgay.setEnabled(false);
		btnDatPhongCho.setEnabled(false);
		btnNhanPhong.setEnabled(false);
		btnHuyPhongCho.setEnabled(false);
		btnXemTT.setEnabled(false);
		btnChuyenPhong.setEnabled(false);
		btnDatDichVu.setEnabled(false);
	}

	public void capNhatPhongThanhPhongDaSuDung(boolean daDat, Map<JPanel, String> danhSachPhongDuocChon) throws RemoteException {
		xoaHetPhong();
		docDuLieuVaoBang(p_dao.getAlltbPhong());
		danhSachPhongDuocChon.clear();
		dspht.clear();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnXoaTrang)) {
			txtTenPhong.setText("");
			cmbLoaiPhong.setSelectedIndex(0);
			cmbTinhTrang.setSelectedIndex(0);
			cmbSoNguoi.setSelectedIndex(0);
			txtTenPhong.requestFocus();
			xoaHetPhong();
			try {
				docDuLieuVaoBang(p_dao.getAlltbPhong());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(btnTimKiem)) {
			String ten = txtTenPhong.getText().trim();
			if (ten.isEmpty()) {
				ten = null;
			}
			String tenLoaiPhong = cmbLoaiPhong.getSelectedItem().toString();
			if (tenLoaiPhong.equalsIgnoreCase("tất cả"))
				tenLoaiPhong = null;
			String tinhTrang = cmbTinhTrang.getSelectedItem().toString();
			if (tinhTrang.equalsIgnoreCase("tất cả"))
				tinhTrang = null;
			int soNguoi = 0;
			if (cmbSoNguoi.getSelectedItem().toString().equalsIgnoreCase("tất cả"))
				soNguoi = 0;
			else
				soNguoi = Integer.parseInt(cmbSoNguoi.getSelectedItem().toString());
			ArrayList<Phong> ds = null;
			try {
				ds = p_dao.timKiemPhongOGiaoDienDP(ten, tinhTrang, tenLoaiPhong, soNguoi);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			xoaHetPhong();
			docDuLieuVaoBang(ds);
		} else if (e.getSource().equals(btnDatPhongNgay)) {
			try {
				new FrmGiaoDienDatPhongNgay(dspht, nvThucThi, danhSachPhongDuocChon, this).setVisible(true);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(btnDatDichVu)) {
//			Đặt dịch vụ chỉ cho 1 phòng đặt duy nhất
			if (dspht.size() == 1)
				try {
					new FrmGiaoDienDatDichVu(dspht).setVisible(true);
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			else
				JOptionPane.showMessageDialog(null, "Hãy chọn 1 phòng duy nhất để đặt dịch vụ");
		} else if (e.getSource().equals(btnDatPhongCho)) {
			try {
				new FrmGiaoDienDatPhongCho(dspht, nvThucThi, danhSachPhongDuocChon, this).setVisible(true);
			} catch (RemoteException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(btnHuyPhongCho)) {
			for (int i = 0; i < dspht.size(); i++) {
				try {
					pdp_dao.deleteTheoMaPhieuDatPhong(pdp_dao.getPhieuDatPhongTheoMaPhongVaTinhTrang(dspht.get(i).getMaPhong(),"Chờ xác nhận").getMaPDP());
				} catch (RemoteException e3) {
					// TODO Auto-generated catch block
					e3.printStackTrace();
				}
				if(dspht.get(i).getTinhTrang().equalsIgnoreCase("Phòng chờ"))
					try {
						p_dao.capNhatTrangThaiPhong(dspht.get(i).getMaPhong(), "Phòng trống");
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				else
					try {
						p_dao.capNhatTrangThaiPhong(dspht.get(i).getMaPhong(), "Phòng đang sử dụng");
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
			JOptionPane.showMessageDialog(null, "Đã hủy thành công");
			xoaHetPhong();
			try {
				docDuLieuVaoBang(p_dao.getAlltbPhong());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(btnNhanPhong)) {
			if(dspht.get(0).getTinhTrang().equalsIgnoreCase("Phòng chờ"))
				try {
					new FrmGiaoDienNhanPhongCho(dspht, nvThucThi, danhSachPhongDuocChon, this).setVisible(true);
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			else
				JOptionPane.showMessageDialog(null, "Chỉ áp dụng với phòng chờ");
		} else if (e.getSource().equals(btnChuyenPhong)) {
			if (dspht.size() > 1) {
				JOptionPane.showMessageDialog(null, "Hãy chọn duy nhất 1 phòng để chuyển");
			} else {
				try {
					new FrmGiaoDienChuyenPhong(dspht, nvThucThi, danhSachPhongDuocChon, this).setVisible(true);
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (e.getSource().equals(btnXemTT)) {
			if (dspht.size() > 1) {
				JOptionPane.showMessageDialog(null, "Hãy chọn duy nhất 1 phòng để xem");
			} else
				try {
					new FrmGiaoDienXemThongTinPhong(dspht).setVisible(true);
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}
}
