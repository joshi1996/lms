package com.freepadhe.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.freepadhe.Adapter.UserCourseAdapter;
import com.freepadhe.GsonModel.Advertisment.AdvertismentModel;
import com.freepadhe.GsonModel.UserCourse.Datum;
import com.freepadhe.GsonModel.UserCourse.UsercourseModel;
import com.freepadhe.R;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.FragmentClassroomBinding;
import com.freepadhe.databinding.FragmentCourselistBinding;
import com.freepadhe.interfaces.OnclickListener;
import com.freepadhe.utility.Connectivity;
import com.freepadhe.utility.FragmentTask;
import com.freepadhe.utility.GridSpacingItemDecoration;
import com.freepadhe.utility.ProgressDialog;
import com.freepadhe.utility.SharePrefs;
import com.freepadhe.utility.ThemeClass;
import com.freepadhe.utility.Utils;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClassRoomFragment extends Fragment implements OnclickListener {
    private FragmentClassroomBinding mbinding;
    private String TAG = ClassRoomFragment.class.getSimpleName();
    List<Datum> mlist;
    UserCourseAdapter adapter;

    String classUrl;

    public static ClassRoomFragment newInstance() {
        ClassRoomFragment fragment = new ClassRoomFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_classroom, container, false);
        View rootView = mbinding.getRoot();

        ThemeClass.changeHeaderColor(mbinding.llHeader, getActivity());
        ThemeClass.changeButtonColor(mbinding.btnTakecourse, getActivity());


        //ThemeClass.setAdvertisment(mbinding.ivAdverticementClassroom, getActivity(), "Class Room Page - No Courses", Utils.getDeviceWidth(getActivity()), (int) getResources().getDimension(R.dimen.size_200));


        Glide.with(getActivity())
                .load(SharePrefs.getSetting(getActivity()).getLogo())
                .placeholder(R.drawable.placeholder_banner).into(mbinding.tvOrgnizationname);


        TextView tv_header = (TextView) mbinding.llHeader.findViewById(R.id.tv_header);
        tv_header.setText(getString(R.string.classroom));
        LinearLayout ll_back = (LinearLayout) mbinding.llHeader.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        ll_back.setVisibility(View.GONE);


        mbinding.tvWelcome.setText(SharePrefs.getSetting(getActivity()).getSlogan());

        mbinding.rvItem.setHasFixedSize(true);
        mbinding.rvItem.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_15);
        mbinding.rvItem.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPixels, false));


        mbinding.btnTakecourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).mainActivityBinding.navigation.setSelectedItemId(R.id.navigation_course);
            }
        });

        mbinding.ivAdverticementClassroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent google=new Intent(Intent.ACTION_VIEW);
                google.setData(Uri.parse(classUrl));
                startActivity(google);
            }
        });


        if (Connectivity.isConnected(getActivity())) {
            callAdvertisementApiDialog(getContext());
            callCourseList(getActivity());
        } else {

            //show net not connected error
            Toasty.warning(getActivity(), getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

        }


        return rootView;
    }


    private void callCourseList(Context context) {

        //ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();
        mparam.put("userId", SharePrefs.getUserdetail(getActivity()).getUser().getId().toString());
        //Toast.makeText(context, ""+SharePrefs.getUserdetail(getActivity()).getUser().getId().toString(), Toast.LENGTH_SHORT).show();

        Observable<UsercourseModel> mdata = mRestApi.UsercourseList(mparam);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UsercourseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(UsercourseModel value) {
                        ProgressDialog.hideDialog();
                        if (value != null)
                            Utils.Relogin(value.getStatus(), getActivity());

                        if (value != null && value.getStatus() == 200) {
                            mlist = value.getData();
                            if (mlist != null) {
                                adapter = new UserCourseAdapter(getActivity(), mlist, ClassRoomFragment.this);
                                mbinding.rvItem.setAdapter(adapter);
                            }
                            callAdvertismentApi(getActivity());
                            mbinding.rvItem.setVisibility(View.VISIBLE);
                            mbinding.llnocourse.setVisibility(View.GONE);
                            mbinding.ivAdverticementClassroom.setVisibility(View.VISIBLE);
                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(getActivity(), value.getMessage(), Toast.LENGTH_SHORT).show();
                            mbinding.llnocourse.setVisibility(View.VISIBLE);
                            mbinding.rvItem.setVisibility(View.GONE);
                            mbinding.ivAdverticementClassroom.setVisibility(View.VISIBLE);
                            //mbinding.llfilter.setVisibility(View.GONE);
                        }

                        else if (value.getStatus() == 400)
                        {
                            Toast.makeText(getActivity(), "Your Session Expired, Please LogIn first...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(),LoginActivity.class);
                            startActivity(intent);
                            getActivity().finish();
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


    /*public void showAlertDialog() {        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_advertisment, null);
        builder.setView(customLayout);
        final AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        ImageView ivaddvertisment = (ImageView) customLayout.findViewById(R.id.iv_advertisment);
        ImageView ivclose = (ImageView) customLayout.findViewById(R.id.ivclose);
        RelativeLayout rlmain = (RelativeLayout) customLayout.findViewById(R.id.rlmain);
        CardView mCardView = (CardView) customLayout.findViewById(R.id.cardview);
        LinearLayout llclose = (LinearLayout) customLayout.findViewById(R.id.llclose);

        Picasso.get().load(SharePrefs.getAdvertisment(getActivity()).getData().get(0).getImage());

        boolean isshow = ThemeClass.setAdvertisment(ivaddvertisment, getActivity(), "Class Room - Popup Ads", (int) (displayRectangle.width() * 0.9f), (int) (displayRectangle.width() * 0.9f));


        ivaddvertisment.getLayoutParams().width = (int) (displayRectangle.width() * 0.9f);
        ivaddvertisment.getLayoutParams().height = (int) (displayRectangle.width() * 0.9f);

        ivaddvertisment.requestLayout();

        mCardView.getLayoutParams().width = (int) (displayRectangle.width() * 0.9f);
        mCardView.getLayoutParams().height = (int) (displayRectangle.width() * 0.9f);

        mCardView.requestLayout();

        llclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (isshow == false) {
            mbinding.cardview.setVisibility(View.GONE);
        } else
            dialog.show();
    }*/


    @Override
    public void OnItemclick(int pos) {
        if (mlist != null && mlist.size() > pos) {
            FragmentTask.replaceFrgament(CoursesubFragment.newInstance(mlist.get(pos)), getActivity().getSupportFragmentManager(), R.id.main_framelayout);
        }
    }

    @Override
    public void OnDesItemclick(int pos) {

    }

    private void callAdvertismentApi(Context context) {

        ProgressDialog.showDialog(getActivity());

        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);

        Observable<AdvertismentModel> mdata = mRestApi.advertisement();
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AdvertismentModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(AdvertismentModel value) {

                        if (value != null && value.getStatus() == 200) {
                            Utils.hideKeyboard(getActivity());
                            ProgressDialog.hideDialog();
                            if (value.getData().get(0).getId() == "5f9d2baef701c65e7f707f12" || value.getData().get(0).getStatus() == 1) {
                                mbinding.cardview.setVisibility(View.VISIBLE);
                                classUrl = value.getData().get(0).getURL();
                                Picasso.get().load(value.getData().get(0).getImage()).into(mbinding.ivAdverticementClassroom);
                            } else {
                                Toast.makeText(getActivity(), "No Such Advertisement", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            ProgressDialog.hideDialog();
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(getActivity(), value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        ProgressDialog.hideDialog();
                        Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "onError: " + e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });
    }

    private void callAdvertisementApiDialog(Context context) {

        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);

        Observable<AdvertismentModel> mdata = mRestApi.advertisement();
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AdvertismentModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(AdvertismentModel value) {
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {
                            Utils.hideKeyboard(getActivity());

                            if (value.getData().get(1).getId() == "5f9d2bf2f701c65e7f707f13" || value.getData().get(1).getStatus() == 1) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                Log.d("Ads",value.getData().get(1).getPage());
                                Rect displayRectangle = new Rect();
                                Window window = getActivity().getWindow();
                                window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
                                final View customLayout = getLayoutInflater().inflate(R.layout.dialog_advertisment, null);
                                builder.setView(customLayout);
                                final AlertDialog dialog = builder.create();
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                                ImageView ivaddvertisment = (ImageView) customLayout.findViewById(R.id.iv_advertisment);
                                ImageView ivclose = (ImageView) customLayout.findViewById(R.id.ivclose);
                                RelativeLayout rlmain = (RelativeLayout) customLayout.findViewById(R.id.rlmain);
                                CardView mCardView = (CardView) customLayout.findViewById(R.id.cardview);
                                LinearLayout llclose = (LinearLayout) customLayout.findViewById(R.id.llclose);

                                Picasso.get().load(value.getData().get(1).getImage()).into(ivaddvertisment);

                                //boolean isshow = ThemeClass.setAdvertisment(ivaddvertisment, getActivity(), "Class Room - Popup Ads", (int) (displayRectangle.width() * 0.9f), (int) (displayRectangle.width() * 0.9f));


                                ivaddvertisment.getLayoutParams().width = (int) (displayRectangle.width() * 0.9f);
                                ivaddvertisment.getLayoutParams().height = (int) (displayRectangle.width() * 0.9f);

                                ivaddvertisment.requestLayout();

                                mCardView.getLayoutParams().width = (int) (displayRectangle.width() * 0.9f);
                                mCardView.getLayoutParams().height = (int) (displayRectangle.width() * 0.9f);

                                mCardView.requestLayout();

                                llclose.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        dialog.dismiss();
                                    }
                                });

                                dialog.show();

                                /*if (isshow == false) {
                                    mbinding.cardview.setVisibility(View.GONE);
                                } else
                                    dialog.show();*/
                            } else {
                                Toast.makeText(getActivity(), "No Such Advertisement", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(getActivity(), value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: " + e.getMessage());
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