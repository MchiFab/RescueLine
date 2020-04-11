package rescue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import assistants.*;
import sensors.*;

/**
 * Class used to check environmental conditions for navigation
 * 
 * @author michi
 */
public class Navigation {

	private boolean allowForward;
	private Offset offset;

	public Navigation() {
		allowForward = true;
		offset = offset.None;
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
		if (Colors.IsBlack(colorM)) {
			allowForward = true;
			offset = Offset.None;
		} else if (Colors.IsWhite(colorM)) {
			if (Colors.IsWhite(colorL) && Colors.IsWhite(colorR)) {
				allowForward = true;
				offset = Offset.None;
			}

			if (Colors.IsBlack(colorR) || Colors.IsBlack(colorL)) {
				allowForward = false;
				offset = Colors.IsBlack(colorR) ? Offset.Right : Offset.Left;
			}
		}
	}

	/**
	 * Turn robot 180 degrees to the left
	 */
	public void turn180Left() {
		SensorManager.getInstance().getGyro().resetAngleMax();

		while (SensorManager.getInstance().getGyro().getAngle() > 180) {
			Motors.rotateLeft(10, true);
		}
	}

	/**
	 * Turn robot 180 degrees to the right
	 */
	public void turn180Right() {
		SensorManager.getInstance().getGyro().resetAngleMin();

		while (SensorManager.getInstance().getGyro().getAngle() < 180) {
			Motors.rotateRight(10, true);
		}
	}

	/**
	 * Turn robot 90 degrees to the left
	 */
	public void turn90Left() {
		SensorManager.getInstance().getGyro().resetAngleMax();

		while (SensorManager.getInstance().getGyro().getAngle() > 270) {
			Motors.rotateLeft(10, true);
		}
	}

	/**
	 * Turn robot 90 degrees to the right
	 */
	public void turn90Right() {
		SensorManager.getInstance().getGyro().resetAngleMin();

		while (SensorManager.getInstance().getGyro().getAngle() < 90) {
			Motors.rotateRight(10, true);
		}
	}
}
