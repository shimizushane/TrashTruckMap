package com.example.apple.trashtruckmap;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.clustering.ClusterManager;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, ClusterManager.OnClusterItemInfoWindowClickListener<MyItem2> {

  //private ClusterManager<MyItem> mClusterManager;
  private ClusterManager<MyItem2> mClusterManager;
  //private MyItem clickedClusterItem;
  private MyItem2 clickedClusterItem;

  public final String LM_GPS = LocationManager.GPS_PROVIDER;
  public final String LM_NETWORK = LocationManager.NETWORK_PROVIDER;

  private GoogleMap mMap;
  private LocationManager locationManager;
  private LocationListener locationListener;
  private Location location;

  //GetTrashData getTrashData;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_maps);
    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    mapFragment.getMapAsync(this);

    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    locationListener = new MyLocationListener();

  }

  @Override protected void onResume() {
    if (locationManager == null) {
      locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
      locationListener = new MyLocationListener();
    }

    locationManager.requestLocationUpdates(LM_GPS, 0, 0, locationListener);
    locationManager.requestLocationUpdates(LM_NETWORK, 0, 0, locationListener);
    super.onResume();
  }

  @Override protected void onPause() {
    if (locationManager != null) {
      locationManager.removeUpdates(locationListener);
      locationManager = null;
    }
    super.onPause();
  }

  private void setUpClusterer() {
    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10));
  
    //mClusterManager = new ClusterManager<MyItem>(this, mMap);
    mClusterManager = new ClusterManager<MyItem2>(this, mMap);

    mMap.setOnCameraIdleListener(mClusterManager);

    mMap.setOnMarkerClickListener(mClusterManager);
    mMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());

    mMap.setOnInfoWindowClickListener(mClusterManager);
    mClusterManager.setOnClusterItemInfoWindowClickListener(this);

    //mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem>() {
    //  @Override public boolean onClusterItemClick(MyItem myItem) {
    //    clickedClusterItem = myItem;
    //    return false;
    //  }
    //});
    mClusterManager.setOnClusterItemClickListener(new ClusterManager.OnClusterItemClickListener<MyItem2>() {
      @Override public boolean onClusterItemClick(MyItem2 myItem) {
        clickedClusterItem = myItem;
        Log.d("Marker TAG =",clickedClusterItem.getmSna().toString() + " " + clickedClusterItem.getmTot().toString());

        return false;
      }
    });
    
    addItems();

    mClusterManager.getMarkerCollection().setOnInfoWindowAdapter(new MyCustomAdapterForItems());

    //Log.d("Marker TAG =", String.valueOf(mClusterManager.getMarkerCollection().toString()));

  }

  private void addItems() {

    double lat;
    double lng;

    for (UbikeInfo a: GetUbikeData.mylist2) {
      //lat = a.getLat();
      //lng = a.getLng();

      MyItem2 offsetItem2 = new MyItem2(a.getLat(), a.getLng(), a.getSna(), a.getTot(), a.getSbi(), a.getBemp());
      //Log.d("Ubike TAG", a.getLat() + " " + a.getLng() + " " + a.getSna() + " " + a.getTot() + " " + a.getSbi() + " " + a.getBemp());
      mClusterManager.addItem(offsetItem2);
    }

    //for (TrashInfo a:GetTrashData.mylist) {
    //  lat = a.getLatitude();
    //  lng = a.getLongitude();
    //
    //  MyItem offsetItem = new MyItem(lat, lng, a.getCar(), a.getLocation());
    //  mClusterManager.addItem(offsetItem);
    //
    //}


    //for (int i = 0; i < 10; i++) {
    //  double offset = i / 60d;
    //  lat = lat + offset;
    //  lng = lng + offset;
    //  MyItem offsetItem = new MyItem(lat, lng);
    //  mClusterManager.addItem(offsetItem);
    //}
  }

  public class MyCustomAdapterForItems implements GoogleMap.InfoWindowAdapter {
    private final View myContentsView;

    MyCustomAdapterForItems() {
      myContentsView = getLayoutInflater().inflate(R.layout.info_window_ubike, null);
    }

    @Override
    public View getInfoWindow(Marker marker) {
      //TextView tvTitle = (TextView) myContentsView.findViewById(R.id.txtTitle);
      //TextView tvSnippet = (TextView) myContentsView.findViewById(R.id.txtSnippet);

      TextView tvSna = (TextView) myContentsView.findViewById(R.id.sna);
      TextView tvTot = (TextView) myContentsView.findViewById(R.id.tot);
      TextView tvSbi = (TextView) myContentsView.findViewById(R.id.sbi);
      TextView tvBemp = (TextView) myContentsView.findViewById(R.id.bemp);


      //tvTitle.setText(clickedClusterItem.getTitle());
      //tvSnippet.setText(clickedClusterItem.getSnippet());

      tvSna.setText("\t" + clickedClusterItem.getmSna() + "\t" );
      tvTot.setText("\t" +clickedClusterItem.getmTot() + "\t" );
      tvSbi.setText("\t" +clickedClusterItem.getmSbi() + "\t" );
      tvBemp.setText("\t" +clickedClusterItem.getmBemp() + "\t" );

      return myContentsView;
    }

    @Override
    public View getInfoContents(Marker marker) {
      return null;
    }
  }

  public Location openGPS() {
    boolean gps = locationManager.isProviderEnabled(locationManager.GPS_PROVIDER);
    boolean network = locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER);

    location = null;
    if (gps || network) {

    } else {
      startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    if (gps) {
      if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
          != PackageManager.PERMISSION_GRANTED
          && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
          != PackageManager.PERMISSION_GRANTED) {
      }
      location = locationManager.getLastKnownLocation(locationManager.GPS_PROVIDER);
    } else if (network) {
      location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
    }
    return location;
    }

  @Override public void onMapReady(GoogleMap googleMap) {
    mMap = googleMap;

    // Add a marker in Sydney and move the camera
    LatLng sydney = new LatLng(-34, 151);
    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED
        && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        != PackageManager.PERMISSION_GRANTED) {
      return;
    }

    mMap.setMyLocationEnabled(true);
    mMap.getUiSettings().setMyLocationButtonEnabled(true);
    mMap.getUiSettings().setZoomControlsEnabled(false);
    mMap.getUiSettings().setAllGesturesEnabled(true);

    mMap.setOnMyLocationButtonClickListener(this);

    locationManager = (LocationManager)getSystemService(LOCATION_SERVICE);

    drawMarker(openGPS());

    //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
    //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
  }

  @Override public boolean onMyLocationButtonClick() {
    Toast.makeText(MapsActivity.this,"MyLocation",Toast.LENGTH_SHORT).show();
    return false;
  }

  private void drawMarker(Location location) {
    setUpClusterer();

    if (mMap == null) {
      //mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map));
      //mMap = ((MapFragment)getFragmentManager().findFragmentById(R.id.map)).getm
    }
    if (mMap != null) {
      Log.d("TAG", mMap.toString());
      mMap.clear();
      LatLng gps = new LatLng(location.getLatitude(), location.getLongitude());
      //mMap.addMarker(new MarkerOptions().position(gps).title("My Position"));
      //GetTrashData.showTrashInfo();
      //GetTrashData.markerPosition(mMap);

      //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 12));
      //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(gps, 10), 2000, null);
      //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gps,11));

    }
  }

  @Override public void onClusterItemInfoWindowClick(MyItem2 myItem) {

    //Intent i = new Intent(this, OtherActivity.class)
  }

  private class MyLocationListener implements LocationListener {
    @Override public void onLocationChanged(Location location) {

    }

    @Override public void onStatusChanged(String s, int i, Bundle bundle) {
      switch (i) {
        case LocationProvider.AVAILABLE:
          Toast.makeText(MapsActivity.this, "服務中", Toast.LENGTH_LONG).show();
          break;
        case LocationProvider.OUT_OF_SERVICE:
          Toast.makeText(MapsActivity.this, "沒服務", Toast.LENGTH_LONG).show();
          break;
        case LocationProvider.TEMPORARILY_UNAVAILABLE:
          Toast.makeText(MapsActivity.this, "暫時沒服務", Toast.LENGTH_LONG).show();
          break;
        default:
      }
    }

    @Override public void onProviderEnabled(String s) {

    }

    @Override public void onProviderDisabled(String s) {

    }
  }
}
