package towerofhanoi;

import processing.core.PApplet;

public class TowerOfHanoi extends PApplet {

	public static void main(String _args[]) {
		PApplet.main(new String[] { towerofhanoi.TowerOfHanoi.class.getName() });
	}

	private int startHeight = 5, whatIsToMove = -1;
	private RingsPlaces ringsPlaces = new RingsPlaces(startHeight, this);
	private boolean isIHoldingSomething = false;

	public void settings() {
		size(700, 600);
	}

	public void setup() {
		surface.setResizable(true);
		SetRingsSize();
	}

	public void draw() {
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
		drawRingOnMouse();
	}

	private void drawRingOnMouse() {
		if (isIHoldingSomething) {
			ringsPlaces.getRects()[whatIsToMove].setX(mouseX - ringsPlaces.getRects()[whatIsToMove].getWidth() / 2);
			ringsPlaces.getRects()[whatIsToMove].setY(mouseY - ringsPlaces.getRects()[whatIsToMove].getHeight() / 2);
			fill(0, 0, 255);
			stroke(0);
			strokeWeight(10);
			rect(ringsPlaces.getRects()[whatIsToMove].getX(), ringsPlaces.getRects()[whatIsToMove].getY(),
					ringsPlaces.getRects()[whatIsToMove].getWidth(), ringsPlaces.getRects()[whatIsToMove].getHeight());
			noStroke();
			fill(0);
		}
	}

	public void mousePressed() {
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

	private void drawRectInPlace(int rect, int placeX, int placeY) {
		int[] XYPos = ringsPlaces.getXYPos(placeX, placeY + 1);
		int XPos = XYPos[0];
		XPos = XPos - ringsPlaces.getRects()[rect].getWidth() / 2;
		int YPos = XYPos[1];
		ringsPlaces.getRects()[rect].setX(XPos);
		ringsPlaces.getRects()[rect].setY(YPos);
		fill(0, 0, 255);
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
		rect(width / 16, height - 10 - 50, width / 4, 50);
		rect(width / 16 * 2 + width / 4, height - 10 - 50, width / 4, 50);
		rect(width / 16 * 3 + width / 4 * 2, height - 60, width / 4, 50);
		rect(width / 16 + ((width / 4) / 2) - 5, height / 2 - 60, 10, height / 2);
		rect(width / 16 * 2 + width / 4 + ((width / 4) / 2) - 5, height / 2 - 60, 10, height / 2);
		rect(width / 16 * 3 + width / 4 * 2 + ((width / 4) / 2) - 5, height / 2 - 60, 10, height / 2);
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
		for (int i = ringsPlaces.getRow()[0].length - 1; i > -1; i--) {
			if (ringsPlaces.getRow()[v][i] != -1) {
				whatPlaceToMove = i;
				break;
			}
		}
		if (whatPlaceToMove != -1) {
			whatIsToMove = ringsPlaces.getRow()[v][whatPlaceToMove];
			isIHoldingSomething = true;
			ringsPlaces.setRow(v, whatPlaceToMove, -1);
		}
	}

	private void putRing(int v) {
		int whatPlaceToMove = -1;
		for (int i = ringsPlaces.getRow()[0].length - 1; i > -1; i--) {
			if (ringsPlaces.getRow()[v][i] != -1) {
				whatPlaceToMove = i;
				break;
			}
		}
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
		takeRing(i);
		putRing(g);
	}

}
