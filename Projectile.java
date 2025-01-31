package Entity;

import Main.GamePanel;

public class Projectile extends Kentity{
	
	Kentity user;

	public Projectile(GamePanel gp) {
		super(gp);
	
	}
	public void set(int worldX, int worldY, String direction, boolean alive, Kentity user) {
		
	    this.worldX = worldX;
	    this.worldY = worldY;
	    this.direction = direction;
	    this.alive = alive;
		this.user = user;
		this.Life = this.maxLife;
	}
	public void update() {
		
		if(user == gp.player) {
			
			int monsterIndex = gp.cChecker.checkKentity(this, gp.monster);
			if(monsterIndex != 999) {
				gp.player.damageMonster(monsterIndex, this, attack, KnockBackPower);
				alive = false ;
			}
		}
		if(user != gp.player) {
			boolean contactPlayer = gp.cChecker.checkPlayer(this);
			if(gp.player.invincible == false && contactPlayer == true) {
				damagePlayer(attack);
				alive = false;
			}
		}
		switch(direction) {
		case"up": worldY -= speed;break;
		case"down": worldY += speed;break;
		case"left": worldX -= speed;break;
		case"right": worldX += speed;break;
		}
		
		Life--;
		if(Life <= 0) {
			alive = false;
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
	}
	public boolean haveResource(Kentity user) {	
		boolean haveResource = false;
		return haveResource;
	}
	public void subtractResource(Kentity user) {}

}
