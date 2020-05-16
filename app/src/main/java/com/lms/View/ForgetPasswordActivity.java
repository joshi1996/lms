package com.lms.View;


import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lms.GsonModel.BasicModel;
import com.lms.GsonModel.ForgetPassword.ForgotPassword;
import com.lms.R;
import com.lms.WebService.Constant_Tag;
import com.lms.WebService.RestApi;
import com.lms.WebService.RestClient;
import com.lms.databinding.ActivityForgotpasswordBinding;
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


public class ForgetPasswordActivity extends AppCompatActivity {
    private static final String TAG = ForgetPasswordActivity.class.getSimpleName();
    private ActivityForgotpasswordBinding activityBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBinding = DataBindingUtil.setContentView(this,  R.layout.activity_forgotpassword);

        ThemeClass.changeLayoutColor(activityBinding.layouttop,ForgetPasswordActivity.this);
        ThemeClass.changeButtonColor(activityBinding.btnResetpassword,ForgetPasswordActivity.this);


        ThemeClass.setAdvertisment(activityBinding.ivAdverticement,ForgetPasswordActivity.this,"Forgot Passwords", Utils.getDeviceWidth(ForgetPasswordActivity.this),(int)getResources().getDimension(R.dimen.size_100));

        Glide.with(ForgetPasswordActivity.this)
                .load(SharePrefs.getSetting(ForgetPasswordActivity.this).getLogo())
                //.transform(new CircleTransform(..))
                .into(activityBinding.ivFree).onLoadFailed(getResources().getDrawable(R.drawable.placeholder));


        activityBinding.tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForgetPasswordActivity.this.finish();
            }
        });

        activityBinding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ForgetPasswordActivity.this,SignupActivity.class));
                ForgetPasswordActivity.this.finish();
            }
        });


        activityBinding.btnResetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityBinding.inmobileno.getText().toString().trim().length() == 0) {
                    Toasty.warning(ForgetPasswordActivity.this, getString(R.string.blank_moblieno), Toast.LENGTH_SHORT).show();
                }
                else if (activityBinding.inmobileno.getText().toString().trim().length()<10) {
                    Toasty.warning(ForgetPasswordActivity.this, getString(R.string.moblieno_invalid), Toast.LENGTH_SHORT).show();
                }
                else {

                    if (Connectivity.isConnected(ForgetPasswordActivity.this)) {

                        CallForgetPasswordApi(ForgetPasswordActivity.this);
                    } else {

                        //show net not connected error
                        Toasty.warning(ForgetPasswordActivity.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        ForgetPasswordActivity.this.finish();
    }

    private void CallForgetPasswordApi(final Context context) {
        String mobileno = activityBinding.inmobileno.getText().toString().trim();
        ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();
        mparam.put(Constant_Tag.PHONE, mobileno);
        Observable<ForgotPassword> mdata = mRestApi.forgotPassword(mparam);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ForgotPassword>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }
                    @Override
                    public void onNext(ForgotPassword value) {
                        ProgressDialog.hideDialog();

                        if (value != null && value.getStatus() == 200) {
                            //Toasty.success(ForgetPasswordActivity.this, value.getMessage(), Toast.LENGTH_LONG).show();
                            Utils.hideKeyboard(ForgetPasswordActivity.this);

                            Intent i=new Intent(ForgetPasswordActivity.this,OtpActivity.class);
                            Bundle b=new Bundle();
                            b.putString("otp",value.getData().getOtp().toString());
                            b.putParcelable("userdata",value.getData().getUser());
                            i.putExtras(b);

                            startActivity(i);
                            if(LoginActivity.activityobj!=null){
                                LoginActivity.activityobj.finish();
                            }
                            ForgetPasswordActivity.this.finish();
                        } else if (value != null) {
                            Toasty.error(ForgetPasswordActivity.this, value.getMessage(), Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: ");
                        ProgressDialog.hideDialog();
                    }
                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });
    }
}

