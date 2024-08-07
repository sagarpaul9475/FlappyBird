package com.example.flappybird;

public class Pole {
    private int x, y, width, height, velocity;
    private boolean passed;
    private boolean isBottomPole;

    public Pole(int x, int y, int width, int height, int velocity, boolean isBottomPole) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.velocity = velocity;
        this.passed = false;
        this.isBottomPole = isBottomPole;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }
    public void setWidth(){
        this.width = width;
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(){
        this.height = height;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public boolean isPassed() {
        return passed;
    }

    public void setPassed(boolean passed) {
        this.passed = passed;
    }

    public boolean isBottomPole() {
        return isBottomPole;
    }

    public void updatePosition() {
        x -= velocity;
        if (x < -width) {
            x = AppConstants.SCREEN_WIDTH;
            y = isBottomPole ? AppConstants.SCREEN_HEIGHT - height : getRandomY();
            passed = false; // Reset passed flag when pole repositions
        }
    }

    private int getRandomY() {
        // Randomize Y position of the top poles
        return (int) (Math.random() * (AppConstants.SCREEN_HEIGHT - height - AppConstants.gapBetweenPoles - AppConstants.getBitmapBank().getPole_height()));
    }
}
