package com.tank.actor.projectiles;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.interfaces.Collidable;
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
	private ArrayList<AbstractVehicle> vehiclesHit;
	
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
		maxLifeTime = stats.getStatValue("Lifetime") / 10.0f;
		lifeTime = 0f;
		setOriginX(FRAME_WIDTH / 2);
		setOriginY(FRAME_HEIGHT / 2);
		vehiclesHit = new ArrayList<AbstractVehicle>();
		
		// 1/fps, where col*row = total frames, maxlifetime is time alloted to finish animation
		damageExplosionAnimation.setFrameDuration(1 / (FRAMES_COLS * FRAMES_ROWS / maxLifeTime));
	}
	
	public void act(float delta) {
		lifeTime += delta;
		currentSize = (int)(Math.pow(lifeTime / maxLifeTime, 0.5) * (endSize - startingSize)) + startingSize;
		setScale((float)currentSize / (float)normalSize);
		if (lifeTime >= maxLifeTime) {
			destroy();
			return;
		}
		else {
			initializeHitbox();
			explode();
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
		
		TextureRegion currentFrame = damageExplosionAnimation.getKeyFrame(lifeTime, false);
		batch.draw(currentFrame, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(), super.getOriginX(),
				super.getOriginY(), FRAME_WIDTH, FRAME_HEIGHT, super.getScaleX(), super.getScaleY(), getRotation());
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
