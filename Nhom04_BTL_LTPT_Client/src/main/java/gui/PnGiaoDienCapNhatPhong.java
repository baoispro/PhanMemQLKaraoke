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

import dao.LoaiPhong_DAO;
import dao.Phong_DAO;
import entity.LoaiPhong;
import entity.Phong;

public class PnGiaoDienCapNhatPhong extends JPanel implements ActionListener {
	private JLabel lblMaPhong, lblTenPhong, lblLoaiPhong, lblTinhTrang;
	private JTextField txtMaPhong, txtTenPhong;
	private JComboBox cmbLP, cmbTinhTrang;
	private JButton btnThem, btnXoa, btnSua, btnXoaTrang;
	private JPanel pnlNorthBackGround;
	private JTable tblTK;
	private DefaultTableModel model;
	private LoaiPhong_DAO lp_dao;
	private Phong_DAO p_dao;

	public PnGiaoDienCapNhatPhong() throws RemoteException, NotBoundException {
		// TODO Auto-generated constructor stub
		Registry registry = LocateRegistry.getRegistry(2024);
		lp_dao = (LoaiPhong_DAO) registry.lookup("lp_dao");
		p_dao = (Phong_DAO) registry.lookup("p_dao");
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
//		Mã phòng
		lblMaPhong = new JLabel("Mã phòng:");
		lblMaPhong.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtMaPhong = new JTextField();
		txtMaPhong.setEditable(false);
		Box b = Box.createVerticalBox();
		Box c = Box.createHorizontalBox();
		b.add(lblMaPhong);
		c.add(Box.createHorizontalStrut(10));
		c.add(txtMaPhong);
		c.add(Box.createHorizontalStrut(10));
		b.add(Box.createVerticalStrut(10));
		b.add(c);
		b.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b);
//		Tên phòng
		lblTenPhong = new JLabel("Tên phòng:");
		lblTenPhong.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtTenPhong = new JTextField();
		Box b1 = Box.createVerticalBox();
		Box c1 = Box.createHorizontalBox();
		b1.add(lblTenPhong);
		c1.add(Box.createHorizontalStrut(10));
		c1.add(txtTenPhong);
		c1.add(Box.createHorizontalStrut(10));
		b1.add(Box.createVerticalStrut(10));
		b1.add(c1);
		b1.add(Box.createVerticalStrut(10));
		pnlWestBackGround.add(b1);
//		Loại phòng
		lblLoaiPhong = new JLabel("Loại phòng:");
		cmbLP = new JComboBox<>();
		ArrayList<LoaiPhong> dslp = lp_dao.getAlltbLoaiPhong();
		for (LoaiPhong loaiPhong : dslp) {
			cmbLP.addItem(loaiPhong.getTenLP());
		}
		Box b2 = Box.createHorizontalBox();
		b2.add(Box.createHorizontalStrut(10));
		b2.add(lblLoaiPhong);
		b2.add(Box.createHorizontalStrut(19));
		b2.add(cmbLP);
		b2.add(Box.createHorizontalStrut(10));
		pnlWestBackGround.add(b2);
		pnlWestBackGround.add(Box.createVerticalStrut(10));
//		Tình trạng
		lblTinhTrang = new JLabel("Tình trạng:");
		cmbTinhTrang = new JComboBox<>();
		cmbTinhTrang.addItem("Phòng trống");
		cmbTinhTrang.addItem("Phòng đang sử dụng");
		cmbTinhTrang.addItem("Phòng chờ");
		cmbTinhTrang.addItem("Phòng đang bảo trì");
		Box b3 = Box.createHorizontalBox();
		b3.add(Box.createHorizontalStrut(10));
		b3.add(lblTinhTrang);
		b3.add(Box.createHorizontalStrut(25));
		b3.add(cmbTinhTrang);
		b3.add(Box.createHorizontalStrut(10));
		pnlWestBackGround.add(b3);
		pnlWestBackGround.add(Box.createVerticalStrut(350));

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
		model.addColumn("Mã Phòng");
		model.addColumn("Tên Phòng");
		model.addColumn("Loại Phòng");
		model.addColumn("Tình trạng");
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
				txtMaPhong.setText(model.getValueAt(row, 0).toString());
				txtTenPhong.setText(model.getValueAt(row, 1).toString());
				cmbLP.setSelectedItem(model.getValueAt(row, 2));
				cmbTinhTrang.setSelectedItem(model.getValueAt(row, 3));
			}
		});
	}

	private void xoaTrang() {
		// TODO Auto-generated method stub
		txtMaPhong.setText("");
		txtTenPhong.setText("");
		cmbLP.setSelectedIndex(0);
		cmbTinhTrang.setSelectedIndex(0);
		tblTK.clearSelection();
		txtTenPhong.requestFocus();
	}

	private ImageIcon getIcon(String path, int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon iconEmployee = new ImageIcon(path);
		Image scaledImage = iconEmployee.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		return scaledIcon;
	}

	// int i để kiểm tra sự trùng lặp tên
	private boolean validData(int i) throws RemoteException {
		String ten = txtTenPhong.getText().trim();
		boolean trung = false;
//		kiểm tra sự trùng lắp trong tên đăng nhập nếu đã có
		ArrayList<Phong> ds = p_dao.getAlltbPhong();
		for (Phong p : ds) {
			if (ten.equals(p.getTenPhong())) {
				trung = true;
			}
			i += 1;
		}
		if (i != 1)
			trung = false;
		if (ten.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không được để tên phòng trống");
			txtTenPhong.requestFocus();
			return false;
		} else if (!ten.matches("^Phòng\\s[0-9]*$")) {
			JOptionPane.showMessageDialog(this, "Tên phòng phải bắt đầu bằng từ Phòng và kế tiếp là số.Ví dụ: Phòng 1");
			txtTenPhong.requestFocus();
			return false;
		} else if (trung) {
			JOptionPane.showMessageDialog(this, "Đã có tên phòng này. Hãy chọn 1 cái tên khác");
			txtTenPhong.requestFocus();
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
					String maP = txtMaPhong.getText();
					String tenP = txtTenPhong.getText();
					LoaiPhong lp = lp_dao.getLoaiPhongTheoTen(cmbLP.getSelectedItem().toString());
					String tinhTrang = cmbTinhTrang.getSelectedItem().toString();
					Phong p = new Phong(maP, tenP, lp, tinhTrang);
					p_dao.create(p);
					model.addRow(new Object[] { p_dao.getPhongMoiNhat().getMaPhong(), tenP, lp.getTenLP(), tinhTrang });
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
						p_dao.delete(maTK);
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
						String maP = txtMaPhong.getText().trim();
						String tenP = txtTenPhong.getText().trim();
						LoaiPhong lp = lp_dao.getLoaiPhongTheoTen(cmbLP.getSelectedItem().toString());
						String tinhTrang = cmbTinhTrang.getSelectedItem().toString();
						Phong pNew = new Phong(maP, tenP, lp, tinhTrang);
						if (p_dao.update(pNew)) {
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
		ArrayList<Phong> ds = p_dao.getAlltbPhong();
		for (Phong phong : ds) {
			model.addRow(new Object[] { phong.getMaPhong(), phong.getTenPhong(),
					lp_dao.getLoaiPhongTheoMa(phong.getLp().getMaLP()).getTenLP(), phong.getTinhTrang() });
		}
	}
}
