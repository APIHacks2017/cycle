package com.dharani.demoo.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dharani.demoo.Cricket_activity;
import com.dharani.demoo.Fuels_details_activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by ANDROIDPC-4 on 24-Mar-17.
 */

public class Get_match_calender_async extends AsyncTask<String, String, String> {

    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ProgressDialog pd;
    Cricket_activity cricket_activity;
    Context context;
    String uname,pass;

    public Get_match_calender_async(Context context, Cricket_activity cricket_activity) {
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
                .host("52.36.211.72").port(5555).addPathSegments("gateway/Cricket%20API/1.0/matchCalendar")
                .addQueryParameter("apikey","0CWPTXYxJmaPWm0eJMGwsrlqR9K2")
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
                JSONArray array=object.getJSONArray("data");
                for(int i=0;i<5;i++){
                    JSONObject object1 = array.getJSONObject(i);
                    cricket_activity.m_date.add(object1.getString("date"));
                    cricket_activity.m_id.add(object1.getString("unique_id"));
                    cricket_activity.m_name.add(object1.getString("name")) ;

                }
cricket_activity.showChangeLangDialog();



            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(context, "cannot reach server..please try again later", Toast.LENGTH_SHORT).show();
    }
}
