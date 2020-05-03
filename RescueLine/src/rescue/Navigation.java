package rescue;

import assistants.*;
import sensors.*;

/**
 * Class used to check environmental conditions for navigation
 * 
 * @author michi
 */
public class Navigation {

	private boolean allowForward;
	private boolean cornerActiveL;
	private boolean cornerActiveR;

	private float[] prevColorL;
	private float[] prevColorR;

	private Offset offset;

	public Navigation() {
		allowForward = true;
		cornerActiveL = true;
		cornerActiveR = true;
		offset = Offset.None;
	}

	public boolean isAllowForward() {
		return allowForward;
	}

	public Offset getOffset() {
		return offset;
	}

	/**
	 * Check the surroundings of the robot, whether it may drive forward or whether
	 * there is an offset to the left/right
	 * 
	 * @param colorL Left Color-Sensor data
	 * @param colorM Middle Color-Sensor data
	 * @param colorR Right Color-Sensor data
	 */
	public void check(float[] colorL, float[] colorM, float[] colorR) {
		updateColors(colorL, colorR);
		checkCorners(colorL, colorR);

		if (Colors.isBlack(colorM)) {
			allowForward = true;
			offset = Offset.None;
		} else if (Colors.isWhite(colorM)) {
			if (Colors.isWhite(colorM) && Colors.isWhite(colorM)) {
				allowForward = true;
				offset = Offset.None;
			}

			if (Colors.isBlack(colorR) || Colors.isBlack(colorL)) {
				allowForward = false;
				offset = Colors.isBlack(colorR) ? Offset.Right : Offset.Left;
			}
		}

		if (isObstacleAhead(SensorManager.getInstance().getUltrasonic().getDistance()))
			avoidObstacle(colorM);

		if (Colors.isWhite(colorL))
			cornerActiveL = true;
		if (Colors.isWhite(colorM))
			cornerActiveR = true;
	}

	public void turnLeft(int degree) {
		SensorManager.getInstance().getGyro().resetAngleMax();
		Motors.setSpeed(100);

		while (SensorManager.getInstance().getGyro().getAngle() > (360 - degree))
			Motors.rotateLeft(5, true);

		Motors.stop(true);
		Motors.resetSpeed();
	}

	public void turnRight(int degree) {
		SensorManager.getInstance().getGyro().resetAngleMin();
		Motors.setSpeed(100);

		while (SensorManager.getInstance().getGyro().getAngle() < degree)
			Motors.rotateRight(5, true);
		Motors.stop(true);
		Motors.resetSpeed();
	}

	private void updateColors(float[] colorL, float[] colorR) {
		prevColorL = colorL;
		prevColorR = colorR;
	}

	/**
	 * Check for corner marks on the path and changes direction accordingly
	 * 
	 * @param colorL Left Color-Sensor data
	 * @param colorR Right Color-Sensor data
	 */
	private void checkCorners(float[] colorL, float[] colorR) {
		if (Colors.isYellow(colorL) || Colors.isYellow(colorR)) {
			if (Colors.isYellow(colorL) && Colors.isBlack(prevColorL))
				cornerActiveL = false;
			if (Colors.isYellow(colorR) && Colors.isBlack(prevColorR))
				cornerActiveR = false;

			if (cornerActiveL && cornerActiveR) {
				if (Colors.isYellow(colorL) && Colors.isYellow(colorR))
					turnLeft(180);
			}

			if (cornerActiveL)
				if (Colors.isYellow(colorL))
					turnLeft(80);

			if (cornerActiveR)
				if (Colors.isYellow(colorR))
					turnRight(80);
		}
	}

	/**
	 * Check if robot is approaching obstacle
	 * 
	 * @param distance Distance measured by Ultrasonic-Sensor
	 * @return True if robot is less than 10 cm away from obstacle
	 */
	private boolean isObstacleAhead(int distance) {
		return distance < 100;
	}

	/**
	 * Preprogrammed course to avoid an obstacle on to the left side
	 * 
	 * @param colorM Middle-Color-Sensor data
	 */
	private void avoidObstacle(float[] colorM) {
		turnLeft(45);
		Motors.forwardTimed(3000);

		turnRight(45);
		Motors.forwardTimed(2000);

		turnRight(45);
		Motors.forward(0);
	}
}
