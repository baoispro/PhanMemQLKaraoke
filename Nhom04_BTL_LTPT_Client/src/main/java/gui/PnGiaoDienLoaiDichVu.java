package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Frame;
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
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.LoaiDichVu_DAO;
import entity.LoaiDichVu;
import java.awt.Font;

public class PnGiaoDienLoaiDichVu extends JPanel implements ActionListener {
	private JLabel lblMaLoaiDichVu, lblTen;
	private JTextField txtMaLoaiDV, txtTenLoaiDV;
	private JButton btnThem, btnXoa, btnSua, btnXoaTrang;
	private JPanel pnlNorthBackGround;
	private JTable tblLoaiDV;
	private DefaultTableModel model;
	private LoaiDichVu_DAO ldv_dao;

	public PnGiaoDienLoaiDichVu() throws RemoteException, NotBoundException {
		// TODO Auto-generated constructor stub
		Registry registry = LocateRegistry.getRegistry(2024);
		ldv_dao = (LoaiDichVu_DAO) registry.lookup("ldv_dao");
		// TODO Auto-generated constructor stub
		this.setLayout(new BorderLayout());
		Border border = BorderFactory.createEmptyBorder(10, 10, 10, 5);
		Border border1 = BorderFactory.createEmptyBorder(5, 5, 10, 10);
		JPanel pnlWest = new JPanel();
		pnlWest.setBorder(border);
		pnlWest.setLayout(new BoxLayout(pnlWest, BoxLayout.X_AXIS));
		JPanel pnlWestBackGround = new JPanel();
		pnlWestBackGround.setBackground(Color.white);
		// Kích thước là 20% của 1251 và có chiều cao là 539
		pnlWestBackGround.setPreferredSize(new Dimension(250, 539));
		pnlWest.add(pnlWestBackGround);
		pnlWestBackGround.setLayout(new BoxLayout(pnlWestBackGround, BoxLayout.Y_AXIS));
		pnlWestBackGround.add(Box.createVerticalStrut(10));
//		Mã
		lblMaLoaiDichVu = new JLabel("Mã loại dịch vụ:");
		lblMaLoaiDichVu.setFont(new Font("Dialog", Font.BOLD, 12));
		lblMaLoaiDichVu.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtMaLoaiDV = new JTextField();
		txtMaLoaiDV.setPreferredSize(new Dimension(120, 25));
		txtMaLoaiDV.setEditable(false);
		Box b = Box.createVerticalBox();
		Box c = Box.createHorizontalBox();
		b.add(lblMaLoaiDichVu);
		c.add(Box.createHorizontalStrut(10));
		c.add(txtMaLoaiDV);
		c.add(Box.createHorizontalStrut(10));
		b.add(Box.createVerticalStrut(10));
		b.add(c);
		b.add(Box.createVerticalStrut(15));
		pnlWestBackGround.add(b);
//		Tên
		lblTen = new JLabel("Tên loại dịch vụ:");
		lblTen.setFont(new Font("Dialog", Font.BOLD, 12));
		lblTen.setAlignmentX(Component.CENTER_ALIGNMENT);
		txtTenLoaiDV = new JTextField();
		txtTenLoaiDV.setPreferredSize(new Dimension(120, 25));
		Box b1 = Box.createVerticalBox();
		b1.setEnabled(false);
		Box c1 = Box.createHorizontalBox();
		b1.add(lblTen);
		c1.add(Box.createHorizontalStrut(10));
		c1.add(txtTenLoaiDV);
		c1.add(Box.createHorizontalStrut(10));
		b1.add(Box.createVerticalStrut(10));
		b1.add(c1);
		b1.add(Box.createVerticalStrut(350));
		pnlWestBackGround.add(b1);
//		

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
		btnThem.setFont(new Font("Dialog", Font.BOLD, 12));
		btnXoa = new JButton("Xóa", getIcon("data/images/icon_btnXoa.png", 16, 16));
		btnXoa.setFont(new Font("Dialog", Font.BOLD, 12));
		btnSua = new JButton("Sửa", getIcon("data/images/icon_btnLuu.png", 16, 16));
		btnSua.setFont(new Font("Dialog", Font.BOLD, 12));
		btnXoaTrang = new JButton("Xóa trắng", getIcon("data/images/icon_btnXoaTrang.png", 16, 16));
		btnXoaTrang.setFont(new Font("Dialog", Font.BOLD, 12));

		pnlSouthBackGround.add(btnThem);
		pnlSouthBackGround.add(btnXoa);
		pnlSouthBackGround.add(btnSua);
		pnlSouthBackGround.add(btnXoaTrang);

		pnlCenter.add(pnlNorthBackGround, BorderLayout.NORTH);
		pnlCenter.add(pnlSouthBackGround, BorderLayout.SOUTH);
		taoBang();

		btnThem.addActionListener(this);
		btnXoa.addActionListener(this);
		btnSua.addActionListener(this);
		btnXoaTrang.addActionListener(this);
		this.add(pnlWest, BorderLayout.WEST);
		this.add(pnlCenter, BorderLayout.CENTER);
		this.setVisible(true);
		docDuLieuDBVaoTable();
	}

