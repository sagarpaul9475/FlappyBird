package com.example.flappybird;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
public class AppConstants {
    static BitmapBank bitmapBank;
    static GameEngine gameEngine;
    static int SCREEN_WIDTH;
    static int SCREEN_HEIGHT;
    static int gravity;
    static int jumpVelocity;
    static int gapBetweenPoles;

    public static void initialization(Context context) {
        setScreenSize(context);
        bitmapBank = new BitmapBank(context.getResources());
        gameEngine = new GameEngine(context);
        gravity = 3;
        jumpVelocity = -25;
        gapBetweenPoles = 400; // Adjust this value to change the gap between poles
    }

    public static BitmapBank getBitmapBank() {
        return bitmapBank;
    }

    public static GameEngine getGameEngine() {
        return gameEngine;
    }

    public static void setGameEngine(GameEngine gameEngine) {
        AppConstants.gameEngine = gameEngine;
    }

    private static void setScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        SCREEN_WIDTH = size.x;
        SCREEN_HEIGHT = size.y;
    }
}
