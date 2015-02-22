package titleScreen;

import gfx.Button;

import java.awt.Font;
import java.util.ArrayList;

import launcher.GamePanel;

public class TitleScreen {

	public static ArrayList<Button> buttons;

	public TitleScreen() {

		buttons = new ArrayList<>();

		Button.font = new Font("Century Gothic", Font.PLAIN, 42);
		Button.g = GamePanel.image.getGraphics();

		// Add buttons to the screen, will be perfected later
		buttons.add(new Button(true, 200, Button.buttonType.START_GAME, "Start Game"));
		buttons.add(new Button(true, 300, Button.buttonType.OPTIONS, "Options"));
		buttons.add(new Button(true, 400, Button.buttonType.STOP_GAME, "Quit"));
		

	}

}
