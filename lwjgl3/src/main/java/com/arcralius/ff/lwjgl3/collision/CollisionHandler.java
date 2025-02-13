package com.arcralius.ff.lwjgl3.collision;

import com.arcralius.ff.lwjgl3.entity.*;

import java.util.ArrayList;

public class CollisionHandler implements CollisionInterface{

    @Override
    public void handleCollision(BaseEntity entityA, BaseEntity entityB) {
        if(entityA instanceof PlayableEntity && entityB instanceof NonPlayableEntity){
            System.out.println(("playableEntity colided with NonplayableEntity!"));

        }
    }


}
