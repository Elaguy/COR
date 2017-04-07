package schultz.personal.cor.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Track1 implements Screen {
	
	private CORGame game;
	private OrthographicCamera cam;
	
	private Texture background;
	private Texture trackTex;
	private Texture playerCarTex;
	
	private Sprite track;
	private Sprite playerCar;
	
	private float trackX;
	private float trackY;
	
	private float speed;
	private float acc; // acceleration
	private float friction;
	private float rotation;
	private float rotationStep;
	private float topvel;
	
	public Track1(CORGame game) {
		this.game = game;
		
		cam = new OrthographicCamera(game.getScreenWidth(), game.getScreenHeight());
		cam.update();
		
		loadAssets();
		
		track = new Sprite(trackTex);
		playerCar = new Sprite(playerCarTex);
		
		trackX = (-track.getWidth()/2) - 90;
		trackY = (game.getScreenHeight()/2) - (playerCar.getHeight()/2);
		
		track.setPosition(trackX, trackY);
		playerCar.setPosition((game.getScreenWidth()/2) - (playerCar.getWidth()/2), 
				(game.getScreenHeight()/2) - (playerCar.getHeight()/2));
		
		/*
		 * Recommended acceleration is 0.2f,
		 * this creates a top speed of 19.8
		 * b/c of friction
		 */
		acc = 0.2f;
		friction = 0.01f;
		rotation = 0;
		rotationStep = 0.1f;
		topvel = 50;
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
		game.batch.draw(playerCar, playerCar.getX(), playerCar.getY(), playerCar.getOriginX(),
				playerCar.getOriginY(), playerCar.getWidth(), playerCar.getHeight(), 1, 1, playerCar.getRotation());
		
		game.batch.end();
		
		update(delta);
	}
	
	private void update(float delta) {
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			speed -= acc;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			speed += acc;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			playerCar.rotate(3);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			playerCar.rotate(-3);
		}
		
		if(Math.abs(speed) < 0.1 || Math.abs(speed) < 0.1) {
			speed = 0;
		}
	
		speed *= (1 - friction);
		
		Vector2 vel = getVelocity(speed, playerCar.getRotation());
		
		playerCar.setOriginCenter();
		
		playerCar.setX((float) (playerCar.getX() + vel.x));
		playerCar.setY((float) (playerCar.getY() + vel.y));
		
		System.out.println("Speed: " + speed);
//		System.out.println("VelX: " + vel.x + " - " + "VelY: " + vel.y);
//		System.out.println("Rotation: " + playerCar.getRotation());
		
		cam.position.set(playerCar.getX() + (playerCar.getWidth()/2), playerCar.getY() + (playerCar.getHeight()/2), 0);
		cam.update();
	}
	
	private Vector2 getVelocity(float speed, float rotation) {
		Vector2 vel = new Vector2();
		float vx = (float) Math.cos(Math.toRadians(rotation)) * speed;
		float vy = (float) Math.sin(Math.toRadians(rotation)) * speed;
		
		vel.x = vx;
		vel.y = vy;
		
		return vel;
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
