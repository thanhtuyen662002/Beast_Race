package com.mini_project.beast_race;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CustomListViewAdapter extends BaseAdapter {
    private int layout;
    private ArrayList<Animal> animals;
    private Context context;
    private LayoutInflater inflater;
    SeekBar seekBar;
    Button btn_start;
    final boolean[] isValueChanged = {false};

    private ArrayList<Integer> randomInteger = new ArrayList<>();
    private Handler handler = new Handler();

    public CustomListViewAdapter(Context context, int layout, ArrayList<Animal> animals, Button start) {
        this.layout = layout;
        this.animals = animals;
        this.context = context;
        this.btn_start = start;
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < 5; i++) {
            randomInteger.add(0);
        }
        Log.d("Random Root", randomInteger.toString());

    }

    public void randomInt() {
        Random random = new Random();
        for (int i = 0; i < animals.size(); i++) {
            int randomInt = random.nextInt(10);
            randomInt += randomInteger.get(i);
            if (randomInt > 1000) {
                randomInteger.set(i, 1000);
            } else {
                randomInteger.set(i, randomInt);
            }
            Log.e("Random", randomInteger.toString());
        }
    }

    public void randomIntWithDelay() {
        handler.postDelayed(() -> {
            randomInt();
            notifyDataSetChanged();
        }, 1000);
    }

    @Override
    public int getCount() {
        return animals.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    boolean checkMax = false;
    private void isCheckMax(){
        for (int i = 0; i < randomInteger.size(); i++) {
            if (randomInteger.get(i) == 1000) {
                checkMax = true;
            }
        }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(layout, null);
        seekBar = convertView.findViewById(R.id.seekBar);
        CheckBox checkBox = convertView.findViewById(R.id.checkbox);
        EditText editText = convertView.findViewById(R.id.bet);
        Log.e("Random", " " + R.id.seekBar);

        Animal animal = animals.get(position);
        seekBar.setThumb(context.getDrawable(animal.getImage()));
        checkBox.setChecked(false);
        editText.setEnabled(false);
        seekBar.setMax(1000);
        editText.setText("0");
        seekBar.setProgress(randomInteger.get(position));

        if (seekBar.getProgress() < 1000) {
            if (position == 4 && randomInteger.get(position) > 0) {
                isCheckMax();
                randomIntWithDelay();
            }
        } else {
            return convertView;
        }


        btn_start.setOnClickListener(v -> {
            if (!animals.isEmpty()) {
                randomIntWithDelay();
            }
        });

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                editText.setEnabled(true);
                if (!isValueChanged[0]) {
                    editText.setText("");
                }
            } else {
                editText.setEnabled(false);
                editText.setText("0");
            }
        });


        editText.addTextChangedListener(new TextWatcher() {
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
        return convertView;
    }
}
