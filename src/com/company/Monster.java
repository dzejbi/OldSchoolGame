package com.company;
import java.util.*;

/**
 * Created by Jan on 2015-03-14.
 */
public class Monster {

    private boolean ifHero=false;

    private int health = 500;
    private int health_max=500;
    private int health_regeneration=50;

    private int attack = 20;
    private int skill_at_attack = 30;
    private int skill_at_magic = 50;

    private int lifesuck = 10;
    private int critical = 60;
    private int speciality = 50;


    private int movement = 2;
    private int xPosition = 0;
    private int yPosition = 0;

    private int maxXPosition = 10;
    private int minXPosition = 0;
    private int maxYPosition = 10;
    private int minYPosition = 0;

    private boolean alive = true;
    private int action;

    public String name = "Big Monster";
    public char charName = 'B';

    static public Random generator = new Random();
    static public Board myBoard;



    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    private boolean LineOfSight(Monster Attacker, Monster Defender){
        int xdiff=Attacker.xPosition-Defender.xPosition;
        int ydiff=Attacker.yPosition-Defender.yPosition;
        boolean ifTrue=true;
        if(xdiff>ydiff){
            for(int i=1;i<=xdiff;i++){
                if(myBoard.battleBoard[Attacker.xPosition+i][Attacker.yPosition+i*ydiff/xdiff] == 'X'){
                    ifTrue=false;
                    break;
                }
            }
        }
        else{
            for(int i=1;i<=ydiff;i++){
                if(myBoard.battleBoard[Attacker.xPosition+i*xdiff/ydiff][Attacker.yPosition+i] == 'X'){
                    ifTrue=false;
                    break;
                }
            }

        }
        return ifTrue;
    }

