package com.arcralius.ff.lwjgl3.collision;


import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.NonPlayableEntity;
import com.arcralius.ff.lwjgl3.scene.GameplayScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import java.util.List;

public interface CollisionInterface {

    void checkCollisions(BaseEntity player, List<BaseEntity> entityList);


    void handleCollision(BaseEntity a, BaseEntity b);
}
