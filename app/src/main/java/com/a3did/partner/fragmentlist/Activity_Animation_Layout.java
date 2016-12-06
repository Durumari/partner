package com.a3did.partner.fragmentlist;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.a3did.partner.partner.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by edward on 12/4/2016.
 */

public class Activity_Animation_Layout extends View{

    Bitmap moominbm;
    Bitmap moomin_image[];
    int moomin_x, moomin_y;
    int moomin_h, moomin_w;
    int x_dir, y_dir;
    AssetManager assetManager;
    int iter =0;
    int gif_image_num;



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        //Moomin
        if (moominbm == null){
            //Log.d("test","nothing defined");
            moominbm = moomin_image[iter];

        }
        moominbm = moomin_image[iter%gif_image_num];



        BitmapFactory.Options option = new BitmapFactory.Options();
        BitmapFactory.decodeResource(getResources(),R.drawable.moomin_mid,option);
        moomin_h = option.outHeight;
        moomin_w = option.outWidth;

        //Log.d("measurement", "Width: "+canvas.getWidth()+" Height: "+canvas.getHeight());
        //Log.d("measurement", "Moomin Width: " + moomin_w+" Moomin Height:"+moomin_w);

        if (moomin_x >= canvas.getWidth()- moomin_w ){// moomin_x <= 0
            x_dir = (-1)*x_dir;
//            moominbm = BitmapFactory.decodeResource(getResources(), R.drawable.moomin_mid_reflect);
        }
        if ( moomin_x <= 0 ){
            x_dir = (-1)*x_dir;
//            moominbm = BitmapFactory.decodeResource(getResources(), R.drawable.moomin_mid);
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
            moomin_x = moomin_x + x_dir;
            moomin_y = moomin_y + y_dir;

        canvas.drawBitmap(moominbm,moomin_x,moomin_y,null);


        iter++;

        invalidate();


    }



    public Activity_Animation_Layout(Context context) {

        super(context);
        //setBackgroundResource(R.drawable.light_blue_background);
        gif_image_num = 14;
        //Bitmap moomin_image[] = new Bitmap[gif_image_num];
        moomin_image= new Bitmap[]{BitmapFactory.decodeResource(getResources(), R.drawable.moomin_01),
                BitmapFactory.decodeResource(getResources(), R.drawable.moomin_02),
                BitmapFactory.decodeResource(getResources(), R.drawable.moomin_03),
                BitmapFactory.decodeResource(getResources(), R.drawable.moomin_04),
                BitmapFactory.decodeResource(getResources(), R.drawable.moomin_05),
                BitmapFactory.decodeResource(getResources(), R.drawable.moomin_06),
                BitmapFactory.decodeResource(getResources(), R.drawable.moomin_07),
                BitmapFactory.decodeResource(getResources(), R.drawable.moomin_08),
                BitmapFactory.decodeResource(getResources(), R.drawable.moomin_09),
                BitmapFactory.decodeResource(getResources(), R.drawable.moomin_10),
                BitmapFactory.decodeResource(getResources(), R.drawable.moomin_11),
                BitmapFactory.decodeResource(getResources(), R.drawable.moomin_12),
                BitmapFactory.decodeResource(getResources(), R.drawable.moomin_13),
                BitmapFactory.decodeResource(getResources(), R.drawable.moomin_14),
        };


        moomin_x = 100;
        moomin_y =100;
        x_dir = 5;
        y_dir = 5;

    }

}
