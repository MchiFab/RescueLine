package rescue;

import assistants.*;
import sensors.*;

import java.text.*;

import lejos.hardware.lcd.*;
import lejos.hardware.port.MotorPort;
import lejos.hardware.port.SensorPort;
import lejos.utility.DebugMessages;
import lejos.hardware.Battery;

import lejos.hardware.*;

public class Program {

	private static DecimalFormat d = new DecimalFormat("0.00");
	private static Navigation nav;
	private static float[] colorL;
	private static float[] colorR;
	private static float[] colorM;

	public static void main(String[] args) {
		try {
			if (isBatteryLow()) {
				LCD.drawString("Battery low", 0, 0);
				Button.waitForAnyPress();
				return;
			}

			initialize();

			Thread t = new Thread() {
				public void run() {
					while (true) {
						int btn = Button.waitForAnyPress(1000);
						if (btn == Button.ID_ESCAPE) {
							System.out.println("ev3 is kil");
							System.out.println("no");
							Motors.stop(true);
							Motors.dispose();
							SensorManager.getInstance().dispose();
							System.exit(0);
						}
					}
				}
			};
			t.start();

			// wait for input before launch
			LCD.clear();
			LCD.drawString("ok buddy", 2, 2);
			Button.waitForAnyPress();
			LCD.clear();
			ColorSensor colorInstance = SensorManager.getInstance().getColor();
			DebugMessages me = new DebugMessages();

			while (true) {
//				me.echo("loop started");

				if (Button.ENTER.isDown() || isBatteryLow()) {
					Motors.stop(true);
					break;
				}
//				me.echo("button not pressed");
				colorL = colorInstance.getColorLeft();
				colorR = colorInstance.getColorRight();
				colorM = colorInstance.getColorMiddle();
//				me.echo("got colors");
//				LCD.drawString("L: " + colorL[0] + " " + colorL[1] + " " + colorL[2], 0, 0);
//				LCD.drawString("R: " + colorR[0] + " " + colorR[1] + " " + colorR[2], 0, 1);
//				LCD.drawString("M: " + colorM[0] + " " + colorM[1] + " " + colorM[2], 0, 2);

//				if (Button.DOWN.isDown()) {
//					System.out.print("L: ");
//					if (Colors.isBlack(colorL))
//						System.out.print("Black");
//					else if (Colors.isWhite(colorL))
//						System.out.print("White");
//					else if (Colors.isYellow(colorL))
//						System.out.print("Yellow");
//
//					System.out.println();
//
//					System.out.print("M: ");
//					if (Colors.isBlack(colorM))
//						System.out.print("Black");
//					else if (Colors.isWhite(colorM))
//						System.out.print("White");
//					else if (Colors.isYellow(colorM))
//						System.out.print("Yellow");
//
//					System.out.println();
//
//					System.out.print("R ");
//					if (Colors.isBlack(colorR))
//						System.out.print("Black");
//					else if (Colors.isWhite(colorR))
//						System.out.print("White");
//					else if (Colors.isYellow(colorR))
//						System.out.print("Yellow");
//				}

				nav.check(colorL, colorM, colorR);
//				me.echo("nav checked");

				if (!nav.isAllowForward()) {
					if (Colors.isBlack(colorR)) {
						nav.turnRight(20);
					}
					if (Colors.isBlack(colorL)) {
						nav.turnLeft(20);
					}
				}
//				me.echo("turned or turned not");

				if (nav.isAllowForward()) {
					Motors.forward(5);
				}
//				me.echo("drove forward");
			}

			Motors.dispose();
			SensorManager.getInstance().dispose();
			System.out.close();
			System.err.close();
			System.exit(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Print values of colors
	 */
	private static void printRGB() {
		LCD.clear();
		System.out.println("L: " + d.format(colorL[0]) + " " + d.format(colorL[1]) + " " + d.format(colorL[2]));
		System.out.println("M: " + d.format(colorM[0]) + " " + d.format(colorM[1]) + " " + d.format(colorM[2]));
		System.out.println("R: " + d.format(colorR[0]) + " " + d.format(colorR[1]) + " " + d.format(colorR[2]));
	}

	/**
	 * Initialize variables necessary for the program
	 */
	private static void initialize() {
		LCD.drawString("Loading...", 2, 2);
		Motors.initialize(MotorPort.D, MotorPort.B, MotorPort.A);
		SensorManager.getInstance().initialize(SensorPort.S1, SensorPort.S2, SensorPort.S3, SensorPort.S4);

		nav = new Navigation();
		colorL = new float[3];
		colorR = new float[3];
		colorM = new float[3];
	}

	/**
	 * Check if battery level is low
	 * 
	 * @return True if battery level is below 5.5V
	 */
	private static boolean isBatteryLow() {
		return Battery.getVoltage() < 5.5f;
	}
}
