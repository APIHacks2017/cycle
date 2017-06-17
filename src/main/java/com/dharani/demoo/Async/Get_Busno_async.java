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
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;


/**
 * Created by ANDROIDPC-4 on 24-Mar-17.
 */

public class Get_Busno_async extends AsyncTask<String, String, String> {

    final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    ProgressDialog pd;
    Route_details_activity route_details_activity;
    Context context;
    String uname,pass;

    public Get_Busno_async(Context context, Route_details_activity route_details_activity) {
        this.context = context;
        this.route_details_activity = route_details_activity;


    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("fetching bus number ...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected String doInBackground(String... params) {
        String jsonData = null;
        okhttp3.Response response;

        OkHttpClient client = new OkHttpClient.Builder().build();



        /*RequestBody body = new FormBody.Builder()
                .add("source",route_details_activity.rfrom)
                .add("destination",route_details_activity.rto)
                .build();*/
        HttpUrl url = new HttpUrl.Builder()
                .scheme("http")
                .host("52.36.211.72").port(5555).addPathSegments("gateway/Chennai/v1/bus/routenumbers")
                .addQueryParameter("source",route_details_activity.rfrom)
                .addQueryParameter("destination",route_details_activity.rto)
                .build();
        Log.d("from", route_details_activity.rfrom);
        Log.d("to", route_details_activity.rto);
        Log.d("url", String.valueOf(url));
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
            try {
                JSONArray array=new JSONArray(jsonData);
                if(array.length()>0) {
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);

                        route_details_activity.busNumber = route_details_activity.busNumber + object.getString("route_Num");
                        if(i!=array.length()-1)
                        route_details_activity.busNumber = route_details_activity.busNumber +", ";
                    }
                    route_details_activity.setBusNumber(route_details_activity.busNumber);
                }else{
                    route_details_activity.setBusNumber("No bus available");
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else
            Toast.makeText(context, "cannot reach server..please try again later", Toast.LENGTH_SHORT).show();
    }
}
