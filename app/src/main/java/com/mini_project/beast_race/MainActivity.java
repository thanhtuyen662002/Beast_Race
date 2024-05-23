package com.mini_project.beast_race;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
//    ListView listView;
//    ArrayList<Animal> animals;
//    CustomListViewAdapter adapter;
//    Button btn_start;
//    TextView point;
//    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5;
//    SeekBar seekBar1, seekBar2, seekBar3, seekBar4, seekBar5;
//    EditText bet1, bet2, bet3, bet4, bet5;
//    private TextView currentMoney;
//    private int currentMoneyAfter = 0;
//    private Handler handler1 = new Handler(), handler2 = new Handler(), handler3 = new Handler(), handler4 = new Handler(), handler5 = new Handler();;
//    private int currentProgress1 = 0, currentProgress2 = 0, currentProgress3 = 0, currentProgress4 = 0, currentProgress5 = 0;
//    private boolean isRaceFinished = false;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_layout);
        point = findViewById(R.id.point);

        btn_start = (Button) findViewById(R.id.btn_start);
        for (int i = 0; i < 5; i++) {
            checkBox[i] = findViewById(getResources().getIdentifier("cb" + (i + 1), "id", getPackageName()));
            seekBar[i] = findViewById(getResources().getIdentifier("sb" + (i + 1), "id", getPackageName()));
            bet[i] = findViewById(getResources().getIdentifier("ed" + (i + 1), "id", getPackageName()));
            handler[i] = new Handler();
            seekBar[i].setMax(1000);
        }
        point.setText("Current Money: " + currentMoneyAfter);
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
        for (int i = 0; i < 5; i++) {
            final int index = i;
            bet[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    String text = s.toString();
                    if (!text.equals("0")) {
                        isValueChanged[0] = true;
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String text = s.toString();
                    if (text.isEmpty()) {
                        isValueChanged[0] = false;
                    }
                }
            });
        }
//        addAnimals();
//        adapter = new CustomListViewAdapter(this, R.layout.activity_custom_list_view, animals, btn_start);
//        listView.setAdapter(adapter);
//        listView = (ListView) findViewById(R.id.custom_listView);


        // Xử lý sự kiện cho các thành phần

    }

//    private void addAnimals() {
//        animals = new ArrayList<Animal>();
//        animals.add(new Animal(1, "elephant", R.drawable.small_elephant));
//        animals.add(new Animal(3, "horse", R.drawable.small_horse));
//        animals.add(new Animal(4, "lion", R.drawable.small_lion));
//        animals.add(new Animal(6, "rhino", R.drawable.small_rhino));
//        animals.add(new Animal(8, "zebra", R.drawable.small_zebra));
//    }
    private void startRace() {
        for (int i = 0; i < 5; i++) {
            final int index = i;
            handler[i].postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (!isRaceFinished && currentProgress[index] < 1000) {
                        int randomIncrement = new Random().nextInt(5) + 1;
                        currentProgress[index] += randomIncrement;
                        seekBar[index].setProgress(currentProgress[index]);
                        handler[index].postDelayed(this, 100);
                    } else {
                        checkRaceCompletion();
                    }
                }
            }, 100);
        }
    }

    private void checkRaceCompletion() {
        if (currentProgress[0] >= 1000 || currentProgress[1] >= 1000 || currentProgress[2] >= 1000) {
            isRaceFinished = true;
            int betAmount1 = Integer.parseInt(bet[0].getText().toString());
            int betAmount2 = Integer.parseInt(bet[1].getText().toString());
            int betAmount3 = Integer.parseInt(bet[2].getText().toString());
            if (currentProgress[0] >= 1000) {
                currentMoneyAfter += 2 * betAmount1;
            } else if (currentProgress[1] >= 1000) {
                currentMoneyAfter += 2 * betAmount2;
            } else if (currentProgress[2] >= 1000) {
                currentMoneyAfter += 2 * betAmount3;
            }
            updateCurrentMoneyTextView();
        }
    }
    private void updateCurrentMoneyTextView() {
        point.setText("Current Money: " + currentMoneyAfter);
    }
}