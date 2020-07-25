package com.lyj.project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MaskAdapter extends ArrayAdapter<MaskVO> implements AdapterView.OnItemClickListener {

    Context context;//화면 제어권자
    int resource; //mylist,,,?
    ArrayList<MaskVO> list;
    MaskVO vo;

    public MaskAdapter(Context context, int resource, ArrayList<MaskVO> list, ListView myMaskListView){
        super(context, resource, list);
        this.context = context;
        this.resource = resource;
        this.list = list;

        myMaskListView.setOnItemClickListener(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //xml view해주기,,,근데 왜 안돼ㅜㅠ
        LayoutInflater linf = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        Log.i("yu", "getview : 안올걸");
        convertView = linf.inflate(resource, null);
        vo = list.get(position);

        TextView name = convertView.findViewById(R.id.Mask_name);
        TextView add = convertView.findViewById(R.id.Mask_addr);
        TextView remain = convertView.findViewById(R.id.Mask_remain);
        ImageView remain_icon = convertView.findViewById(R.id.Mask_remain_icon);

        name.setText(vo.getM_name());
        add.setText(vo.getM_add());
        remain.setText(vo.getM_remain_stat());

        Log.i("my","vo : "+ vo);

        if(vo.getM_remain_stat() != "100개 이상 넉넉!"){
            remain.setBackgroundColor(00123456);
        }

        if(vo.getM_remain_stat() == "품절"){
            remain_icon.setVisibility(View.GONE);
            remain.setText("");
            remain.setBackground(ContextCompat.getDrawable(context, R.drawable.soldout4));
        }

        if(vo.getM_remain_stat() != "2 ~ 30 마감임박!" && vo.getM_remain_stat() != "품절"){
            remain_icon.setVisibility(View.GONE);
        }

        return convertView;
    }//getView()

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {//i : position 몇번째 누르는지

        String m_detail_name = list.get(i).getM_name();
        String m_detail_add = list.get(i).getM_add();
        String m_detail_remain_stat = list.get(i).getM_remain_stat();
        String m_detail_lat = list.get(i).getM_lat();
        String m_detail_lng = list.get(i).getM_lng();

        //bundle에 값 저장
        Bundle bundle = new Bundle();
        bundle.putString("my_m_detail_name", m_detail_name);
        bundle.putString("my_m_detail_add", m_detail_add);
        bundle.putString("my_m_detail_remain_stat", m_detail_remain_stat);
        bundle.putString("my_m_detail_lat", m_detail_lat);
        bundle.putString("my_m_detail_lng", m_detail_lng);

        Intent intent = new Intent(view.getContext() , MaskDetailActivity.class);

        intent.putExtras(bundle);

        view.getContext().startActivity(intent);

    }


}
