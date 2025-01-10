package object;

import Entity.Kentity;
import Main.GamePanel;

public class OBJ_Shield_Wood extends Kentity {
	 
	public  OBJ_Shield_Wood (GamePanel gp){
		super(gp);
		
		type = type_shield;
		name = "Wood Shield";
		down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
		defenseValue = 1;
		description = "[" + name + "]\nMade by wood";
	}
		 

}
