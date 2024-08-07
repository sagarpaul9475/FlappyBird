package com.example.flappybird;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;

public class GameEngine {
    private BackgroundImage backgroundImage;
    private Bird bird;
    private Pole[] topPoles;
    private Pole[] bottomPoles;
    private int numberOfPoles = 2;
    private int score;
    private Paint paint;

    static int gameState;

    private MediaPlayer tapSound;
    private MediaPlayer collisionSound;
    private MediaPlayer gameOverSound;
    private Context context;

    public GameEngine(Context context) {
        this.context = context;
        backgroundImage = new BackgroundImage();
        bird = new Bird();
        topPoles = new Pole[numberOfPoles];
        bottomPoles = new Pole[numberOfPoles];
        for (int i = 0; i < numberOfPoles; i++) {
            int x = AppConstants.SCREEN_WIDTH + i * (AppConstants.SCREEN_WIDTH / 2);
            int[] poleYPositions = getRandomPoleY();
            topPoles[i] = new Pole(x, poleYPositions[0], AppConstants.getBitmapBank().getPole_width(), AppConstants.getBitmapBank().getPole_height(), 10, false);
            bottomPoles[i] = new Pole(x, poleYPositions[1], AppConstants.getBitmapBank().getPole_width(), AppConstants.getBitmapBank().getPole_height(), 10, true);
        }
        gameState = 0;
        score = 0;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setTextSize(100);

        // Initialize sound effects
        loadSounds();
    }

    private void loadSounds() {
        releaseSounds();
        tapSound = MediaPlayer.create(context, R.raw.chirps);
        collisionSound = MediaPlayer.create(context, R.raw.polehit);
        gameOverSound = MediaPlayer.create(context, R.raw.chirp);
    }

    private void releaseSounds() {
        if (tapSound != null) {
            tapSound.release();
            tapSound = null;
        }
        if (collisionSound != null) {
            collisionSound.release();
            collisionSound = null;
        }
        if (gameOverSound != null) {
            gameOverSound.release();
            gameOverSound = null;
        }
    }

    public void updateAndDraw(Canvas canvas) {
        updateAndDrawBackgroundImage(canvas);
        updateAndDrawPoles(canvas);
        updateAndDrawBird(canvas);
        drawScore(canvas);
        checkCollisions();
    }

    private void updateAndDrawBackgroundImage(Canvas canvas) {
        backgroundImage.setX(backgroundImage.getX() - backgroundImage.getVelocity());
        if (backgroundImage.getX() < -AppConstants.getBitmapBank().getBackground_game_width()) {
            backgroundImage.setX(0);
        }
        canvas.drawBitmap(AppConstants.getBitmapBank().getBackground_game(), backgroundImage.getX(), backgroundImage.getY(), null);
        if (backgroundImage.getX() < -(AppConstants.getBitmapBank().getBackground_game_width() - AppConstants.SCREEN_WIDTH)) {
            canvas.drawBitmap(AppConstants.getBitmapBank().getBackground_game(), backgroundImage.getX() + AppConstants.getBitmapBank().getBackground_game_width(), backgroundImage.getY(), null);
        }
    }

    private void updateAndDrawBird(Canvas canvas) {
        if (gameState == 1) {
            if ((bird.getY() < (AppConstants.SCREEN_HEIGHT - AppConstants.getBitmapBank().getBird_height()) && bird.getY() > 0) || bird.getVelocity() < 0) {
                bird.setVelocity(bird.getVelocity() + AppConstants.gravity);
                bird.setY(bird.getY() + bird.getVelocity());
            }
        }
        int currentFrame = bird.getCurrentFrame();
        canvas.drawBitmap(AppConstants.getBitmapBank().getBird(currentFrame), bird.getX(), bird.getY(), null);
        currentFrame++;
        if (currentFrame > bird.maxFrame) {
            currentFrame = 0;
        }
        bird.setCurrentFrame(currentFrame);
    }

    private void updateAndDrawPoles(Canvas canvas) {
        if (gameState == 1) {
            for (int i = 0; i < numberOfPoles; i++) {
                topPoles[i].updatePosition();
                bottomPoles[i].updatePosition();
                if (topPoles[i].getX() < bird.getX() && !topPoles[i].isPassed()) {
                    score++;
                    topPoles[i].setPassed(true);
                }
                if (topPoles[i].getX() < -topPoles[i].getWidth()) {
                    topPoles[i].setX(AppConstants.SCREEN_WIDTH);
                    int[] poleYPositions = getRandomPoleY();
                    topPoles[i].setY(poleYPositions[0]);
                    bottomPoles[i].setX(AppConstants.SCREEN_WIDTH);
                    bottomPoles[i].setY(poleYPositions[1]);
                    topPoles[i].setPassed(false);
                }
                canvas.drawBitmap(AppConstants.getBitmapBank().getPoleTop(), topPoles[i].getX(), topPoles[i].getY(), null);
                canvas.drawBitmap(AppConstants.getBitmapBank().getPoleBottom(), bottomPoles[i].getX(), bottomPoles[i].getY(), null);
            }
        }
    }

