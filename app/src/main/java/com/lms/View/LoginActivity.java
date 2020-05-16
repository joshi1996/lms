package com.lms.View;


import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lms.GsonModel.LmsLogindata;
import com.lms.R;
import com.lms.WebService.Constant_Tag;
import com.lms.WebService.RestApi;
import com.lms.WebService.RestClient;
import com.lms.databinding.ActivityLoginsBinding;
import com.lms.utility.Connectivity;
import com.lms.utility.ProgressDialog;
import com.lms.utility.SharePrefs;
import com.lms.utility.ThemeClass;
import com.lms.utility.Utils;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private ActivityLoginsBinding activityBinding;

    public static LoginActivity activityobj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this,  R.layout.activity_logins);
        activityobj=this;

        if(getIntent().hasExtra("error")){
            Toasty.error(LoginActivity.this,getIntent().getStringExtra("error"),Toast.LENGTH_SHORT).show();

        }

        ThemeClass.changeLayoutColor(activityBinding.layouttop,LoginActivity.this);
        ThemeClass.changeButtonColor(activityBinding.btnLogin,LoginActivity.this);


        ThemeClass.setAdvertisment(activityBinding.ivAdverticement,LoginActivity.this,"Login Page", Utils.getDeviceWidth(LoginActivity.this),(int)getResources().getDimension(R.dimen.size_100));

        Glide.with(LoginActivity.this)
                .load(SharePrefs.getSetting(LoginActivity.this).getLogo())
                //.transform(new CircleTransform(..))
                .into(activityBinding.ivFree).onLoadFailed(getResources().getDrawable(R.drawable.placeholder));

        activityBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activityBinding.inMobileno.getText().toString().trim().length() == 0) {
                    Toasty.warning(LoginActivity.this, getString(R.string.blank_moblieno), Toast.LENGTH_SHORT).show();
                }
                else if (activityBinding.inMobileno.getText().toString().trim().length()<10) {
                    Toasty.warning(LoginActivity.this, getString(R.string.moblieno_invalid), Toast.LENGTH_SHORT).show();
                }
                else if (activityBinding.inpassword.getText().toString().trim().length() == 0) {
                    Toasty.warning(LoginActivity.this, getString(R.string.blank_password), Toast.LENGTH_SHORT).show();
                }

                else {

                    if (Connectivity.isConnected(LoginActivity.this)) {

                        CallLoginApi(LoginActivity.this);
                    } else {

                        //show net not connected error
                        Toasty.warning(LoginActivity.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });



        activityBinding.tvForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
            }
        });

        activityBinding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,SignupActivity.class));
            }
        });


    }


    private void CallLoginApi(final Context context) {
        String mobileno = activityBinding.inMobileno.getText().toString().trim();
        String password = activityBinding.inpassword.getText().toString().trim();

        ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();

        mparam.put(Constant_Tag.PHONE, mobileno);
        mparam.put(Constant_Tag.PASSWORD, password);


        Observable<LmsLogindata> mdata = mRestApi.getLogin(mparam);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LmsLogindata>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(LmsLogindata value) {
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {
                            Utils.hideKeyboard(LoginActivity.this);
                            Intent i=new Intent(LoginActivity.this,OtpActivity.class);
                            Bundle b=new Bundle();
                            b.putString("otp",value.getData().getOtp().toString());
                            b.putParcelable("userdata",value.getData().getUser());
                            i.putExtras(b);

                            startActivity(i);
                            LoginActivity.this.finish();
                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(LoginActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: "+e.getMessage());
                        ProgressDialog.hideDialog();
                        //   mDataListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });
    }


}

