package com.freepadhe.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freepadhe.GsonModel.Advertisment.AdvertismentModel;
import com.freepadhe.GsonModel.settingdata.SettingModel;
import com.freepadhe.R;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.ActivityAboutUsBinding;
import com.freepadhe.databinding.ActivityPrivacyPolicyBinding;
import com.freepadhe.utility.Connectivity;
import com.freepadhe.utility.ProgressDialog;
import com.freepadhe.utility.ThemeClass;
import com.freepadhe.utility.Utils;
import com.squareup.picasso.Picasso;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class AboutUsActivity extends AppCompatActivity {

    private static final String TAG = AboutUsActivity.class.getSimpleName();
    private ActivityAboutUsBinding activityBinding;

    String policyUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.changeStatuscolor(AboutUsActivity.this);

        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_about_us);

        ThemeClass.changeHeaderColor(activityBinding.headerSupport, getApplicationContext());
        TextView tv_header = (TextView) activityBinding.headerSupport.findViewById(R.id.tv_header);
        tv_header.setText(getString(R.string.aboutUs));
        LinearLayout ll_back = (LinearLayout) activityBinding.headerSupport.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activityBinding.ivAdvertisementAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent google=new Intent(Intent.ACTION_VIEW);
                google.setData(Uri.parse(policyUrl));
                startActivity(google);
            }
        });


        if (Connectivity.isConnected(AboutUsActivity.this)) {
            advertisement();
            CallSettingApi(AboutUsActivity.this);
        } else {

            //show net not connected error
            Toasty.warning(AboutUsActivity.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

        }

    }

    private void CallSettingApi(final Context context) {

        ProgressDialog.showDialog(context);

        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        Observable<SettingModel> mdata = mRestApi.Getsettings();
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SettingModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onNext(SettingModel value) {
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {
                            Utils.hideKeyboard(AboutUsActivity.this);

                            String str_without_html= Html.fromHtml(value.getData().getAbout()).toString();
                            activityBinding.tvAbout.setText(str_without_html);
                            activityBinding.tvAbout.setMovementMethod(LinkMovementMethod.getInstance());
                            activityBinding.tvAbout.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);

                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(AboutUsActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();

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

    @Override
    public void onBackPressed() {
        AboutUsActivity.this.finish();
    }

    public void advertisement() {

        RestApi mRestApi = RestClient.getClient(AboutUsActivity.this).create(RestApi.class);

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

                            if (value.getData().get(4).getId() == "5f9d2d04f701c65e7f707f16" || value.getData().get(4).getStatus() == 1) {
                                policyUrl = value.getData().get(4).getURL();
                                Picasso.get().load(value.getData().get(4).getImage()).into(activityBinding.ivAdvertisementAbout);
                            } else {
                                Toast.makeText(AboutUsActivity.this, "No Such Advertisement", Toast.LENGTH_SHORT).show();
                            }
                        } else if (value != null) {
                            Toasty.error(AboutUsActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: " + e.getMessage());
                        Toast.makeText(AboutUsActivity.this, "" + e, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                        Log.e(TAG, "onComplete: All Done!");
                    }
                });
    }

}