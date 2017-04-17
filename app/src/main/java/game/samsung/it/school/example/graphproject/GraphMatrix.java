package game.samsung.it.school.example.graphproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by Оля on 29.03.2017.
 */

public class GraphMatrix {
    Paint mPaint = new Paint();
    static GraphNode[] matrix;
    int ver;
    float x;
    float y;
    float r;

    public GraphMatrix(int vertix) {
        matrix = new GraphNode[vertix + 1];
        for (int i = 1; i <= vertix; i++) {
            matrix[i] = new GraphNode(i, vertix + 1);
        }
        ver = vertix;
    }

    public void addEdge(int v1, int v2, int weight) {
        matrix[v1].list[v2] = weight;
        matrix[v2].list[v1] = weight;
    }

    public void getXY(float x, float y, float r) {
        for (int i = 1; i <= ver; i++) {

            matrix[i].an = (float) (360 / ver * i * Math.PI / 180);
            matrix[i].x = Math.round(x + r * Math.cos(matrix[i].an));
            matrix[i].y = Math.round(y + r * Math.sin(matrix[i].an));
            //  Log.i("QWERTY","X"+ matrix[i].x+"Y"+matrix[i].y);
        }

    }

    public void drawCircle(Canvas canvas) {

        for (int i = 1; i <= ver; i++) {
            canvas.drawCircle(matrix[i].x, matrix[i].y, (float) (r * 0.1), mPaint);
        }
    }

    public void drawLine(Canvas canvas) {
        mPaint.setStrokeWidth(5);
        for (int i = 1; i <= ver; i++) {
            for (int j = 1; j <= ver; j++) {
                if (matrix[i].list[j] != -1) {
                    if (matrix[i].list[j] == 1) mPaint.setColor(Color.RED);
                    if (matrix[i].list[j] == 2) mPaint.setColor(Color.BLUE);

                    canvas.drawLine(matrix[i].x, matrix[i].y, matrix[j].x, matrix[j].y, mPaint);
                }
                mPaint.setColor(Color.BLACK);


            }
        }
    }

    public int searchfirstNode(int x, int y) {
        for (int i = 1; i <= ver; i++) {

            if (Math.pow((matrix[i].x - x), 2) + Math.pow((matrix[i].y - y), 2) <= Math.pow((r * 0.1), 2))
                return (i);
        }


        return 0;
    }

    public int searchsecondNode(int x, int y) {
        for (int i = 1; i <= ver; i++) {

            if (Math.pow((matrix[i].x - x), 2) + Math.pow((matrix[i].y - y), 2) <= Math.pow((r * 0.1), 2))
                return (i);
        }

        return 0;
    }

    public void sup(int q1, int q2) {
        if (matrix[q1].list[q2] != -1) Log.i("QWERTY", "TRUE" + matrix[q1].list[q2]);
        else Log.i("QWERTY", "FALL");

    }

    public void drawLine1(Canvas canvas, int q1, int q2) {
        if (matrix[q1].list[q2] == 1) mPaint.setColor(Color.RED);
        if (matrix[q1].list[q2] == 2) mPaint.setColor(Color.BLUE);

        canvas.drawLine(matrix[q1].x, matrix[q1].y, matrix[q2].x, matrix[q2].y, mPaint);

        mPaint.setColor(Color.BLACK);

    }
}

