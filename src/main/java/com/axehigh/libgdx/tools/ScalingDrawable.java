package com.axehigh.libgdx.tools;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public class ScalingDrawable extends BaseDrawable {

    private final TextureRegionDrawable drawable;
    private final float scaleX;
    private final float scaleY;

    public ScalingDrawable(TextureRegionDrawable drawable, float scaleX, float scaleY) {
        this.drawable = drawable;
        this.scaleX = scaleX;
        this.scaleY = scaleY;

        setMinWidth(drawable.getMinWidth() * scaleX);
        setMinHeight(drawable.getMinHeight() * scaleY);
    }

    public ScalingDrawable(TextureRegionDrawable drawable, Scaling scaling, int align, float scaleX, float scaleY) {
        this.drawable = drawable;
        this.scaleX = scaleX;
        this.scaleY = scaleY;

        setMinWidth(drawable.getMinWidth() * scaleX);
        setMinHeight(drawable.getMinHeight() * scaleY);
    }

    @Override
    public void draw(Batch batch, float x, float y, float width, float height) {
        float drawWidth = width * scaleX;
        float drawHeight = height * scaleY;
        drawable.draw(batch, x + (width - drawWidth) / 2, y + (height - drawHeight) / 2, drawWidth, drawHeight);
    }
}
