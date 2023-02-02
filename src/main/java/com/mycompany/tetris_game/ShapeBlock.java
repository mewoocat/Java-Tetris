
package com.mycompany.tetris_game;

import javafx.scene.paint.Color;

public class ShapeBlock{
    int[][] shape = new int[4][4];
    Color color;
    
    ShapeBlock(int code){
        switch (code){
            case 0:                     
                shape[0][1] = 2;
                shape[1][1] = 2;
                shape[1][0] = 2;
                shape[2][0] = 2;
                color = Color.MAROON;
                break;
            case 1:
                shape[1][0] = 2;
                shape[1][1] = 2;
                shape[1][2] = 2;
                shape[2][2] = 2;
                color = Color.LIMEGREEN;
                break;
            case 2:
                shape[1][0] = 2;
                shape[2][0] = 2;
                shape[1][1] = 2;
                shape[2][1] = 2;
                color = Color.HOTPINK;
                break;
            case 3:
                shape[0][1] = 2;
                shape[1][1] = 2;
                shape[2][1] = 2;
                shape[3][1] = 2;
                color = Color.AQUAMARINE;
                break;
            case 4:
                shape[2][0] = 2;
                shape[2][1] = 2;
                shape[2][2] = 2;
                shape[1][2] = 2;
                color = Color.CRIMSON;
                break;
        }
    }
}
