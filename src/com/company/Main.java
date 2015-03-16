package com.company;

import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        char [][] Board= new char[10][10];
        Board Level_1 = new Board(Board);
        Level_1.buildBattleBoard();
        Monster.myBoard = Level_1;
        Monster Monsters[] = new Monster[4];
        Monsters [0] = new Monster(10,100,1,"Frank");
        Monsters [1] = new Monster(10,150,1,"Paul");
        Monsters [2] = new Monster(20,100,1,"George");
        Monsters [3] = new Monster(10,100,2,"James");
        Level_1.setMyMonsters(Monsters);
        Level_1.redrawBoard();
        for(int i =0;i<1000;i++){
            for(int j=0;j<4;j++) {
                Monsters[j].search();
            }
        }
        Level_1.redrawBoard();
    }
}
