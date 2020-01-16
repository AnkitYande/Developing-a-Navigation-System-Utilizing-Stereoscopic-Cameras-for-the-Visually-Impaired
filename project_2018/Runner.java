package project_2018;

import java.awt.*;
import javax.swing.*;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class Runner {


	public static void main(String arg[]) throws InterruptedException {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		// Initialize
		ColorDetector ColorDetector0 = new ColorDetector();
		ColorDetector ColorDetector1 = new ColorDetector();
		Mat webcam_image0 = new Mat();
		Mat webcam_image1 = new Mat();
		VideoCapture webCam1 = new VideoCapture(1);
		VideoCapture webCam0 = new VideoCapture(2);
		JavaPanel facePanel0 = new JavaPanel();
		JavaPanel facePanel1 = new JavaPanel();
		JFrame frame0 = new JFrame("ball detection cam0");
		JFrame frame1 = new JFrame("ball detection cam1");

		Double Distance;

		// Setup
		frame0.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame0.add(facePanel0, BorderLayout.CENTER);
		frame1.add(facePanel1, BorderLayout.CENTER);
		frame0.setVisible(true);
		frame1.setVisible(true);

		if (webCam0.isOpened() && webCam1.isOpened()) {
			Thread.sleep(1000); // Delay for cameras to initialize

			while (true) { // Main Loop
				webCam0.read(webcam_image0);
				webCam1.read(webcam_image1);

				// Flip the camera image
				Core.flip(webcam_image0, webcam_image0, +1);
				Core.flip(webcam_image1, webcam_image1, +1);

				if (!webcam_image1.empty()) {

					frame0.setSize(webcam_image0.width() / 2 + 10, webcam_image0.height() / 2 + 10);
					frame1.setSize(webcam_image1.width() / 2 + 10, webcam_image1.height() / 2 + 10);

					// Detect Object
					webcam_image0 = ColorDetector0.detect(webcam_image0);
					webcam_image1 = ColorDetector1.detect(webcam_image1);

					// Calculate distance
					Distance = CalcDistance(ColorDetector0, ColorDetector1);

					// Display the image
					facePanel0.matToBufferedImage(webcam_image0);
					facePanel1.matToBufferedImage(webcam_image1);

					// Audio Output
					TTS(Distance);
					if(Distance<Integer.MAX_VALUE)
					System.out.println(Distance);

					facePanel0.repaint();
					facePanel1.repaint();

				} else {
					System.out.println("Error: No captured frame from webcam !");
					break;
				}
			}
		}
		webCam0.release(); // release the webCams
		webCam1.release();

	} // end main

	
	
	
	// Distance calculation method
	private static Double CalcDistance(ColorDetector colorDetector0, ColorDetector colorDetector1) {
		double distCams = 10;
		double xPix = colorDetector0.width;
		double viewAngle = 64 * Math.PI / 180;
		double Δx = Math.abs(colorDetector0.center.x - colorDetector1.center.x);

		return (distCams * xPix / (2 * Math.tan(viewAngle / 2))) * (Math.pow(Δx, -0.961));

	}
	
	
	

	// Text to Speech method
	private static void TTS(Double dist) {
		Voice voice;
		String text;
		VoiceManager voiceManager = VoiceManager.getInstance();
		voice = voiceManager.getVoice("kevin");
		voice.allocate();

		dist = (double) Math.round(dist * 100) / 100;

		if (dist < Integer.MAX_VALUE)
			text = "The object is " + (double) Math.round(dist * 100) / 100 + " centimeters away";
		else
			text = "Object not found";

		voice.speak(text);
	}
	

}