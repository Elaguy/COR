package schultz.personal.cor.helpers;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import schultz.personal.cor.main.Track1;

public class LandMine {
	
	private AnimationObj landMineAni;
	private Car boundCar;
	private Polygon boundPoly;
	private Sprite initialFrame;
	
	private Vector2 pos;
	private Vector2 midPos;
	
	private float hp;
	private boolean isDestroyed;
	
	private Track1 track1;
	
	public LandMine(AnimationObj landMineAni, Car boundCar, Sprite initialFrame, Track1 track1) {
		this.landMineAni = landMineAni;
		this.boundCar = boundCar;
		this.initialFrame = initialFrame;
		
		pos = boundCar.getRearPos();
		
		initialFrame.setOriginCenter();
		
		initialFrame.setX(pos.x - initialFrame.getWidth()/2);
		initialFrame.setY(pos.y - initialFrame.getWidth()/2);
		
		midPos = new Vector2(initialFrame.getX() + initialFrame.getOriginX(), initialFrame.getY() + initialFrame.getOriginY());
		
		hp = 100;
		
		this.track1 = track1;
	}
	
	public void checkHP() {
		if(hp <= 0 && !isDestroyed) {
			isDestroyed = true;
			
			track1.getExplosion().play();
		}
	}
	
	public void setBoundPoly(Polygon boundPoly) {
		this.boundPoly = boundPoly;
	}
	
	public Vector2 getPos() {
		return pos;
	}
	
	public Vector2 getMidPos() {
		return midPos;
	}
	
	public Sprite getSprite() {
		return initialFrame;
	}
	
	public void setHP(float hp) {
		this.hp = hp;
	}
	
	public float getHP() {
		return hp;
	}
	
	public boolean getIsDestroyed() {
		return isDestroyed;
	}
	
	public float getMidXPos() {
		return midPos.x;
	}
	
	public float getMidYPos() {
		return midPos.y;
	}
}