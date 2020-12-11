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

import com.freepadhe.Adapter.LiveVideoAdapter;
import com.freepadhe.GsonModel.LiveVideo.LiveVideoDatum;
import com.freepadhe.GsonModel.LiveVideo.LiveVideoModel;
import com.freepadhe.R;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.ActivityLiveClassBinding;
import com.freepadhe.interfaces.OnclickListener;
import com.freepadhe.utility.Connectivity;
import com.freepadhe.utility.GridSpacingItemDecoration;
import com.freepadhe.utility.ProgressDialog;
import com.freepadhe.utility.ThemeClass;
import com.freepadhe.utility.Utils;

import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LiveClassActivity extends AppCompatActivity implements OnclickListener {

    private static final String TAG = TermsAndCondition.class.getSimpleName();
    private ActivityLiveClassBinding activityBinding;

    LiveVideoAdapter adapter;
    List<LiveVideoDatum> mlist;

    String classIdName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.changeStatuscolor(LiveClassActivity.this);

        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_live_class);

        classIdName = getIntent().getStringExtra("class_id");
        //Toast.makeText(this, ""+classIdName, Toast.LENGTH_SHORT).show();

        ThemeClass.changeHeaderColor(activityBinding.llHeader, getApplicationContext());
        TextView tv_header = (TextView) activityBinding.llHeader.findViewById(R.id.tv_header);
        tv_header.setText("Live Classes");
        LinearLayout ll_back = (LinearLayout) activityBinding.llHeader.findViewById(R.id.ll_back);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        activityBinding.rvItemLive.setHasFixedSize(true);
        activityBinding.rvItemLive.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_15);
        activityBinding.rvItemLive.addItemDecoration(new GridSpacingItemDecoration(1,spacingInPixels,false));

        if (Connectivity.isConnected(LiveClassActivity.this)) {
            callLiveClassApi();
        }
        else {
            //show net not connected error
            Toasty.warning(LiveClassActivity.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        LiveClassActivity.this.finish();
    }

    private void callLiveClassApi() {

        String classId = classIdName;

        ProgressDialog.showDialog(LiveClassActivity.this);
        RestApi mRestApi = RestClient.getClient(getApplicationContext()).create(RestApi.class);

        Observable<LiveVideoModel> mdata = mRestApi.liveVideo("1",classId);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LiveVideoModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(LiveVideoModel value) {
                        ProgressDialog.hideDialog();

                        if (value != null && value.getStatus() == 200) {
                            mlist=value.getData();
                            if(mlist!=null){
                                adapter=new LiveVideoAdapter(LiveClassActivity.this,mlist,LiveClassActivity.this);
                                activityBinding.rvItemLive.setAdapter(adapter);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: ");
                        Toast.makeText(LiveClassActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                        ProgressDialog.hideDialog();
                        //   mDataListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });

    }

    public void OnItemclick(int pos) {

        if (mlist != null) {
            Intent intent = new Intent(LiveClassActivity.this,LiveSessionVideoDetail.class);
            intent.putExtra("videoUrlLive",mlist.get(pos).getCode());
            intent.putExtra("videoTitle",mlist.get(pos).getTitle());
            intent.putExtra("videoDetail",mlist.get(pos).getDescription());
            startActivity(intent);
        }

    }

    public void OnDesItemclick(int pos) {

    }

}