package schultz.personal.cor.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

public class Tutorial implements Screen {
	
	private CORGame game;
	
	public Tutorial(CORGame game) {
		this.game = game;
		
		game.cam.update();
		
		loadAssets();
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0/255f, 0/255f, 0/255f, 0/255f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {
		
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
		
	}

}
