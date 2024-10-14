
package gui;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import dao.ChiTietHoaDon_DAO;
import dao.ChiTietPhieuDatPhong_DAO;
import dao.ChiTietSuDungDichVuHoaDon_DAO;
import dao.ChiTietSuDungDichVu_DAO;
import dao.DichVu_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.KhuyenMai_DAO;
import dao.LoaiDichVu_DAO;
import dao.LoaiPhong_DAO;
import dao.NhanVien_DAO;
import dao.PhieuDatPhong_DAO;
import dao.Phong_DAO;
import entity.ChiTietHoaDon;
import entity.ChiTietPhieuDatPhong;
import entity.ChiTietSuDungDichVu;
import entity.ChiTietSuDungDichVuHoaDon;
import entity.DichVu;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.LoaiDichVu;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.PhieuDatPhong;
import entity.Phong;

import java.awt.Color;
import java.awt.Desktop;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Image;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.sql.Date;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

import javax.swing.JSpinner;

public class PnGiaoDienLapHoaDon extends JPanel implements ActionListener, MouseListener {
	private DefaultTableModel modelKH, modelDV;
	private JTable tblTTHD, tblHD;
	private JComboBox cmbLoaiDV, cmbTenDV, cmbGioTraPhong, cmbPhutTraPhong;
	private DefaultTableModel modelTTHD, modelHD;
	private JTextField txtTongTien, txtTienThua, txtTienNhan, txtSDT, txtTKTenKH, txtTKPhong;
	private JLabel lblTheoSDT, lblTKTenKH, lblTimKiemTenPhong, lblTongTien, lblTTHoaDon, lblTenKhachHang,
			lblChonKhuyenMai, lblGiaKhuyenMai, lblGTKM, lblChonLoaiDV, lblTenDV, lblTienThua, lblTienNhan,
			lblSoDienThoai, lblSoLuong;
	private JPanel pnlThongTinHoaDon, pnlCTHD, pnlTTCTHoaDon, pnlChonDV, pnlTimKiem, pnlThongtinChiTiet;
	private JCheckBox chkXuatHD;
	private JButton btnThanhToan, btnLamMoi, btnTimKiem, btnThemDV, btnChonKM, btnXoaDV;
	private JButton btnXacNhanTraPhong;
	private HoaDon_DAO hd_dao;
	private ChiTietHoaDon_DAO cthd_dao;
	private ChiTietSuDungDichVu_DAO ctsddv_dao;
	private DichVu_DAO dv_dao;
	private LoaiDichVu_DAO ldv_dao;
	private KhachHang_DAO kh_dao;
	private Phong_DAO phong_dao;
	private KhuyenMai_DAO km_dao;
	private DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###");
	private DecimalFormat decimalFormatVND = new DecimalFormat("###,###,###,###,###,### VNĐ");
	private JSpinner spnSoLuong;
	private JLabel lblTenKM;
	private PhieuDatPhong_DAO pdp_dao;
	private ChiTietPhieuDatPhong_DAO ctpdp_dao;
	private LoaiPhong_DAO lp_dao;
	private ChiTietSuDungDichVuHoaDon_DAO ctsddvhd_dao;
	private NhanVien_DAO nv_dao;

	public PnGiaoDienLapHoaDon() throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(2024);
		hd_dao = (HoaDon_DAO) registry.lookup("hd_dao");
		cthd_dao = (ChiTietHoaDon_DAO) registry.lookup("cthd_dao");
		ctsddv_dao = (ChiTietSuDungDichVu_DAO) registry.lookup("ctsddv_dao");
		dv_dao = (DichVu_DAO) registry.lookup("dv_dao");
		ldv_dao = (LoaiDichVu_DAO) registry.lookup("ldv_dao");
		kh_dao = (KhachHang_DAO) registry.lookup("kh_dao");
		phong_dao = (Phong_DAO) registry.lookup("p_dao");
		km_dao = (KhuyenMai_DAO) registry.lookup("km_dao");
		pdp_dao = (PhieuDatPhong_DAO) registry.lookup("pdp_dao");
		ctpdp_dao = (ChiTietPhieuDatPhong_DAO) registry.lookup("ctpdp_dao");
		lp_dao = (LoaiPhong_DAO) registry.lookup("lp_dao");
		ctsddvhd_dao = (ChiTietSuDungDichVuHoaDon_DAO) registry.lookup("ctsddvhd_dao");
		nv_dao = (NhanVien_DAO) registry.lookup("nv_dao");
		setBackground(new Color(240, 240, 240));
		setLayout(null);

		pnlTimKiem = new JPanel();
		pnlTimKiem.setBounds(10, 0, 657, 105);
		add(pnlTimKiem);
		pnlTimKiem.setLayout(null);

		lblTimKiemTenPhong = new JLabel("Tìm kiếm theo tên phòng:");
		lblTimKiemTenPhong.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTimKiemTenPhong.setBounds(10, 7, 192, 25);
		pnlTimKiem.add(lblTimKiemTenPhong);

		lblTKTenKH = new JLabel("Tìm kiếm theo tên khách hàng:");
		lblTKTenKH.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTKTenKH.setBounds(10, 37, 192, 25);
		pnlTimKiem.add(lblTKTenKH);

		lblTheoSDT = new JLabel("Tìm kiếm SĐT khách hàng:");
		lblTheoSDT.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTheoSDT.setBounds(10, 67, 192, 25);
		pnlTimKiem.add(lblTheoSDT);

		txtTKPhong = new JTextField();
		txtTKPhong.setFont(new Font("Dialog", Font.BOLD, 12));
		txtTKPhong.setBounds(212, 7, 263, 25);
		pnlTimKiem.add(txtTKPhong);
		txtTKPhong.setColumns(10);

		txtTKTenKH = new JTextField();
		txtTKTenKH.setFont(new Font("Dialog", Font.BOLD, 12));
		txtTKTenKH.setColumns(10);
		txtTKTenKH.setBounds(212, 37, 263, 25);
		pnlTimKiem.add(txtTKTenKH);

		txtSDT = new JTextField();
		txtSDT.setFont(new Font("Dialog", Font.BOLD, 12));
		txtSDT.setColumns(10);
		txtSDT.setBounds(212, 67, 263, 25);
		pnlTimKiem.add(txtSDT);

		btnTimKiem = new JButton("Tìm kiếm", getIcon("data/images/icon_btnTK.png", 16, 16));
		btnTimKiem.setFont(new Font("Dialog", Font.BOLD, 12));
		btnTimKiem.setBounds(508, 16, 110, 25);
		pnlTimKiem.add(btnTimKiem);

		btnLamMoi = new JButton("Làm mới", getIcon("data/images/icon_btnXoaTrang.png", 16, 16));
		btnLamMoi.setFont(new Font("Dialog", Font.BOLD, 12));
		btnLamMoi.setBounds(508, 56, 110, 25);
		pnlTimKiem.add(btnLamMoi);

		pnlThongTinHoaDon = new JPanel();
		pnlThongTinHoaDon.setBounds(677, 0, 591, 424);
		add(pnlThongTinHoaDon);
		pnlThongTinHoaDon.setLayout(null);

		lblTTHoaDon = new JLabel("Thông tin hóa đơn");
		lblTTHoaDon.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTTHoaDon.setHorizontalAlignment(SwingConstants.CENTER);
		lblTTHoaDon.setBounds(0, 0, 572, 32);
		pnlThongTinHoaDon.add(lblTTHoaDon);

