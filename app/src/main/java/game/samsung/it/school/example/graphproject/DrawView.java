package game.samsung.it.school.example.graphproject;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
public class DrawView extends SurfaceView implements SurfaceHolder.Callback {
    private DrwThread drawThread;



    public DrawView(Context context) {
        super(context);

        getHolder().addCallback(this);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        drawThread = new DrwThread(getContext(),getHolder());
        drawThread.start();
        // создание SurfaceView
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format,
                               int width, int height) {
        // изменение размеров SurfaceView
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
                //
            }
        }

        // уничтожение SurfaceView
    }
}


class DrwThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private Paint backgroundPaint = new Paint();
    private int towardPointX;
    private int towardPointY;

    { GraphMatrix gm = new GraphMatrix(7);
        backgroundPaint.setColor(Color.WHITE);
        backgroundPaint.setStyle(Paint.Style.FILL);
    }
    private volatile boolean running = true;//флаг для остановки потока
    public DrwThread(Context context, SurfaceHolder surfaceHolder) {
        this.surfaceHolder = surfaceHolder;
    }
    public void requestStop() {
        running = false;
    }
    @Override
    public void run() {

        Paint paint = new Paint();
        while (running) {
            Canvas canvas = surfaceHolder.lockCanvas();
            if (canvas != null) {
                try {
                    // рисование на canvas
                    paint.setStrokeWidth(5);
                    GraphMatrix gm = new GraphMatrix(7);
                    gm.x = canvas.getWidth() / 2;
                    gm.y = canvas.getHeight() / 2;
                    gm.r = (float) (canvas.getWidth() * 0.4);
                    paint.setStyle(Paint.Style.STROKE);
                    canvas.drawCircle(gm.x, gm.y, gm.r, paint);
                    paint.setStyle(Paint.Style.FILL);


                    gm.getXY(gm.x,gm.y,gm.r);
                    gm.drawLine(canvas);
                    gm.drawCircle(canvas);
                } finally {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}