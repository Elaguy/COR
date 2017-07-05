package schultz.personal.cor.helpers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class PlasmaBullet {
	
	private Vector2 midPos; // the positon of the origin/middle of the bullet
	private Vector2 initGunPos; // the initial position of the gun at start of game
	private Sprite pBullet;
	private Car boundCar;
	private float speed;
	
	public PlasmaBullet(Sprite pBullet, Car boundCar) {
		this.initGunPos = new Vector2(boundCar.getSprite().getX() + 22, boundCar.getSprite().getY() + 37);
		this.pBullet = pBullet;
		this.boundCar = boundCar;
		this.midPos = new Vector2(pBullet.getX() + pBullet.getOriginX(), pBullet.getY() + pBullet.getOriginY());
		this.speed = -20;
		
		pBullet.setOriginCenter();
		
		pBullet.setX(getInitPosX());
		pBullet.setY(getInitPosY());
		
		pBullet.setRotation(boundCar.getSprite().getRotation());
	}
	
	private Vector2 getVelocity(float speed, float rotation) {
		Vector2 vel = new Vector2();
		float vx = (float) Math.cos(Math.toRadians(rotation)) * speed;
		float vy = (float) Math.sin(Math.toRadians(rotation)) * speed;
		
		vel.x = vx;
		vel.y = vy;
		
		return vel;
	}
	
	public void move() { // converts speed and rotation to velocity and sets x and y accordingly
		Vector2 vel = getVelocity(speed, pBullet.getRotation());
		pBullet.setX(pBullet.getX() + vel.x);
		pBullet.setY(pBullet.getY() + vel.y);
		
		midPos.x = pBullet.getX() + pBullet.getOriginX();
		midPos.y = pBullet.getY() + pBullet.getOriginY();
	}
	
	private float getInitPosX() {
		double angle = Math.abs(Math.toRadians(boundCar.getSprite().getRotation()));
		
		/*
		 * Negative angle, which with the playerCar's current image orientation, is clockwise
		 */
		if(boundCar.getSprite().getRotation() < 0)
			return (float) (Math.cos(angle) * (initGunPos.x - boundCar.getMidXPos()) + Math.sin(angle) * (initGunPos.y - boundCar.getMidYPos()) + boundCar.getMidXPos());
		
		/*
		 * Positive angle, which with the playerCar's current image orientation is counter-clockwise
		 */
		if(boundCar.getSprite().getRotation() >= 0)
			return (float) (Math.cos(angle) * (initGunPos.x - boundCar.getMidXPos()) - Math.sin(angle) * (initGunPos.y - boundCar.getMidYPos()) + boundCar.getMidXPos());
		
		else
			return 0f;
	}
	
	private float getInitPosY() {
		double angle = Math.abs(Math.toRadians(boundCar.getSprite().getRotation()));
		
		// clockwise
		if(boundCar.getSprite().getRotation() < 0)
			return (float) (-Math.sin(angle) * (initGunPos.x - boundCar.getMidXPos()) + Math.cos(angle) * (initGunPos.y - boundCar.getMidYPos()) + boundCar.getMidYPos());
		
		// counter-clockwise
		if(boundCar.getSprite().getRotation() >= 0)
			return (float) (Math.sin(angle) * (initGunPos.x - boundCar.getMidXPos()) + Math.cos(angle) * (initGunPos.y - boundCar.getMidYPos()) + boundCar.getMidYPos());
		
		else
			return 0f;
	}
	
	public Vector2 getMidPos() {
		return midPos;
	}
	
	public Sprite getSprite() {
		return pBullet;
	}
	
	public float getOffsetY() {
		return 31.5f;
	}
	
	public Car getBoundcar() {
		return boundCar;
	}
}