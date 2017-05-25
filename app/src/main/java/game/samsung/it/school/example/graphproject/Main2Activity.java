package game.samsung.it.school.example.graphproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {

    static int k = 0;
    static String nm1;
    static String nm2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DrawThread.qw = 0;
        setContentView(R.layout.activity_main);

        k = getIntent().getIntExtra("count", 0);
        nm1 = getIntent().getStringExtra("name1");
        nm2 = getIntent().getStringExtra("name2");
    }

    public void backb(View view) {
        DrawThread.qw = 0;
        DrawThread.t = 0;
        finish();
        finishActivity(1);
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);

        startActivity(intent);

        onPause();
        onStop();


    }


}
