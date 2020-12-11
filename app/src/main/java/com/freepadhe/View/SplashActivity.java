package com.freepadhe.View;


import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.freepadhe.GsonModel.Advertisment.AdvertismentModel;
import com.freepadhe.GsonModel.settingdata.SettingModel;
import com.freepadhe.R;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.ActivitySplashBinding;
import com.freepadhe.utility.Connectivity;
import com.freepadhe.utility.ProgressDialog;
import com.freepadhe.utility.SharePrefs;
import com.freepadhe.utility.Utils;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


/**
 * A login screen that offers login via email/password.
 */
public class SplashActivity extends AppCompatActivity {
    private static final String TAG = SplashActivity.class.getSimpleName();

    private ActivitySplashBinding activityBinding;

    private final int SPLASH_DISPLAY_LENGTH = 2500;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
         activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_splash);

        Utils.changeStatuscolor(SplashActivity.this);

        if (Connectivity.isConnected(SplashActivity.this)) {

            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {
                    callSettingApi(SplashActivity.this);
                }
            }, SPLASH_DISPLAY_LENGTH);

        } else {

            //show net not connected error
            Toasty.warning(SplashActivity.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

        }
    }
    private void callSettingApi(final Context context) {

        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        Observable<SettingModel> mdata = mRestApi.Getsettings();
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SettingModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(SettingModel value) {
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {
                            Utils.hideKeyboard(SplashActivity.this);
                            SharePrefs.saveSettings(SplashActivity.this,value.getData());

                            callAdvertismentApi(SplashActivity.this);

                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(SplashActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();

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


    private void callAdvertismentApi(final Context context) {

        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);

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
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {
                            Utils.hideKeyboard(SplashActivity.this);

                            SharePrefs.saveAdvertisment(SplashActivity.this,value);

                            //Toast.makeText(SplashActivity.this, ""+SharePrefs.getAdvertisment(SplashActivity.this).getData().get(0).getPage(), Toast.LENGTH_SHORT).show();

                            if (SharePrefs.getUserdetail(SplashActivity.this) != null && SharePrefs.getUserdetail(SplashActivity.this).getUser().getCountry().equals(""))
                            {
                                Intent intent = new Intent(SplashActivity.this,CompleteProfile.class);
                                startActivity(intent);
                                SplashActivity.this.finish();
                            }
                            else if (SharePrefs.getUserdetail(SplashActivity.this) != null)
                            {
                                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                                startActivity(i);
                                SplashActivity.this.finish();
                            }
                            else {
                                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(i);
                                SplashActivity.this.finish();
                            }
                        } else {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(SplashActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
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

