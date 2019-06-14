package com.example.mrv.wqhrd;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsoluteLayout;
import android.widget.TextView;



public class PlayActivity extends AppCompatActivity {
    public static final Integer TEXT_REQUEST = 1;
    GestureDetector myDetector;
    static int steps=0;
    private int time=0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myDetector = new GestureDetector(this, new MyGestureListener());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        steps=0;
        Intent intent = getIntent();
        int level = intent.getIntExtra(RoundActivity.EXTRA_MESSAGE, 1);
        setChess();
        initChess(level);
        handler.postDelayed(runnable, 1000);
        }
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time++;
            TextView timer=findViewById(R.id.timer);
            timer.setText("时间:"+time+"s");
            handler.postDelayed(this, 1000);
        }
    };
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return myDetector.onTouchEvent(event);}

        private void setChess() {
        Resources res=getResources();
        for(int i=1;i<AboutGame.chessNum+1;i++) {
            AboutGame.chess[i-1] =  findViewById(res.getIdentifier("chess" + i, "id", getPackageName()));
        }
    }
    public void initChess(int level){
        DisplayMetrics dm = getResources().getDisplayMetrics();
        AboutGame.phoneHeight = dm.heightPixels;
        AboutGame.phoneWidth = dm.widthPixels;
        AboutGame.squareSize=AboutGame.phoneWidth/4;
        for(int i=0;i<AboutGame.chessNum;i++)
        {
            AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) AboutGame.chess[i].getLayoutParams();
        layoutParams.width = AboutGame.chessSize[2*i]*AboutGame.squareSize;
        layoutParams.height = AboutGame.chessSize[2*i+1]*AboutGame.squareSize;

        layoutParams.x = AboutGame.chessPosition[level-1][i*2]*AboutGame.squareSize;
        layoutParams.y = AboutGame.chessPosition[level-1][i*2+1]*AboutGame.squareSize;
        AboutGame.chess[i].setLayoutParams(layoutParams);
        }
    }
    public TextView findViewByPosition(int xPx, int yPx){
        for(int i = 0; i < AboutGame.chessNum; ++i){
            AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) AboutGame.chess[i].getLayoutParams();

            int viewxPx = layoutParams.x;
            int viewyPx = layoutParams.y;
            int widthPx =  layoutParams.width;
            int heightPx = layoutParams.height;

            if(xPx >= viewxPx && xPx <= viewxPx + widthPx && yPx >= viewyPx && yPx <= viewyPx + heightPx) {
                return AboutGame.chess[i];
            }
        }
        return null;
    }
    public void back(View view) {
        Intent intent = new Intent(this, MenuActivity.class);
        startActivityForResult(intent, TEXT_REQUEST);
    }
    public boolean win(){
        TextView cc = AboutGame.chess[5];
        AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) cc.getLayoutParams();

        int viewxPx = layoutParams.x;
        int viewyPx = layoutParams.y;
        int widthPx =  layoutParams.width;
        int heightPx = layoutParams.height;

        if(viewxPx == AboutGame.squareSize && viewyPx+heightPx == AboutGame.squareSize*AboutGame.yNum) {
//            soundPool.play(music[3], 1, 1, 0, 0, 1);
            return true;
        }
        return false;
    }
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String TAG = "MyGestureListener";

        public MyGestureListener() {

        }
        /**
         * 双击的第二下Touch down时触发
         *
         * @param e
         * @return
         */
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.i(TAG, "onDoubleTap : " + e.getAction());
            return super.onDoubleTap(e);
        }

        /**
         * 双击的第二下 down和up都会触发，可用e.getAction()区分。
         *
         * @param e
         * @return
         */
        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.i(TAG, "onDoubleTapEvent : " + e.getAction());
            return super.onDoubleTapEvent(e);
        }

        /**
         * down时触发
         *
         * @param e
         * @return
         */
        @Override
        public boolean onDown(MotionEvent e) {
            return super.onDown(e);
        }

        /**
         * Touch了滑动一点距离后，up时触发。
         *
         * @param e1
         * @param e2
         * @param velocityX
         * @param velocityY
         * @return
         */
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                               float velocityY) {
            TextView t=findViewByPosition((int)e1.getX(),(int)e1.getY()-AboutGame.squareSize);
            TextView stepCounter=findViewById(R.id.step);
            boolean judge=true;
            double down=e2.getY()-e1.getY();//默认下滑
            double right=e2.getX()-e1.getX();//默认右滑
            if(t!=null){
                if(Math.abs(down)>Math.abs(right))
                {
                    if(down>0)
                    {
                        judge=moveDown(t);
                    }
                    else
                    {
                        judge=moveUp(t);
                    }
                }
                else{
                    if(right>0)
                    {
                        judge=moveRight(t);
                    }
                    else{
                        judge=moveLeft(t);
                    }
                }
            }
            if(judge) {
                PlayActivity.steps++;
                stepCounter.setText("步数："+PlayActivity.steps);
            }
            if(win()) {
                AlertDialog.Builder builder=new AlertDialog.Builder(PlayActivity.this);
                builder.setTitle("胜利！");
                builder.setMessage("恭喜你，你赢了！");
                builder.setPositiveButton("我知道了",null);
                builder.show();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        private boolean moveLeft(TextView t) {

            AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) t.getLayoutParams();

            int myX = layoutParams.x;
            int myY = layoutParams.y;
            int myWidth =  layoutParams.width;
            int myHeight = layoutParams.height;

            for(int y = myY + AboutGame.squareSize/2; y < myY + myHeight; y += AboutGame.squareSize){
                if(findViewByPosition(myX - AboutGame.squareSize/2, y)!= null){
                    return false;
                }
            }
            if(myX - AboutGame.squareSize/2 < 0)
                return false;
            layoutParams.x = t.getLeft() - AboutGame.squareSize;
            t.setLayoutParams(layoutParams);

            return true;
        }

        private boolean moveRight(TextView t) {
            AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) t.getLayoutParams();

            int myX = layoutParams.x;
            int myY = layoutParams.y;
            int myWidth =  layoutParams.width;
            int myHeight = layoutParams.height;

            for(int y = myY + AboutGame.squareSize/2; y < myY + myHeight; y += AboutGame.squareSize){
                if( findViewByPosition(myX + myWidth + AboutGame.squareSize/2, y)!= null){
                    return false;
                }
            }
            if(myX + AboutGame.squareSize/2 + myWidth > AboutGame.squareSize * AboutGame.xNum)
                return false;
            layoutParams.x = t.getLeft() + AboutGame.squareSize;
            t.setLayoutParams(layoutParams);

            return true;
        }

        private boolean moveUp(TextView t) {
            AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) t.getLayoutParams();
            int myX = layoutParams.x;
            int myY = layoutParams.y;
            int myWidth =  layoutParams.width;
            int myHeight = layoutParams.height;

            for(int x = myX + AboutGame.squareSize/2; x < myX + myWidth; x += AboutGame.squareSize){
                if(findViewByPosition(x, myY - AboutGame.squareSize/2) != null)
                    return false;
            }
            if(myY - AboutGame.squareSize/2 < 0)
                return false;
            layoutParams.y = t.getTop() - AboutGame.squareSize;
            t.setLayoutParams(layoutParams);
            return true;
        }

        private boolean moveDown(TextView t) {
            AbsoluteLayout.LayoutParams layoutParams = (AbsoluteLayout.LayoutParams) t.getLayoutParams();

            int myX = layoutParams.x;
            int myY = layoutParams.y;
            int myWidth =  layoutParams.width;
            int myHeight = layoutParams.height;

            for(int x = myX + AboutGame.squareSize/2; x < myX + myWidth; x += AboutGame.squareSize){
                if(findViewByPosition(x, myY + AboutGame.squareSize/2 + myHeight)!= null){
                    return false;
                }
            }
            if(myY + myHeight +AboutGame.squareSize/2 > AboutGame.yNum * AboutGame.squareSize)
                return false;
            layoutParams.y = t.getTop() + AboutGame.squareSize;
            t.setLayoutParams(layoutParams);

            return true;
        }

        /**
         * Touch了不移动一直 down时触发
         *
         * @param e
         */
        @Override
        public void onLongPress(MotionEvent e) {
            super.onLongPress(e);
        }

        /**
         * Touch了滑动时触发。
         *
         * @param e1
         * @param e2
         * @param distanceX
         * @param distanceY
         * @return
         */
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                                float distanceY) {
            Log.i(TAG, "onScroll e1 : " + e1.getAction() + ", e2 : " + e2.getAction() + ", distanceX : " + distanceX + ", distanceY : " + distanceY);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        /**
         * Touch了还没有滑动时触发
         *
         * @param e
         */
        @Override
        public void onShowPress(MotionEvent e) {
            super.onShowPress(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return super.onSingleTapConfirmed(e);
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return super.onSingleTapUp(e);
        }
    }
}
