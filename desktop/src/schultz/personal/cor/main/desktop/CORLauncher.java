package schultz.personal.cor.main.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import schultz.personal.cor.main.CORGame;

public class CORLauncher {
	
	private LwjglApplicationConfiguration config;
	
	public static void main (String[] arg) {
		CORLauncher cor = new CORLauncher();
		cor.config = new LwjglApplicationConfiguration();
		cor.config.width = 1027;
		cor.config.height = 768;
		cor.config.title = "COR - Version 0.1i";
		new LwjglApplication(new CORGame(), cor.config);
	}
	
}