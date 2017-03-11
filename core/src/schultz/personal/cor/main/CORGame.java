package schultz.personal.cor.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class CORGame extends Game {
	
	public AssetManager mgr;
	public SpriteBatch batch;
	public ShapeRenderer shape;
	public BitmapFont mainFont;
	public BitmapFont smallishFont;
	public OrthographicCamera cam;
	public Viewport viewport;
	private FreeTypeFontGenerator defaultFont;
	private FreeTypeFontParameter fontParam;
	
	private int screenWidth = 1024;
	private int screenHeight = 768;
	
	@Override
	public void create () {
		mgr = new AssetManager();
		
		batch = new SpriteBatch();
		shape = new ShapeRenderer();
		mainFont = new BitmapFont();
		
		cam = new OrthographicCamera();
		cam.setToOrtho(false, screenWidth, screenHeight);
		viewport = new FitViewport(screenWidth, screenHeight, cam);
		
		defaultFont = new FreeTypeFontGenerator(Gdx.files.internal("fonts/audiowide-regular.ttf"));
		fontParam = new FreeTypeFontParameter();
		fontParam.size = 35;
		this.mainFont = defaultFont.generateFont(fontParam);
		fontParam.size = 35;
		this.smallishFont = defaultFont.generateFont(fontParam);
		
		this.setScreen(new Loading(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		mgr.dispose();
		batch.dispose();
		shape.dispose();
		mainFont.dispose();
		defaultFont.dispose();
	}
	
	public int getScreenWidth() {
		return screenWidth;
	}
	
	public int getScreenHeight() {
		return screenHeight;
	}
	
	public void setScreenSize(int width, int height) {
		screenWidth = width;
		screenHeight = height;
	}
	
	public FreeTypeFontParameter getFontParam() {
		return fontParam;
	}
}