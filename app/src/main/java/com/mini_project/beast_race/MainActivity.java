package com.mini_project.beast_race;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    final boolean[] isValueChanged = {false};
    private TextView point;
    private Button btn_start, btn_reset;
    private CheckBox[] checkBox = new CheckBox[5];
    private SeekBar[] seekBar = new SeekBar[5];
    private EditText[] bet = new EditText[5];
    private int currentMoneyAfter;
    private Handler[] handler = new Handler[5];
    private int[] currentProgress = new int[5];
    private boolean isRaceFinished = false;
    private ImageView guide;
    boolean anyBetValueChanged = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_layout);
        point = findViewById(R.id.point);

        btn_start = (Button) findViewById(R.id.btn_start);
        btn_reset = (Button) findViewById(R.id.btn_reset);
        guide = (ImageView) findViewById((R.id.guideline));
        for (int i = 0; i < 5; i++) {
            checkBox[i] = findViewById(getResources().getIdentifier("cb" + (i + 1), "id", getPackageName()));
            seekBar[i] = findViewById(getResources().getIdentifier("sb" + (i + 1), "id", getPackageName()));
            bet[i] = findViewById(getResources().getIdentifier("ed" + (i + 1), "id", getPackageName()));
            handler[i] = new Handler();
            seekBar[i].setMax(1000);
            bet[i].setEnabled(false);
            bet[i].setText("0");
        }
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
                currentMoneyAfter = Integer.parseInt(point.getText().toString());
                for (int i = 0; i < 5; i++) {
                    if (checkBox[i].isChecked() && !bet[i].getText().toString().isEmpty()) {
                        anyBetValueChanged = true;
                        break;
                    }
                }
                isValueChanged[0] = anyBetValueChanged;
                startRace();
            }
        });
        for (int i = 0; i < 5; i++) {
            final int index = i;
            checkBox[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        bet[index].setEnabled(true);
                        if (!isValueChanged[0]) {
                            bet[index].setText("");
                        }
                    } else {
                        bet[index].setEnabled(false);
                        bet[index].setText("0");
                    }
                }
            });
            bet[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // Không cần xử lý trước khi văn bản thay đổi
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    // Không cần xử lý khi văn bản thay đổi
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // Sau khi văn bản đã thay đổi, cập nhật số tiền hiện tại
                    String betText = s.toString();
                    int totalBetAmount = 0;
                    for (int i = 0; i < 5; i++) {
                        if (checkBox[i].isChecked() && !bet[i].getText().toString().isEmpty()) {
                            totalBetAmount += Integer.parseInt(bet[i].getText().toString());
                        }
                    }
                    if (!betText.isEmpty()) {
                        // Kiểm tra điều kiện đặt cược so với số tiền hiện tại
                        if (totalBetAmount > currentMoneyAfter) {
                            // Hiển thị thông báo cho người dùng
                            Toast.makeText(MainActivity.this, "Số tiền đặt cược vượt quá số tiền hiện có", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
            });
        }

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetRace();
            }
        });
    }
    private void startRace() {
        int totalBetAmount = 0;
        for (int i = 0; i < 5; i++) {
            if (checkBox[i].isChecked() && !bet[i].getText().toString().isEmpty()) {
                totalBetAmount += Integer.parseInt(bet[i].getText().toString());
            }
        }
        // Trừ tổng số tiền đã đặt cược khỏi số tiền hiện tại
        currentMoneyAfter -= totalBetAmount;

        // Cập nhật số tiền hiện tại trên TextView
        point.setText(String.valueOf(currentMoneyAfter));
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
        btn_start.setEnabled(false);
    }
    private void resetRace() {
        // Đặt lại vị trí của tất cả các seekBar về 0
        for (int i = 0; i < 5; i++) {
            currentProgress[i] = 0;
            checkBox[i].setChecked(false);
            bet[i].setText("0");
            seekBar[i].setProgress(0);
        }
        isRaceFinished = false;
        btn_start.setEnabled(true);
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
        point.setText(String.valueOf(currentMoneyAfter));
    }
}