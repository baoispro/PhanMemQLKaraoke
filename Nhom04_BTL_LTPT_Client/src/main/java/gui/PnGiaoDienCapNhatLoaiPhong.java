package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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
import java.text.DecimalFormat;
import java.util.ArrayList;

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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import dao.LoaiPhong_DAO;
import entity.LoaiPhong;

public class PnGiaoDienCapNhatLoaiPhong extends JPanel implements ActionListener {
	private JLabel lblMaLP, lblTenLP, lblGia, lblSoLuongNguoi, lblMoTa, lblHinhAnh, lblTenHinhDuocChon;
	private JTextField txtMaLP, txtTenLP, txtGia, txtSoLuongNguoi, txtMoTa;
	private JButton btnThem, btnXoa, btnSua, btnXoaTrang, btnChonAnh;
	private JPanel pnlNorthBackGround;
	private JTable tblLP;
	private DefaultTableModel model;
	private FileDialog fileDialog;
	private Component horizontalStrut, kichThuocTen;
	private Box b5;
	private String hinh = null;
	private LoaiPhong_DAO lp_dao;

	public PnGiaoDienCapNhatLoaiPhong() throws RemoteException, NotBoundException {
		// TODO Auto-generated constructor stub
		Registry registry = LocateRegistry.getRegistry(2024);
		lp_dao = (LoaiPhong_DAO) registry.lookup("lp_dao");
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
		lblMaLP = new JLabel("Mã loại phòng:");
		lblMaLP.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtMaLP = new JTextField();
		txtMaLP.setEditable(false);
		Box b = Box.createVerticalBox();
		Box c = Box.createHorizontalBox();
		b.add(lblMaLP);
		c.add(Box.createHorizontalStrut(10));
		c.add(txtMaLP);
		c.add(Box.createHorizontalStrut(10));
		b.add(Box.createVerticalStrut(10));
		b.add(c);
		b.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b);
//		Tên
		lblTenLP = new JLabel("Tên loại phòng:");
		lblTenLP.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtTenLP = new JTextField();
		Box b1 = Box.createVerticalBox();
		Box c1 = Box.createHorizontalBox();
		b1.add(lblTenLP);
		c1.add(Box.createHorizontalStrut(10));
		c1.add(txtTenLP);
		c1.add(Box.createHorizontalStrut(10));
		b1.add(Box.createVerticalStrut(10));
		b1.add(c1);
		b1.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b1);
//		Số lượng chứa người
		lblSoLuongNguoi = new JLabel("Số lượng người chứa được:");
		lblSoLuongNguoi.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtSoLuongNguoi = new JTextField();
		Box b2 = Box.createVerticalBox();
		Box c2 = Box.createHorizontalBox();
		b2.add(lblSoLuongNguoi);
		c2.add(Box.createHorizontalStrut(10));
		c2.add(txtSoLuongNguoi);
		c2.add(Box.createHorizontalStrut(10));
		b2.add(Box.createVerticalStrut(10));
		b2.add(c2);
		b2.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b2);
//		Đơn giá
		lblGia = new JLabel("Giá:");
		lblGia.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtGia = new JTextField();
		Box b3 = Box.createVerticalBox();
		Box c3 = Box.createHorizontalBox();
		b3.add(lblGia);
		c3.add(Box.createHorizontalStrut(10));
		c3.add(txtGia);
		c3.add(Box.createHorizontalStrut(10));
		b3.add(Box.createVerticalStrut(10));
		b3.add(c3);
		b3.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b3);
//		Mô tả
		lblMoTa = new JLabel("Mô tả:");
		lblMoTa.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtMoTa = new JTextField();
		Box b4 = Box.createVerticalBox();
		Box c4 = Box.createHorizontalBox();
		b4.add(lblMoTa);
		c4.add(Box.createHorizontalStrut(10));
		c4.add(txtMoTa);
		c4.add(Box.createHorizontalStrut(10));
		b4.add(Box.createVerticalStrut(10));
		b4.add(c4);
		b4.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b4);
