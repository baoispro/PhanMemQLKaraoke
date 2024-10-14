package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dao.NhanVien_DAO;
import entity.NhanVien;
import eu.hansolo.custom.SteelCheckBox;
import eu.hansolo.tools.ColorDef;

public class PnGiaoDienTimKiemNhanVien extends JPanel implements ActionListener {
	private JLabel lblMa, lblTen, lblDiaChi, lblGioiTinh, lblSDT, lblChucVu, lblNgaySinh, lbltrangThai,
			lblHienDSNVNghiViec;
	private JTextField txtMa, txtTen, txtDiaChi, txtSDT;
	private JRadioButton radNam, radNu;
	private JComboBox cmbCV, cmbTT;
	private JButton btnTimKiem, btnXoaTrang;
	private JPanel pnlNorthBackGround;
	private JTable tblNV;
	private DefaultTableModel model;
	private JDatePickerImpl datePicker;
	private NhanVien_DAO nv_dao;

	public PnGiaoDienTimKiemNhanVien() throws RemoteException, NotBoundException {
		// TODO Auto-generated constructor stub
		Registry registry = LocateRegistry.getRegistry(2024);
		nv_dao = (NhanVien_DAO) registry.lookup("nv_dao");
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
//		Mã
		lblMa = new JLabel("Mã nhân viên:");
		lblMa.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtMa = new JTextField();
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
//		Tên
		lblTen = new JLabel("Tên nhân viên:");
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
//		Ngày sinh
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
//		Giới tính
		lblGioiTinh = new JLabel("Giới tính:");
		radNam = new JRadioButton("Nam");
		radNam.setBackground(Color.white);
		radNam.setSelected(true);
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
//		SDT
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
//		Địa chỉ
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
//		Chức vụ
		lblChucVu = new JLabel("Chức vụ:");
		cmbCV = new JComboBox<>();
		cmbCV.addItem("Tất cả");
		cmbCV.addItem("Quản lí");
		cmbCV.addItem("Nhân viên");
		Box b6 = Box.createHorizontalBox();
		b6.add(Box.createHorizontalStrut(10));
		b6.add(lblChucVu);
		b6.add(Box.createHorizontalStrut(19));
		b6.add(cmbCV);
		b6.add(Box.createHorizontalStrut(10));
		pnlWestBackGround.add(b6);
		pnlWestBackGround.add(Box.createVerticalStrut(10));
//		Trạng thái
		lbltrangThai = new JLabel("Trạng thái:");
		cmbTT = new JComboBox<>();
		cmbTT.addItem("Tất cả");
		cmbTT.addItem("Đang làm");
		cmbTT.addItem("Đã nghỉ");
		Box b7 = Box.createHorizontalBox();
		b7.add(Box.createHorizontalStrut(10));
		b7.add(lbltrangThai);
		b7.add(Box.createHorizontalStrut(10));
		b7.add(cmbTT);
		b7.add(Box.createHorizontalStrut(10));
		pnlWestBackGround.add(b7);
		pnlWestBackGround.add(Box.createVerticalStrut(200));

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
		btnTimKiem.addActionListener(this);
		btnXoaTrang.addActionListener(this);

		pnlSouthBackGround.add(btnTimKiem);
		pnlSouthBackGround.add(btnXoaTrang);

		pnlCenter.add(pnlNorthBackGround, BorderLayout.NORTH);
		pnlCenter.add(pnlSouthBackGround, BorderLayout.SOUTH);
		taoBang();
		docDuLieuDataBaseVaoTable();

		this.add(pnlWest, BorderLayout.WEST);
		this.add(pnlCenter, BorderLayout.CENTER);
		this.setVisible(true);
	}

	private ImageIcon getIcon(String path, int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon iconEmployee = new ImageIcon(path);
		Image scaledImage = iconEmployee.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		return scaledIcon;
	}

