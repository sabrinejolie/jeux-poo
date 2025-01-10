package object;

import Entity.Kentity;
import Main.GamePanel;

public class OBJ_key extends Kentity{
	
	GamePanel gp;
	
	public OBJ_key( GamePanel gp){
		super(gp);
		this.gp = gp;
		 
		type = type_consumable;
		name = "key";
		down1 = setup("/objects/key", gp.tileSize, gp.tileSize);
		description = "[" + name + "]\nIt opens the door";
	}
	public boolean	use(Kentity kentity) {
		gp.gameState = gp.dialogueState;
		int objIndex = getDetecterd(kentity, gp.obj, "Door");
		
		if(objIndex != 999) {
			gp.u1.currentDialogue = "You use the" + name + "and open the door";
			gp.playSE(3);
			gp.obj[objIndex] = null;
			return true;
		}
		else {
			gp.u1.currentDialogue  = "What are you doing?";
			return false;
		}
	}
}
