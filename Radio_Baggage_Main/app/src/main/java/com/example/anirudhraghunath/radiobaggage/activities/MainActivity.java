package com.example.anirudhraghunath.radiobaggage.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.example.anirudhraghunath.radiobaggage.R;

public class MainActivity extends AppCompatActivity {

    TextInputLayout contactTextInputLayout;
    AppCompatButton doneButton;
    SharedPreferences sharedPreferences;
    String contactNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("ContactNumberSharedPreference", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        contactNo = sharedPreferences.getString("Contact", null);

        if(contactNo != null){

            Intent intent = new Intent(MainActivity.this, ScanActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        contactTextInputLayout = (TextInputLayout) findViewById(R.id.contact_no_text_input_layout);
        doneButton = (AppCompatButton) findViewById(R.id.done_button);

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                contactTextInputLayout.setError(null);
                contactNo = contactTextInputLayout.getEditText().getText().toString();
                if(contactNo.isEmpty())
                    contactTextInputLayout.setError("Empty Number");

                if(!contactNo.isEmpty()){

                    editor.putString("Contact", contactNo);
                    editor.commit();

                    Intent intent = new Intent(MainActivity.this, ScanActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });



    }
}
