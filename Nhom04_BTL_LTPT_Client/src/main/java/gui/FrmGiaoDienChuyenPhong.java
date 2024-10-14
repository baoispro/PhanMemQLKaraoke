package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Paint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import dao.ChiTietPhieuDatPhong_DAO;
import dao.LoaiPhong_DAO;
import dao.PhieuDatPhong_DAO;
import dao.Phong_DAO;
import entity.ChiTietPhieuDatPhong;
import entity.NhanVien;
import entity.Phong;

public class FrmGiaoDienChuyenPhong extends JFrame implements ActionListener {
	private JLabel lblPhongHienTai, lblTenPhong;
	private JTextField txtTenPhong;
	private JButton btnTimKiem, btnChuyenPhong;
	private String phongHienTai;
	private JTable tblTK;
	private DefaultTableModel model;
	private JPanel pnlSubCenter;
	private LoaiPhong_DAO lp_dao;
	private Phong_DAO p_dao;
	private ChiTietPhieuDatPhong_DAO ctpdp_dao;
	private PhieuDatPhong_DAO pdp_dao;
	private Phong phongDuocChon;
	private NhanVien nvThucThi;
	private ArrayList<Phong> dsp;
	private Map<JPanel, String> dspdc;
	private PnGiaoDienDatPhong pnl;

