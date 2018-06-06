package com.tank.actor.projectiles;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.interfaces.Collidable;
import com.tank.media.MediaSound;
import com.tank.stage.Level;
import com.tank.stats.Stats;
import com.tank.utils.Assets;
import com.tank.utils.CollisionEvent;

public class Moose extends AbstractProjectile{
	private static Animation<TextureRegion> mooseAnimation;
    private static Texture moose = Assets.manager.get(Assets.moose);
    private static final int FRAMES_ROWS = 6;
    private static final int FRAMES_COLS = 4;
    private static final int EMPTY_FRAMES = 3;
    private static final int FPS = 30;
    private static final int FRAME_WIDTH = moose.getWidth() / FRAMES_COLS;
    private static final int FRAME_HEIGHT = moose.getHeight() / FRAMES_ROWS;
	
	static {
		TextureRegion[][] textureRegions = TextureRegion.split(moose,
				moose.getWidth() / FRAMES_COLS,
				moose.getHeight() / FRAMES_ROWS);

        TextureRegion[] mooseFrames = new TextureRegion[FRAMES_COLS * FRAMES_ROWS - EMPTY_FRAMES];
        int index = 0;
        for (int i = 0; i < FRAMES_ROWS; i++) {
            for (int j = 0; j < FRAMES_COLS; j++) {
            	mooseFrames[index++] = textureRegions[i][j];
            	if (index == mooseFrames.length) {
            		i = FRAMES_ROWS;
            		j = FRAMES_COLS;
            	}
            }
        }

        mooseAnimation = new Animation<TextureRegion>(1.0f / FPS, mooseFrames);
        mooseAnimation.setPlayMode(PlayMode.LOOP);
	}
	
	private ArrayList<AbstractVehicle> vehiclesHit;
	private float angle;
	private float stateTime;
	private boolean hasRunThroughField;
	
	private static final float MOVING_VOLUME = 0.5f;
    private static final float MOO_VOLUME = 0.5f;
    private static final float HIT_VOLUME = 0.5f;
    private static MediaSound moveSound = new MediaSound(Assets.manager.get(Assets.moose_moving), MOVING_VOLUME);
    private static MediaSound mooSound = new MediaSound(Assets.manager.get(Assets.moose_moo), MOO_VOLUME);
    private static MediaSound hitSound = new MediaSound(Assets.manager.get(Assets.moose_hit), HIT_VOLUME);
	
	public Moose(AbstractVehicle src, Stats stats, float x, float y) {
		super(moose, src, stats, x, y);
		setOffscreen();
		Vector2 v = new Vector2(stats.getStatValue("Projectile Speed"), 0);
		velocity = v.setAngle((float) Math.toDegrees(Math.atan2((y - getY()), (x - getX()))));
		velocity.rotate((float)(Math.random() * 30) - 15f);
		setRotation(v.angle() - 90);
		setOrigin(FRAME_WIDTH / 2, FRAME_HEIGHT / 2);
		setWidth(72);
		setHeight(15);
		angle = (float)Math.toDegrees(Math.atan((double)getHeight()/getWidth()));
		hasRunThroughField = false;
		stateTime = (float)Math.random() * (21f / 30f);
		vehiclesHit = new ArrayList<AbstractVehicle>();
		//source.changeBulletCount(1);
		initializeHitbox();
	}
	
