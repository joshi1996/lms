package com.freepadhe.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freepadhe.Adapter.ShowReviewAdapter;
import com.freepadhe.GsonModel.ReviewsModel.ReviewDatum;
import com.freepadhe.GsonModel.ReviewsModel.ReviewModel;
import com.freepadhe.R;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.ActivityPrivacyPolicyBinding;
import com.freepadhe.databinding.ActivityShowReviewsBinding;
import com.freepadhe.utility.Connectivity;
import com.freepadhe.utility.GridSpacingItemDecoration;
import com.freepadhe.utility.ProgressDialog;
import com.freepadhe.utility.SharePrefs;
import com.freepadhe.utility.ThemeClass;
import com.freepadhe.utility.Utils;

import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShowReviews extends AppCompatActivity {
    private static final String TAG = ShowReviews.class.getSimpleName();
    private ActivityShowReviewsBinding activityBinding;
    ShowReviewAdapter adapter;
    List<ReviewDatum> mlist;
    String className;
    Float classReviews;
    String Id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Utils.changeStatuscolor(ShowReviews.this);

        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_show_reviews);

        className = getIntent().getStringExtra("class_name");
        classReviews = getIntent().getFloatExtra("class_reviews",0);

        Id = SharePrefs.getLocale(getApplicationContext());
        //Toast.makeText(this, ""+Id, Toast.LENGTH_SHORT).show();

        ThemeClass.changeHeaderColor(activityBinding.llHeader, getApplicationContext());
        TextView tv_header = (TextView) activityBinding.llHeader.findViewById(R.id.tv_header);
        tv_header.setText(getString(R.string.show_reviews));
        LinearLayout ll_back = (LinearLayout) activityBinding.llHeader.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activityBinding.tvTitle.setText(className);
        activityBinding.ratingBar.setRating(classReviews);

        activityBinding.tvReviewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowReviews.this,ReviewActivity.class);
                startActivity(intent);
            }
        });

        activityBinding.reviewList.setHasFixedSize(true);
        activityBinding.reviewList.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_15);
        activityBinding.reviewList.addItemDecoration(new GridSpacingItemDecoration(1,spacingInPixels,false));

        if (Connectivity.isConnected(ShowReviews.this)) {
            callTransactionApi();
        }
        else {
            //show net not connected error
            Toasty.warning(ShowReviews.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();
        }
    }

    private void callTransactionApi() {

        ProgressDialog.showDialog(ShowReviews.this);
        RestApi mRestApi = RestClient.getClient(getApplicationContext()).create(RestApi.class);

        Observable<ReviewModel> mdata = mRestApi.reviewShow("1",Id);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReviewModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(ReviewModel value) {
                        ProgressDialog.hideDialog();
                        if(value!=null)
                            Utils.Relogin(value.getStatus(),getApplicationContext());

                        if (value != null && value.getStatus() == 200) {
                            mlist=value.getData();
                            if(mlist!=null){
                                adapter=new ShowReviewAdapter(ShowReviews.this,mlist);

                                activityBinding.reviewList.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: ");
                        Toast.makeText(ShowReviews.this, ""+e, Toast.LENGTH_SHORT).show();
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