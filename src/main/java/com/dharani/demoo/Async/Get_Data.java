package com.dharani.demoo.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.dharani.demoo.Fuels_details_activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by ANDROIDPC-4 on 24-Mar-17.
 */

public class Get_Data extends AsyncTask<String, String, String> {

    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ProgressDialog pd;
    Fuels_details_activity fuelsdetailsactivity;
    Context context;
    String uname,pass;

    public Get_Data(Context context,  Fuels_details_activity fuelsdetailsactivity) {
        this.context = context;
        this.fuelsdetailsactivity = fuelsdetailsactivity;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setCancelable(false);
        pd.setMessage("Fetching cities...");
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String jsonData = null;
        okhttp3.Response response;

        OkHttpClient client = new OkHttpClient.Builder().build();



        RequestBody body = new FormBody.Builder()
                .build();
        Request request = new Request.Builder()
                .url("http://52.36.211.72:5555/gateway/FuelPriceIndia/1.0/main/city_list")
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
                fuelsdetailsactivity.runOnUiThread(new Runnable() {
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
            try {
                JSONObject object = new JSONObject(jsonData);
                JSONArray status = object.getJSONArray("cities");
if(status.length()>0) {
    fuelsdetailsactivity.cities.add("Select city");
    for (int i = 0; i < status.length(); i++) {
        Log.d("resuggelt", (String) status.get(i));
fuelsdetailsactivity.cities.add((String) status.get(i));
    }

    fuelsdetailsactivity.ff();
}else{
    Toast.makeText(context, "no data", Toast.LENGTH_SHORT).show();
}

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(context, "cannot reach server..please try again later", Toast.LENGTH_SHORT).show();
    }
}
