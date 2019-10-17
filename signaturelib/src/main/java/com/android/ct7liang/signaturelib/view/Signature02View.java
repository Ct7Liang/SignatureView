package com.android.ct7liang.signaturelib.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.ct7liang.signaturelib.bean.Point;
import com.android.ct7liang.signaturelib.utils.ControlUtils;

import java.util.ArrayList;

/**
 * 加入贝塞尔曲线的画板
 */
public class Signature02View extends View {

    private Paint paint;
    private Path path;
    private ArrayList<Point> pointList = new ArrayList<>();

    public Signature02View(Context context) {
        super(context);
    }

    public Signature02View(Context context, AttributeSet attrs) {
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

    public Signature02View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                pointList.clear();
                break;

            case MotionEvent.ACTION_MOVE:
                //当手指滑动过程中对每一个点进行处理并绘制曲线
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    ControlUtils.drawPathByNewPoint(event.getHistoricalX(i), event.getHistoricalY(i), pointList, path);
                    invalidate();
                }
                break;

            case MotionEvent.ACTION_UP:

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