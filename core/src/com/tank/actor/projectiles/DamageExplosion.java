package com.tank.actor.projectiles;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.map.tiles.BorderTile;
import com.tank.actor.map.tiles.WallTile;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.interfaces.Collidable;
import com.tank.media.MediaSound;
import com.tank.stage.Level;
import com.tank.stats.Stats;
import com.tank.utils.Assets;
import com.tank.utils.CollisionEvent;

public class DamageExplosion extends AbstractProjectile{
	static int normalSize = 512;
	private int startingSize;
	private int endSize;
	private int currentSize;
	private float lifeTime;
	private float maxLifeTime;
	private boolean finished;
	private ArrayList<AbstractVehicle> vehiclesHit;
	
	private static final float EXPLODE_VOLUME = 0.5f;
    private static MediaSound explosionSound = new MediaSound(Assets.manager.get(Assets.explosion_damage_sound), EXPLODE_VOLUME);
	
	private static Animation<TextureRegion> damageExplosionAnimation;
	static Texture damage_explosion = Assets.manager.get(Assets.damage_explosion);
	private static final int FRAMES_ROWS = 12;
    private static final int FRAMES_COLS = 10;
    private static final int FPS = 30;
    private static final int FRAME_WIDTH = damage_explosion.getWidth() / FRAMES_COLS;
    private static final int FRAME_HEIGHT = damage_explosion.getHeight() / FRAMES_ROWS;
	
	static {
		TextureRegion[][] textureRegions = TextureRegion.split(damage_explosion,
				damage_explosion.getWidth() / FRAMES_COLS,
				damage_explosion.getHeight() / FRAMES_ROWS);

        TextureRegion[] explosionFrames = new TextureRegion[FRAMES_COLS * FRAMES_ROWS];
        int index = 0;
        for (int i = 0; i < FRAMES_ROWS; i++) {
            for (int j = 0; j < FRAMES_COLS; j++) {
                explosionFrames[index++] = textureRegions[i][j];
            }
        }

        damageExplosionAnimation = new Animation<TextureRegion>(1.0f / FPS, explosionFrames);
	}
	
	public DamageExplosion(AbstractVehicle src, Stats stats, float x, float y) {
		super(damage_explosion, src, stats, x, y);
		startingSize = 1;
		currentSize = 1;
		endSize = stats.getStatValue("Explosion Size");
		setScale(((float)endSize / (float)normalSize) * 1.1f);
		maxLifeTime = stats.getStatValue("Lifetime") / 10.0f;
		lifeTime = 0f;
		setOriginX(FRAME_WIDTH / 2);
		setOriginY(FRAME_HEIGHT / 2);
		vehiclesHit = new ArrayList<AbstractVehicle>();
		explosionSound.play();
		
		// 1/fps, where col*row = total frames, maxlifetime is time alloted to finish animation
		//damageExplosionAnimation.setFrameDuration(1 / (FRAMES_COLS * FRAMES_ROWS / maxLifeTime));
	}
	
	public void act(float delta) {
		lifeTime += delta;
		currentSize = (int)(Math.pow(lifeTime / maxLifeTime, 0.3) * (endSize - startingSize)) + startingSize;
		if (lifeTime >= maxLifeTime + (maxLifeTime / 3.0f)) {
			destroy();
			return;
		}
		else {
			if (lifeTime < maxLifeTime) {
				initializeHitbox();
				explode();
			}
			else {
				if (!finished) {
					AbstractProjectile.projectileList.remove(this);
					finished = true;
				}
			}
		}
	}
	
	@Override
	public void draw(Batch batch, float a) {
//		int frame = (int)(currentSize / 10) % 15;
//		batch.setColor(NumberUtils.intToFloatColor(Color.toIntBits(255, 255, 255, (int)(192 * (1.0 - ((double)currentSize / endSize)) + 64))));
//		batch.draw(tex, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(), super.getOriginX(),
//				super.getOriginY(), 128, 128, super.getScaleX(), super.getScaleY(),
//				0, 128 * frame, 0, 128, tex.getHeight(), false, false);
//		batch.setColor(NumberUtils.intToFloatColor(Color.toIntBits(255, 255, 255, 255)));
		//drawVertices(batch, a);
		
		TextureRegion currentFrame = damageExplosionAnimation.getKeyFrame(lifeTime * (3.0f / maxLifeTime), false);
		batch.draw(currentFrame, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(), super.getOriginX(),
				super.getOriginY(), FRAME_WIDTH, FRAME_HEIGHT, super.getScaleX(), super.getScaleY(), getRotation());
		//drawVertices(batch, a);
	}
	
	@Override
	public int getStat(String stat) {
		if (stat.equals("Projectile Durability")) return 1;
		return super.getStat(stat);
	}

	public void explode() {
		checkCollisions(getNeighbors());
		for(CollisionEvent e: collisions) {
			if(e.getCollidable() instanceof AbstractVehicle && !vehiclesHit.contains((AbstractVehicle)e.getCollidable())) {
				float tankX = ((AbstractVehicle)e.getCollidable()).getX();
				float tankY = ((AbstractVehicle)e.getCollidable()).getY();
				float direction =  (float) Math.toDegrees(Math.atan2((tankY - getY()), (tankX - getX())));
				Vector2 knockback = new Vector2(endSize * 4 * (float)(1.0 - ((double)currentSize / endSize)), 0);
				knockback.setAngle(direction);
				((AbstractVehicle)e.getCollidable()).applySecondaryForce(knockback);
				vehiclesHit.add((AbstractVehicle)e.getCollidable());
				((AbstractVehicle)e.getCollidable()).damage(this, (int)Math.min(stats.getStatValue("Damage") * (1.35 - 1.1 * ((double)currentSize / endSize)), stats.getStatValue("Damage")));
			}
			if (lifeTime < maxLifeTime * 0.5f) {
				if (e.getCollidable() instanceof WallTile && !(e.getCollidable() instanceof BorderTile)) {
					((WallTile)e.getCollidable()).destroyWall();
				}
			}
		}
	}
	
	@Override
	public ArrayList<Collidable> getNeighbors() {
		ArrayList<Collidable> neighbors = new ArrayList<Collidable>();
		
		int[] gridCoords = ((Level) getStage()).getMap().getTileAt(getX(), getY());
		ArrayList<WallTile> a = ((Level) getStage()).getMap().getWallNeighbors(gridCoords[0], gridCoords[1], (currentSize / 128) + 1);
		neighbors.addAll(a);
		
		for (AbstractVehicle v : AbstractVehicle.vehicleList) {
			boolean canCollide = !(v.getTeam() != null && getTeam() != null && getTeam().equals(v.getTeam()));
			if (canCollide)
				neighbors.add(v);
		}
		return neighbors;
	}
	 
	@Override
	protected void initializeHitbox() {
		testHitbox = hitbox = getHitboxAt(getX(), getY(), getRotation());
	}

	@Override
	public Polygon getHitboxAt(float x, float y, float direction) {
		Vector2 v = new Vector2(currentSize / 2, 0);
		int verticesCount = Math.max(4, currentSize / 32);
		float[] f = new float[verticesCount * 2];
		for (int count = 0; count < verticesCount; count++) {
			f[count * 2] = x + v.x;
			f[count * 2 + 1] = y + v.y;
			v.rotate(360.0f / verticesCount);
		}
		return new Polygon(f);
	}

}
