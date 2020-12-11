package com.freepadhe.utility;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Looper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.freepadhe.GsonModel.Advertisment.Datum;
import com.freepadhe.R;
import com.squareup.picasso.Picasso;

import java.util.List;

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

    public static  ColorStateList getcolorstate(Context mcontext) {


        int[][] states = new int[][] {
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused},
                new int[]{android.R.attr.state_enabled}


        };

        int[] colors = new int[] {
                SharePrefs.getSetting(mcontext).getThemeColor(),
                SharePrefs.getSetting(mcontext).getThemeColor(),
                SharePrefs.getSetting(mcontext).getThemeColor()

        };

        ColorStateList myList = new ColorStateList(states, colors);
    return myList;
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


    public static  void changeNavigationButton(BottomNavigationView view, Context mcontext) {


        int[][] states = new int[][] {
                new int[]{android.R.attr.state_checked},
                new int[]{android.R.attr.state_enabled}


        };

        int[] colors = new int[] {
                SharePrefs.getSetting(mcontext).getThemeColor(),
                Color.parseColor(ColorTransparentUtils.transparentColor50(SharePrefs.getSetting(mcontext).getThemeColor()))

        };

        ColorStateList myList = new ColorStateList(states, colors);

        view.setItemIconTintList(myList);
        view.setItemTextColor(myList);

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
                                            Picasso.get().load(imagepath).resize(width,height).placeholder(R.drawable.card_view).into(view);

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
