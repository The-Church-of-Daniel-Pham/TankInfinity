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
		System.out.println(x + ", " + y);
		Vector2 screenCoor = stage.stageToScreenCoordinates(new Vector2(x, y));
		System.out.println(screenCoor.x + ", "+ screenCoor.y);
		hudPos = getStage().getCamera().unproject(new Vector3(screenCoor.x,screenCoor.y,0));
		super.setPosition(hudPos.x, hudPos.y);
		System.out.println(hudPos.x + ", " + hudPos.y);
	}
	
	public Vector3 getScreenPos() {
		return screenPos;
	}
	
	public Vector3 getStagePos() {
		return stagePos;
	}
	
	@Override
	public void act(float delta) {
		if (screenPos != null) {
			screenPos.x = MathUtils.clamp(player.controls.getCursor(screenPos).x, getStage().getCamera().frustum.planePoints[0].x, getStage().getCamera().frustum.planePoints[2].x);
			screenPos.y = MathUtils.clamp(player.controls.getCursor(screenPos).y, getStage().getCamera().frustum.planePoints[0].y, getStage().getCamera().frustum.planePoints[2].y);
		}
		else {
			screenPos = new Vector3();
			screenPos.x = MathUtils.clamp(player.controls.getCursor(new Vector3(0, 0, 0)).x, getStage().getCamera().frustum.planePoints[0].x, getStage().getCamera().frustum.planePoints[2].x);
			screenPos.y = MathUtils.clamp(player.controls.getCursor(new Vector3(0, 0, 0)).y, getStage().getCamera().frustum.planePoints[0].y, getStage().getCamera().frustum.planePoints[2].y);
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