	private void taoBang() {
		// TODO Auto-generated method stub
		model = new DefaultTableModel();
		tblNV = new JTable(model);
		model.addColumn("Mã Nhân Viên");
		model.addColumn("Tên Nhân Viên");
		model.addColumn("Ngày sinh");
		model.addColumn("Giới tính");
		model.addColumn("Số Điện Thoại");
		model.addColumn("Địa Chỉ");
		model.addColumn("Chức vụ");
		model.addColumn("Trạng thái");
		model.addColumn("Hình ảnh");
		TableColumn column1 = tblNV.getColumnModel().getColumn(1);
		column1.setPreferredWidth(120);
		TableColumn column3 = tblNV.getColumnModel().getColumn(3);
		column3.setPreferredWidth(50);
		TableColumn column5 = tblNV.getColumnModel().getColumn(5);
		column5.setPreferredWidth(250);
		TableColumn column8 = tblNV.getColumnModel().getColumn(8);
		column8.setPreferredWidth(40);
//		Phương thức này để điều chỉnh cột thành định dạng hình
		TableCellRenderer renderer = new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				if (value instanceof ImageIcon) {
					JLabel lbl = new JLabel((ImageIcon) value);
					lbl.setHorizontalAlignment(JLabel.CENTER);
					return lbl;
				}
				return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			}
		};
		tblNV.getColumnModel().getColumn(8).setCellRenderer(renderer);
		tblNV.setRowHeight(50);
		tblNV.addMouseListener(new MouseListener() {

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
				int row = tblNV.getSelectedRow();
				txtMa.setText(model.getValueAt(row, 0).toString());
				txtTen.setText(model.getValueAt(row, 1).toString());
				datePicker.getJFormattedTextField().setText(model.getValueAt(row, 2).toString());
				if (model.getValueAt(row, 3).toString().equalsIgnoreCase("Nam"))
					radNam.setSelected(true);
				else {
					radNu.setSelected(true);
				}
				txtSDT.setText(model.getValueAt(row, 4).toString());
				txtDiaChi.setText(model.getValueAt(row, 5).toString());
				cmbCV.setSelectedItem(model.getValueAt(row, 6));
				cmbTT.setSelectedItem(model.getValueAt(row, 7));
			}
		});
		JScrollPane sp = new JScrollPane(tblNV, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setPreferredSize(new Dimension(1001, 431));
		pnlNorthBackGround.add(sp);
	}

	private void docDuLieuDataBaseVaoTable() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<NhanVien> ds = nv_dao.getAlltbNhanVien();
		String gioiTinh = "Nữ";
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		for (NhanVien nhanVien : ds) {
			if (nhanVien.isGioiTinh() == true) {
				gioiTinh = "Nam";
			}
			else {
				gioiTinh = "Nữ";
			}
			model.addRow(
					new Object[] { nhanVien.getMaNV(), nhanVien.getTenNV(), dateFormat.format(nhanVien.getNgaySinh()),
							gioiTinh, nhanVien.getSoDienThoai(), nhanVien.getDiaChi(), nhanVien.getChucVu(),
							nhanVien.getTrangThai(), getIcon(nhanVien.getHinhAnh(), 60, 50) });
		}
	}
	
	private void xoaTrang() throws RemoteException {
		// TODO Auto-generated method stub
		txtMa.setText("");
		txtTen.setText("");
		datePicker.getJFormattedTextField().setText("");
		radNam.setSelected(true);
		txtDiaChi.setText("");
		txtSDT.setText("");
		cmbCV.setSelectedIndex(0);
		cmbTT.setSelectedIndex(0);
		tblNV.clearSelection();
		model.setRowCount(0);
		docDuLieuDataBaseVaoTable();
		txtTen.requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(btnXoaTrang)) {
			try {
				xoaTrang();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else if(e.getSource().equals(btnTimKiem)) {
			String ma = txtMa.getText();
			if(ma.isEmpty())
				ma=null;
			String ten = txtTen.getText();
			if(ten.isEmpty())
				ten=null;
			String ngaySinh = datePicker.getJFormattedTextField().getText();
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date ns = null;
			try {
				if(ngaySinh.isEmpty()==false) {
					ns = dateFormat.parse(ngaySinh);
				}
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			boolean gt = false;
			if(radNam.isSelected())
				gt = true;
			String diaChi = txtDiaChi.getText();
			if(diaChi.isEmpty())
				diaChi=null;
			String sdt = txtSDT.getText();
			if(sdt.isEmpty())
				sdt=null;
			String cv = cmbCV.getSelectedItem().toString();
			if(cv.equalsIgnoreCase("tất cả"))
				cv=null;
			String tt = cmbTT.getSelectedItem().toString();
			if(tt.equalsIgnoreCase("tất cả"))
				tt=null;
			model.setRowCount(0);
			try {
				if(nv_dao.timKiemNV(ma, ten, ns, gt, sdt, diaChi, cv, tt).isEmpty()) {
					JOptionPane.showMessageDialog(this, "Không tìm thấy");
				}
				else {
					String gioiTinh = "Nữ";
					for (NhanVien nhanVien : nv_dao.timKiemNV(ma, ten, ns, gt, sdt, diaChi, cv, tt)) {
						if (nhanVien.isGioiTinh() == true) {
							gioiTinh = "Nam";
						}
						else {
							gioiTinh = "Nữ";
						}
						model.addRow(new Object[] { nhanVien.getMaNV(), nhanVien.getTenNV(),
								dateFormat.format(nhanVien.getNgaySinh()), gioiTinh, nhanVien.getSoDienThoai(),
								nhanVien.getDiaChi(), nhanVien.getChucVu(), nhanVien.getTrangThai(),
								getIcon(nhanVien.getHinhAnh(), 60, 50) });
					}
				}
			} catch (HeadlessException | RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
