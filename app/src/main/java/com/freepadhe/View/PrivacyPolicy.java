package com.freepadhe.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
import com.freepadhe.databinding.ActivityPrivacyPolicyBinding;
import com.freepadhe.databinding.ActivityTermsAndConditionBinding;
import com.freepadhe.utility.Connectivity;
import com.freepadhe.utility.ProgressDialog;
import com.freepadhe.utility.ThemeClass;
import com.freepadhe.utility.Utils;
import com.squareup.picasso.Picasso;

import static android.text.Layout.JUSTIFICATION_MODE_INTER_WORD;

public class PrivacyPolicy extends AppCompatActivity {

    private static final String TAG = PrivacyPolicy.class.getSimpleName();
    private ActivityPrivacyPolicyBinding activityBinding;

    String policyUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.changeStatuscolor(PrivacyPolicy.this);

        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_privacy_policy);

        ThemeClass.changeHeaderColor(activityBinding.headerSupport, getApplicationContext());
        TextView tv_header = (TextView) activityBinding.headerSupport.findViewById(R.id.tv_header);
        tv_header.setText(getString(R.string.privacy_policy));
        LinearLayout ll_back = (LinearLayout) activityBinding.headerSupport.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activityBinding.ivAdvertisementPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent google=new Intent(Intent.ACTION_VIEW);
                google.setData(Uri.parse(policyUrl));
                startActivity(google);
            }
        });


        if (Connectivity.isConnected(PrivacyPolicy.this)) {
            advertisement();
            CallSettingApi(PrivacyPolicy.this);
        } else {

            //show net not connected error
            Toasty.warning(PrivacyPolicy.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

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
                            Utils.hideKeyboard(PrivacyPolicy.this);

                            String str_without_html= Html.fromHtml(value.getData().getPrivacyPolicy()).toString();
                            activityBinding.tvPolicy.setText(str_without_html);
                            activityBinding.tvPolicy.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD);
                            activityBinding.tvPolicy.setMovementMethod(LinkMovementMethod.getInstance());

                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(PrivacyPolicy.this, value.getMessage(), Toast.LENGTH_SHORT).show();

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
        PrivacyPolicy.this.finish();
    }

    public void advertisement() {

        RestApi mRestApi = RestClient.getClient(PrivacyPolicy.this).create(RestApi.class);

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
                                Picasso.get().load(value.getData().get(4).getImage()).into(activityBinding.ivAdvertisementPrivacy);
                            } else {
                                Toast.makeText(PrivacyPolicy.this, "No Such Advertisement", Toast.LENGTH_SHORT).show();
                            }
                        } else if (value != null) {
                            Toasty.error(PrivacyPolicy.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: " + e.getMessage());
                        Toast.makeText(PrivacyPolicy.this, "" + e, Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onComplete() {

                        Log.e(TAG, "onComplete: All Done!");
                    }
                });
    }

}