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
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dao.KhachHang_DAO;
import entity.KhachHang;

public class PnGiaoDienTimKiemKhachHang extends JPanel implements ActionListener {
	private JLabel lblMa, lblTen, lblDiaChi, lblGioiTinh, lblSDT, lblNgaySinh, lblEmail, lblCCCD;
	private JTextField txtMa, txtTen, txtDiaChi, txtSDT, txtEmail, txtCCCD;
	private JRadioButton radNam, radNu;
	private JButton btnTK, btnXoaTrang;
	private JPanel pnlNorthBackGround;
	JDatePickerImpl datePicker;
	private JTable tblKH;
	private DefaultTableModel model;
	private KhachHang_DAO kh_dao;

	public PnGiaoDienTimKiemKhachHang() throws RemoteException, NotBoundException {
		// TODO Auto-generated constructor stub
		Registry registry = LocateRegistry.getRegistry(2024);
		kh_dao = (KhachHang_DAO) registry.lookup("kh_dao");
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
		// Mã
		lblMa = new JLabel("Mã khách hàng");
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
		lblTen = new JLabel("Tên khách hàng");
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
//				Ngày sinh
		lblNgaySinh = new JLabel("Ngày sinh:");
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
		b2.add(lblNgaySinh);
		b2.add(Box.createHorizontalStrut(10));
		b2.add(datePicker);
		b2.add(Box.createHorizontalStrut(11));
		pnlWestBackGround.add(b2);
		pnlWestBackGround.add(Box.createVerticalStrut(10));
//				Giới tính
		lblGioiTinh = new JLabel("Giới tính:");
		radNam = new JRadioButton("Nam");
		radNam.setSelected(true);
		radNam.setBackground(Color.white);
		radNu = new JRadioButton("Nữ");
		radNu.setBackground(Color.white);
		ButtonGroup G = new ButtonGroup();
		G.add(radNam);
		G.add(radNu);
		Box b3 = Box.createHorizontalBox();
		b3.add(Box.createHorizontalStrut(10));
		b3.add(lblGioiTinh);
		b3.add(Box.createHorizontalStrut(80));
		b3.add(radNam);
		b3.add(radNu);
		b3.add(Box.createHorizontalStrut(10));
		pnlWestBackGround.add(b3);
//				SDT
		lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtSDT = new JTextField();
		Box b4 = Box.createVerticalBox();
		Box c2 = Box.createHorizontalBox();
		c2.add(Box.createHorizontalStrut(10));
		b4.add(lblSDT);
		b4.add(Box.createVerticalStrut(10));
		c2.add(txtSDT);
		c2.add(Box.createHorizontalStrut(10));
		b4.add(c2);
		b4.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b4);
//				Địa chỉ
		lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtDiaChi = new JTextField();
		Box b5 = Box.createVerticalBox();
		Box c3 = Box.createHorizontalBox();
		c3.add(Box.createHorizontalStrut(10));
		b5.add(lblDiaChi);
		b5.add(Box.createVerticalStrut(10));
		c3.add(txtDiaChi);
		b5.add(c3);
		c3.add(Box.createHorizontalStrut(10));
		b5.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b5);

//		 		Email
		lblEmail = new JLabel("Email");
		lblEmail.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtEmail = new JTextField();
		Box b7 = Box.createVerticalBox();
		Box c5 = Box.createHorizontalBox();
		c5.add(Box.createHorizontalStrut(10));
		b7.add(lblEmail);
		b7.add(Box.createVerticalStrut(10));
		c5.add(txtEmail);
		b7.add(c5);
		c5.add(Box.createHorizontalStrut(10));
		b7.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b7);

