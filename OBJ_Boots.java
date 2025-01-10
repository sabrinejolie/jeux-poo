package object;
 
import Entity.Kentity;
import Main.GamePanel;

public class OBJ_Boots extends Kentity {
	
	public OBJ_Boots(GamePanel gp){
		super(gp);
		
		name = "Boots";
		down1 = setup("/objects/boots", gp.tileSize, gp.tileSize);
	}
		
	
}
