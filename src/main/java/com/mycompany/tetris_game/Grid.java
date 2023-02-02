
package com.mycompany.tetris_game;

import java.util.ArrayList;
import java.util.Random;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Grid{
    final int BORDER_EDGE = 20;
    final int BLOCK_SIZE = 26;

    int[][] blockGrid = new int[16][26];    //[x][y]    valid: x > 2 && x < 13  y > 3 && y < 23
    int[][] shapeGrid;                      //[x][y]    4 x 4 grid of current shape
    boolean isActiveShape = false;          //true if there is an active shape
    int xAnchor = 3;                        //x offset for the top left coordinate of the shapeGrid on the blockGrid for screen
    int yAnchor = 0;                        //y offset for the top left coordinate of the shapeGrid on the blockGrid for screen
    Color activeColor;
    
    Integer score = 0;
    Pane scorePane;
    
    Pane root;                                              //javafx root to draw grid on
    ArrayList<Block> blockList = new ArrayList<Block>();    //list of current blocks on the screen
    
    Grid(Pane p, Pane scorePane){                           
        this.root = p;                                      //sets root
        this.scorePane = scorePane;
    }
    
    public boolean isValidMove(char dir){                   //checks if the current dirction input is valid
        boolean valid = true;
        //System.out.println("xAnchor: " + xAnchor);
        //System.out.println("yAnchor: " + yAnchor);
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (shapeGrid[i][j] == 2){
                    //System.out.println("i: " + i + "   " + "j: " + j);
                    if (dir == 'L'){
                        if (((i + xAnchor + 3 - 1) < 3) || (blockGrid[i + xAnchor + 3 - 1][j + yAnchor + 3] == 1)){
                            //System.out.print("i indices: " + i + " ");
                            valid = false;
                        }
                    }
                    if (dir == 'R'){
                        if (((i + xAnchor + 3 + 1) > 12) || (blockGrid[i + xAnchor + 3 + 1][j + yAnchor + 3] == 1)){
                            valid = false;
                        }
                    }
                    if (dir == 'D'){
                        if (((j + yAnchor + 3 + 1) > 22) || (blockGrid[i + xAnchor + 3][j + yAnchor + 3 + 1] == 1)){
                            valid = false;
                        }
                    }
                    if (dir == 'O'){
                        if ((blockGrid[i + xAnchor + 3][j + yAnchor + 3] == 1) || ((j + yAnchor + 3) > 22) || ((i + xAnchor + 3) > 12) || ((i + xAnchor + 3) < 3)){
                            valid = false;
                        }
                    }
                }
            }
        }
        return valid;
    }
    
    
    public void addShape(ShapeBlock s){
        isActiveShape = true;
        shapeGrid = s.shape;
        activeColor = s.color;
        
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (shapeGrid[i][j] == 2){
                    Block b = new Block(((i + this.xAnchor) * BLOCK_SIZE) + BORDER_EDGE, ((j + this.yAnchor) * BLOCK_SIZE) + BORDER_EDGE, BLOCK_SIZE, BLOCK_SIZE, i + this.xAnchor + 3, j + this.yAnchor + 3, s.color);
                    blockList.add(b);
                    root.getChildren().add(b);
                }
            }
        }
    }
    
    public void moveLeft(){
       if (isValidMove('L')){
           for (Block b : this.blockList){
               if (b.active){
                   b.xGrid--;
                   b.setTranslateX(b.getTranslateX() - BLOCK_SIZE);
               }
           }
           xAnchor--;
       }
    }
    
    public void moveRight(){
       if (isValidMove('R')){
           for (Block b : this.blockList){
               if (b.active){
                   b.xGrid++;
                   b.setTranslateX(b.getTranslateX() + BLOCK_SIZE);
               }
           }
           xAnchor++;
       }
    }
    
    public void moveDown(){
        if(this.isActiveShape){
            if (isValidMove('D')){
               for (Block b : this.blockList){
                   if (b.active){
                       b.yGrid++;
                       b.setTranslateY(b.getTranslateY() + BLOCK_SIZE);
                   }
               }
               yAnchor++;
            }
            else{
                //places shape on blockGrid
                for (int i = 0; i < 4; i++){
                    for (int j = 0; j < 4; j++){
                        if (shapeGrid[i][j] == 2){
                            blockGrid[i + xAnchor + 3][j + yAnchor + 3] = 1;
                        }
                   }
               }
                this.isActiveShape = false;
                //deactivates shape
                for (Block b : this.blockList){
                    if (b.active){
                        b.active = false;
                        xAnchor = 3;
                        yAnchor = 0;
                   }
               }
               //clears any full rows
               // Issue with clearing lines somewhere here prob
                // Doesn't seem to be moving everything down
               boolean clearRow = true;
               int[] toBeCleared = {0, 0, 0, 0};            //row indices to be cleared
               int currentIndex = 0;                        //current number of rows to be cleared
               for (int j = 3; j < 23; j++){                //checks each row to see if it is full
                   for (int i = 3; i < 13; i++){            
                       if (blockGrid[i][j] == 0){           //if there is at least 1 empty space in a row
                           clearRow = false;
                       }
                   }
                   if (clearRow){                           //if there are no empty spaces in current row
                       // Check block list for any blocks that are in the same place on the grid as the number array
                       for (int loopCounter = 0; loopCounter < blockList.size(); loopCounter++){
                           if (blockList.get(loopCounter).getYGrid() == j){
                               // Erase block from grid
                               blockList.remove(blockList.get(loopCounter));
                               root.getChildren().remove(blockList.get(loopCounter));
                           }
                       }

                       toBeCleared[currentIndex] = j;       //place current row index at the next spot in the array of rows to be cleared
                       currentIndex++;
                   }
                   clearRow = true;                         //reset clear row
               }
               score++;
               //scoreNumber.setText(score.toString());
               for (int r = 0; r < currentIndex; r++){      //for all rows to be cleared
                   int row = toBeCleared[r];
                   for (int j = row; j > 3; j--){           //copy all blocks on blockGrid above row down by 1
                       for (int i = 3; i < 13; i++){
                           blockGrid[i][j] = blockGrid[i][j - 1];
                       }
                   }
                   for (Block b : this.blockList){          //deletes rows on screen
                        if (b.inRow(row)){
                           root.getChildren().removeIf(n -> {
                                Block node = (Block) n;
                                return node.inRow(row);
                           });
                        }
                    }
                   for (Block b : this.blockList){
                        if (b.yGrid < row){
                            b.yGrid++;
                            b.setTranslateY(b.getTranslateY() + BLOCK_SIZE);
                        }
                    }  
               }
               //prints out blockGrid
               System.out.println();
               System.out.println("blockGrid:");
               for (int j = 3; j < 23; j++){                
                   for (int i = 3; i < 13; i++){            
                       System.out.print(blockGrid[i][j]);
                   }
                   System.out.println();
               }
            }
        }
    }

    public void drop(){
        // Move shape down until hits something
        while (isValidMove('D')) {
            moveDown();
        }
    }
    
    public void rotate(){
       //rotate shape grid
       shapeGrid = rotateGrid();
       for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                System.out.print(shapeGrid[i][j] + "  ");
            }
            System.out.println();
        }
       //if shape grid doesn't collide
       if(isValidMove('O')){
           for (Block b : this.blockList){
                if (b.active){
                   root.getChildren().removeIf(n -> {
                        Block node = (Block) n;
                        return node.active;
                   }); 
               }
           }
           for (int i = 0; i < 4; i++){
                for (int j = 0; j < 4; j++){
                    if (shapeGrid[i][j] == 2){
                        Block b = new Block(((i + this.xAnchor) * BLOCK_SIZE) + BORDER_EDGE, ((j + this.yAnchor) * BLOCK_SIZE) + BORDER_EDGE, BLOCK_SIZE, BLOCK_SIZE, i + this.xAnchor + 3, i + this.yAnchor + 3, activeColor);
                        blockList.add(b);
                        root.getChildren().add(b);
                    }
                }
           }
       }
       else{
           shapeGrid = rotateGrid();
           shapeGrid = rotateGrid();
           shapeGrid = rotateGrid();
       }
    }
    
    public int[][] rotateGrid(){
        int[][] rotatedGrid = new int[4][4];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                rotatedGrid[i][j] = shapeGrid[4 - j - 1][i];
            }
        }
        return rotatedGrid;
    }
    
    public void update(){
        if (this.isActiveShape){
            moveDown();
        }
        else{
            Random Rand = new Random();
            int randInt = Rand.nextInt(5);
            this.addShape(new ShapeBlock(randInt));
            this.isActiveShape = true;
        }
    }
}