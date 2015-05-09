package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import launcher.GamePanel;
import entity.Gun;
import entity.utility.Grenade;
import entity.utility.Utility;
import gameState.inGame.InGame;

public class Inventory {

	private Gun slot1;
	private Gun slot2;
	private Gun slot3;

	public HashMap<Utility, Integer> slot4;
	public HashMap<Utility, Integer> slot5;

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

		if (slot2 == gun) {
			slot2.setBullets(gun.getMaxBullets());
			slot2.setCurrentClip(gun.getClipSize());
			return true;
		}

		if (slot3 == gun) {
			slot3.setBullets(gun.getMaxBullets());
			slot3.setCurrentClip(gun.getClipSize());
			return true;
		}

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

		if (getFirstKey(slot4) instanceof Grenade && utility instanceof Grenade
				&& getFirstValue(slot4) < 3) {
			int amount = getFirstValue(slot4);
			slot4.clear();
			slot4.put(utility, amount + 1);
			return true;
		}

		if (slot5.isEmpty()) {
			slot5.put(utility, 1);
			return true;
		}

		if (getFirstKey(slot5) instanceof Grenade && utility instanceof Grenade
				&& getFirstValue(slot5) < 3) {
			int amount = getFirstValue(slot5);
			slot5.clear();
			slot5.put(utility, amount + 1);
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
			if (!slot4.isEmpty() && getFirstValue(slot4) > 1) {
				int currentAmount = getFirstValue(slot4);
				Utility currentUtility = (Utility) getFirstKey(slot4);
				slot4.clear();
				slot4.put(currentUtility, currentAmount - 1);
				return true;
			} else if (!slot4.isEmpty()) {
				slot4.clear();
				return true;
			}
			return false;
		case 5:
			if (!slot5.isEmpty() && getFirstValue(slot5) > 1) {
				int currentAmount = getFirstValue(slot5);
				Utility currentUtility = (Utility) getFirstKey(slot5);
				slot5.clear();
				slot5.put(currentUtility, currentAmount - 1);
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
		if (!InGame.player.isReloading())
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

	public int getSelectedSlot() {
		return selectedSlot;
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
			case 4:
				if (!slot4.isEmpty()) {
					BufferedImage texture = getFirstKey(slot4).getTexture();
					g.drawRenderedImage(texture, GamePanel.getAffineTransform(
							texture, 20 + i * 70,
							GamePanel.WINDOW_HEIGHT - 130, 0.1, 0));
					if (getFirstValue(slot4) != 1) {
						g.setFont(new Font("Century Gothic", Font.PLAIN, 15));
						g.setColor(Color.WHITE);
						g.drawString(getFirstValue(slot4) + "", 55 + i * 70,
								GamePanel.WINDOW_HEIGHT - 85);
					}
				}
				break;
			case 5:
				if (!slot5.isEmpty()) {
					BufferedImage texture = getFirstKey(slot5).getTexture();
					g.drawRenderedImage(texture, GamePanel.getAffineTransform(
							texture, 20 + i * 70,
							GamePanel.WINDOW_HEIGHT - 130, 0.1, 0));
					if (getFirstValue(slot5) != 1) {
						g.setFont(new Font("Century Gothic", Font.PLAIN, 15));
						g.setColor(Color.WHITE);
						g.drawString(getFirstValue(slot5) + "", 55 + i * 70,
								GamePanel.WINDOW_HEIGHT - 85);
					}
				}
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

	public static Utility getFirstKey(HashMap<Utility, Integer> map) {
		for (Utility u : map.keySet())
			return u;
		return null;
	}

	public static int getFirstValue(HashMap<Utility, Integer> map) {
		for (int i : map.values())
			return i;
		return -1;
	}

}
