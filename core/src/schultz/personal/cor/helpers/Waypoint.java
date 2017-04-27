package schultz.personal.cor.helpers;

import com.badlogic.gdx.math.Vector2;

public class Waypoint {
	
	private Vector2 pos;
	private Vector2 midway;
	private Car targetCar;
	
	public Waypoint(Vector2 pos, Car targetCar) {
		this.pos = pos;
		this.targetCar = targetCar;
		midway = new Vector2(pos.x/2, pos.y/2); // halfway between
	}
	
	public Vector2 getVector() {
		return pos;
	}
	
	public Car getTargetCar() {
		return targetCar;
	}
	
	public Vector2 getMidway() {
		return midway;
	}
	
}
