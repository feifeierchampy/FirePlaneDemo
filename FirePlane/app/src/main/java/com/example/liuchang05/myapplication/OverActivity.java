package com.example.liuchang05.myapplication;

import android.content.Intent;
<<<<<<< HEAD
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OverActivity extends AppCompatActivity {

    //private TextView mMarkView = null;

    //�÷�
    public int mark = 0;
=======
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class OverActivity extends AppCompatActivity {

    private TextView mMarkView = null;
>>>>>>> fe2bdbd7b451dbc1708f8084a9c7c269b3bb6c35
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over);
<<<<<<< HEAD

        Intent intent = getIntent();
        mark = intent.getIntExtra("MARK", -100);
        //mMarkView = (TextView) findViewById(R.id.mark);
        //mMarkView.setText(""+mark);

        //获取TextView
        TextView tvGrade = (TextView)findViewById(R.id.tvGrade);
        tvGrade.setText(Integer.toString(mark));
        //
        Button btnOK = (Button)findViewById(R.id.btnOK);
        //
        Button btnRestart = (Button)findViewById(R.id.btnReStart);
        //
        final EditText edName = (EditText)findViewById(R.id.etUsername);


        btnOK.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                //数据库连接
                DatabaseUtil db = new DatabaseUtil(com.example.liuchang05.myapplication.OverActivity.this);
                try {
                    db.Open();

                    //得到输入的用户名
                    String name = edName.getText().toString();

                    long temp = db.InsertUser(name, mark);

                    Cursor mCursor = db.fetchAllData();

                    //startManagingCursor(mCursor);
                    int rank = 1;
                    List<HashMap<String,Object>> data = new ArrayList<HashMap<String,Object>>();
                    HashMap<String,Object> fhashMap = new HashMap<String, Object>();

                    fhashMap.put("rank", "Rank");
                    fhashMap.put("name", "Name");
                    fhashMap.put("grade", "Grade");
                    data.add(fhashMap);

                    if (mCursor != null) {
                        while (mCursor.moveToNext()&&(rank < 15)) {
                            //Log.i("user:", "grade:" + mCursor.getString(0) + mCursor.getString(1));
                            HashMap<String,Object> hashMap = new HashMap<String, Object>();

                            hashMap.put("rank", rank);
                            hashMap.put("name", mCursor.getString(1));
                            hashMap.put("grade", mCursor.getString(2));

                            data.add(hashMap);

                            rank++;
                        }
                    }

                    ListView ls = (ListView) findViewById(R.id.ls1);

                    SimpleAdapter mAdapter = new SimpleAdapter(com.example.liuchang05.myapplication.OverActivity.this, data, R.layout.item,
                            new String[]{"rank", "name", "grade"}, new int[]{R.id.rangId,R.id.usernameId,R.id.gradeId});

//                    Adapter mAdapter = new Adapter(com.example.liuchang05.startdemo.OverActivity.this, R.layout.item,
//                            mCursor, new String[]{"_id","username", "grade"},
//                            new int[]{R.id.rangId, R.id.usernameId, R.id.gradeId});

//                    ListAdapter mAdapter = new SimpleCursorAdapter(com.example.liuchang05.startdemo.OverActivity.this, R.layout.item,
//                            mCursor, new String[]{"_id","username", "grade"}, new int[]{R.id.rangId, R.id.usernameId, R.id.gradeId});

                    ls.setAdapter(mAdapter);

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    db.Close();
                }
            }
        });

        //重新开始按钮的监听
        btnRestart.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent mIntent = new Intent();

                mIntent.setClass(OverActivity.this, GameActivity.class);
                startActivity(mIntent);
            }
        });



/*            if (mCursor != null) {
                while (mCursor.moveToNext()) {
                    Log.i("user:", "grade:" + mCursor.getString(0) + mCursor.getString(1));
                }

            }*/



=======
        Intent intent = getIntent();
        int mark = intent.getIntExtra("MARK",-100);
        mMarkView = (TextView) findViewById(R.id.mark);
        mMarkView.setText(""+mark);
>>>>>>> fe2bdbd7b451dbc1708f8084a9c7c269b3bb6c35
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_over, menu);
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