//		 		CCCD
		lblCCCD = new JLabel("CCCD/CMND");
		lblCCCD.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtCCCD = new JTextField();
		Box b8 = Box.createVerticalBox();
		Box c6 = Box.createHorizontalBox();
		c6.add(Box.createHorizontalStrut(10));
		b8.add(lblCCCD);
		b8.add(Box.createVerticalStrut(10));
		c6.add(txtCCCD);
		b8.add(c6);
		c6.add(Box.createHorizontalStrut(10));
		b8.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b8);
		pnlWestBackGround.add(Box.createVerticalStrut(200));

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
		btnTK = new JButton("Tìm kiếm", getIcon("data/images/icon_btnTK.png", 16, 16));
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
		tblKH = new JTable(model);
		model.addColumn("Mã khách hàng");
		model.addColumn("Tên khách hàng");
		model.addColumn("Ngày sinh");
		model.addColumn("Giới tính");
		model.addColumn("Số Điện Thoại");
		model.addColumn("Địa Chỉ");
		model.addColumn("Email");
		model.addColumn("CCCD/CMND");
		TableColumn column1 = tblKH.getColumnModel().getColumn(1);
		column1.setPreferredWidth(120);
		TableColumn column3 = tblKH.getColumnModel().getColumn(3);
		column3.setPreferredWidth(50);
		TableColumn column5 = tblKH.getColumnModel().getColumn(6);
		column5.setPreferredWidth(150);
		TableColumn column6 = tblKH.getColumnModel().getColumn(6);
		column6.setPreferredWidth(150);
		docDuLieuDataBaseVaoTable();
