package com.freepadhe.View;

import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.freepadhe.GsonModel.Advertisment.AdvertismentModel;
import com.freepadhe.GsonModel.lms_signup;
import com.freepadhe.R;
import com.freepadhe.WebService.Constant_Tag;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.ActivityRegistrationBinding;
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
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SignupActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = SignupActivity.class.getSimpleName();
    private ActivityRegistrationBinding activityBinding;
    public static SignupActivity activity;

    String signUpUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Utils.changeStatuscolor(SignupActivity.this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_registration);
        activity = this;

        activityBinding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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

        activityBinding.checkBoxVisible.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int start,end;
                Log.i("inside checkbox chnge",""+b);
                if(!b){
                    start=activityBinding.inconfirmpassword.getSelectionStart();
                    end=activityBinding.inconfirmpassword.getSelectionEnd();
                    activityBinding.inconfirmpassword.setTransformationMethod(new PasswordTransformationMethod());;
                    activityBinding.inconfirmpassword.setSelection(start,end);
                }else{
                    start=activityBinding.inconfirmpassword.getSelectionStart();
                    end=activityBinding.inconfirmpassword.getSelectionEnd();
                    activityBinding.inconfirmpassword.setTransformationMethod(null);
                    activityBinding.inconfirmpassword.setSelection(start,end);
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activityBinding.inmobileno.setCompoundDrawableTintList(ThemeClass.getcolorstate(SignupActivity.this));
            activityBinding.inpassword.setCompoundDrawableTintList(ThemeClass.getcolorstate(SignupActivity.this));
            activityBinding.infirstname.setCompoundDrawableTintList(ThemeClass.getcolorstate(SignupActivity.this));
            activityBinding.inconfirmpassword.setCompoundDrawableTintList(ThemeClass.getcolorstate(SignupActivity.this));
            activityBinding.infriendCode.setCompoundDrawableTintList(ThemeClass.getcolorstate(SignupActivity.this));

        }
        ThemeClass.changeButtonColor(activityBinding.btnCreateaccount, SignupActivity.this);

        advertisement();

        activityBinding.ivAdverticement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent google=new Intent(Intent.ACTION_VIEW);
                google.setData(Uri.parse(signUpUrl));
                startActivity(google);
            }
        });

        Glide.with(SignupActivity.this)
                .load(SharePrefs.getSetting(SignupActivity.this).getLogo())
                //.transform(new CircleTransform(..))
                .into(activityBinding.ivFree).onLoadFailed(getResources().getDrawable(R.drawable.placeholder_banner));


        activityBinding.tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignupActivity.this.finish();

            }
        });


        activityBinding.btnCreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activityBinding.infirstname.getText().toString().trim().length() == 0) {
                    Toasty.warning(SignupActivity.this, getString(R.string.blank_firstname), Toast.LENGTH_SHORT).show();
                } else if (activityBinding.inmobileno.getText().toString().trim().length() == 0) {
                    Toasty.warning(SignupActivity.this, getString(R.string.blank_moblieno), Toast.LENGTH_SHORT).show();
                } else if (activityBinding.inmobileno.getText().toString().trim().length() < 10) {
                    Toasty.warning(SignupActivity.this, getString(R.string.moblieno_invalid), Toast.LENGTH_SHORT).show();
                } else if (activityBinding.inpassword.getText().toString().trim().length() == 0) {
                    Toasty.warning(SignupActivity.this, getString(R.string.blank_password), Toast.LENGTH_SHORT).show();
                } else if (activityBinding.inpassword.getText().toString().trim().length() < 8) {
                    Toasty.warning(SignupActivity.this, getString(R.string.invalid_password), Toast.LENGTH_SHORT).show();
                } else if (activityBinding.inconfirmpassword.getText().toString().trim().length() == 0) {
                    Toasty.warning(SignupActivity.this, getString(R.string.blank_confirmpassword), Toast.LENGTH_SHORT).show();
                } else if (!activityBinding.inpassword.getText().toString().trim().equalsIgnoreCase(activityBinding.inconfirmpassword.getText().toString().trim())) {
                    Toasty.warning(SignupActivity.this, getString(R.string.password_notmatched), Toast.LENGTH_SHORT).show();
                } else {

                    if (Connectivity.isConnected(SignupActivity.this)) {

                        CallSignupApi(SignupActivity.this);
                    } else {

                        //show net not connected error
                        Toasty.warning(SignupActivity.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

                    }

                }

            }
        });
    }


    @Override
    public void onBackPressed() {
        SignupActivity.this.finish();
    }

    private void CallSignupApi(final SignupActivity context) {
        String firstname = activityBinding.infirstname.getText().toString().trim();

        String mobileno = activityBinding.inmobileno.getText().toString().trim();
        String password = activityBinding.inpassword.getText().toString().trim();

        ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();
        mparam.put(Constant_Tag.Name, firstname);
        mparam.put(Constant_Tag.PHONE, mobileno);
        mparam.put(Constant_Tag.PASSWORD, password);
        if (activityBinding.infriendCode.getText().toString().trim().length() > 0)
            mparam.put(Constant_Tag.FRIENDCODE, activityBinding.infriendCode.getText().toString());

        String androidId = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        mparam.put("deviceid", androidId);

        Observable<lms_signup> mdata = mRestApi.getSignup(mparam);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<lms_signup>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(lms_signup value) {
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {
                            Toasty.success(SignupActivity.this, value.getMessage(), Toast.LENGTH_LONG).show();

                            Intent i = new Intent(SignupActivity.this, OtpActivity.class);
                            Bundle b = new Bundle();
                            b.putString("otp", value.getData().getOtp().toString());
                            b.putParcelable("userdata", value.getData().getUserData());
                            i.putExtras(b);
                            startActivity(i);
                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(SignupActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: ");
                        ProgressDialog.hideDialog();
                        Toast.makeText(getApplicationContext(), ""+e, Toast.LENGTH_SHORT).show();
                        //   mDataListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });
    }

    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void advertisement()
    {

        RestApi mRestApi = RestClient.getClient(SignupActivity.this).create(RestApi.class);

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

                            if (value.getData().get(7).getId() == "5f9d49e5f701c65e7f707f19" || value.getData().get(7).getStatus() == 1)
                            {
                                signUpUrl = value.getData().get(7).getURL();
                                Picasso.get().load(value.getData().get(7).getImage()).into(activityBinding.ivAdverticement);
                            }
                            else
                            {
                                Toast.makeText(SignupActivity.this, "No Such Advertisement", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (value != null)
                        {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(SignupActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: "+e.getMessage());
                        Toast.makeText(SignupActivity.this, ""+e, Toast.LENGTH_SHORT).show();

                        //   mDataListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });
    }

}

