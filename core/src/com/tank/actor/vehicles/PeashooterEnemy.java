package com.tank.actor.vehicles;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.ListIterator;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.projectiles.AbstractProjectile;
import com.tank.actor.projectiles.Bullet;
import com.tank.actor.ui.MovingText;
import com.tank.game.Player;
import com.tank.media.MediaSound;
import com.tank.utils.Assets;
import com.tank.utils.lineofsight.LineOfSight;
import com.tank.utils.pathfinding.PathfindingUtil;
import com.tank.stage.Level;
import com.tank.stats.Stats;

public class PeashooterEnemy extends FixedTank {
	
	protected Vector2 targetPos = new Vector2(1500, 1500);
	protected LinkedList<Vector2> path;
	protected Thread pathfindingThread;
	protected int[] endTargetTile = new int[] {37, 37};
	protected boolean forwarding;
	protected boolean reversing;
	protected boolean randomTurnReverse;
	protected float reverseTime;
	protected float reverseTimeThreshold;
	protected float reverseTimeChanges = 0.5f;
	
	protected float diagonalLength = 105;
	
	protected boolean patrolling;
	protected float timeSinceLastPathfind;
	
	protected boolean honeInMode;
	protected boolean attackMode;
	protected PlayerTank target;
	
	protected float distanceForTrack = 12f;
	protected float distanceForShoot = 6f;
	//neal was here
	protected float reloadTime = 3.8f;
	protected float gunLength = 118;
	protected float rotateThreshold = 6f;
	protected int onTileThreshold = 110;
	
	protected float cooldownLastShot;
	
	protected static MediaSound shoot_sound = new MediaSound(Assets.manager.get(Assets.bullet_fire), 0.5f);
	
	protected int expGive;
	
	
	public PeashooterEnemy(float x, float y, int level) {
		super(x, y, Assets.manager.get(Assets.enemy_peashooter_fixed));
		initializeStats();
		stats.mergeStats(levelStats(level));
		initializePathfinding();
		initializeHitbox();
		setRotation((float)(Math.random() * 360f));
		forwarding = false;
		reversing = false;
		randomTurnReverse = false;
		patrolling = true;
		reverseTime = 0;
		expGive = (int)Math.pow(1 + level, 1.1);
		
		reverseTimeThreshold = 0.5f;
		timeSinceLastPathfind = 0f;
		cooldownLastShot = 0.5f;
		
		mass = 150f;
		
		health = getMaxHealth();
	}
	
	protected void initializeStats() {
		stats.addStat("Damage", 35);
		stats.addStat("Spread", 40);
		stats.addStat("Accuracy", 50);
		stats.addStat("Stability", 50);
		stats.addStat("Max Bounce", 1);
		stats.addStat("Projectile Speed", 75);
		stats.addStat("Lifetime", 50);
		stats.addStat("Fire Rate", 30);
		stats.addStat("Max Projectile", 6);
		
		stats.addStat("Max Health", 60);
		stats.addStat("Armor", 15);
		
		stats.addStat("Traction", 100);
		stats.addStat("Acceleration", 120);
		stats.addStat("Angular Acceleration", 120);
		
		stats.addStat("Projectile Durability", 1);
	}
	
