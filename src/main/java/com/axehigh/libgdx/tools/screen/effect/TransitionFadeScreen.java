package com.axehigh.libgdx.tools.screen.effect;

import com.axehigh.libgdx.tools.MyLog;
import com.axehigh.libgdx.tools.PixMap;
import com.axehigh.libgdx.tools.XhiMainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

public class TransitionFadeScreen implements Screen {
    private final XhiMainGame game;
    private Screen currentScreen;
    private Screen nextScreen;
    private float alpha; // Used for fading effect
    private final float transitionDuration = 0.5f; // in seconds
    private boolean transitionStarted = false;
    private boolean fadeOutCompleted = false;

    private Texture pixMap;

    public TransitionFadeScreen(XhiMainGame game, Screen currentScreen, Screen nextScreen) {
        this.game = game;
        this.currentScreen = currentScreen;
        this.nextScreen = nextScreen;
        alpha = 0f;

        pixMap = PixMap.getPixMapTexture();
    }

    @Override
    public void show() {
        // Setup any necessary resources
    }

    @Override
    public void render(float delta) {
        if (!transitionStarted) {
            // Render the current screen until the transition starts
            currentScreen.render(delta);
        } else {
            // Update alpha based on time elapsed
            if (!fadeOutCompleted) {
                alpha += delta / transitionDuration;
                currentScreen.render(delta);
            } else {
//            if (alpha >= 1.0f) {
                nextScreen.render(delta);
                alpha -= delta / transitionDuration;
//                // Render the next screen only when alpha is >= 1.0f
//            }
            }


            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            game.getBatch().begin();
            game.getBatch().setColor(1, 1, 1, alpha);
            game.getBatch().draw(pixMap, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            game.getBatch().end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            // If the transition is complete, switch to the next screen
            if (alpha >= 1.0f) {
                MyLog.log("Transition complete");
                fadeOutCompleted = true;
            }
            if (alpha < 0.0f && fadeOutCompleted) {
                MyLog.log("Switching screens");
                game.setScreen(nextScreen);
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        currentScreen.resize(width, height);
        nextScreen.resize(width, height);
    }

    @Override
    public void pause() {
        currentScreen.pause();
        nextScreen.pause();
    }

    @Override
    public void resume() {
        currentScreen.resume();
        nextScreen.resume();
    }

    @Override
    public void hide() {
        // Dispose of any resources
    }

    @Override
    public void dispose() {
        // Dispose of any resources
    }

    public void startTransition() {
        transitionStarted = true;
        fadeOutCompleted = false;
    }
}
