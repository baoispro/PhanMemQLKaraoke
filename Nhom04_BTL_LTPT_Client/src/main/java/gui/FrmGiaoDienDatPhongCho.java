package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import dao.ChiTietHoaDon_DAO;
import dao.ChiTietPhieuDatPhong_DAO;
import dao.HoaDon_DAO;
import dao.KhachHang_DAO;
import dao.LoaiPhong_DAO;
import dao.NhanVien_DAO;
import dao.PhieuDatPhong_DAO;
import dao.Phong_DAO;
import entity.ChiTietHoaDon;
import entity.ChiTietPhieuDatPhong;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.PhieuDatPhong;
import entity.Phong;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

public class FrmGiaoDienDatPhongCho extends JFrame implements ActionListener {
	private JLabel lblNgaySinh, lblGioiTinh, lblSoDienThoai, lblDiaChi, lblTraPhong, lblKH, lblTenKhachHang, lblEmail,
			lblNhanPhong, lblCCCD, lblGioNhanPhong;
	private JTextField txtCCCD, txtDiaChi, txtKH, txtEmail;
	private JDatePickerImpl datePicker, datePickerNgayNhanPhong;
	private JRadioButton radNam, radNu;
	private JTextField txtSoDienThoai;
	private JComboBox cmbGio, cmbPhut, cmbGioTra, cmbPhutTra;
	private JPanel pnlGio, pnlGioTra;
	private JButton btnDatNgay, btnThem, btnKT;
	private KhachHang_DAO kh_dao;
	private NhanVien_DAO nv_dao;
	private Phong_DAO p_dao;
	private PhieuDatPhong_DAO pdp_dao;
	private ChiTietPhieuDatPhong_DAO ctpdp_dao;
	private LoaiPhong_DAO lp_dao;
	private NhanVien nvThucThi;
	private ArrayList<Phong> dsp;
	private Map<JPanel, String> dspdc;
	private PnGiaoDienDatPhong pnl;

