package monster;

import java.util.Random;

import Entity.Kentity;
import Main.GamePanel;
import object.OBJ_Coin;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

public class ORG extends Kentity {
	
	GamePanel gp;

	public ORG(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_monster;
		name ="Orc";
		speed = 2;
		maxLife = 12;
		Life = maxLife;
		attack = 8;
		defense = 2;
		exp = 10;
		KnockBackPower = 5;
		
		solidArea.x = 4;
		solidArea.y = 4;
		solidArea.width = 40;
		solidArea.height = 44;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		attackArea.width = 48;
		attackArea.height = 48;
		motion1_duration = 40;
		motion1_duration = 85;
		
		getImage();
		getAttackImage();
	}
	public void getImage() {
		up1 = setup("/monster/orc_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/orc_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/orc_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/orc_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/orc_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/orc_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/orc_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/orc_right_2", gp.tileSize, gp.tileSize);	
		
	}
	public void getAttackImage() {
		attackUp1 = setup("/monster/orc_attack_up_1", gp.tileSize, gp.tileSize*2);
		attackUp2 = setup("/monster/orc_attack_up_2", gp.tileSize, gp.tileSize*2);
		attackDown1 = setup("/monster/orc_attack_down_1", gp.tileSize, gp.tileSize*2);
		attackDown2 = setup("/monster/orc_attack_down_2", gp.tileSize, gp.tileSize*2);
		attackLeft1 = setup("/monster/orc_attack_left_1", gp.tileSize*2, gp.tileSize);
		attackLeft2 = setup("/monster/orc_attack_left_2", gp.tileSize*2, gp.tileSize);
		attackRight1 = setup("/monster/orc_attack_right_1", gp.tileSize*2, gp.tileSize);
		attackRight2 = setup("/monster/orc_attack_right_2", gp.tileSize*2, gp.tileSize);
	}
	public void setAction() {
	
		actionLookCounter ++;
		
		if(actionLookCounter == 120) {
			
			Random random = new Random();
			int i = random.nextInt(100)+1; //pick up a number from 1 to 100 
			
			if(i <= 25) {
				direction = "up";
			}
			if(i > 25 && i <= 50) {
				direction = "down";
			}
			if(i > 50 && i <= 75) {
				direction = "left";
			}
			if(i > 75 && i <= 100) {
				direction = "right";
			}
			actionLookCounter = 0;
		}
		//Check if it attacks
		if(attacking == false) {
			checkAttackOrNot(20, gp.tileSize*4, gp.tileSize);
		}
	}
	public void damageReaction() {
		actionLookCounter = 0;
		direction = gp.player.direction;
	}
	public void checkDrop() {
		//CASt A DIE
		int i = new Random().nextInt(100)+1;
		
		//SET THE MONSTER DROP
		if(i < 50) {
			dropItem(new OBJ_Coin(gp));
		}
		if(i >= 50 && i < 75) {
			dropItem(new OBJ_Heart(gp));
		}
		if(i >= 75 && i < 100) {
			dropItem(new OBJ_ManaCrystal(gp));
		}
	}
	}


