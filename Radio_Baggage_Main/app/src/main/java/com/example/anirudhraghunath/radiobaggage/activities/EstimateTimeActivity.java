package com.example.anirudhraghunath.radiobaggage.activities;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.anirudhraghunath.radiobaggage.R;

public class EstimateTimeActivity extends AppCompatActivity {

    TextView estimatedTimeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estimate_time);

        estimatedTimeTextView = (TextView) findViewById(R.id.estimated_time_text_view);

        new CountDownTimer(15000, 1000) {

            public void onTick(long millisUntilFinished) {

                long minutes = millisUntilFinished/(1000*60);
                long seconds = millisUntilFinished/(1000);
                if(seconds >= 60)
                    seconds -= 60;
                estimatedTimeTextView.setText(minutes + " mins " + seconds + " secs ");
            }

            public void onFinish() {
                estimatedTimeTextView.setText("Baggage Arrived");
            }
        }.start();
    }
}
