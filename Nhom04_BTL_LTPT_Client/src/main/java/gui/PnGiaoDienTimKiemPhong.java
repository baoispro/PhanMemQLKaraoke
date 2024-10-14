package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;

import dao.LoaiPhong_DAO;
import dao.Phong_DAO;
import entity.LoaiPhong;
import entity.Phong;

public class PnGiaoDienTimKiemPhong extends JPanel implements ActionListener {
	private JLabel lblMaPhong, lblTenPhong, lblLoaiPhong, lblTinhTrang;
	private JTextField txtMaPhong, txtTenPhong;
	private JComboBox cmbLP, cmbTinhTrang;
	private JButton btnTimKiem, btnXoaTrang;
	private JPanel pnlNorthBackGround;
	private JTable tblTK;
	private DefaultTableModel model;
	private LoaiPhong_DAO lp_dao;
	private Phong_DAO p_dao;

	public PnGiaoDienTimKiemPhong() throws RemoteException, NotBoundException {
		// TODO Auto-generated constructor stub
		Registry registry = LocateRegistry.getRegistry(2024);
		lp_dao = (LoaiPhong_DAO) registry.lookup("lp_dao");
		p_dao = (Phong_DAO) registry.lookup("p_dao");
		this.setLayout(new BorderLayout());
		Border border = BorderFactory.createEmptyBorder(10, 10, 10, 5);
		Border border1 = BorderFactory.createEmptyBorder(5, 5, 10, 10);
		JPanel pnlWest = new JPanel();
		pnlWest.setLayout(new BoxLayout(pnlWest, BoxLayout.Y_AXIS));
		pnlWest.setBorder(border);
		JPanel pnlWestBackGround = new JPanel();
		pnlWestBackGround.setBackground(Color.white);
		// Kích thước là 20% của 1251 và có chiều cao là 539
		pnlWestBackGround.setPreferredSize(new Dimension(250, 539));
		pnlWest.add(pnlWestBackGround);
		pnlWestBackGround.setLayout(new BoxLayout(pnlWestBackGround, BoxLayout.Y_AXIS));
		pnlWestBackGround.add(Box.createVerticalStrut(10));
//		Mã phòng
		lblMaPhong = new JLabel("Mã phòng:");
		lblMaPhong.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtMaPhong = new JTextField();
		Box b = Box.createVerticalBox();
		Box c = Box.createHorizontalBox();
		b.add(lblMaPhong);
		c.add(Box.createHorizontalStrut(10));
		c.add(txtMaPhong);
		c.add(Box.createHorizontalStrut(10));
		b.add(Box.createVerticalStrut(10));
		b.add(c);
		b.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b);
//		Tên phòng
		lblTenPhong = new JLabel("Tên phòng:");
		lblTenPhong.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtTenPhong = new JTextField();
		Box b1 = Box.createVerticalBox();
		Box c1 = Box.createHorizontalBox();
		b1.add(lblTenPhong);
		c1.add(Box.createHorizontalStrut(10));
		c1.add(txtTenPhong);
		c1.add(Box.createHorizontalStrut(10));
		b1.add(Box.createVerticalStrut(10));
		b1.add(c1);
		b1.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b1);
//		Loại phòng
		lblLoaiPhong = new JLabel("Loại phòng:");
		cmbLP = new JComboBox<>();
		cmbLP.addItem("Tất cả");
		ArrayList<LoaiPhong> dslp = lp_dao.getAlltbLoaiPhong();
		for (LoaiPhong loaiPhong : dslp) {
			cmbLP.addItem(loaiPhong.getTenLP());
		}
		Box b2 = Box.createHorizontalBox();
		b2.add(Box.createHorizontalStrut(10));
		b2.add(lblLoaiPhong);
		b2.add(Box.createHorizontalStrut(19));
		b2.add(cmbLP);
		b2.add(Box.createHorizontalStrut(10));
		pnlWestBackGround.add(b2);
		pnlWestBackGround.add(Box.createVerticalStrut(10));
//		Tình trạng
		lblTinhTrang = new JLabel("Tình trạng:");
		cmbTinhTrang = new JComboBox<>();
		cmbTinhTrang.addItem("Tất cả");
		cmbTinhTrang.addItem("Phòng trống");
		cmbTinhTrang.addItem("Phòng đang sử dụng");
		cmbTinhTrang.addItem("Phòng chờ");
		Box b3 = Box.createHorizontalBox();
		b3.add(Box.createHorizontalStrut(10));
		b3.add(lblTinhTrang);
		b3.add(Box.createHorizontalStrut(25));
		b3.add(cmbTinhTrang);
		b3.add(Box.createHorizontalStrut(10));
		pnlWestBackGround.add(b3);
		pnlWestBackGround.add(Box.createVerticalStrut(350));

