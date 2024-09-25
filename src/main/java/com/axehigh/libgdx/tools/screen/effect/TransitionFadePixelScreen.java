package com.axehigh.libgdx.tools.screen.effect;

import com.axehigh.libgdx.tools.MyLog;
import com.axehigh.libgdx.tools.PixMap;
import com.axehigh.libgdx.tools.XhiMainGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class TransitionFadePixelScreen implements Screen {
    private final XhiMainGame game;
    private Screen currentScreen;
    private Screen nextScreen;
    private float alpha; // Used for fading effect
    private final float transitionDuration = 2.0f; // in seconds
    private boolean transitionStarted = false;
    private boolean fadeOutCompleted = false;
    private Texture pixMap;
    private ShaderProgram pixelationShader;
    private float pixelSize = 1.0f;

    public TransitionFadePixelScreen(XhiMainGame game, Screen currentScreen, Screen nextScreen) {
        this.game = game;
        this.currentScreen = currentScreen;
        this.nextScreen = nextScreen;
        alpha = 0f;
        pixMap = PixMap.getPixMapTexture();
        loadShaders();
    }

    private void loadShaders() {
        // Load pixelation shader
        String vertexShader = Gdx.files.internal("shaders/default.vert").readString();
        String pixelationFragmentShader = Gdx.files.internal("shaders/pixelation.frag").readString();
        pixelationShader = new ShaderProgram(vertexShader, pixelationFragmentShader);

        if (!pixelationShader.isCompiled()) {
            Gdx.app.error("Shader", pixelationShader.getLog());
        }
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
                nextScreen.render(delta);
                alpha -= delta / transitionDuration;
            }

            // Set the shader before rendering the pixelation effect
            game.getBatch().setShader(pixelationShader);
            pixelationShader.setUniformf("u_pixelSize", pixelSize);

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            game.getBatch().begin();
            game.getBatch().setColor(1, 1, 1, alpha);
            game.getBatch().draw(pixMap, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            game.getBatch().end();
            Gdx.gl.glDisable(GL20.GL_BLEND);

            // Reset the shader after rendering
            game.getBatch().setShader(null);

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
