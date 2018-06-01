package com.tank.actor.items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.tank.actor.map.tiles.AbstractMapTile;
import com.tank.utils.Assets;

public class HealthPackItem extends AbstractItem {
    public static Texture healthpack = Assets.manager.get(Assets.healthpack);
    public static final float SCALE = 1f;
    public float rotationTime;
    public static final float PACK_SIZE = 110;

    public HealthPackItem(int row, int col, String temp) {
        this(col * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2,
                row * AbstractMapTile.SIZE + AbstractMapTile.SIZE / 2);
    }

    public HealthPackItem(float x, float y) {
        super(x, y, healthpack);
        setOrigin(healthpack.getWidth() / 2, healthpack.getHeight() / 2);
        setHeight(PACK_SIZE);
        setWidth(PACK_SIZE);
        setScale(SCALE);
        rotationTime = (float) (Math.random() * 10f);
        initializeHitbox();
    }

    protected void initializeHitbox() {
        hitbox = getHitboxAt(getX(), getY(), getRotation());
    }

    public void act(float delta) {
        rotationTime += delta;
        while (rotationTime >= 10f) {
            rotationTime -= 10f;
        }
        float rotationMult = 0f;
        if (rotationTime >= 5f) {
            rotationMult = (7.5f - rotationTime) / 2.5f;
        } else {
            rotationMult = (rotationTime - 2.5f) / 2.5f;
        }

        setRotation(30f * rotationMult);
        initializeHitbox();
    }

    @Override
    public Polygon getHitboxAt(float x, float y, float direction) {
        float[] f = new float[8];
        Vector2 v = new Vector2(getWidth(), getHeight());
        v.setAngle(direction);
        v.rotate(45);
        f[0] = x + v.x;
        f[1] = y + v.y;
        v.rotate(90);
        f[2] = x + v.x;
        f[3] = y + v.y;
        v.rotate(90);
        f[4] = x + v.x;
        f[5] = y + v.y;
        v.rotate(90);
        f[6] = x + v.x;
        f[7] = y + v.y;
        return new Polygon(f);
    }
}