	protected Stats levelStats(int levelNum) {
		Stats levelUps = new Stats();
		
		levelUps.addStat("Damage", (int)(1.5 * Math.pow(levelNum - 1, 1.2)));
		levelUps.addStat("Spread", (int)(3.2 * Math.pow(levelNum - 1, 0.5)));
		levelUps.addStat("Accuracy", (int)(4.5 * Math.pow(levelNum - 1, 0.8)));
		levelUps.addStat("Stability", (int)(4 * Math.pow(levelNum - 1, 0.3)));
		levelUps.addStat("Max Bounce", (int)(0.3 * Math.pow(levelNum - 1, 0.3)));
		levelUps.addStat("Lifetime", (int)(2.5 * Math.pow(levelNum - 1, 0.5)));
		levelUps.addStat("Fire Rate", (int)(2 * Math.pow(levelNum - 1, 0.6)));
		
		levelUps.addStat("Max Health", (int)(4 * Math.pow(levelNum - 1, 1.1)));
		levelUps.addStat("Armor", (int)(1 * Math.pow(levelNum - 1, 0.9)));
		
		levelUps.addStat("Projectile Durability", (int)(0.4 * Math.pow(levelNum - 1, 0.5)));
		
		return levelUps;
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
		if(isDestroyed()) return;
		if (patrolling) {
			patrolMode(delta);
		}
		else if (honeInMode && target != null && !target.isDestroyed()) {
			honeInMode(delta);
		}
		else if (attackMode && target != null && !target.isDestroyed()) {
			attackMode(delta);
		}
		else {
			patrolling = true;
			selectNewEndTargetTile();
			requestPathfinding();
		}
		
		super.applyFriction(delta);
		if (!super.move(delta)){
			if (attackMode && cooldownLastShot > 0f) {
				
			}
			else if (!reversing && !forwarding && reverseTime >= reverseTimeChanges * 2) {
				reversing = true;
				forwarding = true;
				if (reverseTime < reverseTimeChanges * 5) {
					reverseTimeThreshold += reverseTimeChanges;
					if (reverseTimeThreshold >= reverseTimeChanges * 3) {
						randomTurnReverse = (Math.random() < 0.5);
					}
				}
				else reverseTimeThreshold = 0.5f;
				reverseTime = 0f;
			}
			else if (!reversing && !forwarding) {
				reverseTime += delta;
			}
		}
		else{
			reverseTime += delta;
		}
		if (cooldownLastShot > 0f) cooldownLastShot -= delta;
	}
	
	public void patrolMode(float delta) {
		//Request pathfinding after a certain amount of time not pathfinding
		timeSinceLastPathfind += delta;
		if (timeSinceLastPathfind >= 20f) requestPathfinding();
		//Check if it's on the path (may happen if enemy overshoots tiles)
		if (isOnPath()) setNextTarget(path.removeFirst());
		//Finished patrolling to certain tile? Find something new
		if (path == null || path.isEmpty() || onTile(endTargetTile) || targetPos == null) {
			selectNewEndTargetTile();
		}
		//Any player nearby?
		float shortestDistance = -1f;
		for (Player player : getPlayers()) {
			PlayerTank playerTank = player.tank;
			if (playerTank != null && !playerTank.isDestroyed()) {
				float distance = getDistanceTo(playerTank);
				if (shortestDistance == -1f || distance < shortestDistance) {
					shortestDistance = distance;
					if (shortestDistance <= AbstractMapTile.SIZE * distanceForTrack) {
						target = playerTank;
						patrolling = false;
						honeInMode = true;
					}
				}
			}
		}
		
		//Moving based things
		moveByTargetTile(delta);
	}
	
	public void honeInMode(float delta) {
		if (isOnPath()) setNextTarget(path.removeFirst());
		float distanceToTarget = getDistanceTo(target);
		if (distanceToTarget <= AbstractMapTile.SIZE * distanceForTrack) {
			if (distanceToTarget >= AbstractMapTile.SIZE * distanceForShoot || !hasLineOfSight(target.getX(), target.getY())) {
				timeSinceLastPathfind += delta;
				if (timeSinceLastPathfind >= 20f) {
					endTargetTile = getTileAt(target.getX(), target.getY());
					requestPathfinding();
				}
				else if (path.isEmpty() || onTile(endTargetTile) || targetPos == null) {
					endTargetTile = getTileAt(target.getX(), target.getY());
					requestPathfinding();
				}
				moveByTargetTile(delta);
			}
			else {
				honeInMode = false;
				attackMode = true;
			}
		}
		else {
			target = null;
			honeInMode = false;
		}
	}
	
