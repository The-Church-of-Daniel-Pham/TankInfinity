package com.tank.actor.vehicles;

import java.util.LinkedList;
import java.util.ListIterator;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.utils.Assets;
import com.tank.utils.pathfinding.PathfindingUtil;
import com.tank.stage.Level;

public class BasicEnemy extends FixedTank {
	
	Vector2 targetPos = new Vector2(1500, 1500);
	LinkedList<Vector2> path;
	Thread pathfindingThread;
	int[] endTargetTile = new int[] {37, 37};
	boolean reversing;
	float reverseTime;
	float reverseTimeThreshold;
	
	boolean patrolling;
	float timeSinceLastPathfind;
	
	public BasicEnemy(float x, float y) {
		super(x, y, Assets.manager.get(Assets.tread_default));
		initializeStats();
		initializePathfinding();
		reversing = false;
		patrolling = true;
		reverseTime = 0;
		reverseTimeThreshold = 0.5f;
		timeSinceLastPathfind = 0f;
	}
	
	public void initializeStats() {
		stats.addStat("Friction", 96); // (fraction out of 100)^delta to scale velocity by
		stats.addStat("Acceleration", 1600);
		stats.addStat("Angular_Friction", 98);
		stats.addStat("Angular_Acceleration", 300);
		//stats.addStat("Rate_Of_Fire", 1);
	}
	
	public void initializePathfinding() {
		path = new LinkedList<Vector2>();
		pathfindingThread = new Thread() {
			@Override
			public void run() {
				int[][] map = ((Level)getStage()).getMap().getLayout();
				int[] startPos = ((Level)getStage()).getMap().getTileAt(getX(), getY());
				synchronized (path) {
					path = PathfindingUtil.pathfinding(map, startPos[0], startPos[1], endTargetTile[0], endTargetTile[1]);
				}
			}
		};
	}
	
	@Override
	public void act(float delta) {
		timeSinceLastPathfind += delta;
		if (timeSinceLastPathfind >= 20f) requestPathfinding();
		if (isOnPath()) setNextTarget(path.removeFirst());
		if (patrolling) {
			if (path.isEmpty() || onTile(endTargetTile) || targetPos == null) {
				selectNewEndTargetTile();
			}
			if (targetPos != null && !reversing) {
				moveToTarget(delta);
			}
			else {
				if (reversing) {
					super.applyForce(delta * stats.getStatValue("Acceleration"), 180 + getRotation());
					reverseTime += delta;
					if (reverseTime >= reverseTimeThreshold) {
						reversing = false;
						reverseTime = -delta;
					}
				}
				else if (targetPos == null) {
					if (path != null && !path.isEmpty()) {
						Vector2 nextTile;
						do {
							nextTile = path.removeFirst();
						} while (!path.isEmpty() && (onTile((int)nextTile.x, (int)nextTile.y)));
						synchronized (path) { setNextTarget(nextTile); }
					}
				}
			}
		}
		
		super.applyFriction(delta);
		if (!super.move(delta)){
			if (!reversing && reverseTime >= 1.0f) {
				reversing = true;
				if (reverseTime < 2.5f) reverseTimeThreshold += 0.5f; else reverseTimeThreshold = 0.5f;
				reverseTime = 0f;
			}
			else if (!reversing) {
				reverseTime += delta;
			}
		}
		else{
			reverseTime += delta;
		}
	}
	
	public void moveToTarget(float delta) {
		float targetRotation = (float) Math.toDegrees(Math.atan2((targetPos.y - getY()), (targetPos.x - getX())));
		float rotationDifference = targetRotation - getRotation();
		while (rotationDifference < -180f) {
			rotationDifference += 360f;
		}
		while (rotationDifference > 180f) {
			rotationDifference -= 360f;
		}
		int direction = 0;
		if (rotationDifference > 10) direction = 1;
		else if (rotationDifference < -10) direction = -1;
		
		int moveForward = 0;
		if (!(onTile(getTileAt(targetPos.x, targetPos.y)))) {
			if (Math.abs(rotationDifference) < 25f) moveForward = 1;
		}
		else {
			if (!(onTile(endTargetTile))) {
				if (path != null && !path.isEmpty()) {
					Vector2 nextTile;
					do {
						nextTile = path.removeFirst();
					} while (!path.isEmpty() && (onTile((int)nextTile.x, (int)nextTile.y)));
					synchronized (path) { setNextTarget(nextTile); }
				}
			}
			else {
				targetPos = null;
				direction = 0;
				moveForward = 0;
			}
		}
		//requestPathfinding();
		super.applyAngularForce(delta * stats.getStatValue("Angular_Acceleration") * direction);
		super.applyForce(delta * stats.getStatValue("Acceleration") * moveForward, getRotation());
	}
	
	public void selectNewEndTargetTile() {
		AbstractMapTile randomEmpty = ((Level)getStage()).getMap().getRandomFloorTile();
		endTargetTile[0] = randomEmpty.getRow();
		endTargetTile[1] = randomEmpty.getCol();
		requestPathfinding();
	}
	
	public boolean isOnPath() {
		if (path != null) {
			ListIterator<Vector2> iter = path.listIterator();
			while (iter.hasNext()) {
				Vector2 tile = iter.next();
				if (onTile((int)tile.x, (int)tile.y)) {
					//setNextTarget(tile);
					iter.previous();
					while (iter.hasPrevious()) {
						iter.previous();
						iter.remove();
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public void requestPathfinding() {
		timeSinceLastPathfind = 0;
		if (!pathfindingThread.isAlive() && endTargetTile != null) {
			pathfindingThread = new Thread() {
				@Override
				public void run() {
					int[][] map = ((Level)getStage()).getMap().getLayout();
					int[] startPos = ((Level)getStage()).getMap().getTileAt(getX(), getY());
					path = PathfindingUtil.pathfinding(map, startPos[0], startPos[1], endTargetTile[0], endTargetTile[1]);
				}
			};
			pathfindingThread.start();
		}
	}
	
	public void setNextTarget(Vector2 rowCol) {
		float x = rowCol.y * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;	//center of tile
		float y = rowCol.x * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;
		targetPos = new Vector2(x, y);
	}
	
	public int[] getTileAt(float x, float y) {
		return ((Level)getStage()).getMap().getTileAt(x, y);
	}
	
	public boolean onTile(int row, int col) {
		int[] currentTile = getTileAt(getX(), getY());
		return (currentTile[0] == row && currentTile[1] == col);
	}
	
	public boolean onTile(int[] rowCol) {
		int[] currentTile = getTileAt(getX(), getY());
		return (currentTile[0] == rowCol[0] && currentTile[1] == rowCol[1]);
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initializeHitbox() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2((float) (super.tankTexture.getWidth()) / 2, 0);
		v.setAngle(direction);
		v.rotate(45);

		for (int i = 0; i < 4; i++) {
			f[i * 2] = x + v.x;
			f[i * 2 + 1] = y + v.y;
			v.rotate(90);
		}
		return new Polygon(f);
	}
}
