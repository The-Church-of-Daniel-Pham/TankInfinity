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
import com.badlogic.gdx.graphics.Texture;

public class Assets {
	// Textures
	// Grass
	public static final AssetDescriptor<Texture> grass = new AssetDescriptor<Texture>("map/grass/grass.png",
			Texture.class);
	public static final AssetDescriptor<Texture> clumps1 = new AssetDescriptor<Texture>("map/grass/clumps1.png",
			Texture.class);
	public static final AssetDescriptor<Texture> clumps2 = new AssetDescriptor<Texture>("map/grass/clumps2.png",
			Texture.class);
	public static final AssetDescriptor<Texture> clumps3 = new AssetDescriptor<Texture>("map/grass/clumps3.png",
			Texture.class);
	public static final AssetDescriptor<Texture> flowers1 = new AssetDescriptor<Texture>("map/grass/flowers1.png",
			Texture.class);
	public static final AssetDescriptor<Texture> flowers2 = new AssetDescriptor<Texture>("map/grass/flowers2.png",
			Texture.class);
	public static final AssetDescriptor<Texture> flowers3 = new AssetDescriptor<Texture>("map/grass/flowers3.png",
			Texture.class);
	public static final AssetDescriptor<Texture> bushes1 = new AssetDescriptor<Texture>("map/grass/bushes1.png",
			Texture.class);
	public static final AssetDescriptor<Texture> bushes2 = new AssetDescriptor<Texture>("map/grass/bushes2.png",
			Texture.class);
	public static final AssetDescriptor<Texture> bushes3 = new AssetDescriptor<Texture>("map/grass/bushes3.png",
			Texture.class);
	// Brick
	public static final AssetDescriptor<Texture> brick = new AssetDescriptor<Texture>("map/brick/brick.png",
			Texture.class);
	public static final AssetDescriptor<Texture> stone = new AssetDescriptor<Texture>("map/brick/stone.png",
			Texture.class);
	// Projectiles
	public static final AssetDescriptor<Texture> bullet = new AssetDescriptor<Texture>("projectiles/bullet.png", Texture.class);
	// Tank
	public static final AssetDescriptor<Texture> tread = new AssetDescriptor<Texture>("tank/tank_tread.png",
			Texture.class);
	public static final AssetDescriptor<Texture> gun_0 = new AssetDescriptor<Texture>("tank/tank_gun_0.png",
			Texture.class);
	// Menu
	public static final AssetDescriptor<Texture> splash = new AssetDescriptor<Texture>("menu/loading_screen/splash.png",
			Texture.class);
	public static final AssetDescriptor<Texture> dot = new AssetDescriptor<Texture>("projectiles/dot.png",
			Texture.class);
	public static final AssetManager manager = new AssetManager();

	public static void loadTextures() {
		// menu first
		manager.load(splash);
		//System.out.println("Menu textures loaded");
		
		// then map
		manager.load(grass);
		manager.load(clumps1);
		manager.load(clumps2);
		manager.load(clumps3);
		manager.load(flowers1);
		manager.load(flowers2);
		manager.load(flowers3);
		manager.load(bushes1);
		manager.load(bushes2);
		manager.load(bushes3);
		manager.load(brick);
		manager.load(stone);
		manager.load(bullet);
		manager.load(tread);
		manager.load(gun_0);
		manager.load(dot);
		//System.out.println("Map textures loaded");
	}

	public static void loadAll() {
		loadTextures();
		//System.out.println("All loaded");
		// add more file types later
	}

	public static void dispose() {
		manager.dispose();
	}
}