package com.lms.View;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lms.GsonModel.BasicModel;
import com.lms.R;
import com.lms.WebService.Constant_Tag;
import com.lms.WebService.RestApi;
import com.lms.WebService.RestClient;
import com.lms.databinding.FragmentChangepasswordBinding;
import com.lms.utility.Connectivity;
import com.lms.utility.ProgressDialog;
import com.lms.utility.SharePrefs;
import com.lms.utility.ThemeClass;
import com.lms.utility.Utils;

import java.util.HashMap;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ChangePasswordFragment extends Fragment {
    private FragmentChangepasswordBinding mbinding;
    private String TAG= ChangePasswordFragment.class.getSimpleName();

    public static ChangePasswordFragment newInstance() {
        ChangePasswordFragment fragment = new ChangePasswordFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_changepassword, container, false);
        View rootView = mbinding.getRoot();

        ThemeClass.changeHeaderColor(mbinding.headerview,getActivity());
        ThemeClass.changeButtonColor(mbinding.btnSubmit,getActivity());


        TextView tv_header=(TextView) mbinding.headerview.findViewById(R.id.tv_header);

        tv_header.setText(getString(R.string.changepassword));
        LinearLayout ll_back=(LinearLayout) mbinding.headerview.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();


            }
        });

        mbinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if (mbinding.inpassword.getText().toString().trim().length() == 0) {
                    Toasty.warning(getActivity(), getString(R.string.blank_password), Toast.LENGTH_SHORT).show();
                }
                else if (mbinding.inpassword.getText().toString().trim().length() < 8) {
                    Toasty.warning(getActivity(), getString(R.string.invalid_password), Toast.LENGTH_SHORT).show();
                }
                else if (mbinding.innewpassword.getText().toString().trim().length() == 0) {
                    Toasty.warning(getActivity(), getString(R.string.blank_newpassword), Toast.LENGTH_SHORT).show();
                }
                else if (mbinding.innewpassword.getText().toString().trim().length() < 8) {
                    Toasty.warning(getActivity(), getString(R.string.invalid_newpassword), Toast.LENGTH_SHORT).show();
                }
                else if (mbinding.inconfirmpassword.getText().toString().trim().length() == 0) {
                    Toasty.warning(getActivity(), getString(R.string.blank_confirmpassword), Toast.LENGTH_SHORT).show();
                }
                else if (!mbinding.innewpassword.getText().toString().trim().equalsIgnoreCase(mbinding.inconfirmpassword.getText().toString().trim())) {
                    Toasty.warning(getActivity(), getString(R.string.newpassword_notmatched), Toast.LENGTH_SHORT).show();
                }
                else {
                    if (Connectivity.isConnected(getActivity())) {
                            CallUpdateApi(getActivity());
                    } else {
                       //show net not connected error
                        Toasty.warning(getActivity(), getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        return rootView;
    }






    private void CallUpdateApi(Context mcontext) {
        String current_password = mbinding.inpassword.getText().toString().trim();
        String new_password = mbinding.innewpassword.getText().toString().trim();

        ProgressDialog.showDialog(mcontext);
        RestApi mRestApi = RestClient.getClient(mcontext).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();


        mparam.put(Constant_Tag.OLDPASSWORD, current_password);
        mparam.put(Constant_Tag.newpassword, new_password);


        Observable<BasicModel> mdata = mRestApi.changePassword(mparam);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BasicModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BasicModel value) {
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {
                            Utils.hideKeyboard(getActivity());

                            Toasty.success(getActivity(), value.getMessage(), Toast.LENGTH_SHORT).show();
                            mbinding.innewpassword.getText().clear();
                            mbinding.inpassword.getText().clear();
                            mbinding.inconfirmpassword.getText().clear();

                        } else if (value != null) {
                            Toasty.error(getActivity(), value.getMessage(), Toast.LENGTH_SHORT).show();

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









}