package Main;

import Entity.NPC_OldMan;
import monster.GreenSlime;
import monster.ORG;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_Potion;
import object.OBJ_axe;
import object.OBJ_chest;
import object.OBJ_door;
import object.OBJ_key;
 
 
public class AssetSetter {
	
	GamePanel gp;
	
public AssetSetter(GamePanel gp) {
	this.gp = gp;
}

public void setObject() {
	
	gp.obj[0] = new OBJ_Coin(gp);
	gp.obj[0].worldX = gp.tileSize*25;
	gp.obj[0].worldY = gp.tileSize*23;
	
	gp.obj[1] = new OBJ_Coin(gp);
	gp.obj[1].worldX = gp.tileSize*21;
	gp.obj[1].worldY = gp.tileSize*19;
	
	gp.obj[2] = new OBJ_Heart(gp);
	gp.obj[2].worldX = gp.tileSize*26;
	gp.obj[2].worldY = gp.tileSize*21;
	
	gp.obj[3] = new OBJ_key(gp);
	gp.obj[3].worldX = gp.tileSize*39;
	gp.obj[3].worldY = gp.tileSize*8;

	gp.obj[4] = new OBJ_axe(gp);
	gp.obj[4].worldX = gp.tileSize*37;
	gp.obj[4].worldY = gp.tileSize*31;
	
	gp.obj[5] = new OBJ_Potion(gp);
	gp.obj[5].worldX = gp.tileSize*22;
	gp.obj[5].worldY = gp.tileSize*27;
	
	gp.obj[6] = new OBJ_door(gp);
	gp.obj[6].worldX = gp.tileSize*10;
	gp.obj[6].worldY = gp.tileSize*12;
	
	gp.obj[7] = new OBJ_chest(gp, new OBJ_key(gp));
	gp.obj[7].worldX = gp.tileSize*10;
	gp.obj[7].worldY = gp.tileSize*8;
	
	gp.obj[8] = new OBJ_door(gp);
	gp.obj[8].worldX = gp.tileSize*14;
	gp.obj[8].worldY = gp.tileSize*27;

}
public void setNPC() {
	
	gp.npc[0] = new NPC_OldMan(gp);
	gp.npc[0].worldX = gp.tileSize*21;
	gp.npc[0].worldY = gp.tileSize*21;
	 
 }
public void setMonster() {
	
	gp.monster[0] = new GreenSlime(gp);
	gp.monster[0].worldX = gp.tileSize*23;
	gp.monster[0].worldY = gp.tileSize*36;
	
	gp.monster[1] = new GreenSlime(gp);
	gp.monster[1].worldX = gp.tileSize*23;
	gp.monster[1].worldY = gp.tileSize*37;
	
	gp.monster[2] = new GreenSlime(gp);
	gp.monster[2].worldX = gp.tileSize*24;
	gp.monster[2].worldY = gp.tileSize*37;
	
	gp.monster[3] = new GreenSlime(gp);
	gp.monster[3].worldX = gp.tileSize*34;
	gp.monster[3].worldY = gp.tileSize*42;
	
	gp.monster[4] = new GreenSlime(gp);
	gp.monster[4].worldX = gp.tileSize*38;
	gp.monster[4].worldY = gp.tileSize*42;
	
	gp.monster[5] = new GreenSlime(gp);
	gp.monster[5].worldX = gp.tileSize*38;
	gp.monster[5].worldY = gp.tileSize*10;
	
	gp.monster[6] = new GreenSlime(gp);
	gp.monster[6].worldX = gp.tileSize*39;
	gp.monster[6].worldY = gp.tileSize*11;
	
	gp.monster[7] = new ORG(gp);
	gp.monster[7].worldX = gp.tileSize*12;
	gp.monster[7].worldY = gp.tileSize*33;
	
	gp.monster[8] = new ORG(gp);
	gp.monster[8].worldX = gp.tileSize*37;
	gp.monster[8].worldY = gp.tileSize*10;
	
}
}

