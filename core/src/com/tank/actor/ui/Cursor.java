package com.tank.actor.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
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
	protected Stage stage;	//stage underneath, which teh  cursor is virtually "on"
	protected Color color;

	protected static Texture tex = Assets.manager.get(Assets.crosshairs_default);

	public Cursor(Player player, Stage stage) {
		super(false, true, 0, 0);
		this.player = player;
		this.stage = stage;
		setOrigin(tex.getWidth() / 2, tex.getHeight() / 2);
	}
	
	public Cursor(Texture t, float x, float y) {
		super(false, true, x, y);
		tex = t;
		setOrigin(tex.getWidth() / 2, tex.getHeight() / 2);
	}

	public void moveOnStageTo(float x, float y) {
		stagePos = new Vector3(x, y, 0);
		//screenPos = stage.getCamera().project(stagePos.cpy());
		//hudPos = stage.getCamera().unproject(screenPos.cpy()); // to world coordinates
		//setPosition(hudPos.x, hudPos.y);
	}
	
	public Vector3 getScreenPos() {
		return screenPos;
	}
	
	public Vector3 getStagePos() {
		return stagePos;
	}
	
	@Override
	public void act(float delta) {
		if (screenPos != null)
			screenPos = player.controls.getCursor(screenPos);
		else {
			if (stagePos != null) {
				screenPos = stage.getCamera().project(stagePos.cpy());
			}
			else if (hudPos != null) {
				screenPos = getStage().getCamera().project(hudPos.cpy());
			}
			else screenPos = player.controls.getCursor(new Vector3(0, 0, 0));
		}
		hudPos = getStage().getCamera().unproject(screenPos.cpy());	//copy so screenpos isnt modified
		stagePos = stage.getCamera().unproject(screenPos.cpy()); // to world coordinates
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
