package com.lms.View;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import com.lms.Adapter.CourselistAdapter;
import com.lms.Adapter.FilterSpinnerAdapter;
import com.lms.GsonModel.CourelistModel;
import com.lms.GsonModel.CourseDatum;
import com.lms.R;
import com.lms.WebService.Constant_Tag;
import com.lms.WebService.RestApi;
import com.lms.WebService.RestClient;
import com.lms.databinding.FragmentCourselistBinding;
import com.lms.interfaces.OnclickListener;
import com.lms.utility.Connectivity;
import com.lms.utility.FragmentTask;
import com.lms.utility.GridSpacingItemDecoration;
import com.lms.utility.ProgressDialog;
import com.lms.utility.ThemeClass;
import com.lms.utility.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.internal.Util;

public class CourselistFragment extends Fragment implements OnclickListener  {
    private FragmentCourselistBinding mbinding;
    private String TAG= CourselistFragment.class.getSimpleName();
    private List<String> filterlist=new ArrayList<String>();
    FilterSpinnerAdapter mFilterSpinnerAdapter;
    List<CourseDatum> mlist;
    CourselistAdapter adapter;
    public static CourselistFragment newInstance() {
        CourselistFragment fragment = new CourselistFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_courselist, container, false);
        View rootView = mbinding.getRoot();

        ThemeClass.changeHeaderColor(mbinding.llHeader,getActivity());
        filterlist.clear();
        filterlist.add("All");
        filterlist.add("Hindi");
        filterlist.add("English");

        mFilterSpinnerAdapter=new FilterSpinnerAdapter(getActivity(),R.layout.spinner_rows,filterlist);
        mbinding.spFilter.setAdapter(mFilterSpinnerAdapter);


        mbinding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {


                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {



                if(adapter!=null){
                    if (newText.length() > 0) {
                        // Search
                        adapter.getFilter().filter(newText);

                    } else {
                        // Do something when there's no input
                        adapter.getFilter().filter("");


                    }


                }
                return false;
            }


        });



        mbinding.spFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>-1){
                    if(adapter!=null){
                        if (filterlist.get(position).length() > 0) {
                            // Search
                            adapter.setLanguage(filterlist.get(position));
                            adapter.getFilter().filter("");

                        } else {
                            // Do something when there's no input
                            adapter.getFilter().filter("");


                        }


                    }
                }else{

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        mbinding.rvItem.setHasFixedSize(true);
        mbinding.rvItem.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_15);
        mbinding.rvItem.addItemDecoration(new GridSpacingItemDecoration(2,spacingInPixels,false));


        mbinding.searchView.setActivated(true);
        mbinding.searchView.setQueryHint(getResources().getString(R.string.search1));
        mbinding.searchView.onActionViewExpanded();

        mbinding.searchView.clearFocus();
        SearchView.SearchAutoComplete searchEditText = (SearchView.SearchAutoComplete) mbinding.searchView.findViewById(R.id.search_src_text);
        searchEditText.setTextSize(14);
        searchEditText.setHintTextColor(getResources().getColor(R.color.hint_gray));
        searchEditText.setTextColor(getResources().getColor(R.color.black));
        searchEditText.setGravity(Gravity.CENTER_VERTICAL);

        TextView tv_header=(TextView) mbinding.llHeader.findViewById(R.id.tv_header);
        tv_header.setText(getString(R.string.course));
        LinearLayout ll_back=(LinearLayout) mbinding.llHeader.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();


            }
        });
        ll_back.setVisibility(View.GONE);

        if (Connectivity.isConnected(getActivity())) {

            callCourseList(getActivity());
        } else {

            //show net not connected error
            Toasty.warning(getActivity(), getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

        }


        return rootView;
    }


    private void callCourseList(Context context) {

        ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();
        mparam.put(Constant_Tag.STATUS, "1");

        Observable<CourelistModel> mdata = mRestApi.courselist(mparam);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CourelistModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(CourelistModel value) {
                        ProgressDialog.hideDialog();
                        if(value!=null)
                        Utils.Relogin(value.getStatus(),getActivity());

                        if (value != null && value.getStatus() == 200) {

                            mlist=value.getData();
                            adapter=new CourselistAdapter(getActivity(),mlist, CourselistFragment.this);

                            mbinding.rvItem.setAdapter(adapter);

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
    public void OnItemclick(int pos) {
        if(adapter.exampleListFull!=null && adapter.exampleListFull.size()>pos){
            FragmentTask.replaceFrgament(CoursesubFragment.newInstance(adapter.exampleListFull.get(pos)), getActivity().getSupportFragmentManager(), R.id.main_framelayout);
        }
    }

    @Override
    public void OnDesItemclick(int pos) {

    }


}