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
import schultz.personal.cor.helpers.UiImage;
import schultz.personal.cor.helpers.UiText;

public class MainMenu implements Screen {

	private CORGame game;
	private Texture background;
	
	private UI ui;
	private UiText gameTitle;
	private UiButton startButton;
	private UiButton quitButton;
	
	public MainMenu(CORGame game) {
		this.game = game;
		
		game.cam.update();
		
		ui = new UI(game.viewport.getScreenWidth(), game.viewport.getScreenHeight(), 80, true);
		
		gameTitle = new UiText("Centripetal Orbital Racers", game);
		startButton = new UiButton(new Texture(Gdx.files.internal("img/button.png")), "Start Game", game);
		quitButton = new UiButton(new Texture(Gdx.files.internal("img/button.png")), "Quit Game", game);
		
		ui.addUiButton(quitButton); // must flip order because actual rendering is
		ui.addUiButton(startButton); // background for some reason
		ui.addUiText(gameTitle);
		
		ui.calculateElements();

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
		game.batch.draw(background, 0, 0);
		
		for(int i = 0; i < ui.getElements().size(); i++) {
			if(ui.getElements().get(i).isText()){ // text
				game.mainFont.draw(game.batch, ui.getElements().get(i).getGlyphLayout(), ui.getElements().get(i).getElementX(),
						ui.getElements().get(i).getElementY());
			}
			
			else if(ui.getElements().get(i).isButton()) { // buttons
				game.batch.draw(ui.getElements().get(i).getTexture(), ui.getElements().get(i).getElementX(),
						ui.getElements().get(i).getElementY());
				game.smallishFont.draw(game.batch, ui.getElements().get(i).getGlyphLayout(), ui.getElements().get(i).getTextX(),
						ui.getElements().get(i).getTextY());
			}
			
			else { // images
				game.batch.draw(ui.getElements().get(i).getTexture(), ui.getElements().get(i).getElementX(),
						ui.getElements().get(i).getElementY());
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
		background = new Texture(Gdx.files.internal("img/menu_img.png"));
	}

}
