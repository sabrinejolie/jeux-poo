package Main;



import Entity.Kentity;

public class CollisionChecker {
	GamePanel gp;
	
	public CollisionChecker(GamePanel gp) {
		this.gp =gp;
	}
	
	public void checkTile(Kentity kentity) {
		
		int kentityLeftWorldX = kentity.worldX + kentity.solidArea.x;
		int kentityRightWorldX = kentity.worldX + kentity.solidArea.x + kentity.solidArea.width;
		int kentityTopWorldY = kentity.worldY  + kentity.solidArea.y;
		int kentityBottomWorldY = kentity.worldY + kentity.solidArea.y + kentity.solidArea.height;
		
		int KentityLeftCol = kentityLeftWorldX/gp.tileSize;
		int KentityRightCol = kentityRightWorldX/gp.tileSize;
		int KentityTopRow = kentityTopWorldY/gp.tileSize;
		int KentityBottomRow = kentityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		//Use a temporal direction when it's being KnockBack
		String direction = kentity.direction;
		if(kentity.KnockBack == true) {
			direction = kentity.KnockBackDirection;
		}
		
		switch(direction) {
		case"up" :
			KentityTopRow = (kentityTopWorldY - kentity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[KentityLeftCol][KentityTopRow];
			tileNum2 = gp.tileM.mapTileNum[KentityRightCol][KentityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true ) {
				kentity.collisionOn = true;
			}
			break;
		case"down" :
			KentityBottomRow = ( kentityBottomWorldY + kentity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[KentityLeftCol][KentityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[KentityRightCol][KentityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true ) {
				kentity.collisionOn = true;
			}
			break;
		case"left":
			KentityLeftCol = ( kentityLeftWorldX - kentity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[KentityLeftCol][KentityTopRow];
			tileNum2 = gp.tileM.mapTileNum[KentityRightCol][KentityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true ) {
				kentity.collisionOn = true;
			}
		    break;
		case"right":
			KentityRightCol = ( kentityRightWorldX + kentity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[KentityRightCol][KentityTopRow];
			tileNum2 = gp.tileM.mapTileNum[KentityRightCol][KentityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true ) {
				kentity.collisionOn = true;
			}
			break;
		}
		
	}
	public int checkObject(Kentity kentity, boolean player) {
		
		int index = 999;
		
		for(int i = 0; i < gp.obj.length; i++) {
			if(gp.obj[i] != null) {
				
				// get kentity's solid area position 
				kentity.solidArea.x = kentity.worldX + kentity.solidArea.x;
				kentity.solidArea.y = kentity.worldY + kentity.solidArea.y;
				//get the object's solid area position 
				gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
				gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;
				
				switch(kentity.direction) {
				case"up":	kentity.solidArea.y -= kentity.speed;	break;
				case"down":	kentity.solidArea.y += kentity.speed;	break;
				case"left":	kentity.solidArea.x -= kentity.speed;	break;
				case"right":kentity.solidArea.x += kentity.speed;	break;
					}
					
					if(kentity.solidArea.intersects(gp.obj[i].solidArea)) {
						if(gp.obj[i].collision == true) {
							kentity.collisionOn = true;
						}
						if(player == true) {
							index = i;
						}
					}
			     
				kentity.solidArea.x = kentity.solidAreaDefaultX;
				kentity.solidArea.y = kentity.solidAreaDefaultY;
				gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
				gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
			}
		}
		
		return index;
	}
	//NPC OR MONSTER
  public int checkKentity(Kentity kentity, Kentity[] target) {
	  
	  int index = 999;
	  
	  String direction = kentity.direction;
		if(kentity.KnockBack == true) {
			direction = kentity.KnockBackDirection;
		}
		
		for(int i = 0; i < target.length; i++) {
			
			if(target[i] != null) {
				
				// get kentity's solid area position 
				kentity.solidArea.x = kentity.worldX + kentity.solidArea.x;
				kentity.solidArea.y = kentity.worldY + kentity.solidArea.y;
				//get the object's solid area position 
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;
				
				switch(direction) {
				case"up":   kentity.solidArea.y -= kentity.speed;	break;
				case"down":	kentity.solidArea.y += kentity.speed;	break;
				case"left":	kentity.solidArea.x -= kentity.speed;	break;
				case"right":kentity.solidArea.x += kentity.speed;	break;
			     }
				
				if(kentity.solidArea.intersects(target[i].solidArea)) {
					if(target [i] != kentity) {
						kentity.collisionOn = true;
		                index = i;
					}
					
			    }
				
				kentity.solidArea.x = kentity.solidAreaDefaultX;
				kentity.solidArea.y = kentity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
		}
		return index;
  }
  
  public boolean checkPlayer(Kentity kentity) {
	  
	  boolean contactPlayer = false;
	  
		// get kentity's solid area position 
		kentity.solidArea.x = kentity.worldX + kentity.solidArea.x;
		kentity.solidArea.y = kentity.worldY + kentity.solidArea.y;
		//get the object's solid area position 
		gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
		gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
		
		switch(kentity.direction) {
		case"up":	kentity.solidArea.y -= kentity.speed; break;
		case"down": kentity.solidArea.y += kentity.speed; break;
		case"left": kentity.solidArea.x -= kentity.speed; break;
		case"right":kentity.solidArea.x += kentity.speed; break;
			  }
			if(kentity.solidArea.intersects(gp.player.solidArea)) {
				kentity.collisionOn = true;
				contactPlayer = true ;
			}
	               
		kentity.solidArea.x = kentity.solidAreaDefaultX;
		kentity.solidArea.y = kentity.solidAreaDefaultY;
		gp.player.solidArea.x = gp.player.solidAreaDefaultX;
		gp.player.solidArea.y = gp.player.solidAreaDefaultY;
		
		return contactPlayer;
	}
  }


