package com.tank.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    public static final AssetManager manager = new AssetManager();

    // For animation
    public static final AssetDescriptor<Texture> explosionSheet = new AssetDescriptor<Texture>("animation/explosion_atlas.png",
            Texture.class);

    // Textures
    // Floor
    public static final AssetDescriptor<Texture> grass0 = new AssetDescriptor<Texture>("map/grass0.png",
            Texture.class);
    public static final AssetDescriptor<Texture> grass1 = new AssetDescriptor<Texture>("map/grass1.png",
            Texture.class);
    public static final AssetDescriptor<Texture> grass2 = new AssetDescriptor<Texture>("map/grass2.png",
            Texture.class);
    public static final AssetDescriptor<Texture> grass3 = new AssetDescriptor<Texture>("map/grass3.png",
            Texture.class);
    public static final AssetDescriptor<Texture> grass4 = new AssetDescriptor<Texture>("map/grass4.png",
            Texture.class);
    // Wall
    public static final AssetDescriptor<Texture> stone0 = new AssetDescriptor<Texture>("map/stone0.png",
            Texture.class);
    public static final AssetDescriptor<Texture> stone1 = new AssetDescriptor<Texture>("map/stone1.png",
            Texture.class);
    public static final AssetDescriptor<Texture> stone2 = new AssetDescriptor<Texture>("map/stone2.png",
            Texture.class);
    public static final AssetDescriptor<Texture> stone3 = new AssetDescriptor<Texture>("map/stone3.png",
            Texture.class);
    public static final AssetDescriptor<Texture> stone4 = new AssetDescriptor<Texture>("map/stone4.png",
            Texture.class);
    //Portal
    public static final AssetDescriptor<Texture> portal = new AssetDescriptor<Texture>("map/portalSpiral.png",
    		Texture.class);
    // Projectiles
    public static final AssetDescriptor<Texture> bullet = new AssetDescriptor<Texture>("projectiles/bullet.png",
            Texture.class);
    // Tank
    public static final AssetDescriptor<Texture> tread_default = new AssetDescriptor<Texture>("tank/tank_tread_default.png",
            Texture.class);
    public static final AssetDescriptor<Texture> gun_default = new AssetDescriptor<Texture>("tank/tank_gun_default.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tread_red = new AssetDescriptor<Texture>("tank/tank_tread_red.png",
            Texture.class);
    public static final AssetDescriptor<Texture> gun_red = new AssetDescriptor<Texture>("tank/tank_gun_red.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tread_blue = new AssetDescriptor<Texture>("tank/tank_tread_blue.png",
            Texture.class);
    public static final AssetDescriptor<Texture> gun_blue = new AssetDescriptor<Texture>("tank/tank_gun_blue.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tread_green = new AssetDescriptor<Texture>("tank/tank_tread_green.png",
            Texture.class);
    public static final AssetDescriptor<Texture> gun_green = new AssetDescriptor<Texture>("tank/tank_gun_green.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tread_yellow = new AssetDescriptor<Texture>("tank/tank_tread_yellow.png",
            Texture.class);
    public static final AssetDescriptor<Texture> gun_yellow = new AssetDescriptor<Texture>("tank/tank_gun_yellow.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tread_purple = new AssetDescriptor<Texture>("tank/tank_tread_purple.png",
            Texture.class);
    public static final AssetDescriptor<Texture> gun_purple = new AssetDescriptor<Texture>("tank/tank_gun_purple.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tread_tan = new AssetDescriptor<Texture>("tank/tank_tread_tan.png",
            Texture.class);
    public static final AssetDescriptor<Texture> gun_tan = new AssetDescriptor<Texture>("tank/tank_gun_tan.png",
            Texture.class);
    //UI
    public static final AssetDescriptor<Texture> crosshairs_default = new AssetDescriptor<Texture>("ui/cursor/crosshairs_default.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_red = new AssetDescriptor<Texture>("ui/cursor/crosshairs_red.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_blue = new AssetDescriptor<Texture>("ui/cursor/crosshairs_blue.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_green = new AssetDescriptor<Texture>("ui/cursor/crosshairs_green.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_yellow = new AssetDescriptor<Texture>("ui/cursor/crosshairs_yellow.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_purple = new AssetDescriptor<Texture>("ui/cursor/crosshairs_purple.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_tan = new AssetDescriptor<Texture>("ui/cursor/crosshairs_tan.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tank_preview_default = new AssetDescriptor<Texture>("ui/tank/tank_preview_default.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tank_preview_red = new AssetDescriptor<Texture>("ui/tank/tank_preview_red.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tank_preview_blue = new AssetDescriptor<Texture>("ui/tank/tank_preview_blue.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tank_preview_green = new AssetDescriptor<Texture>("ui/tank/tank_preview_green.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tank_preview_yellow = new AssetDescriptor<Texture>("ui/tank/tank_preview_yellow.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tank_preview_purple = new AssetDescriptor<Texture>("ui/tank/tank_preview_purple.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tank_preview_tan = new AssetDescriptor<Texture>("ui/tank/tank_preview_tan.png",
            Texture.class);
    // Menu
    public static final AssetDescriptor<Texture> backdrop = new AssetDescriptor<Texture>("menu/loading/backdrop.png",
            Texture.class);
    public static final AssetDescriptor<Texture> loading_tank = new AssetDescriptor<Texture>("menu/loading/loading_tank.png",
            Texture.class);
    public static final AssetDescriptor<Texture> title = new AssetDescriptor<Texture>("menu/main/title.png",
            Texture.class);
    public static final AssetDescriptor<Texture> black = new AssetDescriptor<Texture>("menu/pause/black.png",
            Texture.class);
    public static final AssetDescriptor<Texture> red = new AssetDescriptor<Texture>("menu/gameover/red.png", Texture.class);
    // Debug
    public static final AssetDescriptor<Texture> vertex = new AssetDescriptor<Texture>("debug/vertex.png",
            Texture.class);

    // Audio
    // Bullet
    public static final AssetDescriptor<Sound> bullet_fire = new AssetDescriptor<Sound>("audio/bullet_fire.wav",
            Sound.class);
    public static final AssetDescriptor<Sound> bullet_bounce = new AssetDescriptor<Sound>("audio/bullet_bounce.wav",
            Sound.class);
    // Tank
    public static final AssetDescriptor<Sound> tank_engine = new AssetDescriptor<Sound>("audio/tank_engine.wav",
            Sound.class);
    public static final AssetDescriptor<Sound> tank_tread = new AssetDescriptor<Sound>("audio/tank_tread.wav",
            Sound.class);
    public static final AssetDescriptor<Sound> tank_damage = new AssetDescriptor<Sound>("audio/damage_sound.wav",
            Sound.class);

    // UI
    public static final AssetDescriptor<Skin> skin = new AssetDescriptor<Skin>("menu/skin/plain-james/uiskin.json",
            Skin.class);

    public static void loadLoading() {
        // load first
        manager.load(backdrop);
        manager.load(loading_tank);
        manager.load(skin);
    }

    public static void loadTextures() {
        manager.load(explosionSheet);
        // System.out.println("Animation loaded");
        manager.load(grass0);
        manager.load(grass1);
        manager.load(grass2);
        manager.load(grass3);
        manager.load(grass4);
        manager.load(stone0);
        manager.load(stone1);
        manager.load(stone2);
        manager.load(stone3);
        manager.load(stone4);
        manager.load(portal);
        // System.out.println("Map textures loaded");
        manager.load(bullet);
        // System.out.println("Projectile textures loaded");
        manager.load(tread_default);
        manager.load(gun_default);
        manager.load(tread_red);
        manager.load(gun_red);
        manager.load(tread_blue);
        manager.load(gun_blue);
        manager.load(tread_green);
        manager.load(gun_green);
        manager.load(tread_yellow);
        manager.load(gun_yellow);
        manager.load(tread_purple);
        manager.load(gun_purple);
        manager.load(tread_tan);
        manager.load(gun_tan);
        // System.out.println("Tank textures loaded");
        manager.load(crosshairs_default);
        manager.load(crosshairs_red);
        manager.load(crosshairs_blue);
        manager.load(crosshairs_green);
        manager.load(crosshairs_yellow);
        manager.load(crosshairs_purple);
        manager.load(crosshairs_tan);
        manager.load(tank_preview_default);
        manager.load(tank_preview_red);
        manager.load(tank_preview_blue);
        manager.load(tank_preview_green);
        manager.load(tank_preview_yellow);
        manager.load(tank_preview_purple);
        manager.load(tank_preview_tan);
        // System.out.println("UI textures loaded");
        manager.load(title);
        manager.load(black);
        manager.load(red);
        // System.out.println("Menu textures loaded");
        manager.load(vertex);
        // System.out.println("Debug textures loaded");
    }

    public static void loadAudio() {
        manager.load(bullet_fire);
        manager.load(bullet_bounce);
        // System.out.println("Bullet audio loaded");
        manager.load(tank_engine);
        manager.load(tank_tread);
        manager.load(tank_damage);
        // System.out.println("Tank audio loaded");
    }

    public static void loadUI() {
        //more later
        // System.out.println("UI loaded");
    }

    public static void loadAll() {
        loadLoading();
        loadTextures();
        loadAudio();
        loadUI();
        // System.out.println("All loaded");
    }

    public static void dispose() {
        manager.dispose();
    }
}