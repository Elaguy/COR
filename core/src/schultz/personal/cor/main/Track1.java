package schultz.personal.cor.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Track1 implements Screen {
	
	private CORGame game;
	
	private Texture background;
	private Texture trackTex;
	private Texture playerCarTex;
	
	private Sprite track;
	private Sprite playerCar;
	
	private int imgScale;
	
	private float trackX;
	private float trackY;
	
	private float speed;
	private float acc; // acceleration
	private float friction;
	private float rotation;
	
	public Track1(CORGame game) {
		this.game = game;
		
		game.cam.update();
		
		imgScale = 5;
		
		loadAssets();
		
		track = new Sprite(trackTex);
		playerCar = new Sprite(playerCarTex);
		
		trackX = (-track.getWidth()/2) - 90;
		trackY = (game.getScreenHeight()/2) - (playerCar.getHeight()/2);
		
		track.setPosition(trackX, trackY);
		
		track.setOrigin(playerCar.getX(), playerCar.getY());
		
		speed = 0;
		acc = 0.1f;
		friction = 0.05f;
		rotation = 0;
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0/255f, 0/255f, 0/255f, 0/255f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		
		game.batch.draw(background, 0, 0, background.getWidth()*imgScale, background.getHeight()*imgScale);
		track.draw(game.batch);
		game.batch.draw(playerCar, (game.getScreenWidth()/2) - (playerCar.getWidth()/2), 
				(game.getScreenHeight()/2) - (playerCar.getHeight()/2));
		// car's x and y is from screen and not viewport b/c viewport had the car move to a different
		// position when maximizing window. MORE OF A TEMPORARY FIX.
		
		game.batch.end();
		
		update(delta);
	}
	
	private void update(float delta) {
		Gdx.app.log("FPS", String.valueOf(Gdx.graphics.getFramesPerSecond()));
		
		if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
			speed += acc;
		}
		
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			speed -= acc;
		}
		
//		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
//			
//		}
//		
//		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
//			
//		}
		
		else if (speed > 0 && speed != 0) {
				speed -= friction;
		}
		
		else if (speed < 0 && speed != 0) {
			speed += friction;
		}
		
		System.out.println(speed);
		track.setX(track.getX() + speed);
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
