package com.example.liuchang05.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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

    //得分
    private int mark = 0;
    //排名
    private int rank = 1;

    private TextView tvGrade = null;
    private Button btnOK = null;
    private Button btnRestart = null;
    private EditText edName = null;
    private ListView ls = null;

    private DatabaseUtil db = null;

    private List<HashMap<String,Object>> data = null;

    private SimpleAdapter mAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_over);

        Intent intent = getIntent();
        mark = intent.getIntExtra("MARK", -100);

        //得分TextView
        tvGrade = (TextView)findViewById(R.id.tvGrade);
        tvGrade.setText(Integer.toString(mark));
        //确定按钮
        btnOK = (Button)findViewById(R.id.btnOK);
        //重新开始按钮
        btnRestart = (Button)findViewById(R.id.btnReStart);
        //昵称输入框
        edName = (EditText)findViewById(R.id.etUsername);
        //排行榜
        ls = (ListView) findViewById(R.id.ls1);
        //存储ListView中的数据
        data = new ArrayList<HashMap<String,Object>>();
        
        //数据库初始化
        db = new DatabaseUtil(this);



        //输入用户名确定按钮的监听
        btnOK.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    //打开数据库
                    db.Open();

                    //得到用户输入的名字
                    String name = edName.getText().toString();

                    //将用户名、分数 插入到表中
                    long temp = db.InsertUser(name, mark);

                    //获取表中全部数据
                    Cursor mCursor = db.fetchAllData();


                    //表头数据 第一行
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
                    mAdapter = new SimpleAdapter(com.example.liuchang05.myapplication.OverActivity.this, data, R.layout.item,
                            new String[]{"rank", "name", "grade"}, new int[]{R.id.rangId,R.id.usernameId,R.id.gradeId});
                    //将mAdapter
                    ls.setAdapter(mAdapter);

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    db.Close();
                }
            }
        });

        //重新开始
        btnRestart.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View v){
                Intent mIntent = new Intent();
                mIntent.setClass(OverActivity.this, GameActivity.class);
                startActivity(mIntent);
                finish();

            }
        });
    }
}
