package com.dharani.demoo.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.dharani.demoo.Fuels_details_activity;

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

public class Get_fuelrate extends AsyncTask<String, String, String> {

    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ProgressDialog pd;
    Fuels_details_activity fuelsdetailsactivity;
    Context context;
    String uname,pass;

    public Get_fuelrate(Context context, Fuels_details_activity fuelsdetailsactivity) {
        this.context = context;
        this.fuelsdetailsactivity = fuelsdetailsactivity;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("fetching petrol rate...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String jsonData = null;
        okhttp3.Response response;

        OkHttpClient client = new OkHttpClient.Builder().build();



        RequestBody body = new FormBody.Builder()
                .add("city", fuelsdetailsactivity.selected_city)
                .add("fuel_type","petrol")
                .build();
        Request request = new Request.Builder()
                .url("http://52.36.211.72:5555/gateway/FuelPriceIndia/1.0/main/"+ fuelsdetailsactivity.selected_city+"/petrol/price")
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
                String status = object.getString("price");

fuelsdetailsactivity.setrate(status);

            } catch (JSONException e) {
                e.printStackTrace();

            }

            new Get_dieselrate(context, fuelsdetailsactivity).execute();
        } else
            Toast.makeText(context, "cannot reach server..please try again later", Toast.LENGTH_SHORT).show();
    }
}
