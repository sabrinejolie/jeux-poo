package Main;

import java.awt.BasicStroke;
import Entity.Kentity;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import object.OBJ_Heart;
import object.OBJ_ManaCrystal;


public class U1 {
	
	GamePanel gp;
	Graphics2D g2;
	Font JOKERMAN ;
	BufferedImage heart_full, heart_half, heart_blank, crystal_full, crystal_blank;

	public boolean messageOn = false;
    ArrayList<String> message = new ArrayList <>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
	public boolean gameFinished = false;
	public String currentDialogue = "";
	public int commandNum = 0;
	public int titleScreenState = 0; //0: the first screen, 1: the second screen
	public int slotCol = 0;
	public int slotRow = 0;
	int subState = 0;

	public U1(GamePanel gp) {
		this.gp = gp;
		
		try {
			 InputStream is = getClass().getResourceAsStream("/font/JOKERMAN.TTF");
			JOKERMAN = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// CREATE HUD OBJECT
		Kentity heart = new OBJ_Heart(gp);
		heart_full = heart.image;
		heart_half = heart.image1;
		heart_blank = heart.image2;
		Kentity crystal = new OBJ_ManaCrystal(gp);
		crystal_full = crystal.image;
		crystal_blank = crystal.image1;
	}
	
	public void addMessage(String text ) {
	 message.add(text);
	 messageCounter.add(0);
	}
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(JOKERMAN);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setColor(Color.white);
		
		//TITLE STATE
		if(gp.gameState == gp.titleState) {
			drawTitleScreen();
		}
		// PLAY STATE
		if(gp.gameState == gp.playState) {
		      drawPlayerLife();
		      drawMessage();
		}
		// PAUSE STATE
		if(gp.gameState == gp.pauseState) {
			  drawPlayerLife();
			drawPauseScreen();
		}
		// DIALOGUE STATE
		if(gp.gameState == gp.dialogueState) {
			  drawPlayerLife();
			drawDialogueScreen();
		}
		//CHARACTER STATE
		if(gp.gameState == gp.characterState) {
			drawCharacterScreen();
			drawInventery();
		}
		//OPTION STATE
		if(gp.gameState == gp.optionState) {
			drawOptionScreen();
		}
		//GAME OVER STATE
	    if(gp.gameState == gp.gameOverState) {
			drawGameOverScreen();
				}
	    //END GAME
	  if(gameFinished == true) {
	   		gp.stopMusic();
	   	    gp.playSE(2);
	   	    drawEndGame();
	   	    }
	    	}

