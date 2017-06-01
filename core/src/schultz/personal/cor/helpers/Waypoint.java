package schultz.personal.cor.helpers;

import com.badlogic.gdx.math.Vector2;

public class Waypoint {
	
	private Vector2 pos;
	private Vector2 midway;
	private Vector2 dist; // dist between current targetCar midPos and waypoint midPos
	private Car targetCar;
	private boolean stopHere;
	
	public Waypoint(Vector2 pos, Car targetCar, boolean stopHere) {
		this.pos = pos;
		this.targetCar = targetCar;

		if(targetCar.getMidXPos() == pos.x)
			midway = new Vector2(pos.x, (targetCar.getMidYPos() + pos.y)/2);
		if(targetCar.getMidYPos() == pos.y)
			midway = new Vector2((targetCar.getMidXPos() + pos.x)/2, pos.y);
		else
			midway = new Vector2((targetCar.getMidXPos() + pos.x)/2, (targetCar.getMidYPos() + pos.y)/2);
		
		this.stopHere = stopHere;
	}
	
	public Vector2 getMidpointDist() {
		return new Vector2(targetCar.getMidXPos() - midway.x, targetCar.getMidYPos() - midway.y);
	}
	
	public Vector2 getDist() {		
		return new Vector2(targetCar.getMidXPos() - pos.x, targetCar.getMidYPos() - pos.y);
	}
	
	public Vector2 getPos() {
		return pos;
	}
	
	public Car getTargetCar() {
		return targetCar;
	}
	
	public Vector2 getMidway() {
		return midway;
	}
	
	public boolean getStopHere() {
		return stopHere;
	}
	
}
