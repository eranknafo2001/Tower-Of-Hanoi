package towerofhanoi;

import towerofhanoi.global.Rect;

public class RingsPlaces {
	private Rect[] rects;
	private int[][] row;
	TowerOfHanoi towerOfHanoi;

	public int[] getXYPos(int i, int g) {
		int[] s = new int[2];
		if (i == 0) {
			s[0] = (towerOfHanoi.width / 16 + ((towerOfHanoi.width / 4) / 2) - 5) + 5;
		} else if (i == 1) {
			s[0] = (towerOfHanoi.width / 16 * 2 + towerOfHanoi.width / 4 + ((towerOfHanoi.width / 4) / 2) - 5) + 5;
		} else if (i == 2) {
			s[0] = (towerOfHanoi.width / 16 * 3 + towerOfHanoi.width / 4 * 2 + ((towerOfHanoi.width / 4) / 2) + 5) - 5;
		}

		s[1] = towerOfHanoi.height - ((getRects()[1].getHeight() + 9) * g + 56);
		return s;

	}

	public RingsPlaces(int rectsLengt, TowerOfHanoi towerOfHanoi) {
		this.towerOfHanoi = towerOfHanoi;
		row = new int[3][rectsLengt];
		rects = new Rect[rectsLengt];
		for (int i = 0; i < rects.length; i++) {
			rects[i] = new Rect();
		}
		for (int i = 0; i < row.length; i++) {
			if (i != 0) {
				for (int g = 0; g < row[0].length; g++) {
					row[i][g] = -1;
				}
			} else {
				for (int g = 0; g < row[0].length; g++) {
					row[i][g] = g;
				}
			}
		}
	}

	public Rect[] getRects() {
		return rects;
	}

	public Rect getRects(int i) {
		return rects[i];
	}

	public void setRects(Rect[] rects) {
		this.rects = rects;
	}

	public void setRects(int i, Rect rects) {
		this.rects[i] = rects;
	}

	public int[][] getRow() {
		return row;
	}

	public void setRow(int x, int y, int row) {
		this.row[x][y] = row;
	}

}
