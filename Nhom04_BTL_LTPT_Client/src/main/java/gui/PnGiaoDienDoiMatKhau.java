package gui;

import javax.swing.*;

import dao.TaiKhoan_DAO;
import entity.NhanVien;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class PnGiaoDienDoiMatKhau extends JPanel {
	private JLabel lblNhapLaiMatKhauMoi, lblMatKhauCu, lblMatKhauMoi;
	private JTextField txtNhapLaiMatKhauMoi, txtMatKhauCu, txtMatKhauMoi;
	private JButton btnDoiMatKhau;
	private TaiKhoan_DAO tk_dao;

	public PnGiaoDienDoiMatKhau(final NhanVien nv) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry(2024);
		tk_dao = (TaiKhoan_DAO) registry.lookup("tk_dao");
		setLayout(null); // Thiết lập layout thành null để sử dụng setBounds
		int labelWidth = 200;
		int labelHeight = 25;
		int textFieldWidth = 250;
		int textFieldHeight = 25;
		int buttonWidth = 150;
		int buttonHeight = 30;

		lblMatKhauCu = new JLabel("Mật khẩu cũ:");
		lblMatKhauCu.setBounds((1241 - labelWidth) / 2, 50, labelWidth, labelHeight);
		add(lblMatKhauCu);

		txtMatKhauCu = new JTextField();
		txtMatKhauCu.setBounds((1281 - textFieldWidth) / 2, 100, textFieldWidth, textFieldHeight);
		add(txtMatKhauCu);

		lblMatKhauMoi = new JLabel("Mật khẩu mới:");
		lblMatKhauMoi.setBounds((1241 - labelWidth) / 2, 150, labelWidth, labelHeight);
		add(lblMatKhauMoi);

		txtMatKhauMoi = new JPasswordField();
		txtMatKhauMoi.setBounds((1281 - textFieldWidth) / 2, 200, textFieldWidth, textFieldHeight);
		add(txtMatKhauMoi);

		lblNhapLaiMatKhauMoi = new JLabel("Nhập lại mật khẩu mới:");
		lblNhapLaiMatKhauMoi.setBounds((1241 - labelWidth) / 2, 250, labelWidth, labelHeight);
		add(lblNhapLaiMatKhauMoi);

		txtNhapLaiMatKhauMoi = new JPasswordField();
		txtNhapLaiMatKhauMoi.setBounds((1281 - textFieldWidth) / 2, 300, textFieldWidth, textFieldHeight);
		add(txtNhapLaiMatKhauMoi);

		btnDoiMatKhau = new JButton("Đổi mật khẩu");
		btnDoiMatKhau.setBounds((1281 - buttonWidth) / 2, 350, buttonWidth, buttonHeight);
		add(btnDoiMatKhau);
		btnDoiMatKhau.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Xử lý sự kiện khi nhấn nút đổi mật khẩu ở đây
				String matKhauMoiDuocNhapLai = txtNhapLaiMatKhauMoi.getText();
				String matKhauCu = txtMatKhauCu.getText();
				String matKhauMoi = txtMatKhauMoi.getText();
				if (!matKhauMoi.equals(matKhauMoiDuocNhapLai)) {
					JOptionPane.showMessageDialog(null, "Mật khẩu mới nhập lại không trùng khớp");
				} else {
					try {
						if (tk_dao.getTaiKhoanTheoMa(nv.getMaNV()).getMatKhau() == matKhauCu) {
							tk_dao.updateMatKhau(matKhauMoi, tk_dao.getTaiKhoanTheoMa(nv.getMaNV()).getMaTK());
							// Sau khi thay đổi mật khẩu, có thể hiển thị thông báo thành công hoặc thất bại
							JOptionPane.showMessageDialog(null, "Mật khẩu đã được thay đổi!");
						} else {
							JOptionPane.showMessageDialog(null, "Nhập mật khẩu hiện tại sai");
						}
					} catch (HeadlessException | RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		add(btnDoiMatKhau);

		setVisible(true);
	}

}
