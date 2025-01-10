package Entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import Main.GamePanel;
import Main.UtilityTool;

public class Kentity {

	GamePanel gp;
 
  public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
  public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2,
  guardUp,guardDown, guardLeft, guardRight;
  public BufferedImage image, image1, image2;
  public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
  public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
  public int solidAreaDefaultX, solidAreaDefaultY;
  public boolean collision = false;
  String dialogues[] = new String[20];
  public Kentity attacker;
  
 //STATE
  public int worldX, worldY;
  public String direction = "down";
  public int spriteNum = 1;
  int dialogueIndex = 0;
  public boolean collisionOn = false;
  public boolean invincible = false;
  public boolean attacking = false ;
  public boolean alive = true;
  public boolean dying = false;
  boolean hpBarOn = false;
  public boolean KnockBack = false;
  public String KnockBackDirection;
  public boolean guarding = false;
  public boolean offBalance = false;

//COUNTER
  public int spriteCounter = 0;
  public int actionLookCounter = 0;
  public int invincibleCounter = 0;
  public int shotAvailableCounter = 0;
  int dyingCounter = 0;
  int hpBarOnCounter = 0 ;
  int KnockBackCounter = 0;
  public int guardCounter = 0;
  int offBalanceCounter = 0;

// CHARACTER ATTRIBUTES
  public String name;
  public int defaultSpeed;
  public int speed;
  public int maxLife;
  public int Life;
  public int maxMana;
  public int mana;
  public int ammo;
  public int level;
  public int strength;
  public int dexterity;
  public int attack;
  public int defense;
  public int exp;
  public int nextLevelExp;
  public int coin;
  public int motion1_duration;
  public int motion2_duration;
  public Kentity currentWeapon;
  public Kentity currentShield;
  public Projectile projectile;
  
  //ITEM AtTTRIBUTES
  public int value ;
  public int attackValue;
  public int defenseValue;
  public String description = "";
  public int useCost;
  public int KnockBackPower = 0;
  
  //TYPE
  public int type ; 
  public final int type_player = 0;
  public final int type_npc = 1;
  public final int type_monster = 2;
  public final int type_sword = 3;
  public final int type_axe = 4;
  public final int type_shield = 5;
  public final int type_consumable = 6;
  public final int type_pickupOnly = 7;
  public final int type_obstacle = 8;

public Kentity(GamePanel gp) {
	this.gp = gp;
}
public int  getLeftX() {
	return worldX + solidArea.x;
}
public int  getRightX() {
	return worldX + solidArea.x + solidArea.width;
}
public int  getTopY() {
	return worldY + solidArea.y;
}
public int  getBottomY() {
	return worldY + solidArea.y + solidArea.height;
}
public int  getCol() {
	return (worldX + solidArea.x)/gp.tileSize;
}
public int  getRow() {
	return (worldY + solidArea.y)/gp.tileSize;
}
public void setAction() { }
public void damageReaction() { }
public void speak() {
	
	if(dialogues[dialogueIndex] == null) {
		dialogueIndex = 0;
	}
	gp.u1.currentDialogue = dialogues[dialogueIndex];
	dialogueIndex++;
	
	switch (gp.player.direction) {
	case "up":
		direction = "down";
		break;
	case "down":
		direction = "up";
		break;
	case "left":
		direction = "right";
		break;
	case "right":
		direction = "left";
		break;
	}
}
public void interact () {
	
}
 public boolean use(Kentity kentity) {
	 return false;
 }
 public void checkDrop() {
	 
 }
 public void dropItem(Kentity droppedItem) {
	 
	 for(int i = 0; i < gp.obj.length; i++) {
		 if(gp.obj[i] == null) {
			 gp.obj[i] = droppedItem;
			 gp.obj[i].worldX = worldX;// the dead monster's worldX
			 gp.obj[i].worldY = worldY;
			 break;
		 }
	 }
 }
 public void update() {
	
	 if(KnockBack == true) {
		 
			collisionOn = false;
			gp.cChecker.checkTile(this);
			gp.cChecker.checkObject(this, false);
			gp.cChecker.checkKentity(this, gp.npc);
			gp.cChecker.checkKentity(this, gp.monster);
			boolean contactPlayer = gp.cChecker.checkPlayer(this);
			
			if(this.type == type_monster && contactPlayer == true) {
			    damagePlayer(attack);
			}
			
			if(collisionOn == true) {
				KnockBackCounter = 0;
				KnockBack = false;
				speed = defaultSpeed;
			}
			else if (collisionOn == false) {
				switch(KnockBackDirection) {
				case"up": worldY -= speed; break;
				case"down":worldY += speed; break;
				case"left": worldX  -= speed; break;
				case"right":worldX +=  speed; break;
				}
			}
			
			spriteCounter++;
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteCounter == 2) {
					spriteNum = 1 ;
				}
				spriteCounter = 0;
			}
				
