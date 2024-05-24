package com.mini_project.beast_race;

import android.graphics.Color;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Notification extends AppCompatActivity {
    // truyền giá trị tổng tiền thưởng
     public int TienThuong;
    public int WinningIndex;
    public int PointTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notification);

        Intent intent = getIntent();
        TienThuong = intent.getIntExtra("betAmount", 0);
        WinningIndex = intent.getIntExtra("WinningIndex", 0);
        PointTotal = intent.getIntExtra("TienThuong", 0);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnStart = findViewById(R.id.btn_start);
        Button btnReset = findViewById(R.id.btn_reset);
        TextView thongBaoTextView = findViewById(R.id.ThongBao);
        TextView tienthuong = findViewById(R.id.textView);
        TextView giaTriTienThuong = findViewById(R.id.Tienthuong);
        ImageView Animalview = findViewById(R.id.Aminal);
        TextView pointTotal = findViewById(R.id.point);
        pointTotal.setText(String.valueOf(PointTotal));
        giaTriTienThuong.setText(String.valueOf(TienThuong));


        if(TienThuong == 0 || TienThuong < 0){
            thongBaoTextView.setText("Thất bại");
            thongBaoTextView.setTextColor(getResources().getColor(R.color.red));
            tienthuong.setVisibility(View.VISIBLE);
            giaTriTienThuong.setVisibility(View.VISIBLE);

        }else{
            switch (WinningIndex){
                case 1:
                    Animalview.setImageResource(R.drawable.small_lion);
                    break;
                case 2:
                    Animalview.setImageResource(R.drawable.small_elephant);
                    break;

                case 3:
                    Animalview.setImageResource(R.drawable.small_rhino);
                    break;

                case 4:
                    Animalview.setImageResource(R.drawable.small_horse);
                    break;

                case 5:
                    Animalview.setImageResource(R.drawable.small_zebra);
                    break;

            }
        }
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notification.this, MainActivity.class);
                intent.putExtra("PointTotal", PointTotal);
                startActivity(intent);
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Notification.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}