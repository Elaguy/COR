package schultz.personal.cor.main;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import schultz.personal.cor.helpers.Car;
import schultz.personal.cor.helpers.PlasmaBullet;
import schultz.personal.cor.helpers.Waypoint;

public class Track1 implements Screen {
	
	private CORGame game;
	private OrthographicCamera cam;
	
	private int numAiCars;
	
	private ArrayList<Car> cars;
	private ArrayList<Waypoint> waypoints;
	private ArrayList<PlasmaBullet> pBullets;
	private ArrayList<Polygon> carPolys;
	private ArrayList<Polygon> bulletPolys;
	
	private Waypoint current;
	
	private Texture background;
	private Texture trackTex;
	private Texture playerCarTex;
	private Texture AICarTex;
	private Texture plasmaBulletTex;
	private Texture collision;
	
	private Sprite playerCarSprite;
	private Sprite aiCarSprite;
	private Sprite track;
	
	private Car playerCar;
	private Vector2 initGunPos; // initial gun and rear positions before rotation for collision detection
	private Vector2 initRearPos;
	private Vector2 gunPos;
	private Vector2 rearPos;
	
	private float trackX;
	private float trackY;
	
	private float acc; // acceleration for player
	private float aiAcc; // acceleration for ai
	private float friction;
	
	private float totalDt; // used to add up delta for bullet timer
	
	private Pixmap collisionPixmap; // used for collision map so playerCar doesn't run off the track
	
	private Polygon playerPoly;
	
	private Intersector intersector;
	
	private Music race1;
	
	public Track1(CORGame game) {
		this.game = game;
		
		cam = new OrthographicCamera(game.getScreenWidth(), game.getScreenHeight());
		cam.update();
		
		loadAssets();
		
		numAiCars = 1;
		
		cars = new ArrayList<Car>();
		waypoints = new ArrayList<Waypoint>();
		pBullets = new ArrayList<PlasmaBullet>();
		carPolys = new ArrayList<Polygon>();
		bulletPolys = new ArrayList<Polygon>();
		
		playerCarSprite = new Sprite(playerCarTex);
		
		track = new Sprite(trackTex);
		
		/*
		 * With this current configuration, the playerCar starts at (1750, 1.35) (x,y) (based on bottom-left origin of car)
		 */
		playerCarSprite.setPosition(track.getX() + (track.getWidth()/2 + 550), 
				(float) (track.getY() + (track.getHeight()/2 - 1198.65)));
		
		playerCar = new Car(playerCarSprite); // 'PC' = playerCar
		
		cars.add(playerCar); // index 0 of cars is always playerCar
		
		playerPoly = new Polygon(new float[] { playerCarSprite.getX(), playerCarSprite.getY(),
				playerCarSprite.getX(), playerCarSprite.getY() + playerCarSprite.getHeight(),
				playerCarSprite.getX() + playerCarSprite.getWidth(), playerCarSprite.getY() + playerCarSprite.getHeight(),
				playerCarSprite.getX() + playerCarSprite.getWidth(), playerCarSprite.getY()});
		
		playerPoly.setOrigin(playerCar.getMidXPos(), playerCar.getMidYPos());
		playerPoly.scale(-0.3f);
		
		playerCar.setBoundPoly(playerPoly);
		
		carPolys.add(playerPoly);
		
		initGunPos = new Vector2(playerCar.getSprite().getX() + 22, playerCar.getSprite().getY() + 37);
		initRearPos = new Vector2(playerCar.getSprite().getX() + 131, playerCar.getSprite().getY() + 37);
		
		gunPos = initGunPos;
		rearPos = initRearPos;
		
		aiCarSprite = new Sprite(AICarTex);

		/*
		 * With this current configuration, the AI Cars will spawn at (1750, 80) (x, y) (based on bottom-left origin of car)
		 */
		for(int i = 1; i <= numAiCars; i++) {
			Sprite aiSprite = new Sprite(AICarTex);
			
			aiSprite.setX(track.getX() + (track.getWidth()/2 + 550));
			aiSprite.setY(track.getY() + (track.getHeight()/2 - 1120));
			
			cars.add(new Car(aiSprite));
			
			Polygon aiPoly = new Polygon(new float[] { aiSprite.getX(), aiSprite.getY(),
					aiSprite.getX(), aiSprite.getY() + aiSprite.getHeight(),
					aiSprite.getX() + aiSprite.getWidth(), aiSprite.getY() + aiSprite.getHeight(),
					aiSprite.getX() + aiSprite.getWidth(), aiSprite.getY()});
			
			aiPoly.setOrigin(cars.get(i).getMidXPos(), cars.get(i).getMidYPos());
			aiPoly.scale(-0.3f);
			
			cars.get(i).setBoundPoly(aiPoly);
			
			carPolys.add(aiPoly);
		}
		
		// Default waypoints for the track
		float x = cars.get(1).getMidXPos();
		float y = cars.get(1).getMidYPos();
		waypoints.add(new Waypoint(new Vector2(x - 1742, y), cars.get(1), false));
		waypoints.add(new Waypoint(new Vector2(waypoints.get(0).getPos().x, y + 2192), cars.get(1), false));
		waypoints.add(new Waypoint(new Vector2(waypoints.get(1).getPos().x + 2242, waypoints.get(1).getPos().y), cars.get(1), false));
		waypoints.add(new Waypoint(new Vector2(waypoints.get(2).getPos().x, waypoints.get(2).getPos().y - 2242), cars.get(1), false));
		waypoints.add(new Waypoint(new Vector2(waypoints.get(3).getPos().x - 650, waypoints.get(3).getPos().y), cars.get(1), true));
		
		current = waypoints.get(0);
		
		trackX = 0;
		trackY = 0;
		
		track.setPosition(trackX, trackY);
		
		collision.getTextureData().prepare();
		
		collisionPixmap = collision.getTextureData().consumePixmap();
		
		intersector = new Intersector();
		
//		race1.setLooping(true);
//		race1.play();
		
		/*
		 * Default acceleration is 0.2f,
		 * this creates a top speed of 19.8
		 * b/c of friction (tested in-game)
		 * 
		 * Because 19.8 / 0.2 = 99,
		 * acc * 99 = top speed
		 * (0.2 * 99 = 19.8)
		 * 
		 * EX: 0.3 * 99 = 29.7 (approx. top speed)
		 * 
		 * This is with default friction of 0.01f
		 * of course, but the same process can be used
		 * with a different friction value (you just need to
		 * do the initial test in-game first)
		 */
		acc = 0.2f; // top speed (default friction): 19.8
		friction = 0.01f;
		
		aiAcc = 0.1f; // top speed (default friction): 9.9
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0/255f, 0/255f, 0/255f, 0/255f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		game.batch.setProjectionMatrix(cam.combined);
		
		game.batch.draw(background, trackX, trackY, background.getWidth(), background.getHeight());
		game.batch.draw(track, trackX, trackY);
		
		for(int i = 0; i < pBullets.size(); i++) {
			if(!pBullets.isEmpty()) {
				PlasmaBullet current = pBullets.get(i);

				game.batch.draw(current.getSprite(), current.getSprite().getX(), current.getSprite().getY(), current.getSprite().getOriginX(),
						current.getSprite().getOriginY(), current.getSprite().getWidth(), current.getSprite().getHeight(), 1, 1, current.getSprite().getRotation());
			}
		}
		
		for(int i = 0; i < cars.size(); i++) {
			if(!cars.isEmpty()) {
				Car current = cars.get(i);
				
				game.batch.draw(current.getSprite(), current.getSprite().getX(), current.getSprite().getY(), current.getSprite().getOriginX(),
						current.getSprite().getOriginY(), current.getSprite().getWidth(), current.getSprite().getHeight(), 1, 1, current.getSprite().getRotation());
			}
		}
		
		game.batch.end();
		
		game.shape.begin(ShapeType.Line);
		
		game.shape.setProjectionMatrix(cam.combined);
		
		game.shape.setColor(Color.WHITE);
		
		for(int i = 0; i < carPolys.size(); i++)
			game.shape.polygon(carPolys.get(i).getTransformedVertices());
		
		for(int i = 0; i < bulletPolys.size(); i++)
			game.shape.polygon(bulletPolys.get(i).getTransformedVertices());
	
		game.shape.end();
		
		update(delta);
	}
	
