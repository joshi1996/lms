package com.lms.View;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;
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
import com.lms.Adapter.CourselistAdapter;
import com.lms.Adapter.UserCourseAdapter;
import com.lms.GsonModel.CourelistModel;
import com.lms.GsonModel.CourseDatum;
import com.lms.GsonModel.UserCourse.Datum;
import com.lms.GsonModel.UserCourse.UsercourseModel;
import com.lms.R;
import com.lms.WebService.Constant_Tag;
import com.lms.WebService.RestApi;
import com.lms.WebService.RestClient;
import com.lms.databinding.FragmentClassroomBinding;
import com.lms.databinding.FragmentCourselistBinding;
import com.lms.interfaces.OnclickListener;
import com.lms.utility.Connectivity;
import com.lms.utility.FragmentTask;
import com.lms.utility.GridSpacingItemDecoration;
import com.lms.utility.ProgressDialog;
import com.lms.utility.SharePrefs;
import com.lms.utility.ThemeClass;
import com.lms.utility.Utils;

import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClassRoomFragment extends Fragment implements OnclickListener{
    private FragmentClassroomBinding mbinding;
    private String TAG = ClassRoomFragment.class.getSimpleName();
    List<Datum> mlist;
    UserCourseAdapter adapter;

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
        ThemeClass.changeButtonColor(mbinding.btnTakecourse,getActivity());

        ThemeClass.setAdvertisment(mbinding.ivClassroomAdvertisment,getActivity(),"Class Room Page - No Courses", Utils.getDeviceWidth(getActivity()),(int)getResources().getDimension(R.dimen.size_200));


        Glide.with(getActivity())
                .load(SharePrefs.getSetting(getActivity()).getLogo())
        .placeholder(R.drawable.placeholder).into(mbinding.tvOrgnizationname);


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
        mbinding.rvItem.addItemDecoration(new GridSpacingItemDecoration(2,spacingInPixels,false));

        mbinding.tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbinding.tvAll.setText(Html.fromHtml("<B><U>"+getString(R.string.all)+"</U></B>"));
                mbinding.tvRunning.setText(Html.fromHtml(getString(R.string.running)));
                mbinding.tvCompleted.setText(Html.fromHtml(getString(R.string.completed)));
                if(adapter!=null){
                    adapter.getFilter().filter(getString(R.string.all));
                }
                Utils.hideKeyboard(getActivity());
            }
        });

        mbinding.tvRunning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbinding.tvRunning.setText(Html.fromHtml("<B><U>"+getString(R.string.running)+"</U></B>"));
                mbinding.tvAll.setText(Html.fromHtml(getString(R.string.all)));
                mbinding.tvCompleted.setText(Html.fromHtml(getString(R.string.completed)));
                if(adapter!=null){
                    adapter.getFilter().filter(getString(R.string.running));
                }
                Utils.hideKeyboard(getActivity());

            }
        });

        mbinding.tvCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mbinding.tvCompleted.setText(Html.fromHtml("<B><U>"+getString(R.string.completed)+"</U></B>"));
                mbinding.tvRunning.setText(Html.fromHtml(getString(R.string.running)));
                mbinding.tvAll.setText(Html.fromHtml(getString(R.string.all)));
                if(adapter!=null){
                    adapter.getFilter().filter(getString(R.string.completed));
                }
                Utils.hideKeyboard(getActivity());

            }
        });







        showAlertDialog();




        mbinding.btnTakecourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()). mainActivityBinding.navigation.setSelectedItemId(R.id.navigation_course);
            }
        });


        if (Connectivity.isConnected(getActivity())) {

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
        mparam.put(Constant_Tag.USERID, SharePrefs.getUserdetail(getActivity()).getUser().getId().toString());

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
                           if(value!=null)
                            Utils.Relogin(value.getStatus(),getActivity());

                        if (value != null && value.getStatus() == 200) {
                            mlist=value.getData();
                            if(mlist!=null){
                            adapter=new UserCourseAdapter(getActivity(),mlist, ClassRoomFragment.this);

                            mbinding.rvItem.setAdapter(adapter);
                            }
                            mbinding.rvItem.setVisibility(View.VISIBLE);
                            mbinding.llfilter.setVisibility(View.VISIBLE);
                            mbinding.tvAll.setText(Html.fromHtml("<B><U>"+getString(R.string.all)+"</U></B>"));
                            mbinding.llnocourse.setVisibility(View.GONE);
                            mbinding.ivClassroomAdvertisment.setVisibility(View.GONE);
                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(getActivity(), value.getMessage(), Toast.LENGTH_SHORT).show();
                            mbinding.llnocourse.setVisibility(View.VISIBLE);
                            mbinding.rvItem.setVisibility(View.GONE);
                            mbinding.ivClassroomAdvertisment.setVisibility(View.VISIBLE);

                            mbinding.llfilter.setVisibility(View.GONE);

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


    public void showAlertDialog() {        // create an alert builder
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

        boolean isshow= ThemeClass.setAdvertisment(ivaddvertisment, getActivity(), "Class Room - Popup Ads", (int) (displayRectangle.width() * 0.9f),(int) (displayRectangle.width() * 0.9f));


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

        if(isshow==false){
            mbinding.cardview.setVisibility(View.GONE);
        }
        else
        dialog.show();
    }


    @Override
    public void OnItemclick(int pos) {
        if(mlist!=null && mlist.size()>pos){
            FragmentTask.replaceFrgament(CoursesubFragment.newInstance(mlist.get(pos)), getActivity().getSupportFragmentManager(), R.id.main_framelayout);
        }
    }

    @Override
    public void OnDesItemclick(int pos) {

    }
}