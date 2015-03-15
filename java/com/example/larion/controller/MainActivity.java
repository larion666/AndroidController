package com.example.larion.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    TextView myTextView;
    Button button2;
    Button button4;
    Button Go_to_activity_2;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTextView = (TextView) findViewById(R.id.textView);
        button2 = (Button) findViewById(R.id.button2);
        button4 = (Button) findViewById(R.id.button4);
        Go_to_activity_2 = (Button) findViewById(R.id.Go_to_activity_2);
        View.OnClickListener oclBtn2 = new View.OnClickListener() {
            public void onClick(View v) {
                counter++;
                myTextView.setText(String.valueOf(counter));
            }
        };
        View.OnClickListener oclBtn4 = new View.OnClickListener() {
            public void onClick(View v) {
                counter--;
                myTextView.setText(String.valueOf(counter));
            }
        };
        button2.setOnClickListener(oclBtn2);
        button4.setOnClickListener(oclBtn4);
        //myTextView.setText(String.valueOf(counter));
    }
    public void GoToVirtualJoystick(View view){
        Intent intent = new Intent(this, VirtualJoystick.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