			KnockBackCounter++;
			if(KnockBackCounter == 10) {
				KnockBackCounter = 0;
				KnockBack = false;
				speed = defaultSpeed;
			}
	      }else if(attacking == true){
	    	  attacking();
	      }
	        else {
			setAction();
			
			collisionOn = false;
			gp.cChecker.checkTile(this);
			gp.cChecker.checkObject(this, false);
			gp.cChecker.checkKentity(this, gp.npc);
			gp.cChecker.checkKentity(this, gp.monster);
			boolean contactPlayer = gp.cChecker.checkPlayer(this);
			
			if(this.type == type_monster && contactPlayer == true) {
			    damagePlayer(attack);
			}
			
			//IF COLLISION IS FALSE PLAYER CAN MOVE
			if(collisionOn == false) {
				
				switch(direction) {
				case"up": worldY -= speed; break;
				case"down":worldY += speed; break;
				case"left": worldX  -= speed; break;
				case"right":worldX +=  speed; break;
				}
			}
	 }

	if(invincible == true) {
		invincibleCounter++;
		if(invincibleCounter > 40) {
			invincible = false;
			invincibleCounter = 0;
		}
	}
	if(shotAvailableCounter < 30) {
		shotAvailableCounter ++;
	}
	if(offBalance == true) {
		offBalanceCounter++;
		if(offBalanceCounter > 60) {
			offBalance = false;
			offBalanceCounter = 0;
		}
	}
	
}   public String getOppositeDirection(String direction) {
	String oppositDirection ="";
	switch(direction) {
	case "up": oppositDirection = "down";break;
	case "down": oppositDirection = "up";break;
	case "left": oppositDirection = "right";break;
	case "right": oppositDirection = "left";break;
	}
	return oppositDirection;
}
	public void attacking () {
		
		spriteCounter++;
		
		if(spriteCounter <= motion1_duration ) {
			spriteNum = 1;
		}
		if(spriteCounter > motion1_duration  && spriteCounter <= motion2_duration ) {
			spriteNum = 2;
			//save the current worldX, worldY, solidArea
			int currentWorldX = worldX;
			int currentWorldY = worldY;
			int solidAreaWidth = solidArea.width;
			int solidAreaHeight = solidArea.height;
			
			//adjust player's worldX/Y for the attackArea
			switch(direction) {
			case "up": worldY -= attackArea.height;break;
			case "down": worldY += attackArea.height;break;
			case "left": worldY -= attackArea.width;break;
			case "right": worldY += attackArea.width;break;
			}
			// attackArea becames solidArea
			solidArea.width = attackArea.width;
			solidArea.height = attackArea.height;
			if(type == type_monster) {
				if(gp.cChecker.checkPlayer(this) == true) {
					damagePlayer(attack);
				}
			}
			else {//player
				//check monster collision with the updated worldX\Y and solidArea
				int monsterIndex = gp.cChecker.checkKentity(this, gp.monster);
				gp.player.damageMonster(monsterIndex, this, attack, currentWeapon.KnockBackPower);
				
				int projectileIndex = gp.cChecker.checkKentity( this,  gp.projectile);
				gp.player.damageProjectile(projectileIndex);
			}
		
			
			//after cheking collision, resorte the original data
			worldX = currentWorldX;
			worldY = currentWorldY;
			solidArea.width = solidAreaWidth;
			solidArea.height = solidAreaHeight;
		}
		if(spriteCounter > motion2_duration  ) {
			spriteNum = 1;
			spriteCounter = 0;
			attacking = false;
		}
	}
	public void checkAttackOrNot(int rate, int straight, int holizontal) {
		
		boolean targetInRange = false;
		int xDis = gp.tileSize;
		int yDis = gp.tileSize;
		
		switch(direction) {
		case "up": if( gp.player.worldY < worldY && yDis < straight && xDis < holizontal) {
			 targetInRange = true;
		}break;
		case "down": if( gp.player.worldY > worldY && yDis < straight && xDis < holizontal) {
			 targetInRange = true;
		}break;
		case "left": if( gp.player.worldX < worldX && xDis < straight && xDis < holizontal) {
			 targetInRange = true;
		}break;
		case "right": if( gp.player.worldX > worldX && xDis < straight && xDis < holizontal) {
			 targetInRange = true;
		}break; 
		}
		if(targetInRange == true) {
			//Check if it initiates an attack
			int i = new Random().nextInt(rate);
			if(i == 0) {
				attacking = true;
				spriteNum = 1;
				spriteCounter = 0;
				shotAvailableCounter = 0;
			}
		}
	}
 public void damagePlayer(int attack) {
	 
			if(gp.player.invincible == false) {
				 int damage = attack - gp.player.defense;
				 //Get an opposite direction of this attacker
				 String canGuardDirection = getOppositeDirection(direction);
				 if(gp.player.guarding == true && gp.player.direction.equals(canGuardDirection)) {
					 
					 //parry
					 if(gp.player.guardCounter < 10) {
						 damage = 0;
						 gp.playSE(15);
						 setKnockBack(this, gp.player, KnockBackPower);
						 offBalance =  true;
						 spriteCounter =- 60;
					 }//Normal guard
					 damage /= 3;
					 gp.playSE(14);
				 }
				 else {
					 //not guarding
					 gp.playSE(6);
					 if(damage < 1) {
						 damage = 1;
				 }
			}
				 if(damage != 0) {
					 setKnockBack(gp.player, this, KnockBackPower);
				 }
				gp.player.Life -=  damage;
				gp.player.invincible = true;
			}
 }
 public void setKnockBack(Kentity target, Kentity attacker, int KnockBackPower) {
	 
	 this.attacker = attacker;
	 target.KnockBackDirection = attacker.direction;
	 target.speed += KnockBackPower;
	 target.KnockBack = true;
 }
