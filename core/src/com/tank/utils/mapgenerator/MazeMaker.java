package com.tank.utils.mapgenerator;

/**
 * @author Daniel Pham
 * @version April 10th 2018
 * 
 * Description: The MazeMaker class is used to create a map for the Tank Game. It uses a modified algorithm of recursive depth-first
 * maze making. To use, create an object with a given rows and columns, call the createMaze method, then call the getMaze method
 * to return the map in an integer array of 0 (open) and 1 (wall)
 */

import java.util.*;

public class MazeMaker {
	private int[][] maze;		//The map
	ArrayList<MazeCell> path;		//An array list to keep track of where the pointer is and its path. This is in replacement of recursion.

	/**
	 * The MazeMaker constructor creates a map with the given size. It's initially all walls, you must call the createMaze method later
	 * to make the map.
	 * 
	 * @param rows		The amount of rows
	 * @param cols		The amount of cols
	 */
	public MazeMaker(int rows, int cols) {
		maze = new int[rows][cols];
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c < cols; c++) {
				maze[r][c] = 1;
			}
		}
	}

	/**
	 * Gives the maze in an integer array
	 * @return	The map as an integer array
	 */
	public int[][] getMaze() {
		return maze;
	}

	/**
	 * The createMaze method is used to create the maze using the algorithm. The two parameters is the point where to start
	 * building the maze. If the parameter is out the maze, it defaults starting off at 0, 0.
	 * @param sx		The start x
	 * @param sy		The start y
	 * 
	 * Postcondition: The map is generated
	 */
	public void createMaze(int sx, int sy) {
		path = new ArrayList<MazeCell>();		//Initialize the path list
		try {
			maze[sy][sx] = 0;			//Open the start cell
			path.add(new MazeCell(sx, sy, randomDirections()));
		}
		catch (IndexOutOfBoundsException e) {
			maze[0][0] = 0;				//Open the 0, 0 cell if the initial start parameters failed
			path.add(new MazeCell(0, 0, randomDirections()));
		}

		//As long as there is a path, build the maze
		while (path.size() > 0) {
			boolean cellFinished = false;			//This boolen is used to tell if the delete the cell or not at the end
			int x = path.get(path.size() - 1).x;	//Get the x position of the head of the path
			int y = path.get(path.size() - 1).y;	//Get the y position of the head of the path
			//Get the next direction for that path to check
			switch (path.get(path.size() - 1).nextDirection()) {
			case 0:
				//Check east
				if (exists(x + 1, y)) {
					//If the cell exists, the cell is a wall, and the three blocks after that cell are all walls, open the wall
					//And add this cell to the existing path
					if (maze[y][x + 1] == 1 && afterOpen(x, y, 'e') == 0) {
						maze[y][x + 1] = 0;
						path.get(path.size() - 1).deadEnd = false;	//Since we found an open space, this current cell is not a dead end
						path.add(new MazeCell(x + 1, y, randomDirections()));
					}
				}
				break;
			case 1:
				//Check west
				if (exists(x - 1, y)) {
					//If the cell exists, the cell is a wall, and the three blocks after that cell are all walls, open the wall
					//And add this cell to the existing path
					if (maze[y][x - 1] == 1 && afterOpen(x, y, 'w') == 0) {
						maze[y][x - 1] = 0;
						path.get(path.size() - 1).deadEnd = false;	//Since we found an open space, this current cell is not a dead end
						path.add(new MazeCell(x - 1, y, randomDirections()));
					}
				}
				break;
			case 2:
				//Check south
				if (exists(x, y + 1)) {
					//If the cell exists, the cell is a wall, and the three blocks after that cell are all walls, open the wall
					//And add this cell to the existing path
					if (maze[y + 1][x] == 1 && afterOpen(x, y, 's') == 0) {
						maze[y + 1][x] = 0;
						path.get(path.size() - 1).deadEnd = false;	//Since we found an open space, this current cell is not a dead end
						path.add(new MazeCell(x, y + 1, randomDirections()));
					}
				}
				break;
			case 3:
				//Check north
				if (exists(x, y - 1)) {
					//If the cell exists, the cell is a wall, and the three blocks after that cell are all walls, open the wall
					//And add this cell to the existing path
					if (maze[y - 1][x] == 1 && afterOpen(x, y, 'n') == 0) {
						maze[y - 1][x] = 0;
						path.get(path.size() - 1).deadEnd = false;	//Since we found an open space, this current cell is not a dead end
						path.add(new MazeCell(x, y - 1, randomDirections()));
					}
				}
				break;
			case -1:
				//Checked all directions already set the cell as finished
				cellFinished = true;
				break;
			}

			//If the cell is a dead end and this cell is finished, start random breaking for loops
			if ((path.get(path.size() - 1).deadEnd || path.size() == 1) && cellFinished) {
				int randomBreaking = (int) (Math.random() * nearbyClose(x, y));	//Find where to break
				if (exists(x + 1, y)) {
					//Try breaking east
					if (maze[y][x + 1] != 0) {
						if (randomBreaking == 0) {
							maze[y][x + 1] = 0;
							if (exists(x + 2, y))
								maze[y][x + 2] = 0;
						}
						randomBreaking--;
					}
				}
				if (exists(x, y + 1)) {
					//Try breaking south
					if (maze[y + 1][x] != 0) {
						if (randomBreaking == 0) {
							maze[y + 1][x] = 0;
							if (exists(x, y + 2))
								maze[y + 2][x] = 0;
						}
						randomBreaking--;
					}
				}
				if (exists(x - 1, y)) {
					//Try breaking west
					if (maze[y][x - 1] != 0) {
						if (randomBreaking == 0) {
							maze[y][x - 1] = 0;
							if (exists(x - 2, y))
								maze[y][x - 2] = 0;
						}
						randomBreaking--;
					}
				}
				if (exists(x, y - 1)) {
					//Try breaking north
					if (maze[y - 1][x] != 0) {
						if (randomBreaking == 0) {
							maze[y - 1][x] = 0;
							if (exists(x, y - 2))
								maze[y - 2][x] = 0;
						}
						randomBreaking--;
					}
				}
			}
			if (cellFinished)
				path.remove(path.size() - 1);	//Remove the last cell if it is finished checking
		}

		doorMaker(0.25);		//Create "doors" with a 25% chance

		endRemover(0.45);		//Remove end blocks of a wall chain wiht a 45% chance

		//For three times...
		for (int i = 0; i < 3; i++) {
			edgeHedge(0.5);		//Create edge blocks with a 50% chance
		}

		plugHoles();		//Plug all unreachable holes

		cornerRemover();	//Remove "corners"
		
		loneBlockRemover();
		
		clearBottomLeftCorner(5);	//Clears out corner so tank doesn't spawn on bricks
	}
	
	private void clearBottomLeftCorner(int size) {
		for (int row = maze.length-size; row < maze.length; row++)	// bottom 'size' rows
		{
			for(int col = 0; col < size; col++)	// left 'size' columns
			{
				maze[row][col] = 0;
			}
		}
	}

	/**
	 * The cornerRemover removes "corners" from the map. A "corner" is defined as two wall blocks directly diagonal from each other
	 * without any other wall connecting them to each other. These two formations define corners:
	 * 0 1			OR			1 0
	 * 1 0						0 1
	 * The cornerRemover will change these as all open spaces.
	 */
	private void cornerRemover() {
		for (int y = 0; y < maze.length - 1; y++) {
			for (int x = 0; x < maze[y].length - 1; x++) {
				if (maze[y + 1][x] != maze[y][x] && maze[y][x + 1] != maze[y][x] && maze[y + 1][x + 1] == maze[y][x]) {
					maze[y][x] = maze[y + 1][x] = maze[y][x + 1] = maze[y + 1][x + 1] = 0;
					x = y = 0;
				}
			}
		}
	}

	/**
	 * The endRemover slices off wall blocks at the end of wall chains. Specifically, it searches to remove any wall block that
	 * has three or more open spaces next to it with a probability.
	 * @param p		The probability to remove an end
	 */
	private void endRemover(double p) {
		for (int y = 0; y < maze.length; y++) {
			for (int x = 0; x < maze[y].length; x++) {
				if (exists(x, y)) {
					if (nearbyOpen(x, y) >= 3 && maze[y][x] == 1) {
						if (Math.random() < p)
							maze[y][x] = 2;
					}
				}
			}
		}

		for (int y = 0; y < maze.length; y++) {
			for (int x = 0; x < maze[y].length; x++) {
				if (maze[y][x] == 2) {
					maze[y][x] = 0;
				}
			}
		}
	}

	/**
	 * The doorMaker makes openings in wall chains. Specifically, it searches to remove any wall block that has exactly
	 * two wall blocks next to it.
	 * @param p		The probability to make a door on a wall
	 */
	private void doorMaker(double p) {
		for (int y = 0; y < maze.length; y++) {
			for (int x = 0; x < maze[y].length; x++) {
				if (exists(x, y)) {
					if (nearbyOpen(x, y) == 2 && maze[y][x] == 1) {
						if (Math.random() < p)
							maze[y][x] = 2;
					}
				}
			}
		}

		for (int y = 0; y < maze.length; y++) {
			for (int x = 0; x < maze[y].length; x++) {
				if (maze[y][x] == 2) {
					maze[y][x] = 0;
				}
			}
		}
	}

	/**
	 * The edgeHedge method attempts to add wall blocks to the edges of the map. It only tries to add wall blocks to open spaces that
	 * has at least one other wall block next to it.
	 * @param p		The probability to add a wall block
	 */
	private void edgeHedge(double p) {
		for (int y = 0; y < maze.length; y++) {
			if (exists(0, y) && maze[y][0] != 1 && nearbyClose(0, y) >= 1 && Math.random() < p) {
				maze[y][0] = 3;
			}
			if (exists(maze[0].length - 1, y) && maze[y][maze[0].length - 1] != 1
					&& nearbyClose(maze[0].length - 1, y) >= 1 && Math.random() < p) {
				maze[y][maze[0].length - 1] = 3;
			}
		}
		for (int x = 0; x < maze[0].length; x++) {
			if (exists(x, 0) && maze[0][x] != 1 && nearbyClose(x, 0) >= 1 && Math.random() < p) {
				maze[0][x] = 3;
			}
			if (exists(x, maze.length - 1) && maze[maze.length - 1][x] != 1 && nearbyClose(x, maze.length - 1) >= 1
					&& Math.random() < p) {
				maze[maze.length - 1][x] = 3;
			}
		}

		for (int y = 0; y < maze.length; y++) {
			if (maze[y][0] == 3) {
				maze[y][0] = 1;
			}
			if (maze[y][maze[0].length - 1] == 3) {
				maze[y][maze[0].length - 1] = 1;
			}
		}
		for (int x = 0; x < maze[0].length; x++) {
			if (maze[0][x] == 3) {
				maze[0][x] = 1;
			}
			if (maze[maze.length - 1][x] == 3) {
				maze[maze.length - 1][x] = 1;
			}
		}
	}

	/**
	 * The plugHoles method looks to add walls into any holes. It first determines all seperate sections, then it fills wall blocks
	 * to the all sections except the biggest one.
	 */
	public void plugHoles() {
		ArrayList<MazeCell> openCells = new ArrayList<MazeCell>();
		for (int y = 0; y < maze.length; y++) {
			for (int x = 0; x < maze[y].length; x++) {
				if (maze[y][x] == 0) {
					openCells.add(new MazeCell(x, y));
				}
			}
		}

		ArrayList<ArrayList<MazeCell>> openSections = new ArrayList<ArrayList<MazeCell>>();
		ArrayList<MazeCell> unchecked = new ArrayList<MazeCell>();
		while (openCells.size() > 0) {
			openSections.add(new ArrayList<MazeCell>());
			unchecked.add(openCells.remove((int) (Math.random() * openCells.size())));
			int section = openSections.size() - 1;
			while (unchecked.size() > 0) {
				MazeCell cellChecking = unchecked.remove(0);
				MazeCell northCell = new MazeCell(cellChecking.x, cellChecking.y - 1);
				MazeCell southCell = new MazeCell(cellChecking.x, cellChecking.y + 1);
				MazeCell eastCell = new MazeCell(cellChecking.x + 1, cellChecking.y);
				MazeCell westCell = new MazeCell(cellChecking.x - 1, cellChecking.y);

				if (openCells.remove(northCell))
					unchecked.add(northCell);
				if (openCells.remove(southCell))
					unchecked.add(southCell);
				if (openCells.remove(eastCell))
					unchecked.add(eastCell);
				if (openCells.remove(westCell))
					unchecked.add(westCell);

				openSections.get(section).add(cellChecking);
			}
		}

		int largestIndex = 0;
		int largestSize = openSections.get(0).size();

		for (int i = 1; i < openSections.size(); i++) {
			if (openSections.get(i).size() > largestSize) {
				largestIndex = i;
				largestSize = openSections.get(i).size();
			}
		}

		for (int i = 0; i < openSections.size(); i++) {
			if (i != largestIndex) {
				for (MazeCell cell : openSections.get(i)) {
					maze[cell.y][cell.x] = 1;
				}
			}
		}
	}

	/**
	 * The randomDirections method is used to help in deciding which directions to check on a cell
	 * @return	An integer array for the cell to check in that order
	 */
	private int[] randomDirections() {
		ArrayList<Integer> directions = new ArrayList<Integer>();
		for (int i = 0; i < 4; i++) {
			directions.add(i);
		}

		int[] random = new int[4];
		for (int i = 0; i < 4; i++) {
			random[i] = directions.remove((int) (Math.random() * directions.size()));
		}
		return random;
	}

	/**
	 * The nearbyOpen method is used to count how spaces are open next to a given cell
	 * 
	 * @param x			The x position of the cell to check
	 * @param y			The y position of the cell to check
	 * @return			The amount of open spaces around that cell in the 4 cardinal directions
	 */
	private int nearbyOpen(int x, int y) {
		int count = 0;
		if (exists(x + 1, y))
			if (maze[y][x + 1] == 0)
				count++;
		if (exists(x, y + 1))
			if (maze[y + 1][x] == 0)
				count++;
		if (exists(x - 1, y))
			if (maze[y][x - 1] == 0)
				count++;
		if (exists(x, y - 1))
			if (maze[y - 1][x] == 0)
				count++;
		return count;
	}
	/**
	 * The nearbyOpen method is used to count how spaces are walls next to a given cell
	 * 
	 * @param x			The x position of the cell to check
	 * @param y			The y position of the cell to check
	 * @return			The amount of walls around that cell in the 4 cardinal directions
	 */
	private int nearbyClose(int x, int y) {
		int count = 0;
		if (exists(x + 1, y))
			if (maze[y][x + 1] != 0 && maze[y][x + 1] != 3)
				count++;
		if (exists(x, y + 1))
			if (maze[y + 1][x] != 0 && maze[y + 1][x] != 3)
				count++;
		if (exists(x - 1, y))
			if (maze[y][x - 1] != 0 && maze[y][x - 1] != 3)
				count++;
		if (exists(x, y - 1))
			if (maze[y - 1][x] != 0 && maze[y - 1][x] != 3)
				count++;
		return count;
	}

	/**
	 * The afterOpen method is used to count how many open spaces there are in the three spaces after a given cell in that direction
	 * 
	 * @param x		The x position of the cell to check
	 * @param y		The y position of the cell to check
	 * @param d		The direction to check in
	 * @return		The amount of open spaces in the three blocks after a given cell
	 */
	private int afterOpen(int x, int y, char d) {
		int count = 0;
		switch (d) {
		case 'n':
			if (exists(x, y - 1)) {
				for (int x1 = x - 1; x1 <= x + 1; x1++) {
					if (exists(x1, y - 2))
						if (maze[y - 2][x1] == 0)
							count++;
				}
			}
			break;
		case 's':
			if (exists(x, y + 1)) {
				for (int x1 = x - 1; x1 <= x + 1; x1++) {
					if (exists(x1, y + 2))
						if (maze[y + 2][x1] == 0)
							count++;
				}
			}
			break;
		case 'e':
			if (exists(x + 1, y)) {
				for (int y1 = y - 1; y1 <= y + 1; y1++) {
					if (exists(x + 2, y1))
						if (maze[y1][x + 2] == 0)
							count++;
				}
			}
			break;
		case 'w':
			if (exists(x - 1, y)) {
				for (int y1 = y - 1; y1 <= y + 1; y1++) {
					if (exists(x - 2, y1))
						if (maze[y1][x - 2] == 0)
							count++;
				}
			}
			break;
		}
		return count;
	}
	
	private void loneBlockRemover(){
        for (int y = 0; y < maze.length; y++){
            for (int x = 0; x < maze[y].length; x++){
                if (exists(x, y)){
                    if (nearbyOpen(x, y) == 4){
                        maze[y][x] = 0;
                    }
                }
            }
        }
	}

	/**
	 * The exists method is used to check if a given cell is inside the bounds of the map
	 * @param x		The x position of the cell
	 * @param y		The y position of the cell
	 * @return		true if the cell is on the map, false if it is not
	 */
	private boolean exists(int x, int y) {
		return ((x >= 0 && x < maze[0].length) && (y >= 0 && y < maze.length));
	}
}
