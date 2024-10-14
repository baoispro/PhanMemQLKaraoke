package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import dao.NhanVien_DAO;
import dao.TaiKhoan_DAO;
import entity.NhanVien;
import entity.TaiKhoan;

public class PnGiaoDienTaiKhoan extends JPanel implements ActionListener {
	private JLabel lblMaTK, lblTenDangNhap, lblMatKhau, lblMaNV;
	private JTextField txtMaTK, txtTenDangNhap, txtMatKhau;
	private JComboBox cmbNV;
	private JButton btnThem, btnXoa, btnSua, btnXoaTrang;
	private JPanel pnlNorthBackGround;
	private JTable tblTK;
	private DefaultTableModel model;
	private NhanVien_DAO nv_dao;
	private TaiKhoan_DAO tk_dao;

	public PnGiaoDienTaiKhoan(String maNV) throws RemoteException, NotBoundException {
		// TODO Auto-generated constructor stub
		Registry registry = LocateRegistry.getRegistry(2024);
		nv_dao = (NhanVien_DAO) registry.lookup("nv_dao");
		tk_dao = (TaiKhoan_DAO) registry.lookup("tk_dao");
		this.setLayout(new BorderLayout());
		Border border = BorderFactory.createEmptyBorder(10, 10, 10, 5);
		Border border1 = BorderFactory.createEmptyBorder(5, 5, 10, 10);
		JPanel pnlWest = new JPanel();
		pnlWest.setLayout(new BoxLayout(pnlWest, BoxLayout.Y_AXIS));
		pnlWest.setBorder(border);
		JPanel pnlWestBackGround = new JPanel();
		pnlWestBackGround.setBackground(Color.white);
		// Kích thước là 20% của 1251 và có chiều cao là 539
		pnlWestBackGround.setPreferredSize(new Dimension(250, 539));
		pnlWest.add(pnlWestBackGround);
		pnlWestBackGround.setLayout(new BoxLayout(pnlWestBackGround, BoxLayout.Y_AXIS));
		pnlWestBackGround.add(Box.createVerticalStrut(10));
//		Mã Tài khoản
		lblMaTK = new JLabel("Mã tài khoản:");
		lblMaTK.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtMaTK = new JTextField();
		txtMaTK.setEditable(false);
		Box b = Box.createVerticalBox();
		Box c = Box.createHorizontalBox();
		b.add(lblMaTK);
		c.add(Box.createHorizontalStrut(10));
		c.add(txtMaTK);
		c.add(Box.createHorizontalStrut(10));
		b.add(Box.createVerticalStrut(10));
		b.add(c);
		b.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b);
//		Mã Nhân viên
		lblMaNV = new JLabel("Mã nhân viên:");
		cmbNV = new JComboBox<>();
		ArrayList<NhanVien> dsnv = nv_dao.getAlltbNhanVien();
//		Nếu khi nhân viên được thêm vào và muốn đăng kí tài khoản thì khi vào trang tài khoản sẽ hiện lên mã nhân viên đã thêm mới nãy muốn đăng kí
		if (maNV != null) {
			cmbNV.addItem(maNV);
			for (NhanVien nhanVien : dsnv) {
				if (!maNV.equalsIgnoreCase(nhanVien.getMaNV())) {
					if (nhanVien.getTrangThai().equalsIgnoreCase("đang làm"))
						cmbNV.addItem(nhanVien.getMaNV());
				}
			}
		} else {
//			Còn trường hợp này là bình thường chỉ qua trang đăng kí để đăng kí tài khoản nên mã nhân viên sẽ là null chưa xác định
			for (NhanVien nhanVien : dsnv) {
//				Những nhân viên nào đang làm mới có tài khoản

				if (nhanVien.getTrangThai().equalsIgnoreCase("đang làm"))
					cmbNV.addItem(nhanVien.getMaNV());
			}
		}
		Box b3 = Box.createHorizontalBox();
		b3.add(Box.createHorizontalStrut(10));
		b3.add(lblMaNV);
		b3.add(Box.createHorizontalStrut(19));
		b3.add(cmbNV);
		b3.add(Box.createHorizontalStrut(10));
		pnlWestBackGround.add(b3);
		pnlWestBackGround.add(Box.createVerticalStrut(10));
//		Tên đăng nhập
		lblTenDangNhap = new JLabel("Tên đăng nhập:");
		lblTenDangNhap.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtTenDangNhap = new JTextField();
		Box b1 = Box.createVerticalBox();
		Box c1 = Box.createHorizontalBox();
		b1.add(lblTenDangNhap);
		c1.add(Box.createHorizontalStrut(10));
		c1.add(txtTenDangNhap);
		c1.add(Box.createHorizontalStrut(10));
		b1.add(Box.createVerticalStrut(10));
		b1.add(c1);
		b1.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b1);
