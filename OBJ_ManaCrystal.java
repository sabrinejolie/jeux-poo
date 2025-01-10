package object;

import Entity.Kentity;
import Main.GamePanel;

public class OBJ_ManaCrystal extends Kentity{
	
	GamePanel gp;

	public OBJ_ManaCrystal(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_pickupOnly;
		name = "Mana Crystal";
		value = 1;
		down1 = setup("/objects/manacrystal_full",gp.tileSize, gp.tileSize);
		image = setup("/objects/manacrystal_full",gp.tileSize, gp.tileSize);
		image1 = setup("/objects/manacrystal_blank",gp.tileSize, gp.tileSize);
	}
	public boolean use (Kentity kentity) {	
		
		gp.playSE(2);
		gp.u1.addMessage( "Mana+" + value);
		kentity.mana += value;
		return true; 
	}

}
