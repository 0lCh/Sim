package game.samsung.it.school.example.graphproject;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import static android.R.attr.data;

public class MainActivity extends AppCompatActivity {
    static int k = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                    case R.id.radioButton5:
                        k = 5;
                        break;
                    case R.id.radioButton6:
                        k = 6;
                        break;
                    case R.id.radioButton7:
                        k = 7;
                        break;
                    case R.id.radioButton8:
                        k = 8;
                        break;

                    default:
                        break;
                }
            }
        });
    }

    public void play(View view) {
if (k!=0){
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
    intent.putExtra("count", k);
        startActivity(intent);} else                     Toast.makeText(getApplicationContext(), "Выберите количество узлов",
        Toast.LENGTH_SHORT).show();
    }
}
