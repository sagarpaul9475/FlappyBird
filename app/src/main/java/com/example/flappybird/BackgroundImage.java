package com.example.flappybird;

public class BackgroundImage {
    private int backgroundImageX, backgroundImageY, backgroundVelocity;

    public BackgroundImage() {
        backgroundImageX = 0;
        backgroundImageY = 0;
        backgroundVelocity = 3; // Default velocity set to 0
    }

    // Method for getting the x-coordinate
    public int getX() {
        return backgroundImageX;
    }

    // Method for getting the y-coordinate
    public int getY() {
        return backgroundImageY;
    }

    // Method for setting the x-coordinate
    public void setX(int backgroundImageX) {
        this.backgroundImageX = backgroundImageX;
    }

    // Method for setting the y-coordinate
    public void setY(int backgroundImageY) {
        this.backgroundImageY = backgroundImageY;
    }

    // Method for getting the velocity
    public int getVelocity() {
        return backgroundVelocity;
    }

    // Method for setting the velocity
    public void setVelocity(int backgroundVelocity) {
        this.backgroundVelocity = backgroundVelocity;
    }
}