	private void update(float delta) {		
		if(current.getCompleted()) {			
			if((waypoints.indexOf(current) + 1) < waypoints.size()) {
				current = waypoints.get(waypoints.indexOf(current)+1); // go to next waypoint
			}
		}
		
		updatePlayerCar();
		updateAICars();
		updatePlasmaBullets();
		
		totalDt += delta;
		
		if(totalDt > 0.6)
			totalDt = 0;
	}

	private void updatePlayerCar() {
		int rotateAmt = 3;
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			playerCar.setSpeed(playerCar.getSpeed() - acc);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			playerCar.setSpeed(playerCar.getSpeed() + acc);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			playerCar.getSprite().rotate(rotateAmt);
			playerCar.getBoundPoly().rotate(rotateAmt);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			playerCar.getSprite().rotate(-rotateAmt);
			playerCar.getBoundPoly().rotate(-rotateAmt);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {	
			if(totalDt > 0.5) {
				createPlasmaBullet();
			}
		}
		
		if(Math.abs(playerCar.getSpeed()) < 0.1) {
			playerCar.setSpeed(0);
		}
		
		playerCar.setSpeed(playerCar.getSpeed() * (1 - friction));
		
		playerCar.getSprite().setOriginCenter();
		
		playerCar.move();
		
		cam.position.set(playerCar.getSprite().getX() + (playerCar.getSprite().getWidth()/2), 
				playerCar.getSprite().getY() + (playerCar.getSprite().getHeight()/2), 0);
		
		initGunPos = new Vector2(playerCar.getSprite().getX() + 22, playerCar.getSprite().getY() + 37);
		initRearPos = new Vector2(playerCar.getSprite().getX() + 131, playerCar.getSprite().getY() + 37);
		
		gunPos.x = getRotatedX(initGunPos);
		gunPos.y = getRotatedY(initGunPos);
		
		rearPos.x = getRotatedX(initRearPos);
		rearPos.y = getRotatedY(initRearPos);
		
		checkPlayerCollisions();
		
		cam.update();
	}
	
