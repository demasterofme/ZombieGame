package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import launcher.GamePanel;
import entity.Gun;
import entity.utility.Grenade;
import entity.utility.Utility;

public class Inventory {

	private Gun slot1;
	private Gun slot2;
	private Gun slot3;

	private HashMap<Utility, Integer> slot4;
	private HashMap<Utility, Integer> slot5;

	private int selectedSlot = 1;

	public Inventory(Gun startGun) {

		this.slot1 = startGun;

		slot4 = new HashMap<>();
		slot5 = new HashMap<>();

	}

	public void update() {

	}

	public boolean hasGunEquipped() {
		switch (selectedSlot) {
		case 1:
			if (slot1 != null)
				return true;
		case 2:
			if (slot2 != null)
				return true;
		case 3:
			if (slot3 != null)
				return true;
		default:
			return false;
		}
	}

	public boolean hasUtilityEquipped() {
		switch (selectedSlot) {
		case 4:
			if (!slot4.isEmpty())
				return true;
		case 5:
			if (!slot5.isEmpty())
				return true;
		default:
			return false;
		}
	}

	public Gun getCurrentGun() {
		switch (selectedSlot) {
		case 1:
			return slot1;
		case 2:
			return slot2;
		case 3:
			return slot3;
		default:
			return null;
		}
	}

	public HashMap<Utility, Integer> getCurrentUtility() {
		switch (selectedSlot) {
		case 4:
			return slot4;
		case 5:
			return slot5;
		default:
			return null;
		}
	}

	public boolean addGun(Gun gun) {

		if (slot2 == null) {
			slot2 = gun;
			return true;
		}

		if (slot3 == null) {
			slot3 = gun;
			return true;
		}

		return false;
	}

	public boolean addUtility(Utility utility) {

		if (slot4.isEmpty()) {
			slot4.put(utility, 1);
			return true;
		}

		if (slot5.isEmpty()) {
			slot5.put(utility, 1);
			return true;
		}

		if (slot4.keySet().iterator().next() instanceof Grenade
				&& utility instanceof Grenade && slot4.get(0) < 3) {
			int amount = slot4.get(0);
			slot4.clear();
			slot4.put(utility, amount++);
			return true;
		}

		if (slot5.keySet().iterator().next() instanceof Grenade
				&& utility instanceof Grenade && slot5.get(0) < 3) {
			int amount = slot5.get(0);
			slot5.clear();
			slot5.put(utility, amount++);
			return true;
		}

		return false;
	}

	public boolean removeItem(int slot) {

		switch (slot) {
		case 1:
			return false;
		case 2:
			if (slot2 != null) {
				slot2 = null;
				return true;
			} else {
				return false;
			}
		case 3:
			if (slot3 != null) {
				slot3 = null;
				return true;
			} else {
				return false;
			}
		case 4:
			if (!slot4.isEmpty() && slot4.get(0) > 1) {
				int currentAmount = slot4.get(0);
				Utility currentUtility = slot4.keySet().iterator().next();
				slot4.clear();
				slot4.put(currentUtility, currentAmount--);
				return true;
			} else if (!slot4.isEmpty()) {
				slot4.clear();
				return true;
			}
			return false;
		case 5:
			if (!slot5.isEmpty() && slot5.get(0) > 1) {
				int currentAmount = slot5.get(0);
				Utility currentUtility = slot5.keySet().iterator().next();
				slot5.clear();
				slot5.put(currentUtility, currentAmount--);
				return true;
			} else if (!slot5.isEmpty()) {
				slot5.clear();
				return true;
			}
			return false;
		default:
			return false;
		}

	}

	public void cycleSelectedSlot(boolean right) {
		if (right) {
			selectedSlot++;
			if (selectedSlot == 6)
				selectedSlot = 1;
		} else {
			selectedSlot--;
			if (selectedSlot == 0)
				selectedSlot = 5;
		}
	}

	public void draw(Graphics2D g) {

		g.setColor(Color.WHITE);
		g.drawString("Guns:", 20, GamePanel.WINDOW_HEIGHT - 138);
		g.drawString("Utilities:", 230, GamePanel.WINDOW_HEIGHT - 138);

		g.setStroke(new BasicStroke(2));

		for (int i = 0; i < 5; i++) {

			switch (i + 1) {
			case 1:
				if (slot1 != null)
					g.drawRenderedImage(slot1.getTexture(), GamePanel
							.getAffineTransform(slot1.getTexture(),
									20 + i * 70, GamePanel.WINDOW_HEIGHT - 130,
									0.1, 45));
				break;
			case 2:
				if (slot2 != null)
					g.drawRenderedImage(slot2.getTexture(), GamePanel
							.getAffineTransform(slot2.getTexture(),
									20 + i * 70, GamePanel.WINDOW_HEIGHT - 130,
									0.1, 45));
				break;
			case 3:
				if (slot3 != null)
					g.drawRenderedImage(slot3.getTexture(), GamePanel
							.getAffineTransform(slot3.getTexture(),
									20 + i * 70, GamePanel.WINDOW_HEIGHT - 130,
									0.1, 45));
				break;
			}

			if (i + 1 == selectedSlot) {
				g.setColor(Color.YELLOW);
				g.drawRect(20 + i * 70, GamePanel.WINDOW_HEIGHT - 130, 50, 50);
				continue;
			}
			g.setColor(Color.white);
			g.drawRect(20 + i * (70), GamePanel.WINDOW_HEIGHT - 130, 50, 50);
		}

	}

}
