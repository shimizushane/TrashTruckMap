package com.example.apple.trashtruckmap;

import android.content.Context;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 2018/2/13.
 */

public class GetTrashData {
  public static ArrayList<TrashInfo> mylist = new ArrayList<>();

  private Context context;
  private String trashUrl;

  private static float COLOR_NUM = 10;

  public GetTrashData (Context context)
  {
    this.context = context;
  }
  public void getDate() {

    trashUrl = "http://data.ntpc.gov.tw/od/data/api/28AB4122-60E1-4065-98E5-ABCCB69AACA6?$format=json";

    //RequestQueue queue = Volley.newRequestQueue(context);

    StringRequest request = new StringRequest(trashUrl, new Response.Listener<String>() {
      @Override public void onResponse(String response) {

        try {
          JSONArray array = new JSONArray(response);
          for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Gson gson = new Gson();
            TrashInfo trashInfo = gson.fromJson(obj.toString(),TrashInfo.class);
            //Log.d("TrashInfo = ", trashInfo.toString());
            mylist.add(trashInfo);
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }
    }, new Response.ErrorListener() {
      @Override public void onErrorResponse(VolleyError error) {
        Log.d("StringRequest Error = ", error.toString());
      }
    });
    Volley.newRequestQueue(context).add(request);
  }
  public static void showTrashInfo() {
    for (TrashInfo a: mylist) {
      Log.d("TAG TrashInfo = ", a.getCar() + " " + a.getLocation() + " " + a.getLatitude() + " " + a.getLongitude() + " " + a.getCityName());
    }
  }

  //public static void markerPosition(GoogleMap mMap) {
  //  for (TrashInfo a: mylist) {
  //
  //    ;
  //    Log.d("TrashInfo = ", a.getLatitude() + " " + a.getLongitude());
  //
  //    mMap.addMarker(new MarkerOptions()
  //        .position(new LatLng(a.getLatitude(),a.getLongitude()))
  //        .title(a.getCar())
  //        .snippet(a.getLocation())
  //        .anchor(0.5f,1.0f)
  //        .icon(BitmapDescriptorFactory.defaultMarker(COLOR_NUM = COLOR_NUM > 360?0:COLOR_NUM + 30))
  //    );
  //
  //  }
  //}
}
