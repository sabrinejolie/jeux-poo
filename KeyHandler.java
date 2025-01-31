package Main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
	
	GamePanel gp;
	
	public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed, optionkeyPressed, spacePressed;
	//DEBUG
	boolean showDebugText = false;
	
	public KeyHandler (GamePanel gp) {
		this.gp = gp;
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		//TITLE STATE
		if(gp.gameState == gp.titleState) {
			titleState(code);
		}
		// PLAY STATE
		else if(gp.gameState == gp.playState) {
		playState(code); 
	    }
		//PAUSE STATE
		else if(gp.gameState == gp.pauseState) {
			pauseState(code);
		}
		// DIALOGUE STATE
		else if (gp.gameState == gp.dialogueState) {
		  	dialogueState(code);
        }
		//CHARACTER STATE
		else if (gp.gameState == gp.characterState) {
		       characterState(code);
	    }
		//OPTION STATE
		else if (gp.gameState == gp.optionState) {
		       optionState(code);
	    }
		//GAME OVER STATE
		else if (gp.gameState == gp.gameOverState) {
			 gameOverState(code);
			    }
	}
	
	public void titleState(int code) {
		if(gp.u1.titleScreenState == 0) {
			
			if(code == KeyEvent.VK_W){
				 gp.u1.commandNum--;
				 if(gp.u1.commandNum < 0) {
					 gp.u1.commandNum = 2;
				 }
			}
			if(code == KeyEvent.VK_S) {
				 gp.u1.commandNum++;
				 if(gp.u1.commandNum > 2) {
					 gp.u1.commandNum = 0;
				 }
			}
			if(code == KeyEvent.VK_ENTER) {
				if(gp.u1.commandNum == 0) {
					gp.gameState = gp.playState;
					gp.playMusic(0);
				}
				if(gp.u1.commandNum == 1) {
					// ADD LATER
				}
				if(gp.u1.commandNum == 2) {
					System.exit(0);
				}
			}
	}
	 else if(gp.u1.titleScreenState == 1) {
		
		if(code == KeyEvent.VK_W){
			 gp.u1.commandNum--;
			 if(gp.u1.commandNum < 0) {
				 gp.u1.commandNum = 3;
			 }
		}
		if(code == KeyEvent.VK_S) {
			 gp.u1.commandNum++;
			 if(gp.u1.commandNum > 3) {
				 gp.u1.commandNum = 0;
			 }
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.u1.commandNum == 0) {
			// System.out.println("do some fighter specific stuff !");
			 gp.gameState = gp.playState;
				gp.playMusic(0);
			}
			if(gp.u1.commandNum == 1) {
			 //System.out.println("do some theif specific stuff !");
			 gp.gameState = gp.playState;
				gp.playMusic(0);
			}
			if(gp.u1.commandNum == 2) {
			 //System.out.println("do some sorcerer specific stuff !");
			 gp.gameState = gp.playState;
				gp.playMusic(0);
			}
			if(gp.u1.commandNum == 3) {
			 gp.u1.titleScreenState = 0;
			}
		}
	}
}
	public void playState(int code) {
		
		if(code == KeyEvent.VK_W){
			upPressed = true;
		}
		if(code == KeyEvent.VK_S) {
			downPressed = true;
		}
		if(code == KeyEvent.VK_A){
			leftPressed = true;
		}
        if(code == KeyEvent.VK_D){
        	rightPressed = true;
		}
        if(code == KeyEvent.VK_P){
        	gp.gameState = gp.pauseState;
		}
        if(code == KeyEvent.VK_C){
        	gp.gameState = gp.characterState;
		}
        if(code == KeyEvent.VK_ENTER){
        	enterPressed = true;
		}
        if(code == KeyEvent.VK_F){
        	shotKeyPressed = true;
		}
        if(code == KeyEvent.VK_ESCAPE){
        	gp.gameState = gp.optionState;
		}
        if(code == KeyEvent.VK_SPACE){
        	 spacePressed = true;
		}
        //DEBUG 
        if(code == KeyEvent.VK_T){
        	if(showDebugText  == false) {
        		showDebugText  = true;
        	}
        	else if(showDebugText  == true) {
        		showDebugText  = false;
        	}
		}
        if(code == KeyEvent.VK_R){
               
        	gp.tileM.loadMap("/maps/worldV2.txt");
        	}
        } 
	
	public void pauseState(int code) {
		if(code == KeyEvent.VK_P) {	
	          gp.gameState = gp.playState;	
			}
			
	}
	public void dialogueState(int code) {
		if(code == KeyEvent.VK_ENTER) {
			gp.gameState = gp.playState;
		}
	}
	public void characterState(int code) {
		if(code == KeyEvent.VK_C) {
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_W) {
			if(gp.u1.slotRow != 0) {
			gp.u1.slotRow--;
			gp.playSE(8);
		}
	}		
        if(code == KeyEvent.VK_A) {
        	if(gp.u1.slotCol != 0) {
			gp.u1.slotCol--;
			gp.playSE(8);
		}
     }
        if(code == KeyEvent.VK_S) {
        	if(gp.u1.slotRow != 3) {
			gp.u1.slotRow++;
			gp.playSE(8);
		}
      }
        if(code == KeyEvent.VK_D) {
        	if(gp.u1.slotCol != 4) {
			gp.u1.slotCol++;
			gp.playSE(8);
		}
      }
        if(code == KeyEvent.VK_ENTER) {
        	gp.player.selectItem();
        }

	}
	public void optionState( int code) {
		if(code == KeyEvent.VK_ESCAPE) {
			gp.gameState = gp.playState;
		}
		if(code == KeyEvent.VK_Z ) {
			optionkeyPressed  = true;
		}

		int maxCommandNum = 0;
		switch(gp.u1.subState) {
		case 0:maxCommandNum = 4; break;
		case 2:maxCommandNum = 1; break;
		}
		if(code == KeyEvent.VK_W) {
	         gp.u1.commandNum--;
	         gp.playSE(8);
	         if(gp.u1.commandNum < 0) {
	        	 gp.u1.commandNum = maxCommandNum;
	         }
		}
		if(code == KeyEvent.VK_S) {
	         gp.u1.commandNum++;
	         gp.playSE(8);
	         if(gp.u1.commandNum > maxCommandNum) {
	        	 gp.u1.commandNum = 0; 
	         }
		}
		//SONG PARAMETER
		if(code == KeyEvent.VK_A) {
			if(gp.u1.subState == 0) {
				if(gp.u1.commandNum == 0 && gp.music.volumeScale > 0) {
					gp.music.volumeScale--;
					gp.music.checkVolume();
					gp.playSE(8);
				}
					if(gp.u1.commandNum == 1 && gp.se.volumeScale > 0) {
						gp.se.volumeScale--;
						gp.playSE(8);
				}
			}
		}
        if(code == KeyEvent.VK_D) {
        	if(gp.u1.subState == 0) {
				if(gp.u1.commandNum == 0 && gp.music.volumeScale < 5) {
					gp.music.volumeScale++;
					gp.music.checkVolume();
					gp.playSE(8);
				}
					if(gp.u1.commandNum == 1 && gp.se.volumeScale < 5) {
						gp.se.volumeScale++;
						gp.playSE(8);
					}
				}
				
			}
		}
	public void gameOverState(int code)	{
		if(code == KeyEvent.VK_W) {
			gp.u1.commandNum--;
			if(gp.u1.commandNum < 0) {
				gp.u1.commandNum = 1;
			}
			gp.playSE(8);
		}
		if(code == KeyEvent.VK_S) {
			gp.u1.commandNum++;
			if(gp.u1.commandNum > 1) {
				gp.u1.commandNum = 0;
			}
			gp.playSE(8);
		}
		if(code == KeyEvent.VK_ENTER) {
			if(gp.u1.commandNum == 0) {
				gp.gameState = gp.playState;
				gp.retry();
				gp.playMusic(0);
			}
			else if(gp.u1.commandNum == 1) {
				gp.gameState = gp.titleState;
				gp.restart();
			}
		}
	}

	@Override
 public void keyReleased(KeyEvent e) {
		
	int code = e.getKeyCode();
	
	if(code == KeyEvent.VK_W){
		upPressed =  false;
	}
	if(code == KeyEvent.VK_S) {
		downPressed =  false;
	}
	if(code == KeyEvent.VK_A){
		leftPressed =  false;
	}
    if(code == KeyEvent.VK_D){
    	rightPressed =  false;
	}
    if(code == KeyEvent.VK_F){
    	shotKeyPressed = false;
	}
    if(code == KeyEvent.VK_ENTER){
    	enterPressed = false;
	}
    if(code == KeyEvent.VK_SPACE){
    	spacePressed = false;
	}
	
	}
}
  


