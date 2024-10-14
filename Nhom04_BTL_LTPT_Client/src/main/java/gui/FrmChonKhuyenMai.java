package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import dao.ChiTietHoaDon_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.KhuyenMai_DAO;
import dao.LoaiPhong_DAO;
import dao.NhanVien_DAO;
import dao.Phong_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.NhanVien;
import entity.Phong;

public class FrmChonKhuyenMai extends JFrame implements ActionListener {
	private JTextField txtTimKiem;
	private DefaultTableModel model;
	private JTable table;
	private JButton btnTimKiem, btnLamMoi, btnChonKhuyenMai, btnThoat;
	private KhuyenMai_DAO km_dao;
	private PnGiaoDienLapHoaDon frmLapHoaDon;

	public FrmChonKhuyenMai(final PnGiaoDienLapHoaDon frmLapHoaDon, final JLabel lblTenKM, final JLabel lblgiaTriKM,
			final JTextField txtTong) throws RemoteException, NotBoundException {
		super("Đặt phòng ngay");
		// TODO Auto-generated constructor stub
		Registry registry = LocateRegistry.getRegistry(2024);
		km_dao = (KhuyenMai_DAO) registry.lookup("km_dao");
		this.frmLapHoaDon = frmLapHoaDon;
		JPanel pnlMain = new JPanel();
		pnlMain.setBounds(0, 0, 784, 511);

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
		pnlHeader.setBounds(0, 0, 784, 49);
		JLabel lblTitle = new JLabel("Chọn voucher", SwingConstants.CENTER);
		lblTitle.setForeground(Color.white);
		lblTitle.setFont(new Font("Time New Roman", Font.BOLD, 30));
		pnlHeader.add(lblTitle);

		getContentPane().setLayout(null);
		pnlMain.setLayout(null);

		pnlMain.add(pnlHeader);
		getContentPane().add(pnlMain);

		JPanel pnlCenter = new JPanel();
		pnlCenter.setBounds(0, 49, 784, 410);
		pnlMain.add(pnlCenter);
		pnlCenter.setLayout(null);

		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBounds(10, 11, 764, 40);
		pnlCenter.add(pnlTimKiem);
		pnlTimKiem.setLayout(null);

		JLabel lblTimKiemTheoTen = new JLabel("Tìm kiếm khuyến mãi:");
		lblTimKiemTheoTen.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTimKiemTheoTen.setBounds(31, 0, 138, 30);
		pnlTimKiem.add(lblTimKiemTheoTen);

		txtTimKiem = new JTextField();
		txtTimKiem.setFont(new Font("Dialog", Font.BOLD, 12));
		txtTimKiem.setBounds(188, 0, 219, 30);
		pnlTimKiem.add(txtTimKiem);
		txtTimKiem.setColumns(10);

		btnTimKiem = new JButton("Tìm kiếm", getIcon("data/images/icon_btnTK.png", 16, 16));
		btnTimKiem.setBounds(435, 3, 120, 25);
		pnlTimKiem.add(btnTimKiem);

		btnLamMoi = new JButton("Làm mới", getIcon("data/images/icon_btnXoaTrang.png", 16, 16));
		btnLamMoi.setBounds(604, 3, 120, 25);
		pnlTimKiem.add(btnLamMoi);

		JPanel pnlTable = new JPanel();
		pnlTable.setBounds(10, 55, 764, 344);
		String[] colCTHD = { "Mã KM", "Tên khuyến mãi", "Ngày bắt đầu", "Ngày kết thúc", "Giá trị" };
		model = new DefaultTableModel(colCTHD, 0);
		table = new JTable(model);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(70);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		table.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
		table.setFont(new Font("Dialog", 0, 12));
		DefaultTableCellRenderer rendererTTHD = (DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer();
		rendererTTHD.setHorizontalAlignment(SwingConstants.LEFT);
		pnlTable.setLayout(null);
		JScrollPane scrCTHD = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrCTHD.setBounds(0, 0, 764, 344);
		pnlTable.add(scrCTHD);
		pnlCenter.add(pnlTable);

		btnChonKhuyenMai = new JButton("Chọn", null);
		btnChonKhuyenMai.setBounds(183, 469, 120, 25);
		pnlMain.add(btnChonKhuyenMai);

		btnThoat = new JButton("Thoát", null);
		btnThoat.setBounds(472, 469, 120, 25);
		pnlMain.add(btnThoat);
		docDuLieuVaoTable();
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(800, 550);
		setResizable(false);
		setLocationRelativeTo(null);
//		setVisible(false);
		btnThoat.addActionListener(this);
		btnChonKhuyenMai.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int row = table.getSelectedRow();
				if (row != -1) {
					String tenKM = (String) table.getValueAt(row, 1);
					String giaTriKM = (String) table.getValueAt(row, 4).toString();
					double giaTriSoThuc = Double.parseDouble(giaTriKM.replaceAll("%", "")) / 100.0;
					lblTenKM.setText(tenKM);
					lblgiaTriKM.setText(String.format("%.2f%%", giaTriSoThuc * 100));
					double tongTien = 0;
					try {
						tongTien = frmLapHoaDon.tinhTongTien();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					frmLapHoaDon.capNhatTongTien(tongTien);
					daChonKhuyenMai = true;
					setVisible(false);
				} else {
					JOptionPane.showMessageDialog(FrmChonKhuyenMai.this, "Vui lòng chọn một khuyến mãi.", "Thông báo",
							JOptionPane.WARNING_MESSAGE);
					daChonKhuyenMai = false;
				}
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnThoat)) {
			setVisible(false);
		}

	}

	private boolean daChonKhuyenMai = false;

	public boolean isDaChonKhuyenMai() {
		return daChonKhuyenMai;
	}

	private ImageIcon getIcon(String path, int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon iconEmployee = new ImageIcon(path);
		Image scaledImage = iconEmployee.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		return scaledIcon;
	}

//	public static void main(String[] args) {
//		FrmChonKhuyenMai frm = new FrmChonKhuyenMai(null, null,null);
//		frm.setVisible(true);
//	}

	private void docDuLieuVaoTable() throws RemoteException {
		ArrayList<KhuyenMai> ds = km_dao.getAlltbKhuyenMai();
		for (KhuyenMai khuyenMai : ds) {
			model.addRow(new Object[] { khuyenMai.getMaKM(), khuyenMai.getTenKM(), khuyenMai.getNgayBatDau(),
					khuyenMai.getNgayKetThuc(), String.format("%.0f%%", khuyenMai.getGiaTriKhuyenMai() * 100) });
		}
	}
}
