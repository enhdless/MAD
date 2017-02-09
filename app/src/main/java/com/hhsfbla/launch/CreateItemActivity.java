package com.hhsfbla.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by zhenfangchen on 2/7/17.
 */

public class CreateItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private String selectedCondition="";
    private String uid, fid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_item);

        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        fid = intent.getStringExtra("fid");

        Toolbar toolbar = (Toolbar) findViewById(R.id.item_create_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Spinner conditionSelection = (Spinner) findViewById(R.id.item_spinner);
        String[] conditions = {"Bad", "Good", "New"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, conditions);
        conditionSelection.setAdapter(adapter);

        Button nextButton = (Button) findViewById(R.id.item_create_next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((TextView)findViewById(R.id.item_name_field)).getText().length() == 0 || ((TextView)findViewById(R.id.item_price_field)).getText().length() == 0
                        || ((TextView)findViewById(R.id.item_description_field)).getText().length() == 0 || selectedCondition.length() == 0) {
                    Toast.makeText(CreateItemActivity.this, "Enter all fields first!", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent nextPageIntent = new Intent(CreateItemActivity.this, FinishCreateItemActivity.class);
                    nextPageIntent.putExtra("name", ((TextView)findViewById(R.id.item_name_field)).getText());
                    nextPageIntent.putExtra("price", ((TextView)findViewById(R.id.item_price_field)).getText());
                    nextPageIntent.putExtra("description", ((TextView)findViewById(R.id.item_description_field)).getText());
                    nextPageIntent.putExtra("condition", selectedCondition);
                    nextPageIntent.putExtra("uid", uid);
                    nextPageIntent.putExtra("fid", fid);
                    CreateItemActivity.this.startActivity(nextPageIntent);
                }
            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        String s = (String) parent.getItemAtPosition(pos);
        if (s.equals("Bad")) {
            selectedCondition = "Bad";
        } else if (s.equals("Good")) {
            selectedCondition = "Good";
        } else if (s.equals("New")) {
            selectedCondition = "New";
        }
    }

    public void onNothingSelected(AdapterView<?> parent) {

    }

}
