package com.example.ihm_android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Other_user_customGrid extends BaseAdapter{
    private Context mContext;
    //private final String[] web;
    private final ArrayList<String> user;
    //private final int[] Imageid;
    private final ArrayList<Integer> Imageid;

    public Other_user_customGrid(Context c, ArrayList<String> user, ArrayList<Integer> Imageid ) {
        mContext = c;
        this.Imageid = Imageid;
        this.user = user;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        //return web.length;
        return user.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.activity_other_user_single_grid, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);
            //textView.setText(web[position]);
            textView.setText(user.get(position));
            //imageView.setImageResource(Imageid[position]);
            imageView.setImageResource(Imageid.get(position));
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}
