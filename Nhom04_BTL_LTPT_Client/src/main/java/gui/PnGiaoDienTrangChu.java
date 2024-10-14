package gui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PnGiaoDienTrangChu extends JPanel {
	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		BufferedImage bgImage = null;
		try {
			bgImage = ImageIO.read(new File("data/images/trangChu.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int width = getWidth();
		int height = getHeight();
		g.drawImage(bgImage, 0, 0, width, height, null);
	}

	public PnGiaoDienTrangChu() {
		// TODO Auto-generated constructor stub
		this.setVisible(true);
	}
}
