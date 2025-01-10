package object;

import Entity.Kentity;
import Main.GamePanel;

public class OBJ_Heart extends Kentity{
	
	GamePanel gp;
	
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_pickupOnly;
		name = "heart";
		value = 2;
		down1 = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
		image = setup("/objects/heart_full", gp.tileSize, gp.tileSize);
		image1 = setup("/objects/heart_half", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/heart_blank", gp.tileSize, gp.tileSize);
	}
	public boolean use (Kentity kentity) {	
		
		gp.playSE(2);
		gp.u1.addMessage( "Life +" + value);
		kentity.Life += value;
		return true ; 
	}

}
