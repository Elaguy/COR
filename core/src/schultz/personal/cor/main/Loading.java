package schultz.personal.cor.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

public class Loading implements Screen {
	
	private CORGame game;
	private GlyphLayout loadingLayout;
	
	private float progress;
	
	public Loading(CORGame game) {
		this.game = game;
		
		loadingLayout = new GlyphLayout(game.mainFont, "Loading...");
		
		game.cam.update();
		
		progress = 0f;
		
		queueAssets();
	}
	
	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		int rectWidth = (Gdx.graphics.getWidth() - 100);
		
		Gdx.gl.glClearColor(141/255f, 141/255f, 141/255f, 255/255f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);		
		
		game.batch.begin();
		game.mainFont.draw(game.batch, loadingLayout, (Gdx.graphics.getWidth() - loadingLayout.width)/2, ((Gdx.graphics.getHeight() - loadingLayout.height)/2) + (Gdx.graphics.getHeight() - 500));
		game.batch.end();
		
		
		game.shape.begin(ShapeType.Filled);
		game.shape.setColor(Color.SLATE);
		game.shape.rect(((Gdx.graphics.getWidth() - rectWidth)/2), ((Gdx.graphics.getHeight() - rectWidth)/2), rectWidth * progress, 150);
		game.shape.end();
		
		update(delta);
	}
	
	public void update(float delta) {
		progress = game.mgr.getProgress();
		
		if(game.mgr.update())
			game.setScreen(new MainMenu(game));
		
		Gdx.app.log("FPS", String.valueOf(Gdx.graphics.getFramesPerSecond()));
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
	
	private void queueAssets() {
		game.mgr.finishLoading();
		game.mgr.load("img/menu_img.png", Texture.class);
		game.mgr.load("img/button.png", Texture.class);
		game.mgr.load("img/button_selected.png", Texture.class);
		game.mgr.load("img/button_pressed.png", Texture.class);
		game.mgr.load("img/backgrnd_1.png", Texture.class);
		game.mgr.load("img/track1.png", Texture.class);
		game.mgr.load("img/car1.png", Texture.class);
		game.mgr.load("img/car2.png", Texture.class);
		game.mgr.load("img/plasma_bullet.png", Texture.class);
		game.mgr.load("img/collision.png", Texture.class);
		game.mgr.load("img/car1_hit.png", Texture.class);
		game.mgr.load("img/car2_hit.png", Texture.class);
		game.mgr.load("audio/Rhinoceros.mp3", Music.class);
		game.mgr.load("audio/Unwritten_Return.mp3", Music.class);
		game.mgr.load("img/explosion_sheet.png", Texture.class);
		game.mgr.load("audio/plasmabullet.wav", Sound.class);
		game.mgr.load("audio/explosion.mp3", Sound.class);
		game.mgr.load("img/landmine_sheet.png", Texture.class);
	}

}
