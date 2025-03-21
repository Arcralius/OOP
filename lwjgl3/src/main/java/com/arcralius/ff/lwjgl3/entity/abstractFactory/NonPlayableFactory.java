package com.arcralius.ff.lwjgl3.entity.abstractFactory;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.NonPlayableEntity;
import com.arcralius.ff.lwjgl3.entity.PlayableEntity;

public class NonPlayableFactory implements Entity_abstract_factory {
    private String id, texturePath;
    private float x,y, speed, height,width;

    public  NonPlayableFactory(){
        this.id = "";
        this.texturePath = "";
        this.x = 0;
        this.y = 0;
        this.speed = 0;
        this.height =0;
        this.width = 0;
}


public NonPlayableFactory( String id, String texturePath, float x, float y, float speed, float width, float height){
    this.id = id;
    this.texturePath = texturePath;
    this.x = x;
    this.y = y;
    this.speed = speed;
    this.height = height;
    this.width = width;
}

@Override
public BaseEntity createEntity(){
    return new NonPlayableEntity(this.texturePath,this.x,this.y,this.id,this.speed,this.width,this.height);
}

}

