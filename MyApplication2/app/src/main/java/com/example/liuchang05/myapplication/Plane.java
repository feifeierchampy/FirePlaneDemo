package com.example.liuchang05.myapplication;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by CHAMPION on 2015/8/16.
 */
public class Plane {

    //飞机移动速度
    static final int PLANE_SPEED = 50;

    Center center = null;
    //飞机在屏幕中的坐标
    public int mPlanePosX = 1000;
    public int mPlanePosY = 1000;

    private Bitmap pBitmap = null;


    public Plane(Bitmap planeBitmap)
    {
        pBitmap = planeBitmap;
        center = new Center();
    }

    //得到飞机图片的高度
    public int getPlaneBitHeight()
    {
        return pBitmap.getHeight();
    }

    //得到飞机图片的宽度
    public int getPlaneBitWidth()
    {
        return pBitmap.getWidth();
    }

    //初始化飞机坐标
    public void init(int x, int y)
    {
        mPlanePosX = x;
        mPlanePosY = y;
    }

    //绘制飞机
    public void DrawPlane(Canvas canvas, Paint paint)
    {
        canvas.drawBitmap(pBitmap, mPlanePosX, mPlanePosY, paint);
    }

    //更新飞机位置
    public void UpdatePlane(int x, int y)
    {

        if (mPlanePosX < x) {
            mPlanePosX += PLANE_SPEED;
        } else {
            mPlanePosX -= PLANE_SPEED;
        }
        if (mPlanePosY < y) {
            mPlanePosY += PLANE_SPEED;
        } else {
            mPlanePosY -= PLANE_SPEED;
        }

        if (Math.abs(mPlanePosX - x) <= PLANE_SPEED) {
            mPlanePosX = x;
        }
        if (Math.abs(mPlanePosY - y) <= PLANE_SPEED) {
            mPlanePosY = y;
        }
    }

    public Center getCenter(){
        center.centerX = mPlanePosX + (pBitmap.getWidth()/2);
        center.centerY = mPlanePosY + (pBitmap.getHeight()/2);
        return center;
    }

}
