package com.example.liuchang05.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by Morning on 2015/8/18.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    //整个过程
    private Context mContext = null;
    private Thread mThread = null;
    private boolean mIsRunning = false;

    //屏幕与绘图
    private int mScreenWidth = 0;
    private int mScreenHeight = 0;
    private SurfaceHolder mSurfaceHolder = null;
    private Canvas mCanvas = null;
    private Paint mPaint = null;

    //主机
    private Plane mPlane =null;
    private int mPlaneHeight = 0;
    private int mPlaneWidth = 0;
    public int mTouchPosX = 0;
    public int mTouchPosY = 0;
    public boolean mTouching = false;

    //子弹
    Bullet mBullet[] = null;
    Bitmap mBitbullet[] = null;
    private int mBulletBitHeight = 0;
    private int mBulletBitWidth = 0;
    public int mBulletSendId = 0;
    public Long mBulletSendTime = 0L;
    final static int BULLET_COUNT = 10;
    final static int FIRE_TIME = 700;

    //敌机
    private Enemy mEnemy[] = null;
    private Bitmap mBitEnemy[] = null;
    private long mEnemySendTime = 0L;
    private int mEnemySendId = 0;
    private int mEnemyBitWidth = 0;
    private int mEnemyBitHeight = 0;
    private static int ENEMY_TIME = 700;
    private static int ENEMY_COUNT = 10;


    public GameView(Context context, int screenWidth, int screenHeight) {
        super(context);
        mContext = context;

        mScreenWidth = screenWidth;
        mScreenHeight = screenHeight;

        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);

        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);

        init();
    }

    //初始化游戏资源
    private void init() {

        //创建主机对象
        mPlane = new Plane(ReadBitmap(mContext, R.drawable.plane));
        mPlaneHeight = mPlane.getPlaneBitHeight();
        mPlaneWidth = mPlane.getPlaneBitWidth();
        mPlane.init(mScreenWidth/2, mScreenHeight - mPlaneHeight);

        //创建子弹对象
        mBullet = new Bullet[BULLET_COUNT];
        mBitbullet = new Bitmap[BULLET_COUNT];
        for (int i = 0; i < BULLET_COUNT; i++) {
            mBitbullet[i] = ReadBitmap(mContext, R.drawable.bullet);
            mBullet[i] = new Bullet(mBitbullet[i]);
        }
        mBulletBitHeight = mBitbullet[0].getHeight();
        mBulletBitWidth = mBitbullet[0].getWidth();
        mBulletSendTime = System.currentTimeMillis();

        //创建敌机对象
        mEnemy = new Enemy[ENEMY_COUNT];
        mBitEnemy = new Bitmap[ENEMY_COUNT];
        for(int i = 0; i < ENEMY_COUNT; i++){
            mBitEnemy[i] = ReadBitmap(mContext,R.drawable.enemy);
            mEnemy[i] = new Enemy(mBitEnemy[i]);
        }
        mEnemyBitWidth = mBitEnemy[0].getWidth();
        mEnemyBitHeight = mBitEnemy[0].getHeight();
        mEnemySendTime = System.currentTimeMillis();
    }

    //绘制图形
    public void Draw() {
        //绘制主机
        mPlane.DrawPlane(mCanvas, mPaint);
        if (mTouching)
        {
            mPlane.UpdatePlane(mTouchPosX, mTouchPosY);
        }

        //绘制子弹
        for (int i = 0; i < BULLET_COUNT; i++) {
            mBullet[i].DrawBullet(mCanvas, mPaint);
            mBullet[i].UpdateBullet();
        }
        if (mBulletSendId < BULLET_COUNT) {
            long now = System.currentTimeMillis();
            if (now - mBulletSendTime >= FIRE_TIME) {
                mBullet[mBulletSendId].init(mPlane.mPlanePosX + mPlaneWidth/2 - mBulletBitWidth/2 , mPlane.mPlanePosY - mBulletBitHeight);

                mBulletSendTime = now;
                mBulletSendId++;
            }
        } else {
            mBulletSendId = 0;
        }

        //绘制敌机
        for(int i = 0; i < ENEMY_COUNT; i++){
            mEnemy[i].DrawEnemy(mCanvas,mPaint);
            mEnemy[i].UpdateEnemy();
        }
        if(mEnemySendId < ENEMY_COUNT){
            long now = System.currentTimeMillis();
            if(now - mEnemySendTime >= ENEMY_TIME){
                mEnemy[mEnemySendId].init(random(),0);
                mEnemySendTime = now;
                mEnemySendId++;
            }
        } else {
            mEnemySendId = 0;
        }
    }

    public int random(){
        Random random = new Random(System.currentTimeMillis());
        int result = random.nextInt(mScreenWidth);
        return result;
    }

    public void UpdateTouchEvent(int x, int y, boolean touching) {
        mTouching = touching;
        mTouchPosX = x - mPlaneWidth/2;
        mTouchPosY = y - mPlaneHeight/2;
    }

    public Bitmap ReadBitmap(Context context, int resId) {
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is, null, opt);
    }

    public void Listen(){
        for(int i = 0; i < ENEMY_COUNT; i++){
            Center enemyCenter = mEnemy[i].getCenter();
            //子弹触敌机
            for(int j = 0; j < BULLET_COUNT; j++){
                Center bulletCenter = mBullet[j].getCenter();
                if(Math.abs(enemyCenter.centerX - bulletCenter.centerX) < mBulletBitWidth/2 + mEnemyBitWidth/2 &&
                        Math.abs(enemyCenter.centerY - bulletCenter.centerY) < mBulletBitHeight/2 + mEnemyBitHeight/2 ){
                    mEnemy[i].init(random(),0);
                    mBullet[j].init(mPlane.mPlanePosX + mPlaneWidth/2 - mBulletBitWidth/2 , mPlane.mPlanePosY - mBulletBitHeight);
                }
            }

            //敌机触主机
            Center planeCenter = mPlane.getCenter();
            if(Math.abs(enemyCenter.centerX - planeCenter.centerX) < mPlaneWidth/2 + mEnemyBitWidth/2 &&
                    Math.abs(enemyCenter.centerY - planeCenter.centerY) < mPlaneHeight/2 + mEnemyBitHeight/2 ){
                Intent intent = new Intent(mContext,OverActivity.class);
                mContext.startActivity(intent);
                surfaceDestroyed(mSurfaceHolder);
                ((Activity)mContext).finish();
            }
        }
    }

    @Override
    public void run() {
        while (mIsRunning) {
            //在这里加上线程安全锁
            synchronized (mSurfaceHolder) {
                mCanvas = mSurfaceHolder.lockCanvas();//可优化
                mCanvas.drawColor(Color.BLACK);
                Draw();
                Listen();
                mSurfaceHolder.unlockCanvasAndPost(mCanvas);
            }
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
                               int arg3) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        mIsRunning = true;
        mThread = new Thread(this);
        mThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        mIsRunning = false;
    }

}
