package com.android.ct7liang.signaturelib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 初步实现的画板View
 */
public class Signature01View extends View {

    private Paint paint;
    private Path path;

    public Signature01View(Context context) {
        super(context);
    }

    public Signature01View(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        path = new Path();
    }

    public Signature01View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //当手指按下的时候,执行moveTo方法,首先绘制线的起点
                path.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                //当手指滑动的时候,不断对经过的点进行线的绘制
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    path.lineTo(event.getHistoricalX(i), event.getHistoricalY(i));
                    //重绘
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
                //手指抬起时候的点暂不做处理(不影响使用)
                break;
        }
        return true;
    }

    /**
     * 清理画板
     */
    public void clear(){
        //重置路径(清空)
        path.reset();
        //重绘界面
        invalidate();
    }

}