//		Mật khẩu
		lblMatKhau = new JLabel("Mật khẩu:");
		lblMatKhau.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtMatKhau = new JTextField();
		Box b2 = Box.createVerticalBox();
		Box c2 = Box.createHorizontalBox();
		c2.add(Box.createHorizontalStrut(10));
		b2.add(lblMatKhau);
		b2.add(Box.createVerticalStrut(10));
		c2.add(txtMatKhau);
		c2.add(Box.createHorizontalStrut(10));
		b2.add(c2);
		b2.add(Box.createVerticalStrut(300));
		pnlWestBackGround.add(b2);

//		Phần bảng và nút
		JPanel pnlCenter = new JPanel();
		pnlCenter.setBorder(border1);
		pnlCenter.setLayout(new BorderLayout());
		pnlNorthBackGround = new JPanel();
		JPanel pnlSouthBackGround = new JPanel();
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setHgap(30);
		flowLayout.setVgap(44);
		pnlSouthBackGround.setLayout(flowLayout);
		pnlSouthBackGround.setBackground(Color.white);
		pnlSouthBackGround.setPreferredSize(new Dimension(1001, 108));
		btnThem = new JButton("Thêm", getIcon("data/images/icon_btnThem.png", 16, 16));
		btnXoa = new JButton("Xóa", getIcon("data/images/icon_btnXoa.png", 16, 16));
		btnSua = new JButton("Sửa", getIcon("data/images/icon_btnLuu.png", 16, 16));
		btnXoaTrang = new JButton("Xóa trắng", getIcon("data/images/icon_btnXoaTrang.png", 16, 16));
		this.setVisible(true);

		pnlSouthBackGround.add(btnThem);
		pnlSouthBackGround.add(btnXoa);
		pnlSouthBackGround.add(btnSua);
		pnlSouthBackGround.add(btnXoaTrang);
		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btnXoaTrang.addActionListener(this);

		pnlCenter.add(pnlNorthBackGround, BorderLayout.NORTH);
		pnlCenter.add(pnlSouthBackGround, BorderLayout.SOUTH);
		taoBang();
		docDuLieuDataBaseVaoTable();

		this.add(pnlWest, BorderLayout.WEST);
		this.add(pnlCenter, BorderLayout.CENTER);
	}

	private void taoBang() {
		// TODO Auto-generated method stub
		model = new DefaultTableModel();
		tblTK = new JTable(model);
		model.addColumn("Mã Tài Khoản");
		model.addColumn("Tên Đăng Nhập");
		model.addColumn("Mật Khẩu");
		model.addColumn("Mã Nhân Viên");
		JScrollPane sp = new JScrollPane(tblTK, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		tblTK.setRowHeight(30);
		sp.setPreferredSize(new Dimension(1001, 431));
		pnlNorthBackGround.add(sp);
		tblTK.addMouseListener(new MouseListener() {

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
				int row = tblTK.getSelectedRow();
				txtMaTK.setText(model.getValueAt(row, 0).toString());
				txtTenDangNhap.setText(model.getValueAt(row, 1).toString());
				txtMatKhau.setText(model.getValueAt(row, 2).toString());
				cmbNV.setSelectedItem(model.getValueAt(row, 3));
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

	private void xoaTrang() {
		// TODO Auto-generated method stub
		txtMaTK.setText("");
		txtTenDangNhap.setText("");
		txtMatKhau.setText("");
		cmbNV.setSelectedIndex(0);
		tblTK.clearSelection();
		txtTenDangNhap.requestFocus();
	}
//int i để kiểm tra sự trùng lặp tên
	private boolean validData(int i) throws RemoteException {
		String tenDN = txtTenDangNhap.getText().trim();
		String mk = txtMatKhau.getText().trim();
		boolean trung = false;
//		kiểm tra sự trùng lắp trong tên đăng nhập nếu đã có
		ArrayList<TaiKhoan> ds = tk_dao.getAlltbTaiKhoan();
		for (TaiKhoan taiKhoan : ds) {
			if (tenDN.equals(taiKhoan.getTenDangNhap())) {
				trung = true;
			}
			i+=1;
		}
		if(i==1)
			trung = false;
		if (tenDN.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để tên đăng nhập trống");
			txtTenDangNhap.requestFocus();
			return false;
		} else if (!tenDN.matches("^[A-Za-z0-9]{6,20}$")) {
			JOptionPane.showMessageDialog(this,
					"Tên đăng nhập không có kí tự đặc biệt cũng như kí tự có dấu và số lượng kí tự từ 6-20 ");
			txtTenDangNhap.requestFocus();
			return false;
		} else if (trung) {
			JOptionPane.showMessageDialog(this, "Đã có tên đăng nhập này. Hãy chọn 1 cái tên khác");
			txtTenDangNhap.requestFocus();
			return false;
		}
		if (mk.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để mật khẩu trống");
			txtMatKhau.requestFocus();
			return false;
		} else if (!mk.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[^A-Za-z0-9]).{8,}$")) {
			JOptionPane.showMessageDialog(this,
					"Mật khẩu phải 8 kí tự trở lên và phải có ít nhất 1 kí tự in hoa, 1 kí tự in thường, 1 kí tự số và 1 kí tự đặc biệt");
			txtMatKhau.requestFocus();
			return false;
		}
		return true;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnXoaTrang)) {
			xoaTrang();
		} else if (e.getSource().equals(btnThem)) {
			try {
				if (validData(1)) {
					String maTK = txtMaTK.getText();
					String tenDangNhap = txtTenDangNhap.getText();
					String matKhau = txtMatKhau.getText();
					String maNV = cmbNV.getSelectedItem().toString();
					TaiKhoan tk = new TaiKhoan(maTK, tenDangNhap, matKhau,new NhanVien(maNV));
					tk_dao.create(tk);
					model.addRow(new Object[] { tk_dao.getTaiKhoanMoiNhat().getMaTK(), tenDangNhap, matKhau, maNV });
					xoaTrang();
					JOptionPane.showMessageDialog(this, "Đã thêm thành công");
				}
			} catch (HeadlessException | RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getSource().equals(btnXoa)) {
			int row = tblTK.getSelectedRow();
			if (row != -1) {
				String maTK = (String) tblTK.getModel().getValueAt(row, 0);
				int comfirm = JOptionPane.showConfirmDialog(this, "Chắc chắn xóa không?", "Chú ý",
						JOptionPane.YES_NO_OPTION);
				if (comfirm == JOptionPane.YES_OPTION) {
					try {
						tk_dao.delete(maTK);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					model.setRowCount(0);
					try {
						docDuLieuDataBaseVaoTable();
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					xoaTrang();
					JOptionPane.showMessageDialog(this, "Đã xóa thành công");
				}
			} else {
				JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần xóa");
			}
		} else if (e.getSource().equals(btnSua)) {
			if (tblTK.getSelectedRow() == -1)
				JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần sửa");
			else {
				try {
					if (validData(0)) {
						String maTK = txtMaTK.getText().trim();
						String tenDN = txtTenDangNhap.getText().trim();
						String matKhau = txtMatKhau.getText().trim();
						String maNV = cmbNV.getSelectedItem().toString();
						TaiKhoan tkNew = new TaiKhoan(maTK, tenDN, matKhau, new NhanVien(maNV));
						if (tk_dao.update(tkNew)) {
							model.setRowCount(0);
							docDuLieuDataBaseVaoTable();
							xoaTrang();
							JOptionPane.showMessageDialog(this, "Đã cập nhật thành công");
						}
					}
				} catch (HeadlessException | RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	private void docDuLieuDataBaseVaoTable() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<TaiKhoan> ds = tk_dao.getAlltbTaiKhoan();
		for (TaiKhoan taiKhoan : ds) {
			model.addRow(new Object[] { taiKhoan.getMaTK(), taiKhoan.getTenDangNhap(), taiKhoan.getMatKhau(),
					taiKhoan.getNhanVien().getMaNV() });
		}
	}
}
