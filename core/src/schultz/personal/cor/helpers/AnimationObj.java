package schultz.personal.cor.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class AnimationObj {
	
	private Animation<TextureRegion> ani;
	
	private float stateTime;
	private int spriteWidth;
	private int spriteHeight;

	private boolean loop;

	private TextureRegion currentFrame;
	private TextureRegion initialFrame;
	
	public AnimationObj(int fps, int frameCols, int frameRows, Texture spriteSheet, boolean loop) {	
		stateTime = 0f;
		
		this.loop = loop;
		
		spriteWidth = spriteSheet.getWidth() / frameCols;
		spriteHeight = spriteSheet.getHeight() / frameRows;
		
		TextureRegion[][] tmp = TextureRegion.split(spriteSheet, spriteWidth
				, spriteHeight);
		
		TextureRegion[] frames = new TextureRegion[frameCols * frameRows];
		
		int index = 0;
		for(int i = 0; i < frameRows; i++) {
			for(int j = 0; j < frameCols; j++)
				frames[index++] = tmp[i][j];
		}
		
		ani = new Animation<TextureRegion>((float) 1 / fps, frames);
		
		currentFrame = ani.getKeyFrame(stateTime, loop);
		initialFrame = frames[0];
	}
	
	public void update() {
		stateTime += Gdx.graphics.getDeltaTime();
		currentFrame = ani.getKeyFrame(stateTime, loop);
	}
	
	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}
	
	public TextureRegion getInitialFrame() {
		return initialFrame;
	}
	
	public int getSpriteWidth() {
		return spriteWidth;
	}
	
	public int getSpriteHeight() {
		return spriteHeight;
	}
}
