package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.TaiKhoan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class FrmQuenMatKhau extends JFrame implements ActionListener {
	private JLabel lblMatKhauMoi,lblBackground, lblMaXacNhan, lblTItle;
	private JTextField txtSoDienThoai, txtmaxacnhan, txtmatkhaumoi;
	private JButton btnGuiMa, btnXacNhan;
	private JPanel pnlTitle ;
	private NhanVien_DAO nv_dao;
	private TaiKhoan_DAO tk_dao;
	private static final long serialVersionUID = 1L;
	private static final String ACCOUNT_SID = "IDNeedToChange";
	private static final String AUTH_TOKEN = "IDNeedToChange";
	private int maOTP;

	public FrmQuenMatKhau() throws RemoteException, NotBoundException {
		// TODO Auto-generated constructor stub
		super("Quên mật khẩu");
		Registry registry = LocateRegistry.getRegistry(2024);
		tk_dao = (TaiKhoan_DAO) registry.lookup("tk_dao");
		nv_dao = (NhanVien_DAO) registry.lookup("nv_dao");
		gui();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 500);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void gui() {

		lblBackground = new JLabel(getIcon("data/images/bg_DangNhap.jpg", 800, 500));
		lblBackground.setBounds(0, 0, 786, 463);
		getContentPane().add(lblBackground);
		JPanel pnlBackground = new JPanel();
		pnlBackground.setBackground(new Color(255, 255, 255));

		pnlBackground.setBounds(242, 30, 300, 400);
		lblBackground.add(pnlBackground);
		pnlBackground.setBorder(new EmptyBorder(20, 10, 20, 10));
		pnlBackground.setLayout(null);

		JLabel lblSodienThoai = new JLabel("Số điện thoại");
		lblSodienThoai.setFont(new Font("Dialog", Font.BOLD, 12));
		lblSodienThoai.setBounds(24, 67, 150, 25);
		pnlBackground.add(lblSodienThoai);

		txtSoDienThoai = new JTextField();
		txtSoDienThoai.setFont(new Font("Dialog", Font.BOLD, 12));
		txtSoDienThoai.setBounds(10, 92, 280, 30);
		pnlBackground.add(txtSoDienThoai);
		txtSoDienThoai.setColumns(10);

		lblMatKhauMoi = new JLabel("Mật khẩu mới");
		lblMatKhauMoi.setFont(new Font("Dialog", Font.BOLD, 12));
		lblMatKhauMoi.setBounds(24, 200, 150, 25);
		pnlBackground.add(lblMatKhauMoi);

		txtmatkhaumoi = new JTextField();
		txtmatkhaumoi.setFont(new Font("Dialog", Font.BOLD, 12));
		txtmatkhaumoi.setColumns(10);
		txtmatkhaumoi.setBounds(10, 228, 280, 30);
		pnlBackground.add(txtmatkhaumoi);

		lblMaXacNhan = new JLabel("Mã OTP xác nhận");
		lblMaXacNhan.setFont(new Font("Dialog", Font.BOLD, 12));
		lblMaXacNhan.setBounds(20, 132, 150, 25);
		pnlBackground.add(lblMaXacNhan);

		txtmaxacnhan = new JTextField();
		txtmaxacnhan.setFont(new Font("Dialog", Font.BOLD, 12));
		txtmaxacnhan.setColumns(10);
		txtmaxacnhan.setBounds(10, 160, 280, 30);
		pnlBackground.add(txtmaxacnhan);

		btnGuiMa = new JButton("Gửi OTP", getIcon("data/images/mail.png", 16, 16));
		btnGuiMa.setBackground(new Color(255, 128, 0));
		btnGuiMa.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnGuiMa.setFont(new Font("Dialog", Font.BOLD, 12));
		btnGuiMa.setBounds(148, 132, 129, 25);
		btnGuiMa.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnlBackground.add(btnGuiMa);

		pnlTitle = new JPanel();
		pnlTitle.setBackground(new Color(0, 255, 255));
		pnlTitle.setBounds(0, 0, 300, 57);
		pnlBackground.add(pnlTitle);
		pnlTitle.setLayout(null);

		lblTItle = new JLabel("Quên mật khẩu");
		lblTItle.setBackground(new Color(255, 255, 255));
		lblTItle.setFont(new Font("Dialog", Font.BOLD, 20));
		lblTItle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTItle.setBounds(0, 0, 300, 57);
		pnlTitle.add(lblTItle);

		btnXacNhan = new GradientRoundedButton("Xác nhận");
		btnXacNhan.setForeground(Color.white);
		btnXacNhan.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnXacNhan.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnXacNhan.setBounds(79, 288, 150, 30);
		pnlBackground.add(btnXacNhan);
		
		final JLabel lbltrolaidangnhap = new JLabel("Quay trở lại đăng nhập");
		lbltrolaidangnhap.setFont(new Font("Dialog", Font.BOLD, 12));
		lbltrolaidangnhap.setHorizontalAlignment(SwingConstants.CENTER);
		lbltrolaidangnhap.setBounds(78, 329, 151, 25);
		lbltrolaidangnhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
		pnlBackground.add(lbltrolaidangnhap);
		btnXacNhan.addActionListener(this);
		btnGuiMa.addActionListener(this);
		lbltrolaidangnhap.addMouseListener(new MouseListener() {
			
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
				FrmDangNhap frmdn = null;
				try {
					frmdn = new FrmDangNhap();
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frmdn.setVisible(true);

				setVisible(false);
			}
		});
		lbltrolaidangnhap.addMouseListener(new MouseListener() {
			
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
				lbltrolaidangnhap.setForeground(new Color(0x333));
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				lbltrolaidangnhap.setForeground(new Color(0xa64bf4));
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
	}


	private ImageIcon getIcon(String path, int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon iconEmployee = new ImageIcon(path);
		Image scaledImage = iconEmployee.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		return scaledIcon;
	}

	static class GradientRoundedButton extends JButton {
		public GradientRoundedButton(String text) {
			super(text);
			setContentAreaFilled(false); // Loại bỏ nền của JButton
			setFocusPainted(false); // Loại bỏ khung focus khi di chuột vào

			// Tạo viền và font chữ tùy chỉnh
			setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
			setFont(new Font("Arial", Font.PLAIN, 14));
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			if (getModel().isRollover()) {
				g2d.setPaint(new GradientPaint(0, 0, new Color(0xfc00ff), getWidth(), 0, new Color(0x00dbde)));
			} else {
				g2d.setPaint(new GradientPaint(0, 0, new Color(0x00dbde), getWidth(), 0, new Color(0xfc00ff)));
			}

			// Vẽ nút dạng viên thuốc với màu gradient
			g2d.fill(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 30, 30));

			super.paintComponent(g);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnGuiMa)) {
			String sdt = txtSoDienThoai.getText();
			if (Pattern.matches("0[0-9]{9}", sdt)) {
				try {
					if (nv_dao.kiemTraSDT(sdt)) {
						maOTP = guiMaXacNhan(sdt);
						JOptionPane.showMessageDialog(null, "Gửi mã thành công");
					} else
						JOptionPane.showMessageDialog(null, "Bạn không quyền truy cập");
				} catch (HeadlessException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} else {
				JOptionPane.showMessageDialog(null, "Vui lòng nhập số điện thoại thật");
			}
		}
		if(e.getSource().equals(btnXacNhan)) {
		
			if(!txtmatkhaumoi.getText().matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$")) {
				JOptionPane.showMessageDialog(null, "Mật khẩu khá yếu. Mật khẩu phải gồm 8 kí tự, ít nhất một chữ thường, chữ hoa, số và kí tự đặt biệt!");
				return;
			}
			try {
				if(txtmaxacnhan.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập mã xác nhận");
					return;
				}
				Integer.parseInt(txtmaxacnhan.getText());
			} catch (Exception e2) {
				JOptionPane.showMessageDialog(null, "Mã xác nhận bao gồm 6 chử số (XXXXXX)");
				return;
			}
			if(maOTP == Integer.parseInt(txtmaxacnhan.getText())) {
				String sdt = txtSoDienThoai.getText();
				String maNV = null;
				try {
					maNV = nv_dao.getNhanVienTheoSDT(sdt).getMaNV();
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				TaiKhoan tk = null;
				try {
					tk = tk_dao.getTaiKhoanTheoMa(maNV);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tk.setMatKhau(txtmatkhaumoi.getText());
				try {
					tk_dao.update(tk);
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				JOptionPane.showMessageDialog(null, "Cập nhật mật khẩu thành công");
				this.setVisible(false);
				FrmDangNhap frmDN = null;
				try {
					frmDN = new FrmDangNhap();
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frmDN.setVisible(true);

			} else {
				JOptionPane.showMessageDialog(null, "Bạn đã nhập sai mã xác nhận !!!. Vui lòng thử lại");
			}
		}
	}

	public int guiMaXacNhan(String sdt) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		String SDT_VN = "+84" + sdt.substring(1);
		System.out.println(SDT_VN);
		int maXN = (int) (Math.random() * (999999 - 100000 + 1)) + 100000;
		String sms = "Mã xác nhận của bạn là " + maXN;
		Message message = Message.creator(new PhoneNumber(SDT_VN), new PhoneNumber("PhoneNumberNeedToChange"), sms).create();
		System.out.println(message.getSid());
		return maXN;
	}
}
