package project_2018;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

class JavaPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private BufferedImage image;

	// Create a constructor method
	public JavaPanel() {
		super();
	}

	/*
	 * Converts/writes a Mat into a BufferedImage.
	 * 
	 * @param matrix Mat of type CV_8UC3 or CV_8UC1
	 * 
	 * @return BufferedImage of type TYPE_3BYTE_BGR or TYPE_BYTE_GRAY
	 */
	
	public boolean matToBufferedImage(Mat matrix) {
		MatOfByte mb = new MatOfByte();
		Imgcodecs.imencode(".jpg", matrix, mb);
		try {
			this.image = ImageIO.read(new ByteArrayInputStream(mb.toArray()));
		} catch (IOException e) {
			e.printStackTrace();
			return false; // Error
		}
		return true; // Successful
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (this.image == null)
			return;
		g.drawImage(this.image, 10, 10, this.image.getWidth() / 2, this.image.getHeight() / 2, null);
	}

}
