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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dao.KhuyenMai_DAO;
import entity.KhuyenMai;

public class PnGiaoDienTimKiemKhuyenMai extends JPanel implements ActionListener {
	private JLabel lblMa, lblTen, lblGiaTri, lblNgayBatDau, lblNgayKetThuc, lblDieuKienApDung;
	private JTextField txtMa, txtTen, txtGiaTri, txtDKApDung;
	private JButton btnTK, btnXoaTrang;
	private JPanel pnlNorthBackGround;
	private JDatePickerImpl datePicker, datePicker1;
	private JTable tblKM;
	private DefaultTableModel model;
	private KhuyenMai_DAO km_dao;

	public PnGiaoDienTimKiemKhuyenMai() throws RemoteException, NotBoundException {
		// TODO Auto-generated constructor stub
		Registry registry = LocateRegistry.getRegistry(2024);
		km_dao = (KhuyenMai_DAO) registry.lookup("km_dao");
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
//				Mã
		lblMa = new JLabel("Mã khuyến mãi");
		lblMa.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtMa = new JTextField();
		txtMa.setEditable(false);
		Box b = Box.createVerticalBox();
		Box c = Box.createHorizontalBox();
		b.add(lblMa);
		c.add(Box.createHorizontalStrut(10));
		c.add(txtMa);
		c.add(Box.createHorizontalStrut(10));
		b.add(Box.createVerticalStrut(10));
		b.add(c);
		b.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b);
//				Tên
		lblTen = new JLabel("Tên khuyến mãi");
		lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtTen = new JTextField();
		Box b1 = Box.createVerticalBox();
		Box c1 = Box.createHorizontalBox();
		b1.add(lblTen);
		c1.add(Box.createHorizontalStrut(10));
		c1.add(txtTen);
		c1.add(Box.createHorizontalStrut(10));
		b1.add(Box.createVerticalStrut(10));
		b1.add(c1);
		b1.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b1);

//				Ngày bat dau
		lblNgayBatDau = new JLabel("Ngày bắt đầu:");
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		// Don't know about the formatter, but there it is...
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		Box b2 = Box.createHorizontalBox();
		b2.add(Box.createHorizontalStrut(10));
		b2.add(lblNgayBatDau);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(datePicker);
		b2.add(Box.createHorizontalStrut(11));
		pnlWestBackGround.add(b2);
		pnlWestBackGround.add(Box.createVerticalStrut(10));

//				Ngày ket thuc
		lblNgayKetThuc = new JLabel("Ngày kết thúc:");
		UtilDateModel model1 = new UtilDateModel();
		Properties p1 = new Properties();
		p1.put("text.today", "Today");
		p1.put("text.month", "Month");
		p1.put("text.year", "Year");
		JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p1);
		// Don't know about the formatter, but there it is...
		datePicker1 = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
		Box b3 = Box.createHorizontalBox();
		b3.add(Box.createHorizontalStrut(10));
		b3.add(lblNgayKetThuc);
		b3.add(Box.createHorizontalStrut(10));
		b3.add(datePicker1);
		b3.add(Box.createHorizontalStrut(11));
		pnlWestBackGround.add(b3);
		pnlWestBackGround.add(Box.createVerticalStrut(10));
//				DK ap dung

		lblDieuKienApDung = new JLabel("Điều kiện áp dụng");
		lblDieuKienApDung.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtDKApDung = new JTextField();
		Box b4 = Box.createVerticalBox();
		Box c4 = Box.createHorizontalBox();
		b4.add(lblDieuKienApDung);
		c4.add(Box.createHorizontalStrut(10));
		c4.add(txtDKApDung);
		b4.add(Box.createHorizontalStrut(10));
		b4.add(Box.createVerticalStrut(10));
		b4.add(c4);
		b4.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b4);

//				Gia Tri km	
		lblGiaTri = new JLabel("Giá trị khuyến mãi");
		lblGiaTri.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtGiaTri = new JTextField();
		Box b5 = Box.createVerticalBox();
		Box c5 = Box.createHorizontalBox();
		b5.add(lblGiaTri);
		c5.add(Box.createHorizontalStrut(10));
		c5.add(txtGiaTri);
		b5.add(Box.createHorizontalStrut(10));
		b5.add(Box.createVerticalStrut(10));
		b5.add(c5);
		b5.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b5);
		pnlWestBackGround.add(Box.createVerticalStrut(400));

