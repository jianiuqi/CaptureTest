package com.jnq.capturetest;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Polyline;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BaiduMap.OnMapLoadedCallback{

    MapView mMapView = null;
    BaiduMap mBaiduMap = null;
    // 多颜色折线
    Polyline mPolyline = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        addPolyline();
        mBaiduMap.setOnMapLoadedCallback(this);
    }

    private void addPolyline(){
        LatLng p1 = new LatLng(39.91755, 116.400918);
        LatLng p2 = new LatLng(39.917529, 116.400649);
        LatLng p3 = new LatLng(39.917522, 116.400316);
        LatLng p4 = new LatLng(39.917522, 116.399768);
        LatLng p5 = new LatLng(39.917494, 116.399436);
        LatLng p6 = new LatLng(39.917301, 116.399481);
        LatLng p7 = new LatLng(39.917135, 116.399499);
        LatLng p8 = new LatLng(39.916658, 116.399513);
        LatLng p9 = new LatLng(39.916371, 116.399517);
        LatLng p10 = new LatLng(39.916042, 116.399531);
        LatLng p11 = new LatLng(39.916052, 116.399898);
        LatLng p12 = new LatLng(39.916063, 116.400222);
        LatLng p13 = new LatLng(39.91608, 116.400563);
        LatLng p14 = new LatLng(39.916094, 116.400985);
        LatLng p15 = new LatLng(39.916111, 116.401327);
        LatLng p16 = new LatLng(39.916429, 116.401282);
        LatLng p17= new LatLng(39.916703, 116.401246);
        LatLng p18 = new LatLng(39.916841, 116.400909);
        LatLng p19 = new LatLng(39.917042, 116.400891);
        LatLng p20 = new LatLng(39.91726, 116.400895);
        LatLng p21 = new LatLng(39.917457, 116.400922);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(p1);
        points.add(p2);
        points.add(p3);
        points.add(p4);
        points.add(p5);
        points.add(p6);
        points.add(p7);
        points.add(p8);
        points.add(p9);
        points.add(p10);
        points.add(p11);
        points.add(p12);
        points.add(p13);
        points.add(p14);
        points.add(p15);
        points.add(p16);
        points.add(p17);
        points.add(p18);
        points.add(p19);
        points.add(p20);
        points.add(p21);
        OverlayOptions options = new PolylineOptions().width(10).color(Color.BLUE).points(points);
        mPolyline = (Polyline) mBaiduMap.addOverlay(options);


        MapStatus.Builder builder = new MapStatus.Builder();
        builder.target(new LatLng(39.916776, 116.400271));
        builder.zoom(19.0f);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        findViewById(R.id.capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
                    @Override
                    public void onSnapshotReady(Bitmap bitmap) {
                        saveBitmap(bitmap);
                    }
                });
            }
        });

        /*
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng point: points) {
            builder.include(point);
        }
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(metrics);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build(), metrics.widthPixels, metrics.heightPixels));*/
    }

    public void saveBitmap(Bitmap bm) {
        File file = new File("/mnt/sdcard/test.png");
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if (bm.compress(Bitmap.CompressFormat.PNG, 100, out)) {
                out.flush();
                out.close();
            }
            Toast.makeText(this,"屏幕截图成功，图片存在: " + file.toString(),
                    Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "截图保存成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onMapLoaded() {
        mBaiduMap.snapshot(new BaiduMap.SnapshotReadyCallback() {
            @Override
            public void onSnapshotReady(Bitmap bitmap) {
                saveBitmap(bitmap);
            }
        });
    }
}
