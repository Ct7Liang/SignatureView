package com.android.ct7liang.signaturelib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.android.ct7liang.signaturelib.bean.Point;
import com.android.ct7liang.signaturelib.utils.ControlUtils;

import java.util.ArrayList;

/**
 * 加入贝塞尔曲线的画板(bitmap优化)
 */
public class Signature03View extends View {

    private Paint paint;
    private ArrayList<Point> pointList = new ArrayList<>();

    /**
     * 声明一个Bitmap和Canvas,Canvas用于操作Bitmap,对Bitmap进行绘制
     */
    private Bitmap bitmap;
    private Canvas bitmapCanvas;

    public Signature03View(Context context) {
        super(context);
    }

    public Signature03View(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    public Signature03View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //在onDraw()方法中进行Bitmap的判空并创建
        if (bitmap == null){
            bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            bitmapCanvas = new Canvas(bitmap);
            bitmapCanvas.drawColor(Color.WHITE);
        }
        //view控件自己的canvas直接画bitmap即可
        canvas.drawBitmap(bitmap, 0, 0, paint);
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
                    //替换新的方法
                    ControlUtils.drawPathByNewPoint(event.getHistoricalX(i), event.getHistoricalY(i), pointList, bitmapCanvas, paint);
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
        bitmap = null;
        //重绘界面
        invalidate();
    }

}