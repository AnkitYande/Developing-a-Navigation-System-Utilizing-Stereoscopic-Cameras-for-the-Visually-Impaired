package project_2018;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
//import org.opencv.core.Point;
//import org.opencv.core.Rect;
import org.opencv.core.Scalar;
//import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ColorDetector {

	static Scalar min = new Scalar(30, 50, 95);// HSV
	static Scalar max = new Scalar(85, 255, 255);// HSV
	Point center;
	Double width;

	// constructor
	public ColorDetector() {
		center = new Point(0, 0);
		width = 0.0;
	}

	// Detects the object and creates a bounding box
	public Mat detect(Mat inputframe) {
		center = new Point(0, 0);
		width = (double) inputframe.width();

		Mat imgThreshold = new Mat();

		// Convert image from BGR to HSV
		Imgproc.cvtColor(inputframe, imgThreshold, Imgproc.COLOR_BGR2HSV);

		// Create a mask of the areas within the min and max threshold
		Core.inRange(imgThreshold, min, max, imgThreshold);

		// Erode and dilate the mask
		Mat kernelOpen = Mat.ones(15, 20, 0);
		Mat kernelClose = Mat.ones(50, 50, 0);
		Imgproc.morphologyEx(imgThreshold, imgThreshold, Imgproc.MORPH_OPEN, kernelOpen);
		Imgproc.morphologyEx(imgThreshold, imgThreshold, Imgproc.MORPH_CLOSE, kernelClose);

		// Find the contours of the mask
		Mat mat1 = new Mat();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(imgThreshold, contours, mat1, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_NONE);

		for (int i = 0; i < contours.size(); i++) {

			Rect rect = Imgproc.boundingRect(contours.get(i));

			if (Math.abs((double) rect.width / (double) rect.height) < 1.5 && // checks if width and height of the object are similar
																			
					Math.abs((double) rect.height / (double) rect.width) < 1.5) {

				// Draw the bounding rectangle
				Imgproc.rectangle(inputframe, rect.tl(), rect.br(), new Scalar(0, 200, 0), 5);

				// Calculate and draw the center point
				center = new Point((rect.tl().x + rect.br().x) / 2, (rect.tl().y + rect.br().y) / 2);
				Imgproc.rectangle(inputframe, center, center, new Scalar(255, 0, 0), 10);

			}
		}
		return inputframe;
	}

}
