package gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.TaiKhoan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class FrmDangNhap extends JFrame {
	private JLabel lblTenDangNhap, lblMatKhau, lblQuenMatKhau, lblDangNhapNhanh;
	private JTextField txtTenDangNhap, txtMatKhau;
	private JButton btnDangNhap;
	private NhanVien_DAO nv_dao;
	private TaiKhoan_DAO tk_dao;

	public FrmDangNhap() throws RemoteException, NotBoundException {
		// TODO Auto-generated constructor stub
		super("Đăng nhập hệ thống");
		Registry registry = LocateRegistry.getRegistry(2024);
		tk_dao = (TaiKhoan_DAO) registry.lookup("tk_dao");
		nv_dao = (NhanVien_DAO) registry.lookup("nv_dao");
		gui();
		btnDangNhap.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String tenDangNhap = txtTenDangNhap.getText().trim();
				String matKhau = txtMatKhau.getText().trim();
				TaiKhoan tk = null;
				try {
					tk = tk_dao.checkTaiKhoan(tenDangNhap, matKhau);
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if(tk==null) {
					JOptionPane.showMessageDialog(null, "Sai mật khẩu hoặc tên đăng nhập");
				}
				else {
					NhanVien nv = null;
					try {
						nv = nv_dao.getNhanVienTheoMa(tk.getNhanVien().getMaNV());
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					FrmGiaoDienChinh frmChinh = null;
					try {
						frmChinh = new FrmGiaoDienChinh(nv,false);
					} catch (RemoteException | NotBoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					frmChinh.setVisible(true);

					// Ẩn frame đăng nhập
					setVisible(false);
				}
			}
		});
//		Dành cho khách hàng muốn xem phòng và dịch vụ
		lblDangNhapNhanh.addMouseListener(new MouseListener() {
			
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				FrmGiaoDienChinh frmChinh = null;
				try {
					frmChinh = new FrmGiaoDienChinh(null,true);
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				frmChinh.setVisible(true);

				// Ẩn frame đăng nhập
				setVisible(false);
			}
		});
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 500);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void gui() {
		JLabel lblBackground = new JLabel(getIcon("data/images/bg_DangNhap.jpg", 800, 500));
		this.add(lblBackground);
		JPanel pnlBackground = new JPanel();
		pnlBackground.setBackground(Color.white);
		pnlBackground.setBounds(242, 30, 300, 400);
		lblBackground.add(pnlBackground);
		JLabel lblTitle = new JLabel("Đăng nhập");
		lblTitle.setFont(new Font("Time New Roman", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		pnlBackground.setBorder(new EmptyBorder(20, 10, 20, 10));
		pnlBackground.setLayout(new BorderLayout());
		pnlBackground.add(lblTitle, BorderLayout.NORTH);

//		Phần giữa
		JPanel pnlCenter = new JPanel();
		pnlCenter.setBackground(Color.white);
		pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
		Box b = Box.createVerticalBox();
		b.add(Box.createVerticalStrut(20));
		lblTenDangNhap = new JLabel("Tên đăng nhập");
		txtTenDangNhap = new JTextField();
		txtTenDangNhap.setText("QL0001");
		txtTenDangNhap.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0xadadad)));
//		txtTenDangNhap.setForeground(Color.GRAY);
		txtTenDangNhap.addFocusListener(new FocusListener() {

			
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if (txtTenDangNhap.getText().isEmpty()) {
                    txtTenDangNhap.setText("Nhập tên đăng nhập");
                    txtTenDangNhap.setForeground(Color.GRAY); // Đặt màu văn bản khi không nhập
                }
			}

			
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (txtTenDangNhap.getText().equals("Nhập tên đăng nhập")) {
					txtTenDangNhap.setText("");
					txtTenDangNhap.setForeground(Color.BLACK); // Đặt màu văn bản khi nhập
				}
			}
		});
		b.add(lblTenDangNhap);
		b.add(Box.createVerticalStrut(10));
		b.add(txtTenDangNhap);
		lblMatKhau = new JLabel("Mật khẩu");
		b.add(Box.createVerticalStrut(10));
		txtMatKhau = new JTextField();
		txtMatKhau.setText("aA!12345");
		txtMatKhau.setBorder(new MatteBorder(0, 0, 1, 0, new Color(0xadadad)));
//		txtMatKhau.setForeground(Color.GRAY);
		txtMatKhau.addFocusListener(new FocusListener() {

			
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				if (txtMatKhau.getText().isEmpty()) {
					txtMatKhau.setText("Nhập mật khẩu");
					txtMatKhau.setForeground(Color.GRAY); // Đặt màu văn bản khi không nhập
                }
			}

			
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				if (txtMatKhau.getText().equals("Nhập mật khẩu")) {
					txtMatKhau.setText("");
					txtMatKhau.setForeground(Color.BLACK); // Đặt màu văn bản khi nhập
				}
			}
		});
		b.add(lblMatKhau);
		b.add(Box.createVerticalStrut(10));
		b.add(txtMatKhau);
		b.add(Box.createVerticalStrut(10));
		Box d = Box.createHorizontalBox();
		d.add(b);
		pnlCenter.add(d);
		Box b1 = Box.createHorizontalBox();
		lblQuenMatKhau = new JLabel("Quên mật khẩu?");
		lblQuenMatKhau.setFont(new Font("Time New Roman", Font.PLAIN, 12));
		lblQuenMatKhau.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblQuenMatKhau.setForeground(new Color(0x333));
		lblQuenMatKhau.addMouseListener(new MouseListener() {
			
			
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				lblQuenMatKhau.setForeground(new Color(0x333));
			}
			
			
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				lblQuenMatKhau.setForeground(new Color(0xa64bf4));
			}
			
			
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				FrmQuenMatKhau quenMK = null;
				try {
					quenMK = new FrmQuenMatKhau();
				} catch (RemoteException | NotBoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				quenMK.setVisible(true);

				setVisible(false);
			}
		});
		b1.add(Box.createHorizontalStrut(180));
		b1.add(lblQuenMatKhau);
		pnlCenter.add(b1);
		pnlCenter.add(Box.createVerticalStrut(20));
		btnDangNhap = new GradientRoundedButton("ĐĂNG NHẬP");
		btnDangNhap.setForeground(Color.white);
		btnDangNhap.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnDangNhap.setAlignmentX(Component.CENTER_ALIGNMENT);
		pnlCenter.add(btnDangNhap);
		pnlCenter.add(Box.createVerticalStrut(100));

//		Phần dưới
		JPanel pnlSouth = new JPanel();
		pnlSouth.setBackground(Color.white);
		lblDangNhapNhanh = new JLabel("ĐĂNG NHẬP NHANH");
		lblDangNhapNhanh.setFont(new Font("Time New Roman", Font.PLAIN, 10));
		lblDangNhapNhanh.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblDangNhapNhanh.setForeground(new Color(0x333));
		lblDangNhapNhanh.addMouseListener(new MouseListener() {
			
			
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				lblDangNhapNhanh.setForeground(new Color(0x333));
			}
			
			
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				lblDangNhapNhanh.setForeground(new Color(0xa64bf4));
			}
			
			
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		pnlSouth.add(lblDangNhapNhanh);

		pnlBackground.add(pnlCenter, BorderLayout.CENTER);
		pnlBackground.add(pnlSouth, BorderLayout.SOUTH);
	}

	public static void main(String[] args) throws RemoteException, NotBoundException {
		new FrmDangNhap().setVisible(true);
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
}
