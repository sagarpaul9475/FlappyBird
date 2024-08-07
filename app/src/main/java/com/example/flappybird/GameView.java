package com.example.flappybird;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameThread gameThread;
    private GameEngine gameEngine;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        InitView(context);
    }

    public GameView(Context context) {
        super(context);
        InitView(context);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        if (gameThread == null) {
            gameThread = new GameThread(surfaceHolder);
            gameThread.setRunning(true);
            gameThread.start();
        } else {
            gameThread.setRunning(true);
            gameThread.start();
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int format, int width, int height) {
        // No action needed
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        boolean retry = true;
        gameThread.setRunning(false);
        while (retry) {
            try {
                gameThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // Retry stopping the thread
            }
        }
    }

    private void InitView(Context context) {
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);
        gameEngine = new GameEngine(context);
        AppConstants.setGameEngine(gameEngine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (gameEngine.gameState == 0) {
                gameEngine.gameState = 1;
            } else if (gameEngine.gameState == 1) {
                gameEngine.getBird().setVelocity(AppConstants.jumpVelocity);
                gameEngine.playTapSound();
            } else if (gameEngine.gameState == 2) {
                gameEngine.gameState = 0;
                gameEngine = new GameEngine(getContext());
                AppConstants.setGameEngine(gameEngine);
            }
        }
        return true;
    }

}
