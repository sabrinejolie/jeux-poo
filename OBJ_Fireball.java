package object;

import Entity.Kentity;
import Entity.Projectile;
import Main.GamePanel;

public class OBJ_Fireball extends Projectile {
	
	GamePanel gp;

	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_axe;
		name = "Fireball";
		speed = 7;
		maxLife = 80;
		Life = maxLife;
		attack = 3;
		KnockBackPower = 0; 
		useCost = 1;
		alive = false;
		 getImage();
	    description = "[Woodcutter's axe]\nA bit rusty but still \ncan cut sometrees.";
		 
	}
	public void getImage() {
		up1 = setup("/projectile/fireball_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/fireball_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/fireball_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/fireball_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/fireball_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/fireball_right_2", gp.tileSize, gp.tileSize);
	}
	public boolean haveResource(Kentity user) {
		
		boolean haveResource = false;
		if(user.mana >= useCost) {
			haveResource = true;
		}
		return haveResource;
	}
	public void subtractResource(Kentity user) {
		user.mana -= useCost;
	}

}
