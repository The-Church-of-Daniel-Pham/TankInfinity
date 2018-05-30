package com.tank.actor.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.tank.game.Player;
import com.tank.utils.Assets;

public class Cursor extends AbstractUI {
	protected Vector3 screenPos;
	protected Vector3 hudPos;	//actual position
	protected Vector3 stagePos;
	
	protected boolean newGame;
	
	protected Player player;
	protected Texture tex;

	public Cursor(Player player) {
		super(false, true, 0, 0);
		this.player = player;
		newGame = true;
		tex = Assets.manager.get(Assets.crosshairs_default);
		screenPos = new Vector3(0, 0, 0);
		setOrigin(tex.getWidth() / 2, tex.getHeight() / 2);
	}
	
	
	public Vector3 getScreenPos() {
		return screenPos;
	}
	
	public Vector3 getHudPos() {
		return hudPos;
	}
	
	public Vector3 getStagePos() {
		return stagePos;
	}
	
	public void moveToStage(float x, float y) {
		stagePos = new Vector3(x, y, 0);
		screenPos = new Vector3(player.tank.getStage().stageToScreenCoordinates(new Vector2(stagePos.x, stagePos.y)), 0);
		hudPos = getStage().getCamera().unproject(screenPos.cpy());
		super.setPosition(hudPos.x, hudPos.y);
	}
	
	public void moveToTank() {
		moveToStage(player.tank.getX(), player.tank.getY());
	}
	
	@Override
	public void act(float delta) {
		if (newGame) {
			moveToTank();
			newGame = false;
		}
		
		screenPos.x = MathUtils.clamp(player.controls.getCursor(screenPos).x, getStage().getCamera().frustum.planePoints[0].x, getStage().getCamera().frustum.planePoints[2].x);
		screenPos.y = MathUtils.clamp(player.controls.getCursor(screenPos).y, getStage().getCamera().frustum.planePoints[0].y, getStage().getCamera().frustum.planePoints[2].y);
		stagePos = player.tank.getStage().getCamera().unproject(screenPos.cpy()); // to world coordinates
		hudPos = getStage().getCamera().unproject(screenPos.cpy());	//copy so screenpos isnt modified
		setPosition(hudPos.x, hudPos.y);
	}
	
	public void draw(Batch batch, float a) {
		tex = player.custom.getTexture("cursor");
		batch.draw(tex, super.getX() - super.getOriginX(), super.getY() - super.getOriginY(),
				super.getOriginX(), super.getOriginY(), tex.getWidth(), tex.getHeight(), 1, 1,
				super.getRotation(), 0, 0, tex.getWidth(), tex.getHeight(), false, false);
	}
}
