package gui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

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
import dao.ChiTietSuDungDichVuHoaDon_DAO;
import dao.ChiTietSuDungDichVu_DAO;
import dao.DichVu_DAO;
import dao.HoaDon_DAO;
import dao.KhuyenMai_DAO;
import dao.Phong_DAO;
import entity.ChiTietHoaDon;
import entity.ChiTietSuDungDichVu;
import entity.ChiTietSuDungDichVuHoaDon;
import entity.DichVu;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.Phong;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;

public class PnGiaoDienTimKiemHoaDon extends JPanel implements MouseListener, ActionListener {
	private JTextField txtTimKiemKH, txtNhanVien;
	private DefaultTableModel modelHD, modelCTHD;
	private JTable tblHoaDon, tblCTHD;
	private JPanel pnlHoaDon, pnlCTHD, pnlTimKiem;
	private JButton btnInHoaDon, btnTimKiem, btnLamMoi;
	private JLabel lblTimKiemKH, lblNgayLap, lblTimKiemNhanVien;
	private JScrollPane scrHD, scrCTHD;
	private JDatePickerImpl datePicker;
	private HoaDon_DAO hd_dao;
	private ChiTietHoaDon_DAO cthd_dao;
	private KhuyenMai_DAO km_dao;
	private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	private DecimalFormat decimalFormat = new DecimalFormat("###,###,###,###,###,###");
	private DecimalFormat decimalFormatVND = new DecimalFormat("###,###,###,###,###,### VNĐ");
	private ChiTietSuDungDichVuHoaDon_DAO ctsddvhd_dao;
	private Phong_DAO phong_dao;
	private DichVu_DAO dv_dao;

