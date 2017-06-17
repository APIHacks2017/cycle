package com.dharani.demoo.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dharani.demoo.Cricket_activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Created by ANDROIDPC-4 on 24-Mar-17.
 */

public class Get_match_details_async extends AsyncTask<String, String, String> {

    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ProgressDialog pd;
    Cricket_activity cricket_activity;
    Context context;
    String uname,pass;

    public Get_match_details_async(Context context, Cricket_activity cricket_activity) {
        this.context = context;
        this.cricket_activity = cricket_activity;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("fetching match details...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String jsonData = null;
        okhttp3.Response response;

        OkHttpClient client = new OkHttpClient.Builder().build();


        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("52.36.211.72").port(5555).addPathSegments("gateway/Cricket%20API/1.0/cricketScore")
                .addQueryParameter("apikey","0CWPTXYxJmaPWm0eJMGwsrlqR9K2")
                .addQueryParameter("unique_id",cricket_activity.unique_id)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("x-Gateway-APIKey","8264b672-a63d-40ae-a407-e2fec4d2c44d")
                .get()
                .build();
        Call call = client.newCall(request);

        try {
            response = call.execute();

            if (response.isSuccessful()) {
                jsonData = response.body().string();
                call.cancel();
            } else {
                jsonData = null;
                call.cancel();
            }
        } catch (final IOException e) {
            if (e.getMessage() != null) {
                cricket_activity.runOnUiThread(new Runnable() {
                    public void run() {
                        pd.cancel();
                        Log.e("Error", e.getMessage());
                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            e.printStackTrace();
        }
        return jsonData;
    }

    @Override
    protected void onPostExecute(String jsonData) {
        super.onPostExecute(jsonData);
        pd.cancel();
        pd = null;
        if (jsonData != null) {
            Log.d("result", jsonData);
            cricket_activity.m_date.clear();
            cricket_activity.m_id.clear();
            cricket_activity.m_name.clear();
            try {
                JSONObject object = new JSONObject(jsonData);
String team=object.getString("team-1")+" VS "+object.getString("team-2");
String type=object.getString("type");
String scorestr=object.getString("score");
String scoree=scorestr.substring(0, scorestr.indexOf("("));
String inni=object.getString("innings-requirement");


String temp_bm=scorestr.substring(scorestr.indexOf("(")+1, scorestr.indexOf(")"));
                String[] parts = temp_bm.split(",");
if(parts.length==4)
cricket_activity.setmdetails(team,type,scoree,parts[0],parts[1]+"\n"+parts[2],parts[3],inni);
                else
    cricket_activity.setmdetails(team,type,scoree,parts[0],parts[1],parts[2],inni);



          //  void setmdetails(String team,String type,String score1,String over1,String bm1,String bw1){


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(context, "cannot reach server..please try again later", Toast.LENGTH_SHORT).show();
    }
}
