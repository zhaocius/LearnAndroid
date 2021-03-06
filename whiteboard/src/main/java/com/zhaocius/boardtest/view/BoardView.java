package com.zhaocius.boardtest.view;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.zhaocius.boardtest.myinterface.BoardViewPresenter;


/**
 * Created by zhaozi on 2017/5/27.
 */

public class BoardView extends View implements BoardViewPresenter.BoardViewPresenterUI {
    private static final String TAG = "BoardView";
    private int view_width = 0;//屏幕的宽度
    private int view_height = 0;//屏幕的高度
    private float preX;//起始点的x坐标
    private float preY;//起始点的y坐标
    private Path path;//路径0
    private Paint paint;//画笔0
    Bitmap cacheBitmap = null;//定义一个内存中的图片，该图片将作为缓冲区
    Canvas cacheCanvas = null;//定义cacheBitmap上的Canvas对象
    private BoardViewPresenter.BoardViewPresenterUICallback mCallback;

    public BoardView(Context context){
        this(context,null);
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view_width = context.getResources().getDisplayMetrics().widthPixels;//获取屏幕宽度
        view_height = context.getResources().getDisplayMetrics().heightPixels;//获取屏幕高度
        //创建一个与该View相同大小的缓存区
        cacheBitmap = Bitmap.createBitmap(view_width, view_height, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas(cacheBitmap);//创建一个新的画布, 在cacheCanvas上绘制的都保存在cacheBitmap

        path = new Path();
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);//设置填充方式为描边
        paint.setStrokeJoin(Paint.Join.ROUND);//设置笔刷转弯处的连接风格
        paint.setStrokeCap(Paint.Cap.ROUND);//设置笔刷的图形样式(体现在线的端点上)
        paint.setStrokeWidth(1);//设置默认笔触的宽度为1像素
        paint.setAntiAlias(true);//设置抗锯齿效果
        paint.setDither(true);//使用抖动效果
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xFFFFFFFF);//设置背景色
        canvas.drawBitmap(cacheBitmap, 0, 0, paint);//将cachebitmap一次性绘制在该view上面,
        canvas.drawPath(path, paint);//绘制路径，不写无法显示及时路径
        canvas.save();//保存canvas的状态
        //恢复canvas之前保存的状态，防止保存后对canvas执行的操作对后续的绘制有影响
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getPointerCount() > 2) {
            event.getToolType(1);
            return true;
        }
        float x = event.getX(0);
        float y = event.getY(0);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                //将绘图的起始点移到(x,y)坐标点的位置
                path.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:

                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);
                if (dx > 5 || dy > 5) {
                    //.quadTo贝塞尔曲线，实现平滑曲线(对比lineTo)
                    path.quadTo(preX, preY, (x + preX) / 2, (y + preY) / 2);
                    preX = x;
                    preY = y;
                }
                break;

            case MotionEvent.ACTION_UP:
                cacheCanvas.drawPath(path, paint);//绘制路径
                path.reset();
                break;
        }
        // 所以任何时候当你想擦除并绘制窗口的时候，就可以在别的函数中完成功能代码之后Invalidat()一下。OnDraw马上就会被调用了。
        invalidate();
        return true;//返回true,表明处理方法已经处理该事件
    }

    @Override
    public void setCallback(BoardViewPresenter.BoardViewPresenterUICallback callback) {
        mCallback = callback;
    }

    @Override
    public int getID() {
        return 2;
    }
}
