package com.example.apple.trashtruckmap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements Button.OnClickListener{

  LocationManager manager;
  Button btn1;
  Button btn2;

  GetTrashData getTrashData;
  GetUbikeData getUbikeData;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    btn1 = (Button)findViewById(R.id.button1);
    btn2 = (Button)findViewById(R.id.button2);
    manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

    btn1.setOnClickListener(this);
    btn2.setOnClickListener(this);

    getTrashData = new GetTrashData(MainActivity.this);
    getTrashData.getDate();

    getUbikeData = new GetUbikeData(MainActivity.this);
    getUbikeData.getDate();

  }

  @Override public void onClick(View view) {
    switch (view.getId()) {
      case R.id.button1:
        //if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        //    && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        //  ActivityCompat.requestPermissions(this, new String[] {
        //      Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        //  }, 123);
        //}
        //
        //if (isOpenGps()) {
        //  startActivity(new Intent(MainActivity.this, MapsActivity.class));
        //} else {
        //  Snackbar.make(view, "手動開啟GPS定位", Snackbar.LENGTH_LONG).setAction("Action", new View.OnClickListener() {
        //    @Override public void onClick(View view) {
        //      startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),
        //          REQUEST_CODE_LOCATION_SETTINGS);
        //    }
        //  }).show();
        //}

        startActivity(new Intent(MainActivity.this, MapsActivity.class));
        break;
      case R.id.button2:
        //getTrashData.showTrashInfo();
        getUbikeData.showUbikeInfo();
        break;
      default:
    }
  }

  private boolean isOpenGps() {
    manager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
    boolean gps = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    boolean network = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    if (gps || network) {
      return true;
    } else {
      return false;
    }
  }
}
