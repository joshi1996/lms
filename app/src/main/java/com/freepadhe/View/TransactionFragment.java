package com.freepadhe.View;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.freepadhe.Adapter.TransactionAdapter;
import com.freepadhe.GsonModel.UserCourse.Datum;
import com.freepadhe.GsonModel.UserCourse.UsercourseModel;
import com.freepadhe.R;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.FragmentInvitelistBinding;
import com.freepadhe.databinding.FragmentTransactionBinding;
import com.freepadhe.interfaces.OnclickListener;
import com.freepadhe.utility.Connectivity;
import com.freepadhe.utility.GridSpacingItemDecoration;
import com.freepadhe.utility.ProgressDialog;
import com.freepadhe.utility.SharePrefs;
import com.freepadhe.utility.ThemeClass;
import com.freepadhe.utility.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TransactionFragment extends Fragment implements OnclickListener{
    private FragmentTransactionBinding mbinding;
    private String TAG = TransactionFragment.class.getSimpleName();
    List<Datum> mlist;
    TransactionAdapter adapter;

    public static TransactionFragment newInstance() {
        TransactionFragment fragment = new TransactionFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mbinding = DataBindingUtil.inflate(inflater, R.layout.fragment_transaction, container, false);
        View rootView = mbinding.getRoot();

        ThemeClass.changeHeaderColor(mbinding.llHeader, getActivity());


        TextView tv_header = (TextView) mbinding.llHeader.findViewById(R.id.tv_header);
        tv_header.setText(getString(R.string.transaction));
        LinearLayout ll_back = (LinearLayout) mbinding.llHeader.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        ll_back.setVisibility(View.VISIBLE);


        mbinding.rvItem.setHasFixedSize(true);
        mbinding.rvItem.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.margin_15);
        mbinding.rvItem.addItemDecoration(new GridSpacingItemDecoration(1,spacingInPixels,false));


        if (Connectivity.isConnected(getActivity())) {

            callTransactionApi(getActivity());
        } else {

            //show net not connected error
            Toasty.warning(getActivity(), getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

        }
        Utils.hideKeyboard(getActivity());

        return rootView;
    }


    private void callTransactionApi(Context context) {
        ProgressDialog.showDialog(getActivity());
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();
        mparam.put("userId", SharePrefs.getUserdetail(getActivity()).getUser().getId().toString());

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
                                adapter=new TransactionAdapter(getActivity(),mlist, TransactionFragment.this);

                                mbinding.rvItem.setAdapter(adapter);
                            }
                            mbinding.rvItem.setVisibility(View.VISIBLE);
                            mbinding.llfilter.setVisibility(View.VISIBLE);

                        }

                        else
                        {
                            mbinding.rvItem.setVisibility(View.GONE);
                            mbinding.llfilter.setVisibility(View.GONE);
                            mbinding.transactionNotFound.setVisibility(View.VISIBLE);
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



    public void showAlertDialog(Datum mdatum) {        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        Rect displayRectangle = new Rect();
        Window window = getActivity().getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_transcation, null);
        builder.setView(customLayout);
        final AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        LinearLayout llayout= (LinearLayout) customLayout.findViewById(R.id.llayout);
        ImageView ivclose = (ImageView) customLayout.findViewById(R.id.ivclose);
        LinearLayout rlmain = (LinearLayout) customLayout.findViewById(R.id.rlmain);

        TextView tv_title = (TextView) customLayout.findViewById(R.id.tv_title);
        TextView tvcoursename = (TextView) customLayout.findViewById(R.id.tvcoursename);
        TextView tvamount = (TextView) customLayout.findViewById(R.id.tvamount);
        TextView tvpaymentgateway = (TextView) customLayout.findViewById(R.id.tvpaymentgateway);
        TextView tvtranscationid = (TextView) customLayout.findViewById(R.id.tvtranscationid);
        TextView tvdate = (TextView) customLayout.findViewById(R.id.tvdate);

        tv_title.setTextColor(SharePrefs.getSetting(getActivity()).getThemeColor());

        tvcoursename.setText("Course: "+mdatum.getCourseDetail().getName());
        tvamount.setText("Amount: Rs "+mdatum.getAmount()+"/-");
        tvpaymentgateway.setText("Payment Gateway: "+mdatum.getGateway());
        tvtranscationid.setText("Transaction Id: "+mdatum.getTransactionId());

        if(mdatum.getCreatedAt()!=null) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

            Date convertedDate = new Date();
            try {
                convertedDate = dateFormat.parse(mdatum.getCreatedAt());
                SimpleDateFormat sdfmonth = new SimpleDateFormat("dd-MM-yyyy");
                String date_text = sdfmonth.format(convertedDate);

                tvdate.setText("Date: " + date_text);

            } catch (ParseException e) {
                e.printStackTrace();
                tvdate.setText("Date: " + mdatum.getCreatedAt());

            }
        }



       /* llayout.getLayoutParams().width = (int) (displayRectangle.width() * 0.9f);
        llayout.getLayoutParams().height = (int) (displayRectangle.height() * 0.4f);

        llayout.requestLayout();*/


        ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }





    @Override
    public void OnItemclick(int pos) {

        showAlertDialog(mlist.get(pos));
    }

    @Override
    public void OnDesItemclick(int pos) {

    }
}