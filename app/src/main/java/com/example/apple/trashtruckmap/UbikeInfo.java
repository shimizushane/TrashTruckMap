package com.example.apple.trashtruckmap;

/**
 * Created by apple on 2018/2/21.
 */

public class UbikeInfo {
  private String tot;
  private String sbi;
  private String bemp;
  private String sna;
  private Double lat;
  private Double lng;

  public String getTot() {
    return tot;
  }

  public void setTot(String tot) {
    this.tot = tot;
  }

  public String getSbi() {
    return sbi;
  }

  public void setSbi(String sbi) {
    this.sbi = sbi;
  }

  public String getBemp() {
    return bemp;
  }

  public void setBemp(String bemp) {
    this.bemp = bemp;
  }

  public String getSna() {
    return sna;
  }

  public void setSna(String sna) {
    this.sna = sna;
  }

  public Double getLat() {
    return lat;
  }

  public void setLat(String lat) {
    this.lat = Double.parseDouble(lat);
  }

  public Double getLng() {
    return lng;
  }

  public void setLng(String lng) {
    this.lng = Double.parseDouble(lng);
  }
}
