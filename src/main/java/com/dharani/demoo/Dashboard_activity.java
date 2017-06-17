package com.dharani.demoo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Dashboard_activity extends AppCompatActivity {
ImageView bus,petrol,cricket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_activity);

        bus=(ImageView)findViewById(R.id.bus_img);
        petrol=(ImageView)findViewById(R.id.petrol_img);
        cricket=(ImageView)findViewById(R.id.cricket_img);

        bus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Route_details_activity.class);
               startActivity(intent);
            }
        });petrol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Fuels_details_activity.class);
               startActivity(intent);
            }
        });
        cricket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Cricket_activity.class);
               startActivity(intent);
            }
        });
    }
}
