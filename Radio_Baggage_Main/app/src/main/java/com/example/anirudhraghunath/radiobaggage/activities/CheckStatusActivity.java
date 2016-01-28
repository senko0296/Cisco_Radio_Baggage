package com.example.anirudhraghunath.radiobaggage.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anirudhraghunath.radiobaggage.R;
import com.example.anirudhraghunath.radiobaggage.resources.Constants;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class CheckStatusActivity extends AppCompatActivity {

    ParseQuery<ParseObject> query;
    String status, uid;
    FloatingActionButton refreshFab;
    ImageView statusImageView;
    TextView statusTextView;
    AppCompatButton estimateTimeButton;
    boolean isEmbarking;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_status);

        sharedPreferences = getSharedPreferences("UIDSharedPreference", Context.MODE_PRIVATE);
        uid = sharedPreferences.getString("UID", null);

        refreshFab = (FloatingActionButton) findViewById(R.id.refresh_fab);
        statusImageView = (ImageView) findViewById(R.id.status_image_view);
        statusTextView = (TextView) findViewById(R.id.status_text_view);
        estimateTimeButton = (AppCompatButton) findViewById(R.id.estimate_button);

        getStatus();

        refreshFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getStatus();
            }
        });

        estimateTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckStatusActivity.this, EstimateTimeActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

    }

    public void getStatus() {

        final ProgressDialog pgDialog = new ProgressDialog(CheckStatusActivity.this);
        pgDialog.setMessage("Getting status..");
        pgDialog.show();
        query = ParseQuery.getQuery("rfid");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {

                pgDialog.hide();
                if (e == null) {

                    for (int i = 0; i < objects.size(); i++)
                        if (uid.equals(objects.get(i).getString("uid"))) {

                            status = objects.get(i).getString("status");
                            isEmbarking = objects.get(i).getBoolean("isEmbarking");
                            break;
                        }

                    if(isEmbarking)
                        estimateTimeButton.setVisibility(View.VISIBLE);
                    statusTextView.setText(status);
                    if (status.toLowerCase().equals(Constants.BAGGAGE_DROP))
                        statusImageView.setImageResource(R.drawable.ic_baggage_drop);
                    if (status.toLowerCase().equals(Constants.BELT))
                        statusImageView.setImageResource(R.drawable.ic_belt_drop);
                    if (status.toLowerCase().equals(Constants.CARGO))
                        statusImageView.setImageResource(R.drawable.ic_cargo);
                    if(status.toLowerCase().equals(Constants.LOADED) || status.toLowerCase().equals(Constants.UNLOADED))
                        statusImageView.setImageResource(R.drawable.ic_plane);
                } else
                    e.printStackTrace();

            }
        });
    }
}