		pnlCTHD = new JPanel();
		pnlCTHD.setBounds(0, 94, 591, 330);
		pnlCTHD.setBorder(new TitledBorder(null, "Chi tiết hóa đơn"));
		String[] colCTHD = { "STT", "Tên", "Số lượng/Thời gian", "Đơn vị", "Đơn giá", "Thành tiền" };
		modelTTHD = new DefaultTableModel(colCTHD, 0);
		tblTTHD = new JTable(modelTTHD);
		tblTTHD.getColumnModel().getColumn(0).setPreferredWidth(20);
		tblTTHD.getColumnModel().getColumn(1).setPreferredWidth(70);
		tblTTHD.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblTTHD.getColumnModel().getColumn(3).setPreferredWidth(30);
		tblTTHD.getColumnModel().getColumn(4).setPreferredWidth(30);
		tblTTHD.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
		tblTTHD.setFont(new Font("Dialog", 0, 12));
		DefaultTableCellRenderer rendererTTHD = (DefaultTableCellRenderer) tblTTHD.getTableHeader()
				.getDefaultRenderer();
		rendererTTHD.setHorizontalAlignment(SwingConstants.LEFT);
		JScrollPane scrCTHD = new JScrollPane(tblTTHD, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrCTHD.setBounds(10, 20, 561, 290);
		pnlCTHD.add(scrCTHD);
		pnlThongTinHoaDon.add(pnlCTHD);
		pnlCTHD.setLayout(null);

		lblTenKhachHang = new JLabel("Tên khách hàng:");
		lblTenKhachHang.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTenKhachHang.setBounds(21, 49, 249, 20);
		pnlThongTinHoaDon.add(lblTenKhachHang);

		lblSoDienThoai = new JLabel("Số điện thoại:");
		lblSoDienThoai.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSoDienThoai.setBounds(280, 49, 249, 20);
		pnlThongTinHoaDon.add(lblSoDienThoai);

		pnlTTCTHoaDon = new JPanel();
		pnlTTCTHoaDon.setBorder(new TitledBorder(null, "Thông tin các phòng chưa thanh toán"));
		String[] colHD = { "Tên phòng", "Tên khách hàng", "Giờ vào" };
		modelHD = new DefaultTableModel(colHD, 0);
		tblHD = new JTable(modelHD);
		tblHD.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
		tblHD.setFont(new Font("Dialog", 0, 12));
		DefaultTableCellRenderer rendererHD = (DefaultTableCellRenderer) tblHD.getTableHeader().getDefaultRenderer();
		rendererHD.setHorizontalAlignment(SwingConstants.LEFT);
		JScrollPane scrHD = new JScrollPane(tblHD, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrHD.setBounds(10, 20, 637, 165);
		pnlTTCTHoaDon.setBounds(10, 106, 657, 195);
		pnlTTCTHoaDon.setLayout(null);
		pnlTTCTHoaDon.add(scrHD);
		add(pnlTTCTHoaDon);

		pnlChonDV = new JPanel();
		pnlChonDV.setBounds(10, 301, 657, 124);
		pnlChonDV.setBorder(new TitledBorder(null, "Chọn dịch vụ cần thêm vào hóa đơn"));
		add(pnlChonDV);
		pnlChonDV.setLayout(null);

		lblChonLoaiDV = new JLabel("Chọn loại dịch vụ:");
		lblChonLoaiDV.setFont(new Font("Dialog", Font.BOLD, 12));
		lblChonLoaiDV.setBounds(10, 22, 117, 20);
		pnlChonDV.add(lblChonLoaiDV);

		cmbLoaiDV = new JComboBox();
		cmbLoaiDV.setFont(new Font("Dialog", Font.BOLD, 12));
		cmbLoaiDV.setBounds(137, 22, 180, 21);
		pnlChonDV.add(cmbLoaiDV);

		btnThemDV = new JButton("Thêm dịch vụ");
		btnThemDV.setFont(new Font("Dialog", Font.BOLD, 12));
		btnThemDV.setBounds(359, 70, 139, 31);
		pnlChonDV.add(btnThemDV);

		lblTenDV = new JLabel("Tên dịch vụ:");
		lblTenDV.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTenDV.setBounds(10, 75, 95, 20);
		pnlChonDV.add(lblTenDV);

		cmbTenDV = new JComboBox();
		cmbTenDV.setFont(new Font("Dialog", Font.BOLD, 12));
		cmbTenDV.setBounds(138, 74, 179, 21);
		pnlChonDV.add(cmbTenDV);

		lblSoLuong = new JLabel("Số lượng:");
		lblSoLuong.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSoLuong.setBounds(359, 22, 61, 20);
		pnlChonDV.add(lblSoLuong);

		spnSoLuong = new JSpinner();
		spnSoLuong.setFont(new Font("Dialog", Font.BOLD, 12));
		spnSoLuong.setBounds(448, 22, 50, 20);
		pnlChonDV.add(spnSoLuong);

		pnlThongtinChiTiet = new JPanel();
		pnlThongtinChiTiet.setBounds(10, 430, 1258, 124);
		pnlThongtinChiTiet.setBorder(new TitledBorder(null, "Thông tin chi tiết"));
		add(pnlThongtinChiTiet);
		pnlThongtinChiTiet.setLayout(null);

		lblTongTien = new JLabel("Tổng tiền cần thanh toán:");
		lblTongTien.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTongTien.setBounds(645, 13, 172, 30);
		pnlThongtinChiTiet.add(lblTongTien);

		lblTienNhan = new JLabel("Tiền nhận:");
		lblTienNhan.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTienNhan.setBounds(645, 48, 146, 30);
		pnlThongtinChiTiet.add(lblTienNhan);

		lblTienThua = new JLabel("Tiền thừa:");
		lblTienThua.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTienThua.setBounds(645, 83, 146, 30);
		pnlThongtinChiTiet.add(lblTienThua);

		txtTongTien = new JTextField();
		txtTongTien.setEditable(false);
		txtTongTien.setFont(new Font("Dialog", Font.BOLD, 12));
		txtTongTien.setBounds(827, 13, 200, 30);
		pnlThongtinChiTiet.add(txtTongTien);
		txtTongTien.setColumns(10);

		txtTienNhan = new JTextField();
		txtTienNhan.setFont(new Font("Dialog", Font.BOLD, 12));
		txtTienNhan.setColumns(10);
		txtTienNhan.setBounds(827, 48, 200, 30);
		pnlThongtinChiTiet.add(txtTienNhan);

		txtTienThua = new JTextField();
		txtTienThua.setFont(new Font("Dialog", Font.BOLD, 12));
		txtTienThua.setEditable(false);
		txtTienThua.setColumns(10);
		txtTienThua.setBounds(827, 83, 200, 30);
		pnlThongtinChiTiet.add(txtTienThua);

		btnThanhToan = new JButton("Thanh toán");
		btnThanhToan.setFont(new Font("Dialog", Font.BOLD, 12));
		btnThanhToan.setBounds(1060, 28, 154, 30);
		pnlThongtinChiTiet.add(btnThanhToan);

		chkXuatHD = new JCheckBox("Xuất hóa đơn");
		chkXuatHD.setFont(new Font("Dialog", Font.BOLD, 12));
		chkXuatHD.setBounds(1068, 80, 146, 21);
		pnlThongtinChiTiet.add(chkXuatHD);

		btnXacNhanTraPhong = new JButton("Xác nhận trả phòng", null);
		btnXacNhanTraPhong.setFont(new Font("Dialog", Font.BOLD, 12));
		btnXacNhanTraPhong.setBounds(60, 75, 172, 30);
		pnlThongtinChiTiet.add(btnXacNhanTraPhong);

		lblChonKhuyenMai = new JLabel("Khuyến mãi:");
		lblChonKhuyenMai.setFont(new Font("Dialog", Font.BOLD, 12));
		lblChonKhuyenMai.setBounds(303, 15, 116, 30);
		pnlThongtinChiTiet.add(lblChonKhuyenMai);

		btnChonKM = new JButton("Chọn");
		btnChonKM.setFont(new Font("Dialog", Font.BOLD, 12));
		btnChonKM.setBounds(514, 15, 79, 30);
		pnlThongtinChiTiet.add(btnChonKM);

		lblGiaKhuyenMai = new JLabel("Giá trị:");
		lblGiaKhuyenMai.setFont(new Font("Dialog", Font.BOLD, 12));
		lblGiaKhuyenMai.setBounds(303, 58, 116, 30);
		pnlThongtinChiTiet.add(lblGiaKhuyenMai);

		lblGTKM = new JLabel("");
		lblGTKM.setFont(new Font("Dialog", Font.BOLD, 12));
		lblGTKM.setBounds(396, 58, 116, 30);
		pnlThongtinChiTiet.add(lblGTKM);

		lblTenKM = new JLabel("");
		lblTenKM.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTenKM.setBounds(396, 15, 116, 30);
		pnlThongtinChiTiet.add(lblTenKM);

		JLabel lblThoiGianTraPhong = new JLabel("Thời gian trả phòng:");
		lblThoiGianTraPhong.setFont(new Font("Dialog", Font.BOLD, 12));
		lblThoiGianTraPhong.setBounds(24, 25, 137, 30);
		pnlThongtinChiTiet.add(lblThoiGianTraPhong);

		cmbGioTraPhong = new JComboBox();
		cmbGioTraPhong.setEditable(true);
		cmbGioTraPhong.setEnabled(false);
		cmbGioTraPhong.setFont(new Font("Dialog", Font.BOLD, 12));
		cmbGioTraPhong.setBounds(171, 28, 40, 22);
		pnlThongtinChiTiet.add(cmbGioTraPhong);

		JLabel lblhaicham = new JLabel(":");
		lblhaicham.setHorizontalAlignment(SwingConstants.CENTER);
		lblhaicham.setBounds(171, 28, 97, 22);
		pnlThongtinChiTiet.add(lblhaicham);

		cmbPhutTraPhong = new JComboBox();
		cmbPhutTraPhong.setEnabled(false);
		cmbPhutTraPhong.setEditable(true);
		cmbPhutTraPhong.setFont(new Font("Dialog", Font.BOLD, 12));
		cmbPhutTraPhong.setBounds(228, 28, 40, 22);
		pnlThongtinChiTiet.add(cmbPhutTraPhong);
		docDuLieuPhongChuaThanhToan();
		cmbLoaiDV.addItem("Tất cả");

		btnXoaDV = new JButton("Xóa dịch vụ", getIcon("data/images/icon_btnXoa.png", 16, 16));
		btnXoaDV.setFont(new Font("Dialog", Font.BOLD, 12));
		btnXoaDV.setBounds(508, 70, 139, 31);
		pnlChonDV.add(btnXoaDV);
		loadLoaiDichVuVaoCombobox();

		cmbLoaiDV.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				if (e.getStateChange() == ItemEvent.SELECTED) {
					try {
						loadDichVuVaoComboboxTheoLoai((String) cmbLoaiDV.getSelectedItem());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						loadDichVuVaoCombobox();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		loadDichVuVaoCombobox();
		// TODO Auto-generated constructor stub
		this.setVisible(true);
		tblHD.addMouseListener(this);
		btnXacNhanTraPhong.addActionListener(this);
		btnThemDV.addActionListener(this);
		btnThanhToan.addActionListener(this);
		btnTimKiem.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnChonKM.addActionListener(this);
		btnXoaDV.addActionListener(this);
//		Tự động load số tiền thùa
		txtTienNhan.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					hienSoDu();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					hienSoDu();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				try {
					hienSoDu();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnXacNhanTraPhong)) {
			int selectedRow = tblHD.getSelectedRow();
			if (selectedRow != -1) {
				for (int i = 0; i < tblTTHD.getRowCount(); i++) {
					if (modelTTHD.getValueAt(i, 1).toString().contains("Phòng")) {
						Phong phongTheoTTHD = null;
						try {
							phongTheoTTHD = phong_dao.getPhongTheoTen(modelTTHD.getValueAt(i, 1).toString());
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						System.out.println(phongTheoTTHD);
						try {
							LoaiPhong lpTheoTTHD = lp_dao.getLoaiPhongTheoMa(phongTheoTTHD.getLp().getMaLP());
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						PhieuDatPhong pdpTheoTTHD = null;
						try {
							pdpTheoTTHD = pdp_dao.getPhieuDatPhongTheoMaPhong(phongTheoTTHD.getMaPhong());
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						ChiTietPhieuDatPhong ctpdp = null;
						try {
							ctpdp = ctpdp_dao.getChiTietPhieuDatPhongTheoMa(pdpTheoTTHD.getMaPDP());
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if (ctpdp.getThoiGianSuDung() == null) {
							LocalTime thoiGianNhanPhong = pdpTheoTTHD.getGioNhanPhong();
							LocalTime thoiGianTraPhong = LocalTime.now();
							cmbGioTraPhong.setSelectedItem(thoiGianTraPhong.getHour());
							cmbPhutTraPhong.setSelectedItem(thoiGianTraPhong.getMinute());
							if (thoiGianTraPhong.isAfter(thoiGianNhanPhong)) {
								long thoiGianSuDung = ChronoUnit.MINUTES.between(thoiGianNhanPhong, thoiGianTraPhong);
								LocalTime thoiGianSuDungPhong = LocalTime.MIN.plus(thoiGianSuDung, ChronoUnit.MINUTES);
								try {
									ctpdp_dao.capNhatThoiGianSuDungPhong(pdpTheoTTHD.getMaPDP(), thoiGianSuDungPhong);
								} catch (RemoteException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								modelTTHD.setValueAt(String.format("%02d:%02d", thoiGianSuDungPhong.getHour(),
										thoiGianSuDungPhong.getMinute()), i, 2);
								try {
									capNhatTongTien(tinhTongTien());
								} catch (RemoteException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

							} else {
								JOptionPane.showMessageDialog(this, "Thời gian trả phòng phải sau thời gian nhận phòng",
										"Thông báo", JOptionPane.WARNING_MESSAGE);
							}

						} else {
							continue;
						}
					}
				}
				JOptionPane.showMessageDialog(this, "Xác nhận trả phòng thành công!");

			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 hóa đơn muốn xác nhận trả phòng!", "Thông báo",
						JOptionPane.WARNING_MESSAGE);
			}
		} else if (e.getSource().equals(btnThemDV)) {
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
				int soLuong = Integer.parseInt(spnSoLuong.getValue().toString());
				int soLuongConLai = dv.getSoLuong();
				if (soLuong > soLuongConLai) {
					JOptionPane.showMessageDialog(this, "Số lượng dịch vụ không đủ. Số lượng còn lại: " + soLuongConLai,
							"Thông báo", JOptionPane.WARNING_MESSAGE);
				} else {
					try {
						themDichVuMoiVao();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công!");
					cmbLoaiDV.setSelectedIndex(0);
					cmbTenDV.setSelectedIndex(0);
					spnSoLuong.setValue(0);
				}
			}
		} else if (e.getSource().equals(btnXoaDV)) {
			int row = tblTTHD.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng để thanh toán", "Thông báo",
						JOptionPane.WARNING_MESSAGE);
			} else {
				modelTTHD.removeRow(row);
				try {
					capNhatTongTien(tinhTongTien());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		else if (e.getSource().equals(btnThanhToan)) {
			int row = tblHD.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng để thanh toán", "Thông báo",
						JOptionPane.WARNING_MESSAGE);
			} else {
				String tenPhong = tblHD.getValueAt(row, 0).toString();
				Phong phong = null;
				try {
					phong = phong_dao.getPhongTheoTen(tenPhong);
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					LoaiPhong lp = lp_dao.getLoaiPhongTheoMa(phong.getLp().getMaLP());
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				PhieuDatPhong pdp = null;
				try {
					pdp = pdp_dao.getPhieuDatPhongTheoMaPhong(phong.getMaPhong());
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				ChiTietPhieuDatPhong ctpdp = null;
				try {
					ctpdp = ctpdp_dao.getChiTietPhieuDatPhongTheoMa(pdp.getMaPDP());
					System.out.println(ctpdp);
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				// Thông báo yêu cầu xác nhận trả phòng khi thời gian sử dụng là null
				try {
					if (txtTienNhan.getText().equals("")) {
						JOptionPane.showMessageDialog(this, "Chưa nhập số tiền nhận!", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
						txtTienNhan.requestFocus();
					} else if ((Double.parseDouble(txtTienNhan.getText())) < tinhTongTien()) {
						JOptionPane.showMessageDialog(this, "Số tiền nhận không hợp lệ!", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
						txtTienNhan.requestFocus();
					} else {
						KhoiTaoHoaDon();
						modelHD.setRowCount(0);
						modelTTHD.setRowCount(0);
						xoaTrang();
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(this, "Lỗi");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
//
//				} else {
//					JOptionPane.showMessageDialog(this, "Chưa xác nhận thời gian trả phòng!", "Thông báo",
//							JOptionPane.WARNING_MESSAGE);
//
//				}
			}

		} else if (e.getSource().equals(btnTimKiem)) {
			String tenKH = txtTKTenKH.getText();
			String tenPhong = txtTKPhong.getText();
			String sdt = txtSDT.getText();
			if (tenKH.isEmpty() && tenPhong.isEmpty() && sdt.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin cần tìm!", "Thông báo",
						JOptionPane.WARNING_MESSAGE);
			} else {
				if (tenKH.isEmpty() && sdt.isEmpty()) {
					if (tenPhong.matches("Phòng\\s*\\d+")) {
						modelHD.setRowCount(0);
						modelTTHD.setRowCount(0);
						try {
							timKiemHoaDonTheoTenPhong();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(this, "Vui lòng nhập đúng kiểu tìm kiếm là Phòng + số phòng",
								"Thông báo", JOptionPane.WARNING_MESSAGE);
					}
				} else if (tenPhong.isEmpty() && sdt.isEmpty()) {
					modelHD.setRowCount(0);
					modelTTHD.setRowCount(0);
					try {
						timKiemHoaDonTheoTenKhachHang();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (tenPhong.isEmpty() && tenKH.isEmpty()) {
					if (!sdt.matches("^\\d{10}$")) {
						JOptionPane.showMessageDialog(this, "Vui lòng nhập SĐT khách hàng phải là 10 số", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
						txtSDT.requestFocus();
					} else {

						modelHD.setRowCount(0);
						modelTTHD.setRowCount(0);
						try {
							timKiemHoaDonTheoSĐTKH();
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}
		} else if (e.getSource().equals(btnLamMoi))

		{
			try {
				xoaTrang();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(btnChonKM)) {
			if (tblHD.getSelectedRow() != -1) {
				FrmChonKhuyenMai frmKM = null;
				try {
					frmKM = new FrmChonKhuyenMai(this, lblTenKM, lblGTKM, txtTongTien);
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frmKM.setVisible(true);
				txtTienNhan.setText("");
				txtTienNhan.requestFocus();

			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng hóa đơn cần áp dụng mã khuyến mãi.", "Thông báo",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	private void themDichVuMoiVao() throws RemoteException {
		int selectedRow = tblHD.getSelectedRow();
		if (selectedRow != -1) {
			String tenPhong = tblHD.getValueAt(selectedRow, 0).toString();
			Phong phong = phong_dao.getPhongTheoTen(tenPhong);
			PhieuDatPhong pdp = pdp_dao.getPhieuDatPhongTheoMaPhong(phong.getMaPhong());
			DichVu dv = dv_dao.getDichVuTheoTen(cmbTenDV.getSelectedItem().toString());
			int stt = tblTTHD.getRowCount() > 0
					? Integer.parseInt(tblTTHD.getValueAt(tblTTHD.getRowCount() - 1, 0).toString()) + 1
					: 1;
			modelTTHD.addRow(new Object[] { Integer.toString(stt++), cmbTenDV.getSelectedItem().toString(),
					spnSoLuong.getValue(), dv.getDonVi(), dv.getDonGia(),
					decimalFormat.format(Double.parseDouble(spnSoLuong.getValue().toString()) * dv.getDonGia()) });
		}

		capNhatTongTien(tinhTongTien());
	}

	private void KhoiTaoHoaDon() throws SQLException, IOException {
		int selectedRow = tblHD.getSelectedRow();
		if (selectedRow != -1) {
			String tenPhong = tblHD.getValueAt(selectedRow, 0).toString();
			Phong phong = phong_dao.getPhongTheoTen(tenPhong);
			LoaiPhong lp = lp_dao.getLoaiPhongTheoMa(phong.getLp().getMaLP());
			PhieuDatPhong pdp = pdp_dao.getPhieuDatPhongTheoMaPhong(phong.getMaPhong());
			ChiTietPhieuDatPhong ctpdp = ctpdp_dao.getChiTietPhieuDatPhongTheoMa(pdp.getMaPDP());
			NhanVien nv = pdp.getNv();
			NhanVien nvTheoMa = nv_dao.getNhanVienTheoMa(nv.getMaNV());
			String tenNhanVien = nvTheoMa.getTenNV();
			KhachHang KhachHang = pdp.getKh();
			String tenKhachHang = tblHD.getValueAt(selectedRow, 1).toString();
			LocalDate ngayHienTai = LocalDate.now();
			Date ngayLapHD = Date.valueOf(ngayHienTai);
			LocalTime thoiGianTraPhong = pdp.getGioNhanPhong();
			LocalTime thoiGianSuDung;
			if (ctpdp.getThoiGianSuDung() == null) {
				thoiGianSuDung = null;
			} else {

				thoiGianSuDung = ctpdp.getThoiGianSuDung();
			}
			// Chuyển đổi LocalTime thành giây
			long giayGioNhanPhong = thoiGianTraPhong.toSecondOfDay();
			long giayThoiGianSuDung = thoiGianSuDung.toSecondOfDay();
			// Thực hiện phép cộng
			long tongGiay = giayGioNhanPhong + giayThoiGianSuDung;
			// Chuyển đổi lại thành LocalTime
			thoiGianTraPhong = LocalTime.ofSecondOfDay(tongGiay);
			KhuyenMai km;
			if (!lblTenKM.getText().equals("")) {
				String maKM = km_dao.getKhuyenMaiTheoTen(lblTenKM.getText()).getMaKM();
				km = km_dao.getKhuyenMaiTheoMa(maKM);
				km = new KhuyenMai(maKM, km.getTenKM(), km.getDieuKienApDung(), km.getGiaTriKhuyenMai(),
						km.getNgayBatDau(), km.getNgayKetThuc());
			} else {
				km = null;
			}
			int xacNhan = JOptionPane.showConfirmDialog(this, "Xác nhận thanh toán?", "Thông báo",
					JOptionPane.YES_NO_OPTION);
			if (xacNhan == JOptionPane.YES_OPTION) {
				String maHD = hd_dao.taoHoaDonMoi(
						new HoaDon(null, nv, tenNhanVien, tenKhachHang, KhachHang, ngayLapHD, thoiGianTraPhong, km));
				if (maHD != null) {
					HoaDon hdNew = hd_dao.getHoaDonTheoMa(maHD);

					for (int i = 0; i < tblTTHD.getRowCount(); i++) {
						if (modelTTHD.getValueAt(i, 1).toString().contains("Phòng")) {
							Phong phongTheoTTHD = phong_dao.getPhongTheoTen(modelTTHD.getValueAt(i, 1).toString());
							LoaiPhong lpTheoTTHD = lp_dao.getLoaiPhongTheoMa(phongTheoTTHD.getLp().getMaLP());
							PhieuDatPhong pdpTheoTTHD = pdp_dao.getPhieuDatPhongTheoMaPhong(phongTheoTTHD.getMaPhong());
							ChiTietPhieuDatPhong ctpdpTheoTTHD = ctpdp_dao
									.getChiTietPhieuDatPhongTheoMa(pdpTheoTTHD.getMaPDP());
							ChiTietHoaDon cthdtheoTTHD = new ChiTietHoaDon(hdNew, phongTheoTTHD,
									modelTTHD.getValueAt(i, 1).toString(), lpTheoTTHD.getGia(), null,
									ctpdpTheoTTHD.getThoiGianSuDung());
							cthd_dao.create(cthdtheoTTHD);
							phong_dao.capNhatTrangThaiPhong(phongTheoTTHD.getMaPhong());
							pdp_dao.deleteSauKhiThanhToan(ctpdpTheoTTHD.getPdp().getMaPDP());

						} else {
							DichVu dv = dv_dao.getDichVuTheoTen(modelTTHD.getValueAt(i, 1).toString());
							int soLuong = Integer.parseInt(modelTTHD.getValueAt(i, 2).toString());
							String donVi = dv.getDonVi();
							double donGia = dv.getDonGia();
							LoaiDichVu loaiDV = dv_dao.getDichVuTheoTen(dv.getTenDichVu()).getLoaiDichVu();
							String hinhAnh = dv_dao.getDichVuTheoTen(dv.getTenDichVu()).getHinhAnh();
							DichVu dvNew = new DichVu(dv.getMaDichVu(), dv.getTenDichVu(), soLuong, donVi, donGia,
									loaiDV, hinhAnh);
							ChiTietSuDungDichVuHoaDon ctsddvhdNew = new ChiTietSuDungDichVuHoaDon(hdNew, dvNew,
									dv.getTenDichVu(), soLuong, donGia, donVi);
							ctsddvhd_dao.create(ctsddvhdNew);
						}

					}
					capNhapSoLuongTonKhoDichVu();
					JOptionPane.showMessageDialog(this, "Thanh toán " + tenPhong + " thành công");
					xuatPDF(hdNew.getMaHoaDon(), hdNew.getMaHoaDon());
					if (chkXuatHD.isSelected()) {
						int xacNhanIn = JOptionPane.showConfirmDialog(this, "Bạn có muốn xem file", "Thông báo",
								JOptionPane.YES_NO_OPTION);

						if (xacNhanIn == JOptionPane.YES_OPTION) {
							Desktop.getDesktop().open(new File("data\\hoaDon\\" + hdNew.getMaHoaDon() + ".pdf"));
						}
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Tạo hóa đơn thất bại");
			}
		}
	}

	private void docDuLieuPhongChuaThanhToan() throws RemoteException {
		ArrayList<PhieuDatPhong> ds = pdp_dao.getAlltbPhieuDatPhong();
		if (ds.size() != 0) {
			for (PhieuDatPhong pdp : ds) {
				System.out.println(pdp);
				if (pdp.getTinhTrang().equalsIgnoreCase("Đã xác nhận")) {
					KhachHang kh = kh_dao.getKhachHangTheoMa(pdp.getKh().getMaKH());
					ChiTietPhieuDatPhong ctpdp = ctpdp_dao.getChiTietPhieuDatPhongTheoMa(pdp.getMaPDP());
					Phong phong = phong_dao.getPhongTheoMa(ctpdp.getPhong().getMaPhong());
					modelHD.addRow(new Object[] { phong.getTenPhong(), kh.getTenKH(), pdp.getGioNhanPhong() });

				}

			}
		}
	}

	private void docDuLieuChiTietDichVuSD() throws RemoteException {
		int i = tblTTHD.getRowCount() > 0
				? Integer.parseInt(tblTTHD.getValueAt(tblTTHD.getRowCount() - 1, 0).toString()) + 1
				: 1;
		int selectedRow = tblHD.getSelectedRow();
		if (selectedRow != -1) {
			String tenPhong = tblHD.getValueAt(selectedRow, 0).toString();
			Phong phong = phong_dao.getPhongTheoTen(tenPhong);
			LoaiPhong lp = lp_dao.getLoaiPhongTheoMa(phong.getLp().getMaLP());
			PhieuDatPhong pdp = pdp_dao.getPhieuDatPhongTheoMaPhong(phong.getMaPhong());
			ArrayList<ChiTietSuDungDichVu> dsctdv = ctsddv_dao.getCTSDDVTheoMa(pdp.getMaPDP());
			ArrayList<ChiTietPhieuDatPhong> dsctpdp = ctpdp_dao.getALLPhieuDatPhongTheoMa(pdp.getMaPDP());

			for (ChiTietPhieuDatPhong ctphieuDP : dsctpdp) {
				Phong phongCTPDP = phong_dao.getPhongTheoMa(ctphieuDP.getPhong().getMaPhong());
				LoaiPhong loaiphongCTPDP = lp_dao.getLoaiPhongTheoMa(phongCTPDP.getLp().getMaLP());
				if (ctphieuDP.getThoiGianSuDung() != null) {
					double thoiGianSuDungTinhTheoGio = ctphieuDP.getThoiGianSuDung().toSecondOfDay() / 3600.0;
					modelTTHD.addRow(new Object[] { Integer.toString(i++), phongCTPDP.getTenPhong(),
							String.format("%02d:%02d", ctphieuDP.getThoiGianSuDung().getHour(),
									ctphieuDP.getThoiGianSuDung().getMinute()),
							"Giờ", loaiphongCTPDP.getGia(),
							decimalFormat.format(thoiGianSuDungTinhTheoGio * loaiphongCTPDP.getGia()) });
				} else {
					LocalTime thoiGianSuDung = ctphieuDP.getThoiGianSuDung();
					double thoiGianSuDungTinhTheoGio = (thoiGianSuDung != null)
							? thoiGianSuDung.toSecondOfDay() / 3600.0
							: 0.0;
					double thanhTien = thoiGianSuDungTinhTheoGio * lp.getGia();
					String formattedThoiGianSuDung = (thoiGianSuDung != null)
							? String.format("%02d:%02d", thoiGianSuDung.getHour(), thoiGianSuDung.getMinute())
							: "0";
					modelTTHD.addRow(new Object[] { Integer.toString(i++), phongCTPDP.getTenPhong(),
							formattedThoiGianSuDung, "Giờ", loaiphongCTPDP.getGia(), decimalFormat.format(thanhTien) });
				}
			}
			for (ChiTietSuDungDichVu ctsddv : dsctdv) {
				modelTTHD.addRow(
						new Object[] { Integer.toString(i++), ctsddv.getTenDV(), ctsddv.getSoLuong(), ctsddv.getDonVi(),
								ctsddv.getDonGia(), decimalFormat.format(ctsddv.getSoLuong() * ctsddv.getDonGia()) });
			}
		}

	}

	private void loadLoaiDichVuVaoCombobox() throws RemoteException {
		ArrayList<LoaiDichVu> dsdv = ldv_dao.getAlltbLoaiDichVu();
		for (LoaiDichVu loaiDichVu : dsdv) {
			cmbLoaiDV.addItem(loaiDichVu.getTenLoaiDichVu());
		}
	}

	private void loadDichVuVaoCombobox() throws RemoteException {
		ArrayList<DichVu> dsdv = dv_dao.getAlltbDichVu();
		for (DichVu dichVu : dsdv) {
			cmbTenDV.addItem(dichVu.getTenDichVu());
		}
	}

	// Hàm loadDichVuVaoComboboxTheoLoai để cập nhật cmbTenDV dựa trên loại dịch vụ
	private void loadDichVuVaoComboboxTheoLoai(String tenLoaiDichVu) throws RemoteException {
		cmbTenDV.removeAllItems();
		if ("Tất cả".equals(tenLoaiDichVu)) {
			ArrayList<DichVu> dsdv = dv_dao.getAlltbDichVu();
			for (DichVu dv : dsdv) {
				cmbTenDV.addItem(dv.getTenDichVu());
			}
		} else {
			cmbTenDV.removeAllItems();
			LoaiDichVu maLoaiDichVu = ldv_dao.getLoaiDichVuTheoTen(tenLoaiDichVu);
			if (maLoaiDichVu != null) {
				List<DichVu> dsdv = dv_dao.getDichVuTheoLoai(maLoaiDichVu.getMaLoaiDichVu());
				for (DichVu dv : dsdv) {
					cmbTenDV.addItem(dv.getTenDichVu());
				}
			}
		}
	}

	public double tinhTongTien() throws RemoteException {
		double tong = 0;
		int selectedRow = tblHD.getSelectedRow();
		if (selectedRow != -1) {
			String tenPhong = tblHD.getValueAt(selectedRow, 0).toString();
			for (int i = 0; i < tblTTHD.getRowCount(); i++) {
				String loai = modelTTHD.getValueAt(i, 1).toString();
				// Kiểm tra nếu là "Phòng"
				if (loai.contains("Phòng")) {
					Phong phong = phong_dao.getPhongTheoTen(loai);
					ChiTietPhieuDatPhong ctpdp = ctpdp_dao.getChiTietPhieuDatPhongTheoMaPhong(phong.getMaPhong());
					LoaiPhong lp = lp_dao.getLoaiPhongTheoMa(phong.getLp().getMaLP());
					if (ctpdp.getThoiGianSuDung() != null && !ctpdp.getThoiGianSuDung().equals(LocalTime.of(0, 0))) {
						// Nếu thời gian sử dụng khác 00:00, tính như bình thường
						double thoiGianSuDungTinhTheoGio = ctpdp.getThoiGianSuDung().toSecondOfDay() / 3600.0;
						double thanhTien = thoiGianSuDungTinhTheoGio * lp.getGia();
						tong += thanhTien;
					} else {
						tong += 0;

					}

				} else {
					double thanhTienDV = Double.parseDouble(modelTTHD.getValueAt(i, 2).toString())
							* Double.parseDouble(modelTTHD.getValueAt(i, 4).toString());
					tong += thanhTienDV;
				}
			}

			if (lblGTKM.getText().equals("")) {
				tong += 0;
			}

			else {
				KhuyenMai km = km_dao.getKhuyenMaiTheoTen(lblTenKM.getText());
				float giaTriKhuyenMai = km.getGiaTriKhuyenMai();
				double giaTriKhuyenMaiDouble = (double) giaTriKhuyenMai;
				tong = tong - (tong * giaTriKhuyenMaiDouble);
				return tong;

			}
		}
		return tong;
	}

	public void capNhatTongTien(double tongTien) {
		txtTongTien.setText(String.valueOf(decimalFormatVND.format(tongTien)));
	}

	private void hienSoDu() throws RemoteException {
		try {
			double soTienNhan = Double.parseDouble(txtTienNhan.getText());
			double tongTien = tinhTongTien();
			double soTienDu = soTienNhan - tongTien;
			txtTienThua.setText(String.valueOf(decimalFormatVND.format(soTienDu)));
		} catch (NumberFormatException e) {
			// TODO: handle exception
			txtTienThua.setText("");
		}
	}

	private void capNhapSoLuongTonKhoDichVu() throws RemoteException {
		int selectedRow = tblHD.getSelectedRow();
		if (selectedRow != -1) {
			for (int i = 0; i < tblTTHD.getRowCount(); i++) {
				if (modelTTHD.getValueAt(i, 1).toString().contains("Phòng")) {
					continue;
				}
				DichVu dv = dv_dao.getDichVuTheoTen(modelTTHD.getValueAt(i, 1).toString());
				String tenDV = dv.getTenDichVu();
				int soLuong = Integer.parseInt(modelTTHD.getValueAt(i, 2).toString());
				String donVi = dv.getDonVi();
				double donGia = dv.getDonGia();
				LoaiDichVu loaiDV = dv_dao.getDichVuTheoTen(tenDV).getLoaiDichVu();
				String hinhAnh = dv_dao.getDichVuTheoTen(tenDV).getHinhAnh();
				DichVu dvNew = new DichVu(dv.getMaDichVu(), tenDV, soLuong, donVi, donGia, loaiDV, hinhAnh);
				dv_dao.capNhapSoLuongTonKho(dvNew);
			}
		}
	}

	private void timKiemHoaDonTheoTenPhong() throws RemoteException {
		String tenPhong = txtTKPhong.getText();
		Phong phong = phong_dao.getPhongTheoTen(tenPhong);
		ChiTietPhieuDatPhong chitietPDP = ctpdp_dao.getChiTietPhieuDatPhongTheoMaPhong(phong.getMaPhong());

		if (chitietPDP == null) {
			JOptionPane.showMessageDialog(this, "Không có thông tin hóa đơn cho phòng này.", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		PhieuDatPhong pdp = pdp_dao.getPhieuDatPhongTheoMaPhong(chitietPDP.getPhong().getMaPhong());
		KhachHang kh = kh_dao.getKhachHangTheoMa(pdp.getKh().getMaKH());
		if (pdp.getTinhTrang().equals("Đã xác nhận")) {
			modelHD.addRow(new Object[] { phong.getTenPhong(), kh.getTenKH(), pdp.getGioNhanPhong() });

		} else {
			JOptionPane.showMessageDialog(this, "Phòng này đã thanh toán hoặc không có hóa đơn chưa thanh toán.",
					"Thông báo", JOptionPane.INFORMATION_MESSAGE);
			txtTKPhong.requestFocus();
		}

	}

	private void timKiemHoaDonTheoTenKhachHang() throws RemoteException {
		String tenKhachHang = txtTKTenKH.getText();
		KhachHang kh = kh_dao.getKhachHangTheoTen(tenKhachHang);
		ArrayList<PhieuDatPhong> dspdp = pdp_dao.getDSPhieuDatPhongTheoKhachHang(kh.getMaKH());
		boolean kTraTonTaiHD = false;
		boolean ktrTonTaiKH = false;

		for (PhieuDatPhong pdp : dspdp) {
			ChiTietPhieuDatPhong ctpdp = ctpdp_dao.getChiTietPhieuDatPhongTheoMa(pdp.getMaPDP());
			Phong phong = phong_dao.getPhongTheoMa(ctpdp.getPhong().getMaPhong());
			ktrTonTaiKH = true;
			if (pdp.getTinhTrang().equals("Đã xác nhận")) {
				modelHD.addRow(new Object[] { phong.getTenPhong(), kh.getTenKH(), pdp.getGioNhanPhong() });
				kTraTonTaiHD = true;
			}
		}
		if (!ktrTonTaiKH) {
			JOptionPane.showMessageDialog(this, "Không có tên khách hàng này trong danh sách.", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
			txtTKTenKH.requestFocus();
		} else if (!kTraTonTaiHD) {
			JOptionPane.showMessageDialog(this, "Không có hóa đơn chưa thanh toán cho khách hàng này.", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
			txtTKTenKH.requestFocus();
		} else {

			JOptionPane.showMessageDialog(this, "Tìm kiếm theo tên khách thành công!");

		}
	}

	private void timKiemHoaDonTheoSĐTKH() throws RemoteException {
		String sdt = txtSDT.getText();
		KhachHang khachHang = kh_dao.getKhachHangTheoSDT(sdt);
		if (khachHang == null) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng nào có SĐT trên.", "Thông báo",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		ArrayList<PhieuDatPhong> dspdp = pdp_dao.getDSPhieuDatPhongTheoKhachHang(khachHang.getMaKH());
		boolean kiemtratontaiHD = false; // Biến để kiểm tra xem có hóa đơn chưa thanh toán hay không
		for (PhieuDatPhong pdp : dspdp) {
			ChiTietPhieuDatPhong ctpdp = ctpdp_dao.getChiTietPhieuDatPhongTheoMa(pdp.getMaPDP());
			Phong phong = phong_dao.getPhongTheoMa(ctpdp.getPhong().getMaPhong());
			if (pdp.getTinhTrang().equals("Đã xác nhận")) {
				modelHD.addRow(new Object[] { phong.getTenPhong(), khachHang.getTenKH(), pdp.getGioNhanPhong() });
				kiemtratontaiHD = true;
			}
		}
		if (!kiemtratontaiHD) {
			JOptionPane.showMessageDialog(this, "Không có hóa đơn chưa thanh toán cho khách hàng có SĐT này.",
					"Thông báo", JOptionPane.INFORMATION_MESSAGE);
			txtSDT.requestFocus();
		} else {
			JOptionPane.showMessageDialog(this, "Tìm kiếm theo SĐT khách hàng thành công!");
		}
	}

	private void xoaTrang() throws RemoteException {
		txtTKTenKH.setText("");
		txtTKPhong.setText("");
		txtSDT.setText("");
		modelHD.setRowCount(0);
		modelTTHD.setRowCount(0);
		docDuLieuPhongChuaThanhToan();
		cmbLoaiDV.setSelectedIndex(0);
		cmbTenDV.setSelectedIndex(0);
		spnSoLuong.setValue(0);
		txtTienNhan.setText("");
		txtTongTien.setText("");
		txtTienThua.setText("");
		lblTenKM.setText("");
		lblGTKM.setText("");
		lblTenKhachHang.setText("");
		lblSoDienThoai.setText("");
	}

	private void xuatPDF(String path, String maHD) {
		path = "data\\hoaDon\\" + path + ".pdf";
		if (!path.matches("(.)+(\\.pdf)$")) {
			path += ".pdf";
		}
		try {
			com.itextpdf.text.Font font10 = FontFactory.getFont("data/font/SVN-Times New Roman.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10);
			com.itextpdf.text.Font font12 = FontFactory.getFont("data/font/SVN-Times New Roman.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
			com.itextpdf.text.Font font25 = FontFactory.getFont("data/font/SVN-Times New Roman 2 bold.ttf",
					BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 25);
			font25.setStyle(Font.BOLD);

			Document d = new Document();
			PdfWriter.getInstance(d, new FileOutputStream(path));
			d.open();

			Paragraph pTitleAPP = new Paragraph("Karaoke Nice hóa đơn", font25);
			Paragraph pAddress = new Paragraph("12 Nguyễn Văn Bảo, Phường 4, Gò Vấp, Thành phố Hồ Chí Minh", font12);
			pTitleAPP.add(pAddress);

			Paragraph pTitle = new Paragraph("HÓA ĐƠN THANH TOÁN", font25);
			// Định dạng đoạn văn bản thứ nhất
			pTitleAPP.setIndentationLeft(100);
			pTitleAPP.setIndentationRight(80);
			pTitleAPP.setAlignment(Element.ALIGN_CENTER);
			pTitle.setSpacingBefore(30);
			pTitle.setAlignment(Element.ALIGN_CENTER);
			pTitle.setSpacingAfter(20);
			d.add(pTitleAPP);
			d.add(pTitle);

//			đọc dữ liệu vào pdf 

			HoaDon hd = hd_dao.getHoaDonTheoMa(maHD);
			// Thông tin khách hàng
			double tongTienHoaDon = 0;
			float colum[] = { 350, 350 };
			PdfPTable table = new PdfPTable(colum);
			table.getDefaultCell().setBorderWidth(0);
			table.getDefaultCell().setBorderColor(BaseColor.WHITE);
			table.addCell(new Paragraph("Mã HĐ: " + maHD, font12));
			table.addCell(new Paragraph("Ngày lập hóa đơn: " + hd.getNgayLapHD(), font12));
			// row2
			table.addCell(new Paragraph("Khách hàng: " + hd.getTenKH(), font12));
			table.addCell(new Paragraph("Nhân viên: " + hd.getTenNV(), font12));
			// row3
			table.setSpacingAfter(10);
			d.add(table);

			float coulumCTHD[] = { 40, 130, 140, 100, 100, 100 };
			PdfPTable tblCTHD = new PdfPTable(coulumCTHD);
			// Khởi tạo 6 ô header
			PdfPCell header1 = new PdfPCell(new Paragraph("STT", font12));
			PdfPCell header2 = new PdfPCell(new Paragraph("Tên dịch vụ", font12));
			PdfPCell header3 = new PdfPCell(new Paragraph("Số lượng/Thời gian", font12));
			PdfPCell header4 = new PdfPCell(new Paragraph("Đơn vị", font12));
			PdfPCell header5 = new PdfPCell(new Paragraph("Đơn giá", font12));
			PdfPCell header6 = new PdfPCell(new Paragraph("Thành Tiền", font12));
			// Thêm cột ô header vào table
			tblCTHD.addCell(header1);
			tblCTHD.addCell(header2);
			tblCTHD.addCell(header3);
			tblCTHD.addCell(header4);
			tblCTHD.addCell(header5);
			tblCTHD.addCell(header6);
			// Thêm data vào bảng.
			ArrayList<ChiTietHoaDon> dscthd = cthd_dao.getCTHDTheoMaHD(hd.getMaHoaDon());
			int i = 1;
			for (ChiTietHoaDon chiTietHoaDon : dscthd) {
				LocalTime thoiGianSuDung = chiTietHoaDon.getThoiGianSuDung();
				double thoiGianSuDungTinhTheoGio = (thoiGianSuDung != null)
						? (double) thoiGianSuDung.toSecondOfDay() / 3600.0
						: 0.0;
				double thanhTien = thoiGianSuDungTinhTheoGio * chiTietHoaDon.getDonGia();
				String formattedThoiGianSuDung = (thoiGianSuDung != null)
						? String.format("%02d:%02d", thoiGianSuDung.getHour(), thoiGianSuDung.getMinute())
						: "0";
				tongTienHoaDon += thanhTien;
				tblCTHD.addCell(new PdfPCell(new Paragraph(Integer.toString(i++), font12)));
				tblCTHD.addCell(new PdfPCell(new Paragraph(chiTietHoaDon.getTenPhong(), font12)));
				tblCTHD.addCell(new PdfPCell(new Paragraph(formattedThoiGianSuDung, font12)));
				tblCTHD.addCell(new PdfPCell(new Paragraph("Giờ", font12)));
				tblCTHD.addCell(new PdfPCell(new Paragraph(decimalFormat.format(chiTietHoaDon.getDonGia()), font12)));
				tblCTHD.addCell(new PdfPCell(new Paragraph(decimalFormat.format(thanhTien), font12)));
			}
			ArrayList<ChiTietSuDungDichVuHoaDon> dsctsddvhd = ctsddvhd_dao.getCTSDDVHDTheoMaHD(hd.getMaHoaDon());
			for (ChiTietSuDungDichVuHoaDon ctdvsdhd : dsctsddvhd) {
				tblCTHD.addCell(new PdfPCell(new Paragraph(Integer.toString(i++), font12)));
				tblCTHD.addCell(new PdfPCell(new Paragraph(ctdvsdhd.getTenDichVu(), font12)));
				tblCTHD.addCell(new PdfPCell(new Paragraph(Integer.toString(ctdvsdhd.getSoLuong()), font12)));
				tblCTHD.addCell(new PdfPCell(new Paragraph(ctdvsdhd.getDonVi(), font12)));
				tblCTHD.addCell(new PdfPCell(new Paragraph(decimalFormat.format(ctdvsdhd.getDonGia()), font12)));
				double tongTienDV = ctdvsdhd.getDonGia() * ctdvsdhd.getSoLuong();
				tblCTHD.addCell(new PdfPCell(new Paragraph(decimalFormat.format(tongTienDV), font12)));
				tongTienHoaDon += tongTienDV;
			}
			double tongTienChuaGiam = tongTienHoaDon;
			tblCTHD.setSpacingAfter(30);
			d.add(tblCTHD);
			double tienGiamDuoc = 0;
			if (hd.getKm() != null) {
				KhuyenMai km = km_dao.getKhuyenMaiTheoMa(hd.getKm().getMaKM());
				if (km != null) {
					float giaTriKhuyenMai = km.getGiaTriKhuyenMai();
					double giaTriKhuyenMaiDouble = (double) giaTriKhuyenMai;
					tienGiamDuoc = (tongTienHoaDon * giaTriKhuyenMaiDouble);
					tongTienHoaDon = tongTienHoaDon - (tongTienHoaDon * giaTriKhuyenMaiDouble);
				}

			}
			Paragraph pTongTien = new Paragraph("Tổng tiền:" + decimalFormatVND.format(tongTienChuaGiam), font12);
			pTongTien.setIndentationLeft(300);
			pTongTien.setAlignment(Element.ALIGN_LEFT);
			Paragraph pKhuyenMai = new Paragraph("Tiền giảm:" + decimalFormatVND.format(tienGiamDuoc), font12);
			pKhuyenMai.setIndentationLeft(300);
			pKhuyenMai.setAlignment(Element.ALIGN_LEFT);

			Paragraph pTienNhan = new Paragraph(
					"Tiền nhận:" + decimalFormatVND.format(Double.parseDouble(txtTienNhan.getText())), font12);
			pTienNhan.setIndentationLeft(300);
			pTienNhan.setAlignment(Element.ALIGN_LEFT);
			double tienThua = Double.parseDouble(txtTienNhan.getText()) - tongTienHoaDon;
			Paragraph pTienThua = new Paragraph("Tiền thừa:" + decimalFormatVND.format(tienThua), font12);
			pTienThua.setIndentationLeft(300);
			pTienThua.setAlignment(Element.ALIGN_LEFT);

			d.add(pTongTien);
			d.add(pTienNhan);
			d.add(pKhuyenMai);
			d.add(pTienThua);

			Paragraph pLine = new Paragraph(
					"----------------------------------------------------------------------------------------------------------------------------------",
					font12);
			pLine.setSpacingBefore(40);
			pLine.setSpacingAfter(5);
			Paragraph pThanks = new Paragraph("Cảm ơn quý khách, hẹn gặp lại!", font12);
			pThanks.setAlignment(Element.ALIGN_CENTER);

			d.add(pLine);
			d.add(pThanks);
			d.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
		}

	}

	private ImageIcon getIcon(String path, int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon iconEmployee = new ImageIcon(path);
		Image scaledImage = iconEmployee.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		return scaledIcon;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int selectedRow = tblHD.getSelectedRow();
		if (selectedRow != -1) {
			String tenPhong = tblHD.getValueAt(selectedRow, 0).toString();
			Phong phong = null;
			try {
				phong = phong_dao.getPhongTheoTen(tenPhong);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			PhieuDatPhong pdp = null;
			try {
				pdp = pdp_dao.getPhieuDatPhongTheoMaPhong(phong.getMaPhong());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				ChiTietPhieuDatPhong ctpdp = ctpdp_dao.getChiTietPhieuDatPhongTheoMa(pdp.getMaPDP());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			KhachHang kh = null;
			try {
				kh = kh_dao.getKhachHangTheoMa(pdp.getKh().getMaKH());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			lblTenKhachHang.setText("Tên khách hàng: " + kh.getTenKH());
			lblSoDienThoai.setText("Số điện thoại: " + kh.getSoDienThoai());
			cmbGioTraPhong.setSelectedItem("");
			cmbPhutTraPhong.setSelectedItem("");
			boolean flag = false;
			for (int i = 0; i < tblTTHD.getRowCount(); i++) {
				if (modelTTHD.getValueAt(i, 1).toString().contains("Phòng")) {
					if (modelTTHD.getValueAt(i, 1).toString().equals(modelHD.getValueAt(selectedRow, 0))) {
						JOptionPane.showMessageDialog(null, "Bạn đã thêm phòng này rồi!", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
						flag = true;
					}
				}
			}
			if (!flag) {
				try {
					docDuLieuChiTietDichVuSD();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					capNhatTongTien(tinhTongTien());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}