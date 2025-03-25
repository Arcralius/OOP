package com.arcralius.ff.lwjgl3.entity.abstractFactory;

import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.PlayableEntity;
import com.badlogic.gdx.utils.compression.lzma.Base;

public class PlayableFactory implements Entity_abstract_factory {
    private String type, texturePath;
    private float x,y, speed, height,width, hp;

    public PlayableFactory(){
        this.type = "";
        this.texturePath = "";
        this.x = 0;
        this.y = 0;
        this.speed = 0;
        this.height =0;
        this.width = 0;
        this.hp = 0;
    }


    public PlayableFactory( String type, String texturePath, float x, float y, float speed, float width, float height, float hp){
        this.type = type;
        this.texturePath = texturePath;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.height = height;
        this.width = width;
        this.hp = hp;
    }

    @Override
    public BaseEntity createEntity(){
        return new PlayableEntity(this.texturePath,this.x,this.y,this.type,this.speed,this.width,this.height, this.hp);
    }
}
