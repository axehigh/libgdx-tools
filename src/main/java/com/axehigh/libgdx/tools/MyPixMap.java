package com.axehigh.libgdx.tools;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

public class MyPixMap {

    public static Pixmap getPixMapBlack() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0, 0, 0, 1); // Set color to black with alpha 1
        pixmap.fill(); // Fill the pixmap with the chosen color
        return pixmap;
    }

    public static Texture getPixMapTexture() {
        return new Texture(getPixMapBlack());
    }
}
