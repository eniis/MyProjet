package com.ult.android.myprojet.helper;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import com.ult.android.myprojet.R;


/**
 * Created by Mohamed on 01/03/2016.
 */
public class SwipeListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Happy> happyList;
    //private String[] bgColors;

    public SwipeListAdapter(Activity activity, List<Happy> movieList) {
        this.activity = activity;
        this.happyList = movieList;
       // bgColors = activity.getApplicationContext().getResources().getStringArray(R.array.movie_serial_bg);
    }

    @Override
    public int getCount() {
        return happyList.size();
    }

    @Override
    public Object getItem(int location) {
        return happyList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

      //  ImageView img = (ImageView) convertView.findViewById(R.id.imghh);
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView aime = (TextView) convertView.findViewById(R.id.tvaime);
        TextView adr = (TextView) convertView.findViewById(R.id.tvadr);

        //serial.setText(String.valueOf(happyList.get(position).id));
        title.setText(happyList.get(position).title);
        aime.setText(happyList.get(position).aime);
        adr.setText(happyList.get(position).adr);


//        String color = bgColors[position % bgColors.length];
  //      serial.setBackgroundColor(Color.parseColor(color));

        return convertView;
    }

}