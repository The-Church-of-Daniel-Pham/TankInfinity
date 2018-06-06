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
	public static final AssetDescriptor<Texture> damage_explosion = new AssetDescriptor<Texture>(
			"animation/damage_explosion.png", Texture.class);
	public static final AssetDescriptor<Texture> air_bomb = new AssetDescriptor<Texture>(
			"animation/air_bomb.png", Texture.class);
	public static final AssetDescriptor<Texture> moose = new AssetDescriptor<Texture>(
			"animation/moose.png", Texture.class);

    // Textures
		// Map
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
    public static final AssetDescriptor<Texture> landmine = new AssetDescriptor<Texture>("projectiles/landMine.png",
			Texture.class);
    public static final AssetDescriptor<Texture> boomerang = new AssetDescriptor<Texture>("projectiles/boomerang.png",
            Texture.class);
    public static final AssetDescriptor<Texture> pellet = new AssetDescriptor<Texture>("projectiles/pellet.png",
            Texture.class);
    public static final AssetDescriptor<Texture> laser = new AssetDescriptor<Texture>("projectiles/laser.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artilleryShell = new AssetDescriptor<Texture>("projectiles/tiltedAirBomb.png",
            Texture.class);
    public static final AssetDescriptor<Texture> fang = new AssetDescriptor<Texture>("projectiles/fang.png",
            Texture.class);
    public static final AssetDescriptor<Texture> caltrop = new AssetDescriptor<Texture>("projectiles/caltrop.png",
            Texture.class);
    	// Tank
    		// Player
    			// Tread
    public static final AssetDescriptor<Texture> player_tank_tread_red = new AssetDescriptor<Texture>("tank/player/player_tank_tread_red.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_tread_blue = new AssetDescriptor<Texture>("tank/player/player_tank_tread_blue.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_tread_green = new AssetDescriptor<Texture>("tank/player/player_tank_tread_green.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_tread_yellow = new AssetDescriptor<Texture>("tank/player/player_tank_tread_yellow.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_tread_pink = new AssetDescriptor<Texture>("tank/player/player_tank_tread_pink.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_tread_cyan = new AssetDescriptor<Texture>("tank/player/player_tank_tread_cyan.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_tread_lime = new AssetDescriptor<Texture>("tank/player/player_tank_tread_lime.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_tread_orange = new AssetDescriptor<Texture>("tank/player/player_tank_tread_orange.png",
            Texture.class);
    			// Gun
    public static final AssetDescriptor<Texture> player_tank_gun_red = new AssetDescriptor<Texture>("tank/player/player_tank_gun_red.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_gun_blue = new AssetDescriptor<Texture>("tank/player/player_tank_gun_blue.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_gun_green = new AssetDescriptor<Texture>("tank/player/player_tank_gun_green.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_gun_yellow = new AssetDescriptor<Texture>("tank/player/player_tank_gun_yellow.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_gun_pink = new AssetDescriptor<Texture>("tank/player/player_tank_gun_pink.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_gun_cyan = new AssetDescriptor<Texture>("tank/player/player_tank_gun_cyan.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_gun_lime = new AssetDescriptor<Texture>("tank/player/player_tank_gun_lime.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_gun_orange = new AssetDescriptor<Texture>("tank/player/player_tank_gun_orange.png",
            Texture.class);
    
    		// Enemy
    			// Fixed
    public static final AssetDescriptor<Texture> enemy_peashooter_fixed = new AssetDescriptor<Texture>("tank/enemy/enemy_peashooter_fixed.png",
            Texture.class);
    
    public static final AssetDescriptor<Texture> enemy_big_shot_fixed = new AssetDescriptor<Texture>("tank/enemy/enemy_big_shot_fixed.png",
            Texture.class);
    public static final AssetDescriptor<Texture> enemy_artillery_fixed = new AssetDescriptor<Texture>("tank/enemy/enemy_artillery_fixed.png",
            Texture.class);
    			// Free
    				// Tread
    public static final AssetDescriptor<Texture> enemy_peashooter_tread = new AssetDescriptor<Texture>("tank/enemy/enemy_peashooter_tread.png",
            Texture.class);
    				// Gun
    public static final AssetDescriptor<Texture> enemy_peashooter_gun = new AssetDescriptor<Texture>("tank/enemy/enemy_peashooter_gun.png",
            Texture.class);
    
    	// Items
    public static final AssetDescriptor<Texture> mysterybox = new AssetDescriptor<Texture>("item/mysterybox.png",
            Texture.class);
    public static final AssetDescriptor<Texture> repairbox = new AssetDescriptor<Texture>("item/repairbox.png",
            Texture.class);
    
    	// UI
    		// Skin
	public static final AssetDescriptor<Skin> skin = new AssetDescriptor<Skin>("ui/skin/uiskin.json", Skin.class);
			// Crosshairs
	public static final AssetDescriptor<Texture> crosshairs_black = new AssetDescriptor<Texture>("ui/cursor/crosshairs_black.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_red = new AssetDescriptor<Texture>("ui/cursor/crosshairs_red.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_blue = new AssetDescriptor<Texture>("ui/cursor/crosshairs_blue.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_green = new AssetDescriptor<Texture>("ui/cursor/crosshairs_green.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_yellow = new AssetDescriptor<Texture>("ui/cursor/crosshairs_yellow.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_pink = new AssetDescriptor<Texture>("ui/cursor/crosshairs_pink.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_cyan = new AssetDescriptor<Texture>("ui/cursor/crosshairs_cyan.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_lime = new AssetDescriptor<Texture>("ui/cursor/crosshairs_lime.png",
            Texture.class);
    public static final AssetDescriptor<Texture> crosshairs_orange = new AssetDescriptor<Texture>("ui/cursor/crosshairs_orange.png",
            Texture.class);  
    		// Artillery Crosshairs
    public static final AssetDescriptor<Texture> artillery_crosshairs_black = new AssetDescriptor<Texture>("ui/cursor/artillery_crosshairs_black.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artillery_crosshairs_red = new AssetDescriptor<Texture>("ui/cursor/artillery_crosshairs_red.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artillery_crosshairs_blue = new AssetDescriptor<Texture>("ui/cursor/artillery_crosshairs_blue.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artillery_crosshairs_green = new AssetDescriptor<Texture>("ui/cursor/artillery_crosshairs_green.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artillery_crosshairs_yellow = new AssetDescriptor<Texture>("ui/cursor/artillery_crosshairs_yellow.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artillery_crosshairs_pink = new AssetDescriptor<Texture>("ui/cursor/artillery_crosshairs_pink.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artillery_crosshairs_cyan = new AssetDescriptor<Texture>("ui/cursor/artillery_crosshairs_cyan.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artillery_crosshairs_lime = new AssetDescriptor<Texture>("ui/cursor/artillery_crosshairs_lime.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artillery_crosshairs_orange = new AssetDescriptor<Texture>("ui/cursor/artillery_crosshairs_orange.png",
            Texture.class);
    	// Preview
    public static final AssetDescriptor<Texture> player_tank_preview_red = new AssetDescriptor<Texture>("ui/preview/player_tank_preview_red.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_preview_blue = new AssetDescriptor<Texture>("ui/preview/player_tank_preview_blue.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_preview_green = new AssetDescriptor<Texture>("ui/preview/player_tank_preview_green.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_preview_yellow = new AssetDescriptor<Texture>("ui/preview/player_tank_preview_yellow.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_preview_pink = new AssetDescriptor<Texture>("ui/preview/player_tank_preview_pink.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_preview_cyan = new AssetDescriptor<Texture>("ui/preview/player_tank_preview_cyan.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_preview_lime = new AssetDescriptor<Texture>("ui/preview/player_tank_preview_lime.png",
            Texture.class);
    public static final AssetDescriptor<Texture> player_tank_preview_orange = new AssetDescriptor<Texture>("ui/preview/player_tank_preview_orange.png",
            Texture.class);
    	// Sub icon
    public static final AssetDescriptor<Texture> blank_icon = new AssetDescriptor<Texture>("ui/icon/sub/blank_icon.png",
            Texture.class);
    public static final AssetDescriptor<Texture> rocket_icon = new AssetDescriptor<Texture>("ui/icon/sub/rocket_icon.png",
            Texture.class);
    public static final AssetDescriptor<Texture> chakram_icon = new AssetDescriptor<Texture>("ui/icon/sub/chakram_icon.png",
            Texture.class);
    public static final AssetDescriptor<Texture> landmine_icon = new AssetDescriptor<Texture>("ui/icon/sub/landmine_icon.png",
			Texture.class);
    public static final AssetDescriptor<Texture> boomerang_icon = new AssetDescriptor<Texture>("ui/icon/sub/boomerang_icon.png",
            Texture.class);
    public static final AssetDescriptor<Texture> pellet_icon = new AssetDescriptor<Texture>("ui/icon/sub/pellet_icon.png",
            Texture.class);
    public static final AssetDescriptor<Texture> laser_icon = new AssetDescriptor<Texture>("ui/icon/sub/laser_icon.png",
            Texture.class);
    public static final AssetDescriptor<Texture> artillery_icon = new AssetDescriptor<Texture>("ui/icon/sub/artillery_icon.png",
            Texture.class);
    public static final AssetDescriptor<Texture> fang_icon = new AssetDescriptor<Texture>("ui/icon/sub/fang_icon.png",
            Texture.class);
    public static final AssetDescriptor<Texture> caltrop_icon = new AssetDescriptor<Texture>("ui/icon/sub/caltrop_icon.png",
            Texture.class);
    public static final AssetDescriptor<Texture> moose_icon = new AssetDescriptor<Texture>("ui/icon/sub/moose_icon.png",
            Texture.class);
    	// Menu
    public static final AssetDescriptor<Texture> backdrop = new AssetDescriptor<Texture>("ui/menu/loading/backdrop.png",
            Texture.class);
    public static final AssetDescriptor<Texture> loading_tank = new AssetDescriptor<Texture>("ui/menu/loading/loading_tank.png",
            Texture.class);
    public static final AssetDescriptor<Texture> title = new AssetDescriptor<Texture>("ui/menu/main/title.png",
            Texture.class);
    public static final AssetDescriptor<Texture> black = new AssetDescriptor<Texture>("ui/menu/pause/black.png",
            Texture.class);
    public static final AssetDescriptor<Texture> red = new AssetDescriptor<Texture>("ui/menu/gameover/red.png", Texture.class);
    public static final AssetDescriptor<Texture> tutorial_1 = new AssetDescriptor<Texture>("ui/menu/tutorial/tutorial_1.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tutorial_2 = new AssetDescriptor<Texture>("ui/menu/tutorial/tutorial_2.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tutorial_3 = new AssetDescriptor<Texture>("ui/menu/tutorial/tutorial_3.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tutorial_4 = new AssetDescriptor<Texture>("ui/menu/tutorial/tutorial_4.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tutorial_5 = new AssetDescriptor<Texture>("ui/menu/tutorial/tutorial_5.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tutorial_6 = new AssetDescriptor<Texture>("ui/menu/tutorial/tutorial_6.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tutorial_7 = new AssetDescriptor<Texture>("ui/menu/tutorial/tutorial_7.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tutorial_8 = new AssetDescriptor<Texture>("ui/menu/tutorial/tutorial_8.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tutorial_9 = new AssetDescriptor<Texture>("ui/menu/tutorial/tutorial_9.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tutorial_10 = new AssetDescriptor<Texture>("ui/menu/tutorial/tutorial_10.png",
            Texture.class);
    public static final AssetDescriptor<Texture> tutorial_11 = new AssetDescriptor<Texture>("ui/menu/tutorial/tutorial_11.png",
            Texture.class);
    	// Debug
    public static final AssetDescriptor<Texture> vertex = new AssetDescriptor<Texture>("debug/vertex.png",
            Texture.class);

	// Audio
		// Bullet
	public static final AssetDescriptor<Sound> bullet_fire = new AssetDescriptor<Sound>("audio/bullet_fire.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> bullet_bounce = new AssetDescriptor<Sound>("audio/bullet_bounce.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> bullet_hit = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> laser_fire = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> laser_bounce = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> laser_hit = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> rocket_fire = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> rocket_hit = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> chakram_fire = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> chakram_bounce = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> chakram_hit = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> boomerang_fire = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> boomerang_bounce = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> boomerang_hit = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> pellet_fire = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> pellet_hit = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> landmine_deploy = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> caltrop_fire = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> caltrop_hit = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> artillery_fire = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> artillery_countdown = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> artillery_falling = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> fang_shoot = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> fang_hit = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> moose_shoot = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> moose_moving = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> moose_moo = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> moose_hit = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> explosion_damage_sound = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> death_explosion_sound = new AssetDescriptor<Sound>("audio/silent.wav",
			Sound.class);
		// Tank
	public static final AssetDescriptor<Sound> tank_engine = new AssetDescriptor<Sound>("audio/tank_engine.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> tank_tread = new AssetDescriptor<Sound>("audio/tank_tread.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> tank_damage = new AssetDescriptor<Sound>("audio/damage_sound.wav",
			Sound.class);


	public static void loadLoading() {
		// load first
		manager.load(backdrop);
		manager.load(loading_tank);
		manager.load(skin);
	}


    public static void loadTextures() {
        manager.load(death_explosion);
        manager.load(damage_explosion);
        manager.load(air_bomb);
        manager.load(moose);
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
        manager.load(laser);
        manager.load(fang);
        manager.load(caltrop);
        // System.out.println("Projectile textures loaded");
        manager.load(player_tank_tread_red);
        manager.load(player_tank_tread_blue);
        manager.load(player_tank_tread_green);
        manager.load(player_tank_tread_yellow);
        manager.load(player_tank_tread_pink);
        manager.load(player_tank_tread_cyan);
        manager.load(player_tank_tread_lime);
        manager.load(player_tank_tread_orange);
        manager.load(player_tank_gun_red);
        manager.load(player_tank_gun_blue);
        manager.load(player_tank_gun_green);
        manager.load(player_tank_gun_yellow);
        manager.load(player_tank_gun_pink);
        manager.load(player_tank_gun_cyan);
        manager.load(player_tank_gun_lime);
        manager.load(player_tank_gun_orange);
        manager.load(enemy_peashooter_fixed);
        manager.load(enemy_big_shot_fixed);
        manager.load(enemy_artillery_fixed);
        manager.load(enemy_peashooter_tread);
        manager.load(enemy_peashooter_gun);   
        // System.out.println("Tank textures loaded");
        manager.load(mysterybox);
        manager.load(repairbox);
        // System.out.println("Item textures loaded");
        manager.load(crosshairs_black);
        manager.load(crosshairs_red);
        manager.load(crosshairs_blue);
        manager.load(crosshairs_green);
        manager.load(crosshairs_yellow);
        manager.load(crosshairs_pink);
        manager.load(crosshairs_cyan);
        manager.load(crosshairs_lime);
        manager.load(crosshairs_orange);
        manager.load(artillery_crosshairs_black);
        manager.load(artillery_crosshairs_red);
        manager.load(artillery_crosshairs_blue);
        manager.load(artillery_crosshairs_green);
        manager.load(artillery_crosshairs_yellow);
        manager.load(artillery_crosshairs_pink);
        manager.load(artillery_crosshairs_cyan);
        manager.load(artillery_crosshairs_lime);
        manager.load(artillery_crosshairs_orange);
        manager.load(player_tank_preview_red);
        manager.load(player_tank_preview_blue);
        manager.load(player_tank_preview_green);
        manager.load(player_tank_preview_yellow);
        manager.load(player_tank_preview_pink);
        manager.load(player_tank_preview_cyan);
        manager.load(player_tank_preview_lime);
        manager.load(player_tank_preview_orange);
        manager.load(blank_icon);
        manager.load(rocket_icon);
        manager.load(chakram_icon);
        manager.load(boomerang_icon);
        manager.load(landmine_icon);
        manager.load(pellet_icon);
        manager.load(artillery_icon);
        manager.load(laser_icon);
        manager.load(fang_icon);
        manager.load(caltrop_icon);
        manager.load(moose_icon);
        // System.out.println("UI textures loaded");
        manager.load(title);
        manager.load(black);
        manager.load(red);
        manager.load(tutorial_1);
        manager.load(tutorial_2);
        manager.load(tutorial_3);
        manager.load(tutorial_4);
        manager.load(tutorial_5);
        manager.load(tutorial_6);
        manager.load(tutorial_7);
        manager.load(tutorial_8);
        manager.load(tutorial_9);
        manager.load(tutorial_10);
        manager.load(tutorial_11);
        // System.out.println("Menu textures loaded");
        manager.load(vertex);
        // System.out.println("Debug textures loaded");
    }
    
	public static void loadAudio() {
		manager.load(bullet_fire);
		manager.load(bullet_bounce);
		manager.load(bullet_hit);
		manager.load(laser_fire);
		manager.load(laser_bounce);
		manager.load(laser_hit);
		manager.load(rocket_fire);
		manager.load(rocket_hit);
		manager.load(chakram_fire);
		manager.load(chakram_bounce);
		manager.load(chakram_hit);
		manager.load(boomerang_fire);
		manager.load(boomerang_bounce);
		manager.load(boomerang_hit);
		manager.load(pellet_fire);
		manager.load(pellet_hit);
		manager.load(landmine_deploy);
		manager.load(caltrop_fire);
		manager.load(caltrop_hit);
		manager.load(artillery_fire);
		manager.load(artillery_countdown);
		manager.load(artillery_falling);
		manager.load(fang_shoot);
		manager.load(fang_hit);
		manager.load(moose_shoot);
		manager.load(moose_moving);
		manager.load(moose_moo);
		manager.load(moose_hit);
		manager.load(explosion_damage_sound);
		manager.load(death_explosion_sound);
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