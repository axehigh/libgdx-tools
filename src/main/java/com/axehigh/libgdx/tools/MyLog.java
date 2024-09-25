package com.axehigh.libgdx.tools;

import com.badlogic.gdx.Gdx;

public class MyLog {

    public static void log(String tag, String txt) {
        if (Gdx.app == null) {
            System.out.println(tag + ":" + txt);
        } else {
            Gdx.app.log(tag, txt);
        }
    }

    public static void log(Object clazz, String txt) {
        log(clazz.getClass().getSimpleName(), txt);
    }

    public static void log(String txt, Object clazz) {
        log(clazz.getClass().getSimpleName(), txt);
    }

    public static void log(String txt) {
        log("", txt);
    }
}
