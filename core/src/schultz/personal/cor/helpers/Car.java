package schultz.personal.cor.helpers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Car {
	
	private Sprite car;
	private float speed;
	
	public Car(Sprite car) {
		this.car = car;
		speed = 0;
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
}
