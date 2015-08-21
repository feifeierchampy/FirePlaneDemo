package com.example.liuchang05.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        Button btnStart = (Button)findViewById(R.id.btnStart);


        //开始按钮的监听
        btnStart.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent mIntent = new Intent();

                mIntent.setClass(MainActivity.this, GameActivity.class);
                startActivity(mIntent);

            }
        });
    }

}
