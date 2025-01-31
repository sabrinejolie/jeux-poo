package Main;



public class Event {
	
	GamePanel gp;
	 EventRect eventRect [][][];
	 
	 int previousEventX, previousEventY;
	 boolean canTouchEvent = true;
	 
	public Event(GamePanel gp) {
		this.gp = gp;
		
		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		while (map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
		
		eventRect[map][col][row] = new EventRect();
		eventRect[map][col][row].x = 23;
		eventRect[map][col][row].y = 23;
		eventRect[map][col][row].width = 2;
		eventRect[map][col][row].height = 2;
		eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
		eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
		
		col++;
		if(col == gp.maxWorldCol) {
			col = 0;
			row++;
			if(row == gp.maxWorldRow) {
				row = 0;
				map++;
			}
		}
		}
	}
	 public void checkEvent() {
		 
		 //Check if the playercharacteris more than 1 tile awayfrom the last event 
		 int xDistance = Math.abs( gp.player.worldX - previousEventX);
		 int yDistance = Math.abs( gp.player.worldY - previousEventY);
		 int distance = Math.max( xDistance, yDistance);
		 if(distance > gp.tileSize) {
			 canTouchEvent = true;
		 }
		 
		 if(canTouchEvent == true) {
			 
			 if(hit(0,23,19,"any")== true) { damagePit( gp.dialogueState); }
			// if(hit(0,27,16,"right")== true) {teleport(0, gp.dialogueState);}
			 else if(hit(0,23,12,"up") == true) { healingPool( gp.dialogueState); }
			 else if(hit(0,9,13,"any") == true){teleport(1,9,13);}
			 else if(hit(1,9,13,"any") == true){teleport(0,9,13);}
				 }
			 
		 }	 
	 public boolean hit(int map, int col, int row, String reqDirection) {
		 
		 boolean hit = false;
		 
		 if(map == gp.currentMap) {

			 gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
			 gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
			 eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
			 eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;
			 
			 if(gp.player.solidArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
				 if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals( "any")){
					 hit = true;
					 
					 previousEventX = gp.player.worldX; 
					 previousEventY = gp.player.worldY; 
					 canTouchEvent = false;
				 }
			 }
			 gp.player.solidArea.x = gp.player.solidAreaDefaultX;
			 gp.player.solidArea.y = gp.player.solidAreaDefaultY;
			 eventRect[map][col][row].x =  eventRect[map][col][row].eventRectDefaultX;
			 eventRect[map][col][row].y =  eventRect[map][col][row].eventRectDefaultY; 
		 }
		 return hit;
	 }
	 public void damagePit(int gameState) {
		 
		 gp.gameState = gameState;
		 gp.playSE(6);
		 gp.u1.currentDialogue = "You fall into a pit!";
		 gp.player.Life -= 1;
		 canTouchEvent = false;
	 }
	 public void healingPool(int gameState) {
		 
		 if(gp.keyH.enterPressed == true) {
			 gp.gameState = gameState;
			 gp.player.attackCanceled = true;
			 gp.playSE(2);
			 gp.u1.currentDialogue = "You drink the water.\nYour life and mana have been recovered.";
			 gp.player.Life = gp.player.maxLife;
			 gp.player.mana = gp.player.maxMana;
			 gp.aSetter.setMonster();
		 } 
		 
	 }
	 public void teleport(int map, int col, int row) {
		 
		 gp.currentMap = map;
		 gp.player.worldX = gp.tileSize * col;
		 gp.player.worldY = gp.tileSize * row;
		 previousEventX = gp.player.worldX;
		 previousEventY = gp.player.worldY;
		 canTouchEvent = false;
		 gp.playSE(11);
		 gp.u1.currentDialogue = "Teleport !";
		 
		  
	 }
}
