package com.freepadhe.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.freepadhe.Adapter.InviteAdapter;
import com.freepadhe.BuildConfig;
import com.freepadhe.GsonModel.InviteModel.Datum;
import com.freepadhe.GsonModel.InviteModel.InviteModel;
import com.freepadhe.R;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.FragmentClassroomBinding;
import com.freepadhe.databinding.FragmentInvitelistBinding;
import com.freepadhe.interfaces.OnclickListener;
import com.freepadhe.utility.GridSpacingItemDecoration;
import com.freepadhe.utility.ProgressDialog;
import com.freepadhe.utility.SharePrefs;
import com.freepadhe.utility.ThemeClass;
import com.freepadhe.utility.Utils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class InviteListFragment extends Fragment implements OnclickListener{
    private FragmentInvitelistBinding mbinding;
    private String TAG = InviteListFragment.class.getSimpleName();
    List<Datum> mlist;
    InviteAdapter adapter;

    public static InviteListFragment newInstance() {
        InviteListFragment fragment = new InviteListFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_invitelist, container, false);
        View rootView = mbinding.getRoot();

        ThemeClass.changeHeaderColor(mbinding.llHeader, getActivity());
        ThemeClass.changeButtonColor(mbinding.btnInvite,getActivity());

        TextView tv_header = (TextView) mbinding.llHeader.findViewById(R.id.tv_header);
        tv_header.setText(getString(R.string.inviteandearn));
        LinearLayout ll_back = (LinearLayout) mbinding.llHeader.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        ll_back.setVisibility(View.VISIBLE);

        //mbinding.tvWelcome.setText(SharePrefs.getSetting(getActivity()).getSlogan());

        mbinding.rvItem.setHasFixedSize(true);
        mbinding.rvItem.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_15);
        mbinding.rvItem.addItemDecoration(new GridSpacingItemDecoration(2,spacingInPixels,false));


        //mbinding.tvWelcome.setText("Hello "+SharePrefs.getUserdetail(getActivity()).getUser().getFullName()+" your code is "+SharePrefs.getUserdetail(getActivity()).getUser().getUserCode()+" code to invite your friend & students to join "+SharePrefs.getSetting(getActivity()).getOrganizationName()+" and earn  money Rs "+SharePrefs.getSetting(getActivity()).getEarnAmount()+"/- on every "+ SharePrefs.getSetting(getActivity()).getFriendCountForEarn()+" student to join paid course.");


        mbinding.btnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Hey check out  app at: https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +" "+" enter referral code  "+SharePrefs.getUserdetail(getActivity()).getUser().getUserCode()+" to join and share among friends and earn money Rs "+SharePrefs.getSetting(getActivity()).getEarnAmount()+"/- on every "+SharePrefs.getSetting(getActivity()).getFriendCountForEarn()+" student to join paid course.");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        callInvitelist(getActivity(),"free");


        /*mbinding.tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbinding.tvAll.setText(Html.fromHtml("<B><U>"+getString(R.string.all)+"</U></B>"));
                mbinding.tvRunning.setText("Free");
                mbinding.tvCompleted.setText("Paid");

                if (Connectivity.isConnected(getActivity())) {

                    callInvitelist(getActivity(),"all");
                } else {

                    //show net not connected error
                    Toasty.warning(getActivity(), getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

                }
                Utils.hideKeyboard(getActivity());
            }
        });

        mbinding.tvRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbinding.tvRunning.setText(Html.fromHtml("<B><U>Free</U></B>"));
                mbinding.tvAll.setText(Html.fromHtml(getString(R.string.all)));
                mbinding.tvCompleted.setText("Paid");
                if (Connectivity.isConnected(getActivity())) {

                    callInvitelist(getActivity(),"free");
                } else {

                    //show net not connected error
                    Toasty.warning(getActivity(), getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

                }
                Utils.hideKeyboard(getActivity());

            }
        });

        mbinding.tvCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbinding.tvCompleted.setText(Html.fromHtml("<B><U>Paid</U></B>"));
                mbinding.tvRunning.setText("Free");
                mbinding.tvAll.setText(Html.fromHtml(getString(R.string.all)));
                if (Connectivity.isConnected(getActivity())) {

                    callInvitelist(getActivity(),"Paid");
                } else {

                    //show net not connected error
                    Toasty.warning(getActivity(), getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

                }
                Utils.hideKeyboard(getActivity());

            }
        });

        mbinding.tvAll.performClick();*/

        return rootView;
    }


    private void callInvitelist(Context context,String tag) {

        ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);

        Observable<InviteModel> mdata = mRestApi.invitlist(SharePrefs.getUserdetail(getActivity()).getUser().getUserCode().toString(),tag);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InviteModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(InviteModel value) {
                        ProgressDialog.hideDialog();
                           if(value!=null)
                            Utils.Relogin(value.getStatus(),getActivity());

                        if (value != null && value.getStatus() == 200) {
                            mlist=value.getData();
                            if(mlist!=null){
                            adapter=new InviteAdapter(getActivity(),mlist, InviteListFragment.this);

                            mbinding.rvItem.setAdapter(adapter);
                            }
                            mbinding.rvItem.setVisibility(View.VISIBLE);
                            mbinding.rvRelateNodata.setVisibility(View.GONE);
                            //mbinding.llfilter.setVisibility(View.VISIBLE);
                        }

                        else if (value!=null)
                        {
                            mbinding.rvRelateNodata.setVisibility(View.VISIBLE);
                            mbinding.rvItem.setVisibility(View.GONE);
                            //mbinding.tvNoDataTxt.setText("No Data Found");
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

    @Override
    public void OnItemclick(int pos) {

    }

    @Override
    public void OnDesItemclick(int pos) {

    }
}