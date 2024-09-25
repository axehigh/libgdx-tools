package com.axehigh.libgdx.tools.screen.effect;

import com.axehigh.libgdx.tools.XhiMainGame;
import com.badlogic.gdx.Screen;

public class ScreenManager {
    private static XhiMainGame game;

    public static void initialize(XhiMainGame game) {
        ScreenManager.game = game;
    }

    public static void switchScreen(Screen newScreen) {
        game.setScreen(newScreen);
    }

    public static void transitionFadePixelScreen(XhiMainGame game, Screen newScreen) {
        TransitionFadePixelScreen screenEffect = new TransitionFadePixelScreen(game, game.getScreen(), newScreen);
        screenEffect.startTransition();
        game.setScreen(screenEffect);
    }

    public static void transitionFadeScreen(XhiMainGame game, Screen newScreen) {
        TransitionFadeScreen transitionFadeScreen = new TransitionFadeScreen(game, game.getScreen(), newScreen);
        transitionFadeScreen.startTransition();
        game.setScreen(transitionFadeScreen);
    }
}
