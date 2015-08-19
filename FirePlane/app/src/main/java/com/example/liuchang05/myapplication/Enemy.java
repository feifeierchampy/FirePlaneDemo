package com.example.liuchang05.myapplication;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Morning on 2015/8/18.
 */
public class Enemy {

    static final int ENEMY_STEP_Y = 15;

    /** 敌机的XY坐标 **/
    public int m_posX = -1000;
    public int m_posY = -1000;
    private Center center = null;

    //敌机图片
    private Bitmap eBitmap = null;

    /**是否更新绘制敌机**/
    boolean mFacus = false;


    public Enemy(Bitmap EnemyBitmap)
    {
        eBitmap = EnemyBitmap;
        center = new Center();
    }

    /**初始化坐标**/
    public void init(int x, int y) {
        m_posX = x;
        m_posY = y;
        mFacus = true;
    }



    /**绘制子弹**/
    public void DrawEnemy(Canvas canvas, Paint paint) {
        if (mFacus) {
            canvas.drawBitmap(eBitmap,m_posX,m_posY,paint);
        }
    }

    /**更新子弹的坐标点**/
    public void UpdateEnemy() {
        if (mFacus) {
            m_posY += ENEMY_STEP_Y;
        }
    }

    public Center getCenter(){
        center.centerX = m_posX + (eBitmap.getWidth()/2);
        center.centerY = m_posY + (eBitmap.getHeight()/2);
        return center;
    }

}
