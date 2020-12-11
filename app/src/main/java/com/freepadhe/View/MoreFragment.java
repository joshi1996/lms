package com.freepadhe.View;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.freepadhe.R;
import com.freepadhe.databinding.FragmentMenuBinding;
import com.freepadhe.utility.AlertClass;
import com.freepadhe.utility.Connectivity;
import com.freepadhe.utility.FragmentTask;
import com.freepadhe.utility.SharePrefs;
import com.freepadhe.utility.ThemeClass;
import com.freepadhe.utility.Utils;

import es.dmoral.toasty.Toasty;

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

        //Toast.makeText(getActivity(), ""+SharePrefs.getUserdetail(getActivity()).getUser().getEmailAddress(), Toast.LENGTH_SHORT).show();

        ll_back.setVisibility(View.GONE);


        mbinding.rlUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTask.replaceFrgament(ProfileFragment.newInstance(), getActivity().getSupportFragmentManager(), R.id.main_framelayout);

            }
        });

        mbinding.llTransactionhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTask.replaceFrgament(TransactionFragment.newInstance(), getActivity().getSupportFragmentManager(), R.id.main_framelayout);

            }
        });

        mbinding.llEarning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTask.replaceFrgament(EaringFragment.newInstance(), getActivity().getSupportFragmentManager(), R.id.main_framelayout);

            }
        });

        Glide.with(getActivity())
                .load(SharePrefs.getUserdetail(getActivity()).getUser().getProfilePhoto()).dontAnimate().
                placeholder(R.drawable.appstore)
                .into(mbinding.profileImage);


        mbinding.llPrivacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myIntent = new Intent(getActivity(), PrivacyPolicy.class);
                    //myIntent.putExtra("data",SharePrefs.getSetting(getActivity()).getPrivacyPolicy());
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No application can handle this request."
                            + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        mbinding.llAboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myIntent = new Intent(getActivity(), AboutUsActivity.class);
                    //myIntent.putExtra("data",SharePrefs.getSetting(getActivity()).getPrivacyPolicy());
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getActivity(), "No application can handle this request."
                            + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }
        });

        /*mbinding.llLiveClasses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LiveClassActivity.class);
                startActivity(intent);
            }
        });*/

        mbinding.llSupporthelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent myIntent = new Intent(getActivity(), SupportActivity.class);
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
                    Intent myIntent = new Intent(getActivity(), TermsAndCondition.class);
                    //myIntent.putExtra("data",SharePrefs.getSetting(getActivity()).getTermsAndConditions());
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

        mbinding.llInvitelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTask.replaceFrgament(new InviteListFragment(), getActivity().getSupportFragmentManager(), R.id.main_framelayout);

            }
        });  mbinding.llchangepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTask.replaceFrgament(new ChangePasswordFragment(), getActivity().getSupportFragmentManager(), R.id.main_framelayout);

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