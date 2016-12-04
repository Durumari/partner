package com.a3did.partner.fragmentlist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.a3did.partner.partner.R;

/**
 * Created by edward on 12/4/2016.
 */

public class Activity_Animation_Layout extends View{
    Paint red_paintbrush_fill, green_paintbrush_fill, blue_paintbrush_fill ;
    Paint red_paintbrush_stroke, green_paintbrush_stroke, blue_paintbrush_stroke;
    Bitmap moominbm;
    int moomin_x, moomin_y;
    int moomin_h, moomin_w;
    int x_dir, y_dir;



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        red_paintbrush_fill = new Paint();
        red_paintbrush_fill.setColor(Color.RED);
        red_paintbrush_fill.setStyle(Paint.Style.FILL);

        green_paintbrush_fill = new Paint();
        green_paintbrush_fill.setColor(Color.GREEN);
        green_paintbrush_fill.setStyle(Paint.Style.FILL);

        blue_paintbrush_fill = new Paint();
        blue_paintbrush_fill.setColor(Color.BLUE);
        blue_paintbrush_fill.setStyle(Paint.Style.FILL);

        red_paintbrush_stroke = new Paint();
        red_paintbrush_stroke.setColor(Color.RED);
        red_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        red_paintbrush_stroke.setStrokeWidth(10);

        green_paintbrush_stroke = new Paint();
        green_paintbrush_stroke.setColor(Color.GREEN);
        green_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        green_paintbrush_stroke.setStrokeWidth(10);

        blue_paintbrush_stroke = new Paint();
        blue_paintbrush_stroke.setColor(Color.BLUE);
        blue_paintbrush_stroke.setStyle(Paint.Style.STROKE);
        blue_paintbrush_stroke.setStrokeWidth(10);

//        Rect rectangle = new Rect();
//        rectangle.set(210,125,250,175);
//        canvas.drawRect(rectangle, green_paintbrush_stroke);

        //Moomin
        if (moominbm == null){
            //Log.d("test","nothing defined");
            moominbm = BitmapFactory.decodeResource(getResources(), R.drawable.moomin_mid);

        }


        BitmapFactory.Options option = new BitmapFactory.Options();
        BitmapFactory.decodeResource(getResources(),R.drawable.moomin_mid,option);
        moomin_h = option.outHeight;
        moomin_w = option.outWidth;

        //Log.d("measurement", "Width: "+canvas.getWidth()+" Height: "+canvas.getHeight());
        //Log.d("measurement", "Moomin Width: " + moomin_w+" Moomin Height:"+moomin_w);

        if (moomin_x >= canvas.getWidth()- moomin_w ){// moomin_x <= 0
            x_dir = (-1)*x_dir;
            moominbm = BitmapFactory.decodeResource(getResources(), R.drawable.moomin_mid_reflect);
        }
        if ( moomin_x <= 0 ){
            x_dir = (-1)*x_dir;
            moominbm = BitmapFactory.decodeResource(getResources(), R.drawable.moomin_mid);
        }

        if(moomin_y >= canvas.getHeight() -moomin_h || moomin_y <=0){
            y_dir = (-1)*y_dir;
        }

        // TODO: need editing for beacon nearby
//        if ( /*beacon is nearby*/){
//            moomin_x = moomin_x + x_dir;
//            moomin_y = moomin_y + y_dir;
//        }
//        else /*beacon is not nearby*/{
//            moomin_x = canvas.getWidth()/2 - moomin_w/2;
//            moomin_y = canvas.getHeight()/2 - moomin_h/2 ;
//            moominbm = BitmapFactory.decodeResource(getResources(), R.drawable.moomin_front);
//        }


        canvas.drawBitmap(moominbm,moomin_x,moomin_y,null);

        invalidate();

    }

    public Activity_Animation_Layout(Context context) {

        super(context);
        setBackgroundResource(R.drawable.light_blue_background);

        moomin_x = 100;
        moomin_y =100;
        x_dir = 5;
        y_dir = 5;

    }

}
