package schultz.personal.cor.main;

import java.util.ArrayList;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

import schultz.personal.cor.helpers.Car;
import schultz.personal.cor.helpers.Waypoint;

public class Track1 implements Screen {
	
	private CORGame game;
	private OrthographicCamera cam;
	
	private int numAiCars;
	
	private ArrayList<Car> cars;
	private ArrayList<Waypoint> waypoints;
	
	private Waypoint current;
	
	private Texture background;
	private Texture trackTex;
	private Texture playerCarTex;
	private Texture AICarTex;
	
	private Sprite track;
	
	private Car playerCar;
	
	private float trackX;
	private float trackY;
	
	private float acc; // acceleration for player
	private float aiAcc; // acceleration for ai
	private float friction;
	
	private float x1; // used in waypoint calculations
	private float y1;
	
	public Track1(CORGame game) {
		this.game = game;
		
		cam = new OrthographicCamera(game.getScreenWidth(), game.getScreenHeight());
		cam.update();
		
		loadAssets();
		
		numAiCars = 1;
		
		cars = new ArrayList<Car>();
		waypoints = new ArrayList<Waypoint>();
		
		playerCar = new Car(new Sprite(playerCarTex)); // 'PC' = playerCar
		
		cars.add(playerCar); // index 0 of cars is always playerCar
		
		/*
		 * With this current configuration, the AI Cars will spawn at (512, 459) (x, y)
		 */
		for(int i = 1; i <= numAiCars; i++) {
			Sprite aiCarSprite = new Sprite(AICarTex);
			
			aiCarSprite.setX((game.getScreenWidth()/2) - (aiCarSprite.getWidth()/2));
			aiCarSprite.setY((game.getScreenHeight()/2) - (aiCarSprite.getHeight()/2) + 75);
			
			aiCarSprite.setOriginCenter();
			
			cars.add(new Car(aiCarSprite));
		}
		
		float x = cars.get(1).getMidXPos();
		float y = cars.get(1).getMidYPos();
		waypoints.add(new Waypoint(new Vector2(x - 1742, y), cars.get(1), false));
		waypoints.add(new Waypoint(new Vector2(waypoints.get(0).getPos().x, y + 2192), cars.get(1), false));
		waypoints.add(new Waypoint(new Vector2(waypoints.get(1).getPos().x + 2242, waypoints.get(1).getPos().y), cars.get(1), false));
		waypoints.add(new Waypoint(new Vector2(waypoints.get(2).getPos().x, waypoints.get(2).getPos().y - 2242), cars.get(1), false));
		waypoints.add(new Waypoint(new Vector2(waypoints.get(3).getPos().x - 650, waypoints.get(3).getPos().y), cars.get(1), true));
		
		current = waypoints.get(0);
		
		track = new Sprite(trackTex);
		
		trackX = (-track.getWidth()/2) - 90;
		trackY = (game.getScreenHeight()/2) - (playerCar.getSprite().getHeight()/2);
		
		track.setPosition(trackX, trackY);
		playerCar.getSprite().setPosition((game.getScreenWidth()/2) - (playerCar.getSprite().getWidth()/2), 
				(game.getScreenHeight()/2) - (playerCar.getSprite().getHeight()/2));
		
		/*
		 * Default acceleration is 0.2f,
		 * this creates a top speed of 19.8
		 * b/c of friction (tested in-game)
		 * 
		 * So because 19.8 / 0.2 = 99,
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
		
		aiAcc = 0.15f; // top speed (default friction): 14.85
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
		
		for(int i = 0; i < cars.size(); i++) {
			Car current = cars.get(i);
			
			game.batch.draw(current.getSprite(), current.getSprite().getX(), current.getSprite().getY(), current.getSprite().getOriginX(),
					current.getSprite().getOriginY(), current.getSprite().getWidth(), current.getSprite().getHeight(), 1, 1, current.getSprite().getRotation());		
		}
		
		game.batch.end();
		
		
		game.shape.begin(ShapeType.Filled);
		
		game.shape.setProjectionMatrix(cam.combined);
		
		game.shape.setColor(Color.NAVY);
			
		game.shape.circle(current.getPos().x, current.getPos().y, 8);
		
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
		
		System.out.println("(" + Gdx.input.getX() + ", " + Gdx.input.getY() + ")");
	}

	private void updatePlayerCar() {
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			playerCar.setSpeed(playerCar.getSpeed() - acc);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			playerCar.setSpeed(playerCar.getSpeed() + acc);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			playerCar.getSprite().rotate(3);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			playerCar.getSprite().rotate(-3);
		}
		
		if(Math.abs(playerCar.getSpeed()) < 0.1) {
			playerCar.setSpeed(0);
		}
		
		playerCar.setSpeed(playerCar.getSpeed() * (1 - friction));
		
		playerCar.getSprite().setOriginCenter();
		
		playerCar.move();
		
		cam.position.set(playerCar.getSprite().getX() + (playerCar.getSprite().getWidth()/2), 
				playerCar.getSprite().getY() + (playerCar.getSprite().getHeight()/2), 0);
		cam.update();
	}
	
	private void updateAICars() {
		Waypoint wp = current;
		Car car = wp.getTargetCar();
		
		car.getSprite().setRotation(wp.getDist().angle());
		
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
	}

}
