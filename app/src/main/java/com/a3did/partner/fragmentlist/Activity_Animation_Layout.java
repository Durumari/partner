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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.a3did.partner.partner.InteractionManager;
import com.a3did.partner.partner.MainActivity;
import com.a3did.partner.partner.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

/**
 * Created by edward on 12/4/2016.
 */

public class Activity_Animation_Layout extends View implements View.OnTouchListener{

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
        setOnTouchListener(this);
    }



    public boolean pointInRect(int x, int y){
        if(moomin_x <= x && moomin_x + moomin_w >= x && moomin_y <= y && moomin_y + moomin_h >= y){
            //clicked moomin image
            Log.d("test", "touch moomin");
            InteractionManager manager = InteractionManager.getInstance();
            Random random = new Random();
            if(!manager.ttsClient.isPlaying()){
                random.setSeed(System.currentTimeMillis());
                int data = random.nextInt(4);
                switch (data){
                    case 0:
                        manager.ttsClient.play("일정 리스트를 보고 싶으시면, 파트너, 일정 리스트 좀 보여줘, 라고 말해볼래요?");
                        break;
                    case 1:
                        manager.ttsClient.play("목표 리스트를 보고 싶으시면, 파트너, 목표 리스트 좀 보여줘, 라고 말해볼래요?");
                        break;
                    case 2:
                        manager.ttsClient.play("보상 리스트를 보고 싶으시면, 파트너, 보상 리스트 좀 보여줘, 라고 말해볼래요?");
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                }
            }

            return true;
        }
        return false;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

            return pointInRect((int)motionEvent.getX(), (int)motionEvent.getY());
        }
        return false;
    }
}
