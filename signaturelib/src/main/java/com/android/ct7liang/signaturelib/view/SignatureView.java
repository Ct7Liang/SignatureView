package com.android.ct7liang.signaturelib.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.ct7liang.signaturelib.bean.Point;
import com.android.ct7liang.signaturelib.utils.ControlUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 加入贝塞尔曲线的画板(添加笔画粗细)(目前最完整版)
 */
public class SignatureView extends View {

    private Paint paint;
    private ArrayList<Point> pointList = new ArrayList<>();

    /**
     * 声明一个Bitmap和Canvas,Canvas用于操作Bitmap,对Bitmap进行绘制
     */
    private Bitmap bitmap;
    private Canvas bitmapCanvas;

    /**
     * 声明横纵坐标变量用于计算手指滑动速率
     */
    private float lastX;
    private float lastY;

    public SignatureView(Context context) {
        super(context);
    }

    public SignatureView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(7);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }

    public SignatureView(Context context, AttributeSet attrs, int defStyleAttr) {
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

                if (lastX!=0&&lastY!=0){
                    double powX = Math.pow(lastX - event.getX(), 2);
                    double powY = Math.pow(lastY - event.getY(), 2);
                    double sqrt = Math.sqrt(powX + powY);

                    if (sqrt>100){
                        paint.setStrokeWidth(5);
                    }else if (sqrt>95){
                        paint.setStrokeWidth(5.5f);
                    }else if (sqrt>90){
                        paint.setStrokeWidth(6);
                    }else if (sqrt>85){
                        paint.setStrokeWidth(6.5f);
                    }else if (sqrt>80){
                        paint.setStrokeWidth(7);
                    }else if (sqrt>75){
                        paint.setStrokeWidth(7.5f);
                    }else if (sqrt>70){
                        paint.setStrokeWidth(8);
                    }else if (sqrt>65){
                        paint.setStrokeWidth(8.5f);
                    }else {
                        paint.setStrokeWidth(9);
                    }
                }
                lastX = event.getX();
                lastY = event.getY();

                //当手指滑动过程中对每一个点进行处理并绘制曲线
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    //替换新的方法
                    ControlUtils.drawPathByNewPoint(event.getHistoricalX(i), event.getHistoricalY(i), pointList, bitmapCanvas, paint);
                    invalidate();
                }

                break;

            case MotionEvent.ACTION_UP:
                //手指抬起后归零
                lastX = 0;
                lastY = 0;
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

    /**
     * 保存到本地
     */
    public void save(){
        File file=new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis()+".jpg");//将要保存图片的路径
        try {
            bitmapCanvas.save();
            bitmapCanvas.restore();

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Toast.makeText(getContext(), "图片保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存到本地
     */
    public void save(File file){
        try {
            bitmapCanvas.save();
            bitmapCanvas.restore();

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            Toast.makeText(getContext(), "图片保存成功", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}