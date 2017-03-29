package schultz.personal.cor.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class Track1 implements Screen {
	
	private CORGame game;
	
	private Texture background;
	private Texture track;
	private Texture playerCar;
	
	private int imgScale;
	
	public Track1(CORGame game) {
		this.game = game;
		
		game.cam.update();
		
		imgScale = 5;
		
		loadAssets();
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
		game.batch.draw(track, -track.getWidth()/2, 0);
		game.batch.draw(playerCar, (game.getScreenWidth()/2) - (playerCar.getWidth()/2), 
				(game.getScreenHeight()/2) - (playerCar.getHeight()/2));
		// car's x and y is from screen and not viewport b/c viewport had the car move to a different
		// position when maximizing window. MORE OF A TEMPORARY FIX.
		
		game.batch.end();
		
		update(delta);
	}
	
	private void update(float delta) {
		//Gdx.app.log("FPS", String.valueOf(Gdx.graphics.getFramesPerSecond()));
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
		track = game.mgr.get("img/track1.png", Texture.class);
		playerCar = game.mgr.get("img/car1.png", Texture.class);
	}

}