//				Phương thức này để điều chỉnh cột thành định dạng hình
		JScrollPane sp = new JScrollPane(tblKH, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setPreferredSize(new Dimension(1001, 431));
		pnlNorthBackGround.add(sp);

		tblKH.addMouseListener(new MouseListener() {

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
				int row = tblKH.getSelectedRow();
				txtMa.setText(model.getValueAt(row, 0).toString());
				txtTen.setText(model.getValueAt(row, 1).toString());
				datePicker.getJFormattedTextField().setText(model.getValueAt(row, 2).toString());
				if (model.getValueAt(row, 3).toString().equalsIgnoreCase("Nam"))
					radNam.setSelected(true);
				else {
					radNu.setSelected(false);
				}
				txtSDT.setText(model.getValueAt(row, 4).toString());
				txtDiaChi.setText(model.getValueAt(row, 5).toString());
				txtEmail.setText(model.getValueAt(row, 6).toString());
				txtCCCD.setText(model.getValueAt(row, 7).toString());
			}
		});
	}

	private ImageIcon getIcon(String path, int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon iconEmployee = new ImageIcon(path);
		Image scaledImage = iconEmployee.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		return scaledIcon;
	}

	private void xoaTrang() {
		txtMa.setText("");
		txtTen.setText("");
		datePicker.getJFormattedTextField().setText("");
		radNam.setSelected(true);
		txtDiaChi.setText("");
		txtSDT.setText("");
		txtEmail.setText("");
		txtCCCD.setText("");
	}

	private boolean validData() {
		String ten = txtTen.getText().trim();
		String diaChi = txtDiaChi.getText().trim();
		String sdt = txtSDT.getText();
		String email = txtEmail.getText().trim();
		String cccd = txtCCCD.getText().trim();
		String ngaySinhBangChu = datePicker.getJFormattedTextField().getText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate now = LocalDate.now();
		if (ten.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để tên trống");
			txtTen.requestFocus();
			return false;
		} else if (!ten.matches("^([A-Z\\p{Lu}][a-z\\p{Ll}]*(\\s[A-Z\\p{Lu}][a-z\\p{Ll}]*){0,})$")) {
			JOptionPane.showMessageDialog(this,
					"Tên phải viết hoa với mỗi kí tự đầu tiên và không chứa số hoặc kí tự đặc biệt");
			txtTen.requestFocus();
			return false;
		}
		if (ngaySinhBangChu.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để ngày sinh trống");
			return false;
		} else if (now.getYear() - LocalDate.parse(ngaySinhBangChu, formatter).getYear() <= 16) {
			JOptionPane.showMessageDialog(this, "Tuổi phải trên 15");
			return false;
		}

		if (sdt.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để số điện thoại trống");
			txtSDT.requestFocus();
			return false;
		} else if (!sdt.matches("^\\d{10}$")) {
			JOptionPane.showMessageDialog(this, "Số điện thoại chỉ có 10 kí tự số");
			txtSDT.requestFocus();
			return false;
		}
		if (diaChi.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để địa chỉ trống");
			txtDiaChi.requestFocus();
			return false;
		}
		if (email.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để địa chỉ email trống");
			txtEmail.requestFocus();
			return false;
		} else if (!email.matches("^[^@&=_'-+,<>.]{1,}@[A-Za-z]{1,}.com$")) {
			JOptionPane.showMessageDialog(this,
					"Email phải đúng định dạng example@example.com và tên không được chứa kí tự @&=_'-+,<>.");
			txtEmail.requestFocus();
			return false;
		}
		if (cccd.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để cccd trống");
			txtCCCD.requestFocus();
			return false;
		} else if (!cccd.matches("^\\d{12}$")) {
			JOptionPane.showMessageDialog(this, "CCCD chỉ 12 kí tự số");
			txtCCCD.requestFocus();
			return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
//		if (e.getSource().equals(btnThem)) {
//			if (validData()) {
//				boolean gioiTinh = false;
//				String ma = txtMa.getText();
//				String ten = txtTen.getText();
//				if (radNam.isSelected()) {
//					gioiTinh = true;
//				} else {
//					gioiTinh = false;
//				}
//				String gioiTinhBangChu = "Nữ";
//				if (gioiTinh == true) {
//					gioiTinhBangChu = "Nam";
//				} else {
//					gioiTinhBangChu = "Nữ";
//				}
//				String diaChi = txtDiaChi.getText();
//				String email = txtEmail.getText();
//				String cccd = txtCCCD.getText();
//				Date ngaySinh = (Date) datePicker.getModel().getValue();
//
//				String sdt = txtSDT.getText();
////						Lưu hình ảnh vào project mỗi khi thêm
//				String ns_bangChu = datePicker.getJFormattedTextField().getText();
//				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//				KhachHang kh = null;
//				try {
//					kh = new KhachHang(ma, ten, gioiTinh, diaChi, email, sdt, cccd, dateFormat.parse(ns_bangChu));
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				kh_dao.create(kh);
//				Date ns = null;
//				try {
//					ns = dateFormat.parse(ns_bangChu);
//				} catch (ParseException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				Object[] row = { ma, ten, dateFormat.format(ns), gioiTinhBangChu, sdt, diaChi, email, cccd };
//				model.addRow(row);
//				xoaTrang();
//				PnGiaoDienTimKiemKhachHang.this.model.setRowCount(0);
//				docDuLieuDataBaseVaoTable();
//			}
//		} else if (e.getSource().equals(btnXoaTrang)) {
//			xoaTrang();
//		} else if (e.getSource().equals(btnXoa)) {
//			int row = tblKH.getSelectedRow();
//			if (row != -1) {
//				String maKH = (String) tblKH.getModel().getValueAt(row, 0);
//				int comfirm = JOptionPane.showConfirmDialog(this, "Chắc chắn xóa không?", "Chú ý",
//						JOptionPane.YES_NO_OPTION);
//				if (comfirm == JOptionPane.YES_OPTION) {
//					kh_dao.delete(maKH);
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
//			if (tblKH.getSelectedRow() == -1)
//				JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần sửa");
//			else {
//				if (validData()) {
//					String ma = txtMa.getText();
//					String ten = txtTen.getText();
//					String ngaySinh = datePicker.getJFormattedTextField().getText();
//					String gioiTinh = "Nữ";
//					if (radNam.isSelected()) {
//						gioiTinh = "Nam";
//					}
//					String sdt = txtSDT.getText();
//					String diaChi = txtDiaChi.getText();
//					String email = txtEmail.getText();
//					String cccd = txtCCCD.getText();
//					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
//					boolean gt = false;
//					if (radNam.isSelected()) {
//						gt = true;
//					}
//					KhachHang khNew = null;
//					try {
//						khNew = new KhachHang(ma, ten, gt, diaChi, email, sdt, cccd, dateFormat.parse(ngaySinh));
//					} catch (ParseException e1) {
//						// TODO Auto-generated catch block
//						e1.printStackTrace();
//					}
//
//					if (kh_dao.update(khNew)) {
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
		ArrayList<KhachHang> ds = kh_dao.getAllKhachHang();
		String gioiTinh = "Nữ";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		for (KhachHang khachHang : ds) {
			if (khachHang.isGioiTinh() == true) {
				gioiTinh = "Nam";
			} else {
				gioiTinh = "Nữ";
			}
			Object[] row = { khachHang.getMaKH(), khachHang.getTenKH(), dateFormat.format(khachHang.getNgaySinh()),
					gioiTinh, khachHang.getSoDienThoai(), khachHang.getDiaChi(), khachHang.getEmail(),
					khachHang.getCccd() };
			model.addRow(row);
		}
	}
}
