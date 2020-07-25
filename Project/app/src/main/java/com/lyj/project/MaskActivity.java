package com.lyj.project;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MaskActivity extends Activity {

    MaskParser mask_parser;
    ListView myMaskListView;
    ArrayList<MaskVO> list;
    MaskAdapter adapter;
    ImageView btn_map_marker;

    ProgressDialog dialog;
    View mask_detail_footer_View;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mask);

        btn_map_marker = findViewById(R.id.btn_map_marker);
        myMaskListView = findViewById(R.id.myMaskListView);

        btn_map_marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MaskActivity.this, AreaActivity.class);
                startActivity(i);
            }
        });

        list = new ArrayList<>();
        adapter = null;
        new MaskAsync().execute();

        mask_parser = new MaskParser();

    }//onCreate()

    //뒤로가기 버튼
    @Override
    public void onBackPressed(){
        Intent i = new Intent(MaskActivity.this, MenuActivity.class);
        startActivity(i);
        finish();
    }


    class MaskAsync extends AsyncTask<String, Void, ArrayList<MaskVO>> {

        @Override
        protected ArrayList<MaskVO> doInBackground(String... strings) {
            return mask_parser.connectMask(list);
        }

        @Override
        protected void onPostExecute(ArrayList<MaskVO> maskVOS) {

            if (adapter == null) {                    //제어권자,       리스트뷰 xml,리스트뷰에 표시할 list내용, listview
                adapter = new MaskAdapter(MaskActivity.this, R.layout.mask_item, maskVOS, myMaskListView);

                myMaskListView.setAdapter(adapter);

            }
            adapter.notifyDataSetChanged();
        }
    }

}