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

		buttons.add(new Button(200, 200, "Start Game"));

	}

}
