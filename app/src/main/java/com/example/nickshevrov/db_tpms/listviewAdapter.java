package com.example.nickshevrov.db_tpms;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class listviewAdapter extends BaseAdapter {
    public ArrayList<HashMap> list;
    Activity activity;

    public listviewAdapter(Activity activity, ArrayList<HashMap> list) {
        super();
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        LayoutInflater inflater = activity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_view, null);
            holder = new ViewHolder();
            holder.txtFirst = (TextView) convertView.findViewById(R.id.FirstText);
            holder.txtSecond = (TextView) convertView.findViewById(R.id.SecondText);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HashMap map = list.get(position);
        holder.txtFirst.setText(map.get("First").toString());
        holder.txtSecond.setText(map.get("Second").toString());

        return convertView;
    }

    private class ViewHolder {
        TextView txtFirst;
        TextView txtSecond;
    }

}
