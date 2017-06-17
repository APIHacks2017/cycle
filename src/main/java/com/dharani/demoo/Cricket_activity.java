package com.dharani.demoo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dharani.demoo.Async.Get_match_calender_async;
import com.dharani.demoo.Async.Get_match_details_async;

import java.util.ArrayList;
import java.util.List;

public class Cricket_activity extends AppCompatActivity {
    TextView teamnaeme, score, typem, over, batsmen, bowler,inni_text;
    Button refresh;
    public List<String> m_id = new ArrayList<>();
    public List<String> m_name = new ArrayList<>();
    public List<String> m_date = new ArrayList<>();
    LinearLayout data, nodata;
   public String unique_id = "";
    public AlertDialog.Builder dialogBuilder;
    AlertDialog b;

    public String selected_city, diesel_rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket_activity);
        teamnaeme = (TextView) findViewById(R.id.team_text);
        score = (TextView) findViewById(R.id.score_text);
        typem = (TextView) findViewById(R.id.m_type);
        over = (TextView) findViewById(R.id.over_text);
        batsmen = (TextView) findViewById(R.id.batsmen_text);
        bowler = (TextView) findViewById(R.id.bowler_text);
        inni_text = (TextView) findViewById(R.id.inni_text);
        refresh = (Button) findViewById(R.id.refresh);
        data = (LinearLayout) findViewById(R.id.data);
        nodata = (LinearLayout) findViewById(R.id.nodata);
        data.setVisibility(View.GONE);
        nodata.setVisibility(View.VISIBLE);
        new Get_match_calender_async(Cricket_activity.this,Cricket_activity.this).execute();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Get_match_details_async(Cricket_activity.this, Cricket_activity.this).execute();
            }
        });
    }

    public void showChangeLangDialog() {
         dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialoge_layout, null);
        dialogBuilder.setView(dialogView);

        final Button m1 = (Button) dialogView.findViewById(R.id.m1);
        final Button m2 = (Button) dialogView.findViewById(R.id.m2);
        final Button m3 = (Button) dialogView.findViewById(R.id.m3);
        final Button m4 = (Button) dialogView.findViewById(R.id.m4);
        final Button m5 = (Button) dialogView.findViewById(R.id.m5);

        m1.setText(m_name.get(0) + "\n" + m_date.get(0));
        m2.setText(m_name.get(1) + "\n" + m_date.get(1));
        m3.setText(m_name.get(2) + "\n" + m_date.get(2));
        m4.setText(m_name.get(3) + "\n" + m_date.get(3));
        m5.setText(m_name.get(4) + "\n" + m_date.get(4));

        m1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m_id.get(0).contains("will")){
                    Toast.makeText(Cricket_activity.this,"Match not yet started", Toast.LENGTH_SHORT).show();
                }else {
                    unique_id = m_id.get(0);
                    new Get_match_details_async(Cricket_activity.this, Cricket_activity.this).execute();
                }
            }
        });
        m2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m_id.get(1).contains("will")){
                    Toast.makeText(Cricket_activity.this,"Match not yet started", Toast.LENGTH_SHORT).show();
                }else {
                    unique_id = m_id.get(1);
                    new Get_match_details_async(Cricket_activity.this, Cricket_activity.this).execute();
                }
            }
        });
        m3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m_id.get(2).contains("will")){
                    Toast.makeText(Cricket_activity.this,"Match not yet started", Toast.LENGTH_SHORT).show();
                }else {
                    unique_id = m_id.get(2);
                    new Get_match_details_async(Cricket_activity.this, Cricket_activity.this).execute();
                }
            }
        });
        m4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m_id.get(3).contains("will")){
                    Toast.makeText(Cricket_activity.this,"Match not yet started", Toast.LENGTH_SHORT).show();
                }else {
                    unique_id = m_id.get(3);
                    new Get_match_details_async(Cricket_activity.this, Cricket_activity.this).execute();
                }
            }
        });
        m5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(m_id.get(4).contains("will")){
                    Toast.makeText(Cricket_activity.this,"Match not yet started", Toast.LENGTH_SHORT).show();
                }else {
                    unique_id = m_id.get(4);
                    new Get_match_details_async(Cricket_activity.this, Cricket_activity.this).execute();
                }
            }
        });

        dialogBuilder.setTitle("Choose Match");


        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
                Cricket_activity.this.finish();
            }
        });
        b = dialogBuilder.create();
        b.show();
    }

public void setmdetails(String team,String type,String score1,String over1,String bm1,String bw1,String inni_text1){
    if(b!=null)
    b.cancel();
    teamnaeme.setText(team);
    typem.setText(type);
    score.setText(score1);
    over.setText(over1);
    batsmen.setText(bm1);
    bowler.setText(bw1);
    inni_text.setText(inni_text1);
    data.setVisibility(View.VISIBLE);
    nodata.setVisibility(View.GONE);
}

}
