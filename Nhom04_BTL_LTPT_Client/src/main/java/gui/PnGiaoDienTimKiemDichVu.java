package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import dao.DichVu_DAO;
import dao.LoaiDichVu_DAO;
import entity.DichVu;
import entity.LoaiDichVu;

import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class PnGiaoDienTimKiemDichVu extends JPanel implements ActionListener {
	private JTextField txtTKMaDV, txtTenDV;
	private JComboBox cmbLoaiDV;
	private JButton btnTimKiem, btnLamMoi, btnThemGH;
	private JPanel pnlTimKiemDV, pnlDanhSachDichVu, pnlChiTietDV, pnlDichVu;
	private JLabel lblMaDV, lblTenDichVu, lblLoaiDichVu, lblKhoangGia, lblDanhSachDV, lblImageDV, lblGiaDV, lblSoLuong;
	private JTextField txtGiaBĐ;
	private JTextField txtGiaKT;
	private DichVu_DAO dv_dao;
	private LoaiDichVu_DAO ldv_dao;

	public PnGiaoDienTimKiemDichVu() throws RemoteException, NotBoundException {

		Registry registry = LocateRegistry.getRegistry(2024);
		dv_dao = (DichVu_DAO) registry.lookup("dv_dao");
		ldv_dao = (LoaiDichVu_DAO) registry.lookup("ldv_dao");

		setLayout(null);

		pnlTimKiemDV = new JPanel();
		pnlTimKiemDV.setBounds(10, 10, 1260, 108);
		add(pnlTimKiemDV);
		pnlTimKiemDV.setLayout(null);

		lblMaDV = new JLabel("Mã dịch vụ:");
		lblMaDV.setFont(new Font("Dialog", Font.BOLD, 12));
		lblMaDV.setBounds(89, 10, 100, 30);
		pnlTimKiemDV.add(lblMaDV);

		txtTKMaDV = new JTextField();
		txtTKMaDV.setFont(new Font("Dialog", Font.BOLD, 12));
		txtTKMaDV.setBounds(199, 10, 380, 30);
		pnlTimKiemDV.add(txtTKMaDV);
		txtTKMaDV.setColumns(10);

		lblTenDichVu = new JLabel("Tên dịch vụ:");
		lblTenDichVu.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTenDichVu.setBounds(89, 60, 100, 30);
		pnlTimKiemDV.add(lblTenDichVu);

		txtTenDV = new JTextField();
		txtTenDV.setFont(new Font("Dialog", Font.BOLD, 12));
		txtTenDV.setColumns(10);
		txtTenDV.setBounds(199, 60, 380, 30);
		pnlTimKiemDV.add(txtTenDV);

		lblLoaiDichVu = new JLabel("Loại dịch vụ:");
		lblLoaiDichVu.setFont(new Font("Dialog", Font.BOLD, 12));
		lblLoaiDichVu.setBounds(704, 10, 100, 30);
		pnlTimKiemDV.add(lblLoaiDichVu);

		cmbLoaiDV = new JComboBox();
		cmbLoaiDV.setFont(new Font("Dialog", Font.BOLD, 12));
		cmbLoaiDV.setBounds(805, 10, 242, 30);
		cmbLoaiDV.addItem("Tất cả");
		ArrayList<LoaiDichVu> dsdv = ldv_dao.getAlltbLoaiDichVu();
		for (LoaiDichVu loaiDichVu : dsdv) {
			cmbLoaiDV.addItem(loaiDichVu.getTenLoaiDichVu());
		}
		pnlTimKiemDV.add(cmbLoaiDV);

		lblKhoangGia = new JLabel("Giá dịch vụ:");
		lblKhoangGia.setFont(new Font("Dialog", Font.BOLD, 12));
		lblKhoangGia.setBounds(704, 60, 100, 30);
		pnlTimKiemDV.add(lblKhoangGia);

		btnTimKiem = new JButton("Tìm kiếm", getIcon("data/images/icon_btnTK.png", 16, 16));
		btnTimKiem.setFont(new Font("Dialog", Font.BOLD, 12));
		btnTimKiem.setBounds(1083, 10, 120, 30);
		pnlTimKiemDV.add(btnTimKiem);

		btnLamMoi = new JButton("Làm mới", getIcon("data/images/icon_btnXoaTrang.png", 16, 16));
		btnLamMoi.setFont(new Font("Dialog", Font.BOLD, 12));
		btnLamMoi.setBounds(1083, 60, 120, 30);
		pnlTimKiemDV.add(btnLamMoi);

		txtGiaBĐ = new JTextField();
		txtGiaBĐ.setText("0");
		txtGiaBĐ.setFont(new Font("Dialog", Font.BOLD, 12));
		txtGiaBĐ.setBounds(805, 60, 100, 30);
		txtGiaBĐ.setForeground(Color.GRAY);
		txtGiaBĐ.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if (txtGiaBĐ.getText().isEmpty()) {
					txtGiaBĐ.setText("0");
					txtGiaBĐ.setForeground(Color.GRAY); // Đặt màu văn bản khi không nhập
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (txtGiaBĐ.getText().equals("0")) {
					txtGiaBĐ.setText("");
					txtGiaBĐ.setForeground(Color.BLACK); // Đặt màu văn bản khi nhập
				}
			}
		});
		pnlTimKiemDV.add(txtGiaBĐ);
		txtGiaBĐ.setColumns(10);

		txtGiaKT = new JTextField();
		txtGiaKT.setFont(new Font("Dialog", Font.BOLD, 12));
		txtGiaKT.setColumns(10);
		txtGiaKT.setText("5000000");
		txtGiaKT.setBounds(947, 60, 100, 30);
		txtGiaKT.setForeground(Color.GRAY);
		txtGiaKT.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if (txtGiaKT.getText().isEmpty()) {
					txtGiaKT.setText("5000000");
					txtGiaKT.setForeground(Color.GRAY); // Đặt màu văn bản khi không nhập
				}
			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (txtGiaKT.getText().equals("5000000")) {
					txtGiaKT.setText("");
					txtGiaKT.setForeground(Color.BLACK); // Đặt màu văn bản khi nhập
				}
			}
		});

		pnlTimKiemDV.add(txtGiaKT);

		JLabel lblKiTu = new JLabel("~");
		lblKiTu.setHorizontalAlignment(SwingConstants.CENTER);
		lblKiTu.setFont(new Font("Dialog", Font.BOLD, 22));
		lblKiTu.setBounds(908, 69, 39, 14);
		pnlTimKiemDV.add(lblKiTu);

		lblDanhSachDV = new JLabel("Danh Sách Dịch Vụ");
		lblDanhSachDV.setOpaque(true);
		lblDanhSachDV.setHorizontalTextPosition(SwingConstants.CENTER);
		lblDanhSachDV.setHorizontalAlignment(SwingConstants.CENTER);
		lblDanhSachDV.setForeground(Color.WHITE);
		lblDanhSachDV.setFont(new Font("Tahoma", Font.BOLD, 30));
		lblDanhSachDV.setBackground(new Color(72, 209, 204));

		pnlDanhSachDichVu = new JPanel();
		pnlDanhSachDichVu.setBounds(20, 129, 1230, 430);
		add(pnlDanhSachDichVu);
		pnlDanhSachDichVu.setLayout(null);
		pnlDanhSachDichVu.setBorder(new TitledBorder(null, "Danh sách dịch vụ:"));

		pnlChiTietDV = new JPanel();
		pnlChiTietDV.setBackground(Color.WHITE);
		JScrollPane scrollPane = new JScrollPane(pnlChiTietDV);
		scrollPane.setBounds(20, 18, 1200, 400);
		pnlDanhSachDichVu.add(scrollPane);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);

		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setViewportView(pnlChiTietDV);
		pnlChiTietDV.setLayout(new GridLayout(0, 4));
		pnlChiTietDV.removeAll();
		pnlChiTietDV.revalidate();
		pnlChiTietDV.repaint();
		ArrayList<DichVu> dsall = dv_dao.getAlltbDichVu();
		hienThiDanhSachDV(pnlChiTietDV, dsall);
		// TODO Auto-generated constructor stub
		this.setVisible(true);
		btnTimKiem.addActionListener(this);
		btnLamMoi.addActionListener(this);
	}

	public void hienThiDanhSachDV(JPanel dsDV, ArrayList<DichVu> dsDichVu) {
		for (DichVu dvInfo : dsDichVu) {

			pnlDichVu = new JPanel();
			pnlDichVu.setBounds(0, 0, 250, 250);
			pnlDichVu.setBackground(Color.WHITE);

			// Sử dụng GridBagLayout cho pnlDV1
			pnlDichVu.setLayout(new GridBagLayout());
			GridBagConstraints gbc = new GridBagConstraints();

			lblImageDV = new JLabel("");
			lblImageDV.setAlignmentX(Component.CENTER_ALIGNMENT);
			lblImageDV.setIcon(getIcon(dvInfo.getHinhAnh(), 200, 220));

			lblTenDichVu = new JLabel(dvInfo.getTenDichVu());
			lblTenDichVu.setFont(new Font("Dialog", Font.BOLD, 15));

			lblGiaDV = new JLabel("Giá:" + dvInfo.getDonGia());
			lblGiaDV.setFont(new Font("Dialog", Font.BOLD, 14));
			lblSoLuong = new JLabel("SL: " + dvInfo.getSoLuong());
			lblSoLuong.setFont(new Font("Dialog", Font.BOLD, 14));
			btnThemGH = new JButton("Thêm dịch vụ");
			btnThemGH.setForeground(Color.WHITE);
			btnThemGH.setBackground(new Color(0xDA, 0x29, 0x1C));
			btnThemGH.setFont(new Font("Dialog", Font.BOLD, 12));
			btnThemGH.setAlignmentX(Component.CENTER_ALIGNMENT);
			btnThemGH.setBorderPainted(false);
			btnThemGH.setFocusPainted(false);
			btnThemGH.setVisible(false);
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 1;
			gbc.fill = GridBagConstraints.NONE;
			gbc.anchor = GridBagConstraints.CENTER;
			gbc.insets = new Insets(0, 0, 5, 0); // Để cách các dòng nhau ra
			pnlDichVu.add(lblImageDV, gbc);

			gbc.gridy = 1;
			gbc.anchor = GridBagConstraints.WEST;
			pnlDichVu.add(lblTenDichVu, gbc);

			gbc.gridy = 2;
			pnlDichVu.add(lblGiaDV, gbc);

			gbc.gridy = 2;
			gbc.anchor = GridBagConstraints.EAST;
			pnlDichVu.add(lblSoLuong, gbc);

			gbc.gridy = 3;
			gbc.anchor = GridBagConstraints.CENTER;
			pnlDichVu.add(btnThemGH, gbc);

			pnlDichVu.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					JPanel thisPanel = (JPanel) e.getSource();
					// Add a border when hovering
					thisPanel.setBorder(new LineBorder(new Color(100, 100, 100, 100), 1));
					thisPanel.repaint();
				}

				@Override
				public void mouseExited(MouseEvent e) {
					JPanel thisPanel = (JPanel) e.getSource();
					// Loại bỏ đường viền khi không rê chuột
					thisPanel.setBorder(null);
					thisPanel.repaint();
				}
			});

			dsDV.add(pnlDichVu);
		}

	}

	private ImageIcon getIcon(String path, int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon iconEmployee = new ImageIcon(path);
		Image scaledImage = iconEmployee.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		return scaledIcon;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnLamMoi)) {
			try {
				xoaTrang();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(btnTimKiem)) {
			String ma = txtTKMaDV.getText();
			String ten = txtTenDV.getText();
			String loaidv = cmbLoaiDV.getSelectedItem().toString();
			String tienBD = txtGiaBĐ.getText();
			String tienKT = txtGiaKT.getText();
			if (loaidv.equals("Tất cả")) {
				// Tìm kiếm dựa trên mã hoặc tên dịch vụ mà không chọn loại dịch vụ
				if (ma.isEmpty()) {
					ma = null;
				}
				if (ten.isEmpty()) {
					ten = null;
				}
				if (tienBD.isEmpty()) {
					tienBD = null;
				}
				if (tienKT.isEmpty()) {
					tienKT = null;
				}
				try {
					if (dv_dao.timKiemDichVu(ma, ten, tienBD, tienKT, null).isEmpty()) {
						JOptionPane.showMessageDialog(this, "Không tìm thấy");
					} else {
						pnlChiTietDV.removeAll();
						ArrayList<DichVu> dsTK = dv_dao.timKiemDichVu(ma, ten, tienBD, tienKT, null);
						hienThiDanhSachDV(pnlChiTietDV, dsTK);
					}
				} catch (HeadlessException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				// Tìm kiếm dựa trên mã, tên và loại dịch vụ đã chọn
				if (ma.isEmpty()) {
					ma = null;
				}
				if (ten.isEmpty()) {
					ten = null;
				}
				String maLoaiDV = null;
				try {
					maLoaiDV = ldv_dao.getLoaiDichVuTheoTen(loaidv).getMaLoaiDichVu();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					if (dv_dao.timKiemDichVu(ma, ten, tienBD, tienKT, maLoaiDV).isEmpty()) {
						JOptionPane.showMessageDialog(this, "Không tìm thấy");
					} else {
						pnlChiTietDV.removeAll();
						ArrayList<DichVu> dsTK = dv_dao.timKiemDichVu(ma, ten, tienBD, tienKT, maLoaiDV);
						hienThiDanhSachDV(pnlChiTietDV, dsTK);
					}
				} catch (HeadlessException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	private void xoaTrang() throws RemoteException {
		txtTKMaDV.setText("");
		txtTenDV.setText("");
		txtGiaBĐ.setText("0");
		txtGiaKT.setText("5000000");
		txtGiaBĐ.setForeground(Color.GRAY);
		txtGiaKT.setForeground(Color.GRAY);
		cmbLoaiDV.setSelectedIndex(0);
		pnlChiTietDV.removeAll();
		ArrayList<DichVu> dsall = dv_dao.getAlltbDichVu();
		hienThiDanhSachDV(pnlChiTietDV, dsall);
	}
}
