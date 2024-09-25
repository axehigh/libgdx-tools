package com.axehigh.libgdx.tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract  class XhiMainGame extends Game {

    private SpriteBatch batch;

    public SpriteBatch getBatch() {
        return batch;
    }
}
