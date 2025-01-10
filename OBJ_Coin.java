package object;

import Entity.Kentity;
import Main.GamePanel;

public class OBJ_Coin extends Kentity{
	
	GamePanel gp;

	public OBJ_Coin(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_pickupOnly;
		name = "Bronze Coin";
		value = 1;
		down1 =setup("/objects/coin_bronze",gp.tileSize,gp.tileSize);
		 
	}
	public boolean use (Kentity kentity) {
		
		gp.playSE(1);
		gp.u1.addMessage( "Coin" + value); 
		gp.player.coin += value;
	    return true;
	}

}
