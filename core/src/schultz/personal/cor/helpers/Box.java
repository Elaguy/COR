package schultz.personal.cor.helpers;

import com.badlogic.gdx.math.Vector2;

public class Box {
	
	private float x, y, w, h;
	private float rotation;
	private Vector2[] points = new Vector2[4];
	private Vector2 center;
	private Car boundCar;
	
	public Box(float x, float y, float w, float h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.center = new Vector2(w/2 + x, h/2 + y);
		this.rotation = 0;
		
		points[0] = new Vector2(x, y); // Point A
		points[1] = new Vector2(x, y+h); // Point B
		points[2] = new Vector2(x+w, y+h); // point C
		points[3] = new Vector2(x+w, y); // point D
	}
	
	public Box(float w, float h, Car boundCar) {
		this.boundCar = boundCar;
		this.center = new Vector2(boundCar.getMidXPos(), boundCar.getMidYPos());
		this.x = center.x - (w/2);
		this.y = center.y - (h/2);
		this.w = w;
		this.h = h;
		this.rotation = 0;
		
		points[0] = new Vector2(x, y); // Point A
		points[1] = new Vector2(x, y+h); // Point B
		points[2] = new Vector2(x+w, y+h); // point C
		points[3] = new Vector2(x+w, y); // point D
		
		rotate(boundCar.getSprite().getRotation());
	}
	
	public void rotate(float angle) {
		float a = (float) Math.toRadians(angle);
		
		for(int i = 0; i < points.length; i++) {
			points[i] = new Vector2(rotX(a, points[i]), rotY(a, points[i]));
		}
		
		this.x = points[0].x;
		this.y = points[0].y;
		this.rotation += angle;
	}
	
	public void translate(float x, float y) {
		for(int i = 0; i < points.length; i++) {
			points[i].x += x;
			points[i].y += y;
		}
		
		center.x += x;
		center.y += y;
	}
	
	public boolean isColliding(Box a, Box b) {
		return !((a.x+a.w < b.x) || (b.x+b.w < a.x) || (a.y+a.h < b.y) || (b.y+b.h < a.y));
	}
	
	private float rotX(float a, Vector2 point) {
		return (float) (Math.cos(a) * (point.x - center.x) - Math.sin(a) * (point.y - center.y) + center.x);
	}
	
	private float rotY(float a, Vector2 point) {
		return (float) (Math.sin(a) * (point.x - center.x) + Math.cos(a) * (point.y - center.y) + center.y);
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getW() {
		return w;
	}
	
	public float getH() {
		return h;
	}
	
	public Vector2[] getPoints() {
		return points;
	}
	
	public float getRotation() {
		return rotation;
	}
	
}
