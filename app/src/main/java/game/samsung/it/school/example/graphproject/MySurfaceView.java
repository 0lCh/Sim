package game.samsung.it.school.example.graphproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private DrawThread drawThread;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getContext(), getHolder());
        drawThread.start();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (drawThread.t % 2 == 0)
            drawThread.setTowardPoint((int) event.getX(), (int) event.getY());
        else drawThread.setTowardPoint2((int) event.getX(), (int) event.getY());

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        drawThread.requestStop();
        boolean retry = true;
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }

    }
}

class DrawThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private volatile boolean running = true;
    private int towardPointX1;
    private int towardPointY1;
    private int towardPointX2;
    private int towardPointY2;
    GraphMatrix gm = new GraphMatrix(7);
    int search1;
    int search2;
    int number;
    int s1;
    static int m = 0;

    int t = 0;

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;

    }

    public void setTowardPoint(int x, int y) {
        towardPointX1 = x;
        towardPointY1 = y;
        t++;
    }

    public void setTowardPoint2(int x, int y) {
        towardPointX2 = x;
        towardPointY2 = y;
        t++;
    }

    public void requestStop() {
        running = false;
    }

    @Override
    public void run() {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    canvas.drawColor(Color.WHITE);
                    paint.setStrokeWidth(5);


                    gm.x = canvas.getWidth() / 2;
                    gm.y = canvas.getHeight() / 2;
                    gm.r = (float) (canvas.getWidth() * 0.4);
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawCircle(gm.x, gm.y, gm.r, paint);
                    paint.setStyle(Paint.Style.FILL);


                    gm.getXY(gm.x, gm.y, gm.r);

                    gm.drawCircle(canvas);


                    search1 = gm.searchfirstNode(towardPointX1, towardPointY1);

                    if (search1 != 0) {

                        Log.i("QWERTY", "QWE" + t);
                        search2 = gm.searchsecondNode(towardPointX2, towardPointY2);
                        if (search2 != 0) {
                           // if (m % 2 == 1) number = 2;
                         //   if (m % 2 == 0) number = 1;

                            gm.addEdge(search1, search2, 1);

                            Log.i("QWERTY", "qwe" + search1 + search2);
                        //  m++;
                        }

                    }
                    gm.drawLine(canvas);
                    gm.drawCircle(canvas);
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}