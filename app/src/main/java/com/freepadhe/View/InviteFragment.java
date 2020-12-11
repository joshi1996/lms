package com.freepadhe.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.freepadhe.BuildConfig;
import com.freepadhe.R;
import com.freepadhe.databinding.FragmentCourselistBinding;
import com.freepadhe.databinding.FragmentInviteBinding;
import com.freepadhe.utility.SharePrefs;
import com.freepadhe.utility.ThemeClass;

public class InviteFragment extends Fragment  {
    private FragmentInviteBinding mbinding;
    private String TAG= InviteFragment.class.getSimpleName();
    public static InviteFragment newInstance() {
        InviteFragment fragment = new InviteFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_invite, container, false);
        View rootView = mbinding.getRoot();
        ThemeClass.changeHeaderColor(mbinding.llHeader,getActivity());
        TextView tv_header=(TextView) mbinding.llHeader.findViewById(R.id.tv_header);
        tv_header.setText(getString(R.string.inviteandearn));
        LinearLayout ll_back=(LinearLayout) mbinding.llHeader.findViewById(R.id.ll_back);
        ll_back.setVisibility(View.GONE);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        ll_back.setVisibility(View.GONE);

       /* Glide.with(getActivity())
                .load(SharePrefs.getSetting(getActivity()).getLogo())
                .placeholder(R.drawable.placeholder).into(mbinding.ivOrgnizationname);*/

        ThemeClass.changeButtonColor(mbinding.btnInvite,getActivity());

        mbinding.tvWelcome.setText("Hello "+SharePrefs.getUserdetail(getActivity()).getUser().getFullName()+" your code is "+SharePrefs.getUserdetail(getActivity()).getUser().getUserCode()+" code to invite your friend & students to join "+SharePrefs.getSetting(getActivity()).getOrganizationName()+" and earn  money Rs "+SharePrefs.getSetting(getActivity()).getEarnAmount()+"/- on every "+ SharePrefs.getSetting(getActivity()).getFriendCountForEarn()+" student to join paid course.");


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

        return rootView;
    }










}