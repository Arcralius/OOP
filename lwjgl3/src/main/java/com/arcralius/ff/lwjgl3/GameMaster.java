package com.arcralius.ff.lwjgl3;
//...........................................
import com.arcralius.ff.lwjgl3.entity.*;
import com.arcralius.ff.lwjgl3.entity.BaseEntity;
import com.arcralius.ff.lwjgl3.entity.EntityController;
import com.arcralius.ff.lwjgl3.input_output.*;
import com.arcralius.ff.lwjgl3.scene.*;

//...................................................

import com.badlogic.gdx.ApplicationAdapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random; //random numbers


public class GameMaster extends ApplicationAdapter{

    private SceneController sceneController;


    public GameMaster() {
        this.sceneController = new SceneController();
    }


    @Override
    public void create() {

        sceneController = new SceneController();
        sceneController.changeScreen(new GameplayScreen()); // Start with GameplayScreen




    }
    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        sceneController.render();



    }

    @Override
    public void dispose() {
        System.out.println("Disposing resources...");

        // Dispose batch




        // Loop through and dispose all entities


        // Dispose EntityManager
    }


}
