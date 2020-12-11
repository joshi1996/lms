package com.freepadhe.View;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.freepadhe.GsonModel.Advertisment.AdvertismentModel;
import com.freepadhe.GsonModel.ForgetPassword.ForgotPassword;
import com.freepadhe.GsonModel.ForgetPassword.User;
import com.freepadhe.GsonModel.lms_OtpVerify;
import com.freepadhe.R;
import com.freepadhe.WebService.Constant_Tag;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.ActivityOtpBinding;
import com.freepadhe.utility.Connectivity;
import com.freepadhe.utility.ProgressDialog;
import com.freepadhe.utility.SharePrefs;
import com.freepadhe.utility.ThemeClass;
import com.freepadhe.utility.Utils;
import com.squareup.picasso.Picasso;

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
     String  otp,otpStringUrl;
    User userdata;

    CountDownTimer mCountDownTimer = new CountDownTimer(30 * 1000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            //this will be called every second.
            activityBinding.tvTime.setText("00:"+millisUntilFinished/1000);
        }

        @Override
        public void onFinish() {
            activityBinding.tvResend.setVisibility(View.VISIBLE);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_otp);
        Utils.changeStatuscolor(OtpActivity.this);

        mCountDownTimer.start();

        ThemeClass.changeButtonColor(activityBinding.btnSubmit,OtpActivity.this);


        advertisement();

        activityBinding.ivAdverticement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent google=new Intent(Intent.ACTION_VIEW);
                google.setData(Uri.parse(otpStringUrl));
                startActivity(google);
            }
        });

        Glide.with(OtpActivity.this)
                .load(SharePrefs.getSetting(OtpActivity.this).getLogo())
                //.transform(new CircleTransform(..))
                .into(activityBinding.ivFree).onLoadFailed(getResources().getDrawable(R.drawable.placeholder_banner));


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
                    mCountDownTimer.start();
                    activityBinding.tvResend.setVisibility(View.GONE);
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
        mparam.put(Constant_Tag.FIREBASEKEY, "XYZ");
        mparam.put(Constant_Tag.DEVICEID, "ANDROID");

        Observable<lms_OtpVerify> mdata = mRestApi.verifyOtp(mparam);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<lms_OtpVerify>() {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(lms_OtpVerify value) {
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200)
                        {
                              SharePrefs.saveUserDetail(OtpActivity.this,value.getData());

                              if (value.getData().getUser().getCity().equals(""))
                              {
                                  startActivity(new Intent(OtpActivity.this,CompleteProfile.class));
                                  OtpActivity.this.finish();
                              }
                              else
                              {
                                  startActivity(new Intent(OtpActivity.this,MainActivity.class));
                                  OtpActivity.this.finish();
                              }
                            /*if(SignupActivity.activity!=null)
                                SignupActivity.activity.finish();
                            OtpActivity.this.finish();*/
                        }
                        else if (value != null)
                        {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(OtpActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void advertisement()
    {
        RestApi mRestApi = RestClient.getClient(OtpActivity.this).create(RestApi.class);

        Observable<AdvertismentModel> mdata = mRestApi.advertisement();
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AdvertismentModel>() {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(AdvertismentModel value) {

                        if (value != null && value.getStatus() == 200)
                        {

                            if (value.getData().get(2).getId() == "5f9d2ca8f701c65e7f707f14" || value.getData().get(2).getStatus() == 1)
                            {
                                otpStringUrl = value.getData().get(2).getURL();
                                Picasso.get().load(value.getData().get(2).getImage()).into(activityBinding.ivAdverticement);
                            }
                            else
                            {
                                Toast.makeText(OtpActivity.this, "No Such Advertisement", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (value != null)
                        {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(OtpActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: "+e.getMessage());
                        Toast.makeText(OtpActivity.this, ""+e, Toast.LENGTH_SHORT).show();

                        //   mDataListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });
    }

}

