package com.example.liuchang05.myapplication;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        Button btnStart = (Button)findViewById(R.id.btnStart);
        //Button btnRank = (Button)findViewById(R.id.btnViewRank);

/*        final TextView tv1= (TextView)findViewById(R.id.tv1);
        final TextView tvGrade = (TextView)findViewById(R.id.tvGrade);

        final EditText etUsername = (EditText)findViewById(R.id.etUsername);
        final Button btnOK = (Button)findViewById(R.id.btnOK);
        final Button btnRestart = (Button)findViewById(R.id.btnReStart);*/




        //重新开始游戏的监听
        btnStart.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Intent mIntent = new Intent();

                //mIntent.putExtra("testIntent", "123");

                mIntent.setClass(MainActivity.this, GameActivity.class);
                startActivity(mIntent);

            }
        });


    }

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
