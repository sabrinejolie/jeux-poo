package object;

import Entity.Kentity;
import Main.GamePanel;

public class OBJ_door extends Kentity {
	
	GamePanel gp;

	public OBJ_door(GamePanel gp){
		super(gp);
		this.gp = gp;
		
		type = type_obstacle;
		name = "Door";
		down1 = setup("/objects/door", gp.tileSize, gp.tileSize);
		collision = true;
		
		solidArea.x = 0;
		solidArea.y = 16;
		solidArea.width = 48;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	
	public void interact() {
		
		gp.gameState = gp.dialogueState;
		gp.u1.currentDialogue = "You need a key to open this.";
	}
}
