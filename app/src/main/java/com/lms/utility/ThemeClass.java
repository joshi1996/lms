package com.lms.utility;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.lms.GsonModel.Advertisment.AdvertismentModel;
import com.lms.GsonModel.Advertisment.Datum;
import com.lms.R;
import com.lms.View.LoginActivity;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.logging.Handler;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class ThemeClass {

    public static  void changeHeaderColor(View view, Context mcontext) {
        view.setBackgroundColor(SharePrefs.getSetting(mcontext).getThemeColor());
    }

    public static  void changeLayoutColor(ViewGroup view, Context mcontext) {
        view.setBackgroundColor(SharePrefs.getSetting(mcontext).getThemeColor());
    }


    public static  int getThemecolor(Context mcontext) {
        return SharePrefs.getSetting(mcontext).getThemeColor();
    }

    public static  void changeButtonColor(AppCompatButton view, Context mcontext) {


        int[][] states = new int[][] {
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused},
                new int[]{android.R.attr.state_enabled}


        };

        int[] colors = new int[] {
                Color.parseColor(ColorTransparentUtils.transparentColor60(SharePrefs.getSetting(mcontext).getThemeColor())),
                SharePrefs.getSetting(mcontext).getThemeColor(),
                SharePrefs.getSetting(mcontext).getThemeColor()

        };

        ColorStateList myList = new ColorStateList(states, colors);
        view.setBackgroundTintList(myList);
    }


    public static  boolean setAdvertisment(final ImageView view,final  Context mcontext,String pagename,final int width,final int height) {

           List<Datum> mAdvertismentModel= SharePrefs.getAdvertisment(mcontext).getData();

        for(int i=0;i<mAdvertismentModel.size();i++)
        {
            if(mAdvertismentModel.get(i).getTitle().equalsIgnoreCase(pagename))
            {
                boolean isload=false;
                if(mAdvertismentModel.get(i).getStatus()==1)
                {
                    isload=true;
                final  String imagepath=mAdvertismentModel.get(i).getImage();
                   new Thread(new Runnable() {
                       @Override
                       public void run() {
                                    new android.os.Handler(Looper.getMainLooper()).post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Picasso.get().load(imagepath).resize(width,height).placeholder(R.drawable.placeholder).into(view);

                                        }
                                    });


                       }
                   }).start();

                    final  String url=mAdvertismentModel.get(i).getURL();
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            mcontext.startActivity(i);
                        }
                    });
                    if(!pagename.equalsIgnoreCase("Class Room Page - No Courses"))
                    view.setVisibility(View.VISIBLE);

                }else{
                    isload=false;

                    view.setVisibility(View.GONE);

                }
                return isload;
            }

        }
         return false;

    }



}
