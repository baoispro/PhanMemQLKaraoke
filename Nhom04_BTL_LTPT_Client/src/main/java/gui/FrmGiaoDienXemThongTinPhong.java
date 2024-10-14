package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.KhachHang_DAO;
import dao.LoaiPhong_DAO;
import dao.PhieuDatPhong_DAO;
import entity.PhieuDatPhong;
import entity.Phong;

public class FrmGiaoDienXemThongTinPhong extends JFrame {
	private JLabel lblTenPhong, lblTinhTrang, lblDonGia, lblLoaiPhong;
	private JPanel pnlSubCenter;
	private ArrayList<Phong> dsp;
	private JTable tblTK;
	private DefaultTableModel model;
	private LoaiPhong_DAO lp_dao;
	private PhieuDatPhong_DAO pdp_dao;
	private KhachHang_DAO kh_dao;

	public FrmGiaoDienXemThongTinPhong(ArrayList<Phong> dspht) throws RemoteException, NotBoundException {
		// TODO Auto-generated constructor stub
		super("Xem thông tin phòng");
		Registry registry = LocateRegistry.getRegistry(2024);
		dsp = dspht;
		lp_dao = (LoaiPhong_DAO) registry.lookup("lp_dao");
		pdp_dao = (PhieuDatPhong_DAO) registry.lookup("pdp_dao");
		kh_dao = (KhachHang_DAO) registry.lookup("kh_dao");
		gui();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000, 500);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void gui() throws RemoteException {
		// TODO Auto-generated method stub
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(new BorderLayout());

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
		JLabel lblTitle = new JLabel("Xem thông tin phòng", SwingConstants.CENTER);
		lblTitle.setForeground(Color.white);
		lblTitle.setFont(new Font("Time New Roman", Font.BOLD, 30));
		pnlHeader.add(lblTitle);

		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(new BorderLayout());
		pnlCenter.setBorder(new EmptyBorder(10, 10, 10, 10));
		JPanel pnlSubNorth = new JPanel();
		pnlSubCenter = new JPanel();

		pnlSubNorth.setLayout(new BoxLayout(pnlSubNorth, BoxLayout.X_AXIS));
		lblTenPhong = new JLabel("Tên phòng: " + dsp.get(0).getTenPhong());
		lblLoaiPhong = new JLabel("Loại phòng: " + lp_dao.getLoaiPhongTheoMa(dsp.get(0).getLp().getMaLP()).getTenLP());
		lblDonGia = new JLabel("Giá: " + lp_dao.getLoaiPhongTheoMa(dsp.get(0).getLp().getMaLP()).getGia());
		lblTinhTrang = new JLabel("Tình trạng: " + dsp.get(0).getTinhTrang());

		pnlSubNorth.add(lblTenPhong);
		pnlSubNorth.add(Box.createHorizontalStrut(100));
		pnlSubNorth.add(lblLoaiPhong);
		pnlSubNorth.add(Box.createHorizontalStrut(100));
		pnlSubNorth.add(lblDonGia);
		pnlSubNorth.add(Box.createHorizontalStrut(100));
		pnlSubNorth.add(lblTinhTrang);

		taoBang();
		docDuLieuDataBaseVaoTable();

		pnlCenter.add(pnlSubNorth, BorderLayout.NORTH);
		pnlCenter.add(pnlSubCenter, BorderLayout.CENTER);
		pnlMain.add(pnlHeader, BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		this.add(pnlMain);
	}

	private void taoBang() {
		// TODO Auto-generated method stub
		model = new DefaultTableModel();
		tblTK = new JTable(model);
		model.addColumn("Khách hàng");
		model.addColumn("Ngày nhận phòng");
		model.addColumn("Giờ nhận phòng");
		model.addColumn("Giờ trả phòng");
		model.addColumn("Tình trạng");
		JScrollPane sp = new JScrollPane(tblTK, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		tblTK.setRowHeight(30);
		sp.setPreferredSize(new Dimension(967, 350));
		pnlSubCenter.add(sp);
	}

	private void docDuLieuDataBaseVaoTable() throws RemoteException {
		// TODO Auto-generated method stub
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
		if (!dsp.get(0).getTinhTrang().equalsIgnoreCase("Phòng trống")) {
			ArrayList<PhieuDatPhong> ds = pdp_dao.getDSPhieuDatPhongTheoMaPhong(dsp.get(0).getMaPhong());
			for (PhieuDatPhong pdp : ds) {
					model.addRow(new Object[] {kh_dao.getKhachHangTheoMa(pdp.getKh().getMaKH()).getTenKH(),dateFormat.format(pdp.getNgayNhanPhong()),formatter.format(pdp.getGioNhanPhong()),formatter.format(pdp.getGioTraPhong()),pdp.getTinhTrang()});
			}
		}
	}
}
