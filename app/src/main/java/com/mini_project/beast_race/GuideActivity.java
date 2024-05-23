package com.mini_project.beast_race;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GuideActivity extends AppCompatActivity {

    private TextView messageCheckbox;
    private TextView messageEdittext;
    private TextView messageButton;
    private TextView messageButton2;
    private Button btnNext;
    private int currentMessage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        TextView message = findViewById(R.id.message);
        messageCheckbox = findViewById(R.id.message_checkbox);
        messageEdittext = findViewById(R.id.message_edittext);
        messageButton = findViewById(R.id.message_button);
        messageButton2 = findViewById(R.id.message_button2);
        btnNext = findViewById(R.id.btn_next);



        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_next) {
                    showNextMessage();
                } else if (v.getId() == R.id.btn_back) {
                    goBack();
                }
            }


        });
    }

    private void goBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    private void showNextMessage() {
        switch (currentMessage) {
            case 0:
                messageCheckbox.setVisibility(View.VISIBLE);
                break;
            case 1:
                messageEdittext.setVisibility(View.VISIBLE);
                break;
            case 2:
                messageButton.setVisibility(View.VISIBLE);
                break;
            case 3:
                messageButton2.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        currentMessage++;
    }
}

