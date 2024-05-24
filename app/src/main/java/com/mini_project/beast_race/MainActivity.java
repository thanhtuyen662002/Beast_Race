package com.mini_project.beast_race;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
    private EditText topUp;
    private Button btn_start, btn_reset;
    private CheckBox[] checkBox = new CheckBox[5];
    private SeekBar[] seekBar = new SeekBar[5];
    private EditText[] bet = new EditText[5];
    private int currentMoneyAfter;
    private Handler[] handler = new Handler[5];
    private int[] currentProgress = new int[5];
    private boolean isRaceFinished = false;
    private ImageView guide;

    // MediaPlayer
    private MediaPlayer mediaPlayer;
    boolean anyBetValueChanged = false;
    private boolean isResetting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_layout);
        point = findViewById(R.id.point);
        currentMoneyAfter = Integer.parseInt(point.getText().toString());
        topUp = findViewById(R.id.topUp);
        topUp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus && topUp.getText().toString().equals("0")) {
                    topUp.setText("");
                } else if (!hasFocus && topUp.getText().toString().isEmpty()) {
                    topUp.setText("0");
                }
            }
        });
        topUp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE|| actionId == EditorInfo.IME_ACTION_NEXT) {
                    // Lấy số tiền được nhập từ EditText
                    String topUpAmountStr = topUp.getText().toString();
                    if (!topUpAmountStr.isEmpty()) {
                        int topUpAmount = Integer.parseInt(topUpAmountStr);
                        currentMoneyAfter += topUpAmount;
                        point.setText(String.valueOf(currentMoneyAfter));

                    }
                    topUp.setText("0");
                    topUp.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(topUp.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
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
        // Initialize MediaPlayer
        mediaPlayer = MediaPlayer.create(this, R.raw.welcome_music);
//        btn_start.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startRace();
//            }
//        });
        btn_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                }

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
                        bet[index].setText("");
                        bet[index].requestFocus();
                    } else {
                        bet[index].setEnabled(false);
                        bet[index].setText("0");
                    }
                }
            });
            bet[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }
                @Override
                public void afterTextChanged(Editable s) {
                    if (isResetting){
                        return;
                    } else{
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
                }
            });
            bet[i].setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_DONE|| actionId == EditorInfo.IME_ACTION_NEXT) {
                        // Lấy số tiền cược được nhập từ EditText
                        String betAmountStr = bet[index].getText().toString();
                        if (!betAmountStr.isEmpty()) {
                            int betAmount = Integer.parseInt(betAmountStr);

                            // Kiểm tra điều kiện đặt cược so với số tiền hiện tại
                            if (betAmount > currentMoneyAfter) {
                                Toast.makeText(MainActivity.this, "Số tiền đặt cược vượt quá số tiền hiện có", Toast.LENGTH_SHORT).show();
                                return true; // Báo hiệu rằng sự kiện đã được xử lý
                            }
                            bet[index].clearFocus();
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(bet[index].getWindowToken(), 0);
                        }
                        return true;
                    }
                    return false;
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
        btn_reset.setClickable(false);
        for (int i = 0; i < 5; i++) {
            checkBox[i].setClickable(false);
            bet[i].setClickable(false);
        }
        btn_start.setEnabled(false);
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
                            stopMusic();
                            updateCurrentMoneyTextView();
                            btn_reset.setClickable(true);
                            for (int i = 0; i < 5; i++) {
                                checkBox[i].setClickable(true);
                                if (checkBox[i].isChecked()) {
                                    bet[i].setClickable(true);
                                }
                            }
                        } else {
                            handler[index].postDelayed(this, 100);
                        }
                    }
                }
            }, 100);
        }
    }
    private void stopMusic() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    private void resetRace() {
        isResetting = true;
        for (int i = 0; i < 5; i++) {
            currentProgress[i] = 0;
            checkBox[i].setChecked(false);
            bet[i].setText("0");
            seekBar[i].setProgress(0);

        }
        isRaceFinished = false;
        btn_start.setEnabled(true);
        isResetting = false;
    }

    private void updateCurrentMoneyTextView() {
        EditText betAmount1 = findViewById(R.id.ed1);
        EditText betAmount2 = findViewById(R.id.ed2);
        EditText betAmount3 = findViewById(R.id.ed3);
        EditText betAmount4 = findViewById(R.id.ed4);
        EditText betAmount5 = findViewById(R.id.ed5);
        if (currentProgress[0] >= 1000) {
            if (!betAmount1.getText().toString().isEmpty()) {
                currentMoneyAfter += 2 * Integer.parseInt(betAmount1.getText().toString());
            }
        } else if (currentProgress[1] >= 1000) {
            if (!betAmount2.getText().toString().isEmpty()) {
                currentMoneyAfter += 2 * Integer.parseInt(betAmount2.getText().toString());
            }
        } else if (currentProgress[2] >= 1000) {
            if (!betAmount3.getText().toString().isEmpty()) {
                currentMoneyAfter += 2 * Integer.parseInt(betAmount3.getText().toString());
            }
        } else if (currentProgress[3] >= 1000) {
            if (!betAmount4.getText().toString().isEmpty()) {
                currentMoneyAfter += 2 * Integer.parseInt(betAmount4.getText().toString());
            }
        } else if (currentProgress[4] >= 1000) {
            if (!betAmount5.getText().toString().isEmpty()) {
                currentMoneyAfter += 2 * Integer.parseInt(betAmount5.getText().toString());
            }
        } else {
            return;
        }
        point.setText(String.valueOf(currentMoneyAfter));
    }
}