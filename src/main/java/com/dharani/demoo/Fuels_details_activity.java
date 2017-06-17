package com.dharani.demoo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.dharani.demoo.Async.Get_Data;
import com.dharani.demoo.Async.Get_fuelrate;

import java.util.ArrayList;
import java.util.List;

public class Fuels_details_activity extends AppCompatActivity {
    public TextView petrate,derate;
    public List<String>  cities=new ArrayList<>();
    public String selected_city,diesel_rate;
    Spinner city_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Get_Data(this,Fuels_details_activity.this).execute();
       petrate=(TextView)findViewById(R.id.pet_rate);
       derate=(TextView)findViewById(R.id.die_rate);
      city_spinner=(Spinner) findViewById(R.id.city_spinner);

setTitle("MyAssist");

        city_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_city = cities.get(i);
                if (i != 0) {
                    if (!selected_city.isEmpty()) {
                        new Get_fuelrate(Fuels_details_activity.this, Fuels_details_activity.this).execute();

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }

    public void ff(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.spinner_text,cities);
        city_spinner.setAdapter(adapter);
    }


    public void setrate(String rate){

        petrate.setText("\u20B9 "+rate);
    } public void setrate1(String rate){

        derate.setText("\u20B9 "+rate);
    }

}
