package com.company;

import java.util.Random;

/**
 * Created by Jan on 2015-03-14.
 */
public class Monster {

    public final String TOMBSTONE = "Here Lies a Dead Monster";

    private int health = 500;
    private int attack = 20;
    private int skill_at_attack = 30;
    private int skill_at_magic = 50;
    private int lifesuck = 10;
    private int critical = 60;
    private int movement = 2;
    private int xPosition = 0;
    private int yPosition = 0;
    private boolean alive = true;
    private int action;
    private Random generator = new Random();
    
    private int maxXPosition = 10;
    private int minXPosition = 0;
    private int maxYPosition = 10;
    private int minYPosition = 0;
    static public Board myBoard;

    public String name = "Big Monster";
    public char charName = 'B';


    public void setAlive(boolean alive) {
        this.alive = alive;
    }


    public void move(int x, int y) {

        myBoard.battleBoard[xPosition][yPosition] = '*';

        if (Math.abs(x) > movement) {
            if (x > 0) {
                x = movement;
            } else {
                x = (-movement);
            }
        }

        if (maxXPosition > (xPosition + x) && minXPosition <= (xPosition + x)) {
            xPosition += x;
        } else {
            while (maxXPosition <= (xPosition + x) || minXPosition > (xPosition + x)) {
                if (x > 0) {
                    x--;
                } else {
                    x++;
                }
            }
            xPosition += x;
        }

        if (Math.abs(y) > movement) {
            if (y > 0) {
                y = movement;
            } else {
                y = (-movement);
            }
        }

        if (maxYPosition > (yPosition + y) && minYPosition <= (yPosition + y)) {
            yPosition += y;
        } else {
            while (maxYPosition <= (yPosition + y) || minYPosition > (yPosition + y)) {
                if (y > 0) {
                    y--;
                } else {
                    y++;
                }
            }
            yPosition += y;
        }
        myBoard.battleBoard[xPosition][yPosition] = charName;
    }

    public void search(){
        if(alive) {
            int xMonster = -1, yMonster = -1;

            for (int x = (xPosition - movement < minXPosition) ? minXPosition : xPosition - movement; x <= ((maxXPosition <= xPosition + movement) ? (maxXPosition - 1) : xPosition + movement); x++) {
                for (int y = (yPosition - movement <= minYPosition) ? minYPosition : yPosition - movement; y <= ((maxYPosition <= yPosition + movement) ? (maxYPosition - 1) : yPosition + movement); y++) {
                    if (myBoard.battleBoard[x][y] != '*' && (x != xPosition && y != yPosition)) {
                        xMonster = x;
                        yMonster = y;
                        char enemy = myBoard.battleBoard[x][y];
                        for (int i=0;i<myBoard.myMonsters.length;i++) {
                            if (myBoard.myMonsters[i].charName == enemy) {
                                fight(myBoard.myMonsters[i]);
                            }
                        }
                       // System.out.println(xPosition + " " + yPosition);
                       // System.out.println(xMonster + " " + yMonster);
                        break;
                    }
                }
                if (xMonster != -1) {
                    break;
                }
            }

            if (xMonster == -1) {
                xMonster = (int) (4 * Math.random() - 2) * movement;
                yMonster = (int) (4 * Math.random() - 2) * movement;
                //System.out.println(xMonster + " " + yMonster);
                move(xMonster, yMonster);
            } else {
                xMonster = xMonster - xPosition;
                yMonster = yMonster - yPosition;
                // System.out.println(xMonster + " " + yMonster);
                move(xMonster, yMonster);
            }
        }
    }

    public Monster(int attack, int health, int movement, String name) {
        this.attack = attack;
        this.health = health;
        this.movement = movement;
        this.name = name;
        charName = name.charAt(0);
        this.lifesuck = this.attack/2;
        this.critical = this.attack*3;
        do {
            xPosition = (int) (Math.random() * maxXPosition);
            yPosition = (int) (Math.random() * maxYPosition);
        }
        while (myBoard.battleBoard[xPosition][yPosition] != '*');
        myBoard.battleBoard[xPosition][yPosition] = charName;
    }

    private void fight(Monster enemy){
    	System.out.println(this.name+" ambushed "+enemy.name );
    	while(enemy.alive==true && this.alive==true){
    		this.action=this.generator.nextInt(2);
    		enemy.action=this.generator.nextInt(2);
    		if(this.action==0){
    			if(this.skill_at_attack<generator.nextInt(100)){
    				enemy.health-=this.critical;
    				System.out.println(this.name+" uses slash, it's super effective    "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
    			}
    			else{
    				enemy.health-=this.attack;
    				System.out.println(this.name+" uses slash                          "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
    			}
    		}
    			else {
    				if(this.skill_at_magic<generator.nextInt(100)){
        				enemy.health-=this.attack;
        				this.health+=this.lifesuck;
        				System.out.println(this.name+" uses lifesuck, it's super effective    "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
        			}
        			else{
        				enemy.health-=this.lifesuck;
        				System.out.println(this.name+" uses lifesuck, but it's ineffective    "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
        			}
    			}
    		
    		if(enemy.action==0){
    			if(this.skill_at_attack<generator.nextInt(100)){
    				this.health-=enemy.critical;
    				System.out.println(enemy.name+" uses slash, it's super effective     "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
    			}
    			else{
    				this.health-=enemy.attack;
    				System.out.println(enemy.name+" uses slash                           "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
    			}
    		}
    			else {
    				if(this.skill_at_magic<generator.nextInt(100)){
    					this.health-=enemy.attack;
        				enemy.health+=enemy.lifesuck;
        			System.out.println(enemy.name+" uses lifesuck, it's super effective  "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
        			}
        			else{
        				this.health-=enemy.lifesuck;
        				System.out.println(enemy.name+" uses lifesuck, but it's ineffective   "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
        			}
    			}
    		
    		if(enemy.health<=0){
    			enemy.setAlive(false);
    			System.out.println("Infamous moster "+enemy.name+" was slain by "+this.name);
    		}	
    		else if(this.health<=0){
    			this.setAlive(false);
    			System.out.println("Ambush failed. "+this.name+" was slain by "+enemy.name);   	
    		}
    	}
    }
}
