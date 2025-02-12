package com.arcralius.ff.lwjgl3.scene;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.arcralius.ff.lwjgl3.entity.*
;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameplayScreen extends BaseScreen {
    private List<BaseEntity> Entities;
    private EntityController em;
    private BaseEntity bucket;
    private Texture texture;   // Declare the Texture (Sprite)
    private SpriteBatch batch; // Declare the SpriteBatch
	private BaseEntity player;



    public GameplayScreen() {
        super();
        this.background = new Texture("gameplay_background.png"); // Load background texture
        this.menuOptions = Arrays.asList("Resume", "Settings", "Exit");
    }




    public void create() {

 //need to find a way to

    }



    @Override
    public void render() {
    	batch = new SpriteBatch();

        Entities = new ArrayList<>();

        bucket = new PlayableEntity("bucket.png", 0,0,"hello",0);

        Entities.add(bucket);

        em = new EntityController(Entities);


        ScreenUtils.clear(0, 0, 0.2f, 1);
        super.render(); //background
        em.draw(batch); //bucket



    }

    @Override
    public void dispose() {
        super.dispose();
    }
}

