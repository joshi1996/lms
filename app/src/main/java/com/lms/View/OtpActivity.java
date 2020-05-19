package com.lms.View;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lms.GsonModel.ForgetPassword.ForgotPassword;
import com.lms.GsonModel.ForgetPassword.User;
import com.lms.GsonModel.lms_OtpVerify;
import com.lms.R;
import com.lms.WebService.Constant_Tag;
import com.lms.WebService.RestApi;
import com.lms.WebService.RestClient;
import com.lms.databinding.ActivityOtpBinding;
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

public class OtpActivity extends AppCompatActivity {
    private static final String TAG = OtpActivity.class.getSimpleName();
    private ActivityOtpBinding activityBinding;
     String  otp;
    User userdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
        Utils.changeStatuscolor(OtpActivity.this);

        ThemeClass.changeLayoutColor(activityBinding.layouttop,OtpActivity.this);
        ThemeClass.changeButtonColor(activityBinding.btnSubmit,OtpActivity.this);

        ThemeClass.setAdvertisment(activityBinding.ivAdverticement,OtpActivity.this,"OTP Verify", Utils.getDeviceWidth(OtpActivity.this),(int)getResources().getDimension(R.dimen.size_100));


        Glide.with(OtpActivity.this)
                .load(SharePrefs.getSetting(OtpActivity.this).getLogo())
                //.transform(new CircleTransform(..))
                .into(activityBinding.ivFree).onLoadFailed(getResources().getDrawable(R.drawable.placeholder));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activityBinding.inpincode.setCompoundDrawableTintList(ThemeClass.getcolorstate(OtpActivity.this));


        }

        if(getIntent()!=null){
            Bundle b=getIntent().getExtras();

            if(b!=null){

            otp= b.getString("otp");
            userdata=b.getParcelable("userdata");
             activityBinding.inpincode.setText(otp);
        }
        }



        activityBinding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OtpActivity.this,SignupActivity.class));
                OtpActivity.this.finish();
            }
        });


        activityBinding.tvResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Connectivity.isConnected(OtpActivity.this)) {

                    CallResendOTpApi(OtpActivity.this);
                } else {

                    //show net not connected error
                    Toasty.warning(OtpActivity.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

                }

            }
        });

        activityBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(activityBinding.inpincode.getText().toString().length()==0){
                    Toasty.warning(OtpActivity.this,getString(R.string.blank_otp), Toast.LENGTH_SHORT).show();
                }
                else{

                    if (Connectivity.isConnected(OtpActivity.this)) {

                        CallVerifyOTPApi(OtpActivity.this);
                    } else {

                        //show net not connected error
                        Toasty.warning(OtpActivity.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

                    }




                }


            }
        });


    }

    private void CallVerifyOTPApi(OtpActivity context) {

        ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();
        mparam.put(Constant_Tag.OTP, activityBinding.inpincode.getText().toString());
        mparam.put(Constant_Tag.PHONE, userdata.getPhoneNo().toString());

        Observable<lms_OtpVerify> mdata = mRestApi.verifyOtp(mparam);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<lms_OtpVerify>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(lms_OtpVerify value) {
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {
                            //no mobile number entered
                              Toasty.success(OtpActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();

                              SharePrefs.saveUserDetail(OtpActivity.this,value.getData());
                              startActivity(new Intent(OtpActivity.this,MainActivity.class));

                              if(SignupActivity.activity!=null)
                              SignupActivity.activity.finish();


                              OtpActivity.this.finish();
                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(OtpActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: ");
                        ProgressDialog.hideDialog();
                        //   mDataListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });

    }


    private void CallResendOTpApi(final OtpActivity context) {
        ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();
        mparam.put(Constant_Tag.PHONE, userdata.getPhoneNo().toString());

        Observable<ForgotPassword> mdata = mRestApi.resendOtp(mparam);
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
                                //no mobile number entered
                                Toasty.success(OtpActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                                activityBinding.inpincode.setText(value.getData().getOtp());
                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(OtpActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: ");
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

