package com.company;

import java.util.Arrays;
/**
 * Created by Jan on 2015-03-14.
 */
public class Board {

    public char [][] battleBoard;

    public  void buildBattleBoard() {
        for(char[] row : battleBoard){
            Arrays.fill(row, '*');
        }
    }

    public  void redrawBoard(){
        for(int k=0;k<30;k++){
            System.out.print('-');
        }

        System.out.println();

        for (int i=0; i<battleBoard.length;i++){
            for (int j=0; j<battleBoard[i].length;j++){
                System.out.print("|" + battleBoard[i][j] + "|");
            }
            System.out.println();
        }

        for(int k=0;k<30;k++){
            System.out.print('-');
        }

        System.out.println();
    }

    public static Monster [] myMonsters;

    public Board(char[][] battleBoard) {
        this.battleBoard = battleBoard;
    }

    public void setMyMonsters(Monster[] myMonsters) {
        Board.myMonsters = myMonsters;
    }
}
