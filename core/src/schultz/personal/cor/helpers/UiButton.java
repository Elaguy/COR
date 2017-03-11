package schultz.personal.cor.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import schultz.personal.cor.main.CORGame;

/*
 * Given an image, Button "wraps" it and allows 
 * it to be placed better on the game screen.
 * and also (thanks to FreeType) allows for a more advanced font placement
 * that adjusts to a changing resolution of the game
 * and therefore the button (No more pixelated button text).
 */

public class UiButton extends UiElement {
	
	public UiButton(Texture tex, String text, CORGame game) {
		super(tex, text, game);
	}
	
}
