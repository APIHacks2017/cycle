package com.dharani.demoo.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dharani.demoo.Route_details_activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Created by ANDROIDPC-4 on 24-Mar-17.
 */

public class Get_Routes_async extends AsyncTask<String, String, String> {

    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ProgressDialog pd;
    Route_details_activity route_details_activity;
    Context context;
    String uname,pass;

    public Get_Routes_async(Context context, Route_details_activity route_details_activity) {
        this.context = context;
        this.route_details_activity = route_details_activity;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("fetching route details...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String jsonData = null;
        okhttp3.Response response;

        OkHttpClient client = new OkHttpClient.Builder().build();


        Request request = new Request.Builder()
                .url("http://52.36.211.72:5555/gateway/Chennai/v1/bus/routes")
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
                route_details_activity.runOnUiThread(new Runnable() {
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
            route_details_activity.r_to_name.clear();
            route_details_activity.route_id.clear();
            route_details_activity.r_from_name.clear();
            route_details_activity.source_set.clear();
            //route_details_activity.source_set.add("select souce");
      try {
                JSONArray object = new JSONArray(jsonData);
          for (int i=0;i<object.length();i++){
              JSONObject ob1=object.getJSONObject(i);
              route_details_activity.r_from_name.add(ob1.getString("source"));
              route_details_activity.source_set.add(ob1.getString("source"));
              route_details_activity.r_to_name.add(ob1.getString("destination"));
              route_details_activity.route_id.add(ob1.getString("route_id"));

          }


route_details_activity.set_source();

            } catch (JSONException e) {
                e.printStackTrace();

            }


        } else
            Toast.makeText(context, "cannot reach server..please try again later", Toast.LENGTH_SHORT).show();
    }
}
