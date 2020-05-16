package com.lms.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.CardInfo;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentMethodToken;
import com.google.android.gms.wallet.PaymentsClient;

import java.util.HashMap;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.AbstractExecutorService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.android.gms.wallet.WalletConstants;
import com.lms.Adapter.CoursesublistAdapter;
import com.lms.GsonModel.CourseDatum;
import com.lms.GsonModel.UserCourse.Datum;
import com.lms.GsonModel.UsercourseAdd.AddUsercourseModel;
import com.lms.GsonModel.coursedetail.CouresubModel;
import com.lms.R;
import com.lms.WebService.Constant_Tag;
import com.lms.WebService.RestApi;
import com.lms.WebService.RestClient;
import com.lms.databinding.FragmentCheckoutBinding;
import com.lms.utility.Connectivity;
import com.lms.utility.PaymentsUtil;
import com.lms.utility.ProgressDialog;
import com.lms.utility.SharePrefs;
import com.lms.utility.ThemeClass;
import com.lms.utility.Utils;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CheckoutActivity extends AppCompatActivity {
    private FragmentCheckoutBinding mbinding;
    private String TAG = CheckoutActivity.class.getSimpleName();
    CouresubModel mCouresubModel;
    private PaymentsClient paymentsClient;
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

    private static final int TEZ_REQUEST_CODE = 123;

    private static final String GOOGLE_TEZ_PACKAGE_NAME = "com.google.android.apps.nbu.paisa.user";
   String courseid="",imagepath="",title="";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mbinding = DataBindingUtil.setContentView(this, R.layout.fragment_checkout);

        paymentsClient = PaymentsUtil.createPaymentsClient(this);
        possiblyShowGooglePayButton();

        Bundle b=getIntent().getExtras();

        if(b!=null){


            courseid= b.getString("courseid");
            imagepath= b.getString("imagepath");
            title=b.getString("title");



        }


        ThemeClass.changeHeaderColor(mbinding.llHeader,CheckoutActivity.this);

        TextView tv_header=(TextView) mbinding.llHeader.findViewById(R.id.tv_header);
        tv_header.setText(getString(R.string.checkout));
        LinearLayout ll_back=(LinearLayout) mbinding.llHeader.findViewById(R.id.ll_back);
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();


            }
        });
        ll_back.setVisibility(View.VISIBLE);

        mbinding.tvTitle.setText(title);
        Glide.with(CheckoutActivity.this).load(imagepath)
                .placeholder(R.drawable.placeholder)
                .into(mbinding.ivCover);







        mbinding.tvDesciptiontitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mbinding.tvDesciption.getVisibility()==View.VISIBLE){
                    mbinding.tvDesciptiontitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
                    mbinding.tvDesciption.setVisibility(View.GONE);
                }else{
                    mbinding.tvDesciptiontitle.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0);

                    mbinding.tvDesciption.setVisibility(View.VISIBLE);

                }
            }
        });


        if (Connectivity.isConnected(CheckoutActivity.this)) {
            getsubCourselist(CheckoutActivity.this);
        } else {
            //show net not connected error
            Toasty.warning(CheckoutActivity.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();
        }

        mbinding.llgooglepay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try
                {
                    Uri uri =
                            new Uri.Builder()
                                    .scheme("upi")
                                    .authority("pay")
                                    .appendQueryParameter("pa", "rajsaran100@okhdfcbank")
                                    .appendQueryParameter("pn", SharePrefs.getSetting(CheckoutActivity.this).getOrganizationName())
                                    .appendQueryParameter("mc", SharePrefs.getSetting(CheckoutActivity.this).getCompanyId())      //merchant code
                                    .appendQueryParameter("tr", ""+System.currentTimeMillis()) //your-transaction-ref-id
                                    .appendQueryParameter("tn", "purchase course " +title)
                                    .appendQueryParameter("am", "1")
                                    .appendQueryParameter("cu", "INR")
                                    .appendQueryParameter("url", SharePrefs.getSetting(CheckoutActivity.this).getWebsiteLink())
                                    .build();
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(uri);
                    intent.setPackage(GOOGLE_TEZ_PACKAGE_NAME);
                    startActivityForResult(intent, TEZ_REQUEST_CODE);

                }
                catch (Exception e)
                {
                    Log.e(TAG, "Error in submitting payment details", e);
                }
                
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void possiblyShowGooglePayButton() {

        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (!isReadyToPayJson.isPresent()) {
                return;
            }


            // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
            // OnCompleteListener to be triggered when the result of the call is known.
            IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
            Task<Boolean> task = paymentsClient.isReadyToPay(request);
            task.addOnCompleteListener(this,
                    new OnCompleteListener<Boolean>() {
                        @Override
                        public void onComplete(@NonNull Task<Boolean> task) {
                            if (task.isSuccessful()) {
                                setGooglePayAvailable(task.getResult());
                            } else {
                                Log.w("isReadyToPay failed", task.getException());
                            }
                        }
                    });
        }


    }



    private void setGooglePayAvailable(boolean available) {
        if (available) {
            mbinding.llgooglepay.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, R.string.googlepay_status_unavailable, Toast.LENGTH_LONG).show();
        }
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TEZ_REQUEST_CODE) {
            // Process based on the data in response.
            switch (resultCode) {
            case Activity.RESULT_OK:


                try {

                   // String paymentResponse =data.getStringExtra("response");
                     String txnRef =data.getStringExtra("txnRef");

                     String txnId =data.getStringExtra("txnId");

                    callpaycourse(CheckoutActivity.this,txnRef,txnId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;

            case Activity.RESULT_CANCELED:
                // The user cancelled the payment attempt
                Toasty.error(CheckoutActivity.this, "PAYMENT CANCELLED", Toast.LENGTH_SHORT).show();

                break;

            case AutoResolveHelper.RESULT_ERROR:
                Status status = AutoResolveHelper.getStatusFromIntent(data);
                Toasty.error(CheckoutActivity.this, status.getStatusMessage(), Toast.LENGTH_SHORT).show();

                break;
            }


        }
    }

    private void callpaycourse(final Context context, String txnRef, final String txnId) {

        ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();
        mparam.put("userId", SharePrefs.getUserdetail(context).getUser().getId());

        mparam.put(Constant_Tag.COURSEID, courseid);
        mparam.put(Constant_Tag.CONTENTPLANTYPE, "paid");
        mparam.put("gateway", "google pay");
        mparam.put("transactionId", txnId);

        try{
            mparam.put("amount", mCouresubModel.getPlanData().get(0).getOfferPrice().toString());


        }catch (Exception e){

        }



        Observable<AddUsercourseModel> mdata = mRestApi.AddUsercourse(mparam);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddUsercourseModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(AddUsercourseModel value) {
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {
                            Utils.hideKeyboard(CheckoutActivity.this);
                            //Toasty.success(CheckoutActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                            showAlertDialog(value.getMessage(),txnId);

                        } else if (value != null) {
                            //mDataListener.onError(value.getMessage());
                            Toasty.error(CheckoutActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: "+e.getMessage());
                        ProgressDialog.hideDialog();
                        //   mDataListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });
    }





    private void getsubCourselist(Context mcontext) {

        ProgressDialog.showDialog(mcontext);
        RestApi mRestApi = RestClient.getClient(mcontext).create(RestApi.class);


        Observable<CouresubModel> mdata = mRestApi.coursesublist(courseid);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CouresubModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CouresubModel value) {
                        ProgressDialog.hideDialog();
                        if(value!=null)
                            Utils.Relogin(value.getStatus(),CheckoutActivity.this);

                        if (value != null && value.getStatus() == 1) {
                            mCouresubModel=value;
                            if(mCouresubModel!=null){
                                mbinding.tvDesciption.setText(mCouresubModel.getDescription());
                                mbinding.ratingBar.setRating(mCouresubModel.getRating());
                            }
                            if(mCouresubModel!=null && mCouresubModel.getPlanData()!=null && mCouresubModel.getPlanData().size()>0){
                                mbinding.tvSubcription.setText(mCouresubModel.getPlanData().get(0).getDurationTime() +" "+mCouresubModel.getPlanData().get(0).getDurationType());
                                mbinding.tvPrice.setText(mCouresubModel.getPlanData().get(0).getOfferPrice()+" /-");
                            }
                        } else if (value != null) {
                            Toasty.error(CheckoutActivity.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        // display an error message
                        Log.e(TAG, "onError: "+e.getMessage());
                        ProgressDialog.hideDialog();
                        //   mDataListener.onError(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete: All Done!");
                    }
                });


    }

    public void showAlertDialog(String message,String transcationid ) {        // create an alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);

        Rect displayRectangle = new Rect();
        Window window = CheckoutActivity.this.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_paymentsuccess, null);
        builder.setView(customLayout);
        final AlertDialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        TextView tv_introtext = (TextView) customLayout.findViewById(R.id.tv_introtext);
        TextView tv_transactionid = (TextView) customLayout.findViewById(R.id.tv_transactionid);

        tv_transactionid.setText(transcationid);
        LinearLayout rlmain = (LinearLayout) customLayout.findViewById(R.id.rlmain);


        AppCompatButton btnhome = (AppCompatButton) customLayout.findViewById(R.id.btn_home);
        tv_introtext.setText(Html.fromHtml(message));


        ThemeClass.changeButtonColor(btnhome,CheckoutActivity.this);


        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(MainActivity.activity!=null){
                    MainActivity.activity.finish();
                    startActivity(new Intent(CheckoutActivity.this,MainActivity.class));
                    CheckoutActivity.this.finish();

                }
            }

        });
        dialog.show();
    }





}
