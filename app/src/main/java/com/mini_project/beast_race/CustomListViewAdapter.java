package com.mini_project.beast_race;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;

import java.util.ArrayList;

public class CustomListViewAdapter extends BaseAdapter {
    private int layout;
    private ArrayList<Animal> animals;
    private Context context;
    private LayoutInflater inflater;

    public CustomListViewAdapter( Context context, int layout, ArrayList<Animal> animals) {
        this.layout = layout;
        this.animals = animals;
        this.context = context;
        inflater = LayoutInflater.from(context);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(layout, null);
        SeekBar seekBar = convertView.findViewById(R.id.seekBar);
        Animal animal = animals.get(position);
        seekBar.setThumb(context.getDrawable(animal.getImage()));
        return convertView;
    }
}
