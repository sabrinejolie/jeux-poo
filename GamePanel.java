package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import Entity.Kentity;
import Entity.Player;
import Tuile.TileManager;


		
    public class GamePanel extends JPanel implements Runnable {
    	
		 
			//screen settings
			final int originalTileSize = 16; // 16x16 tile
			final int scale = 3;
			
			public final int tileSize = originalTileSize * scale; // 48x48 tile
			public final int maxScreenCol = 16;
			public final int maxScreenRow = 12;
			public final int screenWidth = tileSize * maxScreenCol; // 768 pixels
			public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
			
			//WORLD SETINGS
			public final int maxWorldCol = 50 ;
			public final int maxWorldRow = 50;
			public final int maxMap = 10;
			public  int currentMap = 0; 
			
			//FPS
			int FPS = 60;
			
			// SYSTEM
			TileManager tileM = new TileManager(this);
			public KeyHandler keyH = new KeyHandler(this);
			Sound music = new Sound();
			Sound se = new Sound();
			public CollisionChecker cChecker = new CollisionChecker(this);
			public AssetSetter aSetter = new AssetSetter(this);
			public U1 u1 = new U1(this);
			public Event event = new Event(this);
			Config config = new Config(this);
			Thread gameThread;
			
			//entity and object
			public Player player = new Player(this,keyH);
			public Kentity obj[] = new Kentity[20];
			public Kentity npc[] = new Kentity[10];
			public Kentity monster[] = new Kentity[20];
			public Kentity projectile[] = new Kentity[20];
			ArrayList<Kentity> kentityList = new ArrayList<>();
			
			//GAME STATE
			public int gameState;
			public final int titleState = 0;
			public final int playState = 1;
			public final int pauseState = 2;
			public final int dialogueState = 3;
			public final int characterState = 4;
			public final int optionState = 5;
			public final int gameOverState = 6;
			 
			public GamePanel() {
				
				this.setPreferredSize(new Dimension(screenWidth, screenHeight));
				this.setBackground(Color.black);
				this.setDoubleBuffered(true);
				this.addKeyListener(keyH);
				this.setFocusable(true);
			}
			public void setupGame() {
				aSetter.setObject();
				aSetter.setNPC();
				aSetter.setMonster();
				//playMusic(0);
				gameState = titleState;
			}
			public void retry() {
		       player.setDefaultPosition();
		       player.restoreLifeAndMana();
		       aSetter.setNPC();
			   aSetter.setMonster();
			}
			public void restart() {
				player.setDefaultValues();
				player.setDefaultPosition();
			    player.restoreLifeAndMana();
			    aSetter.setObject();
			    player.setItems();
			    aSetter.setNPC();
				aSetter.setMonster();
			}
			public void startGameThread() {
				gameThread = new Thread(this);
				gameThread.start();
			}
			public void run () {
				
				double drawInterval = 1000000000/FPS;
				double delta = 0;
				long lastTime = System.nanoTime();
				long currentTime;
				long timer = 0;
				int drawCount = 0;
				
				while(gameThread != null) {
					
					currentTime = System.nanoTime();
					
					
					delta += (currentTime - lastTime) / drawInterval;
					timer += (currentTime - lastTime);
					lastTime = currentTime;
					
					if(delta >= 1) {
			         	update();
			        	repaint();
			        	delta--;
			        	drawCount++;
					}
					if(timer >= 1000000000) {
					
						drawCount = 0;
						timer = 0;
					}
				}
			}
			public void update() { 
				
				if(gameState == playState) {
					//PLAYER
					player.update();
					//NPC
					for(int i = 0; i < npc.length; i++) {
						if(npc[i] != null) {
							npc[i].update();
						}
					}
					//MONSTER
					for(int i = 0; i < monster.length; i++) {
						if(monster[i] != null) {
                           if(monster[i].alive == true && monster[i].dying == false) {
                        	   monster[i].update();
							}
                           if(monster[i].alive == false) {
                        	   monster[i].checkDrop();
                        	   monster[i] = null;
							}
							
						}
				}
					for(int i = 0; i < projectile.length; i++) {
						if(projectile[i] != null) {
                           if(projectile[i].alive == true ) {
                        	   projectile[i].update();
							}
                           if(projectile[i].alive == false) {
                        	   projectile[i] = null;
							}
							
						}
				}
					
					}
			
				if(gameState == pauseState) {
					//nothing
				}
				 player.update();
			}
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D)g;
				
				
				//DEBUG
				long drawStart = 0;
				if(keyH.showDebugText  == true) {
					drawStart = System.nanoTime();
				}
			
				//TITLE SCREEN
				if(gameState == titleState) {
					u1.draw(g2);
					
				}
				//OTHERS
				else {
					 //TILE
					tileM.draw(g2);
					}
					
					//ADD ENTITIES TO THE LIST
					kentityList.add(player);
					
					for(int i = 0; i < npc.length; i++) {
						if(npc[i] != null) {
							kentityList.add(npc[i]);
						}
					}
					for(int i = 0; i < obj.length; i++) {
						if(obj[i] != null) {
							kentityList.add(obj[i]);
						}
					}
					
					for(int i = 0; i < monster.length; i++) {
						if(monster[i] != null) {
							kentityList.add(monster[i]);
						}
					}
					
					for(int i = 0; i < projectile.length; i++) {
						if(projectile[i] != null) {
							kentityList.add(projectile[i]);
						}
					}
					
					//SORT
					Collections.sort(kentityList, new Comparator<Kentity>() {

						@Override
						public int compare(Kentity e1, Kentity e2) {
							
							int result = Integer.compare(e1.worldY, e2.worldY);
							return result;
						}
						
					});
					
					//DRAW ENTITIES
					for(int i = 0; i < kentityList.size(); i++) {
						kentityList.get(i).draw(g2);
						
					}
					//EMPTY ENTITY LIST
					 kentityList.clear();
			
					 //U1
					 u1.draw(g2);
				
				
				
					//DEBUG
				 if(keyH.showDebugText  == true) {
					long drawEnd = System.nanoTime();
					long passed = drawEnd - drawStart;
					
					g2.setFont( new Font("Arial", Font.PLAIN,20));
					g2.setColor(Color.white);
					int x = 10;
					int y = 400;
					int lineHeight = 20;
					
					g2.drawString("WorldX" + player.worldX,x ,y); y += lineHeight;
					g2.drawString("WorldY" + player.worldY,x ,y); y += lineHeight;
					g2.drawString("Col" +( player.worldX + player.solidArea.x)/tileSize,x ,y); y += lineHeight;
					g2.drawString("Row" +( player.worldY + player.solidArea.y)/tileSize,x ,y); y += lineHeight;
				
					
					g2.drawString("Draw Time :"+ passed, x, y);
					System.out.println("draw time :"+passed);
				 }
				g2.dispose();
			
			}
			public void playMusic(int i) {
				
				music.setFile(i);
				music.play();
				music.loop();
		
			}
			public void stopMusic() {
				
				music.stop();
			}
			public void playSE(int i) {
				
				se.setFile(i);
				se.play();
			}

		}






