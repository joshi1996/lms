package com.freepadhe.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.freepadhe.GsonModel.Advertisment.AdvertismentModel;
import com.freepadhe.GsonModel.ForgetPassword.User;
import com.freepadhe.GsonModel.ResetPasswordModel.ResetPasswordDataModel;
import com.freepadhe.R;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.ActivityForgotpasswordBinding;
import com.freepadhe.databinding.ActivityResetPasswordBinding;
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

public class ResetPassword extends AppCompatActivity {

    private static final String TAG = ForgetPasswordActivity.class.getSimpleName();
    private ActivityResetPasswordBinding activityBinding;
    String  otp,mobile;
    User userdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.changeStatuscolor(ResetPassword.this);

        activityBinding = DataBindingUtil.setContentView(this,  R.layout.activity_reset_password);

        ThemeClass.changeButtonColor(activityBinding.btnResetpassword,ResetPassword.this);

        activityBinding.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int start,end;
                Log.i("inside checkbox chnge",""+b);
                if(!b){
                    start=activityBinding.inpasswordforget.getSelectionStart();
                    end=activityBinding.inpasswordforget.getSelectionEnd();
                    activityBinding.inpasswordforget.setTransformationMethod(new PasswordTransformationMethod());;
                    activityBinding.inpasswordforget.setSelection(start,end);
                }else{
                    start=activityBinding.inpasswordforget.getSelectionStart();
                    end=activityBinding.inpasswordforget.getSelectionEnd();
                    activityBinding.inpasswordforget.setTransformationMethod(null);
                    activityBinding.inpasswordforget.setSelection(start,end);
                }
            }
        });

        activityBinding.cCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int start,end;
                Log.i("inside checkbox chnge",""+b);
                if(!b){
                    start=activityBinding.cinpasswordforget.getSelectionStart();
                    end=activityBinding.cinpasswordforget.getSelectionEnd();
                    activityBinding.cinpasswordforget.setTransformationMethod(new PasswordTransformationMethod());;
                    activityBinding.cinpasswordforget.setSelection(start,end);
                }else{
                    start=activityBinding.cinpasswordforget.getSelectionStart();
                    end=activityBinding.cinpasswordforget.getSelectionEnd();
                    activityBinding.cinpasswordforget.setTransformationMethod(null);
                    activityBinding.cinpasswordforget.setSelection(start,end);
                }
            }
        });

        //advertisement();

        Glide.with(ResetPassword.this)
                .load(SharePrefs.getSetting(ResetPassword.this).getLogo())
                //.transform(new CircleTransform(..))
                .into(activityBinding.ivFree).onLoadFailed(getResources().getDrawable(R.drawable.placeholder_banner));


        activityBinding.tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetPassword.this.finish();
            }
        });

        activityBinding.tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ResetPassword.this,SignupActivity.class));
                ResetPassword.this.finish();
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activityBinding.inotpforget.setCompoundDrawableTintList(ThemeClass.getcolorstate(ResetPassword.this));
        }

        if(getIntent()!=null){
            Bundle b=getIntent().getExtras();

            if(b!=null){
                otp= b.getString("otp");
                userdata=b.getParcelable("userdata");
                mobile = b.getString("mobile");
                activityBinding.inotpforget.setText(otp);
            }
        }

        activityBinding.showPhoneReset.setText(mobile);

        activityBinding.btnResetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (activityBinding.inpasswordforget.getText().toString().trim().length() == 0) {
                    Toasty.warning(ResetPassword.this, getString(R.string.blank_password), Toast.LENGTH_SHORT).show();
                }
                else if (activityBinding.inpasswordforget.getText().toString().trim().length()<8) {
                    Toasty.warning(ResetPassword.this, getString(R.string.invalid_password), Toast.LENGTH_SHORT).show();
                }
                else if (activityBinding.cinpasswordforget.getText().toString().trim().length() == 0) {
                    Toasty.warning(ResetPassword.this, getString(R.string.blank_confirmpassword), Toast.LENGTH_SHORT).show();
                } else if (!activityBinding.cinpasswordforget.getText().toString().trim().equalsIgnoreCase(activityBinding.inpasswordforget.getText().toString().trim())) {
                    Toasty.warning(ResetPassword.this, getString(R.string.password_notmatched), Toast.LENGTH_SHORT).show();
                }
                    else {

                    if (Connectivity.isConnected(ResetPassword.this)) {

                        CallResetApi(ResetPassword.this);
                    } else {

                        //show net not connected error
                        Toasty.warning(ResetPassword.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

                    }

                }
            }
        });

    }


    @Override
    public void onBackPressed() {
        ResetPassword.this.finish();
    }

    private void CallResetApi(final Context context) {
        String password = activityBinding.inpasswordforget.getText().toString().trim();
        String otp = activityBinding.inotpforget.getText().toString().trim();
        //String mobileno = activityBinding.showPhoneReset.getText().toString();
        ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();
        mparam.put("phoneNo", mobile);
        mparam.put("otp",otp);
        mparam.put("password",password);
        Observable<ResetPasswordDataModel> mdata = mRestApi.resetPassword(mparam);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResetPasswordDataModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }
                    @Override
                    public void onNext(ResetPasswordDataModel value) {
                        ProgressDialog.hideDialog();

                        Log.d("value",value.getStatus().toString());

                        if (value != null && value.getStatus() == 200) {
                            //Toasty.success(ForgetPasswordActivity.this, value.getMessage(), Toast.LENGTH_LONG).show();
                            Utils.hideKeyboard(ResetPassword.this);

                            Intent i=new Intent(ResetPassword.this,LoginActivity.class);
                            Toast.makeText(context, "Password Reset Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(i);
                            ResetPassword.this.finish();
                        }

                        else if (value != null) {
                            Toasty.error(ResetPassword.this, value.getMessage(), Toast.LENGTH_LONG).show();
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

    public void advertisement()
    {
        ProgressDialog.showDialog(ResetPassword.this);
        RestApi mRestApi = RestClient.getClient(ResetPassword.this).create(RestApi.class);

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
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200)
                        {

                            if (value.getData().get(8).getId() == "5f9d49f8f701c65e7f707f1a" || value.getData().get(8).getStatus() == 1)
                            {
                                Picasso.get().load(value.getData().get(8).getImage()).into(activityBinding.ivAdvertisementForget);
                            }
                            else
                            {
                                Toast.makeText(ResetPassword.this, "No Such Advertisement", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (value != null)
                        {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(ResetPassword.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: "+e.getMessage());
                        Toast.makeText(ResetPassword.this, ""+e, Toast.LENGTH_SHORT).show();
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