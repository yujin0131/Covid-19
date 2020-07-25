package com.lyj.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MaskDetailActivity extends Activity implements OnMapReadyCallback {

    TextView m_detail_name_tv, m_detail_add_tv, m_detail_remain_stat_tv;
    String m_detail_name, m_detail_add, m_detail_remain_stat, m_detail_lat, m_detail_lng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mask_detail);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        m_detail_name_tv = findViewById(R.id.m_detail_name_tv);
        m_detail_add_tv = findViewById(R.id.m_detail_add_tv);
        m_detail_remain_stat_tv = findViewById(R.id.m_detail_remain_stat_tv);

        //bundle 값 추출
        Bundle bundle = getIntent().getExtras();
        m_detail_name = bundle.getString("my_m_detail_name");
        m_detail_add = bundle.getString("my_m_detail_add");
        m_detail_remain_stat = bundle.getString("my_m_detail_remain_stat");
        m_detail_lat = bundle.getString("my_m_detail_lat");
        m_detail_lng = bundle.getString("my_m_detail_lng");

        //화면에 띄우기
        m_detail_name_tv.setText(m_detail_name);
        m_detail_add_tv.setText(m_detail_add);
        m_detail_remain_stat_tv.setText(m_detail_remain_stat);

    }//onCreate()


    @Override
    public void onMapReady(final GoogleMap map) {

        LatLng drugstore = new LatLng(Double.parseDouble(m_detail_lat), Double.parseDouble(m_detail_lng));

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(drugstore);
        markerOptions.title(m_detail_name);
        markerOptions.snippet(m_detail_add);
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(drugstore));
        map.animateCamera(CameraUpdateFactory.zoomTo(16));

    }//map
}