package object;

import Entity.Kentity;
import Main.GamePanel;

public class OBJ_Potion extends Kentity{

	GamePanel gp;
	
	public OBJ_Potion(GamePanel gp) {
		super(gp);
		
		this.gp = gp;
	
		type = type_consumable;
		name ="Red Potion";
		value = 5;
		down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
	 
		 description = "["+ name + "]\nHeals your life by" + value +".";
	}
	public boolean use (Kentity kentity) {
		
		gp.gameState = gp.dialogueState;
		gp.u1.currentDialogue = "you drink the "+ name + "!\n" + "your life has been recovered.";
		kentity.Life += value;
		if(gp.player.Life > gp.player.maxLife) {
			gp.player.Life = gp.player.maxLife;
		}
		gp.playSE(3);
		return true ;
	}

}
