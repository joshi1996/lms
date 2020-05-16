package com.lms.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.bumptech.glide.Glide;
import com.lms.Adapter.CoursesublistAdapter;
import com.lms.GsonModel.CourseContentList;
import com.lms.GsonModel.CourseDatum;
import com.lms.GsonModel.coursedetail.CouresubModel;
import com.lms.R;
import com.lms.WebService.Constant_Tag;
import com.lms.WebService.RestApi;
import com.lms.WebService.RestClient;
import com.lms.databinding.FragmentCoursesubBinding;
import com.lms.databinding.FragmentLessionBinding;
import com.lms.interfaces.OnclickListener;
import com.lms.utility.AlertClass;
import com.lms.utility.Connectivity;
import com.lms.utility.FragmentTask;
import com.lms.utility.GridSpacingItemDecoration;
import com.lms.utility.ProgressDialog;
import com.lms.utility.ThemeClass;
import com.lms.utility.Utils;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LessionTopicFragment extends Fragment {
    private FragmentLessionBinding mbinding;
    private String TAG= LessionTopicFragment.class.getSimpleName();
    CourseContentList mCourseContentList;
    int buystatus;

    public static LessionTopicFragment newInstance(CourseContentList mCourseContentList,int buystatus) {
        LessionTopicFragment fragment = new LessionTopicFragment();
        Bundle b=new Bundle();
        b.putParcelable(Constant_Tag.COURSEDATUM,mCourseContentList);
        b.putInt("buystatus",buystatus);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b=getArguments();

        if(b!=null){
            mCourseContentList= b.getParcelable(Constant_Tag.COURSEDATUM);
            buystatus=b.getInt("buystatus");


        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_lession, container, false);
        View rootView = mbinding.getRoot();
        ThemeClass.changeHeaderColor(mbinding.llHeader,getActivity());

        TextView tv_header=(TextView) mbinding.llHeader.findViewById(R.id.tv_header);
        tv_header.setText(getString(R.string.coursedetail));
        LinearLayout ll_back=(LinearLayout) mbinding.llHeader.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();


            }
        });
        ll_back.setVisibility(View.VISIBLE);




        if(mCourseContentList!=null){
            mbinding.tvSubject.setText(mCourseContentList.getSubjectName()+" | "+mCourseContentList.getTopicName());
             Glide.with(getActivity()).load(mCourseContentList.getPhoto())
                    .placeholder(R.drawable.placeholder).dontAnimate()
                    .into(mbinding.ivCover);
            mbinding.tvTitle.setText(mCourseContentList.getTitle());

            tv_header.setText(mCourseContentList.getCourseName()+" "+getString(R.string.course));
           mbinding.tvDesciption.setText(mCourseContentList.getDescription());

           if(mCourseContentList.getContentType().equalsIgnoreCase("video")){
               mbinding.ivPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
           }
           else  if(mCourseContentList.getContentType().equalsIgnoreCase("pdf")){
               mbinding.ivPlay.setImageDrawable(getResources().getDrawable(R.drawable.ic_pdf));
           }
          if(!mCourseContentList.getContentType().equalsIgnoreCase("pdf")){
            if(mCourseContentList.getPdf()!=null && mCourseContentList.getPdf().length()>0){
                mbinding.ivPdfdownload.setVisibility(View.VISIBLE);
            }else{
                mbinding.ivPdfdownload.setVisibility(View.GONE);
            }
          }else{
              mbinding.ivPdfdownload.setVisibility(View.GONE);

          }

            mbinding.ivPdfdownload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(buystatus==1){

                        Intent i= new Intent(getActivity(),PdFViewer.class);
                        i.putExtra("videourl",mCourseContentList.getPdf());
                        i.putExtra("buystatus",buystatus);
                        startActivity(i);
                    }
                    else{

                        AlertClass.BaseAlert_done(getActivity(), "please buy this course to show pdf", getString(R.string.done), getString(R.string.no), false, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();

                                Utils.hideKeyboard(getActivity());

                            }
                        }, null);
                    }


                }
            });
        }






        mbinding.ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCourseContentList.getContentType().equalsIgnoreCase("video")){
                    Intent i= new Intent(getActivity(),VideoPlayer.class);
                    Bundle b=new Bundle();
                    b.putString("videourl",mCourseContentList.getVideo());
                    b.putParcelable("data",mCourseContentList);
                    b.putInt("buystatus",buystatus);
                    i.putExtras(b);
                    if(mCourseContentList.getVideo()!=null && mCourseContentList.getVideo().length()>0)
                    startActivity(i);

                }
                else  if(mCourseContentList.getContentType().equalsIgnoreCase("pdf")){
                    Intent i= new Intent(getActivity(),PdFViewer.class);
                    i.putExtra("videourl",mCourseContentList.getPdf());
                    i.putExtra("buystatus",buystatus);
                    if(mCourseContentList.getPdf()!=null && mCourseContentList.getPdf().length()>0 ){
                     if(buystatus==1)
                    startActivity(i);
                     else{

                         AlertClass.BaseAlert_done(getActivity(), "please buy this course to show pdf", getString(R.string.done), getString(R.string.no), false, new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 dialogInterface.dismiss();

                                 Utils.hideKeyboard(getActivity());

                             }
                         }, null);
                     }
                    }

                }

            }
        });


        return rootView;
    }











}