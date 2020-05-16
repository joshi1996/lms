package com.lms.View;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.lms.R;
import com.lms.databinding.ActivityForgotpasswordBinding;
import com.lms.databinding.ActivityIntroBinding;
import com.lms.databinding.ActivitySplashBinding;


/**
 * A login screen that offers login via email/password.
 */
public class IntroActivity extends AppCompatActivity {
    private static final String TAG = IntroActivity.class.getSimpleName();

    private ActivityIntroBinding activityBinding;
   public static  IntroActivity activityobj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
         activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_intro);


        activityobj=this;

         activityBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(IntroActivity.this,LoginActivity.class));

             }
         });



        activityBinding.btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(IntroActivity.this,SignupActivity.class));
            }
        });

        activityBinding.btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}

