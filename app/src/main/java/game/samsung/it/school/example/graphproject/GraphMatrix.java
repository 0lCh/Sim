package game.samsung.it.school.example.graphproject;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


/**
 * Created by Оля on 29.03.2017.
 */

public class GraphMatrix {
   private Paint mPaint = new Paint();
   private static GraphNode[] matrix;
   private int ver;
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

    public void deleteEge(int v1, int v2) {
        matrix[v1].list[v2] = -1;
        matrix[v2].list[v1] = -1;
    }

    public void getXY(float x, float y, float r) {
        for (int i = 1; i <= ver; i++) {

            matrix[i].an = (float) (360 / ver * i * Math.PI / 180);
            matrix[i].x = Math.round(x + r * Math.cos(matrix[i].an));
            matrix[i].y = Math.round(y + r * Math.sin(matrix[i].an));
        }

    }

    public void drawCircle(Canvas canvas) {

        for (int i = 1; i <= ver; i++) {
            canvas.drawCircle(matrix[i].x, matrix[i].y, (float) (r * 0.15), mPaint);
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

    public void searchTriangle() {

        for (int a = 1; a <= ver; a++) {
            for (int b = 1; b <= ver; b++) {
                for (int c = 1; c <= ver; c++)
                    if ((matrix[a].list[b] == matrix[b].list[c]) && (matrix[c].list[a] == matrix[a].list[b]) && (matrix[c].list[a] != -1)) {
                        DrawThread.qw = matrix[a].list[b];

                    }
            }
        }

    }

    public int searchNode(int x, int y) {
        for (int i = 1; i <= ver; i++) {

            if (Math.pow((matrix[i].x - x), 2) + Math.pow((matrix[i].y - y), 2) <= Math.pow((r * 0.2), 2))
                return (i);
        }


        return 0;
    }

    public int retweight(int n1, int n2) {
        if (matrix[n1].list[n2] == -1) return 1;
        return 0;
    }
}

