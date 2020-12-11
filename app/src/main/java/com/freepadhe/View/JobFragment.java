package com.freepadhe.View;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.freepadhe.GsonModel.Job1.Job1Model;
import com.freepadhe.GsonModel.Job2.Job2Model;
import com.freepadhe.R;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.FragmentCourselistBinding;
import com.freepadhe.databinding.FragmentJobBinding;
import com.freepadhe.utility.ThemeClass;
import com.freepadhe.utility.Utils;

public class JobFragment extends Fragment{

    private FragmentJobBinding mbinding;
    private String TAG= JobFragment.class.getSimpleName();

    public static JobFragment newInstance() {
        JobFragment fragment = new JobFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_job, container, false);
        View rootView = mbinding.getRoot();

        ThemeClass.changeHeaderColor(mbinding.llHeaderJob,getActivity());

        TextView tv_header=(TextView) mbinding.llHeaderJob.findViewById(R.id.tv_header);
        tv_header.setText("Jobs");
        LinearLayout ll_back=(LinearLayout) mbinding.llHeaderJob.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();

            }
        });

        callJob1(getActivity());

        mbinding.llParentJob1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFragment();
            }
        });

        callJob2(getActivity());

        mbinding.llParentJob2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoFragment1();
            }
        });

        ll_back.setVisibility(View.GONE);

        return rootView;
    }

    private void callJob1(Context context) {

        //ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        /*HashMap<String, String> mparam = new HashMap<String, String>();
        mparam.put(Constant_Tag.STATUS, "1");*/
        String id = "5f8967e3f701c65e7f707ef9";

        Observable<Job1Model> mdata = mRestApi.job1(id);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Job1Model>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Job1Model value) {
                        //ProgressDialog.hideDialog();
                        if(value!=null)
                            Utils.Relogin(value.getStatus(),getActivity());

                        if (value != null && value.getStatus() == 200) {

                            mbinding.tvTitleJob.setText(value.getData().getName());
                            Glide.with(getActivity())
                                    .load(value.getData().getImage()).into(mbinding.ivJobimage);

                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(getActivity(), value.getMessage(), Toast.LENGTH_SHORT).show();
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
        String id = "5f8968f4f701c65e7f707efa";

        Observable<Job2Model> mdata = mRestApi.job2(id);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Job2Model>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Job2Model value) {
                        //ProgressDialog.hideDialog();
                        if(value!=null)
                            Utils.Relogin(value.getStatus(),getActivity());

                        if (value != null && value.getStatus() == 200) {

                            mbinding.tvTitleJob1.setText(value.getData().getName());
                            Glide.with(getActivity())
                                    .load(value.getData().getImage()).into(mbinding.ivJobimage1);

                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(getActivity(), value.getMessage(), Toast.LENGTH_SHORT).show();
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

    public void gotoFragment()
    {
        FragmentManager fm = getFragmentManager();
        JobDetailFragment fragment = new JobDetailFragment();
        Bundle args = new Bundle();
        args.putString("Job1Id","5f8967e3f701c65e7f707ef9");
        fragment.setArguments(args);
        fm.beginTransaction().replace(R.id.main_framelayout,fragment).addToBackStack(null).commit();
    }

    public void gotoFragment1()
    {
        FragmentManager fm = getFragmentManager();
        JobDetailFragment fragment = new JobDetailFragment();
        Bundle args = new Bundle();
        args.putString("Job2Id","5f8968f4f701c65e7f707efa");
        fragment.setArguments(args);
        //fm.addOnBackStackChangedListener(null);
        fm.beginTransaction().replace(R.id.main_framelayout,fragment).addToBackStack(null).commit();
    }

    /*@Override
    public void OnItemclick(int pos) {
            FragmentTask.replaceFrgament(JobDetailFragment.newInstance(), getActivity().getSupportFragmentManager(), R.id.main_framelayout);
    }

    @Override
    public void OnDesItemclick(int pos) {

    }*/
}