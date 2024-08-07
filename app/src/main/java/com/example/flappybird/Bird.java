package com.example.flappybird;

public class Bird {
    private int birdX, birdY, currentFrame, velocity;
    public static int maxFrame;

    public Bird() {
        birdX = AppConstants.SCREEN_WIDTH / 2 - AppConstants.getBitmapBank().getBird_width() / 2;
        birdY = AppConstants.SCREEN_HEIGHT / 2 - AppConstants.getBitmapBank().getBird_height() / 2;
        currentFrame = 0;
        maxFrame = 3;
        velocity = 0;
    }

    // Getter method for velocity
    public int getVelocity() {
        return velocity;
    }

    // Setter method for velocity
    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    // Getter method for current frame
    public int getCurrentFrame() {
        return currentFrame;
    }

    // Setter method for current frame
    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    // Coordinates of bird
    // Getter method for birdX
    public int getX() {
        return birdX;
    }

    // Getter method for birdY
    public int getY() {
        return birdY;
    }

    // Setter method for birdX
    public void setX(int birdX) {
        this.birdX = birdX;
    }

    // Setter method for birdY
    public void setY(int birdY) {
        this.birdY = birdY;
    }

    // Getter for bird width
    public int getWidth() {
        return AppConstants.getBitmapBank().getBird_width()/2;
    }

    // Getter for bird height
    public int getHeight() {
        return AppConstants.getBitmapBank().getBird_height()/2;
    }
}
