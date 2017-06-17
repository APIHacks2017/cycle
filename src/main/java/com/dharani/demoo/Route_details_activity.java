package com.dharani.demoo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dharani.demoo.Async.Get_Busno_async;
import com.dharani.demoo.Async.Get_Routes_async;
import com.dharani.demoo.Async.Get_fuelrate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class Route_details_activity extends AppCompatActivity {
    public List<String> r_from_name = new ArrayList<>();
    public List<String> route_id = new ArrayList<>();
    public List<String> r_to_name = new ArrayList<>();
    public HashSet source_set = new HashSet();
    public List<String> new_source = new ArrayList<>();
    public String rfrom="", rto="";
    Spinner source_spinner, dest_spinner;
    public List<String> new_dest = new ArrayList<>();
    public Button find_busno;
    public TextView busno;
    public String busNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_details_activity);
        new Get_Routes_async(this, Route_details_activity.this).execute();


        busno = (TextView) findViewById(R.id.bus_number);
        find_busno = (Button) findViewById(R.id.find_bus);
        source_spinner = (Spinner) findViewById(R.id.source_spinner);
        dest_spinner = (Spinner) findViewById(R.id.dest_spinner);
        setTitle("MyAssist");

        source_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



                if (i != 0) {
                    rfrom = new_source.get(i);
                    retrieveValuesFromListMethod1();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        dest_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {

                    rto = new_dest.get(i);

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        find_busno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!rfrom.isEmpty()&&!rto.isEmpty()){
                    new Get_Busno_async(Route_details_activity.this,Route_details_activity.this).execute();
                }else{
                    Toast.makeText(getApplicationContext(), "Choose source and destination first..", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    public void set_source() {


        new_source.clear();
        new_source.add("select souce");
        Iterator itr = source_set.iterator();
        while (itr.hasNext()) {

            new_source.add((String) itr.next());


        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text, new_source);
        source_spinner.setAdapter(adapter);
    }

    public void set_dest(List<String> dest) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_text, dest);
        dest_spinner.setAdapter(adapter);
    }


    public void retrieveValuesFromListMethod1() {


        new_dest.clear();
        new_dest.add("select destination");

        for (int j = 0; j < r_from_name.size(); j++) {
            if (r_from_name.get(j).equals(rfrom)) {
                new_dest.add(r_to_name.get(j));
                Log.e(r_from_name.get(j),r_to_name.get(j));

            }

        }
        set_dest(new_dest);
    }
    public void setBusNumber(String dd) {
        busno.setText(dd);
    }

}