	public void attackMode(float delta) {
		float distanceToTarget = getDistanceTo(target);
		if (distanceToTarget < AbstractMapTile.SIZE * distanceForShoot && hasLineOfSight(target.getX(), target.getY())) {
			if (reversing) {
				backingUp(delta);
			}
			else if (forwarding) {
				accelerateForward(delta);
				reverseTime += delta;
				if (reverseTime >= reverseTimeChanges + (reverseTimeThreshold / 3)) {
					forwarding = false;
					reverseTime = -delta;
				}
				
			}
			else if (!rotateTowardsTarget(delta, target.getX(), target.getY())) {
				if (cooldownLastShot <= 0f) {
					shoot();
					int fireRate = stats.getStatValue("Fire Rate");
					cooldownLastShot = reloadTime * (1.0f - ((float)(fireRate) / (fireRate + 60)));
				}
			}
		}
		else {
			if (distanceToTarget <= AbstractMapTile.SIZE * distanceForTrack) {
				attackMode = false;
				honeInMode = true;
				endTargetTile = getTileAt(target.getX(), target.getY());
				requestPathfinding();
			}
			else {
				attackMode = false;
				patrolling = true;
				selectNewEndTargetTile();
				requestPathfinding();
			}
		}
	}
	
	public void moveByTargetTile(float delta) {
		if (targetPos != null && !reversing && !forwarding) {
			moveToTarget(delta);
		}
		else {
			if (reversing) {
				backingUp(delta);
			}
			else if (forwarding) {
				accelerateForward(delta);
				reverseTime += delta;
				if (reverseTime >= reverseTimeChanges + (reverseTimeThreshold / 3)) {
					forwarding = false;
					reverseTime = -delta;
				}
				
			}
			else if (targetPos == null) {
				if (path != null && !path.isEmpty()) {
					Vector2 nextTile;
					do {
						nextTile = path.removeFirst();
					} while (!path.isEmpty() && (onTile((int)nextTile.x, (int)nextTile.y)));
					synchronized (path) {
						setNextTarget(nextTile);
					}
				}
			}
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
		if (rotationDifference > rotateThreshold) direction = 1;
		else if (rotationDifference < -rotateThreshold) direction = -1;
		
		int moveForward = 0;
		if (!(onTile(getTileAt(targetPos.x, targetPos.y)))) {
			if (Math.abs(rotationDifference) < rotateThreshold * 2.5f) moveForward = 1;
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
		if (direction == 1) turnLeft(delta); else if (direction == -1) turnRight(delta);
		if (moveForward == 1) accelerateForward(delta);
		
	}
	
	public boolean rotateTowardsTarget(float delta, float x, float y) {
		float targetRotation = (float) Math.toDegrees(Math.atan2((y - getY()), (x - getX())));
		float rotationDifference = targetRotation - getRotation();
		while (rotationDifference < -180f) {
			rotationDifference += 360f;
		}
		while (rotationDifference > 180f) {
			rotationDifference -= 360f;
		}
		int direction = 0;
		if (rotationDifference > rotateThreshold) direction = 1;
		else if (rotationDifference < -rotateThreshold) direction = -1;
		
		if (direction == 1) turnLeft(delta); else if (direction == -1) turnRight(delta);
		return (direction != 0);
	}
	
	public void shoot() {
		Vector2 v = new Vector2(gunLength, 0);
		float randomAngle = randomShootAngle();
		v.setAngle(getRotation());
		getStage().addActor(new Bullet(this, createBulletStats(), getX() + v.x, getY() + v.y, getRotation() + randomAngle));
		shoot_sound.play();
	}
	
	public void backingUp(float delta) {
		accelerateBackward(delta);
		if (reverseTimeThreshold >= reverseTimeChanges * 3) {
			if (randomTurnReverse) 
				turnLeft(delta);
			else
				turnRight(delta);
		}
		reverseTime += delta;
		if (reverseTime >= reverseTimeThreshold) {
			reversing = false;
			if (reverseTimeThreshold >= reverseTimeChanges * 5) {
				if (patrolling) {
					selectNewEndTargetTile();
					requestPathfinding();
				}
				if (honeInMode) {
					requestPathfinding();
				}
				forwarding = true;
			}
			else if(reverseTime >= reverseTimeChanges * 3) {
				if (patrolling) {
					selectNewEndTargetTile();
					requestPathfinding();
					forwarding = true;
				}
				if (honeInMode) {
					requestPathfinding();
				}
			}
			reverseTime = -delta;
		}
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
					try {
						int[][] map = ((Level)getStage()).getMap().getLayout();
						int[] startPos = ((Level)getStage()).getMap().getTileAt(getX(), getY());
						path = PathfindingUtil.pathfinding(map, startPos[0], startPos[1], endTargetTile[0], endTargetTile[1]);
					}
					catch (Exception e) {
						
					}
				}
			};
			pathfindingThread.start();
		}
	}
	
	public boolean hasLineOfSight(float x, float y) {
		int[] currentTile = getTileAt(getX(), getY());
		int[] targetTile = getTileAt(x, y);
		int[][] map = ((Level)getStage()).getMap().getLayout();
		return LineOfSight.hasSight(map, currentTile[0], currentTile[1], targetTile[0], targetTile[1]);
	}
	
	public void setNextTarget(Vector2 rowCol) {
		float x = rowCol.y * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;	//center of tile
		float y = rowCol.x * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;
		targetPos = new Vector2(x, y);
	}
	
	public int[] getTileAt(float x, float y) {
		return ((Level)getStage()).getMap().getTileAt(x, y);
	}
	
	public ArrayList<Player> getPlayers(){
		return ((Level)getStage()).getGame().players;
	}
	
	public boolean onTile(int row, int col) {
		//int[] currentTile = getTileAt(getX(), getY());
		float x = col * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;	//center of tile
		float y = row * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;
		float difference = Math.max(Math.abs(getX() - x), Math.abs(getY() - y));
		return (difference <= onTileThreshold);
	}
	
	public boolean onTile(int[] rowCol) {
		//int[] currentTile = getTileAt(getX(), getY());
		float x = rowCol[1] * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;	//center of tile
		float y = rowCol[0] * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2;
		float difference = Math.max(Math.abs(getX() - x), Math.abs(getY() - y));
		return (difference <= onTileThreshold);
	}
	
	public float getDistanceTo(Actor other) {
		return (float)Math.sqrt(Math.pow(getX() - other.getX(), 2) + Math.pow(getY() - other.getY(), 2));
	}

	@Override
	protected void initializeHitbox() {
		hitbox = getHitboxAt(getX(), getY(), getRotation());
	}
	
	@Override
	/**
	 * From the Destructible interface. The damage method is used to handle the
	 * object getting hit.
	 * 
	 * @param source
	 *            The other actor that hit this object, in any
	 * @param damage
	 *            The damage dealt
	 */
	public void damage(Actor source, int damage) {
		if (damage > 0) {
			damage =  Math.max(1, damage - (int)Math.pow(getStatValue("Armor"), 0.8));
			health -= damage;
			if (getStage() != null)
				getStage().addActor(new MovingText("-" + damage, Color.RED, 1.5f, new Vector2(0, 200),
						getX() + (float)(100f * Math.random()) - 50f,
						getY() + (float)(100f * Math.random()) - 50f));
			if (health <= 0 && !isDestroyed()) {
				((Level)getStage()).changeEnemyCount(-1);
				destroy();
				if (source instanceof AbstractProjectile) {
					AbstractVehicle tankSource = ((AbstractProjectile)source).getSource();
					if (tankSource instanceof PlayerTank) {
						((PlayerTank)tankSource).gainExp(expGive);
					}
				}
				else {
					if (source instanceof PlayerTank) {
						((PlayerTank)source).gainExp(expGive);
					}
				}
			}
		}
	}

	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2((float) diagonalLength, 0);
		v.setAngle(direction);
		v.rotate(45);

		for (int i = 0; i < 4; i++) {
			f[i * 2] = x + v.x;
			f[i * 2 + 1] = y + v.y;
			v.rotate(90);
		}
		return new Polygon(f);
	}
	
	@Override
	public String getTeam() {
		return "ENEMY";
	}
	
	@Override
	public boolean remove() {
		if (super.remove()) {
			if (shoot_sound != null) shoot_sound.stop();
			return true;
		}
		return false;
	}
}
