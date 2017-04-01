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
	
	private Vector2 speed;
	private float acc; // acceleration
	private float friction;
	private float rotation;
	private float rotationStep;
	private float topSpeed;
	
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
		
		speed = new Vector2(0, 0);
		acc = 0.1f;
		friction = 0.01f;
		rotation = 0;
		rotationStep = 0.001f;
		topSpeed = 50;
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
		// car's x and y is from screen and not viewport b/c viewport had the car move to a different
		// position when maximizing window. MORE OF A TEMPORARY FIX.
		
		game.batch.end();
		
		update(delta);
	}
	
	private void update(float delta) {
		Gdx.app.log("FPS", String.valueOf(Gdx.graphics.getFramesPerSecond()));
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
//			speed.x += acc;
//			speed.y += acc;
			playerCar.translate(speed.x += acc, speed.y += acc);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
//			speed.x -= acc;
//			speed.y -= acc;
			playerCar.translate(speed.x -= acc, speed.y -= acc);
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			rotation += rotationStep;
			speed.rotate(rotation);
			playerCar.rotate(speed.angle());
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			rotation -= rotationStep;
			speed.rotate(rotation);
			playerCar.rotate(speed.angle());
		}
		
		if(Math.abs(speed.x) < 0.01 || Math.abs(speed.y) < 0.01) {
			speed.x = 0;
			speed.y = 0;
		}
		
		speed.x *= (1 - friction);
		speed.y *= (1 - friction);
		
		System.out.println(speed);
		
		playerCar.setOriginCenter();
		
		cam.position.set(playerCar.getX() + (playerCar.getWidth()/2), playerCar.getY() + (playerCar.getHeight()/2), 0);
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