//		Phần bảng và nút
		JPanel pnlCenter = new JPanel();
		pnlCenter.setBorder(border1);
		pnlCenter.setLayout(new BorderLayout());
		pnlNorthBackGround = new JPanel();
		JPanel pnlSouthBackGround = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(30);
		flowLayout.setVgap(44);
		pnlSouthBackGround.setLayout(flowLayout);
		pnlSouthBackGround.setBackground(Color.white);
		pnlSouthBackGround.setPreferredSize(new Dimension(1001, 108));
		btnTimKiem = new JButton("Tìm kiếm", getIcon("data/images/icon_btnTK.png", 16, 16));
		btnXoaTrang = new JButton("Xóa trắng", getIcon("data/images/icon_btnXoaTrang.png", 16, 16));
		this.setVisible(true);

		pnlSouthBackGround.add(btnTimKiem);
		pnlSouthBackGround.add(btnXoaTrang);
		btnTimKiem.addActionListener(this);
		btnXoaTrang.addActionListener(this);

		pnlCenter.add(pnlNorthBackGround, BorderLayout.NORTH);
		pnlCenter.add(pnlSouthBackGround, BorderLayout.SOUTH);
		taoBang();
		docDuLieuDataBaseVaoTable();

		this.add(pnlWest, BorderLayout.WEST);
		this.add(pnlCenter, BorderLayout.CENTER);
	}

	private void taoBang() {
		// TODO Auto-generated method stub
		model = new DefaultTableModel();
		tblTK = new JTable(model);
		model.addColumn("Mã Phòng");
		model.addColumn("Tên Phòng");
		model.addColumn("Loại Phòng");
		model.addColumn("Tình trạng");
		JScrollPane sp = new JScrollPane(tblTK, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		tblTK.setRowHeight(30);
		sp.setPreferredSize(new Dimension(1001, 431));
		pnlNorthBackGround.add(sp);
		tblTK.addMouseListener(new MouseListener() {

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
				int row = tblTK.getSelectedRow();
				txtMaPhong.setText(model.getValueAt(row, 0).toString());
				txtTenPhong.setText(model.getValueAt(row, 1).toString());
				cmbLP.setSelectedItem(model.getValueAt(row, 2));
				cmbTinhTrang.setSelectedItem(model.getValueAt(row, 3));
			}
		});
	}

	private void xoaTrang() throws RemoteException {
		// TODO Auto-generated method stub
		txtMaPhong.setText("");
		txtTenPhong.setText("");
		cmbLP.setSelectedIndex(0);
		cmbTinhTrang.setSelectedIndex(0);
		tblTK.clearSelection();
		txtTenPhong.requestFocus();
		model.setRowCount(0);
		docDuLieuDataBaseVaoTable();
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
		if (e.getSource().equals(btnXoaTrang)) {
			try {
				xoaTrang();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(btnTimKiem)) {
			String maP = txtMaPhong.getText();
			if (maP.isEmpty()) {
				maP = null;
			}
			String tenP = txtTenPhong.getText();
			if (tenP.isEmpty())
				tenP = null;
			String tenLP = cmbLP.getSelectedItem().toString();
			if(tenLP.equalsIgnoreCase("tất cả")) {
				tenLP = null;
			}
			String tinhTrang = cmbTinhTrang.getSelectedItem().toString();
			if(tinhTrang.equalsIgnoreCase("tất cả")) {
				tinhTrang = null;
			}
			ArrayList<Phong> ds = null;
			try {
				ds = p_dao.timKiemPhong(tenP, tinhTrang, tenLP, maP);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (ds.size() == 0) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy");
			} else {
				model.setRowCount(0);
				for (Phong phong : ds) {
					try {
						model.addRow(new Object[] { phong.getMaPhong(), phong.getTenPhong(),
								lp_dao.getLoaiPhongTheoMa(phong.getLp().getMaLP()).getTenLP(), phong.getTinhTrang() });
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(this, "Đã tìm kiếm thành công");
			}
		}
	}

	private void docDuLieuDataBaseVaoTable() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<Phong> ds = p_dao.getAlltbPhong();
		for (Phong phong : ds) {
			model.addRow(new Object[] { phong.getMaPhong(), phong.getTenPhong(),
					lp_dao.getLoaiPhongTheoMa(phong.getLp().getMaLP()).getTenLP(), phong.getTinhTrang() });
		}
	}
}
