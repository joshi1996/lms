package com.freepadhe.View;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.freepadhe.Adapter.JobDetailListAdapter;
import com.freepadhe.GsonModel.JobDetailModels.JobDetailModel;
import com.freepadhe.R;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.FragmentJobDetailBinding;
import com.freepadhe.utility.Connectivity;
import com.freepadhe.utility.GridSpacingItemDecoration;
import com.freepadhe.utility.ThemeClass;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class JobDetailFragment extends Fragment {
    private FragmentJobDetailBinding mbinding;
    private String TAG = JobDetailFragment.class.getSimpleName();
    JobDetailModel jobDetailModel;
    JobDetailListAdapter jobDetailListAdapter;
    String job1Id,job2Id;

    public static JobDetailFragment newInstance() {
        JobDetailFragment fragment = new JobDetailFragment();
        return fragment;
    }

    /*public static JobDetailFragment newInstance(JobDetailModel jobDetailModel) {
        JobDetailFragment fragment = new JobDetailFragment();
        return fragment;
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_detail, container, false);
        View rootView = mbinding.getRoot();
        ThemeClass.changeHeaderColor(mbinding.llHeaderJobDetail, getActivity());

        job1Id = getArguments().getString("Job1Id");
        job2Id = getArguments().getString("Job2Id");

        TextView tv_header = (TextView) mbinding.llHeaderJobDetail.findViewById(R.id.tv_header);
        tv_header.setText(getString(R.string.Jobdetail));
        LinearLayout ll_back = (LinearLayout) mbinding.llHeaderJobDetail.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });
        ll_back.setVisibility(View.VISIBLE);


        mbinding.rvItemJobDetail.setHasFixedSize(true);
        mbinding.rvItemJobDetail.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_15);
        mbinding.rvItemJobDetail.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, false));

        mbinding.tvDesciptiontitleJobDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mbinding.wvDesciptionJob.getVisibility() == View.VISIBLE) {
                    mbinding.tvDesciptiontitleJobDetail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
                    mbinding.wvDesciptionJob.setVisibility(View.GONE);
                } else {
                    mbinding.tvDesciptiontitleJobDetail.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0);

                    mbinding.wvDesciptionJob.setVisibility(View.VISIBLE);

                }
            }
        });


        if (Connectivity.isConnected(getActivity())) {
            callJob1(getActivity());
            callJob2(getActivity());
        } else {
            //show net not connected error
            Toasty.warning(getActivity(), getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }

    private void callJob1(Context context) {

        //ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        String id = "5f8967e3f701c65e7f707ef9";

        Observable<JobDetailModel> mdata = mRestApi.jobSublist(job1Id);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JobDetailModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(JobDetailModel value) {

                        Log.d("jobData",value.getName());
                        //ProgressDialog.hideDialog();

                        if (value != null) {
                            mbinding.tvTitleJobDetail.setText(value.getName());
                            Glide.with(getActivity())
                                    .load(value.getImage()).into(mbinding.ivCoverJobDetail);
                            mbinding.wvDesciptionJob.loadData("<p align='justify'>" + value.getDescription() + "</p>", "text/html; charset=UTF-8", null);

                            if(jobDetailModel!=null) {

                                jobDetailListAdapter = new JobDetailListAdapter(getActivity(), jobDetailModel.getSubjectData());
                                mbinding.rvItemJobDetail.setAdapter(jobDetailListAdapter);
                            }

                        } else if (value != null) {
                            Toasty.error(getActivity(), String.valueOf( value.getStatus()), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: ");
                        //ProgressDialog.hideDialog();
                        //   mDataListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //ProgressDialog.hideDialog();
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });

    }

    private void callJob2(Context context) {

        //ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        //String id = "5f8967e3f701c65e7f707ef9";

        Observable<JobDetailModel> mdata = mRestApi.jobSublist(job2Id);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<JobDetailModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(JobDetailModel value) {

                        Log.d("jobData",value.getName());
                        //ProgressDialog.hideDialog();

                        if (value != null) {
                            mbinding.tvTitleJobDetail.setText(value.getName());
                            Glide.with(getActivity())
                                    .load(value.getImage()).into(mbinding.ivCoverJobDetail);
                            mbinding.wvDesciptionJob.loadData("<p align='justify'>" + value.getDescription() + "</p>", "text/html; charset=UTF-8", null);

                            if(jobDetailModel!=null) {

                                jobDetailListAdapter = new JobDetailListAdapter(getActivity(), jobDetailModel.getSubjectData());
                                mbinding.rvItemJobDetail.setAdapter(jobDetailListAdapter);
                            }

                        } else if (value != null) {
                            Toasty.error(getActivity(), String.valueOf( value.getStatus()), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: ");
                        //ProgressDialog.hideDialog();
                        //   mDataListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        //ProgressDialog.hideDialog();
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });

    }

}