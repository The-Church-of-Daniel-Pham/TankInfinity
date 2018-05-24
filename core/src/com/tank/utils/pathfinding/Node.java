package com.tank.utils.pathfinding;

public class Node
{
    private int row;
    private int col;
    private double distance;
    private Node prev;
    
    public Node(int row, int col, double distance, Node prev){
        this.row = row;
        this.col = col;
        this.distance = distance;
        this.prev = prev;
    }
    
    public int getRow(){
        return row;
    }
    
    public int getCol(){
        return col;
    }
    
    public double getDistance(){
        return distance;
    }
    
    public void updateDistance(){
    }
    
    public Node getPrevious(){
        return prev;
    }
    
    public double getHeuristicDistance(Node end){
        return Math.sqrt(Math.pow(end.row - row, 2) + Math.pow(end.col - col, 2)) + distance;
    }
    
    public boolean equals(Object obj){
        return (obj instanceof Node && ((Node)obj).row == row && ((Node)obj).col == col);
    }
    
    public String toString(){
        return ("(R:" + row + ", C:" + col + ")");
    }
}

