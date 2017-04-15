package schultz.personal.cor.main;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

import schultz.personal.cor.helpers.Car;

public class Track1 implements Screen {
	
	private CORGame game;
	private OrthographicCamera cam;
	
	private int numAiCars;
	
	private ArrayList<Car> cars;
	
	private Texture background;
	private Texture trackTex;
	private Texture playerCarTex;
	
	private Sprite track;
	
	private Car playerCar;
	
	private float trackX;
	private float trackY;
	
	private float speed;
	private float acc; // acceleration
	private float friction;
	private float rotation;
	
	public Track1(CORGame game) {
		this.game = game;
		
		cam = new OrthographicCamera(game.getScreenWidth(), game.getScreenHeight());
		cam.update();
		
		loadAssets();
		
		numAiCars = 1;
		
		cars = new ArrayList<Car>();
		
		playerCar = new Car(new Sprite(playerCarTex));
		
		cars.add(playerCar); // index 0 of cars is always playerCar
		
		for(int i = 0; i < numAiCars; i++) {
			//cars.add(new Car());
		}
		
		track = new Sprite(trackTex);
		
		trackX = (-track.getWidth()/2) - 90;
		trackY = (game.getScreenHeight()/2) - (playerCar.getSprite().getHeight()/2);
		
		track.setPosition(trackX, trackY);
		playerCar.getSprite().setPosition((game.getScreenWidth()/2) - (playerCar.getSprite().getWidth()/2), 
				(game.getScreenHeight()/2) - (playerCar.getSprite().getHeight()/2));
		
		/*
		 * Default acceleration is 0.2f,
		 * this creates a top speed of 19.8
		 * b/c of friction
		 */
		acc = 0.2f;
		friction = 0.01f;
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
		game.batch.draw(playerCar.getSprite(), playerCar.getSprite().getX(), playerCar.getSprite().getY(), playerCar.getSprite().getOriginX(),
				playerCar.getSprite().getOriginY(), playerCar.getSprite().getWidth(), playerCar.getSprite().getHeight(), 1, 1, playerCar.getSprite().getRotation());
		
		game.batch.end();
		
		update(delta);
	}
	
	private void update(float delta) {
		
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
		
		System.out.println("Speed: " + playerCar.getSpeed());
		
		cam.position.set(playerCar.getSprite().getX() + (playerCar.getSprite().getWidth()/2), playerCar.getSprite().getY() + (playerCar.getSprite().getHeight()/2), 0);
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
	}

}
