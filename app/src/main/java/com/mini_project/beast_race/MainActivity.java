package com.mini_project.beast_race;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final boolean[] isValueChanged = {false};
    private TextView point;
    private Button btn_start;
    private CheckBox[] checkBox = new CheckBox[5];
    private SeekBar[] seekBar = new SeekBar[5];
    private EditText[] bet = new EditText[5];
    private int currentMoneyAfter = 0;
    private Handler[] handler = new Handler[5];
    private int[] currentProgress = new int[5];
    private boolean isRaceFinished = false;
    private ImageView guide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_layout);
        point = findViewById(R.id.point);

        btn_start = (Button) findViewById(R.id.btn_start);
        guide = (ImageView) findViewById((R.id.guideline));
        for (int i = 0; i < 5; i++) {
            checkBox[i] = findViewById(getResources().getIdentifier("cb" + (i + 1), "id", getPackageName()));
            seekBar[i] = findViewById(getResources().getIdentifier("sb" + (i + 1), "id", getPackageName()));
            bet[i] = findViewById(getResources().getIdentifier("ed" + (i + 1), "id", getPackageName()));
            handler[i] = new Handler();
            seekBar[i].setMax(1000);
        }
        point.setText(currentMoneyAfter + "");
        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GuideActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRace();
            }
            });
        for (int i = 0; i < 5; i++) {
            final int index = i;
            checkBox[i].setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    bet[index].setEnabled(true);
                    if (!isValueChanged[0]) {
                        bet[index].setText("");
                    }
                } else {
                    bet[index].setEnabled(false);
                    bet[index].setText("0");
                }
            });
        }
    }
    private void startRace() {
        for (int i = 0; i < 5; i++) {
            final int index = i;
            handler[i].postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isRaceFinished && currentProgress[index] < 1000) {
                        Log.e("STARTRACE", "random");
                        int randomIncrement = new Random().nextInt(5) + 1;
                        currentProgress[index] += randomIncrement;
                        seekBar[index].setProgress(currentProgress[index]);
                        if (currentProgress[index] >= 1000) {
                            Log.e("STARTRACE", "endrandom");
                            isRaceFinished = true;
                            updateCurrentMoneyTextView();
                        } else {
                            handler[index].postDelayed(this, 100);
                        }
                    }
                }
            }, 100);
        }
    }

    private boolean checkRaceCompletion() {
        for (int i = 0; i < 5; i++){
            if (currentProgress[i] >= 1000) {
                isRaceFinished = true;
            }
        }
        return isRaceFinished;
    }
    private void updateCurrentMoneyTextView() {
        EditText betAmount1 = findViewById(R.id.ed1);
        EditText betAmount2 = findViewById(R.id.ed2);
        EditText betAmount3 = findViewById(R.id.ed3);
        EditText betAmount4 = findViewById(R.id.ed4);
        EditText betAmount5 = findViewById(R.id.ed5);
        if (currentProgress[0] >= 1000) {
            if (!betAmount1.getText().toString().isEmpty()){
                currentMoneyAfter += 2 * Integer.parseInt(betAmount1.getText().toString());
            }
        } else if (currentProgress[1] >= 1000) {
            if (!betAmount2.getText().toString().isEmpty()){
                currentMoneyAfter += 2 * Integer.parseInt(betAmount2.getText().toString());
            }
        } else if (currentProgress[2] >= 1000) {
            if (!betAmount3.getText().toString().isEmpty()){
                currentMoneyAfter += 2 * Integer.parseInt(betAmount3.getText().toString());
            }
        } else if (currentProgress[3] >= 1000) {
            if (!betAmount4.getText().toString().isEmpty()){
                currentMoneyAfter += 2 * Integer.parseInt(betAmount4.getText().toString());
            }
        } else if (currentProgress[4] >= 1000) {
            if (!betAmount5.getText().toString().isEmpty()){
                currentMoneyAfter += 2 * Integer.parseInt(betAmount5.getText().toString());
            }
        } else {
            return;
        }
        point.setText(currentMoneyAfter + "");
    }
}