	@Override
	public void act(float delta) {
		stateTime += delta;
		
		if (!isDestroyed()) {
			if (hasRunThroughField) {
				if (isOffscreen(2)) {
					destroy();
					return;
				}
			}
			else {
				if (!isOffscreen(2)) hasRunThroughField = true;
				else if (isOffscreen(10)) {
					destroy();
					return;
				}
			}
			velocity.rotate(angularVelocity * delta);
			setRotation(getRotation() + delta * angularVelocity);
			setPosition(getX() + velocity.x * delta, getY() + velocity.y * delta);
			initializeHitbox();
			
			checkCollisions(getNeighbors());
			for (CollisionEvent e : collisions) {
				if (e.getCollidable() instanceof AbstractProjectile) {
					int durability = stats.getStatValue("Projectile Durability");
					int otherDurability = ((AbstractProjectile) e.getCollidable()).getStat("Projectile Durability");
					if (durability > otherDurability && !(e.getCollidable() instanceof DamageExplosion)) {
						((AbstractProjectile) e.getCollidable()).destroy();
						stats.addStat("Projectile Durability", durability - otherDurability);
						return;
					} else if (otherDurability > durability || e.getCollidable() instanceof DamageExplosion) {
						destroy();
						((AbstractProjectile) e.getCollidable()).setStat(otherDurability - durability,
								"Projectile Durability");
						return;
					} else {
						((AbstractProjectile) e.getCollidable()).destroy();
						destroy();
						return;
					}
				}
				if (e.getCollidable() instanceof AbstractVehicle && !vehiclesHit.contains((AbstractVehicle)e.getCollidable())) {
					((AbstractVehicle)e.getCollidable()).damage(this, stats.getStatValue("Damage"));
					((AbstractVehicle)e.getCollidable()).applySecondaryForce(getVelocity().cpy().scl(2));
					vehiclesHit.add((AbstractVehicle)e.getCollidable());
					hitSound.play();
				}
			}
		}
		
	}
	
	public boolean isOffscreen(int thresh) {
		int levelWidth = ((Level)getStage()).getMapWidth();
		int levelHeight = ((Level)getStage()).getMapHeight();
		int[] currentTile = ((Level)getStage()).getMap().getTileAt(getX(), getY());
		if (currentTile[0] < -thresh || currentTile[0] > levelHeight + thresh - 1) return true;
		if (currentTile[1] < -thresh || currentTile[1] > levelWidth + thresh - 1) return true;
		return false;
	}
	
	public void setOffscreen() {
		int levelWidth = ((Level)source.getStage()).getMapWidth();
		int levelHeight = ((Level)source.getStage()).getMapHeight();
		if (Math.random() < 0.5) {
			float x = (float)(Math.random() * AbstractMapTile.SIZE * (levelWidth + 3)) - (AbstractMapTile.SIZE * 2);
			setX(x);
			if (Math.random() < 0.5) {
				setY(-AbstractMapTile.SIZE * 2);
			}
			else {
				setY((levelHeight + 1) * AbstractMapTile.SIZE);
			}
		}
		else {
			float y = (float)(Math.random() * AbstractMapTile.SIZE * (levelHeight + 3)) - (AbstractMapTile.SIZE * 2);
			setY(y);
			if (Math.random() < 0.5) {
				setX(-AbstractMapTile.SIZE * 2);
			}
			else {
				setX((levelWidth + 1) * AbstractMapTile.SIZE);
			}
		}
	}
	
	@Override
	public ArrayList<Collidable> getNeighbors() {
		ArrayList<Collidable> neighbors = new ArrayList<Collidable>();
		for (AbstractVehicle v : AbstractVehicle.vehicleList) {
			boolean canCollide = !(v.getTeam() != null && getTeam() != null && getTeam().equals(v.getTeam()));
			if (canCollide)
				neighbors.add(v);
		}
		
		for (AbstractProjectile p : AbstractProjectile.projectileList) {
			boolean canCollide = !(p.getTeam() != null && getTeam() != null && getTeam().equals(p.getTeam()));
			if (canCollide)
				neighbors.add(p);
		}
		return neighbors;
	}
	
	@Override
	public void draw(Batch batch, float a) {
    	TextureRegion currentFrame = mooseAnimation.getKeyFrame(stateTime, true);
		batch.draw(currentFrame, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(), super.getOriginX(),
				super.getOriginY(), FRAME_WIDTH, FRAME_HEIGHT, super.getScaleX(), super.getScaleY(), getRotation());
    }
	
	@Override
	protected void initializeHitbox() {
		testHitbox = hitbox = getHitboxAt(getX(), getY(), getRotation());
	}

	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		float[] f = new float[8];
		Vector2 v = new Vector2(getWidth(), getHeight());
		v.setAngle(direction);
		v.rotate(angle);
		f[0] = x+ v.x;
		f[1] = y +v.y;
		v.rotate(180-2*angle);
		f[2] = x + v.x;
		f[3] = y + v.y;
		v.rotate(2*angle);
		f[4] = x + v.x;
		f[5] = y + v.y;
		v.rotate(180-2*angle);
		f[6] = x + v.x;
		f[7] = y + v.y;
		return new Polygon(f);
	}
}