//		Hình ảnh nhân viên
		lblHinhAnh = new JLabel("Hình ảnh:");
		btnChonAnh = new JButton("Chọn ảnh");
		b5 = Box.createHorizontalBox();
		b5.add(Box.createHorizontalStrut(10));
		b5.add(lblHinhAnh);
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
					b5.remove(btnChonAnh);
					b5.remove(horizontalStrut);
					b5.add(lblTenHinhDuocChon);
					int kichThuoc = lblTenHinhDuocChon.getText().length() * (85 / 13);
					kichThuocTen = Box.createHorizontalStrut(kichThuoc);
					b5.add(kichThuocTen);
				}
			}
		});
		b5.add(horizontalStrut);
		b5.add(btnChonAnh);
		b5.add(Box.createHorizontalStrut(10));
		pnlWestBackGround.add(b5);
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
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btnXoaTrang.addActionListener(this);

		pnlSouthBackGround.add(btnThem);
		pnlSouthBackGround.add(btnXoa);
		pnlSouthBackGround.add(btnSua);
		pnlSouthBackGround.add(btnXoaTrang);

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
		tblLP = new JTable(model);
		model.addColumn("Mã Loại Phòng");
		model.addColumn("Tên Loại Phòng");
		model.addColumn("Số lượng người");
		model.addColumn("Giá");
		model.addColumn("Mô tả");
		model.addColumn("Hình ảnh");
		TableColumn column3 = tblLP.getColumnModel().getColumn(3);
		column3.setPreferredWidth(100);
		TableColumn column4 = tblLP.getColumnModel().getColumn(4);
		column4.setPreferredWidth(400);
		TableColumn column5 = tblLP.getColumnModel().getColumn(5);
		column5.setPreferredWidth(20);
