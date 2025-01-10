package object;

import Entity.Kentity;
import Main.GamePanel;

public class OBJ_axe extends Kentity{

	public OBJ_axe(GamePanel gp) {
		super(gp);
		
		type = type_axe;
		name = " Woodcutter's axe";
		down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
		attackValue = 2;
		attackArea.width = 30;
		attackArea.height = 30;
		description = "[Woodcutter's axe]\nA bit rusty but still \ncan cut sometrees.";
		KnockBackPower = 10; 
		motion1_duration = 20;
		motion2_duration = 40;
	}

}
