package com.company;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by Jan on 2015-03-14.
 */
public class Monster {

    public final String TOMBSTONE = "Here Lies a Dead Monster";
    private boolean ifHero=false;
    private int health = 500;
    private int health_max=500;
    private int health_regeneration=50;
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
    static public Random generator = new Random();
    private int speciality = 50;
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
    	if(health_max>health)
    		health+=health_regeneration;
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

    public Monster( int health, int movement, String name, int skill_at_attack,int skill_at_magic,int speciality, boolean Hero) {
        this.attack = skill_at_attack;
        this.health = health;
        this.health_max=health;
        this.health_regeneration=health/10;
        this.movement = movement;
        this.name = name;
        this.skill_at_attack=skill_at_attack;
        this.skill_at_magic=skill_at_magic;
        this.ifHero=Hero;
        if(speciality>70)
        	this.speciality=70;
        else if(speciality<30){
        	this.speciality=30;
        }
        	else
        		this.speciality=speciality;
        charName = name.charAt(0);
        this.lifesuck = this.skill_at_magic/2;
        this.critical = this.attack*3;
        do {
            xPosition = (int) (Math.random() * maxXPosition);
            yPosition = (int) (Math.random() * maxYPosition);
        }
        while (myBoard.battleBoard[xPosition][yPosition] != '*');
        myBoard.battleBoard[xPosition][yPosition] = charName;
    }
    public Monster(int attack, int health, int movement, String name) {
        this.attack = attack;
        this.health = health;
        this.health_max=health;
        this.health_regeneration=health/10;
        this.movement = movement;
        this.name = name;
        charName = name.charAt(0);
        this.lifesuck = this.skill_at_magic/2;
        this.critical = this.attack*3;
        do {
            xPosition = (int) (Math.random() * maxXPosition);
            yPosition = (int) (Math.random() * maxYPosition);
        }
        while (myBoard.battleBoard[xPosition][yPosition] != '*');
        myBoard.battleBoard[xPosition][yPosition] = charName;
    }
    private void fight(Monster enemy){
    	int demagecountenemy=0, demagecountattacker=0;
    	int spellcountenemy=0, spellcountattacker=0;
    	int succesrateenemy=0, succesrateattacker=0;
    	int successpellenemy=0, successpellattacker=0;
    	int tryrateenemy=1, tryrateattacker=1;
    	int tryspellenemy=1, tryspellattacker=1;
    	System.out.println(this.name+" ambushed "+enemy.name );
    	while(enemy.alive==true && this.alive==true){
    		this.action=generator.nextInt(100);
    		enemy.action=generator.nextInt(100);
    		if(this.action<speciality){
    			if(this.skill_at_attack>generator.nextInt(100)){
    				enemy.health-=this.critical;
    				demagecountattacker+=this.critical;
    				tryrateattacker++;
    				succesrateattacker++;
    				System.out.println(this.name+" uses slash, it's super effective    "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
    			}
    			else{
    				enemy.health-=this.attack;
    				demagecountattacker+=this.attack;
    				tryrateattacker++;
    				System.out.println(this.name+" uses slash                          "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
    			}
    		}
    			else {
    				if(this.skill_at_magic>generator.nextInt(100)){
        				enemy.health-=this.lifesuck;
        				this.health+=this.lifesuck;
        				spellcountattacker+=this.lifesuck*2;
        				tryspellattacker++;
        				successpellattacker++;
        				System.out.println(this.name+" uses lifesuck, it's super effective    "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
        			}
        			else{
        				enemy.health-=this.attack;
        				spellcountattacker+=this.attack;
        				tryspellattacker++;
        				System.out.println(this.name+" uses lifesuck, but it's ineffective    "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
        			}
    			}
    		
    		if(enemy.action<enemy.speciality){
    			if(enemy.skill_at_attack>generator.nextInt(100)){
    				this.health-=enemy.critical;
    				demagecountenemy+=enemy.critical;
    				tryrateenemy++;
    				succesrateenemy++;
    				System.out.println(enemy.name+" uses slash, it's super effective     "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
    			}
    			else{
    				this.health-=enemy.attack;
    				demagecountenemy+=enemy.attack;
    				tryrateenemy++;
    				System.out.println(enemy.name+" uses slash                           "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
    			}
    		}
    			else {
    				if(enemy.skill_at_magic>generator.nextInt(100)){
    					this.health-=enemy.lifesuck;
        				enemy.health+=enemy.lifesuck;
        				spellcountenemy=enemy.lifesuck+enemy.lifesuck;
        				tryspellenemy++;
        				successpellenemy++;
        			System.out.println(enemy.name+" uses lifesuck, it's super effective  "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
        			}
        			else{
        				this.health-=enemy.attack;
        				spellcountenemy=enemy.attack;
        				tryspellenemy++;
        				System.out.println(enemy.name+" uses lifesuck, but it's ineffective   "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
        			}
    			}
    		
    		if(enemy.health<=0){
    			enemy.setAlive(false);
    			System.out.println("Infamous moster "+enemy.name+" was slain by "+this.name);
    			if(this.ifHero==false){
    			System.out.println("Infamous moster "+enemy.name+" was slain by "+this.name);
    			if((demagecountattacker*succesrateattacker^2/tryrateattacker)>(spellcountattacker*successpellattacker^2/tryspellattacker)){
    				this.speciality+=5;
    				this.attack+=0.1*this.attack;
    				this.skill_at_attack+=5;
    				System.out.println(this.name+" took a level in Badass");
    			}
    			else{
    				this.speciality-=5;
    				this.lifesuck+=0.2*this.lifesuck;
    				this.skill_at_magic+=5;
    				System.out.println(this.name+" took a level in Wizardry");
    			}
    			}
    			else{
    				System.out.println(this.name+" have gained expirience. You can use it to upgrade your skills and health. How would you like to spend it?");
    				System.out.println("1 - Health    2 - Attack   3 - Magic   4 - Attack skill   5 - Magic skill   6 - More magic based fighting style   7 - More melee based fightin style");

    				int exp=2;
    				while(exp!=0){
    					int n= new Scanner(System.in).nextInt();
    					switch (n) {
    		            case 1:  this.health_max+=10;
    		            		 exp--;
    		            		 break;
    		            case 2:  this.attack+=5;
    		        			 exp--;
    		                     break;
    		            case 3:  this.lifesuck+=3;
    		            		 exp--;
    		                     break;
    		            case 4:  this.skill_at_attack+=7;
    		            		 exp--;
    		                     break;
    		            case 5:  this.skill_at_magic+=10;
    		            		 exp--;
    		                     break;
    		            case 6:  this.speciality-=5;
    		            		 exp--;
    		                     break;
    		            case 7:  this.speciality+=5;
    		            		 exp--;
    		                     break;
    		            
    		            default: System.out.println("Ivalid instruction");
    		                     break;
    		        }

    				}
    				
    			}
    		}
    		else if(this.health<=0){
    			this.setAlive(false);
    			System.out.println("Ambush failed. "+this.name+" was slain by "+enemy.name);   
    			if(enemy.ifHero==false){
    			if((demagecountenemy*succesrateenemy^2/tryrateenemy)>(spellcountenemy*successpellenemy^2/tryspellenemy)){
    				enemy.speciality+=5;
    				enemy.attack+=0.1*this.attack;
    				enemy.skill_at_attack+=5;
    				System.out.println(enemy.name+" took a level in Badass");
    			}
    			else{
    				enemy.speciality-=5;
    				enemy.lifesuck+=0.2*this.lifesuck;
    				enemy.skill_at_magic+=5;
    				System.out.println(enemy.name+" took a level in Wizardry");
    			}
    			}
    			else{
    				System.out.println(enemy.name+" have gained expirience. You can use it to upgrade your skills and health. How would you like to spend it?");
    				System.out.println("1 - Health    2 - Attack   3 - Magic   4 - Attack skill   5 - Magic skill   6 - More magic based fighting style   7 - More melee based fightin style");

    				int exp=2;
    				while(exp!=0){
    					int n= new Scanner(System.in).nextInt();
    					switch (n) {
    		            case 1:  enemy.health_max+=10;
    		            		 exp--;
    		            		 break;
    		            case 2:  enemy.attack+=5;
    		        			 exp--;
    		                     break;
    		            case 3:  enemy.lifesuck+=3;
    		            		 exp--;
    		                     break;
    		            case 4:  enemy.skill_at_attack+=7;
    		            		 exp--;
    		                     break;
    		            case 5:  enemy.skill_at_magic+=10;
    		            		 exp--;
    		                     break;
    		            case 6:  enemy.speciality-=5;
    		            		 exp--;
    		                     break;
    		            case 7:  enemy.speciality+=5;
    		            		 exp--;
    		                     break;
    		            
    		            default: System.out.println("Ivalid instruction");
    		                     break;
    		        }

    				}
    				
    			}
    				
    				
    		}
    	}
    }
}
