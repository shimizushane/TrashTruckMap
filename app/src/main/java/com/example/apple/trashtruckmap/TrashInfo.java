package com.example.apple.trashtruckmap;

/**
 * Created by apple on 2018/2/13.
 */

public class TrashInfo {
  private String lineid;
  private String car;
  private String time;
  private String location;
  private Double longitude;
  private Double latitude;
  private String cityid;
  private String cityname;

  @Override public String toString() {
    return "TrashInfo{"
        + "lineid='"
        + lineid
        + '\''
        + ", car='"
        + car
        + '\''
        + ", time='"
        + time
        + '\''
        + ", location='"
        + location
        + '\''
        + ", longitude="
        + longitude
        + ", latitude="
        + latitude
        + ", cityId='"
        + cityid
        + '\''
        + ", cityName='"
        + cityname
        + '\''
        + '}';
  }

  public String getLineid() {
    return lineid;
  }

  public void setLineid(String lineid) {
    this.lineid = lineid;
  }

  public String getCar() {
    return car;
  }

  public void setCar(String car) {
    this.car = car;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(String longitude) {
    this.longitude = Double.parseDouble(longitude);
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(String latitude) {
    this.latitude = Double.parseDouble(latitude);
  }

  public String getCityId() {
    return cityid;
  }

  public void setCityId(String cityid) {
    this.cityid = cityid;
  }

  public String getCityName() {
    return cityname;
  }

  public void setCityName(String cityname) {
    this.cityname = cityname;
  }
}
