package com.example.liuchang05.myapplication;


import android.graphics.Color;
import android.os.Bundle;

import java.io.InputStream;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.view.Window;
import android.view.WindowManager;
;


public class MainActivity extends Activity {

    PlaneView mPlaneView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 全屏显示窗口
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 获取屏幕宽高
        Display display = getWindowManager().getDefaultDisplay();

        // 显示自定义的游戏View
        mPlaneView = new PlaneView(this, display.getWidth(), display.getHeight());
        setContentView(mPlaneView);
    }
    public boolean onTouchEvent(MotionEvent event) {
        // 获得触摸的坐标
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            // 触摸屏幕时刻
            case MotionEvent.ACTION_DOWN:
                mPlaneView.UpdateTouchEvent(x, y,true);
                break;
            // 触摸并移动时刻
            case MotionEvent.ACTION_MOVE:
                mPlaneView.UpdateTouchEvent(x, y,true);
                break;
            // 终止触摸时刻
            case MotionEvent.ACTION_UP:
                mPlaneView.UpdateTouchEvent(x, y,false);
                break;
        }
        return false;
    }

    public class PlaneView extends SurfaceView implements Callback, Runnable {
        /**
         * 屏幕的宽高*
         */
        private int mScreenWidth = 0;
        private int mScreenHeight = 0;

        Paint mPaint = null;



        //子弹对象的数量
        final static int BULLET_COUNT = 10;


        //每过1500毫秒发射一颗子弹
        final static int FIRE_TIME = 1300;



        /**
         * 游戏主线程*
         */
        private Thread mThread = null;
        /**
         * 线程循环标志*
         */
        private boolean mIsRunning = false;

        private SurfaceHolder mSurfaceHolder = null;
        private Canvas mCanvas = null;

        private Context mContext = null;


        Plane mPlane =null;

        //飞机图片的高度
        private int mPlaneHeight = 0;
        //飞机图片的宽度
        private int mPlaneWidth = 0;


        /**
         * 子弹类*
         */
        Bullet mBullet[] = null;
        Bitmap mBitbullet[] = null;

        //子弹图片高度
        private int mBulletBitHeight = 0;
        //子弹图片宽度
        private int mBulletBitWidth = 0;


        /**
         * 初始化发射子弹ID升序*
         */
        public int mSendId = 0;

        /**
         * 上一颗子弹发射的时间*
         */
        public Long mSendTime = 0L;


        /**
         * 手指在屏幕触摸的坐标*
         */
        public int mTouchPosX = 0;
        public int mTouchPosY = 0;


        /**
         * 标志手指在屏幕触摸中*
         */
        public boolean mTouching = false;


        public PlaneView(Context context, int screenWidth, int screenHeight) {
            super(context);
            mContext = context;

            mPaint = new Paint();
            mPaint.setColor(Color.WHITE);

            mScreenWidth = screenWidth;
            mScreenHeight = screenHeight;

            //获取mSurfaceHolder
            mSurfaceHolder = getHolder();
            mSurfaceHolder.addCallback(this);
            setFocusable(true);

            init();

        }

        //初始化游戏资源
        private void init() {

            //创建主机对象
            mPlane = new Plane(ReadBitmap(mContext, R.drawable.plane));

            mPlaneHeight = mPlane.getPlaneBitHeight();
            mPlaneWidth = mPlane.getPlaneBitWidth();

            //初始化主机坐标
            mPlane.init(mScreenWidth/2, mScreenHeight - mPlaneHeight);


            //创建子弹对象
            mBullet = new Bullet[BULLET_COUNT];
            mBitbullet = new Bitmap[BULLET_COUNT];

            for (int i = 0; i < BULLET_COUNT; i++) {
                mBitbullet[i] = ReadBitmap(mContext, R.drawable.bullet);

                mBulletBitHeight = mBitbullet[0].getHeight();
                mBulletBitWidth = mBitbullet[0].getWidth();
            }

            for (int i = 0; i < BULLET_COUNT; i++) {
                mBullet[i] = new Bullet(mBitbullet[i]);
            }

            mSendTime = System.currentTimeMillis();



        }

        //初始化绘制
        public void InitDraw() {
            /**绘制飞机动画**/
            mPlane.DrawPlane(mCanvas, mPaint);

            /**绘制子弹动画*/
            for (int i = 0; i < BULLET_COUNT; i++) {
                mBullet[i].DrawBullet(mCanvas, mPaint);
            }

        }

        //更新绘图(只是更新坐标)
        public void UpdateDraw()
        {
            /** 手指触摸屏幕更新飞机坐标 **/
            if (mTouching)
            {
                mPlane.UpdatePlane(mTouchPosX, mTouchPosY);
            }
            /** 更新子弹动画 **/
            for (int i = 0; i < BULLET_COUNT; i++) {
                /** 子弹出屏后重新赋值**/
                mBullet[i].UpdateBullet();

            }


            /**根据时间初始化为发射的子弹**/
            if (mSendId < BULLET_COUNT) {
                long now = System.currentTimeMillis();
                if (now - mSendTime >= FIRE_TIME) {
                    // mBullet[mSendId].init(mPlane.mPlanePosX - BULLET_LEFT_OFFSET, mPlane.mPlanePosY - BULLET_UP_OFFSET);
                    mBullet[mSendId].init(mPlane.mPlanePosX + mPlaneWidth/2 - mBulletBitWidth/2 , mPlane.mPlanePosY - mBulletBitHeight);

                    mSendTime = now;
                    mSendId++;
                }
            } else {
                mSendId = 0;
            }
        }


        protected void Draw() {

            InitDraw();

            UpdateDraw();


        }

        public void UpdateTouchEvent(int x, int y, boolean touching) {
            // 在这里检测按钮按下播放不同的特效
            mTouching = touching;
            mTouchPosX = x - mPlaneWidth/2;
            mTouchPosY = y - mPlaneHeight/2;
        }

        public Bitmap ReadBitmap(Context context, int resId) {
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            // 获取资源图片
            InputStream is = context.getResources().openRawResource(resId);
            return BitmapFactory.decodeStream(is, null, opt);
        }



        @Override
        public void run() {
            while (mIsRunning) {
                //在这里加上线程安全锁
                synchronized (mSurfaceHolder) {
                    /**拿到当前画布 然后锁定**/
                    mCanvas = mSurfaceHolder.lockCanvas();
                    mCanvas.drawColor(Color.BLACK);
                    Draw();
                    /**绘制结束后解锁显示在屏幕上**/
                    mSurfaceHolder.unlockCanvasAndPost(mCanvas);
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        @Override
        public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2,
                                   int arg3) {
            // surfaceView的大小发生改变的时候

        }

        @Override
        public void surfaceCreated(SurfaceHolder arg0) {
            /**启动游戏主线程**/
            mIsRunning = true;
            mThread = new Thread(this);
            mThread.start();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder arg0) {
            // surfaceView销毁的时候
            mIsRunning = false;
        }

    }
}


















