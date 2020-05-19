package com.lms.View;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.lms.Adapter.SearchAdapter;
import com.lms.Adapter.VideoListAdapter;
import com.lms.GsonModel.TopicModel.Data;
import com.lms.GsonModel.TopicModel.LessionModel;
import com.lms.GsonModel.TopicModel.TopicDatum;
import com.lms.GsonModel.coursedetail.SubjectDatum;
import com.lms.R;
import com.lms.WebService.Constant_Tag;
import com.lms.WebService.RestApi;
import com.lms.WebService.RestClient;
import com.lms.databinding.FragmentVideolistBinding;
import com.lms.interfaces.ChildModel;
import com.lms.interfaces.ListItem1;
import com.lms.interfaces.OnLessionListener;
import com.lms.interfaces.OnclickListener;
import com.lms.interfaces.SearchChildModel;
import com.lms.interfaces.SearchHeaderModel;
import com.lms.utility.Connectivity;
import com.lms.utility.FragmentTask;
import com.lms.utility.GridSpacingItemDecoration;
import com.lms.utility.ProgressDialog;
import com.lms.utility.ThemeClass;
import com.lms.utility.Utils;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CoursevideolistFragment extends Fragment implements OnLessionListener {
    private FragmentVideolistBinding mbinding;
    private String TAG= CoursevideolistFragment.class.getSimpleName();
    SubjectDatum mSubjectDatum;
    List<TopicDatum> Topiclist;
    Data mLessionModel;
    VideoListAdapter mvidelistadapter;
    private ArrayList<ListItem1> listItemArrayList=new ArrayList<>();
    TextView tv_header;
    int buystatus;
    public static CoursevideolistFragment newInstance(SubjectDatum mSubjectDatum,int buystatus) {
        CoursevideolistFragment fragment = new CoursevideolistFragment();
        Bundle b=new Bundle();
        b.putParcelable(Constant_Tag.COURSEDATUM,mSubjectDatum);
        b.putInt("buystatus",buystatus);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b=getArguments();

        if(b!=null){
            mSubjectDatum= b.getParcelable(Constant_Tag.COURSEDATUM);
            buystatus=b.getInt("buystatus");
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_videolist, container, false);
        View rootView = mbinding.getRoot();
        ThemeClass.changeHeaderColor(mbinding.llHeader,getActivity());

        mbinding.tvSubjectname.setText(mSubjectDatum.getName());
         tv_header=(TextView) mbinding.llHeader.findViewById(R.id.tv_header);
        LinearLayout ll_back=(LinearLayout) mbinding.llHeader.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();


            }
        });
        ll_back.setVisibility(View.VISIBLE);

        if(mSubjectDatum.getDescription()!=null){

            mbinding.wvDesciption.loadData(mSubjectDatum.getDescription(), "text/html; charset=UTF-8", null);


        }


        mbinding.rvItem.setHasFixedSize(true);
        mbinding.rvItem.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        //mbinding.rvItem.addItemDecoration(new GridSpacingItemDecoration(1,spacingInPixels,false));




        mbinding.tvSubjectname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mbinding.wvDesciption.getVisibility()==View.VISIBLE){
                    mbinding.tvSubjectname.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
                    mbinding.wvDesciption.setVisibility(View.GONE);
                }else{
                    mbinding.tvSubjectname.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0);

                    mbinding.wvDesciption.setVisibility(View.VISIBLE);

                }
            }
        });


        if (Connectivity.isConnected(getActivity())) {
            callCourseLessionList(getActivity());
        } else {
            //show net not connected error
            Toasty.warning(getActivity(), getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();
        }
        return rootView;
    }

    SearchAdapter mSearchAdapter;
    private void populateList(){

        listItemArrayList.clear();
        for(int i = 0; i < Topiclist.size(); i++){

            SearchHeaderModel vehicleModel = new SearchHeaderModel();
            vehicleModel.setheader(Topiclist.get(i));
            listItemArrayList.add(vehicleModel);
               if(Topiclist.get(i).getCourseContentList()!=null)
                for(int k=0;k<Topiclist.get(i).getCourseContentList().size();k++) {
                    SearchChildModel childModel = new SearchChildModel();
                    Topiclist.get(i).getCourseContentList().get(k).setShowchild(true);
                    Topiclist.get(i).getCourseContentList().get(k).setTopicName(Topiclist.get(i).getName());
                    childModel.setChild(Topiclist.get(i).getCourseContentList().get(k));
                    listItemArrayList.add(childModel);
                }
        }
         mSearchAdapter = new SearchAdapter(getActivity(),listItemArrayList,CoursevideolistFragment.this,buystatus);
        mbinding.rvItem.setAdapter(mSearchAdapter);
        Utils.hideKeyboard(getActivity());
    }

    private void callCourseLessionList(Context context) {

        ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);

        Observable<LessionModel> mdata = mRestApi.Topiclist(mSubjectDatum.getId());
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LessionModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(LessionModel value) {
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {
                            if(value.getData()!=null && value.getData().getTopicData()!=null){
                                mLessionModel=value.getData();

                                Topiclist=value.getData().getTopicData();
                                populateList();
                                tv_header.setText(mLessionModel.getCourseName()+" "+getString(R.string.course));

                            }


                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
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




    @Override
    public void OnHeaderclick(int pos) {
        if(listItemArrayList!=null && listItemArrayList.size()>pos){
            Log.e("data",listItemArrayList.get(pos).getHeaderData().getName());
            if(listItemArrayList.get(pos).getHeaderData().isShowchild()){
                listItemArrayList.get(pos).getHeaderData().setShowchild(false);

                if(pos+1<listItemArrayList.size())
                for(int k=pos+1; listItemArrayList.get(k) instanceof SearchChildModel;k++) {

                        listItemArrayList.get(k).getChildData().setShowchild(false);
                    if(k+1==listItemArrayList.size()){
                        break;
                    }
                }

            }else{
                listItemArrayList.get(pos).getHeaderData().setShowchild(true);

                if(pos+1<listItemArrayList.size())
                    for(int k=pos+1; (listItemArrayList.get(k) instanceof SearchChildModel && k<listItemArrayList.size()) ;k++) {
                            listItemArrayList.get(k).getChildData().setShowchild(true);

                        if(k+1==listItemArrayList.size()){
                            break;
                        }
                    }
            }

            mSearchAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int pos) {

        if(listItemArrayList!=null && listItemArrayList.size()>pos){
            listItemArrayList.get(pos).getChildData().setSubjectName(mSubjectDatum.getName());

            listItemArrayList.get(pos).getChildData().setCourseName(mLessionModel.getCourseName());
            FragmentTask.replaceFrgament(LessionTopicFragment.newInstance(listItemArrayList.get(pos).getChildData(),buystatus), getActivity().getSupportFragmentManager(), R.id.main_framelayout);


        }

    }



}