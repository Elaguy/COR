package schultz.personal.cor.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import schultz.personal.cor.helpers.UI;
import schultz.personal.cor.helpers.UiButton;
import schultz.personal.cor.helpers.UiElement;
import schultz.personal.cor.helpers.UiImage;
import schultz.personal.cor.helpers.UiText;

public class MainMenu implements Screen {

	private CORGame game;
	private Texture background;
	private Texture button;
	
	private UI ui;
	private UiText gameTitle;
	private UiButton startButton;
	private UiButton quitButton;
	
	public MainMenu(CORGame game) {
		this.game = game;
		
		game.cam.update();
		
		ui = new UI(game.viewport.getScreenWidth(), game.viewport.getScreenHeight(), 80, true);
		
		loadAssets();
		
		gameTitle = new UiText("Centripetal Orbital Racers", 1,game);
		startButton = new UiButton(button, "Start Game", 5,game);
		quitButton = new UiButton(button, "Quit Game", 5,game);
		
		ui.addUiButton(quitButton); // must flip order because actual rendering is
		ui.addUiButton(startButton); // backward for some reason
		ui.addUiText(gameTitle);
		
		ui.calculateElements();
	}

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0/255f, 0/255f, 0/255f, 0/255f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		game.batch.begin();
		game.batch.draw(background, 0, 0);
		
		for(int i = 0; i < ui.getElements().size(); i++) {
			UiElement current = ui.getElements().get(i);
			
			if(current.isText()){ // text
				game.mainFont.draw(game.batch, current.getGlyphLayout(), current.getElementX(),
						current.getElementY());
			}
			
			else if(ui.getElements().get(i).isButton()) { // buttons
				game.batch.draw(current.getTexture(), current.getElementX(),
						current.getElementY(), current.getWidth()*current.getScale(),
						current.getHeight()*current.getScale());
				game.smallishFont.draw(game.batch, current.getGlyphLayout(), current.getTextX(),
						current.getTextY());
			}
			
			else { // images
				game.batch.draw(current.getTexture(), current.getElementX(),
						current.getElementY());
			}
		}
		
		game.batch.end();
		
		update(delta);
	}
	
	public void update(float delta) {
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
	
	private void loadAssets() {
		background = game.mgr.get("img/menu_img.png", Texture.class);
		button = game.mgr.get("img/button.png", Texture.class);
	}

}
