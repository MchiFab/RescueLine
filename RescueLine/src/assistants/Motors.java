package assistants;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;

/**
 * Class used to handle the Motors
 *
 */
public class Motors {
	private static EV3LargeRegulatedMotor motorLeft;
	private static EV3LargeRegulatedMotor motorRight;
	// private static EV3LargeRegulatedMotor motorMiddle;

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
		// motorMiddle = new EV3LargeRegulatedMotor(middle);

		motorLeft.setSpeed(MOTOR_SPEED);
		motorRight.setSpeed(MOTOR_SPEED);
		// motorMiddle.setSpeed(MOTOR_SPEED);

		// lockShovel();
		motorLeft.synchronizeWith(new EV3LargeRegulatedMotor[] { (EV3LargeRegulatedMotor) motorRight });
	}

	public static void setSpeed(int speed) {
		motorLeft.setSpeed(speed);
		motorRight.setSpeed(speed);
	}

	public static void resetSpeed() {
		motorLeft.setSpeed(MOTOR_SPEED);
		motorRight.setSpeed(MOTOR_SPEED);
		// motorMiddle.setSpeed(MOTOR_SPEED);
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

	public static void forwardTimed(int millisec) {
		long t = System.currentTimeMillis() + millisec;

		while (System.currentTimeMillis() < t)
			forward(0);

		stop(false);
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
	 * Stop Motors
	 * 
	 * @param immediateReaturn If true do not wait for the motor to actually stop
	 */
	public static void stop(boolean immediateReaturn) {
		motorLeft.startSynchronization();

		motorLeft.stop(immediateReaturn);
		motorRight.stop(immediateReaturn);

		motorLeft.endSynchronization();
	}

//	public static void lockShovel() {
//		motorMiddle.rotate(-2000);
//		motorMiddle.stop();
//	}

	/**
	 * Dispose motors
	 */
	public static void dispose() {
		if (motorLeft != null)
			motorLeft.close();
		if (motorRight != null)
			motorRight.close();
	}
}