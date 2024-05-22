package com.mini_project.beast_race;

import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Animal> animals;
    CustomListViewAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.custom_listView);
        addAnimals();
        adapter = new CustomListViewAdapter(this, R.layout.activity_custom_list_view, animals);
        listView.setAdapter(adapter);
    }

    private void addAnimals() {
        animals = new ArrayList<Animal>();
        animals.add(new Animal(1, "elephant", R.drawable.small_elephant));
        animals.add(new Animal(3, "horse", R.drawable.small_horse));
        animals.add(new Animal(4, "lion", R.drawable.small_lion));
        animals.add(new Animal(6, "rhino", R.drawable.small_rhino));
        animals.add(new Animal(8, "zebra", R.drawable.small_zebra));
    }
}