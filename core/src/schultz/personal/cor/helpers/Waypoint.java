package schultz.personal.cor.helpers;

import java.util.ArrayList;
import com.badlogic.gdx.math.Vector2;

public class Waypoint {
	
	private Vector2 pos;
	private Vector2 midway;
	private String carID;
	
	public Waypoint(Vector2 pos, String carID) {
		this.pos = pos;
		this.carID = carID.substring(0, Math.min(carID.length(), 4));
		midway = new Vector2(pos.x/2, pos.y/2); // halfway between
	}
	
	public Vector2 getVector() {
		return pos;
	}
	
	public String getID() {
		return carID;
	}
	
	public Vector2 getMidway() {
		return midway;
	}
	
}
