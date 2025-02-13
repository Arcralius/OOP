package com.arcralius.ff.lwjgl3.entity;


import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;



public class EntityController {
    private List<BaseEntity> entityList;

    public EntityController(List<BaseEntity> entityList) {
        this.entityList = entityList;
    }



    public void addEntity(BaseEntity entity) {
        entityList.add(entity);

    }

    public void removeEntity(BaseEntity entity) {
        entityList.remove(entity);
    }


    public void draw(SpriteBatch batch) {
        for (BaseEntity entity : entityList) {
            entity.draw(batch);


    }


    }





}





