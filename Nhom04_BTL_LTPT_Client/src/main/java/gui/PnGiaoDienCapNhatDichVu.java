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
import java.text.ParseException;
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
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import dao.DichVu_DAO;
import dao.LoaiDichVu_DAO;
import entity.DichVu;
import entity.LoaiDichVu;
import eu.hansolo.custom.SteelCheckBox;
import java.awt.Font;

public class PnGiaoDienCapNhatDichVu extends JPanel implements ActionListener {
	private JLabel lblMa, lblTen, lblSoLuong, lblDonVi, lblTenHinhDuocChon, lblDonGia, lblLoaiDV, lblHinhAnh;
	private JTextField txtMa, txtTen, txtSoLuong, txtDonVi, txtDonGia;
	private JComboBox cmbLDV;
	private JButton btnThem, btnXoa, btnSua, btnXoaTrang, btnChonAnh;
	private JPanel pnlNorthBackGround;
	private JTable tblDV;
	private DefaultTableModel model;
	private String hinh = null;
	private DichVu_DAO dv_dao;
	private Box b8;
	private FileDialog fileDialog;
	private Component horizontalStrut, kichThuocTen;
	private LoaiDichVu_DAO ldv_dao;

	public PnGiaoDienCapNhatDichVu() throws RemoteException, NotBoundException {
		// TODO Auto-generated constructor stub
		Registry registry = LocateRegistry.getRegistry(2024);
		dv_dao = (DichVu_DAO) registry.lookup("dv_dao");
		ldv_dao = (LoaiDichVu_DAO) registry.lookup("ldv_dao");
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
		lblMa = new JLabel("Mã dịch vụ:");
		lblMa.setFont(new Font("Dialog", Font.BOLD, 12));
		lblMa.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtMa = new JTextField();
		txtMa.setFont(new Font("Dialog", Font.BOLD, 12));
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
		lblTen = new JLabel("Tên dịch vụ:");
		lblTen.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtTen = new JTextField();
		txtTen.setFont(new Font("Dialog", Font.BOLD, 12));
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
//		Số lượng
		lblSoLuong = new JLabel("Số lượng:");
		lblSoLuong.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSoLuong.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtSoLuong = new JTextField();
		txtSoLuong.setFont(new Font("Dialog", Font.BOLD, 12));
		Box b4 = Box.createVerticalBox();
		Box c2 = Box.createHorizontalBox();
		c2.add(Box.createHorizontalStrut(10));
		b4.add(lblSoLuong);
		b4.add(Box.createVerticalStrut(10));
		c2.add(txtSoLuong);
		c2.add(Box.createHorizontalStrut(10));
		b4.add(c2);
		b4.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b4);
//		Đơn vị
		lblDonVi = new JLabel("Đơn vị:");
		lblDonVi.setFont(new Font("Dialog", Font.BOLD, 12));
		lblDonVi.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtDonVi = new JTextField();
		txtDonVi.setFont(new Font("Dialog", Font.BOLD, 12));
		Box b5 = Box.createVerticalBox();
		Box c3 = Box.createHorizontalBox();
		c3.add(Box.createHorizontalStrut(10));
		b5.add(lblDonVi);
		b5.add(Box.createVerticalStrut(10));
		c3.add(txtDonVi);
		b5.add(c3);
		c3.add(Box.createHorizontalStrut(10));
		b5.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b5);
//		Đơn giá
		lblDonGia = new JLabel("Đơn giá:");
		lblDonGia.setFont(new Font("Dialog", Font.BOLD, 12));
		lblDonGia.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtDonGia = new JTextField();
		txtDonGia.setFont(new Font("Dialog", Font.BOLD, 12));
		Box b6 = Box.createVerticalBox();
		Box c4 = Box.createHorizontalBox();
		c4.add(Box.createHorizontalStrut(10));
		b6.add(lblDonGia);
		b6.add(Box.createVerticalStrut(10));
		c4.add(txtDonGia);
		b6.add(c4);
		c4.add(Box.createHorizontalStrut(10));
		b6.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b6);
//		Combobox loai dv
		lblLoaiDV = new JLabel("Loại dịch vụ:");
		lblLoaiDV.setFont(new Font("Dialog", Font.BOLD, 12));
		cmbLDV = new JComboBox<>();
		cmbLDV.setFont(new Font("Dialog", Font.BOLD, 12));
//		đổ dữ liệu vào combobox
		ArrayList<LoaiDichVu> dsdv = ldv_dao.getAlltbLoaiDichVu();
		for (LoaiDichVu loaiDichVu : dsdv) {
			cmbLDV.addItem(loaiDichVu.getTenLoaiDichVu());
		}

		Box b7 = Box.createHorizontalBox();
		b7.add(Box.createHorizontalStrut(10));
		b7.add(lblLoaiDV);
		b7.add(Box.createHorizontalStrut(10));
		b7.add(cmbLDV);
		b7.add(Box.createHorizontalStrut(10));
		pnlWestBackGround.add(b7);
		pnlWestBackGround.add(Box.createVerticalStrut(10));
