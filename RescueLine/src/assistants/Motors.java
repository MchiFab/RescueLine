package assistants;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.Port;
import lejos.robotics.MirrorMotor;
import lejos.robotics.RegulatedMotor;

/**
 * Class used to handle the Motors
 *
 */
public class Motors {
	private static EV3LargeRegulatedMotor motorLeft;
	private static EV3LargeRegulatedMotor motorRight;
	private static EV3LargeRegulatedMotor motorMiddle;

	private static final int MOTOR_SPEED = 300;

	/*
	 * Initializing all three motors and setting their speed, synchronizes the two
	 * driving motors
	 * 
	 * @param left Left Motor
	 * 
	 * @param right Right Motor
	 * 
	 * @param middle Middle Motor
	 */
	public static void initialize(Port left, Port middle, Port right) {
		motorLeft = new EV3LargeRegulatedMotor(left);
		motorRight = new EV3LargeRegulatedMotor(right);
		motorMiddle = new EV3LargeRegulatedMotor(middle);

		motorLeft.setSpeed(MOTOR_SPEED);
		motorRight.setSpeed(MOTOR_SPEED);
		motorMiddle.setSpeed(MOTOR_SPEED);

		motorLeft.synchronizeWith(new EV3LargeRegulatedMotor[] { (EV3LargeRegulatedMotor) motorRight });
	}

	/**
	 * Drive backwards
	 * 
	 * @param deg Degree of motor-rotation, pass 0 to drive indefinitely
	 */
	public static void backward(int deg) {
		motorLeft.startSynchronization();

		if (deg == 0) {
			motorLeft.forward();
			motorRight.forward();
		} else {
			motorLeft.rotate(deg);
			motorRight.rotate(deg);
		}

		motorLeft.endSynchronization();
	}

	/**
	 * Drive forward
	 * 
	 * @param deg Degree of motor-rotation, pass 0 to drive indefinitely
	 */
	public static void forward(int deg) {
		motorLeft.startSynchronization();

		if (deg == 0) {
			motorLeft.backward();
			motorRight.backward();
		} else {
			motorLeft.rotate(-deg);
			motorRight.rotate(-deg);
		}

		motorLeft.endSynchronization();
	}

	/**
	 * Drive left
	 * 
	 * @param v Base velocity, right motor will have v * 1.5
	 */
	public static void DriveLeft(int v) {
		motorLeft.startSynchronization();

		motorLeft.setSpeed(v);
		motorRight.setSpeed((int) (v * 1.5));

		motorLeft.endSynchronization();
	}

	/**
	 * Drive right
	 * 
	 * @param v Base velocity, left motor will have v * 1.5
	 */
	public static void DriveRight(int v) {
		motorLeft.startSynchronization();

		motorRight.setSpeed(v);
		motorLeft.setSpeed((int) (v * 1.5));

		motorLeft.endSynchronization();
	}

	/**
	 * Turn left
	 * 
	 * @param deg Degree of motor-rotation, pass 0 to rotate indefinitely
	 */
	public static void rotateLeft(int deg) {
		motorLeft.startSynchronization();

		if (deg == 0) {
			motorLeft.forward();
			motorRight.backward();
		} else {
			motorLeft.rotate(deg);
			motorRight.rotate(-deg);
		}

		motorLeft.endSynchronization();
	}

	/**
	 * Turn right
	 * 
	 * @param deg Degree of motor-rotation, pass 0 to rotate indefinitely
	 */
	public static void rotateRight(int deg) {
		motorLeft.startSynchronization();

		if (deg == 0) {
			motorRight.forward();
			motorLeft.backward();
		} else {
			motorLeft.rotate(-deg);
			motorRight.rotate(deg);
		}

		motorLeft.endSynchronization();
	}

	/**
	 * Turn left
	 * 
	 * @param deg             Degree of motor-rotation
	 * @param immediateReturn If true do not wait for move to complete
	 */
	public static void rotateLeft(int deg, boolean immediateReturn) {
		motorLeft.startSynchronization();

		motorLeft.rotate(deg, immediateReturn);
		motorRight.rotate(-deg, immediateReturn);

		motorLeft.endSynchronization();
	}

	/**
	 * Turn right
	 * 
	 * @param deg             Degree of motor-rotation
	 * @param immediateReturn If true do not wait for move to complete
	 */
	public static void rotateRight(int deg, boolean immediateReturn) {
		motorLeft.startSynchronization();

		motorLeft.rotate(-deg, immediateReturn);
		motorRight.rotate(deg, immediateReturn);

		motorLeft.endSynchronization();
	}

	/**
	 * Stop motors
	 */
	public static void MotorStop() {
		motorLeft.startSynchronization();

		motorLeft.stop();
		motorRight.stop();

		motorLeft.endSynchronization();
	}

	/**
	 * Dispose motors
	 */
	public static void Dispose() {
		if (motorLeft != null)
			motorLeft.close();
		if (motorRight != null)
			motorRight.close();
	}
}