package com.example.flappybird;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapBank {
    private Bitmap background_game;
    private Bitmap[] bird;
    private Bitmap poleTop;
    private Bitmap poleBottom;

    private static final float BIRD_SCALE_FACTOR = 0.6f; // Adjust this value to scale the bird

    public BitmapBank(Resources resources) {
        background_game = BitmapFactory.decodeResource(resources, R.drawable.background_game);
        background_game = scaleImage(background_game);

        bird = new Bitmap[4];
        for (int i = 0; i < bird.length; i++) {
            Bitmap originalBird = BitmapFactory.decodeResource(resources, getBirdFrameResourceId(i));
            bird[i] = scaleImage(originalBird, BIRD_SCALE_FACTOR);
        }

        poleTop = BitmapFactory.decodeResource(resources, R.drawable.image);
        poleBottom = BitmapFactory.decodeResource(resources, R.drawable.pole);
    }

    private int getBirdFrameResourceId(int frameIndex) {
        switch (frameIndex) {
            case 0: return R.drawable.bird_frame1;
            case 1: return R.drawable.bird_frame2;
            case 2: return R.drawable.bird_frame3;
            case 3: return R.drawable.bird_frame4;
            default: throw new IllegalArgumentException("Invalid frame index");
        }
    }

    public Bitmap getBird(int frame) {
        return bird[frame];
    }

    public int getBird_width() {
        return bird[0].getWidth();
    }

    public int getBird_height() {
        return bird[0].getHeight();
    }

    public Bitmap getBackground_game() {
        return background_game;
    }

    public int getBackground_game_width() {
        return background_game.getWidth();
    }

    public int getBackground_game_height() {
        return background_game.getHeight();
    }

    public Bitmap getPoleTop() {
        return poleTop;
    }

    public int getPole_width() {
        return poleTop.getWidth();
    }

    public int getPole_height() {
        return poleTop.getHeight();
    }

    public Bitmap getPoleBottom() {
        return poleBottom;
    }

    public int getPoleBottom_width() {
        return poleBottom.getWidth();
    }

    public int getPoleBottom_height() {
        return poleBottom.getHeight();
    }

    private Bitmap scaleImage(Bitmap bitmap, float scaleFactor) {
        int newWidth = Math.round(bitmap.getWidth() * scaleFactor);
        int newHeight = Math.round(bitmap.getHeight() * scaleFactor);
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }

    private Bitmap scaleImage(Bitmap bitmap) {
        float widthHeightRatio = (float) bitmap.getWidth() / bitmap.getHeight();
        int backgroundScaledWidth = (int) (widthHeightRatio * AppConstants.SCREEN_HEIGHT);
        return Bitmap.createScaledBitmap(bitmap, backgroundScaledWidth, AppConstants.SCREEN_HEIGHT, false);
    }
}
