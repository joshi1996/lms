package com.lms.View;

import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
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
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.lms.GsonModel.lms_signup;
import com.lms.R;
import com.lms.WebService.Constant_Tag;
import com.lms.WebService.RestApi;
import com.lms.WebService.RestClient;
import com.lms.databinding.ActivityRegistrationBinding;
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

public class SignupActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = SignupActivity.class.getSimpleName();
    private ActivityRegistrationBinding activityBinding;
    public static SignupActivity activity ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Utils.changeStatuscolor(SignupActivity.this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        activityBinding = DataBindingUtil.setContentView(this,  R.layout.activity_registration);
        activity=this;


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activityBinding.inmobileno.setCompoundDrawableTintList(ThemeClass.getcolorstate(SignupActivity.this));
            activityBinding.inpassword.setCompoundDrawableTintList(ThemeClass.getcolorstate(SignupActivity.this));
            activityBinding.infirstname.setCompoundDrawableTintList(ThemeClass.getcolorstate(SignupActivity.this));
            activityBinding.inconfirmpassword.setCompoundDrawableTintList(ThemeClass.getcolorstate(SignupActivity.this));

        }


        ThemeClass.changeLayoutColor(activityBinding.layouttop,SignupActivity.this);
        ThemeClass.changeButtonColor(activityBinding.btnCreateaccount,SignupActivity.this);

        ThemeClass.setAdvertisment(activityBinding.ivAdverticement,SignupActivity.this,"Registration Page", Utils.getDeviceWidth(SignupActivity.this),(int)getResources().getDimension(R.dimen.size_100));


        Glide.with(SignupActivity.this)
                .load(SharePrefs.getSetting(SignupActivity.this).getLogo())
                //.transform(new CircleTransform(..))
                .into(activityBinding.ivFree).onLoadFailed(getResources().getDrawable(R.drawable.placeholder));


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
                }
                else if (activityBinding.inmobileno.getText().toString().trim().length() == 0) {
                    Toasty.warning(SignupActivity.this, getString(R.string.blank_moblieno), Toast.LENGTH_SHORT).show();
                }
                else if (activityBinding.inmobileno.getText().toString().trim().length()<10) {
                    Toasty.warning(SignupActivity.this, getString(R.string.moblieno_invalid), Toast.LENGTH_SHORT).show();
                }
                else if (activityBinding.inpassword.getText().toString().trim().length() == 0) {
                    Toasty.warning(SignupActivity.this, getString(R.string.blank_password), Toast.LENGTH_SHORT).show();
                }
                else if (activityBinding.inpassword.getText().toString().trim().length() < 8) {
                    Toasty.warning(SignupActivity.this, getString(R.string.invalid_password), Toast.LENGTH_SHORT).show();
                }
                else if (activityBinding.inconfirmpassword.getText().toString().trim().length() == 0) {
                    Toasty.warning(SignupActivity.this, getString(R.string.blank_confirmpassword), Toast.LENGTH_SHORT).show();
                }
                else if (!activityBinding.inpassword.getText().toString().trim().equalsIgnoreCase(activityBinding.inconfirmpassword.getText().toString().trim())) {
                    Toasty.warning(SignupActivity.this, getString(R.string.password_notmatched), Toast.LENGTH_SHORT).show();
                }

                else {

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

                            Intent i=  new Intent(SignupActivity.this,OtpActivity.class);
                            Bundle b=new Bundle();
                            b.putString("otp",value.getData().getOtp().toString());
                            b.putParcelable("userdata",value.getData().getUserData());
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
                        //   mDataListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });
    }







    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

