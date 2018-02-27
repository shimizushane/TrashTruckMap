package com.example.apple.trashtruckmap;

import android.content.Context;
import android.util.Log;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by apple on 2018/2/21.
 */

public class GetUbikeData {
  public static ArrayList<UbikeInfo> mylist2 = new ArrayList<>();

  private Context context;
  private String ubikeUrl;


  public GetUbikeData (Context context)
  {
    this.context = context;
  }
  public void getDate() {

    ubikeUrl = "https://data.tycg.gov.tw/api/v1/rest/datastore/a1b4714b-3b75-4ff8-a8f2-cc377e4eaa0f?format=json";

    //RequestQueue queue = Volley.newRequestQueue(context);

    StringRequest request = new StringRequest(ubikeUrl, new Response.Listener<String>() {
      @Override public void onResponse(String response) {

        try {
          //JSONObject o1 = new JSONObject(response).getJSONObject("result");
          //JSONArray a1 = o1.getJSONArray("records");
          //JSONArray array2 = new JSONArray(new JSONObject(new JSONObject(response).getJSONObject("result")).getJSONArray("records"));

          JSONArray array = new JSONObject(response).getJSONObject("result").getJSONArray("records");

          for (int i = 0; i < array.length(); i++) {
            JSONObject obj = array.getJSONObject(i);
            Gson gson = new Gson();
            UbikeInfo ubikeInfo = gson.fromJson(obj.toString(),UbikeInfo.class);
            Log.d("UbikeInfo = ", ubikeInfo.toString());
            mylist2.add(ubikeInfo);
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
  public void showUbikeInfo() {
    for (UbikeInfo a: mylist2) {
      Log.d("TAG UbikeInfo = ", a.getSna() + " " + a.getTot() + " " + a.getSbi() + " " + a.getBemp() + " " + a.getLat() + " " + a.getLng());
    }
  }
}
