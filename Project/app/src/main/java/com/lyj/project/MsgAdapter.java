package com.lyj.project;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MsgAdapter extends ArrayAdapter<MsgVO> implements AdapterView.OnItemClickListener {

    Context context;//화면 제어권자
    int resource; //mylist,,,?
    ArrayList<MsgVO> list;
    MsgVO vo;

    public MsgAdapter(Context context, int resource, ArrayList<MsgVO> list, ListView msg_list) {
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;
        msg_list.setOnItemClickListener(this);
        Log.i("yu", "getview : dd안올걸");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater linf = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        Log.i("yu", "getview : 안올걸");
        convertView = linf.inflate(resource, null);
        vo = list.get(position);

        TextView create_date = convertView.findViewById(R.id.create_date);
        TextView location_name = convertView.findViewById(R.id.location_name);
        TextView msg = convertView.findViewById(R.id.msg);

        create_date.setText(vo.getCreate_date());
        location_name.setText(vo.getLocation_name());
        msg.setText(vo.getMsg());

        return convertView;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String date = list.get(i).getCreate_date();
        String id = list.get(i).getLocation_id();
        String name = list.get(i).getLocation_name();
        String msg = list.get(i).getMsg();

        Intent intent = new Intent(view.getContext(), MsgActivity.class);
        view.getContext().startActivity(intent);
    }
}
