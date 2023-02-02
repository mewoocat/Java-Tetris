
package com.mycompany.tetris_game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle{
    boolean active = true;
    int xGrid;
    int yGrid;
    
    Block(int x, int y, int w, int h, int xGrid, int yGrid, Color color){
        super(x, y, w, h);
        this.xGrid = xGrid;
        this.yGrid = yGrid;
        this.setFill(color);
    }

    int getYGrid(){
        return this.yGrid;
    }
    
    boolean inRow(int row){
        if(this.yGrid == row){
            return true;
        }
        else{
            return false;
        }
    }
}