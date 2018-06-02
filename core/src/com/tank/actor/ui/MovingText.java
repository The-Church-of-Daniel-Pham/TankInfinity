package com.tank.actor.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.NumberUtils;
import com.tank.utils.Assets;

public class MovingText extends Label{
	private static Skin skin = Assets.manager.get(Assets.skin);
	private float lifeTime;
	private float maxLifeTime;
	private Vector2 velocity;
	boolean fade;
	
	public MovingText(String text, Color color, float lifeTime, Vector2 velocity, float x, float y) {
		super(text, skin);
		setX(x);
		setY(y);
		maxLifeTime = lifeTime;
		this.velocity = velocity;
		setColor(color);
		setX(x - getWidth() / 2);
		setY(y - getHeight() / 2);
	}
	
	public MovingText(String text, Color color, float lifeTime, Vector2 velocity, float x, float y, float scale) {
		super(text, skin);
		maxLifeTime = lifeTime;
		this.velocity = velocity;
		setColor(color);
		setFontScale(scale);
		setScale(scale);
		setX(x - getWidth() * scale / 2);
		setY(y - getHeight() * scale / 2);
	}
	
	@Override
	public void act(float delta) {
		lifeTime += delta;
		setX(getX() + velocity.x * delta);
		setY(getY() + velocity.y * delta);
		if (lifeTime >= maxLifeTime) {
			remove();
		}
	}
}
