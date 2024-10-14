package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

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
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dao.ChiTietHoaDon_DAO;
import dao.ChiTietSuDungDichVu_DAO;
import dao.DichVu_DAO;
import dao.LoaiDichVu_DAO;
import dao.PhieuDatPhong_DAO;
import entity.ChiTietSuDungDichVu;
import entity.DichVu;
import entity.HoaDon;
import entity.KhachHang;
import entity.LoaiDichVu;
import entity.PhieuDatPhong;
import entity.Phong;

public class FrmGiaoDienDatDichVu extends JFrame implements ActionListener {
	private JComboBox cmbLoaiDV, cmbTenDV;
	private DefaultTableModel model;
	private JTable tblDV;
	private JButton btnThem, btnXoa, btnXoaTrang, btnSua, btnLuu;
	private JSpinner spnSoLuong;
	private DichVu_DAO dv_dao;
	private LoaiDichVu_DAO ldv_dao;
	private ArrayList<Phong> dspht;
	private PhieuDatPhong_DAO pdp_dao;
	private ChiTietSuDungDichVu_DAO ctsddv_dao;

	public FrmGiaoDienDatDichVu(ArrayList<Phong> dsp) throws RemoteException, NotBoundException {

		super("Đặt dịch vụ");
		// TODO Auto-generated constructor stub
		Registry registry = LocateRegistry.getRegistry(2024);
		dspht = dsp;
		pdp_dao = (PhieuDatPhong_DAO) registry.lookup("pdp_dao");
		ctsddv_dao = (ChiTietSuDungDichVu_DAO) registry.lookup("ctsddv_dao");
		dv_dao = (DichVu_DAO) registry.lookup("dv_dao");
		ldv_dao = (LoaiDichVu_DAO) registry.lookup("ldv_dao");
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(null);
		// Tạo panel header luôn xuất hiện trong mọi giao diện
		JPanel pnlHeader = new JPanel() {
			@Override
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
		pnlHeader.setBounds(0, 0, 986, 50);
		pnlHeader.setLayout(null);
		JLabel lblTitle = new JLabel("Đặt dịch vụ", SwingConstants.CENTER);
		lblTitle.setBounds(10, 5, 966, 39);
		lblTitle.setForeground(Color.white);
		lblTitle.setFont(new Font("Time New Roman", Font.BOLD, 30));
		pnlHeader.add(lblTitle);

		pnlMain.add(pnlHeader);
		getContentPane().add(pnlMain);

		JPanel pnlChonDV = new JPanel();
		pnlChonDV.setBackground(new Color(255, 255, 255));
		pnlChonDV.setBounds(10, 65, 270, 438);
		pnlMain.add(pnlChonDV);
		pnlChonDV.setLayout(null);

		JLabel lblChonLoaiDV = new JLabel("Chọn loại dịch vụ:");
		lblChonLoaiDV.setFont(new Font("Dialog", Font.BOLD, 12));
		lblChonLoaiDV.setBounds(10, 35, 117, 20);
		pnlChonDV.add(lblChonLoaiDV);

		cmbLoaiDV = new JComboBox();
		cmbLoaiDV.setFont(new Font("Dialog", Font.BOLD, 12));
		cmbLoaiDV.setBounds(10, 65, 250, 30);
		pnlChonDV.add(cmbLoaiDV);
		cmbLoaiDV.addItem("Tất cả");
		ArrayList<LoaiDichVu> dsLoaiDichVu = ldv_dao.getAlltbLoaiDichVu();
		for (LoaiDichVu loaiDichVu : dsLoaiDichVu) {
			cmbLoaiDV.addItem(loaiDichVu.getTenLoaiDichVu());
		}
		cmbLoaiDV.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					String tenLoaiDV = cmbLoaiDV.getSelectedItem().toString().trim();
					try {
						updateComboTenDV(tenLoaiDV);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});

		JLabel lblTenDV = new JLabel("Tên dịch vụ:");
		lblTenDV.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTenDV.setBounds(10, 121, 95, 20);
		pnlChonDV.add(lblTenDV);

		cmbTenDV = new JComboBox();
		cmbTenDV.setFont(new Font("Dialog", Font.BOLD, 12));
		cmbTenDV.setBounds(10, 151, 250, 30);
		pnlChonDV.add(cmbTenDV);
		ArrayList<DichVu> dsdv = dv_dao.getAlltbDichVu();
		if (cmbLoaiDV.getSelectedItem().toString().equalsIgnoreCase("tất cả")) {
			for (DichVu dichVu : dsdv) {
				cmbTenDV.addItem(dichVu.getTenDichVu());
			}
		}

		JLabel lblSoLuong = new JLabel("Số lượng:");
		lblSoLuong.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSoLuong.setBounds(10, 214, 61, 20);
		pnlChonDV.add(lblSoLuong);

		spnSoLuong = new JSpinner();
		spnSoLuong.setFont(new Font("Dialog", Font.BOLD, 12));
		spnSoLuong.setBounds(99, 214, 50, 20);
		pnlChonDV.add(spnSoLuong);

		JPanel pnlTableDanhSachDV = new JPanel();
		pnlTableDanhSachDV.setBounds(290, 65, 686, 353);
		pnlMain.add(pnlTableDanhSachDV);
		model = new DefaultTableModel();
		tblDV = new JTable(model);
		tblDV.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
		model.addColumn("Tên dịch vụ");
		model.addColumn("Số lượng");
		model.addColumn("Đơn vị");
		model.addColumn("Đơn giá");
		model.addColumn("Thành Tiền");
		TableColumn column1 = tblDV.getColumnModel().getColumn(0);
		column1.setPreferredWidth(150);
		TableColumn column2 = tblDV.getColumnModel().getColumn(1);
		column2.setPreferredWidth(30);
		TableColumn column4 = tblDV.getColumnModel().getColumn(4);
		column4.setPreferredWidth(100);
		DefaultTableCellRenderer rendererDV = (DefaultTableCellRenderer) tblDV.getTableHeader().getDefaultRenderer();
		rendererDV.setHorizontalAlignment(SwingConstants.LEFT);
		tblDV.setFont(new Font("Dialog", 0, 12));

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
				cmbTenDV.setSelectedItem(model.getValueAt(row, 0));
				spnSoLuong.setValue(model.getValueAt(row, 1));
			}
		});
		pnlTableDanhSachDV.setLayout(null);
		JScrollPane sp = new JScrollPane(tblDV, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sp.setBounds(0, 0, 686, 353);
		sp.setPreferredSize(new Dimension(670, 340));

		pnlTableDanhSachDV.add(sp);

		btnThem = new JButton("Thêm", getIcon("data/images/icon_btnThem.png", 16, 16));
		btnThem.setBounds(41, 25, 100, 25);
		btnThem.setFont(new Font("Dialog", Font.BOLD, 12));
		btnXoa = new JButton("Xóa", getIcon("data/images/icon_btnXoa.png", 16, 16));
		btnXoa.setBounds(161, 25, 100, 25);
		btnXoa.setFont(new Font("Dialog", Font.BOLD, 12));
		btnSua = new JButton("Sửa", getIcon("data/images/icon_btnLuu.png", 16, 16));
		btnSua.setBounds(280, 25, 100, 25);
		btnSua.setFont(new Font("Dialog", Font.BOLD, 12));
		btnXoaTrang = new JButton("Xóa trắng", getIcon("data/images/icon_btnXoaTrang.png", 16, 16));
		btnXoaTrang.setBounds(400, 25, 119, 25);
		btnXoaTrang.setFont(new Font("Dialog", Font.BOLD, 12));
		btnLuu = new JButton("Lưu", getIcon("data/images/icon_btnDownload.png", 16, 16));
		btnLuu.setBounds(540, 25, 100, 25);

		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		btnSua.addActionListener(this);
		btnLuu.addActionListener(this);

		docDuLieuVaoBang(dsp);

		JPanel pnlButton = new JPanel();
		pnlButton.setBackground(new Color(255, 255, 255));
		pnlButton.setBounds(290, 428, 686, 75);
		pnlButton.setLayout(null);
		pnlButton.add(btnThem);
		pnlButton.add(btnXoa);
		pnlButton.add(btnSua);
		pnlButton.add(btnXoaTrang);
		pnlButton.add(btnLuu);
		pnlMain.add(pnlButton);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000, 550);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void xoaTrang() {
		cmbLoaiDV.setSelectedIndex(0);
		cmbTenDV.setSelectedIndex(0);
		spnSoLuong.setValue(0);
		tblDV.clearSelection();
	}

	private void updateComboTenDV(String tenLoaiDV) throws RemoteException {
		cmbTenDV.removeAllItems();
		ArrayList<DichVu> dsdv = dv_dao.getAlltbDichVu();
		if (tenLoaiDV.equalsIgnoreCase("tất cả")) {
			for (DichVu dichVu : dsdv) {
				cmbTenDV.addItem(dichVu.getTenDichVu());
			}
		} else if (dv_dao.getAllDichVuTheoTenLoaiDV(tenLoaiDV).size() != 0) {
			ArrayList<DichVu> dsdv1 = dv_dao.getAllDichVuTheoTenLoaiDV(tenLoaiDV);
			for (DichVu dichVu : dsdv1) {
				cmbTenDV.addItem(dichVu.getTenDichVu());
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnXoaTrang)) {
			xoaTrang();
		} else if (e.getSource().equals(btnXoa)) {
			int row = tblDV.getSelectedRow();
			if (row != -1) {
				int comfirm = JOptionPane.showConfirmDialog(this, "Chắc chắn xóa không?", "Chú ý",
						JOptionPane.YES_NO_OPTION);
				if (comfirm == JOptionPane.YES_OPTION) {
					model.removeRow(row);
					xoaTrang();
					JOptionPane.showMessageDialog(this, "Đã xóa thành công");
				}
			} else {
				JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần xóa");
			}
		} else if (e.getSource().equals(btnThem)) {
//			Yêu cầu số lượng phải lớn hơn 0
			if (Integer.parseInt(spnSoLuong.getValue().toString()) <= 0) {
				JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0");
			} else {
				DichVu dv = null;
				try {
					dv = dv_dao.getDichVuTheoTen(cmbTenDV.getSelectedItem().toString());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (model.getRowCount() == 0) {
					model.addRow(new Object[] { cmbTenDV.getSelectedItem().toString(), spnSoLuong.getValue(),
							dv.getDonVi(), dv.getDonGia(),
							Double.parseDouble(spnSoLuong.getValue().toString()) * dv.getDonGia() });
				} else {
					for (int i = 0; i < model.getRowCount(); i++) {
						if (model.getValueAt(i, 0).toString().equalsIgnoreCase(dv.getTenDichVu())) {
							JOptionPane.showMessageDialog(null, "Đã có dịch vụ đó vui lòng bấm nút sửa để sửa lại");
							break;
						} else if (i == model.getRowCount() - 1) {
							model.addRow(new Object[] { cmbTenDV.getSelectedItem().toString(), spnSoLuong.getValue(),
									dv.getDonVi(), dv.getDonGia(),
									Double.parseDouble(spnSoLuong.getValue().toString()) * dv.getDonGia() });
							break;
						}
					}
				}

				xoaTrang();
			}
		} else if (e.getSource().equals(btnSua)) {
			if (tblDV.getSelectedRow() == -1)
				JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần sửa");
			else {
				String tenDV = cmbTenDV.getSelectedItem().toString();
				int soLuong = Integer.parseInt(spnSoLuong.getValue().toString());
				if (!tenDV.equalsIgnoreCase(model.getValueAt(tblDV.getSelectedRow(), 0).toString())) {
					JOptionPane.showMessageDialog(null, "Không được thay đổi tên dịch vụ");
				} else {
					DichVu dv = null;
					try {
						dv = dv_dao.getDichVuTheoTen(cmbTenDV.getSelectedItem().toString());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Object[] row = { tenDV, soLuong, dv.getDonGia(),
							Double.parseDouble(spnSoLuong.getValue().toString()) * dv.getDonGia() };
					for (int i = 0; i < model.getRowCount(); i++)
						if (model.getValueAt(i, 0).equals(tenDV))
							for (int j = 1; j <= row.length; j++)
								model.setValueAt(row[j - 1], i, j - 1);
					xoaTrang();
					JOptionPane.showMessageDialog(this, "Đã sửa thành công");
				}
			}
		} else if (e.getSource().equals(btnLuu)) {
			if (model.getRowCount() == 0) {
				JOptionPane.showMessageDialog(null, "Hãy thêm dịch vụ để lưu");
			} else {
				PhieuDatPhong pdp = null;
				try {
					pdp = pdp_dao.getPhieuDatPhongTheoMaPhong(dspht.get(0).getMaPhong());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//					xóa hết chi tiết sử dụng dịch vụ để phục vụ cho việc nếu phòng đó đã đặt dịch vụ trước đó giờ muốn cập nhật lại
				try {
					ctsddv_dao.deleteTheoMaPDP(pdp.getMaPDP());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				for (int i = 0; i < model.getRowCount(); i++) {
					String maDV = null;
					try {
						maDV = dv_dao.getDichVuTheoTen(model.getValueAt(i, 0).toString()).getMaDichVu();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String tenDV = model.getValueAt(i, 0).toString();
					int soLuong = Integer.parseInt(model.getValueAt(i, 1).toString());
					String donVi = model.getValueAt(i, 2).toString();
					double donGia = Double.parseDouble(model.getValueAt(i, 3).toString());
					LoaiDichVu loaiDV = null;
					try {
						loaiDV = dv_dao.getDichVuTheoTen(model.getValueAt(i, 0).toString()).getLoaiDichVu();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					String hinhAnh = null;
					try {
						hinhAnh = dv_dao.getDichVuTheoTen(model.getValueAt(i, 0).toString()).getHinhAnh();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					DichVu dv = null;
					try {
						dv = new DichVu(maDV, tenDV, dv_dao.getDichVuTheoTen(tenDV).getSoLuong(), donVi, donGia,
								loaiDV, hinhAnh);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					ChiTietSuDungDichVu ctsddv = new ChiTietSuDungDichVu(pdp, dv, tenDV, soLuong, donGia, donVi);
					try {
						ctsddv_dao.create(ctsddv);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(null, "Đã lưu thành công");
				this.dispose();
			}
		}
	}

	private ImageIcon getIcon(String path, int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon iconEmployee = new ImageIcon(path);
		Image scaledImage = iconEmployee.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		return scaledIcon;
	}

//	Đọc dữ liệu nếu phòng đó đặt dữ liệu trước đó
	private void docDuLieuVaoBang(ArrayList<Phong> dsp) throws RemoteException {
		PhieuDatPhong pdp = pdp_dao.getPhieuDatPhongTheoMaPhong(dspht.get(0).getMaPhong());
		ArrayList<ChiTietSuDungDichVu> ds = ctsddv_dao.getCTSDDVTheoMa(pdp.getMaPDP());
		if (ds.size() != 0) {
			for (ChiTietSuDungDichVu chiTietSuDungDichVu : ds) {
				model.addRow(new Object[] { chiTietSuDungDichVu.getTenDV(), chiTietSuDungDichVu.getSoLuong(),
						chiTietSuDungDichVu.getDonVi(), chiTietSuDungDichVu.getDonGia(),
						(double) chiTietSuDungDichVu.getSoLuong() * chiTietSuDungDichVu.getDonGia() });
			}
		}
	}

}
