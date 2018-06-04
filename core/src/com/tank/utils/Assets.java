package com.tank.utils;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public static final AssetManager manager = new AssetManager();

	// For animation
	public static final AssetDescriptor<Texture> death_explosion = new AssetDescriptor<Texture>(
			"animation/death_explosion.png", Texture.class);
	public static final AssetDescriptor<Texture> burstSheet = new AssetDescriptor<Texture>(
			"animation/burstSheet.png", Texture.class);
	public static final AssetDescriptor<Texture> airBombSheet = new AssetDescriptor<Texture>(
			"animation/airBombSheet.png", Texture.class);

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
    // Border
    public static final AssetDescriptor<Texture> border0 = new AssetDescriptor<Texture>("map/border0.png",
            Texture.class);
    public static final AssetDescriptor<Texture> border1 = new AssetDescriptor<Texture>("map/border1.png",
            Texture.class);
    public static final AssetDescriptor<Texture> border2 = new AssetDescriptor<Texture>("map/border2.png",
            Texture.class);
    public static final AssetDescriptor<Texture> border3 = new AssetDescriptor<Texture>("map/border3.png",
            Texture.class);
    public static final AssetDescriptor<Texture> border4 = new AssetDescriptor<Texture>("map/border4.png",
            Texture.class);
    //Portal
    public static final AssetDescriptor<Texture> portal = new AssetDescriptor<Texture>("map/portal.png",
    		Texture.class);
    // Projectiles
    public static final AssetDescriptor<Texture> bullet = new AssetDescriptor<Texture>("projectiles/bullet.png",
            Texture.class);
    public static final AssetDescriptor<Texture> rocket = new AssetDescriptor<Texture>("projectiles/rocket.png",
            Texture.class);
    public static final AssetDescriptor<Texture> chakram = new AssetDescriptor<Texture>("projectiles/chakram.png",
            Texture.class);
    public static final AssetDescriptor<Texture> landmine = new AssetDescriptor<Texture>("projectiles/landmine.png",
			Texture.class);
    public static final AssetDescriptor<Texture> boomerang = new AssetDescriptor<Texture>("projectiles/boomerang.png",
            Texture.class);
    public static final AssetDescriptor<Texture> pellet = new AssetDescriptor<Texture>("projectiles/pellet.png",
            Texture.class);
    public static final AssetDescriptor<Texture> laser = new AssetDescriptor<Texture>("projectiles/laser.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artilleryShell = new AssetDescriptor<Texture>("projectiles/tiltedAirBomb.png",
            Texture.class);
    public static final AssetDescriptor<Texture> pelletIcon = new AssetDescriptor<Texture>("projectiles/pelletIcon.png",
            Texture.class);
    // Tank
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
    public static final AssetDescriptor<Texture> tread_gray = new AssetDescriptor<Texture>("tank/tank_tread_gray.png",
            Texture.class);
    public static final AssetDescriptor<Texture> gun_gray = new AssetDescriptor<Texture>("tank/tank_gun_gray.png",
            Texture.class);
    public static final AssetDescriptor<Texture> fixed_purple = new AssetDescriptor<Texture>("tank/tank_fixed_purple.png",
            Texture.class);
    public static final AssetDescriptor<Texture> fixed_tan = new AssetDescriptor<Texture>("tank/tank_fixed_tan.png",
            Texture.class);
    public static final AssetDescriptor<Texture> fixed_gray = new AssetDescriptor<Texture>("tank/tank_fixed_gray.png",
            Texture.class);
    public static final AssetDescriptor<Texture> fixed_big = new AssetDescriptor<Texture>("tank/tank_fixed_big_rocket.png",
            Texture.class);
    //Items
    public static final AssetDescriptor<Texture> mysterybox = new AssetDescriptor<Texture>("item/mysterybox.png",
            Texture.class);
    public static final AssetDescriptor<Texture> repairbox = new AssetDescriptor<Texture>("item/repairbox.png",
            Texture.class);
    //UI
    public static final AssetDescriptor<Texture> crosshairs_red = new AssetDescriptor<Texture>("ui/cursor/crosshairs_red.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_blue = new AssetDescriptor<Texture>("ui/cursor/crosshairs_blue.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_green = new AssetDescriptor<Texture>("ui/cursor/crosshairs_green.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_yellow = new AssetDescriptor<Texture>("ui/cursor/crosshairs_yellow.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_black = new AssetDescriptor<Texture>("ui/cursor/crosshairs_black.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artillery_crosshairs_red = new AssetDescriptor<Texture>("ui/cursor/artillery_crosshairs_red.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artillery_crosshairs_blue = new AssetDescriptor<Texture>("ui/cursor/artillery_crosshairs_blue.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artillery_crosshairs_green = new AssetDescriptor<Texture>("ui/cursor/artillery_crosshairs_green.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artillery_crosshairs_yellow = new AssetDescriptor<Texture>("ui/cursor/artillery_crosshairs_yellow.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artillery_crosshairs_black = new AssetDescriptor<Texture>("ui/cursor/artillery_crosshairs_black.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tank_preview_red = new AssetDescriptor<Texture>("ui/tank/tank_preview_red.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tank_preview_blue = new AssetDescriptor<Texture>("ui/tank/tank_preview_blue.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tank_preview_green = new AssetDescriptor<Texture>("ui/tank/tank_preview_green.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tank_preview_yellow = new AssetDescriptor<Texture>("ui/tank/tank_preview_yellow.png",
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
	public static final AssetDescriptor<Skin> skin = new AssetDescriptor<Skin>("menu/skin/uiskin.json", Skin.class);

	public static void loadLoading() {
		// load first
		manager.load(backdrop);
		manager.load(loading_tank);
		manager.load(skin);
	}


    public static void loadTextures() {
        manager.load(death_explosion);
        manager.load(burstSheet);
        manager.load(airBombSheet);
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
        manager.load(border0);
        manager.load(border1);
        manager.load(border2);
        manager.load(border3);
        manager.load(border4);
        manager.load(portal);
        // System.out.println("Map textures loaded");
        manager.load(bullet);
        manager.load(rocket);
        manager.load(chakram);
        manager.load(boomerang);
        manager.load(landmine);
        manager.load(pellet);
        manager.load(artilleryShell);
        manager.load(pelletIcon);
        manager.load(laser);
        // System.out.println("Projectile textures loaded");
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
        manager.load(tread_gray);
        manager.load(gun_gray);
        manager.load(fixed_purple);
        manager.load(fixed_tan);
        manager.load(fixed_gray);
        manager.load(fixed_big);
        // System.out.println("Tank textures loaded");
        manager.load(mysterybox);
        manager.load(repairbox);
        // System.out.println("Item textures loaded");
        manager.load(crosshairs_red);
        manager.load(crosshairs_blue);
        manager.load(crosshairs_green);
        manager.load(crosshairs_yellow);
        manager.load(crosshairs_black);
        manager.load(artillery_crosshairs_red);
        manager.load(artillery_crosshairs_blue);
        manager.load(artillery_crosshairs_green);
        manager.load(artillery_crosshairs_yellow);
        manager.load(artillery_crosshairs_black);
        manager.load(tank_preview_red);
        manager.load(tank_preview_blue);
        manager.load(tank_preview_green);
        manager.load(tank_preview_yellow);
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
		// more later
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