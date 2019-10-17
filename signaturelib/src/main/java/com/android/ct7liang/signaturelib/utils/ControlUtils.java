package com.android.ct7liang.signaturelib.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.android.ct7liang.signaturelib.bean.Point;

import java.util.ArrayList;

public class ControlUtils {

    /**
     * 根据已知的点的集合计算出所有的控制点
     * @param points 已知的点的集合
     * @param controls 控制点的集合
     */
    public static void getControlsByPoints(ArrayList<Point> points, ArrayList<Point> controls){
        if (points.size()<3){
            return;
        }
        for (int i = 0; i < points.size() - 2; i++) {
            Point p1 = points.get(i);
            Point p2 = points.get(i+1);
            Point p3 = points.get(i+2);

            Point p12 = new Point((p1.x+p2.x)/2, (p1.y+p2.y)/2);
            Point p23 = new Point((p2.x+p3.x)/2, (p2.y+p3.y)/2);

            float dy = (p12.y+p23.y)/2 - p2.y;

            Point c1 = new Point((p1.x+p2.x)/2, (p1.y+p2.y)/2-dy);
            Point c2 = new Point((p2.x+p3.x)/2, (p2.y+p3.y)/2-dy);

            controls.add(c1);
            controls.add(c2);
        }
    }



    public static void drawPathByNewPoint(float x, float y, ArrayList<Point> list, Canvas canvas, Paint paint){
        //此处path为局部变量,且每次都重新生成,保证每次只将新的路径绘制进bitmap中
        Path path = new Path();
        //将新增的节点加入集合中
        list.add(new Point(x, y));
        //计算控制点,更新控制点数组
        countControls(list);

        int size = list.size();
        if (size == 3){
            //当集合的长度为3的时候,画二阶曲线
            path.moveTo(list.get(0).x, list.get(0).y);
            path.quadTo(controls[2].x, controls[2].y, list.get(1).x, list.get(1).y);
            //将曲线画入到传进来的Canvas中,也就是内存中的Bitmap中
            canvas.drawPath(path, paint);
        }else if (size > 3){
            //当集合的长度大于3的时候,画三阶曲线
            path.moveTo(list.get(size-3).x, list.get(size-3).y);
            path.cubicTo(
                    controls[1].x, controls[1].y,
                    controls[2].x, controls[2].y,
                    list.get(size-2).x, list.get(size-2).y
            );
            //将曲线画入到传进来的Canvas中,也就是内存中的Bitmap中
            canvas.drawPath(path, paint);
        }
    }

    /**
     * 每次向节点集合加入一条数据,计算出控制点,然后画一条曲线
     * @param x 新加入点的横坐标
     * @param y 新加入点的纵坐标
     * @param list 节点集合
     * @param path Path对象
     */
    public static void drawPathByNewPoint(float x, float y, ArrayList<Point> list, Path path){
        //将新增的节点加入集合中
        list.add(new Point(x, y));
        //计算控制点,更新控制点数组
        countControls(list);

        int size = list.size();
        if (size == 3){
            //当集合的长度为3的时候,画二阶曲线
            path.moveTo(list.get(0).x, list.get(0).y);
            path.quadTo(controls[2].x, controls[2].y, list.get(1).x, list.get(1).y);
        }else if (size > 3){
            //当集合的长度大于3的时候,画三阶曲线
            path.moveTo(list.get(size-3).x, list.get(size-3).y);
            path.cubicTo(
                    controls[1].x, controls[1].y,
                    controls[2].x, controls[2].y,
                    list.get(size-2).x, list.get(size-2).y
            );
        }
    }

    /**
     * 长度为4的控制点数组,只存储每次最后计算出来的四个控制点
     */
    private static Point[] controls = new Point[4];

    /**
     * 当节点的集合每增加一条数据之后,计算出控制点,更新控制点的数组
     * @param list
     */
    private static void countControls(ArrayList<Point> list){
        int size = list.size();
        if (size < 3){
            return;
        }

        //更新数组中的数据,后两位前移至前两位
        controls[0] = controls[2];
        controls[1] = controls[3];

        Point p1 = list.get(size-3);
        Point p2 = list.get(size-2);
        Point p3 = list.get(size-1);
        Point p12 = new Point((p1.x+p2.x)/2, (p1.y+p2.y)/2);
        Point p23 = new Point((p2.x+p3.x)/2, (p2.y+p3.y)/2);
        float dy = (p12.y+p23.y)/2 - p2.y;
        Point c1 = new Point((p1.x+p2.x)/2, (p1.y+p2.y)/2-dy);
        Point c2 = new Point((p2.x+p3.x)/2, (p2.y+p3.y)/2-dy);

        //将计算得到的两个控制点依次放入后两位
        controls[2] = c1;
        controls[3] = c2;
    }

}