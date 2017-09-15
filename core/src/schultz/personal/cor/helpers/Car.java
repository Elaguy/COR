package schultz.personal.cor.helpers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import schultz.personal.cor.main.Track1;

public class Car {
	
	private Sprite car;
	private Track1 track1;
	private float speed;
	private float hp;
	private float midXPos; // the X pos of the car, but in the middle of the car
	private float midYPos;
	private Polygon boundPoly; // the polygon associated with this car object
	private boolean isDestroyed;
	
	public Car(Sprite car, Track1 track1) {
		this.car = car;
		this.track1 = track1;
		
		speed = 0;
		hp = 100;
		
		midXPos = car.getX() + car.getOriginX();
		midYPos = car.getY() + car.getOriginY();
		
		isDestroyed = false;
	}
	
	public Vector2 getVelocity(float speed, float rotation) {
		Vector2 vel = new Vector2();
		float vx = (float) Math.cos(Math.toRadians(rotation)) * speed;
		float vy = (float) Math.sin(Math.toRadians(rotation)) * speed;
		
		vel.x = vx;
		vel.y = vy;
		
		return vel;
	}
	
	public void move() { // converts speed and rotation to velocity and sets x and y accordingly
		Vector2 vel = getVelocity(speed, car.getRotation());
		car.setX(car.getX() + vel.x);
		car.setY(car.getY() + vel.y);
		
		midXPos = car.getX() + car.getOriginX();
		midYPos = car.getY() + car.getOriginY();
		
		if(boundPoly != null) {
			boundPoly.translate(vel.x, vel.y);
		}
	}
	
	public void checkHP() {
		if(hp <= 0 && !isDestroyed) {
			isDestroyed = true;
			
			track1.getCars().remove(this);
			track1.getCarPolys().remove(this.boundPoly);
		}
	}
	
	public Sprite getSprite() {
		return car;
	}

	public float getSpeed() {
		return speed;
	}
	
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public float getHP() {
		return hp;
	}
	
	public void setHP(float hp) {
		this.hp = hp;
	}
	
	public float getMidXPos() {
		return midXPos;
	}
	
	public float getMidYPos() {
		return midYPos;
	}
	
	public void setBoundPoly(Polygon poly) {
		this.boundPoly = poly;
	}
	
	public Polygon getBoundPoly() {
		return boundPoly;
	}
	
	public boolean getIsDestroyed() {
		return isDestroyed;
	}
	
	public void setIsDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}
}