	private void updateAICars() {
		Waypoint wp = current;
		Car car = wp.getTargetCar();
		
		car.getSprite().setRotation(wp.getDist().angle());
		
		car.getBoundPoly().setRotation(car.getSprite().getRotation());
		
		//System.out.println(car.getSprite().getRotation());
			
		if(wp.getAbsDist().x > 0 || wp.getAbsDist().y > 0) { // if car is not on waypoint, speed up
			car.setSpeed(car.getSpeed() - aiAcc);
		}
			
		/*
		 * If car is set to stop at this waypoint OR this waypoint is the last in the list AND
		 * the car's distance meets the "threshold", stop the car (this prevents strange glitches)
		 */
		if((Math.floor(wp.getAbsDist().x) < 10 && Math.floor(wp.getAbsDist().y) < 10)) {
			if(wp.getStopHere() || waypoints.indexOf(wp) == (waypoints.size() - 1))
				car.setSpeed(0);
			
			wp.setCompleted(true);
		}
		
		car.setSpeed(car.getSpeed() * (1 - friction));
			
		car.move();
		cam.update();
	}
	
	private void updatePlasmaBullets() {
		for(int i = 0; i < pBullets.size(); i++) {
			PlasmaBullet current = pBullets.get(i);
			
			current.getBoundPoly().setRotation(current.getSprite().getRotation());
			
			current.move();
		}
	}
	
	private void checkPlayerCollisions() {
		if(!(getPixelColor(gunPos.x, background.getHeight() - gunPos.y).equals(Color.GREEN))) {
			playerCar.setSpeed(2);
		}
		
		if(!(getPixelColor(rearPos.x, background.getHeight() - rearPos.y).equals(Color.GREEN))) {
			playerCar.setSpeed(-2);
		}
		
		for(int i = 0; i < carPolys.size()-1; i++) {
			if(intersector.overlapConvexPolygons(carPolys.get(i), carPolys.get(i+1))) {
				cars.get(i).setSpeed(5);
				cars.get(i+1).setSpeed(-5);
			}
		}
	}
	
	/*
	 * A point on the car that was there initially will change position when the car rotates and moves.
	 * These methods return the correct rotated point about the playerCar's middle x and y.
	 */
	public float getRotatedX(Vector2 pos) {
		double angle = Math.toRadians(playerCar.getSprite().getRotation());
		
		return (float) (Math.cos(angle) * (pos.x - playerCar.getMidXPos()) - Math.sin(angle) * (pos.y - playerCar.getMidYPos()) + playerCar.getMidXPos());
	}
	
	public float getRotatedY(Vector2 pos) {
		double angle = Math.toRadians(playerCar.getSprite().getRotation());
		
		return (float) (Math.sin(angle) * (pos.x - playerCar.getMidXPos()) + Math.cos(angle) * (pos.y - playerCar.getMidYPos()) + playerCar.getMidYPos());
	}
	
	/*
	 * Gets the color of a pixel specified by the x and y location from the collision map
	 * (collision.png).
	 * == LOCATIONS ARE BASED ON TOP LEFT WITH PIXMAP ==
	 */
	private Color getPixelColor(float f, float g) {
		return new Color(collisionPixmap.getPixel((int) f, (int) g));
	}
	
	private void createPlasmaBullet() {
		Sprite bulletSprite = new Sprite(plasmaBulletTex);
		PlasmaBullet pBullet = new PlasmaBullet(bulletSprite, playerCar, this);
		
		Polygon bulletPoly = new Polygon(new float[] { bulletSprite.getX(), bulletSprite.getY(),
				bulletSprite.getX(), bulletSprite.getY() + bulletSprite.getHeight(),
				bulletSprite.getX() + bulletSprite.getWidth(), bulletSprite.getY() + bulletSprite.getHeight(),
				bulletSprite.getX() + bulletSprite.getWidth(), bulletSprite.getY()});
		
		bulletPoly.setOrigin(pBullet.getMidPos().x, pBullet.getMidPos().y);
		
		pBullet.setBoundPoly(bulletPoly);
		
		pBullets.add(pBullet);
		bulletPolys.add(bulletPoly);
	}
	
	@Override
	public void resize(int width, int height) {
		game.viewport.update(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void dispose() {
		
	}
	
	private void loadAssets() {
		background = game.mgr.get("img/backgrnd_1.png", Texture.class);
		trackTex = game.mgr.get("img/track1.png", Texture.class);
		playerCarTex = game.mgr.get("img/car1.png", Texture.class);
		AICarTex = game.mgr.get("img/car2.png", Texture.class);
		plasmaBulletTex = game.mgr.get("img/plasma_bullet.png", Texture.class);
		collision = game.mgr.get("img/collision.png", Texture.class);
		race1 = game.mgr.get("audio/Rhinoceros.mp3", Music.class);
	}

}
