package com.freepadhe.View;


import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.freepadhe.GsonModel.Advertisment.AdvertismentModel;
import com.freepadhe.GsonModel.LmsLogindata;
import com.freepadhe.R;
import com.freepadhe.WebService.Constant_Tag;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.ActivityLoginsBinding;
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


public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    private ActivityLoginsBinding activityBinding;

    public static LoginActivity activityobj;
    String loginUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.changeStatuscolor(LoginActivity.this);

        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_logins);
        activityobj = this;

        advertisement();


        activityBinding.checkBoxVisibleLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int start,end;
                Log.i("inside checkbox chnge",""+b);
                if(!b){
                    start=activityBinding.inpassword.getSelectionStart();
                    end=activityBinding.inpassword.getSelectionEnd();
                    activityBinding.inpassword.setTransformationMethod(new PasswordTransformationMethod());;
                    activityBinding.inpassword.setSelection(start,end);
                }else{
                    start=activityBinding.inpassword.getSelectionStart();
                    end=activityBinding.inpassword.getSelectionEnd();
                    activityBinding.inpassword.setTransformationMethod(null);
                    activityBinding.inpassword.setSelection(start,end);
                }
            }
        });


        activityBinding.ivAdverticement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent google=new Intent(Intent.ACTION_VIEW);
                google.setData(Uri.parse(loginUrl));
                startActivity(google);
            }
        });

        if (getIntent().hasExtra("error")) {
            Toasty.error(LoginActivity.this, getIntent().getStringExtra("error"), Toast.LENGTH_SHORT).show();

        }
        ThemeClass.changeButtonColor(activityBinding.btnLogin, LoginActivity.this);

        Glide.with(LoginActivity.this)
                .load(SharePrefs.getSetting(LoginActivity.this).getLogo())
                //.transform(new CircleTransform(..))
                .into(activityBinding.ivFree).onLoadFailed(getResources().getDrawable(R.drawable.placeholder_banner));


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activityBinding.inMobileno.setCompoundDrawableTintList(ThemeClass.getcolorstate(LoginActivity.this));
            activityBinding.inpassword.setCompoundDrawableTintList(ThemeClass.getcolorstate(LoginActivity.this));

        }

        activityBinding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activityBinding.inMobileno.getText().toString().trim().length() == 0) {
                    Toasty.warning(LoginActivity.this, getString(R.string.blank_moblieno), Toast.LENGTH_SHORT).show();
                } else if (activityBinding.inMobileno.getText().toString().trim().length() < 10) {
                    Toasty.warning(LoginActivity.this, getString(R.string.moblieno_invalid), Toast.LENGTH_SHORT).show();
                } else if (activityBinding.inpassword.getText().toString().trim().length() == 0) {
                    Toasty.warning(LoginActivity.this, getString(R.string.blank_password), Toast.LENGTH_SHORT).show();
                } else {

                    if (Connectivity.isConnected(LoginActivity.this)) {

                        CallLoginApi(LoginActivity.this);

                    }
                    else {

                        //show net not connected error
                        Toasty.warning(LoginActivity.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });


        activityBinding.tvForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
            }
        });

        activityBinding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
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
        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        mparam.put("deviceid", androidId);

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
                            Intent i = new Intent(LoginActivity.this, OtpActivity.class);
                            Bundle b = new Bundle();
                            b.putString("otp", value.getData().getOtp().toString());
                            b.putParcelable("userdata", value.getData().getUser());
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
                        Log.e(TAG, "onError: " + e.getMessage());
                        ProgressDialog.hideDialog();
                        //   mDataListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });
    }

    public void advertisement() {

        RestApi mRestApi = RestClient.getClient(LoginActivity.this).create(RestApi.class);

        Observable<AdvertismentModel> mdata = mRestApi.advertisement();
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AdvertismentModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(AdvertismentModel value) {

                        if (value != null && value.getStatus() == 200) {

                            if (value.getData().get(6).getId() == "5f9d49c7f701c65e7f707f18" || value.getData().get(6).getStatus() == 1) {
                                loginUrl = value.getData().get(6).getURL();
                                Picasso.get().load(value.getData().get(6).getImage()).into(activityBinding.ivAdverticement);
                            } else {
                                Toast.makeText(LoginActivity.this, "No Such Advertisement", Toast.LENGTH_SHORT).show();
                            }
                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(LoginActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: " + e.getMessage());
                        Toast.makeText(LoginActivity.this, "" + e, Toast.LENGTH_SHORT).show();

                        //   mDataListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });
    }

}