//				Phần bảng và nút
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
		btnTK = new JButton("Thêm", getIcon("data/images/icon_btnTK.png", 16, 16));
		btnXoaTrang = new JButton("Xóa trắng", getIcon("data/images/icon_btnXoaTrang.png", 16, 16));

		pnlSouthBackGround.add(btnTK);
		pnlSouthBackGround.add(btnXoaTrang);
		btnTK.addActionListener(this);
		btnXoaTrang.addActionListener(this);

		pnlCenter.add(pnlNorthBackGround, BorderLayout.NORTH);
		pnlCenter.add(pnlSouthBackGround, BorderLayout.SOUTH);
		taoBang();

		this.add(pnlWest, BorderLayout.WEST);
		this.add(pnlCenter, BorderLayout.CENTER);
		this.setVisible(true);
	}

	private void taoBang() throws RemoteException {
		// TODO Auto-generated method stub
		
		model = new DefaultTableModel();
		tblKM = new JTable(model);
		model.addColumn("Mã khuyến mãi");
		model.addColumn("Tên khuyến mãi");
		model.addColumn("Ngày bắt đầu");
		model.addColumn("Ngày kết thúc");
		model.addColumn("ĐK Áp dụng");
		model.addColumn("Giá trị KM");
		TableColumn column0 = tblKM.getColumnModel().getColumn(0);
		column0.setPreferredWidth(10);
		TableColumn column1 = tblKM.getColumnModel().getColumn(1);
		column1.setPreferredWidth(200);
		TableColumn column2 = tblKM.getColumnModel().getColumn(2);
		column2.setPreferredWidth(5);
		TableColumn column3 = tblKM.getColumnModel().getColumn(3);
		column3.setPreferredWidth(5);
		TableColumn column5 = tblKM.getColumnModel().getColumn(3);
		column5.setPreferredWidth(5);

		JScrollPane sp = new JScrollPane(tblKM, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setPreferredSize(new Dimension(1001, 431));
		tblKM.addMouseListener(new MouseListener() {

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
				int row = tblKM.getSelectedRow();
				txtMa.setText(model.getValueAt(row, 0).toString());
				txtTen.setText(model.getValueAt(row, 1).toString());
				datePicker.getJFormattedTextField().setText(model.getValueAt(row, 2).toString());
				datePicker1.getJFormattedTextField().setText(model.getValueAt(row, 3).toString());
				txtDKApDung.setText(model.getValueAt(row, 4).toString());
				String[] khuyenMaiChuyenDoi = model.getValueAt(row, 5).toString().split("%");
				txtGiaTri.setText(((Float.parseFloat(khuyenMaiChuyenDoi[0]) / 100) + ""));

			}
		});
		pnlNorthBackGround.add(sp);
		docDuLieuDataBaseVaoTable();
	}

	private void xoaTrang() {
		txtMa.setText("");
		txtTen.setText("");
		datePicker.getJFormattedTextField().setText("");
		datePicker1.getJFormattedTextField().setText("");
		txtDKApDung.setText("");
		txtGiaTri.setText("");

	}

	private boolean validData() {
		String ten = txtTen.getText().trim();
		String dieuKienApDung = txtDKApDung.getText().trim();
		String giaTriKm = txtGiaTri.getText();
		String ngayBDBangChu = datePicker.getJFormattedTextField().getText();
		String ngayKTBangChu = datePicker1.getJFormattedTextField().getText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate now = LocalDate.now();
		if (ten.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để tên trống");
			txtTen.requestFocus();
			return false;
		}

		if (dieuKienApDung.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để điều kiện áp dụng trống");
			txtDKApDung.requestFocus();
			return false;
		}
		if (!giaTriKm.matches("^\\d*\\.\\d+$")) {
			JOptionPane.showMessageDialog(this, "Giá trị khuyến mãi phải là số thập phân");
			txtGiaTri.requestFocus();
			return false;
		} else if (Float.parseFloat(giaTriKm) < 0) {
			JOptionPane.showMessageDialog(this, "Giá trị khuyến mãi phải lớn hơn 0");
			txtGiaTri.requestFocus();
			return false;
		}

		if (ngayBDBangChu.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để ngày bắt đầu trống");
			return false;
		} else if (LocalDate.parse(ngayBDBangChu, formatter).isBefore(now)) {
			JOptionPane.showMessageDialog(this, "Ngày bắt đầu phải lớn hơn hoặc bằng ngày hiện hành ");
			return false;
		}
		if (ngayKTBangChu.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để ngày bắt đầu trống");
			return false;
		} else if (LocalDate.parse(ngayKTBangChu, formatter).isBefore(now)) {
			JOptionPane.showMessageDialog(this, "Ngày kết thúc phải lớn hơn hoặc bằng ngày hiện hành ");
			return false;
		}

		return true;
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
//		if (e.getSource().equals(btnThem)) {
//			if (validData()) {
//				String maKM = txtMa.getText();
//				String ten = txtTen.getText().trim();
//				String dieuKienApDung = txtDKApDung.getText().trim();
//				Float giaTriKm = Float.parseFloat(txtGiaTri.getText());
//				String ngayBatDau = datePicker.getJFormattedTextField().getText();
//				String ngayKetThuc = datePicker.getJFormattedTextField().getText();
//				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//				KhuyenMai km = null;
//				try {
//					km = new KhuyenMai(maKM, ten, dieuKienApDung, giaTriKm, dateFormat.parse(ngayBatDau),
//							dateFormat.parse(ngayKetThuc));
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				km_dao.create(km);
//				PnGiaoDienCapNhatKhuyenMai.this.model.setRowCount(0);
//				docDuLieuDataBaseVaoTable();
//				xoaTrang();
//			}
//		} else if (e.getSource().equals(btnXoaTrang)) {
//			xoaTrang();
//		} else if (e.getSource().equals(btnXoa)) {
//			int row = tblKM.getSelectedRow();
//			if (row != -1) {
//				String maKH = (String) tblKM.getModel().getValueAt(row, 0);
//				int comfirm = JOptionPane.showConfirmDialog(this, "Chắc chắn xóa không?", "Chú ý",
//						JOptionPane.YES_NO_OPTION);
//				if (comfirm == JOptionPane.YES_OPTION) {
//					km_dao.delete(maKH);
//					for (int i = model.getRowCount() - 1; i >= 0; i--) {
//						model.removeRow(i);
//					}
//					docDuLieuDataBaseVaoTable();
//					xoaTrang();
//					JOptionPane.showMessageDialog(this, "Đã xóa thành công");
//				}
//			} else {
//				JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần xóa");
//			}
//		} else if (e.getSource().equals(btnSua)) {
//			if (tblKM.getSelectedRow() == -1)
//				JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần sửa");
//			else {
//				if (validData()) {
//					String maKM = txtMa.getText();
//					String ten = txtTen.getText().trim();
//					String dieuKienApDung = txtDKApDung.getText().trim();
//					Float giaTriKm = Float.parseFloat(txtGiaTri.getText());
//					String ngayBatDau = datePicker.getJFormattedTextField().getText();
//					String ngayKetThuc = datePicker.getJFormattedTextField().getText();
//					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//					KhuyenMai km = null;
//					try {
//						km = new KhuyenMai(maKM, ten, dieuKienApDung, giaTriKm, dateFormat.parse(ngayBatDau),
//								dateFormat.parse(ngayKetThuc));
//					} catch (ParseException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//
//					if (km_dao.update(km)) {
//						model.setRowCount(0);
//						docDuLieuDataBaseVaoTable();
//						xoaTrang();
//						JOptionPane.showMessageDialog(this, "Đã cập nhật thành công");
//					}
//				}
//			}
//		}
	}

	private void docDuLieuDataBaseVaoTable() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<KhuyenMai> ds = km_dao.getAlltbKhuyenMai();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String khuyenMaiChuyenDoi = "";

		for (KhuyenMai khuyenMai : ds) {
			khuyenMaiChuyenDoi = Math.round(khuyenMai.getGiaTriKhuyenMai() * 100) + "%";
			Object[] row = { khuyenMai.getMaKM(), khuyenMai.getTenKM(), dateFormat.format(khuyenMai.getNgayBatDau()),
					dateFormat.format(khuyenMai.getNgayKetThuc()), khuyenMai.getDieuKienApDung(), khuyenMaiChuyenDoi };
			model.addRow(row);
		}
	}
}
