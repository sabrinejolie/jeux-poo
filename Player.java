package Entity;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Main.GamePanel;
import Main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;
import object.OBJ_key;

public class Player extends Kentity {
	
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	int counter2 = 0;
	public boolean attackCanceled = false;
	public ArrayList<Kentity> inventory = new ArrayList<>();	
	public final int maxInventorySize = 20;
	 
	 public Player(GamePanel gp, KeyHandler keyH) {
		super(gp);
		this.keyH = keyH;
		
		screenX = gp.screenWidth/2 - (gp.tileSize/2);
		screenY = gp.screenHeight/2 - (gp.tileSize/2);
	//SOLID AREA
	solidArea = new Rectangle();
	solidArea.x = 8;
	solidArea.y = 16;
	solidAreaDefaultX = solidArea.x;
	solidAreaDefaultY = solidArea.y;
	solidArea.width = 32;
	solidArea.height = 32; 
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAttack();
		getGuardImage();
		setItems();
		
	}
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
	    worldY = gp.tileSize * 21;
	    defaultSpeed = 4;
		speed = defaultSpeed;
		direction ="down";
		
		// PLAYER STATUS
		level = 1;
		maxLife = 8;
		Life = maxLife;
		maxMana = 4;
		mana = maxMana;
		ammo = 10;
		strength = 1;// more strength he has, the more damage he gives
		dexterity = 1;// more dexterity he has, the less damage he receives
		nextLevelExp =5;
		coin = 0;
		currentWeapon = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		projectile = new OBJ_Fireball(gp);
		//projectile = new OBJ_Rock(gp);
		attack = getAttack();// the total attack value is decided by strenght and weapon
		defense = getDefense();// the total defense value is decided by dexterity abd shield
		
	}
	public void setDefaultPosition() {
		
		worldX = gp.tileSize * 23;
	    worldY = gp.tileSize * 21;
	    direction ="down";  
	}
	public void restoreLifeAndMana() {
		
		Life = maxLife;
		mana = maxMana;
		invincible = false;
	}
	public void setItems() {
		inventory.clear();
		inventory.add(currentWeapon);
		inventory.add(currentShield);
		inventory.add(new OBJ_key(gp));
	}
	public int getAttack() {
		attackArea = currentWeapon.attackArea;
		motion1_duration = currentWeapon.motion1_duration;
		motion2_duration = currentWeapon.motion2_duration;
		return attack = strength * currentWeapon.attackValue;
	}
	public int getDefense() {
		return defense = dexterity * currentShield.defenseValue;
	}
	public void getPlayerImage() {
		up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
	}
 	public void getPlayerAttack() {
 		if(currentWeapon.type == type_sword) {
		attackUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
		attackUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
		attackDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
		attackDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
		attackLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
		attackLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
		attackRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
		attackRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
 		}
 		if(currentWeapon.type == type_axe){
 				attackUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
 				attackUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
 				attackDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
 				attackDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
 				attackLeft1 = setup("/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
 				attackLeft2 = setup("/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
 				attackRight1 = setup("/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
 				attackRight2 = setup("/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
 		}
	}
 	public void getGuardImage() {
 	        guardUp = setup("/player/boy_guard_up", gp.tileSize, gp.tileSize);
			guardDown = setup("/player/boy_guard_down", gp.tileSize, gp.tileSize);
			guardLeft = setup("/player/boy_guard_left", gp.tileSize, gp.tileSize);
			guardRight = setup("/player/boy_guard_right", gp.tileSize, gp.tileSize);
 	}
	public void update() {
		
		
		 if(KnockBack == true) {
				collisionOn = false;
				gp.cChecker.checkObject(this, true);
				gp.cChecker.checkKentity(this, gp.npc);
			    gp.cChecker.checkKentity(this, gp.monster);	
			 
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
		 }
		 else if(attacking == true) {
		    attacking();	
		}
		else if (keyH.spacePressed == true) {
			guarding = true;
			guardCounter++;
		}
		else if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || 
				keyH.rightPressed == true || keyH.enterPressed == true  ) {
		
			if(keyH.upPressed == true) {
				direction = "up";
				
			}else if(keyH.downPressed == true) {
				direction = "down";
		
			} else if (keyH.leftPressed == true) {
				direction = "left";
				
			} else if (keyH.rightPressed == true) {
				direction = "right";
			
			}
			   spriteCounter++; 
			   if(spriteCounter > 12) {
				   if(spriteNum == 1) {
					   spriteNum = 2;
				   }
				   else if(spriteNum == 2) {
					   spriteNum = 1;
					   }
				   spriteCounter = 0;
			   }
			//CHECK TILE COLLISION
			collisionOn = false;
			gp.cChecker.checkTile(this);
			
			//CHECK OBJECT COLLISION 
			int objIndex = gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			// CHECK NPC COLLISION
			int npcIndex = gp.cChecker.checkKentity(this, gp.npc);
			interactNPC(npcIndex);
			
			//CHECK MONSTER COLLISION
			int monsterIndex = gp.cChecker.checkKentity(this, gp.monster);
            contactMonster(monsterIndex);		 
			
			//CHECK EVENT
			gp.event.checkEvent();
			
			//IF COLLISION IS FALSE PLAYER CAN MOVE
			if(collisionOn == false && keyH.enterPressed == false) {
				
				switch(direction) {
				case"up": worldY -= speed; break;
				case"down":worldY += speed; break;
				case"left": worldX  -= speed; break;
				case"right":worldX +=  speed; break;
				}
			}
			
			if(keyH.enterPressed == true && attackCanceled == false) {
				attacking = true;
				spriteCounter = 0;
			}
			
			attackCanceled = false;
			gp.keyH.enterPressed = false;
			guarding = false;
			guardCounter = 0;
			
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
			guarding = false;
			guardCounter = 0;
		}
		
		if(gp.keyH.shotKeyPressed == true && projectile.alive == false && shotAvailableCounter == 30 && projectile.haveResource(this) == true) {
			
			//SET DEFAULT COORDINATES< DIRECTION ABD USER
			projectile.set(worldX, worldY, direction, true, this);
			//SUBTRACT THE COST(MANA, AMMO) 
			projectile.subtractResource(this);
			
			//CHECK VACANCY
			for(int i = 0; i < gp.projectile.length; i++) {
				if(gp.projectile[i] == null) {
					gp.projectile[i] = projectile;
					break;
				}
			}
			shotAvailableCounter = 0;
			gp.playSE(9);
		}
		
		//This need to be outside of key statement
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		if(shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
		if(Life > maxLife) {
			Life = maxLife;
		}
		if(mana > maxMana) {
			mana = maxMana;
		}
	   if(Life <= 0) {
	    	gp.gameState = gp.gameOverState;
	    	gp.stopMusic();
	    	gp.playSE(10);
	    }
	}

	public void pickUpObject(int i) {
		if(i != 999) {
			
			//PICK UP ONLY ITEMS
			if(gp.obj[i].type == type_pickupOnly) {
				
				gp.obj[i].use(this);
				gp.obj[i] = null;
				
			}
			//OBSTACLE
			else if (gp.obj[i].type == type_obstacle) {
				if(keyH.enterPressed == true) {
					attackCanceled = true;
					gp.obj[i].interact();
				}
			}
			//INVENTORY ITEMS 
			else {
				String text;
				
				if(inventory.size() != maxInventorySize) {
					
					inventory.add(gp.obj[i]);
					gp.playSE(1);
					text = "Got a "+ gp.obj[i].name + "!";
					
				}else {
					text = "You can't not carry any more !";
				}
				gp.u1.addMessage(text);
				gp.obj[i] = null;
			}
		}
	}
	public void interactNPC(int i) {
		if(gp.keyH.enterPressed == true) {
			
         if(i != 999) {
        	 attackCanceled = true;
        		 gp.gameState = gp.dialogueState;
    		     gp.npc[i].speak(); 
        
		   }
		}
	}
	public void contactMonster(int i) {
		
		if(i != 999) {
			if(invincible == false && gp.monster[i].dying == false) {
				gp.playSE(6);
				
				 int damage = gp.monster[i].attack - defense;
				 if(damage < 0) {
					 damage = 0;
				 }
				Life -= damage;
				invincible = true;
			}
			
		}
	}
	public void damageMonster(int i, Kentity attacker, int attack, int KnockBackPower) {
		if(i != 999) {
			 if(gp.monster[i].invincible == false) {
				 
				 gp.playSE(5);
				 if(KnockBackPower > 0) {
					setKnockBack(gp.monster[i], attacker,KnockBackPower ); 
				 }
				 if(gp.monster[i].offBalance == true) {
					 attack *= 5;
				 }
				
				 int damage = attack - gp.monster[i].defense;
				 if(damage < 0) {
					 damage = 0;
				 }
				 gp.monster[i].Life -= damage;
				 gp.u1.addMessage( damage + "damage!");
				 gp.monster[i].invincible = true;
				 gp.monster[i].damageReaction();
				 
				 if(gp.monster[i].Life <= 0 ) {
					 gp.monster[i].dying = true;
					 gp.u1.addMessage("killed the "+ gp.monster[i].name + "!");
					 gp.u1.addMessage("Exp + "+ gp.monster[i].exp + "!");
					 exp += gp.monster[i].exp;
					 checkLevelUp();
				 }
			 }
		}
		
	}
	public void KnockBack(Kentity kentity, int KnockBackPower ) {
		
		kentity.direction = direction;
		kentity.speed += KnockBackPower ;
		kentity.KnockBack = true;
	}
	public void damageProjectile(int i) {
		if(i != 999) {
			Kentity projectile = gp.projectile[i];
			projectile.alive = false;
			
		}
	}
	public void  checkLevelUp() {
		if(exp >= nextLevelExp) {
			level++;
			nextLevelExp = nextLevelExp*2;
			maxLife += 2;
			strength++;
			dexterity++;
			attack = getAttack();
			defense = getDefense();
			
			gp.playSE(7);
			gp.gameState = gp.dialogueState;
			gp.u1.currentDialogue = "You are level" + level + "now!\n" + "You feel stronger!";
		}
	}
	public void selectItem() {
		
		int itemIndex = gp.u1.getItemIndexOnSlot();
		
		if(itemIndex < inventory.size()) {
			
			Kentity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
				
				currentWeapon = selectedItem;
				attack = getAttack();
				 getPlayerAttack();
			}
			if(selectedItem.type == type_shield) {
				currentShield = selectedItem;
				defense = getDefense();
			}
			if(selectedItem.type == type_consumable) {
				if(selectedItem.use( this) == true){
					inventory.remove(itemIndex);
				}
			}
			
		}
	}
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
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
			if(guarding == true) {
				image = guardUp;
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
			if(guarding == true) {
				image = guardDown;
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
			if(guarding == true) {
				image = guardLeft;
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
			if(guarding == true) {
				image = guardRight;
			}
			break;
		}
		
		if(invincible == true) {
			g2.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}
		g2.drawImage(image, tempScreenX, tempScreenY, null);
		
		//Reset alpha
		g2.setComposite( AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		//Debug
		//g2.setFont(new Font("Arial", Font.PLAIN, 26));
		//g2.setColor(Color.white);
		//g2.drawString("Invincible"+ invincibleCounter, 10, 400 );
	}
	

}