	public PnGiaoDienTimKiemHoaDon() throws RemoteException, NotBoundException {
		setLayout(null);
		Registry registry = LocateRegistry.getRegistry(2024);
		hd_dao = (HoaDon_DAO) registry.lookup("hd_dao");
		ctsddvhd_dao = (ChiTietSuDungDichVuHoaDon_DAO) registry.lookup("ctsddvhd_dao");
		cthd_dao = (ChiTietHoaDon_DAO) registry.lookup("cthd_dao");
		km_dao = (KhuyenMai_DAO) registry.lookup("km_dao");
		phong_dao = (Phong_DAO) registry.lookup("p_dao");
		dv_dao = (DichVu_DAO) registry.lookup("dv_dao");
		pnlHoaDon = new JPanel();
		pnlHoaDon.setBounds(0, 106, 686, 450);
		pnlHoaDon.setBorder(new TitledBorder(null, "Hóa đơn"));
		add(pnlHoaDon);
		pnlHoaDon.setLayout(null);
//		table hóa đơn
		String[] colHD = { "Mã hóa đơn", "Tên khách hàng", "Tên nhân viên", "Ngày lập hóa đơn", "Thành Tiền" };
		modelHD = new DefaultTableModel(colHD, 0);
		tblHoaDon = new JTable(modelHD);
//		Căn chỉnh header left
		DefaultTableCellRenderer rendererHD = (DefaultTableCellRenderer) tblHoaDon.getTableHeader()
				.getDefaultRenderer();
		rendererHD.setHorizontalAlignment(SwingConstants.LEFT);

		tblHoaDon.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));
		tblHoaDon.setFont(new Font("Dialog", 0, 12));
		tblHoaDon.getColumnModel().getColumn(0).setPreferredWidth(30);
		tblHoaDon.getColumnModel().getColumn(1).setPreferredWidth(70);
		tblHoaDon.getColumnModel().getColumn(2).setPreferredWidth(70);
		tblHoaDon.getColumnModel().getColumn(3).setPreferredWidth(80);
		tblHoaDon.getColumnModel().getColumn(4).setPreferredWidth(50);

		scrHD = new JScrollPane(tblHoaDon, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrHD.setBounds(10, 20, 666, 420);
		pnlHoaDon.setLayout(null);
		pnlHoaDon.add(scrHD);

		pnlCTHD = new JPanel();
		pnlCTHD.setBounds(695, 106, 576, 450);
		pnlCTHD.setBorder(new TitledBorder(null, "Chi tiết hóa đơn"));

//		table chi tiết hóa đơn
		String[] colCTHD = { "STT", "Tên dịch vụ", "Số lượng/Thời gian", "Đơn vị", "Đơn giá", "Thành Tiền" };
		modelCTHD = new DefaultTableModel(colCTHD, 0);
		tblCTHD = new JTable(modelCTHD);
		tblCTHD.getColumnModel().getColumn(0).setPreferredWidth(20);
		tblCTHD.getColumnModel().getColumn(1).setPreferredWidth(70);
		tblCTHD.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblCTHD.getColumnModel().getColumn(3).setPreferredWidth(30);
		tblCTHD.getColumnModel().getColumn(4).setPreferredWidth(40);
		tblCTHD.getColumnModel().getColumn(5).setPreferredWidth(50);
		tblCTHD.setFont(new Font("Dialog", Font.PLAIN, 12));
		tblCTHD.getTableHeader().setFont(new Font("Diago", Font.BOLD, 12));
//		Căn chỉnh header table
		DefaultTableCellRenderer rendererCTHD = (DefaultTableCellRenderer) tblCTHD.getTableHeader()
				.getDefaultRenderer();
		rendererCTHD.setHorizontalAlignment(SwingConstants.LEFT);
		scrCTHD = new JScrollPane(tblCTHD, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrCTHD.setBounds(10, 20, 556, 419);
		pnlCTHD.setLayout(null);
		pnlCTHD.add(scrCTHD);
		add(pnlCTHD);

		pnlTimKiem = new JPanel();
		pnlTimKiem.setBounds(10, 10, 1261, 98);
		add(pnlTimKiem);
		pnlTimKiem.setLayout(null);

		lblTimKiemKH = new JLabel("Tìm kiếm khách hàng:");
		lblTimKiemKH.setBounds(124, 11, 200, 30);
		pnlTimKiem.add(lblTimKiemKH);
		lblTimKiemKH.setFont(new Font("Dialog", Font.BOLD, 12));

		lblNgayLap = new JLabel("Ngày lập hóa đơn:");
		lblNgayLap.setBounds(700, 11, 138, 30);
		pnlTimKiem.add(lblNgayLap);
		lblNgayLap.setFont(new Font("Dialog", Font.BOLD, 12));

		txtTimKiemKH = new JTextField();
		txtTimKiemKH.setBounds(343, 11, 300, 30);
		pnlTimKiem.add(txtTimKiemKH);
		txtTimKiemKH.setColumns(10);
//		input date
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);

		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(848, 11, 237, 30);
		pnlTimKiem.add(datePicker);
		SpringLayout springLayout = (SpringLayout) datePicker.getLayout();
		springLayout.putConstraint(SpringLayout.SOUTH, datePicker.getJFormattedTextField(), 0, SpringLayout.SOUTH,
				datePicker);

		btnTimKiem = new JButton("Tìm kiếm", getIcon("data/images/icon_btnTK.png", 16, 16));
		btnTimKiem.setFont(new Font("Dialog", Font.BOLD, 12));
		btnTimKiem.setBounds(710, 55, 126, 35);
		pnlTimKiem.add(btnTimKiem);

		btnLamMoi = new JButton("Làm mới", getIcon("data/images/icon_btnXoaTrang.png", 16, 16));
		btnLamMoi.setFont(new Font("Dialog", Font.BOLD, 12));
		btnLamMoi.setBounds(858, 55, 126, 35);
		pnlTimKiem.add(btnLamMoi);

		txtNhanVien = new JTextField();
		txtNhanVien.setColumns(10);
		txtNhanVien.setBounds(343, 55, 300, 30);
		pnlTimKiem.add(txtNhanVien);

		lblTimKiemNhanVien = new JLabel("Tìm kiếm nhân viên:");
		lblTimKiemNhanVien.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTimKiemNhanVien.setBounds(124, 55, 200, 30);
		pnlTimKiem.add(lblTimKiemNhanVien);

		// button in lại hóa đơn
		btnInHoaDon = new JButton("In hóa đơn");
		btnInHoaDon.setBounds(1006, 55, 126, 35);
		pnlTimKiem.add(btnInHoaDon);
		btnInHoaDon.setFont(new Font("Dialog", Font.BOLD, 12));

		docDuLieuVaoTableHD();

		tblHoaDon.addMouseListener(this);
		btnTimKiem.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnInHoaDon.addActionListener(this);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
//		 TODO Auto-generated method stub
		if (e.getSource().equals(btnTimKiem)) {
			String tenKH = txtTimKiemKH.getText();
			String ngayLHD = datePicker.getJFormattedTextField().getText();
			String tenNV = txtNhanVien.getText();
			if (tenKH.isEmpty() && tenNV.isEmpty() && ngayLHD.isEmpty()) {
				JOptionPane.showMessageDialog(this, "Vui lòng nhập thông tin cần tìm!", "Thông báo",
						JOptionPane.WARNING_MESSAGE);
			} else {
				if (!tenKH.isEmpty() && tenNV.isEmpty() && ngayLHD.isEmpty() == true) {
					modelHD.setRowCount(0);
					modelCTHD.setRowCount(0);
					try {
						timKiemHoaDonTheoTenKhachHang();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else if (!tenNV.isEmpty() && tenKH.isEmpty() && ngayLHD.isEmpty() == true) {
					modelHD.setRowCount(0);
					modelCTHD.setRowCount(0);
					try {
						timKiemHoaDonTheoTenNhanVien();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				} else if (ngayLHD.isEmpty() == false && tenNV.isEmpty() && tenKH.isEmpty()) {
					modelHD.setRowCount(0);
					modelCTHD.setRowCount(0);
					try {
						timKiemHoaDonTheoNgayLapHD();
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		} else if (e.getSource().equals(btnLamMoi)) {
			try {
				xoaTrang();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(btnInHoaDon)) {
			int row = tblHoaDon.getSelectedRow();
			if (row != -1) {
				String maHD = null;
				try {
					maHD = hd_dao.getHoaDonTheoMa(modelHD.getValueAt(row, 0).toString()).getMaHoaDon();
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					Desktop.getDesktop().open(new File("data\\hoaDon\\" + maHD + ".pdf"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần in lại!", "Thông báo",
						JOptionPane.WARNING_MESSAGE);
			}
		}
	}

	void docDuLieuVaoTableHD() throws RemoteException {
		ArrayList<HoaDon> ds = hd_dao.getALLDanhSachHoaDon();
		for (HoaDon hd : ds) {
			ArrayList<ChiTietHoaDon> dscthd = cthd_dao.getCTHDTheoMaHD(hd.getMaHoaDon());
			ArrayList<ChiTietSuDungDichVuHoaDon> dsctsddvhd = ctsddvhd_dao.getCTSDDVHDTheoMaHD(hd.getMaHoaDon());
			double tongTien = 0;
			for (ChiTietHoaDon cthd : dscthd) {
				LocalTime thoiGianSuDung = cthd.getThoiGianSuDung();
				double thoiGianSuDungTinhTheoGio = (thoiGianSuDung != null) ? thoiGianSuDung.toSecondOfDay() / 3600.0
						: 0.0;
				tongTien += thoiGianSuDungTinhTheoGio * cthd.getDonGia();
			}
			for (ChiTietSuDungDichVuHoaDon ctsddvhd : dsctsddvhd) {
				tongTien += ctsddvhd.getSoLuong() * ctsddvhd.getDonGia();
			}
			modelHD.addRow(new Object[] { hd.getMaHoaDon(), hd.getTenKH(), hd.getTenNV(), sdf.format(hd.getNgayLapHD()),
					decimalFormatVND.format(tongTien) });
		}
	}

	private void docDuLieuCTHDtheoHD() throws RemoteException {
		int i = 1;
		int selectedRow = tblHoaDon.getSelectedRow();
		if (selectedRow != -1) {
			String maHoaDon = (String) tblHoaDon.getValueAt(selectedRow, 0);
			HoaDon hd = hd_dao.getHoaDonTheoMa(maHoaDon);
			ArrayList<ChiTietHoaDon> dscthd = cthd_dao.getCTHDTheoMaHD(hd.getMaHoaDon());
			ArrayList<ChiTietSuDungDichVuHoaDon> dsctsddvhd = ctsddvhd_dao.getCTSDDVHDTheoMaHD(hd.getMaHoaDon());
			for (ChiTietHoaDon cthd : dscthd) {
				Phong phong = phong_dao.getPhongTheoMa(cthd.getP().getMaPhong());
				LocalTime thoiGianSuDung = cthd.getThoiGianSuDung();
				double thoiGianSuDungTinhTheoGio = (thoiGianSuDung != null) ? thoiGianSuDung.toSecondOfDay() / 3600.0
						: 0.0;
				double tongTien = thoiGianSuDungTinhTheoGio * cthd.getDonGia();
				modelCTHD.addRow(new Object[] { Integer.toString(i++), phong.getTenPhong(), cthd.getThoiGianSuDung(),
						cthd.getDonVi(), cthd.getDonGia(), decimalFormatVND.format(tongTien) });
			}

			for (ChiTietSuDungDichVuHoaDon ctsddvhd : dsctsddvhd) {
				DichVu dv = dv_dao.getDichVuTheoMa(ctsddvhd.getDv().getMaDichVu());
				modelCTHD.addRow(new Object[] { Integer.toString(i++), dv.getTenDichVu(), ctsddvhd.getSoLuong(),
						ctsddvhd.getDonVi(), ctsddvhd.getDonGia(),
						decimalFormatVND.format(ctsddvhd.getSoLuong() * ctsddvhd.getDonGia()) });
			}
		}
	}

	private void xoaTrang() throws RemoteException {
		txtNhanVien.setText("");
		txtTimKiemKH.setText("");
		modelHD.setRowCount(0);
		modelCTHD.setRowCount(0);
		docDuLieuVaoTableHD();
		datePicker.getJFormattedTextField().setText("");
	}

	private void timKiemHoaDonTheoTenKhachHang() throws RemoteException {
		String tenKhachHang = txtTimKiemKH.getText();
		ArrayList<HoaDon> dshd = hd_dao.getHoaDonTheoTenKH(tenKhachHang);
		boolean ktTonTaiHD = false;
		for (HoaDon hd : dshd) {
			ArrayList<ChiTietHoaDon> dscthd = cthd_dao.getCTHDTheoMaHD(hd.getMaHoaDon());
			ArrayList<ChiTietSuDungDichVuHoaDon> dsctsddvhd = ctsddvhd_dao.getCTSDDVHDTheoMaHD(hd.getMaHoaDon());
			double tongTien = 0;
			for (ChiTietHoaDon cthd : dscthd) {
				LocalTime thoiGianSuDung = cthd.getThoiGianSuDung();
				double thoiGianSuDungTinhTheoGio = (thoiGianSuDung != null) ? thoiGianSuDung.toSecondOfDay() / 3600.0
						: 0.0;
				tongTien += thoiGianSuDungTinhTheoGio * cthd.getDonGia();
			}
			for (ChiTietSuDungDichVuHoaDon ctsddvhd : dsctsddvhd) {
				tongTien += ctsddvhd.getSoLuong() * ctsddvhd.getDonGia();
			}
			ktTonTaiHD = true;
			modelHD.addRow(new Object[] { hd.getMaHoaDon(), hd.getTenKH(), hd.getTenNV(), sdf.format(hd.getNgayLapHD()),
					decimalFormatVND.format(tongTien) });
		}
		if (!ktTonTaiHD) {
			JOptionPane.showMessageDialog(this, "Không có hóa đơn của khách hàng này.", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
			txtTimKiemKH.requestFocus();
		} else {
			JOptionPane.showMessageDialog(this, "Đã tìm kiếm thành công theo tên khách hàng!");
		}
	}

	private void timKiemHoaDonTheoTenNhanVien() throws RemoteException {
		String tenNV = txtNhanVien.getText();
		ArrayList<HoaDon> dshd = hd_dao.getHoaDonTheoTenNV(tenNV);
		boolean ktTonTaiHD = false;
		for (HoaDon hd : dshd) {
			ArrayList<ChiTietHoaDon> dscthd = cthd_dao.getCTHDTheoMaHD(hd.getMaHoaDon());
			ArrayList<ChiTietSuDungDichVuHoaDon> dsctsddvhd = ctsddvhd_dao.getCTSDDVHDTheoMaHD(hd.getMaHoaDon());
			double tongTien = 0;
			for (ChiTietHoaDon cthd : dscthd) {
				LocalTime thoiGianSuDung = cthd.getThoiGianSuDung();
				double thoiGianSuDungTinhTheoGio = (thoiGianSuDung != null) ? thoiGianSuDung.toSecondOfDay() / 3600.0
						: 0.0;
				tongTien += thoiGianSuDungTinhTheoGio * cthd.getDonGia();
			}
			for (ChiTietSuDungDichVuHoaDon ctsddvhd : dsctsddvhd) {
				tongTien += ctsddvhd.getSoLuong() * ctsddvhd.getDonGia();
			}
			ktTonTaiHD = true;
			modelHD.addRow(new Object[] { hd.getMaHoaDon(), hd.getTenKH(), hd.getTenNV(), sdf.format(hd.getNgayLapHD()),
					decimalFormatVND.format(tongTien) });
		}
		if (!ktTonTaiHD) {
			JOptionPane.showMessageDialog(this, "Không có hóa đơn của nhân viên này.", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
			txtNhanVien.requestFocus();
		} else {
			JOptionPane.showMessageDialog(this, "Đã tìm kiếm thành công theo nhân viên!");
		}
	}

	private void timKiemHoaDonTheoNgayLapHD() throws ParseException, RemoteException {
		String ngayLHD = datePicker.getJFormattedTextField().getText();
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date ngayLap = dateFormat.parse(ngayLHD);
		boolean ktTonTaiHD = false;
		ArrayList<HoaDon> dshd = hd_dao.getHoaDonTheoNgayLapHD(ngayLap);
		for (HoaDon hd : dshd) {
			ArrayList<ChiTietHoaDon> dscthd = cthd_dao.getCTHDTheoMaHD(hd.getMaHoaDon());
			ArrayList<ChiTietSuDungDichVuHoaDon> dsctsddvhd = ctsddvhd_dao.getCTSDDVHDTheoMaHD(hd.getMaHoaDon());
			double tongTien = 0;
			for (ChiTietHoaDon cthd : dscthd) {
				LocalTime thoiGianSuDung = cthd.getThoiGianSuDung();
				double thoiGianSuDungTinhTheoGio = (thoiGianSuDung != null) ? thoiGianSuDung.toSecondOfDay() / 3600.0
						: 0.0;
				tongTien += thoiGianSuDungTinhTheoGio * cthd.getDonGia();
			}
			for (ChiTietSuDungDichVuHoaDon ctsddvhd : dsctsddvhd) {
				tongTien += ctsddvhd.getSoLuong() * ctsddvhd.getDonGia();
			}
			ktTonTaiHD = true;
			modelHD.addRow(new Object[] { hd.getMaHoaDon(), hd.getTenKH(), hd.getTenNV(), sdf.format(hd.getNgayLapHD()),
					decimalFormatVND.format(tongTien) });
		}
		if (!ktTonTaiHD) {
			JOptionPane.showMessageDialog(this, "Không có hóa đơn của ngày lập hóa đơn theo yêu cầu.", "Thông báo",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "Đã tìm kiếm thành công theo ngày lập hóa đơn");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		int selectedRow = tblHoaDon.getSelectedRow();
		if (selectedRow != -1) {
			modelCTHD.setRowCount(0);
			try {
				docDuLieuCTHDtheoHD();
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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

	private ImageIcon getIcon(String path, int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon iconEmployee = new ImageIcon(path);
		Image scaledImage = iconEmployee.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		return scaledIcon;
	}
}