//		Hình ảnh
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
		pnlWestBackGround.add(Box.createVerticalStrut(150));

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
		btnThem.setFont(new Font("Dialog", Font.BOLD, 12));
		btnXoa = new JButton("Xóa", getIcon("data/images/icon_btnXoa.png", 16, 16));
		btnXoa.setFont(new Font("Dialog", Font.BOLD, 12));
		btnSua = new JButton("Sửa", getIcon("data/images/icon_btnLuu.png", 16, 16));
		btnSua.setFont(new Font("Dialog", Font.BOLD, 12));
		btnXoaTrang = new JButton("Xóa trắng", getIcon("data/images/icon_btnXoaTrang.png", 16, 16));
		btnXoaTrang.setFont(new Font("Dialog", Font.BOLD, 12));

		pnlSouthBackGround.add(btnThem);
		pnlSouthBackGround.add(btnXoa);
		pnlSouthBackGround.add(btnSua);
		pnlSouthBackGround.add(btnXoaTrang);

		pnlCenter.add(pnlNorthBackGround, BorderLayout.NORTH);
		pnlCenter.add(pnlSouthBackGround, BorderLayout.SOUTH);
		taoBang();

		this.add(pnlWest, BorderLayout.WEST);
		this.add(pnlCenter, BorderLayout.CENTER);
		this.setVisible(true);
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		docDuLieuDataVaoTable();
	}

	private void taoBang() {
		// TODO Auto-generated method stub
		model = new DefaultTableModel();
		tblDV = new JTable(model);
		tblDV.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
		model.addColumn("Mã dịch vụ");
		model.addColumn("Tên dịch vụ");
		model.addColumn("Số lượng");
		model.addColumn("Đơn vị");
		model.addColumn("Đơn giá");
		model.addColumn("Loại dịch vụ");
		model.addColumn("Hình ảnh");
		TableColumn column0 = tblDV.getColumnModel().getColumn(0);
		column0.setPreferredWidth(80);
		TableColumn column1 = tblDV.getColumnModel().getColumn(1);
		column1.setPreferredWidth(200);
		TableColumn column2 = tblDV.getColumnModel().getColumn(2);
		column2.setPreferredWidth(120);
		TableColumn column4 = tblDV.getColumnModel().getColumn(4);
		column4.setPreferredWidth(150);
		TableColumn column5 = tblDV.getColumnModel().getColumn(5);
		column5.setPreferredWidth(150);
		TableColumn column6 = tblDV.getColumnModel().getColumn(6);
		column6.setPreferredWidth(40);
		DefaultTableCellRenderer rendererDV = (DefaultTableCellRenderer) tblDV.getTableHeader().getDefaultRenderer();
		rendererDV.setHorizontalAlignment(SwingConstants.LEFT);
		tblDV.setFont(new Font("Dialog", 0, 12));

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
		tblDV.getColumnModel().getColumn(6).setCellRenderer(renderer);
		tblDV.setRowHeight(50);

		tblDV.addMouseListener(new MouseListener() {

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
				int row = tblDV.getSelectedRow();
				txtMa.setText(model.getValueAt(row, 0).toString());
				txtTen.setText(model.getValueAt(row, 1).toString());
				txtSoLuong.setText(model.getValueAt(row, 2).toString());
				txtDonVi.setText(model.getValueAt(row, 3).toString());
				txtDonGia.setText(model.getValueAt(row, 4).toString());
				cmbLDV.setSelectedItem(model.getValueAt(row, 5));
				String filePath = null;
				try {
					filePath = dv_dao.getDichVuTheoMa(model.getValueAt(row, 0).toString()).getHinhAnh();
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
		JScrollPane sp = new JScrollPane(tblDV, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnXoaTrang)) {
			xoaTrang();
		} else if (e.getSource().equals(btnThem)) {
			if (valiData()) {
				String ma = txtMa.getText();
				String ten = txtTen.getText();
				String soluong = txtSoLuong.getText();
				String dv = txtDonVi.getText();
				String dg = txtDonGia.getText();
				String ldv = cmbLDV.getSelectedItem().toString();
				LoaiDichVu loaiDV = null;
				try {
					loaiDV = new LoaiDichVu(ldv_dao.getLoaiDichVuTheoTen(ldv).getMaLoaiDichVu());
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}

				if (fileDialog == null) {
					DichVu dichvu = null;
					try {
						dichvu = new DichVu(ma, ten, Integer.parseInt(soluong), dv, Double.parseDouble(dg), loaiDV,
								"data/images/" + hinh);
					} catch (NumberFormatException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						dv_dao.create(dichvu);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Object[] row = { dichvu.getMaDichVu(), dichvu.getTenDichVu(), dichvu.getSoLuong(),
							dichvu.getDonVi(), dichvu.getDonGia(), dichvu.getLoaiDichVu().getTenLoaiDichVu(),
							getIcon(dichvu.getHinhAnh(), 60, 50) };
					model.addRow(row);
					xoaTrang();
					model.setRowCount(0);
					try {
						docDuLieuDataVaoTable();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "Đã thêm thành công");
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

							DichVu dichvu = new DichVu(ma, ten, Integer.parseInt(soluong), dv, Double.parseDouble(dg),
									loaiDV, destinationFolder);
							dv_dao.create(dichvu);
							fileDialog.setFile(null);
							Object[] row = { dichvu.getMaDichVu(), dichvu.getTenDichVu(), dichvu.getSoLuong(),
									dichvu.getDonVi(), dichvu.getDonGia(), dichvu.getLoaiDichVu().getTenLoaiDichVu(),
									getIcon(destinationFolder, 60, 50) };
							model.addRow(row);
							xoaTrang();
							model.setRowCount(0);
							docDuLieuDataVaoTable();
							JOptionPane.showMessageDialog(null, "Đã thêm thành công");
						} catch (IOException ex) {
							// Xử lý lỗi nếu có
							ex.printStackTrace();
						}
					}
				}
			}
		} else if (e.getSource().equals(btnXoa)) {
			int row = tblDV.getSelectedRow();
			if (row != -1) {
				String maDV = (String) tblDV.getModel().getValueAt(row, 0);
				int comfirm = JOptionPane.showConfirmDialog(this, "Chắc chắn xóa không?", "Chú ý",
						JOptionPane.YES_NO_OPTION);
				if (comfirm == JOptionPane.YES_OPTION) {
					try {
						if (dv_dao.delete(maDV)) {
							for (int i = model.getRowCount() - 1; i >= 0; i--) {
								model.removeRow(i);
							}
							docDuLieuDataVaoTable();
							xoaTrang();
							JOptionPane.showMessageDialog(this, "Đã xóa thành công");
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
			if (tblDV.getSelectedRow() == -1)
				JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần sửa");
			else {
				String ma = txtMa.getText();
				String ten = txtTen.getText();
				String soluong = txtSoLuong.getText();
				String dv = txtDonVi.getText();
				String dg = txtDonGia.getText();
				String ldv = cmbLDV.getSelectedItem().toString();
				LoaiDichVu loaiDV = null;
				try {
					loaiDV = new LoaiDichVu(ldv_dao.getLoaiDichVuTheoTen(ldv).getMaLoaiDichVu());
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				String hinhAnh = null;
				try {
					hinhAnh = dv_dao.getDichVuTheoMa(ma).getHinhAnh();
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
				DichVu dvNew = null;
				dvNew = new DichVu(ma, ten, Integer.parseInt(soluong), dv, Double.parseDouble(dg), loaiDV, hinhAnh);

				try {
					if (dv_dao.update(dvNew)) {
						model.setRowCount(0);
						docDuLieuDataVaoTable();
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

	private void xoaTrang() {
		txtMa.setText("");
		txtTen.setText("");
		txtSoLuong.setText("");
		txtDonVi.setText("");
		txtDonGia.setText("");
		cmbLDV.setSelectedIndex(0);
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
		tblDV.clearSelection();
		txtTen.requestFocus();
	}

	private boolean valiData() {
		String ten = txtTen.getText().trim();
		String sl = txtSoLuong.getText().trim();
		String donvi = txtDonVi.getText().trim();
		String dongia = txtDonGia.getText().trim();

		if (ten.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để tên trống");
			txtTen.requestFocus();
			return false;
		}

		if (sl.isEmpty()) {
			try {
				int soluong = Integer.parseInt(sl);
				if (soluong < 1) {
					JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0!");
					txtSoLuong.requestFocus();
					return false;
				} else {
					return true;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Không được để số lượng trống");
				txtSoLuong.requestFocus();
				return false;
			}
		}
		if (donvi.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để đơn vị trống");
			txtDonVi.requestFocus();
			return false;
		}
		if (dongia.isEmpty()) {
			try {
				double gia = Double.parseDouble(dongia);
				if (gia < 0) {
					JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn 0!");
					txtDonGia.requestFocus();
					return false;
				} else {
					return true;
				}
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Không được để đơn giá trống");
				txtDonGia.requestFocus();
				return false;
			}
		}
		if (fileDialog == null && hinh == null) {
			JOptionPane.showMessageDialog(this, "Hãy chọn ảnh" + "");
			return false;
		}
		return true;
	}

	private void docDuLieuDataVaoTable() throws RemoteException {
		ArrayList<DichVu> ds = dv_dao.getAlltbDichVu();

		for (DichVu dichvu : ds) {
			model.addRow(new Object[] { dichvu.getMaDichVu(), dichvu.getTenDichVu(), dichvu.getSoLuong(),
					dichvu.getDonVi(), dichvu.getDonGia(),
					ldv_dao.getLoaiDichVuTheoMa(dichvu.getLoaiDichVu().getMaLoaiDichVu()).getTenLoaiDichVu(),
					getIcon(dichvu.getHinhAnh(), 60, 50) });
		}
	}
}
