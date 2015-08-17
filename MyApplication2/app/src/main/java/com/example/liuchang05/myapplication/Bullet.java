package com.example.liuchang05.myapplication;

/**
 * Created by CHAMPION on 2015/8/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Bullet {

    /**子弹的Y轴速度**/
    static final int BULLET_STEP_Y = 35;


    /** 子弹的XY坐标 **/
    public int m_posX = 0;
    public int m_posY = 0;

    //子弹图片
    private Bitmap bBitmap = null;


    /**是否更新绘制子弹**/
    boolean mFacus = false;


    public Bullet(Bitmap bulletBitmap)
    {
        bBitmap = bulletBitmap;

    }

    /**初始化坐标**/
    public void init(int x, int y) {
        m_posX = x;
        m_posY = y;
        mFacus = true;
    }



    /**绘制子弹**/
    public void DrawBullet(Canvas canvas, Paint paint) {
        if (mFacus) {
            canvas.drawBitmap(bBitmap,m_posX,m_posY,paint);
        }
    }

    /**更新子弹的坐标点**/
    public void UpdateBullet() {
        if (mFacus) {
            m_posY -= BULLET_STEP_Y;
        }

    }

}