	public FrmGiaoDienDatPhongCho(ArrayList<Phong> dspht, NhanVien nv, Map<JPanel, String> danhSachPhongDuocChon,
			PnGiaoDienDatPhong pnlGiaoDienDatPhong) throws RemoteException, NotBoundException {
		super("Đặt phòng chờ");
		// TODO Auto-generated constructor stub
		Registry registry = LocateRegistry.getRegistry(2024);
		pnl = pnlGiaoDienDatPhong;
		dspdc = danhSachPhongDuocChon;
		nvThucThi = nv;
		dsp = dspht;
		lp_dao = (LoaiPhong_DAO) registry.lookup("lp_dao");
		kh_dao = (KhachHang_DAO) registry.lookup("kh_dao");
		nv_dao = (NhanVien_DAO) registry.lookup("nv_dao");
		p_dao = (Phong_DAO) registry.lookup("p_dao");
		pdp_dao = (PhieuDatPhong_DAO) registry.lookup("pdp_dao");
		ctpdp_dao = (ChiTietPhieuDatPhong_DAO) registry.lookup("ctpdp_dao");
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
		JLabel lblTitle = new JLabel("Đặt phòng chờ", SwingConstants.CENTER);
		lblTitle.setForeground(Color.white);
		lblTitle.setFont(new Font("Time New Roman", Font.BOLD, 30));
		pnlHeader.add(lblTitle);

		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(null);

//		Label "Tên phòng"
		String ten = "";
		for (int i = 0; i < dspht.size(); i++) {
			ten += dspht.get(i).getTenPhong();
			if (dspht.size() != 1 && i != dspht.size() - 1)
				ten += ",";
		}
		JLabel lblTenPhong = new JLabel("Tên phòng: " + ten);
		lblTenPhong.setBounds(10, 10, 200, 20);
		pnlCenter.add(lblTenPhong);

		// Label "CCCD"
		lblCCCD = new JLabel("CCCD:");
		lblCCCD.setBounds(10, 160, 100, 20);
		pnlCenter.add(lblCCCD);

		// TextField "CCCD"
		txtCCCD = new JTextField();
		txtCCCD.setBounds(120, 160, 200, 20);
		pnlCenter.add(txtCCCD);

		txtKH = new JTextField();
		txtKH.setBounds(120, 70, 150, 20);
		pnlCenter.add(txtKH);

		// Button "Kiểm tra"
		btnKT = new JButton("Kiểm tra");
		btnKT.setBounds(280, 40, 100, 20);
		pnlCenter.add(btnKT);

		// Label "Ngày sinh"
		lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setBounds(10, 100, 100, 20);
		pnlCenter.add(lblNgaySinh);

		// DatePicker (điều này cần sử dụng một thư viện hoặc tạo riêng)
		// Ví dụ sử dụng JDatePicker
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		// Don't know about the formatter, but there it is...
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(120, 100, 200, 20);
		pnlCenter.add(datePicker);

		// Label "Giới tính"
		lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setBounds(10, 130, 100, 20);
		pnlCenter.add(lblGioiTinh);

		// RadioButton Nam
		radNam = new JRadioButton("Nam");
		radNam.setBounds(120, 130, 60, 20);
		pnlCenter.add(radNam);

		// RadioButton Nữ
		radNu = new JRadioButton("Nữ");
		radNu.setBounds(180, 130, 60, 20);
		pnlCenter.add(radNu);

		// Button Group để quản lý việc chọn giới tính
		ButtonGroup gioiTinhGroup = new ButtonGroup();
		gioiTinhGroup.add(radNam);
		gioiTinhGroup.add(radNu);

		// Label "Số điện thoại"
		lblSoDienThoai = new JLabel("Số điện thoại:");
		lblSoDienThoai.setBounds(10, 40, 100, 20);
		pnlCenter.add(lblSoDienThoai);

		// Textfield "Số điện thoại"
		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setBounds(120, 40, 150, 20);
		pnlCenter.add(txtSoDienThoai);

		// Label "Địa chỉ"
		lblDiaChi = new JLabel("Địa chỉ:");
		lblDiaChi.setBounds(10, 190, 100, 20);
		pnlCenter.add(lblDiaChi);

		// Textfield "Địa chỉ"
		txtDiaChi = new JTextField();
		txtDiaChi.setBounds(120, 190, 200, 20);
		pnlCenter.add(txtDiaChi);

		// Label "Khách hàng"
		lblKH = new JLabel("Khách hàng:");
		lblKH.setBounds(10, 70, 100, 20);
		pnlCenter.add(lblKH);

		lblTenKhachHang = new JLabel("Nguyễn Lê Gia Bảo");
		lblTenKhachHang.setBounds(100, 70, 200, 20);
		lblTenKhachHang.setVisible(false);
		pnlCenter.add(lblTenKhachHang);

		UtilDateModel model1 = new UtilDateModel();
		Properties p1 = new Properties();
		p1.put("text.today", "Today");
		p1.put("text.month", "Month");
		p1.put("text.year", "Year");
		JDatePanelImpl datePanel1 = new JDatePanelImpl(model1, p1);
		// Don't know about the formatter, but there it is...
		datePickerNgayNhanPhong = new JDatePickerImpl(datePanel1, new DateLabelFormatter());
		datePickerNgayNhanPhong.setBounds(160, 160, 200, 20);
		pnlCenter.add(datePickerNgayNhanPhong);

		// Label "Thời gian nhận phòng"
		lblNhanPhong = new JLabel("Ngày nhận phòng:");
		lblNhanPhong.setBounds(10, 160, 150, 20);
		pnlCenter.add(lblNhanPhong);

		// Label "Thời gian nhận phòng"
		lblGioNhanPhong = new JLabel("Giờ nhận phòng:");
		lblGioNhanPhong.setBounds(10, 100, 150, 20);
		pnlCenter.add(lblGioNhanPhong);

		// Panel chứa ComboBox cho giờ và phút
		pnlGio = new JPanel();
		pnlGio.setLayout(null);
		pnlGio.setBounds(160, 100, 150, 20);

		// Label ":" giữa giờ và phút
		JLabel lbHaiCham = new JLabel(":");
		lbHaiCham.setBounds(60, 0, 10, 20);
		pnlGio.add(lbHaiCham);

		// ComboBox cho giờ
		cmbGio = new JComboBox();
		cmbGio.setBounds(0, 0, 50, 20);
		for (int i = 0; i <= 23; i++) {
			String gio = (i <= 9) ? "0" + i : String.valueOf(i);
			cmbGio.addItem(gio);
		}
		pnlGio.add(cmbGio);

		// ComboBox cho phút
		cmbPhut = new JComboBox();
		cmbPhut.setBounds(70, 0, 50, 20);
		for (int j = 0; j <= 59; j++) {
			String phut = (j <= 9) ? "0" + j : String.valueOf(j);
			cmbPhut.addItem(phut);
		}
		pnlGio.add(cmbPhut);

		pnlCenter.add(pnlGio);

		// Label "Thời gian trả phòng"
		lblTraPhong = new JLabel("Giờ trả phòng:");
		lblTraPhong.setBounds(10, 130, 150, 20);
		pnlCenter.add(lblTraPhong);

		// Panel chứa ComboBox cho giờ và phút
		pnlGioTra = new JPanel();
		pnlGioTra.setLayout(null);
		pnlGioTra.setBounds(160, 130, 150, 20);

		// Label ":" giữa giờ và phút
		JLabel lbHaiCham1 = new JLabel(":");
		lbHaiCham1.setBounds(60, 0, 10, 20);
		pnlGioTra.add(lbHaiCham1);

		// ComboBox cho giờ
		cmbGioTra = new JComboBox();
		cmbGioTra.setBounds(0, 0, 50, 20);
		for (int i = 0; i <= 23; i++) {
			String gio = (i <= 9) ? "0" + i : String.valueOf(i);
			cmbGioTra.addItem(gio);
		}
		pnlGioTra.add(cmbGioTra);

		// ComboBox cho phút
		cmbPhutTra = new JComboBox();
		cmbPhutTra.setBounds(70, 0, 50, 20);
		for (int j = 0; j <= 59; j++) {
			String phut = (j <= 9) ? "0" + j : String.valueOf(j);
			cmbPhutTra.addItem(phut);
		}
		pnlGioTra.add(cmbPhutTra);

		pnlCenter.add(pnlGioTra);

		// Label "Email"
		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 220, 100, 20);
		pnlCenter.add(lblEmail);

