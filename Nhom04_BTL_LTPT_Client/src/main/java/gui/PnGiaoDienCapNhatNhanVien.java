package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
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

public class PnGiaoDienCapNhatNhanVien extends JPanel implements ActionListener {
	private JLabel lblMa, lblTen, lblDiaChi, lblGioiTinh, lblSDT, lblChucVu, lblNgaySinh, lbltrangThai, lblHinhAnh,
			lblHienDSNVNghiViec, lblTenHinhDuocChon;
	private JTextField txtMa, txtTen, txtDiaChi, txtSDT;
	private JRadioButton radNam, radNu;
	private JComboBox cmbCV, cmbTT;
	private JButton btnThem, btnXoa, btnSua, btnXoaTrang, btnChonAnh;
	private JPanel pnlNorthBackGround;
	private JTable tblNV;
	private DefaultTableModel model;
	private JDatePickerImpl datePicker;
	private FileDialog fileDialog;
	private Component horizontalStrut, kichThuocTen;
	private Box b8;
	private SteelCheckBox scbHienThi;
	private NhanVien_DAO nv_dao;
	private FrmGiaoDienChinh frmMain;
	private String hinh = null;

	public PnGiaoDienCapNhatNhanVien(FrmGiaoDienChinh frm) throws RemoteException, NotBoundException {
		frmMain = frm;
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
		cmbTT.addItem("Đang làm");
		cmbTT.addItem("Đã nghỉ");
		Box b7 = Box.createHorizontalBox();
		b7.add(Box.createHorizontalStrut(10));
		b7.add(lbltrangThai);
		b7.add(Box.createHorizontalStrut(10));
		b7.add(cmbTT);
		b7.add(Box.createHorizontalStrut(10));
		pnlWestBackGround.add(b7);
		pnlWestBackGround.add(Box.createVerticalStrut(10));
//		Hình ảnh nhân viên
		lblHinhAnh = new JLabel("Hình ảnh:");
		btnChonAnh = new JButton("Chọn ảnh");
		b8 = Box.createHorizontalBox();
		b8.add(Box.createHorizontalStrut(10));
		b8.add(lblHinhAnh);
		horizontalStrut = Box.createHorizontalStrut(85);
		btnChonAnh.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				fileDialog = new FileDialog((Frame) null, "Chọn Ảnh", FileDialog.LOAD);
				fileDialog.setVisible(true);
				String fileName = fileDialog.getFile();
				if (fileName != null) {
					lblTenHinhDuocChon = new JLabel(fileName);
					b8.remove(btnChonAnh);
					b8.remove(horizontalStrut);
					b8.add(lblTenHinhDuocChon);
					int kichThuoc = lblTenHinhDuocChon.getText().length() * (85 / 13);
					kichThuocTen = Box.createHorizontalStrut(kichThuoc);
					b8.add(kichThuocTen);
				}
			}
		});
		b8.add(horizontalStrut);
		b8.add(btnChonAnh);
		b8.add(Box.createHorizontalStrut(10));
		pnlWestBackGround.add(b8);
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
		btnThem = new JButton("Thêm", getIcon("data/images/icon_btnThem.png", 16, 16));
		btnXoa = new JButton("Xóa", getIcon("data/images/icon_btnXoa.png", 16, 16));
		btnSua = new JButton("Sửa", getIcon("data/images/icon_btnLuu.png", 16, 16));
		btnXoaTrang = new JButton("Xóa trắng", getIcon("data/images/icon_btnXoaTrang.png", 16, 16));
		JPanel pnlHienThi = new JPanel();
		lblHienDSNVNghiViec = new JLabel("Hiển thị danh sách nhân viên nghỉ việc:");
		scbHienThi = new SteelCheckBox();
		scbHienThi.setBackground(Color.white);
		scbHienThi.setSelectedColor(ColorDef.GREEN);
		scbHienThi.setRised(true);
		scbHienThi.setText("yellow");
		scbHienThi.setColored(true);
		scbHienThi.setForeground(Color.white);
		scbHienThi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (scbHienThi.isSelected() == true) {
					lblHienDSNVNghiViec.setText("Ẩn danh sách nhân viên nghỉ việc:");
					PnGiaoDienCapNhatNhanVien.this.model.setRowCount(0);
					try {
						docDuLieuDataBaseVaoTable();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					lblHienDSNVNghiViec.setText("Hiển thị danh sách nhân viên nghỉ việc:");
					PnGiaoDienCapNhatNhanVien.this.model.setRowCount(0);
					try {
						docDuLieuDataBaseVaoTable();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		pnlHienThi.add(lblHienDSNVNghiViec);
		pnlHienThi.add(scbHienThi);
		pnlHienThi.setBackground(Color.white);
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btnXoaTrang.addActionListener(this);

		pnlSouthBackGround.add(btnThem);
		pnlSouthBackGround.add(btnXoa);
		pnlSouthBackGround.add(btnSua);
		pnlSouthBackGround.add(btnXoaTrang);
		pnlSouthBackGround.add(pnlHienThi);

		pnlCenter.add(pnlNorthBackGround, BorderLayout.NORTH);
		pnlCenter.add(pnlSouthBackGround, BorderLayout.SOUTH);
		taoBang();
		docDuLieuDataBaseVaoTable();

		this.add(pnlWest, BorderLayout.WEST);
		this.add(pnlCenter, BorderLayout.CENTER);
		this.setVisible(true);
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
				String filePath = null;
				try {
					filePath = nv_dao.getNhanVienTheoMa(model.getValueAt(row, 0).toString()).getHinhAnh();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				File file = new File(filePath);
				String fileName = file.getName(); // Lấy tên tệp
				if (lblTenHinhDuocChon != null && lblTenHinhDuocChon.getText().isEmpty() == false) {
					b8.remove(lblTenHinhDuocChon);
					b8.remove(kichThuocTen);
				}
				lblTenHinhDuocChon = new JLabel(fileName);
				hinh = lblTenHinhDuocChon.getText();
				chonAnh();
				b8.remove(btnChonAnh);
				b8.remove(horizontalStrut);
				b8.add(lblTenHinhDuocChon);
				int kichThuoc = lblTenHinhDuocChon.getText().length() * (85 / 13);
				kichThuocTen = Box.createHorizontalStrut(kichThuoc);
				b8.add(kichThuocTen);
			}
		});
		JScrollPane sp = new JScrollPane(tblNV, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		sp.setPreferredSize(new Dimension(1001, 431));
		pnlNorthBackGround.add(sp);
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
		if (e.getSource().equals(btnThem)) {
			if (validData()) {
				String ma = txtMa.getText();
				String ten = txtTen.getText();
				Date ngaySinh = (Date) datePicker.getModel().getValue();
				boolean gioiTinh = false;
				if (radNam.isSelected()) {
					gioiTinh = true;
				}
				String sdt = txtSDT.getText();
				String diaChi = txtDiaChi.getText();
				String chucVu = cmbCV.getSelectedItem().toString();
				String trangThai = cmbTT.getSelectedItem().toString();
				String gioiTinhBangChu = "Nữ";
				if (gioiTinh == true) {
					gioiTinhBangChu = "Nam";
				}
//				Lưu hình ảnh vào project mỗi khi thêm
				if (fileDialog == null) {
					String ns_bangChu = datePicker.getJFormattedTextField().getText();
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
					NhanVien nv = null;
					try {
						nv = new NhanVien(ma, ten, dateFormat.parse(ns_bangChu), gioiTinh, sdt, diaChi, chucVu,
								trangThai, "data/images/" + hinh);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						nv_dao.create(nv);
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					Date ns = null;
					try {
						ns = dateFormat.parse(ns_bangChu);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String ma1 = null;
					try {
						ma1 = nv_dao.getNhanVienMoiNhat().getMaNV();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Object[] row = { ma1 , ten, dateFormat.format(ns), gioiTinhBangChu,
							sdt, diaChi, chucVu, trangThai, getIcon("data/images/" + hinh, 60, 50) };
					model.addRow(row);
					xoaTrang();
					PnGiaoDienCapNhatNhanVien.this.model.setRowCount(0);
					try {
						docDuLieuDataBaseVaoTable();
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					try {
						chonDangKiTaiKhoan(nv_dao.getNhanVienMoiNhat().getMaNV());
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					String selectedFilePath = fileDialog.getDirectory() + fileDialog.getFile();
					String destinationFolder = null;

					if (fileDialog.getFile() != null || hinh != null) {
						// Thư mục đích trong dự án (thay đổi theo dự án của bạn)
						destinationFolder = "data/images/" + fileDialog.getFile();

						// Tạo tệp đích
						File destinationFile = new File(destinationFolder);

						try {
							// Sao chép tệp từ nguồn đến đích
							if (fileDialog.getFile() != null) {
								Files.copy(Paths.get(selectedFilePath), destinationFile.toPath(),
										StandardCopyOption.REPLACE_EXISTING);
							} else {
								destinationFolder = "data/images/" + hinh;
							}

							NhanVien nv = new NhanVien(ma, ten, ngaySinh, gioiTinh, sdt, diaChi, chucVu, trangThai,
									destinationFolder);
							nv_dao.create(nv);
							SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
							Object[] row = { nv_dao.getNhanVienMoiNhat().getMaNV(), ten, dateFormat.format(ngaySinh),
									gioiTinhBangChu, sdt, diaChi, chucVu, trangThai,
									getIcon(destinationFolder, 60, 50) };
							model.addRow(row);
							xoaTrang();
							fileDialog.setFile(null);
							PnGiaoDienCapNhatNhanVien.this.model.setRowCount(0);
							docDuLieuDataBaseVaoTable();
							chonDangKiTaiKhoan(nv_dao.getNhanVienMoiNhat().getMaNV());
						} catch (IOException | NotBoundException ex) {
							// Xử lý lỗi nếu có
							ex.printStackTrace();
						}
					}
				}
			}
		} else if (e.getSource().equals(btnXoaTrang)) {
			xoaTrang();
		} else if (e.getSource().equals(btnXoa)) {
			int row = tblNV.getSelectedRow();
			if (row != -1) {
				String maNV = (String) tblNV.getModel().getValueAt(row, 0);
				int comfirm = JOptionPane.showConfirmDialog(this, "Chắc chắn xóa không?", "Chú ý",
						JOptionPane.YES_NO_OPTION);
				if (comfirm == JOptionPane.YES_OPTION) {
					try {
						if (nv_dao.getNhanVienTheoMa(maNV).getTrangThai().equalsIgnoreCase("Đang làm")) {
							nv_dao.delete(maNV);
							for (int i = model.getRowCount() - 1; i >= 0; i--) {
								model.removeRow(i);
							}
							docDuLieuDataBaseVaoTable();
							xoaTrang();
							JOptionPane.showMessageDialog(this, "Đã xóa thành công");
						} else {
							JOptionPane.showMessageDialog(this, "Nhân viên đã nghỉ không thể xóa");
						}
					} catch (HeadlessException | RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			} else {
				JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần xóa");
			}
		} else if (e.getSource().equals(btnSua)) {
			if (tblNV.getSelectedRow() == -1)
				JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần sửa");
			else {
				if (validData()) {
					String ma = txtMa.getText();
					String ten = txtTen.getText();
					String ngaySinh = datePicker.getJFormattedTextField().getText();
					String gioiTinh = "Nữ";
					if (radNam.isSelected()) {
						gioiTinh = "Nam";
					}
					String sdt = txtSDT.getText();
					String diaChi = txtDiaChi.getText();
					String chucVu = cmbCV.getSelectedItem().toString();
					String trangThai = cmbTT.getSelectedItem().toString();
					String hinhAnh = null;
					try {
						hinhAnh = nv_dao.getNhanVienTheoMa(ma).getHinhAnh();
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if (hinhAnh.equalsIgnoreCase("data/images/" + hinh) == false) {
						hinhAnh = "data/images/" + hinh;
						String selectedFilePath = fileDialog.getDirectory() + fileDialog.getFile();
						File destinationFile = new File(hinhAnh);
						try {
							Files.copy(Paths.get(selectedFilePath), destinationFile.toPath(),
									StandardCopyOption.REPLACE_EXISTING);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
					boolean gt = false;
					if (radNam.isSelected()) {
						gt = true;
					}
					NhanVien nvNew = null;
					try {
						nvNew = new NhanVien(ma, ten, dateFormat.parse(ngaySinh), gt, sdt, diaChi, chucVu, trangThai,
								hinhAnh);
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						if (nv_dao.update(nvNew)) {
							model.setRowCount(0);
							docDuLieuDataBaseVaoTable();
							xoaTrang();
							JOptionPane.showMessageDialog(this, "Đã cập nhật thành công");
						}
					} catch (HeadlessException | RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
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
			if (nhanVien.getTrangThai().equalsIgnoreCase("Đang làm") && scbHienThi.isSelected() == false) {
				model.addRow(new Object[] { nhanVien.getMaNV(), nhanVien.getTenNV(),
						dateFormat.format(nhanVien.getNgaySinh()), gioiTinh, nhanVien.getSoDienThoai(),
						nhanVien.getDiaChi(), nhanVien.getChucVu(), nhanVien.getTrangThai(),
						getIcon(nhanVien.getHinhAnh(), 60, 50) });
			} else if (scbHienThi.isSelected() == true) {
				model.addRow(new Object[] { nhanVien.getMaNV(), nhanVien.getTenNV(),
						dateFormat.format(nhanVien.getNgaySinh()), gioiTinh, nhanVien.getSoDienThoai(),
						nhanVien.getDiaChi(), nhanVien.getChucVu(), nhanVien.getTrangThai(),
						getIcon(nhanVien.getHinhAnh(), 60, 50) });
			}
		}
	}

	private void xoaTrang() {
		// TODO Auto-generated method stub
		txtMa.setText("");
		txtTen.setText("");
		datePicker.getJFormattedTextField().setText("");
		radNam.setSelected(true);
		txtDiaChi.setText("");
		txtSDT.setText("");
		cmbCV.setSelectedIndex(0);
		cmbTT.setSelectedIndex(0);
		if (lblTenHinhDuocChon.getText().isEmpty() == false) {
			horizontalStrut = Box.createHorizontalStrut(80);
			b8.remove(lblTenHinhDuocChon);
			b8.remove(kichThuocTen);
			b8.add(horizontalStrut);
			btnChonAnh.setPreferredSize(new Dimension(10, 20));
			b8.add(btnChonAnh);
			lblTenHinhDuocChon = new JLabel();
		}
		hinh = null;
		tblNV.clearSelection();
		txtTen.requestFocus();
	}

	private void chonDangKiTaiKhoan(String maNV) throws RemoteException, NotBoundException {
		int comfirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn đăng kí tài khoản không?", "Đã thêm thành công",
				JOptionPane.YES_NO_OPTION);
		if (comfirm == JOptionPane.YES_OPTION) {
			frmMain.chuyenSangTrangTaiKhoan(maNV);
		}
	}

	private void chonAnh() {
		lblTenHinhDuocChon.addMouseListener(new MouseListener() {

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
				fileDialog = new FileDialog((Frame) null, "Chọn Ảnh", FileDialog.LOAD);
				fileDialog.setVisible(true);
				lblTenHinhDuocChon.setText(fileDialog.getFile());
				hinh = fileDialog.getFile();
			}
		});
	}

	private boolean validData() {
		String ten = txtTen.getText().trim();
		String sdt = txtSDT.getText().trim();
		String diaChi = txtDiaChi.getText().trim();
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
		} else if (now.getYear() - LocalDate.parse(ngaySinhBangChu, formatter).getYear() <= 17 || now.getYear() - LocalDate.parse(ngaySinhBangChu, formatter).getYear() >= 63) {
			JOptionPane.showMessageDialog(this, "Tuổi phải trên 17 và nhỏ hơn 63");
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
		if (fileDialog == null && hinh == null) {
			JOptionPane.showMessageDialog(this, "Hãy chọn ảnh" + "");
			return false;
		}
		return true;
	}
}
