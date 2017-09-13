package schultz.personal.cor.helpers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import schultz.personal.cor.main.Track1;

public class PlasmaBullet {
	
	private Vector2 midPos; // the positon of the origin/middle of the bullet
	private Vector2 initGunPos; // the initial position of the gun at start of game
	private Vector2 vel;
	
	private Sprite pBullet;
	
	private Car boundCar;
	private Polygon boundPoly;
	
	private Track1 track;
	
	private float speed;
	
	public PlasmaBullet(Sprite pBullet, Car boundCar, Track1 track) {
		this.initGunPos = new Vector2(boundCar.getSprite().getX() + 22, boundCar.getSprite().getY() + 37);
		this.pBullet = pBullet;
		this.boundCar = boundCar;
		
		pBullet.setX(track.getRotatedX(initGunPos) - pBullet.getWidth()/2);
		pBullet.setY(track.getRotatedY(initGunPos) - pBullet.getHeight()/2);
		
		pBullet.setOriginCenter();
		this.midPos = new Vector2(pBullet.getX() + pBullet.getOriginX(), pBullet.getY() + pBullet.getOriginY());
		
		this.track = track;
		this.speed = -5;
		
		pBullet.setRotation(boundCar.getSprite().getRotation());
	}
	
	private Vector2 getVelocity(float speed, float rotation) {
		vel = new Vector2();
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
		
		if(boundPoly != null) {
			boundPoly.translate(vel.x, vel.y);
		}
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
	
	public void setBoundPoly(Polygon boundPoly) {
		this.boundPoly = boundPoly;
	}
	
	public Polygon getBoundPoly() {
		return boundPoly;
	}
}