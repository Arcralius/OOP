package application.scene;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Align;

public class UIComponentFactory {
    private final Skin skin;
    private BitmapFont font;
    public static final Color FRENCH_BEIGE = new Color(0xa67b5bff); // Add beige color
    public static final Texture boxTexture = new Texture(Gdx.files.internal("button_rectangle.png"));
    public static final TextureRegionDrawable boxDrawable = new TextureRegionDrawable(new TextureRegion(boxTexture));

    public UIComponentFactory(String fontPath) {
        generateFont(fontPath);
        skin = new Skin();
    }

    private void generateFont(String fontPath) {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal(fontPath));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 30;
        font = generator.generateFont(parameter);
        generator.dispose();
    }

    public ImageButton createImageButton(String imagePath) {
        Texture texture = new Texture(imagePath);
        TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
        return new ImageButton(drawable);
    }

    public TextButton createTextButton(String text, Color color, TextureRegionDrawable background) {
        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        style.font = font;
        style.fontColor = color;
        style.up = background; // Set the background
        style.down = background; // Set the background on press
        style.checked = background; // set background on checked
        return new TextButton(text, style);
    }

    public Label createLabel(String text, Color color, TextureRegionDrawable background) {
        Label.LabelStyle style = new Label.LabelStyle(font, color);
        style.background = background;
        Label label = new Label(text, style);
        label.setAlignment(Align.center); // Center the text
        // Adjust the background size by setting the minWidth and minHeight of the background drawable.
        if (style.background != null) {
            style.background.setMinWidth(label.getWidth() + 40); // Adjust as needed
        }
        return label;
    }

    public void dispose() {
        skin.dispose();
        font.dispose();
    }
}
