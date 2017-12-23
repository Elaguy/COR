package schultz.personal.cor.helpers;

import java.util.ArrayList;

/*
 * Pieces all of the UI elements together and, using
 * each element's dimensions, the margin specified, and
 * the game screen's current resolution, places each element
 * in its correct position on the screen. Specially suited for
 * main menus, but will probably be used elsewhere in the game.
 * Each element is added to the UI which stores it for recall into
 * an ArrayList of generic type.
 * 
 * Will probably break if used in an unusual manner.
 */

public class UI {
	
	private int screenWidth;
	private int screenHeight;
	private int margin;
	private boolean vertMode; // if true, elements will be placed vertically, otherwise placed horizontally
	private float offsetX, offsetY;
	
	private ArrayList<UiElement> elements = new ArrayList<UiElement>();
	
	// offsets can be 0 if there is no position difference you want to use
	public UI(int width, int height, int margin, float offsetX, float offsetY, boolean vertMode) {
		screenWidth = width;
		screenHeight = height;
		this.margin = margin;
		this.vertMode = vertMode;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	/*
	 * Where the magic happens. Calculates positions and
	 * records each element's optimal screen position
	 * Note: each element has two margins
	 */
	public void calculateElements() {
		int usableWidth = screenWidth - (margin * elements.size()*2);
		int usableHeight = screenHeight - (margin * elements.size()*2);
		
//		for(int i = 0; i < elements.size(); i++) {
//			totalWidth += elements.get(i).getWidth();
//			totalHeight += elements.get(i).getHeight();
//		}
		
		if(vertMode) {
			if(usableHeight >= 0) { // if its possible to place the elements at all
				float pos = (margin + elements.get(0).getHeight());
				
				for(int i = 0; i < elements.size(); i++) {
					UiElement current = elements.get(i);
					current.setElementY(pos + offsetY);
					current.setElementX(((screenWidth/2) - (current.getWidth()/2)) + offsetX); // center on x axis
					
					if(current.isButton()) {
						current.setTextX((current.getElementX() + 
								(current.getWidth() - current.getGlyphLayout().width)/2));
						current.setTextY((current.getElementY() + 
								(current.getHeight() + current.getGlyphLayout().height)/2));
					}
					
					pos += (margin + current.getHeight());
				}
			}
		}
		
		else {
			if(usableWidth >= 0) {
				float pos = (margin + elements.get(0).getWidth());
				
				for(int i = 0; i < elements.size(); i++) {
					UiElement current = elements.get(i);
					current.setElementX(pos + offsetX);
					current.setElementY(((screenHeight/2) - (current.getHeight()/2)) + offsetY);
					
					if(current.isButton()) {
						current.setTextX((current.getElementX() + 
								(current.getWidth() - current.getGlyphLayout().width)/2));
						current.setTextY((current.getElementY() + 
								(current.getHeight() + current.getGlyphLayout().height)/2));
					}
					
					pos += (margin + current.getWidth());
				}
			}
		}
	}
	
	public void setOffsets(float offsetX, float offsetY) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
	}
	
	public void addUiButton(UiButton button) {
		elements.add(button);
	}
	
	public void addUiImage(UiImage img) {
		elements.add(img);
	}
	
	public void addUiText(UiText text) {
		elements.add(text);
	}
	
	public ArrayList<UiElement> getElements() {
		return elements;
	}
	
	public void setWidth(int width) {
		screenWidth = width;
	}
	
	public void setHeight(int height) {
		screenHeight = height;
	}
	
}
