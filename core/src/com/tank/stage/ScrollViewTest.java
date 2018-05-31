package com.tank.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.tank.game.TankInfinity;
import com.tank.utils.Assets;

public class ScrollViewTest extends Stage implements InputProcessor {
    protected TankInfinity game;
    protected Table titleTable, settingsTable;
    private Skin skin;

    public ScrollViewTest(TankInfinity game) {
        super(new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        this.game = game;
        skin = Assets.manager.get(Assets.skin);

        titleTable = new Table();
        buildTitleTable();

        settingsTable = new Table();
        buildSettingsTable();

        super.addActor(titleTable);
        super.addActor(settingsTable);
    }

    public void buildTitleTable()
    {
        TextButton videoButton = new TextButton("Video", skin);
        videoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buildVideoSettings();
            }
        });

        TextButton audioButton = new TextButton("Audio", skin);
        audioButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                buildAudioSettings();
            }
        });

        TextButton controlsButton = new TextButton("Controls", skin);
        controlsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                buildControlsSettings();
            }
        });

        titleTable.setDebug(true);
        titleTable.setFillParent(true);
        titleTable.defaults().width(200).height(75).space(25);
        titleTable.center();
        titleTable.top();
        titleTable.padTop(100f);
        titleTable.add(videoButton);
        titleTable.add(audioButton);
        titleTable.add(controlsButton);
    }

    public void buildSettingsTable()
    {
        buildVideoSettings();
    }

    public void buildVideoSettings()
    {
        settingsTable.clearChildren();
        settingsTable.setFillParent(true);
        settingsTable.defaults().width(200).height(75).space(25).center();
        settingsTable.center();
        settingsTable.add(new TextButton("Video Settings", skin));
    }

    public void buildAudioSettings()
    {
        settingsTable.clearChildren();
        settingsTable.setFillParent(true);
        settingsTable.defaults().width(200).height(75).space(25).center();
        settingsTable.center();
        settingsTable.add(new TextButton("Audio Settings", skin));
    }

    public void buildControlsSettings()
    {
        settingsTable.clearChildren();
        settingsTable.setFillParent(true);
        settingsTable.defaults().width(200).height(75).space(25).center();
        settingsTable.center();
        settingsTable.add(new TextButton("Controls Settings", skin));
    }
}
