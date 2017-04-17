package game.samsung.it.school.example.graphproject;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;


public class MyDraw extends View {
    ///GraphMatrix gm= new GraphMatrix(9);
    private Paint backgroundPaint = new Paint();
    GraphMatrix gm = new GraphMatrix(7);
       //     backgroundPaint.setColor(Color.WHITE);


    Paint paint = new Paint();


    public MyDraw(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    public MyDraw(Context context) {
        super(context);



    }
    public MyDraw(Context context, AttributeSet attrs) {
        super(context, attrs);
        gm.addEdge(1,4,1);
        gm.addEdge(1,3,1);
        gm.addEdge(1,2,1);
        gm.addEdge(2,4,2);
        gm.addEdge(2,3,1);
        gm.addEdge(1,7,2);
        gm.addEdge(2,7,2);
    }


    @Override
    protected void onDraw(Canvas canvas){

            paint.setStrokeWidth(5);
            gm.x = getWidth() / 2;
            gm.y = getHeight() / 2;
            gm.r = (float) (getWidth() * 0.4);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawCircle(gm.x, gm.y, gm.r, paint);
            paint.setStyle(Paint.Style.FILL);


        gm.getXY(gm.x,gm.y,gm.r);
        gm.drawLine(canvas);
        gm.drawCircle(canvas);


}}