    public void move(int x, int y) {

        //System.out.println(x+ ":" +y);

        int distance;

        SortedMap<Integer, Integer> map_x = new TreeMap<Integer, Integer>();
        SortedMap<Integer, Integer> map_y = new TreeMap<Integer, Integer>();

        int [] data = new int [2];


        for(int i = 0; i < movement; i++ ) {

            myBoard.battleBoard[xPosition][yPosition] = '*';

            for (int a = (xPosition - 1 < minXPosition) ? minXPosition : xPosition - 1; a <= ((maxXPosition <= xPosition + 1) ? (maxXPosition - 1) : xPosition + 1); a++) {
                for (int b = (yPosition - 1 <= minYPosition) ? minYPosition : yPosition - 1; b <= ((maxYPosition <= yPosition + 1) ? (maxYPosition - 1) : yPosition + 1); b++) {

                    if (myBoard.battleBoard[a][b] != 'X') {
                        distance = (int) (Math.sqrt((a - x) * (a - x) + (b - y) * (b - y)));
                        data[0] = a;
                        data[1] = b;
                       // System.out.println(a+ "-" +b + "-"+distance);
                        map_x.put(distance, data[0]);
                        map_y.put(distance,data[1]);

                    }
                }
            }

            data[0] = map_x.get(map_x.firstKey());
            data[1] = map_y.get(map_y.firstKey());
           // System.out.println(map_x);
            xPosition = data[0];
            yPosition = data[1];

            if (myBoard.battleBoard[xPosition][yPosition]!='*'){
                char enemy = myBoard.battleBoard[xPosition][yPosition];
                System.out.println(playerStats.players+"**");
                for (int o=0;o<myBoard.myMonsters.length;o++) {
                    System.out.println(playerStats.players+"***");
                    if (myBoard.myMonsters[o].charName == enemy) {
                        System.out.println(playerStats.players+"****");
                        fight(myBoard.myMonsters[o]);
                    }
                    if(this.alive){
                        myBoard.battleBoard[xPosition][yPosition] = charName;
                    }
                    else{
                        myBoard.battleBoard[xPosition][yPosition] = enemy;
                    }
                }
                break;
            }
            else{
                myBoard.battleBoard[xPosition][yPosition] = charName;
            }

            //myBoard.redrawBoard();
        }

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
                        break;
                    }
                }
                if (xMonster != -1) {
                    break;
                }
            }

            if (xMonster == -1) {
                xMonster = (int) (4 * Math.random() - 2) * movement + xPosition;
                yMonster = (int) (4 * Math.random() - 2) * movement + yPosition;
                //System.out.println(xMonster + " " + yMonster);
                move(xMonster, yMonster);
            } else {
                xMonster = xMonster ;
                yMonster = yMonster ;
                // System.out.println(xMonster + " " + yMonster);
                move(xMonster, yMonster);
            }
        }
    }

    public Monster( int health, int movement, String name, int skill_at_attack, int skill_at_magic, int speciality, boolean Hero) {

        this.attack = skill_at_attack;
        this.health = health;
        this.health_max = health;
        this.health_regeneration = health / 10;
        this.movement = movement;
        this.name = name;
        this.skill_at_attack = skill_at_attack;
        this.skill_at_magic = skill_at_magic;
        this.ifHero = Hero;
        charName = name.charAt(0);
        this.lifesuck = this.skill_at_magic/2;
        this.critical = this.attack*3;

        if (speciality > 70) {
            this.speciality = 70;
        }
        else {
            if (speciality < 30) {
                this.speciality = 30;
            }
            else {
                this.speciality = speciality;
            }
        }

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

    public Monster (){
    }

    private void fight(Monster enemy){

    	int damageCountEnemy =0, damageCountAttacker =0,
            spellCountEnemy =0, spellCountAttacker =0,
    	    successRateEnemy =0, successRateAttacker =0,
    	    successSpellEnemy =0, successSpellAttacker =0,
            tryRateEnemy =1, tryRateAttacker =1,
    	    trySpellEnemy =1, trySpellAttacker =1;


    	System.out.println(this.name+" ambushed "+enemy.name );

    	while(enemy.alive==true && this.alive==true){

    		this.action=generator.nextInt(100);
    		enemy.action=generator.nextInt(100);

    		if(this.action<speciality){
    			if(this.skill_at_attack>generator.nextInt(100)){
    				enemy.health-=this.critical;
    				damageCountAttacker +=this.critical;
    				tryRateAttacker++;
    				successRateAttacker++;
    				System.out.println(this.name+" uses slash, it's super effective    "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
    			}
    			else{
    				enemy.health-=this.attack;
    				damageCountAttacker +=this.attack;
    				tryRateAttacker++;
    				System.out.println(this.name+" uses slash                          "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
    			}
    		}
    			else {
    				if(this.skill_at_magic>generator.nextInt(100)){
        				enemy.health-=this.lifesuck;
        				this.health+=this.lifesuck;
        				spellCountAttacker +=this.lifesuck*2;
        				trySpellAttacker++;
        				successSpellAttacker++;
        				System.out.println(this.name+" uses lifesuck, it's super effective    "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
        			}
        			else{
        				enemy.health-=this.attack;
        				spellCountAttacker +=this.attack;
        				trySpellAttacker++;
        				System.out.println(this.name+" uses lifesuck, but it's ineffective    "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
        			}
    			}
    		
    		if(enemy.action<enemy.speciality){
    			if(enemy.skill_at_attack>generator.nextInt(100)){
    				this.health-=enemy.critical;
    				damageCountEnemy +=enemy.critical;
    				tryRateEnemy++;
    				successRateEnemy++;
    				System.out.println(enemy.name+" uses slash, it's super effective     "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
    			}
    			else{
    				this.health-=enemy.attack;
    				damageCountEnemy +=enemy.attack;
    				tryRateEnemy++;
    				System.out.println(enemy.name+" uses slash                           "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
    			}
    		}
    			else {
    				if(enemy.skill_at_magic>generator.nextInt(100)){
    					this.health-=enemy.lifesuck;
        				enemy.health+=enemy.lifesuck;
        				spellCountEnemy =enemy.lifesuck+enemy.lifesuck;
        				trySpellEnemy++;
        				successSpellEnemy++;
        			System.out.println(enemy.name+" uses lifesuck, it's super effective  "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
        			}
        			else{
        				this.health-=enemy.attack;
        				spellCountEnemy =enemy.attack;
        				trySpellEnemy++;
        				System.out.println(enemy.name+" uses lifesuck, but it's ineffective   "+this.name+" "+this.health+"  "+enemy.name+" "+enemy.health );
        			}
    			}
    		
    		if(enemy.health<=0){
    			enemy.setAlive(false);
    			System.out.println("Infamous moster "+enemy.name+" was slain by "+this.name);
    			if(this.ifHero==false){
    			if((damageCountAttacker * successRateAttacker ^2/ tryRateAttacker)>(spellCountAttacker * successSpellAttacker ^2/ trySpellAttacker)){
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
    			if((damageCountEnemy * successRateEnemy ^2/ tryRateEnemy)>(spellCountEnemy * successSpellEnemy ^2/ trySpellEnemy)){
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