    private void checkCollisions() {
        for (int i = 0; i < numberOfPoles; i++) {
            // Get bird dimensions
            int birdLeft = bird.getX();
            int birdRight = bird.getX() + AppConstants.getBitmapBank().getBird_width();
            int birdTop = bird.getY();
            int birdBottom = bird.getY() + AppConstants.getBitmapBank().getBird_height();

            // Get top pole dimensions
            int topPoleLeft = topPoles[i].getX();
            int topPoleRight = topPoles[i].getX() + topPoles[i].getWidth();
            int topPoleTop = topPoles[i].getY();
            int topPoleBottom = topPoles[i].getY() + topPoles[i].getHeight();

            // Get bottom pole dimensions
            int bottomPoleLeft = bottomPoles[i].getX();
            int bottomPoleRight = bottomPoles[i].getX() + bottomPoles[i].getWidth();
            int bottomPoleTop = bottomPoles[i].getY();
            int bottomPoleBottom = bottomPoles[i].getY() + bottomPoles[i].getHeight();

            // Check collision with top pole
            if (birdRight > topPoleLeft && birdLeft < topPoleRight &&
                    birdBottom > topPoleTop && birdTop < topPoleBottom) {
                gameState = 2;
                playCollisionSound();
                return; // End the check if a collision is detected
            }

            // Check collision with bottom pole
            if (birdRight > bottomPoleLeft && birdLeft < bottomPoleRight &&
                    birdBottom > bottomPoleTop && birdTop < bottomPoleBottom) {
                gameState = 2;
                playCollisionSound();
                return; // End the check if a collision is detected
            }
        }

        // Check if the bird touches the top or bottom of the screen
        if (bird.getY() >= AppConstants.SCREEN_HEIGHT - AppConstants.getBitmapBank().getBird_height() || bird.getY() <= 0) {
            gameState = 2;
            playGameOverSound();
        }
    }




    private void drawScore(Canvas canvas) {
        // Draw the score at the top left corner
        canvas.drawText("Score: " + score, 50, 100, paint);

        // Draw the "Game Over" text if the game state is 2 (game over)
        if (gameState == 2) {
            String gameOverText = "Game Over";

            // Measure the width and height of the text
            float textWidth = paint.measureText(gameOverText);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float textHeight = fontMetrics.bottom - fontMetrics.top;

            // Calculate the x and y positions to center the text on the canvas
            float x = (canvas.getWidth() - textWidth) / 2;
            float y = (canvas.getHeight() + textHeight) / 2;

            // Draw the "Game Over" text at the center of the canvas
            canvas.drawText(gameOverText, x, y, paint);
        }
    }

    public void playTapSound() {
        if (tapSound != null) {
            tapSound.start();
        }
    }

    private void playCollisionSound() {
        if (collisionSound != null) {
            collisionSound.start();
        }
    }

    private void playGameOverSound() {
        if (gameOverSound != null) {
            gameOverSound.start();
        }
    }

    private int[] getRandomPoleY() {
        int basePoleHeight = AppConstants.getBitmapBank().getPole_height();
        int minPoleGap = AppConstants.gapBetweenPoles;
        int maxAdditionalGap = 100; // Adjust this value to control the maximum additional gap
        int maxAdditionalBottomHeight = 300; // Maximum additional height for bottom pole

        // Randomly increase the gap
        int actualGap = minPoleGap + (int)(Math.random() * maxAdditionalGap);

        // Randomly increase bottom pole height
        int bottomPoleExtraHeight = (int)(Math.random() * maxAdditionalBottomHeight);
        int bottomPoleHeight = basePoleHeight + bottomPoleExtraHeight;

        //Randomly increase the top pole height

        int topPoleExtraHeight = (int)(Math.random() * maxAdditionalBottomHeight);
        int topPoleHeight = basePoleHeight + topPoleExtraHeight;

        int topPoleY = 0; // Ensures the top pole touches the upper screen
        int bottomPoleY = AppConstants.SCREEN_HEIGHT - bottomPoleHeight;

        // Adjust top pole position if necessary
        int availableSpace = AppConstants.SCREEN_HEIGHT - bottomPoleHeight - actualGap;
        if (availableSpace < basePoleHeight) {
            bottomPoleY = basePoleHeight + actualGap;
        } else {
            int maxTopPoleY = availableSpace - basePoleHeight;
            topPoleY = (int)(Math.random() * maxTopPoleY);
        }

        return new int[]{topPoleY, bottomPoleY, basePoleHeight, bottomPoleHeight};
    }
    public Bird getBird() {
        return bird;
    }
}
