package com.tank.actor.projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.tank.actor.vehicles.AbstractVehicle;
import com.tank.stats.Stats;
import com.tank.utils.Assets;

public class ArtilleryShell extends Actor{
	
	protected static Texture artilleryShell =  Assets.manager.get(Assets.artilleryShell);
	protected static Texture artillerySheet =  Assets.manager.get(Assets.airBombSheet);
	protected AbstractVehicle source;
	protected Stats stats;
	protected Vector2 velocity;
	protected float timeUntilHit;
	
	public ArtilleryShell(AbstractVehicle src, Stats stats, float x, float y) {
		source = src;
		this.stats = stats;
		timeUntilHit = 1.5f;
		Vector2 randomDistance = new Vector2(128, 0);
		randomDistance.rotate((float)(Math.random() * 360));
		setX(x + randomDistance.x);
		setY(y + randomDistance.y);
		velocity = randomDistance.cpy().rotate(180).scl(0.5f);
		setRotation(velocity.angle());
		setOriginX(artilleryShell.getWidth() / 2);
		setOriginY(artilleryShell.getHeight() / 2);
	}
	
	@Override
	public void act(float delta) {
		setX(getX() + velocity.x * delta);
		setY(getY() + velocity.y * delta);
		timeUntilHit -= delta;
		if (timeUntilHit <= 0f) {
			getStage().addActor(new RadialExplosion(source, stats, getX(), getY()));
			remove();
		}
		else {
			setScale((2.5f * timeUntilHit) + 1.0f);
		}
	}
	
	@Override
	public void draw(Batch batch, float a) {
		int frame = (int)(timeUntilHit * 20) % 4;
		batch.draw(artillerySheet, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(), super.getOriginX(),
				super.getOriginY(), 128, 128, super.getScaleX(), super.getScaleY(),
				getRotation(), 128 * frame, 0, 128, 128, false, false);
		//drawVertices(batch, a);
	}
}
