package com.example.apple.trashtruckmap;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by apple on 2018/2/21.
 */

public class MyItem2 implements ClusterItem {
  private final LatLng mPostition;
  private String mSna;
  private String mTot;
  private String mSbi;
  private String mBemp;

  public MyItem2(double lat, double lng) {
    mPostition = new LatLng(lat, lng);
  }

  public MyItem2(double lat, double lng, String sna, String tot, String sbi, String bemp) {
    mPostition = new LatLng(lat, lng);
    mSna = sna;
    mTot = tot;
    mSbi = sbi;
    mBemp = bemp;
  }
  @Override public LatLng getPosition() {
    return mPostition;
  }

  public String getmSna() {
    return mSna;
  }

  public String getmTot() {
    return mTot;
  }

  public String getmSbi() {
    return mSbi;
  }

  public String getmBemp() {
    return mBemp;
  }
}
