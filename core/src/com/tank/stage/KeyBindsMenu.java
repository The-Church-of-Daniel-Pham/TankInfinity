package com.tank.stage;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.actor.ui.Background;
import com.tank.game.TankInfinity;
import com.tank.utils.Assets;
import com.tank.utils.Constants;

public class KeyBindsMenu extends Stage implements InputProcessor {
    protected TankInfinity game;
    private Skin skin = Assets.manager.get(Assets.skin);
    private Texture black = Assets.manager.get(Assets.black);

    public KeyBindsMenu(TankInfinity game) {
        super(new ExtendViewport(Constants.DEFAULT_WIDTH, Constants.DEFAULT_HEIGHT));
        this.game = game;
        Background darken = new Background(black);
        darken.setFill(true);
        super.addActor(darken);
        super.addActor(keyTable());
    }

    private Table keyTable() {
        Table uiTable = new Table();
        uiTable.setFillParent(true);
        uiTable.setDebug(false);
        uiTable.defaults().width(300).height(100).space(25).center();
        Label keyNotif = new Label("Press any key or mouse button", skin);
        keyNotif.setFontScale(.5f);
        keyNotif.setAlignment(Align.center);
        uiTable.add(keyNotif).height(150);
        return uiTable;
    }
}
