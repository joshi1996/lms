package com.freepadhe.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.freepadhe.GsonModel.Advertisment.AdvertismentModel;
import com.freepadhe.GsonModel.SubmitReviews.SubmitReviewModel;
import com.freepadhe.R;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.ActivityRegistrationBinding;
import com.freepadhe.databinding.ActivityReviewBinding;
import com.freepadhe.utility.Connectivity;
import com.freepadhe.utility.ProgressDialog;
import com.freepadhe.utility.SharePrefs;
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

public class ReviewActivity extends AppCompatActivity {

    private static final String TAG = ReviewActivity.class.getSimpleName();
    private ActivityReviewBinding activityBinding;
    public static ReviewActivity activity;
    String reviewUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.changeStatuscolor(ReviewActivity.this);

        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_review);
        activity = this;

        advertisement();

        activityBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activityBinding.etComment.getText().toString().trim().length() == 0)
                {
                    Toasty.warning(ReviewActivity.this, getString(R.string.blank_feedback), Toast.LENGTH_SHORT).show();
                }
                else if (activityBinding.ratingBar.getRating() == 0.0)
                {
                    Toasty.warning(ReviewActivity.this, getString(R.string.blank_rating), Toast.LENGTH_SHORT).show();
                }

                else
                {
                    if (Connectivity.isConnected(ReviewActivity.this)) {
                        CallReviewApi();
                    } else {

                        //show net not connected error
                        Toasty.warning(ReviewActivity.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void CallReviewApi() {
        String feedBack = activityBinding.etComment.getText().toString();
        String rating = String.valueOf(activityBinding.ratingBar.getNumStars());
        String courseId = SharePrefs.getLocale(getApplicationContext());
        String userId = SharePrefs.getUserdetail(getApplicationContext()).getUser().getId();

        ProgressDialog.showDialog(ReviewActivity.this);
        RestApi mRestApi = RestClient.getClient(getApplicationContext()).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();
        mparam.put("review", feedBack);
        mparam.put("rating",rating);
        mparam.put("courseId",courseId);
        mparam.put("userId",userId);

        Observable<SubmitReviewModel> mdata = mRestApi.getReviews(mparam);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SubmitReviewModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(SubmitReviewModel value) {
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {
                            Toasty.success(ReviewActivity.this, value.getMessage(), Toast.LENGTH_LONG).show();

                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(ReviewActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void advertisement()
    {

        RestApi mRestApi = RestClient.getClient(ReviewActivity.this).create(RestApi.class);

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
                                reviewUrl = value.getData().get(7).getURL();
                                Picasso.get().load(value.getData().get(7).getImage()).into(activityBinding.ivAdvertisementReview);
                            }
                            else
                            {
                                Toast.makeText(ReviewActivity.this, "No Such Advertisement", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else if (value != null)
                        {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(ReviewActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: "+e.getMessage());
                        Toast.makeText(ReviewActivity.this, ""+e, Toast.LENGTH_SHORT).show();

                        //   mDataListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });
    }
}