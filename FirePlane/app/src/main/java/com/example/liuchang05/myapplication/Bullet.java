package com.example.liuchang05.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by CHAMPION on 2015/8/16.
 */
public class Bullet {

    /**子弹的Y轴速度**/
    static final int BULLET_STEP_Y = 50;

    private Center center = null;


    /** 子弹的XY坐标 **/
    public int m_posX = 1000;
    public int m_posY = 1000;

    //子弹图片
    private Bitmap bBitmap = null;


    /**是否更新绘制子弹**/
    boolean mFacus = false;


    public Bullet(Bitmap bulletBitmap)
    {
        bBitmap = bulletBitmap;
        center = new Center();
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

    public Center getCenter(){
        center.centerX = m_posX + (bBitmap.getWidth()/2);
        center.centerY = m_posY + (bBitmap.getHeight()/2);
        return center;
    }

}
