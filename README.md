# Developing-a-Navigation-System-Utilizing-Stereoscopic-Cameras-for-the-Visually-Impaired

Similar to how humans can perceive depth with two eyes, a computer can measure
distance using a stereoscopic camera system. The goal of this project was to develop software in
Java that can successfully detect an object in two different camera feeds and then calculate the
distance between the object and the cameras using trigonometry. First, an image detection
algorithm was created using OpenCV libraries. The images from the webcam feeds were
converted to a matrix and a maximum and minimum values for the hue, saturation, and value
(HSV) of a monochromatic spherical target were determined experimentally. These values were
used to create a mask of areas in the image that were within the correct color range. Noise was
reduced by eroding the mask and clarity was added after by dilating the mask. Finally, a
bounding box was created using the contours of the region that remained and the center was
calculated. Trigonometry calculations were then performed by the computer using the center of
the object on both feeds as well as characteristics of the cameras to calculate the distance and
position of the object. This data was then compared to the actual distance and was used to create
a distortion constant for more accurate calculations. Finally, the distance and positional
information of the detected object was outputted using a text to speech software known as
FreeTTS so that someone with visual impairments could use the audio directions to locate the
object.