	public void drawPlayerLife() {
		
		
		int x = gp.tileSize/2;
		int y = gp.tileSize/2;
		int i = 0;
		
		//DRAW MAX HEART
		while(i < gp.player.maxLife/2) {
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x += gp.tileSize;
		}
		//RESET
		x = gp.tileSize/2;
	    y = gp.tileSize/2;
		i = 0;
		
		//DRAW CURRENT LIFE
		while(i < gp.player.Life) {
			g2.drawImage(heart_half, x, y, null);
			i++;
			if(i < gp.player.Life) {
				g2.drawImage(heart_full, x, y, null);
			}
			i++;
			x += gp.tileSize;
		}
		//DRAW MAX MANA
		 x = (gp.tileSize/2)-5;
		 y =(int) (gp.tileSize*1.5);
		 i = 0;
		
		while(i < gp.player.maxMana) {
			g2.drawImage(crystal_blank, x, y, null);
			i++;
			x += 35;
		}
		//DRAW MANA
		 x = (gp.tileSize/2)-5;
		 y =(int) (gp.tileSize*1.5);
		 i = 0;
		
		while(i < gp.player.mana) {
			g2.drawImage(crystal_full, x, y, null);
			i++;
			x += 35;
		}
	}
	public void drawMessage() {
		
		int messageX = gp.tileSize;
		int messageY = gp.tileSize*4;
		g2.setFont( g2.getFont().deriveFont( Font.BOLD, 30F));
		
		for(int i = 0 ; i < message.size(); i++) {
			
			if(message.get(i) != null) {
				
				g2.setColor(Color.black);
				g2.drawString(message.get(i), messageX+2, messageY+2);
				g2.setColor(Color.white);
				g2.drawString(message.get(i), messageX, messageY);
				
				int counter = messageCounter.get(i) + 1;//messageCounter++
				messageCounter.set(i, counter);//set counter to the array
				messageY += 50;
				
				if(messageCounter.get(i) > 180) {
					message.remove(i);
					messageCounter.remove(i);
				}
					
				
			}
		}
	}
	public void drawTitleScreen() {
		
		if(titleScreenState == 0) {
			
			g2.setColor(new Color(20,50,90));
			g2.fillRect(0, 0,  gp.screenWidth,  gp.screenHeight);
			
			// THE TITLE NAME
			g2.setFont(g2.getFont().deriveFont( Font.BOLD,60F));
			String text ="SHADOW ADVENTURE";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			
			//SHADOW
			g2.setColor(Color.black);
			g2.drawString( text, x+9, y+9);
			
			//MAIN COLOR
			g2.setColor(Color.white);
			g2.drawString(text, x, y);
			
			//CHARACTER
			x = gp.screenWidth/2 - (gp.tileSize*2)/2;
			y += gp.tileSize*2;
			g2.drawImage( gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);
			
			//MENU
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			
			text = "NEW GAME";
			x = getXforCenteredText(text);
			y += gp.tileSize*4;
			g2.drawString(text, x, y);
			if(commandNum == 0) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
			
			text = "QUIT";
			x = getXforCenteredText(text);
			y += gp.tileSize;
			g2.drawString(text, x, y);
			if(commandNum == 2) {
				g2.drawString(">", x-gp.tileSize, y);
			}
			
		} else if (titleScreenState == 1) {
			
			//CLASS SELECTION SCREEN
			g2.setColor(Color.white);
			g2.setFont(g2.getFont().deriveFont(42F));
			
			String text = "Select your class !";
			int x = getXforCenteredText(text);
			int y = gp.tileSize*3;
			g2.drawString(text, x, y);
			
			text = "Fighter";
			 x = getXforCenteredText(text);
			 y += gp.tileSize*3;
			 g2.drawString(text, x, y);
			 if(commandNum == 0) {
				 g2.drawString(">", x-gp.tileSize, y);
			 }
			 text = " Thief";
			 x = getXforCenteredText(text);
			 y += gp.tileSize;
			 g2.drawString(text, x, y);
			 if(commandNum == 1) {
				 g2.drawString(">", x-gp.tileSize, y);
			 }
			 text = "Sorcerer";
			 x = getXforCenteredText(text);
			 y += gp.tileSize;
			 g2.drawString(text, x, y);
			 if(commandNum == 2) {
				 g2.drawString(">", x-gp.tileSize, y);
			 }
			 text = "Back";
			 x = getXforCenteredText(text);
			 y += gp.tileSize*2;
			 g2.drawString(text, x, y);
			 if(commandNum == 3) {
				 g2.drawString(">", x-gp.tileSize, y);
			 }
		}
		
	}
	public void drawPauseScreen() {
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
		String text = "PAUSED";
		int x = getXforCenteredText(text);
		int y = gp.screenHeight/2;
		
		g2.drawString(text, x, y);
		
	}
	public void drawDialogueScreen() {
		
		//WINDOW
		int x = gp.tileSize*2;
		int y = gp.tileSize/2;
		int width = gp.screenWidth - (gp.tileSize*4);
		int height = gp.tileSize*4;
		drawSubWindow(x, y, width, height);
		
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,20F));
		x += gp.tileSize;
		y += gp.tileSize;
		
		for(String line : currentDialogue.split("\n")) {
			g2.drawString(line, x, y);
			y += 40;
		}
		
	}
    public void drawCharacterScreen() {
		
    	//CREAT A FRAME
    	final int frameX = gp.tileSize;
    	final int frameY = gp.tileSize;
    	final int frameWidth = gp.tileSize*5;
    	final int frameHeight = gp.tileSize*10;
    	drawSubWindow(frameX, frameY, frameWidth, frameHeight);
    	
    	//TEXT
    	g2.setColor(Color.white);
    	g2.setFont(g2.getFont().deriveFont(28F));
    	
    	int textX = frameX + 20;
    	int textY = frameY + gp.tileSize;
    	final int lineHeight = 36;
    	
    	//NAMES
    	g2.drawString("Level", textX, textY);
    	textY += lineHeight;
    	g2.drawString( "Life", textX, textY);
    	textY += lineHeight;
    	g2.drawString( "Mana", textX, textY);
    	textY += lineHeight;
    	g2.drawString( "Strength", textX, textY);
    	textY += lineHeight;
    	g2.drawString( "Dexterity", textX, textY);
    	textY += lineHeight;
    	g2.drawString( "Attack", textX, textY);
    	textY += lineHeight;
    	g2.drawString( "Defense", textX, textY);
    	textY += lineHeight;
    	g2.drawString( "Exp", textX, textY);
    	textY += lineHeight;
    	g2.drawString( "Next Level", textX, textY);
    	textY += lineHeight;
    	g2.drawString( "Coin", textX, textY);
    	textY += lineHeight + 10;
    	g2.drawString( "Weapon", textX, textY);
    	textY += lineHeight + 5;
    	g2.drawString( "Shield", textX, textY);
    	textY += lineHeight;
    	
    	//VALUES
    	int tailX = (frameX + frameWidth) - 30;
    	//Reset textY
    	textY = frameY + gp.tileSize;
    	String value;
    	
    	value = String.valueOf(gp.player.level);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.Life + "/" + gp.player.maxLife);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.mana + "/" + gp.player.maxMana);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.strength);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.dexterity);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.attack);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.defense);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.exp);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.nextLevelExp);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	value = String.valueOf(gp.player.coin);
    	textX = getXforAlignToRightText(value, tailX);
    	g2.drawString(value, textX, textY);
    	textY += lineHeight;
    	
    	 g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 28, null);
    	 textY += gp.tileSize;
    	 g2.drawImage( gp.player.currentShield.down1, tailX - gp.tileSize, textY - 28, null);
    	
    	
	}
    public void drawInventery() {
    	//FRAME
        int frameX = gp.tileSize*9;
    	int frameY = gp.tileSize;
    	int frameWidth = gp.tileSize*6;
    	int frameHeight = gp.tileSize*5;
    	drawSubWindow(frameX, frameY, frameWidth, frameHeight);
    	
    	//SLOT
    	final int slotXStart = frameX + 20;
    	final int slotYStart = frameY + 20;
    	int slotX = slotXStart;
    	int slotY = slotYStart;
    	int slotSize = gp.tileSize+3;
    	
    	//PLAYER'S ITEMS
    	for(int i = 0; i < gp.player.inventory.size(); i++) {
    		
    		//EQUIP CURSOR
    		if(gp.player.inventory.get(i) == gp.player.currentWeapon || gp.player.inventory.get(i) == gp.player.currentShield) {
    			g2.setColor(new Color(240,190,90));
    			g2.fillRoundRect(slotX, slotY,  gp.tileSize, gp.tileSize, 10, 10);
    		}
    		g2.drawImage( gp.player.inventory.get(i).down1, slotX, slotY, null);
    		
    		slotX += slotSize;
    		if(i == 4 || i == 9 || i == 14) {
    			slotX =  slotXStart ;
    			slotY += slotSize;
    		}
    	}
    	
    	//CURSOR
    	int cursorsX = slotXStart + (slotSize * slotCol);
    	int cursorsY = slotYStart + (slotSize * slotRow);
    	int cursorsWidth = gp.tileSize;
    	int cursorsHeight = gp.tileSize;
    	
    	//DRAW CURSOR
    	g2.setColor( Color.white);
    	g2.setStroke(new BasicStroke(3));
    	g2.drawRoundRect(cursorsX, cursorsY, cursorsWidth, cursorsHeight, 10, 10);
    	
    	//DESCREPTION FRAME
    	int dFrameX = frameX;
    	int dFrameY = frameY + frameHeight;
    	int dFrameWidth = frameWidth;
    	int dFrameHeight = gp.tileSize*3;
    	drawSubWindow( dFrameX, dFrameY, dFrameWidth, dFrameHeight);
    	
    	//DRAW DESCREPTION TEXT
    	int textX = dFrameX + 20;
    	int textY = dFrameY + gp.tileSize;
    	g2.setFont(g2.getFont().deriveFont(24F));
    	
    	int itemIndex = getItemIndexOnSlot();
    	
    	if(itemIndex < gp.player.inventory.size()) {
    		
    		for(String line: gp.player.inventory.get(itemIndex).description.split( "\n")) {
    			g2.drawString(line, textX, textY);
    			textY += 32;
    		}
    		gp.keyH.optionkeyPressed = false;
    	}
    }
    public void drawOptionScreen() {
    	g2.setColor( Color.white);
    	g2.setFont( g2.getFont().deriveFont(32F));
    	
    	//SUB WINDOW
    	int frameX = gp.tileSize*5;
    	int frameY = gp.tileSize;
    	int frameWidth = gp.tileSize*9;
    	int frameHeight = gp.tileSize*10;
    	drawSubWindow( frameX , frameY , frameWidth, frameHeight);
    	
    	switch(subState) {
    	case 0: options_top(frameX , frameY ); break;
    	case 1: options_control( frameX, frameY);break;
    	case 2: option_endGame(frameX, frameY); break;
    	}
    }
    public void options_top(int frameX , int frameY) {
    	
    	int textX;
    	int textY;
    	
    	//TITLE
    	String text = "Options";
    	textX = getXforCenteredText(text);
    	textY = frameY + gp.tileSize ;
    	g2.drawString(text, textX, textY);
    	 
    	//MUSIC
    	textX = frameX + gp.tileSize;
    	textY += gp.tileSize*2;
    	g2.drawString("MUSIC", textX, textY);
    	if(commandNum == 0) {
    		g2.drawString(">", textX-25, textY);
    	}
    	//SE
    	textY += gp.tileSize;
    	g2.drawString("SE", textX, textY);
    	if(commandNum == 1) {
    		g2.drawString(">", textX-25, textY);
    	}
    	//CONTROL 
    	textY += gp.tileSize;
    	g2.drawString("CONTROL", textX, textY);
    	if(commandNum == 2) {
    		g2.drawString(">", textX-25, textY);
    		if(gp.keyH.optionkeyPressed == true) {
    			gp.u1.commandNum = -1;
    			subState = 1;
    			commandNum = 0;
    		}
    		
    	}
    	//END GAME
    	textY += gp.tileSize;
    	g2.drawString("END GAME", textX, textY);
    	if(commandNum == 3) {
    		g2.drawString(">", textX-25, textY);
    		if(gp.keyH.optionkeyPressed == true) {
    			gp.u1.commandNum = -1;
    			subState = 0;
    			commandNum = 0;
    		}
    		
    	}
    	//BACK
    	textY += gp.tileSize*2;
    	g2.drawString("BACK", textX, textY);
    	if(commandNum == 4) {
    		g2.drawString(">", textX-25, textY);
    		if(gp.keyH.optionkeyPressed == true) {
    			gp.u1.commandNum = -1;
    			gp.gameState = gp.playState;
    			commandNum =0;
    		}
    	}
    	//MUSIC VOLUME
    	textX = frameX + gp.tileSize*4;
    	textY = frameY + gp.tileSize*2 + 24;
    	g2.setStroke( new BasicStroke(3));
        g2.drawRect(textX, textY, 150, 24);
        int volumeWidth = 30 * gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth,  24);
        
      //SOUND EFFECTS (SE) VOLUME
    	textY += gp.tileSize;
        g2.drawRect(textX, textY, 150, 24);
        volumeWidth = 30 * gp.se.volumeScale;
        g2.fillRect(textX, textY, volumeWidth,  24);
        
        gp.config.saveConfig();
    }
    public void options_control(int frameX, int frameY) {
    	 
          int textX;
          int textY;
          
          //TITLE
          String text = "CONTROL";
          textX = getXforCenteredText(text);
          textY = frameY + gp.tileSize;
          g2.drawString(text, textX, textY);
          
          textX = frameX + gp.tileSize;
          textY += gp.tileSize;
          g2.drawString("Move", textX, textY); textY+=gp.tileSize;
          g2.drawString("Confirm/Attack", textX, textY); textY+=gp.tileSize;
          g2.drawString("Shoot/Cast", textX, textY); textY+=gp.tileSize;
          g2.drawString("Character screen", textX, textY); textY+=gp.tileSize;
          g2.drawString("Pause", textX, textY); textY+=gp.tileSize;
          g2.drawString("Options", textX, textY); textY+=gp.tileSize;
          
          textX = frameX + (int)(gp.tileSize*6.5);
          textY = frameY + 100;
          g2.drawString("WASD", textX, textY); textY+=gp.tileSize;
          g2.drawString("ENTER", textX, textY); textY+=gp.tileSize;
          g2.drawString("    F", textX, textY); textY+=gp.tileSize;
          g2.drawString("    C", textX, textY); textY+=gp.tileSize;
          g2.drawString("    P", textX, textY); textY+=gp.tileSize;
          g2.drawString("   ESC", textX, textY); textY+=gp.tileSize;
          
   	   //BACK
          textX = frameX + gp.tileSize;
          textY = frameX + gp.tileSize*5;
          g2.drawString("BACK", textX, textY);
          if(commandNum == 0) {
        	  g2.drawString(">", textX-25, textY);
        	  if(gp.keyH.optionkeyPressed == true) {
        		  gp.u1.commandNum = -1;
        		  subState = 0;
        		  commandNum =0;
        	  }
          }
    } 
    public void option_endGame(int frameX, int frameY) {
    	
    	int textX = frameX + gp.tileSize;
    	int textY = frameY + gp.tileSize*2;
    	
    	currentDialogue = "Quit the game and \nreturn to \nthe screen?";
    	
    	for(String line: currentDialogue.split("\n")) {
    	g2.drawString(line, textX, textY);
    	textY += 40;
    	}
  	  //yes
  	  String text = "Yes";
  	  textX =  getXforCenteredText(text);
  	  textY += gp.tileSize*3;
  	  g2.drawString(text, textX, textY);
  	  if(commandNum == 0) {
  	  g2.drawString(">", textX-25, textY);
  	     if(gp.keyH.optionkeyPressed == true) {
  	    	gp.u1.commandNum = -1;
  		    subState = 0;
  		    gp.gameState = gp.titleState;
  	     }
  	  }
  	  //no
  	  text = "No";
  	  textX =  getXforCenteredText(text);
  	  textY += gp.tileSize;
  	  g2.drawString(text, textX, textY);
  	  if(commandNum == 1) {
  	  g2.drawString(">", textX-25, textY);
  	     if(gp.keyH.optionkeyPressed == true) {
  	    	gp.u1.commandNum = -1;
  		    subState = 0;
  		    commandNum = 3;
  	     }
  	  }
    }
  public void drawGameOverScreen() {
    	
    	g2.setColor(new Color(0,0,0,150));
    	g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
    	
    	int x;
    	int y;
    	String text;
    	g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
    	
    	text = "Game Over";
    	g2.setColor( Color.black);
    	x = getXforCenteredText(text);
    	y = gp.tileSize*4;
    	g2.drawString(text, x, y);
    	//MAIN
    	g2.setColor( Color.white);
    	g2.drawString(text, x-4, y-4);
    	
    	// retry
    	g2.setFont(g2.getFont().deriveFont(50F));
    	text = "Retry";
    	x =  getXforCenteredText(text);	
    	y += gp.tileSize*4;
    	g2.drawString(text, x, y);
    	if(commandNum == 0) {
    		g2.drawString(">", x-40, y);
    	}
    	
    	//BACK TO THE TITLE SCREEN
    	text ="Quit";
    	x =  getXforCenteredText(text);	
    	y += 55;
    	g2.drawString(text, x, y);
    	if(commandNum == 1) {
    		g2.drawString(">", x-40, y);
    	}
  }
  public void drawEndGame() {
	  
	  g2.setColor(new Color(0,0,0,150));
  	g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
  	
  	int x;
  	int y;
  	String text;
  	g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110F));
  	
  	text = "You won";
  	g2.setColor( Color.black);
  	x = getXforCenteredText(text);
  	y = gp.tileSize*4;
  	g2.drawString(text, x-300, y-300);
  //MAIN
	g2.setColor( Color.white);
	g2.drawString(text, x-30, y-30);
  }
    public int getItemIndexOnSlot() {
    	int itemIndex = slotCol + (slotRow*5);
    	return itemIndex;
    }
	public void drawSubWindow(int x, int y, int width, int height) {
		
		Color c = new Color(0,0,0,200);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		
		c = new Color(255,255,255);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10,  25,  25);
		
	}
	public int getXforCenteredText(String text) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text,  g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		return x;
		
	}
	public int getXforAlignToRightText(String text, int tailX) {
		
		int length = (int)g2.getFontMetrics().getStringBounds(text,  g2).getWidth();
		int x = tailX - length;
		return x;
		
	}

}
