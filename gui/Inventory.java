package gui;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import entity.Gun;
import entity.utility.Utility;

public class Inventory {
	
	private Gun slot1;
	private Gun slot2;
	private Gun slot3;
	
	private HashMap<Utility, Integer> slot4;
	private HashMap<Utility, Integer> slot5;
	
	public Inventory(Gun startGun) {
		
		this.slot1 = startGun;
		
	}
	
	public void update() {
		
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
	}
	
	public void draw(Graphics2D g) {
		
	}

}
