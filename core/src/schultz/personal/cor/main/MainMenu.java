package schultz.personal.cor.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;

import schultz.personal.cor.helpers.UI;
import schultz.personal.cor.helpers.UiButton;
import schultz.personal.cor.helpers.UiElement;
import schultz.personal.cor.helpers.UiText;

public class MainMenu implements Screen {

	private CORGame game;
	private Texture background;
	private Texture button;
	private Texture buttonSelected;
	private Texture buttonPressed;
	
	private Vector3 mousePos;
	
	private UI ui;
	private UiText gameTitle;
	private UiButton startButton;
	private UiButton quitButton;
	
	private Music menu1;
	
	public MainMenu(CORGame game) {
		this.game = game;
		
		game.cam.update();
		
		ui = new UI(game.viewport.getScreenWidth(), game.viewport.getScreenHeight(), 80, 0, 0, true);
		
		loadAssets();
		
		mousePos = new Vector3();
		
		gameTitle = new UiText("Centripetal Orbital Racers", 1, game);
		startButton = new UiButton(button, "Start Game", 5, game);
		quitButton = new UiButton(button, "Quit Game", 5, game);
		
		ui.addUiButton(quitButton); // must flip order because actual rendering is
		ui.addUiButton(startButton); // backward for some reason
		ui.addUiText(gameTitle);
		
		ui.calculateElements();
		
		menu1.setLooping(true);
		menu1.play();
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
						current.getElementY(), current.getWidth(),
						current.getHeight());
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
		mousePos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
		game.cam.unproject(mousePos);
		
		Gdx.app.log("FPS", String.valueOf(Gdx.graphics.getFramesPerSecond()));
		
		for(int i = 0; i < ui.getElements().size(); i++) {
			UiElement current = ui.getElements().get(i);
			
			if(current.isButton()) {
				if((mousePos.x >= current.getElementX()) && (mousePos.x <= (current.getElementX() + current.getWidth()))) {
					if((mousePos.y >= current.getElementY()) && (mousePos.y <= (current.getElementY() + current.getHeight()))) {
						current.setTexture(buttonSelected);
						
						if(Gdx.input.isTouched() && (current.getText().equals("Start Game"))) {
							current.setTexture(buttonPressed);
							
							Timer.schedule(new Task() {

								@Override
								public void run() {
									menu1.stop();
									game.setScreen(new Track1(game));
								}
								
							}, 0.5f);
						}
						
						else if(Gdx.input.isTouched() && (current.getText().equals("Quit Game"))) {
							current.setTexture(buttonPressed);
							
							Timer.schedule(new Task() {

								@Override
								public void run() {
									Gdx.app.exit();
								}
								
							}, 0.5f);
						}
							
					}
					
					else
						current.setTexture(button);
				}
				
				else
					current.setTexture(button);
			}
		}
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
		background.dispose();
		button.dispose();
		buttonSelected.dispose();
		buttonPressed.dispose();
	}
	
	private void loadAssets() {
		background = game.mgr.get("img/menu_img.png", Texture.class);
		button = game.mgr.get("img/button.png", Texture.class);
		buttonSelected = game.mgr.get("img/button_selected.png", Texture.class);
		buttonPressed = game.mgr.get("img/button_pressed.png", Texture.class);
		menu1 = game.mgr.get("audio/Unwritten_Return.mp3", Music.class);
	}

}
