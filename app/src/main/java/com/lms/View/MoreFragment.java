package com.lms.View;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lms.GsonModel.BasicModel;
import com.lms.R;
import com.lms.WebService.Constant_Tag;
import com.lms.WebService.RestApi;
import com.lms.WebService.RestClient;
import com.lms.databinding.FragmentMenuBinding;
import com.lms.utility.AlertClass;
import com.lms.utility.Connectivity;
import com.lms.utility.FragmentTask;
import com.lms.utility.ProgressDialog;
import com.lms.utility.SharePrefs;
import com.lms.utility.ThemeClass;
import com.lms.utility.Utils;

import java.util.HashMap;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MoreFragment extends Fragment {
    private FragmentMenuBinding mbinding;

    private String TAG = MoreFragment.class.getSimpleName();

    public static MoreFragment newInstance() {
        MoreFragment fragment = new MoreFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false);
        View rootView = mbinding.getRoot();


        TextView tv_header=(TextView) mbinding.headerview.findViewById(R.id.tv_header);


        ThemeClass.changeHeaderColor(mbinding.headerview,getActivity());

        tv_header.setText(getString(R.string.account));
        LinearLayout ll_back=(LinearLayout) mbinding.headerview.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();


            }
        });
        mbinding.tvVersion.setText("App version : "+SharePrefs.getSetting(getActivity()).getVersion());


        mbinding.tvmobileno.setText(SharePrefs.getUserdetail(getActivity()).getUser().getPhoneNo());

        mbinding.tvemailid.setText(SharePrefs.getUserdetail(getActivity()).getUser().getEmailAddress());

        ll_back.setVisibility(View.GONE);


        mbinding.rlUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTask.replaceFrgament(ProfileFragment.newInstance(), getActivity().getSupportFragmentManager(), R.id.main_framelayout);

            }
        });

        Glide.with(getActivity())
                .load(SharePrefs.getUserdetail(getActivity()).getUser().getProfilePhoto()).dontAnimate().
                placeholder(R.drawable.placeholder)
                .into(mbinding.profileImage);


        mbinding.llPrivacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myIntent = new Intent(getActivity(), WebViewer.class);
                    myIntent.putExtra("data",SharePrefs.getSetting(getActivity()).getPrivacyPolicy());
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No application can handle this request."
                            + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });
        mbinding.llSupporthelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myIntent = new Intent(getActivity(), WebViewer.class);
                    myIntent.putExtra("data",SharePrefs.getSetting(getActivity()).getSupportContent());
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No application can handle this request."
                            + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });



        mbinding.llTermcondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myIntent = new Intent(getActivity(), WebViewer.class);
                    myIntent.putExtra("data",SharePrefs.getSetting(getActivity()).getTermsAndConditions());
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No application can handle this request."
                            + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });


        mbinding.llwebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myIntent = new Intent(getActivity(), WebViewer.class);
                    myIntent.putExtra("url",SharePrefs.getSetting(getActivity()).getWebsiteLink());
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No application can handle this request."
                            + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        mbinding.llchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTask.replaceFrgament(new ChangePasswordFragment(), getActivity().getSupportFragmentManager(), R.id.main_framelayout);

            }
        });

        mbinding.llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Connectivity.isConnected(getActivity())) {


                    if(SharePrefs.getUserdetail(getActivity())!=null){
                    AlertClass.BaseAlert_yesNo(getActivity(),SharePrefs.getSetting(getActivity()).getOrganizationName(),
                        getString(R.string.logout_message), getString(R.string.yes), getString(R.string.no), true, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();

                            Utils.hideKeyboard(getActivity());
                            SharePrefs.clearUserdetail(getActivity());
                            startActivity(new Intent(getActivity(), LoginActivity.class));
                            getActivity().finish();

                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    }else{
                        startActivity(new Intent(getActivity(), LoginActivity.class));
                        getActivity().finish();
                    }

                } else {

                    //show net not connected error
                    Toasty.warning(getActivity(), getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

                }
            }
        });


        mbinding.tvUsername.setText(""+SharePrefs.getUserdetail(getActivity()).getUser().getFullName());

        return rootView;
    }



}