		// Textfield "Email"
		txtEmail = new JTextField();
		txtEmail.setBounds(120, 220, 200, 20);
		pnlCenter.add(txtEmail);

		btnDatNgay = new JButton("Đặt phòng");
		btnDatNgay.setBounds(280, 190, 100, 30);
		pnlCenter.add(btnDatNgay);

		btnThem = new JButton("Thêm");
		btnThem.setBounds(280, 250, 100, 30);
		pnlCenter.add(btnThem);

		hienAnThemKH(false);

		btnKT.addActionListener(this);
		btnThem.addActionListener(this);
		btnDatNgay.addActionListener(this);
		radNam.setSelected(true);

		pnlMain.add(pnlHeader, BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		this.add(pnlMain);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(400, 370);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void hienAnThemKH(boolean anHien) {
		lblNgaySinh.setVisible(anHien);
		datePicker.setVisible(anHien);
		lblGioiTinh.setVisible(anHien);
		radNam.setVisible(anHien);
		radNu.setVisible(anHien);
		lblCCCD.setVisible(anHien);
		txtCCCD.setVisible(anHien);
		lblDiaChi.setVisible(anHien);
		txtDiaChi.setVisible(anHien);
		txtKH.setVisible(anHien);
		lblEmail.setVisible(anHien);
		txtEmail.setVisible(anHien);

		if (anHien == true) {
			btnDatNgay.setVisible(false);
			btnKT.setVisible(false);
			pnlGio.setVisible(false);
			lblTraPhong.setVisible(false);
			pnlGioTra.setVisible(false);
			lblGioNhanPhong.setVisible(false);
			lblKH.setText("Tên khách hàng:");
			lblNhanPhong.setVisible(false);
			datePickerNgayNhanPhong.setVisible(false);
		} else {
			btnDatNgay.setVisible(true);
			btnKT.setVisible(true);
			pnlGio.setVisible(true);
			lblTraPhong.setVisible(true);
			lblKH.setText("Khách hàng:");
			lblNhanPhong.setVisible(true);
			datePickerNgayNhanPhong.setVisible(true);
			pnlGioTra.setVisible(true);
			lblGioNhanPhong.setVisible(true);
		}
		btnThem.setVisible(anHien);
	}

	private boolean validData() {
		String ten = txtKH.getText().trim();
		String diaChi = txtDiaChi.getText().trim();
		String sdt = txtSoDienThoai.getText();
		String email = txtEmail.getText().trim();
		String cccd = txtCCCD.getText().trim();
		String ngaySinhBangChu = datePicker.getJFormattedTextField().getText();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate now = LocalDate.now();
		if (cccd.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để cccd trống");
			txtCCCD.requestFocus();
			return false;
		} else if (!cccd.matches("^\\d{12}$")) {
			JOptionPane.showMessageDialog(this, "CCCD chỉ có 12 kí tự số");
			txtCCCD.requestFocus();
			return false;
		}
		if (ten.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để tên trống");
			txtKH.requestFocus();
			return false;
		} else if (!ten.matches("^([A-Z\\p{Lu}][a-z\\p{Ll}]*(\\s[A-Z\\p{Lu}][a-z\\p{Ll}]*){0,})$")) {
			JOptionPane.showMessageDialog(this,
					"Tên phải viết hoa với mỗi kí tự đầu tiên và không chứa số hoặc kí tự đặc biệt");
			txtKH.requestFocus();
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
			txtSoDienThoai.requestFocus();
			return false;
		} else if (!sdt.matches("^\\d{10}$")) {
			JOptionPane.showMessageDialog(this, "Số điện thoại chỉ có 10 kí tự số");
			txtSoDienThoai.requestFocus();
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
		return true;
	}

	private boolean checkGioLamViec(LocalTime tgLamViec) {
		if (tgLamViec.isAfter(LocalTime.of(6, 0)) && tgLamViec.isBefore(LocalTime.MAX))
			return true;
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnKT)) {
			String sdt = txtSoDienThoai.getText().trim();
			if (sdt.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Bạn chưa nhập số điện thoại");
				txtSoDienThoai.requestFocus();
			} else
				try {
					if (kh_dao.getKhachHangTheoSDT(sdt) == null) {
						int comfirm = JOptionPane.showConfirmDialog(this, "Bạn muốn thêm khách hàng này không?",
								"Chú ý không có khách hàng này", JOptionPane.YES_NO_OPTION);
						if (comfirm == JOptionPane.YES_OPTION) {
							lblTenKhachHang.setVisible(false);
							hienAnThemKH(true);
						}
					} else {
						lblTenKhachHang.setText(kh_dao.getKhachHangTheoSDT(sdt).getTenKH());
						lblTenKhachHang.setVisible(true);
					}
				} catch (HeadlessException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		} else if (e.getSource().equals(btnThem)) {
			if (validData()) {
				boolean gioiTinh = false;
				String ten = txtKH.getText();
				if (radNam.isSelected()) {
					gioiTinh = true;
				}
				String gioiTinhBangChu = "Nữ";
				if (gioiTinh == true) {
					gioiTinhBangChu = "Nam";
				}
				String diaChi = txtDiaChi.getText();
				String email = txtEmail.getText();
				String cccd = txtCCCD.getText();
				Date ngaySinh = (Date) datePicker.getModel().getValue();

				String sdt = txtSoDienThoai.getText();
				String ns_bangChu = datePicker.getJFormattedTextField().getText();
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
				KhachHang kh = null;
				try {
					kh = new KhachHang(null, ten, gioiTinh, diaChi, email, sdt, cccd, dateFormat.parse(ns_bangChu));
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					if (kh_dao.create(kh)) {
						JOptionPane.showMessageDialog(null, "Đã thêm thành công");
						txtKH.setText("");
						datePicker.getJFormattedTextField().setText("");
						radNam.setSelected(true);
						txtDiaChi.setText("");
						txtSoDienThoai.setText("");
						txtEmail.setText("");
						hienAnThemKH(false);
					}
				} catch (HeadlessException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} else if (e.getSource().equals(btnDatNgay)) {
			String sdt = txtSoDienThoai.getText().trim();
			LocalTime thoiGianNhanPhong = LocalTime.of(Integer.parseInt(cmbGio.getSelectedItem().toString()),
					Integer.parseInt(cmbPhut.getSelectedItem().toString()));
			LocalTime thoiGianTraPhong = LocalTime.of(Integer.parseInt(cmbGioTra.getSelectedItem().toString()),
					Integer.parseInt(cmbPhutTra.getSelectedItem().toString()));
			boolean kt = true;
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			if (sdt.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Bạn chưa nhập số điện thoại");
				txtSoDienThoai.requestFocus();
				kt = false;
			}
			if (lblTenKhachHang.isVisible() == false) {
				JOptionPane.showMessageDialog(null, "Chưa có khách hàng");
				txtCCCD.requestFocus();
				kt = false;
			}
			if (checkGioLamViec(LocalTime.of(Integer.parseInt(cmbGio.getSelectedItem().toString()),
					Integer.parseInt(cmbPhut.getSelectedItem().toString()))) == false) {
				JOptionPane.showMessageDialog(null, "Quán chỉ làm từ 8:00AM - 00:00PM");
				kt = false;
			}
			if (datePickerNgayNhanPhong.getJFormattedTextField().getText() == null) {
				JOptionPane.showMessageDialog(null, "Hãy nhập ngày nhận phòng");
				kt = false;
			}
			try {
				if (LocalTime
						.of(Integer.parseInt(cmbGio.getSelectedItem().toString()),
								Integer.parseInt(cmbPhut.getSelectedItem().toString()))
						.isBefore(LocalTime.now())
						&& dateFormat.parse(datePickerNgayNhanPhong.getJFormattedTextField().getText())
								.equals(LocalDate.now())) {
					JOptionPane.showMessageDialog(null, "Hãy nhập giờ sau giờ hiện tại");
					kt = false;
				}
			} catch (NumberFormatException | HeadlessException | ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				for (PhieuDatPhong pdp : pdp_dao.getDSPhieuDatPhongTheoMaPhong(dsp.get(0).getMaPhong())) {
					try {
						if (pdp.getNgayNhanPhong().compareTo(
								dateFormat.parse(datePickerNgayNhanPhong.getJFormattedTextField().getText())) == 0) {
							if ((pdp.getGioNhanPhong().isBefore(thoiGianNhanPhong))
									&& (pdp.getGioNhanPhong().compareTo(thoiGianNhanPhong)) == 0) {
								JOptionPane.showMessageDialog(null, "Phải đặt trước " + pdp.getGioNhanPhong().toString()
										+ " vì đã có khách đặt nhận vào giờ này");
								kt = false;
								break;
							} else if ((pdp.getGioTraPhong().isAfter(thoiGianNhanPhong))
									&& (pdp.getGioTraPhong().compareTo(thoiGianNhanPhong)) == 0) {
								JOptionPane.showMessageDialog(null, "Phải đặt trước " + pdp.getGioTraPhong().toString()
										+ " vì khách sẽ trả phòng dự kiến vào giờ này");
								kt = false;
								break;
							}
//						Thêm điều kiện phải trả phòng trước giờ nhận phòng của phòng đã đặt trước với thời gian dọn phòng là 10 phút
							else if ((pdp.getGioNhanPhong().minusMinutes(10).isBefore(thoiGianTraPhong))
									&& (pdp.getGioNhanPhong().minusMinutes(10).compareTo(thoiGianTraPhong)) == 0) {
								JOptionPane.showMessageDialog(null, "Phải chọn giờ trả phòng trước "
										+ pdp.getGioNhanPhong().toString() + " vì đã có khách đặt nhận vào giờ này");
								kt = false;
							}
						}
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} catch (HeadlessException | RemoteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			if (kt == true) {
				String ngayNhanPhong = datePickerNgayNhanPhong.getJFormattedTextField().getText();

				for (Phong phong : dsp) {
					if (phong.getTinhTrang().equalsIgnoreCase("Phòng trống")) {
						try {
							try {
								pdp_dao.create(new PhieuDatPhong(null, nvThucThi, kh_dao.getKhachHangTheoSDT(sdt),
										LocalDateTime.now(), dateFormat.parse(ngayNhanPhong),
										LocalTime.of(Integer.parseInt(cmbGio.getSelectedItem().toString()),
												Integer.parseInt(cmbPhut.getSelectedItem().toString())),
										LocalTime.of(Integer.parseInt(cmbGioTra.getSelectedItem().toString()),
												Integer.parseInt(cmbPhutTra.getSelectedItem().toString())),
										"Chờ xác nhận"));
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							ctpdp_dao.create(new ChiTietPhieuDatPhong(phong, pdp_dao.getPhieuDatPhongMoiNhat(), null));
						} catch (RemoteException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							p_dao.capNhatTrangThaiPhong(phong.getMaPhong(), "Phòng chờ");
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else if(phong.getTinhTrang().equalsIgnoreCase("Phòng đang sử dụng")) {
						try {
							try {
								pdp_dao.create(new PhieuDatPhong(null, nvThucThi, kh_dao.getKhachHangTheoSDT(sdt),
										LocalDateTime.now(), dateFormat.parse(ngayNhanPhong),
										LocalTime.of(Integer.parseInt(cmbGio.getSelectedItem().toString()),
												Integer.parseInt(cmbPhut.getSelectedItem().toString())),
										LocalTime.of(Integer.parseInt(cmbGioTra.getSelectedItem().toString()),
												Integer.parseInt(cmbPhutTra.getSelectedItem().toString())),
										"Chờ xác nhận"));
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							ctpdp_dao.create(new ChiTietPhieuDatPhong(phong, pdp_dao.getPhieuDatPhongMoiNhat(), null));
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							p_dao.capNhatTrangThaiPhong(phong.getMaPhong(), "Phòng đang sử dụng và chờ");
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else if(phong.getTinhTrang().equalsIgnoreCase("Phòng chờ")) {
						try {
							try {
								pdp_dao.create(new PhieuDatPhong(null, nvThucThi, kh_dao.getKhachHangTheoSDT(sdt),
										LocalDateTime.now(), dateFormat.parse(ngayNhanPhong),
										LocalTime.of(Integer.parseInt(cmbGio.getSelectedItem().toString()),
												Integer.parseInt(cmbPhut.getSelectedItem().toString())),
										LocalTime.of(Integer.parseInt(cmbGioTra.getSelectedItem().toString()),
												Integer.parseInt(cmbPhutTra.getSelectedItem().toString())),
										"Chờ xác nhận"));
							} catch (RemoteException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} catch (NumberFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						try {
							ctpdp_dao.create(new ChiTietPhieuDatPhong(phong, pdp_dao.getPhieuDatPhongMoiNhat(), null));
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
				}
				JOptionPane.showMessageDialog(null, "Đã đặt phòng thành công");
//				In pdf phiếu đặt phòng
				int comfirm = JOptionPane.showConfirmDialog(this, "Bạn có muốn in phiếu đặt phòng và gửi qua email?",
						"Chú ý", JOptionPane.YES_NO_OPTION);
				if (comfirm == JOptionPane.YES_OPTION) {
					Document document = new Document();
					try {
						PdfWriter.getInstance(document,
								new FileOutputStream("data/phieuDatPhongBanPDF/PhieuDatPhong.pdf"));
						BaseFont unicodeFont = BaseFont.createFont("data/font/font-times-new-roman.ttf",
								BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
						com.itextpdf.text.Font font = new com.itextpdf.text.Font(unicodeFont, 14);

						document.open();
						Paragraph p = new Paragraph("Tên Quán: Karaoke Nice", font);
						document.add(p);

						Paragraph p1 = new Paragraph(
								"Địa chỉ: Số 12 Nguyễn Văn Bảo, Phường 4, Quận Gò Vấp, Thành phố Hồ Chí Minh", font);
						document.add(p1);
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
						LocalDateTime currentDateTime = LocalDateTime.now();
						String formattedDateTime = currentDateTime.format(formatter);
						Paragraph p8 = new Paragraph("Ngày lập phiếu: " + formattedDateTime, font);
						document.add(p8);
						font.setSize(30);
						Paragraph p2 = new Paragraph("Phiếu đặt phòng chờ", font);
						p2.setAlignment(Element.ALIGN_CENTER);
						document.add(p2);
						font.setSize(14);
						Paragraph p3 = new Paragraph(
								"Mã phiếu đặt phòng: " + pdp_dao.getPhieuDatPhongMoiNhat().getMaPDP(), font);
						Paragraph p4 = new Paragraph("Nhân viên lập: " + nvThucThi.getTenNV(), font);
						Paragraph p5 = new Paragraph("Khách hàng: " + kh_dao.getKhachHangTheoSDT(sdt).getTenKH(), font);
						Paragraph p6 = new Paragraph("Email: " + kh_dao.getKhachHangTheoSDT(sdt).getEmail(), font);
						String ten = "";
						for (int i = 0; i < dsp.size(); i++) {
							ten += dsp.get(i).getTenPhong();
							if (dsp.size() != 1 && i != dsp.size() - 1)
								ten += ",";
						}
						Paragraph p7 = new Paragraph("Tên phòng: " + ten, font);
						SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
						DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
						Paragraph p9 = new Paragraph("Ngày giờ nhận phòng dự kiến: "
								+ dateFormatter.format(datePickerNgayNhanPhong.getModel().getValue()) + " "
								+ timeFormatter
										.format(LocalTime.of(Integer.parseInt(cmbGio.getSelectedItem().toString()),
												Integer.parseInt(cmbPhut.getSelectedItem().toString()))),
								font);
						document.add(p3);
						document.add(p4);
						document.add(p5);
						document.add(p6);
						document.add(p7);
						document.add(p9);

						document.close();
					} catch (DocumentException | FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						if (Desktop.isDesktopSupported()) {
							File pdfFile = new File("data/phieuDatPhongBanPDF/PhieuDatPhong.pdf");
							Desktop.getDesktop().open(pdfFile);
						} else {
							System.out.println("Môi trường không hỗ trợ mở tệp PDF.");
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					try {
						try {
							guiEmail("data/phieuDatPhongBanPDF/PhieuDatPhong.pdf", kh_dao.getKhachHangTheoSDT(sdt));
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					} catch (MessagingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				pnl.xoaHetPhong();
				try {
					pnl.docDuLieuVaoBang(p_dao.getAlltbPhong());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.dispose();
			}
		}
	}

	private void guiEmail(String file, KhachHang kh) throws AddressException, MessagingException {
		final String fromEmail = "EmailNeedToChange";
		// Mat khai email cua ban
		final String password = "PasswordNeedToChange";
		// dia chi email nguoi nhan
		final String toEmail = kh.getEmail();
		final String subject = "Thông Báo: Đặt Phòng Đã Hoàn Tất";
		final String body = "Cảm ơn, bạn đã đặt phòng tại quán chúng tôi";

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com"); // SMTP Host
		props.put("mail.smtp.port", "587"); // TLS Port
		props.put("mail.smtp.auth", "true"); // enable authentication
		props.put("mail.smtp.starttls.enable", "true"); // enable
		Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(fromEmail));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
		message.setSubject(subject);
		// Phan 1 gom doan tin nhan
		BodyPart messageBodyPart1 = new MimeBodyPart();
		messageBodyPart1.setText(body);
		// phan 2 chua tap tin txt
		MimeBodyPart messageBodyPart2 = new MimeBodyPart();
		// Duong dan den file cua ban
		String filePath = file;
		DataSource source1 = new FileDataSource(filePath);
		messageBodyPart2.setDataHandler(new DataHandler(source1));
		messageBodyPart2.setFileName(filePath);

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart1);
		multipart.addBodyPart(messageBodyPart2);
		message.setContent(multipart);
		Transport.send(message);
		System.out.println("Gửi mail thanh cong");
	}
}
