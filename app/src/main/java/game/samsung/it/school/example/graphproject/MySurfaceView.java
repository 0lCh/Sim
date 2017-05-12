package game.samsung.it.school.example.graphproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Size;
import android.util.AttributeSet;
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

    public MySurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getHolder().addCallback(this);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getHolder().addCallback(this);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
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
        synchronized (this) {
            if ((drawThread.t % 2 == 0))
                drawThread.searchFirstNode((int) event.getX(), (int) event.getY());
            if (drawThread.t % 2 == 1)
                drawThread.searchSecondNode((int) event.getX(), (int) event.getY());
        }
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
    private int FirstNode = 0;
    private int SecondNode = 0;
    public static int qw = 0;

    private GraphMatrix graphMatrix = new GraphMatrix(MainActivity.k);


    public static int t = 0;

    public DrawThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;

    }

    public void searchFirstNode(int x, int y) {

        FirstNode = graphMatrix.searchNode(x, y);
        if ((FirstNode != 0)) t++;
    }

    public void searchSecondNode(int x, int y) {
        SecondNode = graphMatrix.searchNode(x, y);
        if ((SecondNode != 0) && (FirstNode != SecondNode) && (FirstNode != 0)) {
            if (graphMatrix.retweight(FirstNode, SecondNode) == 1) t++;
            else t--;
        }
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
            if (canvas != null) try {
                canvas.drawColor(Color.WHITE);
                paint.setStrokeWidth(5);


                graphMatrix.x = canvas.getWidth() / 2;
                graphMatrix.y = canvas.getHeight() / 2;
                graphMatrix.r = (float) (canvas.getWidth() * 0.4);
                paint.setStyle(Paint.Style.STROKE);
                canvas.drawCircle(graphMatrix.x, graphMatrix.y, graphMatrix.r, paint);
                paint.setStyle(Paint.Style.FILL);


                graphMatrix.getXY(graphMatrix.x, graphMatrix.y, graphMatrix.r);

                graphMatrix.drawCircle(canvas);
                if (qw == 0) {
                    if (((FirstNode != 0) && (FirstNode != SecondNode) && (SecondNode != 0) && (graphMatrix.retweight(FirstNode, SecondNode) == 1) && (t % 2 == 0)) ||
                            ((FirstNode != SecondNode) && (SecondNode != 0) && (FirstNode != 0) && (t % 2 == 0) && (t == 2))) {
                        if (t / 2 % 2 == 0) graphMatrix.addEdge(FirstNode, SecondNode, 2);
                        if (t / 2 % 2 == 1) graphMatrix.addEdge(FirstNode, SecondNode, 1);

                    }
                }
                graphMatrix.searchTriangle();
                graphMatrix.drawLine(canvas);
                graphMatrix.drawCircle(canvas);
                if (qw != 0) {


                    paint.setColor(Color.parseColor("#e0C0C0C0"));
                    canvas.drawRect((float) (graphMatrix.x - graphMatrix.x * 0.95), (float) (graphMatrix.y - graphMatrix.y * 0.95), (float) (graphMatrix.x + graphMatrix.x * 0.95), (float) (graphMatrix.y + graphMatrix.y * 0.95), paint);
                    paint.setColor(Color.parseColor("#b0ffffff"));
                    canvas.drawRect((float) (graphMatrix.x - graphMatrix.x * 0.9), (float) (graphMatrix.y - graphMatrix.y * 0.9), (float) (graphMatrix.x + graphMatrix.x * 0.9), (float) (graphMatrix.y + graphMatrix.y * 0.9), paint);
                    paint.setColor(Color.BLACK);
                    paint.setTextSize((float) (graphMatrix.x * 0.2));
                    if (qw == 2) {
                        paint.setColor(Color.RED);
                        canvas.drawText("Выиграл игрок", graphMatrix.x, graphMatrix.y, paint);
                        canvas.drawText(Main2Activity.nm1, (float) (graphMatrix.x - 0.5 * graphMatrix.x), (float) (graphMatrix.y - (graphMatrix.x * 0.3)), paint);
                    }
                    paint.setTextAlign(Paint.Align.CENTER);
                    if (qw == 1) {
                        paint.setColor(Color.BLUE);

                        canvas.drawText("Выиграл игрок", graphMatrix.x, graphMatrix.y, paint);
                        canvas.drawText(Main2Activity.nm2, graphMatrix.x, (int) (graphMatrix.y + graphMatrix.y * 0.2), paint);
                    }
                }
            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}