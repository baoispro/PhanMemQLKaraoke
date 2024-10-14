package gui;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import entity.NhanVien;
import java.awt.Color;

public class PnGiaoDienThongTinCaNhan extends JPanel {

	private NhanVien nvThucThi;
	private JPanel pnlAVT, pnlThongTinCaNhan;
	private JLabel lblMa, lblTenNV, lblMaNhanVIen, lblGioiTinhNV, lblTen, lblGT, lblSoDienThoaiNV, lblSDT, lblDiaChi,
			lblGmail, lblGmailNhanVien, lblNgaySinhNV, lblNS, lblTT, lblTrangThaiNV, lblChucVu, lblCV;
	private JLabel lblDiaChiNV;
	private JLabel lblAnh;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	public PnGiaoDienThongTinCaNhan(NhanVien nv) {
		nvThucThi = nv;
		gui();
	}

	private void gui() {
		setLayout(null);

		pnlAVT = new JPanel();
		pnlAVT.setBounds(29, 93, 330, 370);
		add(pnlAVT);
		pnlAVT.setLayout(null);

		lblAnh = new JLabel("");
		lblAnh.setBounds(10, 10, 310, 350);
		pnlAVT.add(lblAnh);

		pnlThongTinCaNhan = new JPanel();
		pnlThongTinCaNhan.setBackground(new Color(255, 255, 255));
		pnlThongTinCaNhan.setBorder(new TitledBorder(null, "Chi tiết thông tin nhân viên"));
		pnlThongTinCaNhan.setBounds(385, 43, 859, 480);
		add(pnlThongTinCaNhan);
		pnlThongTinCaNhan.setLayout(null);

		lblMa = new JLabel("Mã nhân viên: ");
		lblMa.setFont(new Font("Dialog", Font.BOLD, 12));
		lblMa.setBounds(10, 40, 120, 35);
		pnlThongTinCaNhan.add(lblMa);

		lblMaNhanVIen = new JLabel("");
		lblMaNhanVIen.setFont(new Font("Dialog", Font.BOLD, 12));
		lblMaNhanVIen.setBounds(128, 40, 200, 35);
		pnlThongTinCaNhan.add(lblMaNhanVIen);

		lblTenNV = new JLabel("");
		lblTenNV.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTenNV.setBounds(128, 110, 200, 35);
		pnlThongTinCaNhan.add(lblTenNV);

		lblTen = new JLabel("Họ và tên: ");
		lblTen.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTen.setBounds(10, 110, 120, 35);
		pnlThongTinCaNhan.add(lblTen);

		lblGioiTinhNV = new JLabel("");
		lblGioiTinhNV.setFont(new Font("Dialog", Font.BOLD, 12));
		lblGioiTinhNV.setBounds(128, 180, 200, 35);
		pnlThongTinCaNhan.add(lblGioiTinhNV);

		lblGT = new JLabel("Giới tính: ");
		lblGT.setFont(new Font("Dialog", Font.BOLD, 12));
		lblGT.setBounds(10, 180, 120, 35);
		pnlThongTinCaNhan.add(lblGT);

		lblSoDienThoaiNV = new JLabel("");
		lblSoDienThoaiNV.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSoDienThoaiNV.setBounds(540, 40, 200, 35);
		pnlThongTinCaNhan.add(lblSoDienThoaiNV);

		lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSDT.setBounds(428, 40, 120, 35);
		pnlThongTinCaNhan.add(lblSDT);

		lblDiaChi = new JLabel("Địa chỉ: ");
		lblDiaChi.setFont(new Font("Dialog", Font.BOLD, 12));
		lblDiaChi.setBounds(428, 110, 120, 35);
		pnlThongTinCaNhan.add(lblDiaChi);

		lblGmailNhanVien = new JLabel("");
		lblGmailNhanVien.setFont(new Font("Dialog", Font.BOLD, 12));
		lblGmailNhanVien.setBounds(571, 180, 200, 35);
		pnlThongTinCaNhan.add(lblGmailNhanVien);

		lblGmail = new JLabel("Gmail: ");
		lblGmail.setFont(new Font("Dialog", Font.BOLD, 12));
		lblGmail.setBounds(428, 180, 120, 35);
		pnlThongTinCaNhan.add(lblGmail);

		lblNgaySinhNV = new JLabel("");
		lblNgaySinhNV.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNgaySinhNV.setBounds(128, 250, 200, 35);
		pnlThongTinCaNhan.add(lblNgaySinhNV);

		lblNS = new JLabel("Ngày sinh: ");
		lblNS.setFont(new Font("Dialog", Font.BOLD, 12));
		lblNS.setBounds(10, 250, 120, 35);
		pnlThongTinCaNhan.add(lblNS);

		lblTrangThaiNV = new JLabel("");
		lblTrangThaiNV.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTrangThaiNV.setBounds(540, 250, 200, 35);
		pnlThongTinCaNhan.add(lblTrangThaiNV);

		lblTT = new JLabel("Trạng thái:");
		lblTT.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTT.setBounds(428, 250, 120, 35);
		pnlThongTinCaNhan.add(lblTT);

		lblChucVu = new JLabel("");
		lblChucVu.setFont(new Font("Dialog", Font.BOLD, 12));
		lblChucVu.setBounds(128, 320, 200, 35);
		pnlThongTinCaNhan.add(lblChucVu);

		lblCV = new JLabel("Chức Vụ:");
		lblCV.setFont(new Font("Dialog", Font.BOLD, 12));
		lblCV.setBounds(10, 320, 120, 35);
		pnlThongTinCaNhan.add(lblCV);

		lblDiaChiNV = new JLabel("");
		lblDiaChiNV.setFont(new Font("Dialog", Font.BOLD, 12));
		lblDiaChiNV.setBounds(540, 110, 200, 35);
		pnlThongTinCaNhan.add(lblDiaChiNV);
		// TODO Auto-generated constructor stub
		loadDuLieuVaoForm();
		this.setVisible(true);
	}

	private void loadDuLieuVaoForm() {
		// TODO Auto-generated method stub
		String gioiTinh = "Nữ";
		if (nvThucThi.isGioiTinh() == true) {
			gioiTinh = "Nam";
		} else {
			gioiTinh = "Nữ";
		}
		lblGioiTinhNV.setText(gioiTinh);
		lblMaNhanVIen.setText(nvThucThi.getMaNV());
		lblTenNV.setText(nvThucThi.getTenNV());
		lblNgaySinhNV.setText(dateFormat.format(nvThucThi.getNgaySinh()));
		lblSoDienThoaiNV.setText(nvThucThi.getSoDienThoai());
		lblDiaChiNV.setText(nvThucThi.getDiaChi());
		lblChucVu.setText(nvThucThi.getChucVu());
		lblTrangThaiNV.setText(nvThucThi.getTrangThai());
		String imagePath = nvThucThi.getHinhAnh();
		lblAnh.setIcon(getIcon(imagePath, 310, 350));
		System.out.println(imagePath);
		
	}

	private ImageIcon getIcon(String path, int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon iconEmployee = new ImageIcon(path);
		Image scaledImage = iconEmployee.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		return scaledIcon;
	}
}
