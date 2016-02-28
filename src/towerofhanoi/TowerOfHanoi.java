package towerofhanoi;

import processing.core.PApplet;


public class TowerOfHanoi extends PApplet {

	public void settings() {
		size(1000, 1000);
	}
	
	public void setup() {
		background(255);
		println("test");
	}

	public void draw() {
	}
	
	public static void main(String _args[]) {
		PApplet.main(new String[] { towerofhanoi.TowerOfHanoi.class.getName() });
	}
}
