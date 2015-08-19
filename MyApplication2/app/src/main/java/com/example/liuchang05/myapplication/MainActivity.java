package com.example.liuchang05.myapplication;


import android.os.Bundle;
import android.app.Activity;
import android.view.Display;
import android.view.MotionEvent;



public class MainActivity extends Activity {

    private GameView mGameView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 获取屏幕宽高
        Display display = getWindowManager().getDefaultDisplay();

        // 显示自定义的游戏View
        mGameView = new GameView(this, display.getWidth(), display.getHeight());
        setContentView(mGameView);


    }


    public boolean onTouchEvent(MotionEvent event) {
        // 获得触摸的坐标
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            // 触摸屏幕时刻
            case MotionEvent.ACTION_DOWN:
                mGameView.UpdateTouchEvent(x, y,true);
                break;
            // 触摸并移动时刻
            case MotionEvent.ACTION_MOVE:
                mGameView.UpdateTouchEvent(x, y,true);
                break;
            // 终止触摸时刻
            case MotionEvent.ACTION_UP:
                mGameView.UpdateTouchEvent(x, y,false);
                break;
        }
        return false;
    }


}