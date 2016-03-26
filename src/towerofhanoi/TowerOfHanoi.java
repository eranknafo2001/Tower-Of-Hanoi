package towerofhanoi;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

import processing.core.PApplet;
import towerofhanoi.global.Rect;

public class TowerOfHanoi extends PApplet {

	public static void main(String _args[]) {
		PApplet.main(new String[] { towerofhanoi.TowerOfHanoi.class.getName() });
	}

	private int startHeight = 10, whatIsToMove = -1;
	private double speed = 500;
	private RingsPlaces ringsPlaces = new RingsPlaces(startHeight, this);
	private Rect rectForSpeedSlader = new Rect();
	private long startTime;
	private boolean isIHoldingSomething = false, autoOrgolazing = true, skip = false;
	private int[] timePastFromStar = new int[3];

	public void settings() {
		size(700, 600);
	}

	public void setup() {
		surface.setResizable(true);
		SetRingsSize();
		rectForSpeedSlader.setColor(color(0));
		rectForSpeedSlader.setHeight(20);
		rectForSpeedSlader.setWidth(10);
		rectForSpeedSlader.setX(16);
		startTime = System.currentTimeMillis();
		if (autoOrgolazing) {
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					new Thread(new Runnable() {
						public void run() {
							autoOrgonazing(-1, true);
						}
					}).start();
				}
			}, 1000);

		}
	}

	@SuppressWarnings("deprecation")
	public void draw() {
		Time time = new Time(System.currentTimeMillis() - startTime);
		timePastFromStar[0] = time.getSeconds();
		timePastFromStar[1] = time.getMinutes();
		timePastFromStar[2] = time.getHours() - 2;
		background(255);
		drawBase();
		SetRingsSize();
		for (int i = 0; i < ringsPlaces.getRow().length; i++) {
			for (int g = 0; g < ringsPlaces.getRow()[0].length; g++) {
				if (ringsPlaces.getRow()[i][g] != -1) {
					drawRectInPlace(ringsPlaces.getRow()[i][g], i, g);
				}
			}
		}
		if (autoOrgolazing) {
			println(rectForSpeedSlader.getX());
			drawSpeedSlader();
			float i = (rectForSpeedSlader.getX() - 10f) / (width - 40f);
			i = i * 999f + 1f;
			speed = (10000f / i);
			if (isIHoldingSomething) {
				fill(ringsPlaces.getRects()[whatIsToMove].getColor());
				stroke(0);
				strokeWeight(10);
				rect(ringsPlaces.getRects()[whatIsToMove].getX(), ringsPlaces.getRects()[whatIsToMove].getY(),
						ringsPlaces.getRects()[whatIsToMove].getWidth(),
						ringsPlaces.getRects()[whatIsToMove].getHeight());
				noStroke();
				fill(0);
			}
		} else {
			drawRingOnMouse();
		}

	}

	private void drawSpeedSlader() {
		fill(0);
		stroke(0);
		strokeWeight(5);
		line(20, height / 4, width - 20, height / 4);
		rectForSpeedSlader.setY(height / 4 - 10);
		rect(rectForSpeedSlader.getX(), rectForSpeedSlader.getY(), rectForSpeedSlader.getWidth(),
				rectForSpeedSlader.getHeight());
		noStroke();
		fill(0);
	}

	public void mousePressed() {
		if (!autoOrgolazing) {
			if (mouseY > height / 2 - 65) {
				if (isIHoldingSomething) {
					if (mouseX < width / 3) {
						putRing(0);
					} else if (mouseX < width / 3 * 2) {
						putRing(1);
					} else if (mouseX < width) {
						putRing(2);
					}
				} else {
					if (mouseX < width / 3) {
						takeRing(0);
					} else if (mouseX < width / 3 * 2) {
						takeRing(1);
					} else if (mouseX < width) {
						takeRing(2);
					}
				}
			}
		} else {
			if (width - 20 > mouseX && mouseX > 20) {
				rectForSpeedSlader.setX(mouseX - 5);
			}
		}
	}

	public void mouseDragged() {
		if (autoOrgolazing) {
			if (width - 20 > mouseX && mouseX > 20) {
				rectForSpeedSlader.setX(mouseX - 5);
			}
		}
	}

	private void autoOrgonazing(int toMove, boolean left) {
		if (toMove == ringsPlaces.getRects().length - 3) {
			if (left) {
				moveRing(0, 1);
				moveRing(0, 2);
				moveRing(1, 2);
			} else {
				moveRing(2, 1);
				moveRing(2, 0);
				moveRing(1, 0);
			}
			return;
		}
		autoOrgonazing(toMove + 1, left);
		moveRing(left ? 0 : 2, 1);
		autoOrgonazing(toMove + 1, !left);
		moveRing(1, left ? 2 : 0);
		autoOrgonazing(toMove + 1, left);
	}

	private void drawRingOnMouse() {
		if (isIHoldingSomething) {
			ringsPlaces.getRects()[whatIsToMove].setX(mouseX - ringsPlaces.getRects()[whatIsToMove].getWidth() / 2);
			ringsPlaces.getRects()[whatIsToMove].setY(mouseY - ringsPlaces.getRects()[whatIsToMove].getHeight() / 2);
			fill(ringsPlaces.getRects()[whatIsToMove].getColor());
			stroke(0);
			strokeWeight(10);
			rect(ringsPlaces.getRects()[whatIsToMove].getX(), ringsPlaces.getRects()[whatIsToMove].getY(),
					ringsPlaces.getRects()[whatIsToMove].getWidth(), ringsPlaces.getRects()[whatIsToMove].getHeight());
			noStroke();
			fill(0);
		}
	}

	private void drawRectInPlace(int rect, int placeX, int placeY) {
		int[] XYPos = ringsPlaces.getXYPos(placeX, placeY);
		int XPos = XYPos[0];
		XPos = XPos - ringsPlaces.getRects()[rect].getWidth() / 2;
		int YPos = XYPos[1];
		ringsPlaces.getRects()[rect].setX(XPos);
		ringsPlaces.getRects()[rect].setY(YPos);
		fill(ringsPlaces.getRects()[rect].getColor());
		stroke(0);
		strokeWeight(10);
		rect(ringsPlaces.getRects()[rect].getX(), ringsPlaces.getRects()[rect].getY(),
				ringsPlaces.getRects()[rect].getWidth(), ringsPlaces.getRects()[rect].getHeight());
		noStroke();
		fill(0);
	}

	private void drawBase() {
		fill(0);
		noStroke();
		textFont(createDefaultFont(50));
		text("Time Past : " + timePastFromStar[2] + " h " + timePastFromStar[1] + " m " + timePastFromStar[0] + " s",
				25, 60);
		rect(width / 16, height - 10 - 50, width / 4, 50);
		rect(width / 16 * 2 + width / 4, height - 10 - 50, width / 4, 50);
		rect(width / 16 * 3 + width / 4 * 2, height - 60, width / 4, 50);
		rect(width / 16 + ((width / 4) / 2) - 5, height / 2 - 60, 10, height / 2);
		rect(width / 16 * 2 + width / 4 + ((width / 4) / 2) - 5, height / 2 - 60, 10, height / 2);
		rect(width / 16 * 3 + width / 4 * 2 + ((width / 4) / 2) - 5, height / 2 - 60, 10, height / 2);
		noStroke();
		fill(0);
	}

	private void SetRingsSize() {
		for (int i = 0; i < ringsPlaces.getRects().length; i++) {
			float revrtI = ringsPlaces.getRects().length - i;
			float maxWidth = width / 4;
			float g = revrtI / ((float) ringsPlaces.getRects().length + 1f);
			float finelResult = g * maxWidth;
			ringsPlaces.getRects()[i].setWidth((int) finelResult);
			ringsPlaces.getRects()[i].setHeight(((height / 2) / ringsPlaces.getRects().length) - 9);
		}
	}

	private void takeRing(int v) {
		int whatPlaceToMove = -1;
		whatPlaceToMove = whatIsTopRing(v);
		if (whatPlaceToMove != -1) {
			whatIsToMove = ringsPlaces.getRow()[v][whatPlaceToMove];
			isIHoldingSomething = true;
			ringsPlaces.setRow(v, whatPlaceToMove, -1);
		}
	}

	private void putRing(int v) {
		int whatPlaceToMove = -1;
		whatPlaceToMove = whatIsTopRing(v);
		if (whatPlaceToMove != -1) {
			if (whatIsToMove > ringsPlaces.getRow()[v][whatPlaceToMove]) {
				ringsPlaces.setRow(v, whatPlaceToMove + 1, whatIsToMove);
				whatIsToMove = -1;
				isIHoldingSomething = false;
			}
		} else {
			ringsPlaces.setRow(v, whatPlaceToMove + 1, whatIsToMove);
			whatIsToMove = -1;
			isIHoldingSomething = false;
		}
	}

	private void moveRing(int i, int g) {
		// try {
		// Thread.sleep(speed);
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// }
		if (!skip) {
			int whatPlaceToMove = -1, whatPlaceToMove2 = -1;
			takeRing(i);
			whatPlaceToMove = whatIsTopRing(i);
			whatPlaceToMove++;
			int[] XYPos = ringsPlaces.getXYPos(i, whatPlaceToMove);
			int XPos = XYPos[0];
			XPos = XPos - ringsPlaces.getRects()[whatIsToMove].getWidth() / 2;
			int YPos = XYPos[1];
			whatPlaceToMove2 = whatIsTopRing(g);
			whatPlaceToMove2++;
			int[] XY2Pos = ringsPlaces.getXYPos(g, whatPlaceToMove2);
			int X2Pos = XY2Pos[0];
			X2Pos = X2Pos - ringsPlaces.getRects()[whatIsToMove].getWidth() / 2;
			int Y2Pos = XY2Pos[1];
			try {
				for (int k = 0; k < speed; k++) {
					ringsPlaces.getRects()[whatIsToMove].setX(XPos);
					ringsPlaces.getRects()[whatIsToMove].setY(
							(int) lerp(YPos, height / 4 + (((height / 2) / ringsPlaces.getRects().length) - 9) + 30,
									(float) ((float) k / (speed - 1f))));
					Thread.sleep(1);
				}
				for (int k = 0; k < speed; k++) {
					ringsPlaces.getRects()[whatIsToMove]
							.setY(height / 4 + (((height / 2) / ringsPlaces.getRects().length) - 9) + 30);
					ringsPlaces.getRects()[whatIsToMove]
							.setX((int) lerp(XPos, X2Pos, (float) ((float) k / (speed - 1f))));
					Thread.sleep(1);
				}
				for (int k = 0; k < speed; k++) {
					ringsPlaces.getRects()[whatIsToMove]
							.setY((int) lerp(height / 4 + (((height / 2) / ringsPlaces.getRects().length) - 9) + 30,
									Y2Pos, (float) ((float) k / (speed - 1f))));
					ringsPlaces.getRects()[whatIsToMove].setX(X2Pos);
					Thread.sleep(1);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		putRing(g);
	}

	private int whatIsTopRing(int v) {
		int whatPlaceToMove = -1;
		for (int i = ringsPlaces.getRow()[0].length - 1; i > -1; i--) {
			if (ringsPlaces.getRow()[v][i] != -1) {
				whatPlaceToMove = i;
				break;
			}
		}
		return whatPlaceToMove;
	}

}