	private void taoBang() {
		// TODO Auto-generated method stub
		model = new DefaultTableModel();
		tblLoaiDV = new JTable(model);

		model.addColumn("Mã loại dịch vụ");
		model.addColumn("Tên loại dịch vụ");
		DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) tblLoaiDV.getTableHeader().getDefaultRenderer();
		renderer.setHorizontalAlignment(SwingConstants.LEFT);
		tblLoaiDV.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));

		JScrollPane scrLDV = new JScrollPane(tblLoaiDV, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrLDV.setPreferredSize(new Dimension(1001, 431));
		tblLoaiDV.setRowHeight(25);
		pnlNorthBackGround.add(scrLDV);
		tblLoaiDV.addMouseListener(new MouseListener() {

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
				int row = tblLoaiDV.getSelectedRow();
				txtMaLoaiDV.setText(model.getValueAt(row, 0).toString());
				txtTenLoaiDV.setText(model.getValueAt(row, 1).toString());

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

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnThem)) {
			try {
				if(validData(1)) {
					String ma = txtMaLoaiDV.getText();
					String ten = txtTenLoaiDV.getText();
					LoaiDichVu ldv = new LoaiDichVu(ma, ten);
					try {
						ldv_dao.create(ldv);
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						model.addRow(new Object[] {ldv_dao.getLoaiDichVuMoiNhat().getMaLoaiDichVu(),ten});
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					xoaTrang();
					JOptionPane.showMessageDialog(this, "Đã thêm loại dịch vụ mới thành công!");
				}
			} catch (HeadlessException | RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			

		} else if (e.getSource().equals(btnXoaTrang)) {
			xoaTrang();
		} else if(e.getSource().equals(btnSua)) {
			if (tblLoaiDV.getSelectedRow() == -1)
				JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần sửa");
			else
				try {
					if (validData(0)) {
						String ma = txtMaLoaiDV.getText().trim();
						String ten = txtTenLoaiDV.getText().trim();
						LoaiDichVu ldvMoi = new LoaiDichVu(ma, ten);
						try {
							if(ldv_dao.update(ldvMoi)) {
								model.setRowCount(0);
								docDuLieuDBVaoTable();
								xoaTrang();
								JOptionPane.showMessageDialog(this, "Đã cập nhập thành công");
							}
						} catch (HeadlessException | RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		else if(e.getSource().equals(btnXoa)) {
			int row = tblLoaiDV.getSelectedRow();
			if(row==-1) {
				JOptionPane.showMessageDialog(this, "Chưa chọn dòng cần xóa");
			}
			else if(row !=-1) {
				String maDV = (String) tblLoaiDV.getModel().getValueAt(row, 0);
				int comfirm = JOptionPane.showConfirmDialog(this, "Chắc chắn xóa không?", "Chú ý",
						JOptionPane.YES_NO_OPTION);
				if(comfirm == JOptionPane.YES_OPTION) {
					try {
						if(ldv_dao.delete(maDV)) {
							for (int i = model.getRowCount() - 1; i >= 0; i--) {
								model.removeRow(i);
							}
							docDuLieuDBVaoTable();
							xoaTrang();
							JOptionPane.showMessageDialog(this, "Đã xóa thành công");
						}
					} catch (HeadlessException | RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		}
	}

	private void xoaTrang() {
		// TODO Auto-generated method stub
		txtMaLoaiDV.setText("");
		txtTenLoaiDV.setText("");
		tblLoaiDV.clearSelection();
		txtTenLoaiDV.requestFocus();
	}

	private void docDuLieuDBVaoTable() throws RemoteException {
		ArrayList<LoaiDichVu> ds = ldv_dao.getAlltbLoaiDichVu();
		for (LoaiDichVu loaidichvu : ds) {
			model.addRow(new Object[] { loaidichvu.getMaLoaiDichVu(), loaidichvu.getTenLoaiDichVu() });
		}
	}
	private boolean validData(int i ) throws RemoteException {
	    String ten = txtTenLoaiDV.getText().trim().toLowerCase(); 
	    boolean trung = false;
		ArrayList<LoaiDichVu> ds = ldv_dao.getAlltbLoaiDichVu();
		for (LoaiDichVu loaidichvu : ds) {
	        if (ten.equals(loaidichvu.getTenLoaiDichVu().toLowerCase())) { 
	            trung = true;
	        }
	        i+=1;
	    }
	    
	    if (i == 1) {
	        trung = false;
	    }
	    
	    if (ten.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Không được để tên trống");
	        txtTenLoaiDV.requestFocus();
	        return false;
	    } else if (trung) {
	        JOptionPane.showMessageDialog(this, "Đã có tên loại dịch vụ này. Hãy nhập một cái tên khác");
	        txtTenLoaiDV.requestFocus();
	        return false;
	    }
	    
	    return true;
	}

}