//		Phương thức này để điều chỉnh cột thành định dạng hình
		double price = 5000000.0; // Giá sản phẩm
		DecimalFormat decimalFormat = new DecimalFormat("#,###.## VND"); // Định dạng giá
		String formattedPrice = decimalFormat.format(price);
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
		tblLP.getColumnModel().getColumn(5).setCellRenderer(renderer);
		tblLP.setRowHeight(50);
		tblLP.addMouseListener(new MouseListener() {

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
				int row = tblLP.getSelectedRow();
				txtMaLP.setText(model.getValueAt(row, 0).toString());
				txtTenLP.setText(model.getValueAt(row, 1).toString());
				txtSoLuongNguoi.setText(model.getValueAt(row, 2).toString());
				txtGia.setText(model.getValueAt(row, 3).toString().split(" ")[0].replaceAll(",", ""));
				txtMoTa.setText(model.getValueAt(row, 4).toString());
				String filePath = null;
				try {
					filePath = lp_dao.getLoaiPhongTheoMa(model.getValueAt(row, 0).toString()).getHinhAnh();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				File file = new File(filePath);
				String fileName = file.getName(); // Lấy tên tệp
				if (lblTenHinhDuocChon != null && lblTenHinhDuocChon.getText().isEmpty() == false) {
					b5.remove(lblTenHinhDuocChon);
					b5.remove(kichThuocTen);
				}
				lblTenHinhDuocChon = new JLabel(fileName);
				hinh = lblTenHinhDuocChon.getText();
				chonAnh();
				b5.remove(btnChonAnh);
				b5.remove(horizontalStrut);
				b5.add(lblTenHinhDuocChon);
				int kichThuoc = lblTenHinhDuocChon.getText().length() * (85 / 13);
				kichThuocTen = Box.createHorizontalStrut(kichThuoc);
				b5.add(kichThuocTen);
			}
		});
		JScrollPane sp = new JScrollPane(tblLP, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
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

//	int i ở đây để tránh sự trùng tên(nếu không có trùng thì đừng copy)
	private boolean validData(int i) throws RemoteException {
		String tenLoaiPhong = txtTenLP.getText().trim();
		String soLuongNg = txtSoLuongNguoi.getText().trim();
		String gia = txtGia.getText().trim();
		String moTa = txtMoTa.getText().trim();
		ArrayList<LoaiPhong> ds = lp_dao.getAlltbLoaiPhong();
		boolean trungTen = false;
		for (LoaiPhong loaiPhong : ds) {
			if (tenLoaiPhong.equalsIgnoreCase(loaiPhong.getTenLP())) {
				trungTen = true;
				i+=1;
			}
		}
		if(i==1 || model.getRowCount()==1)
			trungTen = false;

		if (tenLoaiPhong.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để tên trống");
			txtTenLP.requestFocus();
			return false;
		} else if (!tenLoaiPhong.matches("^Phòng(\\s[0-9\\p{L}]*){1,}$")) {
			JOptionPane.showMessageDialog(this, "Tên phòng phải bắt đầu bằng Phòng và đi sau là bất kì kí tự không giới hạn số lượng");
			txtTenLP.requestFocus();
			return false;
		} else if (trungTen) {
			JOptionPane.showMessageDialog(this, "Tên phòng bị trùng. Hãy thay đổi tên khác");
			txtTenLP.requestFocus();
			return false;
		}
		if (soLuongNg.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để số lượng chứa được người trống");
			txtSoLuongNguoi.requestFocus();
			return false;
		}
		try {
			int sl = Integer.parseInt(soLuongNg);
			if (sl <= 0) {
				JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0");
				txtSoLuongNguoi.requestFocus();
				return false;
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(this, "Số lượng có định dạng là số");
			txtSoLuongNguoi.requestFocus();
			return false;
		}
		if (gia.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để giá trống");
			txtGia.requestFocus();
			return false;
		}
		try {
			double price = Double.parseDouble(gia);
			if (price <= 0) {
				JOptionPane.showMessageDialog(this, "Giá phải lớn hơn 0");
				txtGia.requestFocus();
				return false;
			}
		} catch (NumberFormatException e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(this, "Giá có định dạng là số");
			txtGia.requestFocus();
			return false;
		}
		if (moTa.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để mô tả trống");
			txtMoTa.requestFocus();
			return false;
		}
		if (fileDialog == null && hinh == null) {
			JOptionPane.showMessageDialog(this, "Hãy chọn ảnh" + "");
			return false;
		}
		return true;
	}

	private void docDuLieuDataBaseVaoTable() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<LoaiPhong> ds = lp_dao.getAlltbLoaiPhong();
		for (LoaiPhong loaiPhong : ds) {
			double price = loaiPhong.getGia();
			DecimalFormat decimalFormat = new DecimalFormat("#,###.## VND"); // Định dạng giá
			String formattedPrice = decimalFormat.format(price);
			model.addRow(new Object[] { loaiPhong.getMaLP(), loaiPhong.getTenLP(), loaiPhong.getSoLuongNguoi(),
					formattedPrice, loaiPhong.getMoTa(), getIcon(loaiPhong.getHinhAnh(), 60, 50) });
		}
	}

	private void xoaTrang() {
		// TODO Auto-generated method stub
		txtMaLP.setText("");
		txtTenLP.setText("");
		txtSoLuongNguoi.setText("");
		txtGia.setText("");
		txtMoTa.setText("");
		if (lblTenHinhDuocChon.getText().isEmpty() == false) {
			horizontalStrut = Box.createHorizontalStrut(80);
			b5.remove(lblTenHinhDuocChon);
			b5.remove(kichThuocTen);
			b5.add(horizontalStrut);
			btnChonAnh.setPreferredSize(new Dimension(10, 20));
			b5.add(btnChonAnh);
			lblTenHinhDuocChon = new JLabel();
		}
		hinh = null;
		tblLP.clearSelection();
		txtTenLP.requestFocus();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnThem)) {
			try {
				if (validData(1)) {
					String maLP = txtMaLP.getText();
					String tenLP = txtTenLP.getText();
					int soLuong = Integer.parseInt(txtSoLuongNguoi.getText());
					double gia = Double.parseDouble(txtGia.getText());
					DecimalFormat decimalFormat = new DecimalFormat("#,###.## VND"); // Định dạng giá
					String formattedPrice = decimalFormat.format(gia);
					String moTa = txtMoTa.getText();
//				Lưu hình ảnh vào project mỗi khi thêm
					if (fileDialog == null) {
						LoaiPhong lp = new LoaiPhong(maLP, tenLP, soLuong, gia, moTa, "data/images/" + hinh);
						lp_dao.create(lp);
						Object[] row = { lp_dao.getLoaiPhongMoiNhat().getMaLP(), tenLP, txtSoLuongNguoi.getText(),
								formattedPrice, moTa, getIcon("data/images/" + hinh, 60, 50) };
						model.addRow(row);
						xoaTrang();
						PnGiaoDienCapNhatLoaiPhong.this.model.setRowCount(0);
						docDuLieuDataBaseVaoTable();
						JOptionPane.showMessageDialog(this, "Đã thêm thành công");
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

								LoaiPhong lp = new LoaiPhong(maLP, tenLP, soLuong, gia, moTa, destinationFolder);
								lp_dao.create(lp);
								Object[] row = { lp_dao.getLoaiPhongMoiNhat().getMaLP(), tenLP, txtSoLuongNguoi.getText(),
										formattedPrice, moTa, getIcon(destinationFolder, 60, 50) };
								model.addRow(row);
								xoaTrang();
								fileDialog.setFile(null);
								PnGiaoDienCapNhatLoaiPhong.this.model.setRowCount(0);
								docDuLieuDataBaseVaoTable();
								JOptionPane.showMessageDialog(this, "Đã thêm thành công");
							} catch (IOException ex) {
								// Xử lý lỗi nếu có
								ex.printStackTrace();
							}
						}
					}
				}
			} catch (NumberFormatException | HeadlessException | RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(btnXoa)) {
			int row = tblLP.getSelectedRow();
			if (row != -1) {
				String maLP = (String) tblLP.getModel().getValueAt(row, 0);
				int comfirm = JOptionPane.showConfirmDialog(this, "Chắc chắn xóa không?", "Chú ý",
						JOptionPane.YES_NO_OPTION);
				if (comfirm == JOptionPane.YES_OPTION) {
					try {
						lp_dao.delete(maLP);
					} catch (RemoteException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					for (int i = model.getRowCount() - 1; i >= 0; i--) {
						model.removeRow(i);
					}
					try {
						docDuLieuDataBaseVaoTable();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					xoaTrang();
					JOptionPane.showMessageDialog(this, "Đã xóa thành công");
				}
			} else {
				JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần xóa");
			}
		} else if (e.getSource().equals(btnSua)) {
			if (tblLP.getSelectedRow() == -1)
				JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần sửa");
			else {
				try {
					if (validData(0)) {
						String maLP = txtMaLP.getText();
						String tenLP = txtTenLP.getText();
						int soLuong = Integer.parseInt(txtSoLuongNguoi.getText());
						double gia = Double.parseDouble(txtGia.getText());
						String moTa = txtMoTa.getText();
						String hinhAnh = lp_dao.getLoaiPhongTheoMa(maLP).getHinhAnh();
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
						LoaiPhong lpNew = new LoaiPhong(maLP, tenLP, soLuong, gia, moTa, hinhAnh);
						if (lp_dao.update(lpNew)) {
							model.setRowCount(0);
							docDuLieuDataBaseVaoTable();
							xoaTrang();
							JOptionPane.showMessageDialog(this, "Đã cập nhật thành công");
						}
					}
				} catch (NumberFormatException | HeadlessException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (e.getSource().equals(btnXoaTrang)) {
			xoaTrang();
		}
	}
}
