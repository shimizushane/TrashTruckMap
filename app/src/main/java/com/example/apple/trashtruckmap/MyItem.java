package com.example.apple.trashtruckmap;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by apple on 2018/2/14.
 */

public class MyItem implements ClusterItem {
  private final LatLng mPostition;
  private String mTitle;
  private String mSnippet;

  public MyItem(double lat, double lng) {
    mPostition = new LatLng(lat, lng);
  }

  public MyItem(double lat, double lng, String title, String snippet) {
    mPostition = new LatLng(lat, lng);
    mTitle = title;
    mSnippet = snippet;
  }
  @Override public LatLng getPosition() {
    return mPostition;
  }

  public String getTitle() {
    return mTitle;
  }

  public String getSnippet() {
    return mSnippet;
  }

}
