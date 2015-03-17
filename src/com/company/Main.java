package com.company;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	
    public static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) throws IOException, InterruptedException {
    	char [][] Board= new char[10][10];
        Board Level_1 = new Board(Board);
        Level_1.buildBattleBoard();
        Monster.myBoard = Level_1;
    	boolean create = true;
    	int players=1;
    	Monster Players[] = new Monster[4];
    	System.out.println("Welcome into my castle dr. Hellsing, create yo playaa (all stats should add up to 170. You can create up to 4 players)");
    	while(create==true){
    		
    		System.out.println("Name ");
    		String name= new Scanner(System.in).nextLine();
    		System.out.println("Health ");
    		int health= new Scanner(System.in).nextInt();
    		
    		
    		System.out.println("Fighting skill ");
    		int skill_at_attack= new Scanner(System.in).nextInt();
    		System.out.println("Magic skill ");
    		int skill_at_magic= new Scanner(System.in).nextInt();
    		if(health+skill_at_attack+skill_at_magic>170){
    			System.out.println("wrong amount of skillpoints, create again ");
    			continue;
    		}
    		System.out.println("Last question: fighting style of your character (30-70, higher number - melee weapon specialist, lesser number - blood mage): ");
    		int speciality= new Scanner(System.in).nextInt();
    		Players[players-1]=new Monster(health, 2,name, skill_at_attack, skill_at_magic, speciality, true);
    		System.out.println("Creat another character? (t - yes, anything alse-no) ");
    		String text = new Scanner(System.in).nextLine();
    		char txt= text.charAt(0);
    		if(txt=='t'){
    			System.out.println("Next Player ");	
    			players++;
    		}
    		
    		else {
    			System.out.println("Starting Deathmatch Simulation...");
    			create=false;    		
    		}
    		

    	}
        
        Monster Monsters[] = new Monster[4+players];
        Monsters [0] = new Monster(15,125,1,"Frank");
        Monsters [1] = new Monster(10,150,1,"Paul");
        Monsters [2] = new Monster(20,100,1,"George");
        Monsters [3] = new Monster(5,200,2,"James");
        for(int n=1;n<players+1;n++)
        	Monsters [3+n] = Players[n-1];
        Level_1.setMyMonsters(Monsters);
        Level_1.redrawBoard();
        for(int i =0;i<1000;i++){
            for(int j=0;j<4+players;j++) {
                Monsters[j].search();
            }
        }
        Level_1.redrawBoard();
    }
}

