package com.arcralius.ff.lwjgl3.scene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;

public class UIComponentFactory {

    private final Skin skin;
    private final BitmapFont font;

    public UIComponentFactory(String atlasPath, String fontPath) {
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(atlasPath));
        skin = new Skin(atlas);
        font = new BitmapFont(Gdx.files.internal(fontPath), false);
    }

    public TextButton createTextButton(String text) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.up = skin.getDrawable("menacing");
        style.over = skin.getDrawable("menacing2");
        style.font = font;

        return new TextButton(text, style);
    }

    public Label createLabel(String text) {
        Label.LabelStyle style = new Label.LabelStyle(font, null);
        return new Label(text, style);
    }

    public Skin getSkin() {
        return skin;
    }

    public void dispose() {
        skin.dispose();
        font.dispose();
    }
}
