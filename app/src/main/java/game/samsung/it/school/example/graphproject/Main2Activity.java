package game.samsung.it.school.example.graphproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    static int t = 0;
    static int k=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SurfaceView mySurface = (SurfaceView) findViewById(R.id.surfaceView2);
        k = getIntent().getIntExtra("count", 0);

    }


    public void backb(View view) {

        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        startActivity(intent);
        DrawThread.qw=0;
    }




}
