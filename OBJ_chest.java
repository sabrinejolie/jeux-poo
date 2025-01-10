package object;

import Entity.Kentity;
import Main.GamePanel;

public class OBJ_chest extends Kentity {
	
	GamePanel gp;
	Kentity loot;
	boolean opened = false;

	public OBJ_chest(GamePanel gp, Kentity loot){
		super(gp);
		this.gp = gp;
		this.loot = loot;
		
		type = type_obstacle;
		name = "Chest";
		image = setup("/objects/chest", gp.tileSize, gp.tileSize);
		image1 = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
		down1 = image;
		collision = true;
		
		solidArea.x = 4;
		solidArea.y = 16;
		solidArea.width = 40;
		solidArea.height = 32;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
	}
	public void intract() {
		gp.gameState = gp.dialogueState;
		
		if(opened == false) {
			gp.playSE(3);
			
			StringBuilder sb = new StringBuilder();
			sb.append("you open the chest and find a "+ loot.name + "!");
			
			if(gp.player.inventory.size() == gp.player.maxInventorySize) {
				sb.append("\n....but you can't carry any more");
			}
			else {
				sb.append("\nYou obtain the "+ loot.name + "!");
				gp.player.inventory.add(loot);
				down1 = image1;
				opened = true;
			}
			gp.u1.currentDialogue = sb.toString();
		}
		else {
			gp.u1.currentDialogue = "you won !";
		}
  
	
}
}
