package com.ttr.utils;
/**
 * @author Samuel
 * @version April 13th 2018
 * 
 * Description: Custom AssetManager to load all required files for the game. Asset Descriptors store file path and file type, and can be accessed externally by Assets.ASSET_NAME.
 * Each file type has its own load method, and loadAll calls them all. After a file is loaded, it can be accessed by Assets.manager.get(Assets.ASSET_NAME).
 */

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
	public static final AssetManager manager = new AssetManager();

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
	// Projectiles
	public static final AssetDescriptor<Texture> bullet = new AssetDescriptor<Texture>("projectiles/bullet.png",
			Texture.class);
	// Tank
	public static final AssetDescriptor<Texture> tread = new AssetDescriptor<Texture>("tank/tank_tread.png",
			Texture.class);
	public static final AssetDescriptor<Texture> gun_0 = new AssetDescriptor<Texture>("tank/tank_gun_0.png",
			Texture.class);
	// Menu
	public static final AssetDescriptor<Texture> splash = new AssetDescriptor<Texture>("menu/loading/splash.png",
			Texture.class);
	public static final AssetDescriptor<Texture> black = new AssetDescriptor<Texture>("menu/pause/black.png",
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
	// Tank
	public static final AssetDescriptor<Sound> tank_engine = new AssetDescriptor<Sound>("audio/tank_engine.wav",
			Sound.class);
	public static final AssetDescriptor<Sound> tank_tread = new AssetDescriptor<Sound>("audio/tank_tread.wav",
			Sound.class);
	
	// UI
	public static final AssetDescriptor<Skin> skin = new AssetDescriptor<Skin>("menu/skin/plain-james/uiskin.json",
			Skin.class);
	
	public static void loadLoading() {
		// load first
		manager.load(splash);
		manager.load(skin);
	}
	
	public static void loadTextures() {
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
		manager.load(bullet);
		manager.load(tread);
		manager.load(gun_0);
		// System.out.println("Map textures loaded");
		manager.load(black);
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