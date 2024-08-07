package com.example.flappybird;

import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private final SurfaceHolder surfaceHolder;
    private boolean isRunning;
    private long startTime, loopTime;
    private static final long DELAY = 33; // Delay in milliseconds between screen refresh

    public GameThread(SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while (isRunning) {
            startTime = SystemClock.uptimeMillis();

            Canvas canvas = surfaceHolder.lockCanvas(null);
            if (canvas != null) {
                synchronized (surfaceHolder) {
                    AppConstants.getGameEngine().updateAndDraw(canvas);
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }

            loopTime = SystemClock.uptimeMillis() - startTime;

            if (loopTime < DELAY) {
                try {
                    Thread.sleep(DELAY - loopTime);
                } catch (InterruptedException e) {
                    Log.e("InterruptedException", "Interrupted while sleeping");
                }
            }
        }
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean state) {
        this.isRunning = state;
    }
}
