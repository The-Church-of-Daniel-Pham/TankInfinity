package com.tank.actor.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.tank.game.Player;
import com.tank.utils.Assets;

public class Cursor extends AbstractUI {
	protected Vector3 screenPos;
	protected Vector3 hudPos;
	protected Vector3 stagePos;
	
	protected Player player;
	protected Color color;

	protected static Texture tex = Assets.manager.get(Assets.crosshairs_default);

	public Cursor(Player player) {
		super(false, true, 0, 0);
		this.player = player;
		setOrigin(tex.getWidth() / 2, tex.getHeight() / 2);
	}
	
	public Cursor(Texture t, float x, float y) {
		super(false, true, x, y);
		tex = t;
		setOrigin(tex.getWidth() / 2, tex.getHeight() / 2);
	}
	
	public float[] getHudPos(float x, float y) {
		Vector2 screenCoor = player.tank.getStage().stageToScreenCoordinates(new Vector2(x, y));
		hudPos = getStage().getCamera().unproject(new Vector3(screenCoor.x,screenCoor.y,0));
		return new float[] {hudPos.x, hudPos.y};
	}

	public void moveOnStageTo(float x, float y) {
		super.setPosition(getHudPos(x, y)[0], getHudPos(x, y)[1]);
	}
	
	public Vector3 getScreenPos() {
		return screenPos;
	}
	
	public Vector3 getStagePos() {
		return stagePos;
	}
	
	@Override
	public void act(float delta) {
		screenPos.x = MathUtils.clamp(player.controls.getCursor(screenPos).x, getStage().getCamera().frustum.planePoints[0].x, getStage().getCamera().frustum.planePoints[2].x);
		screenPos.y = MathUtils.clamp(player.controls.getCursor(screenPos).y, getStage().getCamera().frustum.planePoints[0].y, getStage().getCamera().frustum.planePoints[2].y);
		hudPos = getStage().getCamera().unproject(screenPos.cpy());	//copy so screenpos isnt modified
		stagePos = player.tank.getStage().getCamera().unproject(screenPos.cpy()); // to world coordinates
		setPosition(hudPos.x, hudPos.y);
	}
	
	public void draw(Batch batch, float a) {
		tex = player.custom.getTexture("cursor");
		batch.draw(tex, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(),
				super.getOriginX(), super.getOriginY(), tex.getWidth(), tex.getHeight(), 1, 1,
				super.getRotation(), 0, 0, tex.getWidth(), tex.getHeight(), false, false);
	}
	
	public void setColor(Color col) {
		
	}
	
	public Color getColor() {
		return color;
	}
}