	public FrmGiaoDienChuyenPhong(ArrayList<Phong> dspht, NhanVien nv, Map<JPanel, String> danhSachPhongDuocChon,
			PnGiaoDienDatPhong pnlGiaoDienDatPhong) throws RemoteException, NotBoundException {
		// TODO Auto-generated constructor stub
		super("Chuyển phòng");
		Registry registry = LocateRegistry.getRegistry(2024);
		pnl = pnlGiaoDienDatPhong;
		dspdc = danhSachPhongDuocChon;
		nvThucThi = nv;
		dsp = dspht;
		lp_dao = (LoaiPhong_DAO) registry.lookup("lp_dao");
		p_dao =  (Phong_DAO) registry.lookup("p_dao");
		ctpdp_dao =  (ChiTietPhieuDatPhong_DAO) registry.lookup("ctpdp_dao");
		pdp_dao =  (PhieuDatPhong_DAO) registry.lookup("pdp_dao");
		gui();
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(1000, 500);
		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void gui() throws RemoteException {
		// TODO Auto-generated method stub
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(new BorderLayout());

		// Tạo panel header luôn xuất hiện trong mọi giao diện
		JPanel pnlHeader = new JPanel() {
			
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
		JLabel lblTitle = new JLabel("Chuyển phòng", SwingConstants.CENTER);
		lblTitle.setForeground(Color.white);
		lblTitle.setFont(new Font("Time New Roman", Font.BOLD, 30));
		pnlHeader.add(lblTitle);

		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(new BorderLayout());
		pnlCenter.setBorder(new EmptyBorder(10, 10, 10, 10));
		JPanel pnlSubNorth = new JPanel();
		pnlSubCenter = new JPanel();
		JPanel pnlSubSouth = new JPanel();

		pnlSubNorth.setLayout(new BoxLayout(pnlSubNorth, BoxLayout.X_AXIS));
		lblPhongHienTai = new JLabel("Phòng hiện tại: phòng 8 --> phòng 10");
		lblTenPhong = new JLabel("Tên phòng:");
		txtTenPhong = new JTextField();
		btnTimKiem = new JButton("Tìm kiếm", getIcon("data/images/icon_btnTK.png", 16, 16));
		pnlSubNorth.add(lblPhongHienTai);
		pnlSubNorth.add(Box.createHorizontalStrut(300));
		pnlSubNorth.add(lblTenPhong);
		pnlSubNorth.add(Box.createHorizontalStrut(10));
		pnlSubNorth.add(txtTenPhong);
		pnlSubNorth.add(Box.createHorizontalStrut(100));
		pnlSubNorth.add(btnTimKiem);

		pnlSubSouth.setLayout(new FlowLayout(FlowLayout.RIGHT));
		btnChuyenPhong = new JButton("Chuyển phòng");
		pnlSubSouth.add(btnChuyenPhong);

		taoBang();
		docDuLieuDataBaseVaoTable();

		btnTimKiem.addActionListener(this);
		btnChuyenPhong.addActionListener(this);

		pnlCenter.add(pnlSubNorth, BorderLayout.NORTH);
		pnlCenter.add(pnlSubCenter, BorderLayout.CENTER);
		pnlCenter.add(pnlSubSouth, BorderLayout.SOUTH);
		pnlMain.add(pnlHeader, BorderLayout.NORTH);
		pnlMain.add(pnlCenter, BorderLayout.CENTER);
		this.add(pnlMain);
	}

	private ImageIcon getIcon(String path, int width, int height) {
		// TODO Auto-generated method stub
		ImageIcon iconEmployee = new ImageIcon(path);
		Image scaledImage = iconEmployee.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImage);
		return scaledIcon;
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
		sp.setPreferredSize(new Dimension(967, 320));
		pnlSubCenter.add(sp);
		tblTK.addMouseListener(new MouseListener() {

			
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
				int row = tblTK.getSelectedRow();
				try {
					phongDuocChon = p_dao.getPhongTheoMa(model.getValueAt(row, 0).toString());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	private void docDuLieuDataBaseVaoTable() throws RemoteException {
		// TODO Auto-generated method stub
		ArrayList<Phong> ds = p_dao.getAlltbPhong();
		for (Phong phong : ds) {
			if (phong.getTinhTrang().equalsIgnoreCase("Phòng trống")
					|| phong.getTinhTrang().equalsIgnoreCase("Phòng chờ"))
				model.addRow(new Object[] { phong.getMaPhong(), phong.getTenPhong(),
						lp_dao.getLoaiPhongTheoMa(phong.getLp().getMaLP()).getTenLP(), phong.getTinhTrang() });
		}
	}

	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(btnTimKiem)) {
			String tenP = txtTenPhong.getText();
			if (tenP.isEmpty())
				tenP = null;
			ArrayList<Phong> ds = null;
			try {
				ds = p_dao.timKiemPhong(tenP, null, null, null);
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (ds.size() == 0) {
				JOptionPane.showMessageDialog(this, "Không tìm thấy");
			} else {
				model.setRowCount(0);
				for (Phong phong : ds) {
					try {
						model.addRow(new Object[] { phong.getMaPhong(), phong.getTenPhong(),
								lp_dao.getLoaiPhongTheoMa(phong.getLp().getMaLP()).getTenLP(), phong.getTinhTrang() });
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				JOptionPane.showMessageDialog(this, "Đã tìm kiếm thành công");
			}
		} else if (e.getSource().equals(btnChuyenPhong)) {
			long thoiGianSuDung = 0;
			try {
				thoiGianSuDung = ChronoUnit.MINUTES.between(
						pdp_dao.getPhieuDatPhongTheoMaPhong(dsp.get(0).getMaPhong()).getGioNhanPhong(), LocalTime.now());
			} catch (RemoteException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			LocalTime thoiGianSuDungLocalTime = LocalTime.MIN.plus(thoiGianSuDung, ChronoUnit.MINUTES);
//			Trường hợp phòng đó đã từng chuyển mà muốn chuyển lại lần nữa
			
//			Trường hợp chuyển phòng đang sử dụng sang phòng khác có tình trạng là phòng trống
			if (phongDuocChon.getTinhTrang().equalsIgnoreCase("phòng trống")
					&& dsp.get(0).getTinhTrang().equalsIgnoreCase("Phòng đang sử dụng")) {
				try {
					ctpdp_dao.create(new ChiTietPhieuDatPhong(phongDuocChon,
							pdp_dao.getPhieuDatPhongTheoMaPhong(dsp.get(0).getMaPhong()), null));
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					pdp_dao.update(pdp_dao.getPhieuDatPhongTheoMaPhong(dsp.get(0).getMaPhong()).getMaPDP(),
							LocalTime.now());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					ctpdp_dao.updateThoiGianSuDungPhong(thoiGianSuDungLocalTime, dsp.get(0).getMaPhong(),
							pdp_dao.getPhieuDatPhongTheoMaPhong(dsp.get(0).getMaPhong()).getMaPDP());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					p_dao.capNhatTrangThaiPhong(dsp.get(0).getMaPhong(), "phòng trống");
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					p_dao.capNhatTrangThaiPhong(phongDuocChon.getMaPhong(), "phòng đang sử dụng");
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
//			Trường hợp chuyển phòng đang sử dụng và chờ sang phòng khác có tình trạng là phòng trống
			else if (phongDuocChon.getTinhTrang().equalsIgnoreCase("phòng trống")
					&& dsp.get(0).getTinhTrang().equalsIgnoreCase("Phòng đang sử dụng và chờ")) {
				try {
					ctpdp_dao.create(new ChiTietPhieuDatPhong(phongDuocChon,
							pdp_dao.getPhieuDatPhongTheoMaPhongVaTinhTrang(dsp.get(0).getMaPhong(), "Đã xác nhận"), null));
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					pdp_dao.update(pdp_dao.getPhieuDatPhongTheoMaPhongVaTinhTrang(dsp.get(0).getMaPhong(), "Đã xác nhận")
							.getMaPDP(), LocalTime.now());
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					ctpdp_dao.updateThoiGianSuDungPhong(thoiGianSuDungLocalTime, dsp.get(0).getMaPhong(), pdp_dao
							.getPhieuDatPhongTheoMaPhongVaTinhTrang(dsp.get(0).getMaPhong(), "Đã xác nhận").getMaPDP());
				} catch (RemoteException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				try {
					p_dao.capNhatTrangThaiPhong(dsp.get(0).getMaPhong(), "phòng chờ");
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					p_dao.capNhatTrangThaiPhong(phongDuocChon.getMaPhong(), "phòng đang sử dụng");
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
//			Trường hợp chuyển phòng đang sử dụng sang phòng khác có tình trạng là phòng chờ
			else if (phongDuocChon.getTinhTrang().equalsIgnoreCase("phòng chờ")
					&& dsp.get(0).getTinhTrang().equalsIgnoreCase("Phòng đang sử dụng")) {
				try {
					if (pdp_dao.getPhieuDatPhongTheoMaPhong(dsp.get(0).getMaPhong()).getNgayNhanPhong()
							.compareTo(new Date()) != 0) {
						ctpdp_dao.create(new ChiTietPhieuDatPhong(phongDuocChon,
								pdp_dao.getPhieuDatPhongTheoMaPhong(dsp.get(0).getMaPhong()), null));
						pdp_dao.update(pdp_dao.getPhieuDatPhongTheoMaPhong(dsp.get(0).getMaPhong()).getMaPDP(),
								LocalTime.now());
						ctpdp_dao.updateThoiGianSuDungPhong(thoiGianSuDungLocalTime, dsp.get(0).getMaPhong(),
								pdp_dao.getPhieuDatPhongTheoMaPhong(dsp.get(0).getMaPhong()).getMaPDP());
						p_dao.capNhatTrangThaiPhong(dsp.get(0).getMaPhong(), "phòng trống");
						p_dao.capNhatTrangThaiPhong(phongDuocChon.getMaPhong(), "phòng đang sử dụng và chờ");
					}
					else {
						
					}
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
//			Trường hợp chuyển phòng đang sử dụng và chờ sang phòng khác có tình trạng là phòng chờ
			else if (phongDuocChon.getTinhTrang().equalsIgnoreCase("phòng chờ")
					&& dsp.get(0).getTinhTrang().equalsIgnoreCase("Phòng đang sử dụng và chờ")) {
				try {
					if (pdp_dao.getPhieuDatPhongTheoMaPhong(dsp.get(0).getMaPhong()).getNgayNhanPhong()
							.compareTo(new Date()) != 0) {
						try {
							ctpdp_dao.create(new ChiTietPhieuDatPhong(phongDuocChon,
									pdp_dao.getPhieuDatPhongTheoMaPhongVaTinhTrang(dsp.get(0).getMaPhong(), "Đã xác nhận"),
									null));
						} catch (RemoteException e5) {
							// TODO Auto-generated catch block
							e5.printStackTrace();
						}
						try {
							pdp_dao.update(pdp_dao
									.getPhieuDatPhongTheoMaPhongVaTinhTrang(dsp.get(0).getMaPhong(), "Đã xác nhận").getMaPDP(),
									LocalTime.now());
						} catch (RemoteException e4) {
							// TODO Auto-generated catch block
							e4.printStackTrace();
						}
						try {
							ctpdp_dao.updateThoiGianSuDungPhong(thoiGianSuDungLocalTime, dsp.get(0).getMaPhong(), pdp_dao
									.getPhieuDatPhongTheoMaPhongVaTinhTrang(dsp.get(0).getMaPhong(), "Đã xác nhận").getMaPDP());
						} catch (RemoteException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
						try {
							p_dao.capNhatTrangThaiPhong(dsp.get(0).getMaPhong(), "phòng chờ");
						} catch (RemoteException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
							p_dao.capNhatTrangThaiPhong(phongDuocChon.getMaPhong(), "phòng đang sử dụng và chờ");
						} catch (RemoteException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					else {
						
					}
				} catch (RemoteException e1) {
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
			JOptionPane.showMessageDialog(null, "Đã chuyển phòng thành công");
			this.dispose();
		}
	}
}