public void draw(Graphics2D g2) {
	
	BufferedImage image = null;
	int screenX = worldX - gp.player.worldX + gp.player.screenX;
	int screenY = worldY - gp.player.worldY + gp.player.screenY;
	
	if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
	   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
	   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
	   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY ) {
		
		
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		switch(direction) {
		case "up":
			if( attacking == false) {
			  if(spriteNum == 1) {image = up1;}
			  if(spriteNum == 2) {image = up2;}
			}
			if(attacking == true) {
				tempScreenY = screenY - gp.tileSize;
				if(spriteNum == 1) {image = attackUp1;}
				 if(spriteNum == 2) {image = attackUp2;}
			}
			break;
		case "down":
			if(attacking == false) {
				if(spriteNum == 1) {image = down1;}
				if(spriteNum == 2) {image = down2;}
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = attackDown1;}
				if(spriteNum == 2) {image = attackDown2;}
			}
			break;
		case "left":
			if(attacking == false) {
				if(spriteNum == 1) {image = left1;}
				if(spriteNum == 2) {image = left2;}
			}
			if(attacking == true) {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum == 1) {image = attackLeft1;}
				if(spriteNum == 2) {image = attackLeft2;}
			}
			break;
		case "right":
			if(attacking == false ) {
				if(spriteNum == 1) {image = right1;}
				if(spriteNum == 2) {image = right2;}
			}
			if(attacking == true) {
				if(spriteNum == 1) {image = attackRight1;}
				if(spriteNum == 2) {image = attackRight2;}
			}
			break;
		}
		
		//Monster HP Bar
		if(type == 2 && hpBarOn == true) {
			
			double oneScale = (double)gp.tileSize/maxLife;
			double hpBarValue = oneScale*Life;
			
			g2.setColor(new Color(35, 35, 35));
			g2.fillRect(screenX - 1, screenY - 16, gp.tileSize + 2, 12);
			
			g2.setColor( new Color(255,0, 30));
			g2.fillRect(screenX, screenY - 15, (int)hpBarValue, 10);
			
			hpBarOnCounter++;
			
			if(hpBarOnCounter > 600 ) {
				hpBarOnCounter = 0;
				hpBarOn = false;
			}
				
			}
		 
		
		if(invincible == true) {
			hpBarOn = true;
			hpBarOnCounter = 0 ;
		     changeAlpha(g2, 0.4f);
		}
		if(dying == true) {
			dyingAnimation(g2);
		}

		g2.drawImage(image, tempScreenX, tempScreenY, null);
	
		changeAlpha(g2, 1f);
		
	}
	
}
public void dyingAnimation(Graphics2D g2) {
	
	dyingCounter++;
	int i = 5;
	
	if(dyingCounter <= i) {changeAlpha(g2,0f);}
	if(dyingCounter > i && dyingCounter <= i*2) {changeAlpha(g2,1f);}
	if(dyingCounter > i*2 && dyingCounter <= i*3) {changeAlpha(g2,0f);}
	if(dyingCounter > i*3 && dyingCounter <= i*4) {changeAlpha(g2,1f);}
	if(dyingCounter > i*4 && dyingCounter <= i*5) {changeAlpha(g2,0f);}
	if(dyingCounter > i*5 && dyingCounter <= i*6) {changeAlpha(g2,1f);}
	if(dyingCounter > i*6 && dyingCounter <= i*7) {changeAlpha(g2,0f);}
	if(dyingCounter > i*7 && dyingCounter <= i*8) {changeAlpha(g2,1f);}
	if(dyingCounter > i*8) {
		dying = false;
		alive = false;
	}
}
public void changeAlpha(Graphics2D g2, float alphaValue) {
	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
}
public BufferedImage setup(String imagePath, int width, int height) {
	
	UtilityTool uTool = new UtilityTool();
	BufferedImage image = null;
	try {
		image = ImageIO.read(getClass().getResourceAsStream( imagePath + ".png"));
		image = uTool.scaleImage(image, width, height);
		
	}catch(IOException e) {
		e.printStackTrace();
		
	}
	return image;

}
public int getDetecterd(Kentity user, Kentity target[],String targetName) {
	
	int index = 999;
	//Check the surrounding object
	int nextWorldX = user.getLeftX();
	int nextWorldY = user.getTopY();
	
	switch(user.direction) {
	case "up" : nextWorldY = user.getTopY()-1;break;
	case "down" : nextWorldY = user.getBottomY()+1;break;
	case "Left" : nextWorldX = user.getLeftX()-1;break;
	case "Right" : nextWorldX = user.getRightX()+1;break;
	}
	int col = nextWorldX/gp.tileSize;
	int row = nextWorldY/gp.tileSize;
	
	for(int i = 0; i  < target.length; i++) {
		if(target[i] != null) {
			if(target[i].getCol() == col && target[i].getRow() == row
					&& target[i].name.equals(targetName)) {
				index = i;
				break;
			}
		}
	}
	return index;
}

}
