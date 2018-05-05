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

public class Assets {
	public static final AssetManager manager = new AssetManager();

	// Textures
	// Floor
	public static final AssetDescriptor<Texture> floor = new AssetDescriptor<Texture>("map/floor.png",
			Texture.class);
	// Brick
	public static final AssetDescriptor<Texture> brick = new AssetDescriptor<Texture>("map/brick/brick.png",
			Texture.class);
	public static final AssetDescriptor<Texture> stone = new AssetDescriptor<Texture>("map/brick/stone.png",
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
	public static final AssetDescriptor<Texture> splash = new AssetDescriptor<Texture>("menu/loading_screen/splash.png",
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
	
	public static void loadTextures() {
		// menu first
		manager.load(splash);
		// System.out.println("Menu textures loaded");

		// then map
		manager.load(floor);
		manager.load(brick);
		manager.load(stone);
		manager.load(bullet);
		manager.load(tread);
		manager.load(gun_0);
		// System.out.println("Map textures loaded");

		// then debug
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

	public static void loadAll() {
		loadTextures();
		loadAudio();
		// System.out.println("All loaded");
	}

	public static void dispose() {
		manager.dispose();
	}
}