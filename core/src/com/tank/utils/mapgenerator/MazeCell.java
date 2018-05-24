package com.tank.utils.mapgenerator;

/**
 * @author Daniel Pham
 * @version April 10th 2018
 * 
 * Description: The MazeCell class is used to help the MazeMaker class by storing values at specific locales for checking. It is used to
 * create the path in the depth-first creation.
 */

public class MazeCell{
    int[] randomDirections;		//The array of directions to check
    int x;				//The x position of the cell
    int y;				//The y position of the cell
    int count;			//The current direction to check
    boolean deadEnd;	//If this cell is a dead end

    public MazeCell(int pos1, int pos2, int[] directions){
        x = pos1;
        y = pos2;
        randomDirections = directions;
        count = -1;
        deadEnd = true;
    }
    
    public MazeCell(int pos1, int pos2){
        x = pos1;
        y = pos2;
        randomDirections = null;
        count = -1;
        deadEnd = true;
    }
    
    public int nextDirection(){
        count++;
        if (count < randomDirections.length){
            return randomDirections[count];
        }
        else{
            return -1;
        }
    }
    
    public boolean equals(Object obj){
        if (super.equals(obj)) return true;
        if (!(obj instanceof MazeCell)) return false;
        
        return (((MazeCell)obj).x == x && (((MazeCell)obj).y == y));
    }
}
