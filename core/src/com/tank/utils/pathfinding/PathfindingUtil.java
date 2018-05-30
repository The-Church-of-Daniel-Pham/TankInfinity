package com.tank.utils.pathfinding;

import java.util.*;

import com.badlogic.gdx.math.Vector2;
public class PathfindingUtil
{
    public static LinkedList<Vector2> pathfinding(int[][] map, int sRow, int sCol, int eRow, int eCol){
        ArrayList<Node> queue = new ArrayList<Node>();
        ArrayList<Node> checked = new ArrayList<Node>();
        LinkedList<Vector2> path = null;
        Node start = new Node(sRow, sCol, 0, null);
        Node end = new Node(eRow, eCol, 0, null);
        queue.add(start);
        boolean found = false;

        while(queue.size() > 0 && !found){
            Node checking = selectNode(queue, end);
            if (checking.equals(end)){
                found = true;
                path = new LinkedList<Vector2>();
                path.addFirst(new Vector2(checking.getRow(), checking.getCol()));
                while(checking.getPrevious() != null){
                    checking = checking.getPrevious();
                    path.addFirst(new Vector2(checking.getRow(), checking.getCol()));
                }
            }
            else{
                queue.remove(checking);
                checkAdjacentNodes(map, queue, checked, checking);
                checked.add(checking);
            }
        }

        return path;
    }

    private static void checkAdjacentNodes(int[][] map, ArrayList<Node> queue, ArrayList<Node> checked, Node checking){
        int[] rowChanges = {0, 1, 0, -1};
        int[] colChanges = {1, 0, -1, 0};

        for (int i = 0; i < 4; i++){
            int row = checking.getRow() + rowChanges[i];
            int col = checking.getCol() + colChanges[i];
            if (isOpenSpace(map, row, col)){
                Node node = new Node(row, col, checking.getDistance() + 1, checking);
                if (!checked.contains(node)){
                    int index = queue.indexOf(node);
                    if (index != -1){
                        if (node.getDistance() < queue.get(index).getDistance()){
                            queue.set(index, node);
                        }
                    }
                    else{
                        queue.add(node);
                    }
                }
            }
        }

        int[] diagRowChanges = {1, 1, -1, -1};
        int[] diagColChanges = {1, -1, 1, -1};
        for (int i = 0; i < 4; i++){
            int row = checking.getRow() + diagRowChanges[i];
            int col = checking.getCol() + diagColChanges[i];
            if (isOpenSpace(map, row, col) && isOpenSpace(map, checking.getRow(), col) && isOpenSpace(map, row, checking.getCol())){
                Node node = new Node(row, col, checking.getDistance() + Math.sqrt(2), checking);
                if (!checked.contains(node)){
                    int index = queue.indexOf(node);
                    if (index != -1){
                        if (node.getDistance() < queue.get(index).getDistance()){
                            queue.set(index, node);
                        }
                    }
                    else{
                        queue.add(node);
                    }
                }
            }
        }
    }

    private static boolean isOpenSpace(int[][] map, int row, int col){
        return (isOnMap(map, row, col) && map[row][col] == 0);
    }

    private static boolean isOnMap(int[][] map, int row, int col){
        return (row >= 0 && row < map.length && col >= 0 && col < map[row].length);
    }

    private static Node selectNode(ArrayList<Node> queue, Node end){
        Node best = null;
        double distance = 0;
        for (Node node : queue){
            if (best == null || node.getHeuristicDistance(end) < distance){
                best = node;
                distance = node.getHeuristicDistance(end);
            }
        }
        return best;
    }
}

