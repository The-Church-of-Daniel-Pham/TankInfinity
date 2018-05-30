package com.tank.utils.lineofsight;

/**
 * @author Daniel
 * Line of Sight Util class, based off the Bresenham Line drawing algorithm
 */

public class LineOfSight{
    public static boolean hasSight(int[][] map, int sRow, int sCol, int eRow, int eCol){
        int rowChange = Math.abs(eRow - sRow);
        int colChange = Math.abs(eCol - sCol);
        if (colChange > rowChange){
            return longColVersion(map, sRow, sCol, eRow, eCol);
        }
        else if (rowChange > colChange){
            return longRowVersion(map, sRow, sCol, eRow, eCol);
        }
        else{
            if (rowChange != 0 && colChange != 0){
                return longRowVersion(map, sRow, sCol, eRow, eCol);
            }
            else return true;
        }
    }
    
    private static boolean longRowVersion(int[][] map, int sRow, int sCol, int eRow, int eCol){
        int rowLength = eRow - sRow;
        int colLength = eCol - sCol;
        float delta = Math.abs(((float)colLength / rowLength));
        float currentError = 0.0f;
        int col = sCol;
        if (rowLength > 0){
            for (int row = sRow; row <= eRow; row++){
                if (isNotOpen(map,row, col)) return false;
                currentError += delta;
                while (currentError > 0.5f){
                    if (colLength > 0){
                        if (isNotOpen(map,row + 1, col) || isNotOpen(map,row, col + 1)) return false;
                        col++;
                    }
                    else if (colLength < 0){
                        if (isNotOpen(map,row + 1, col) || isNotOpen(map,row, col - 1)) return false;
                        col--;
                    }
                    currentError -= 1.0f;
                }
            }
        }
        else{
            for (int row = sRow; row >= eRow; row--){
                if (isNotOpen(map,row, col)) return false;
                currentError += delta;
                while (currentError > 0.5f){
                    if (colLength > 0){
                        if (isNotOpen(map,row - 1, col) || isNotOpen(map,row, col + 1)) return false;
                        col++;
                    }
                    else if (colLength < 0){
                        if (isNotOpen(map,row - 1, col) || isNotOpen(map,row, col - 1)) return false;
                        col--;
                    }
                    currentError -= 1.0f;
                }
            }
        }
        return true;
    }
    
    private static boolean longColVersion(int[][] map, int sRow, int sCol, int eRow, int eCol){
        int rowLength = eRow - sRow;
        int colLength = eCol - sCol;
        float delta = Math.abs(((float)rowLength / colLength));
        float currentError = 0.0f;
        int row = sRow;
        if (colLength > 0){
            for (int col = sCol; col <= eCol; col++){
                if (isNotOpen(map,row, col)) return false;
                currentError += delta;
                while (currentError > 0.5f){
                    if (rowLength > 0){
                        if (isNotOpen(map,row + 1, col) || isNotOpen(map,row, col + 1)) return false;
                        row++;
                    }
                    else if (rowLength < 0){
                        if (isNotOpen(map,row - 1, col) || isNotOpen(map,row, col + 1)) return false;
                        row--;
                    }
                    currentError -= 1.0f;
                }
            }
        }
        else{
            for (int col = sCol; col >= eCol; col--){
                if (isNotOpen(map,row, col)) return false;
                currentError += delta;
                while (currentError > 0.5f){
                    if (rowLength > 0){
                        if (isNotOpen(map,row + 1, col) || isNotOpen(map,row, col - 1)) return false;
                        row++;
                    }
                    else if (rowLength < 0){
                        if (isNotOpen(map,row - 1, col) || isNotOpen(map,row, col - 1)) return false;
                        row--;
                    }
                    currentError -= 1.0f;
                }
            }
        }
        return true;
    }
    
    private static boolean isNotOpen(int[][] map, int row, int col){
        return (!isOnMap(map, row, col) || map[row][col] != 0);
    }
    
     private static boolean isOnMap(int[][] map, int row, int col){
        return (row >= 0 && row < map.length && col >= 0 && col < map[row].length);
    }
}
