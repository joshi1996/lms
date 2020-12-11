package com.freepadhe.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.freepadhe.Adapter.CitySpinnerAdapter;
import com.freepadhe.Adapter.CountrySpinnerAdapter;
import com.freepadhe.Adapter.StateSpinnerAdapter;
import com.freepadhe.GsonModel.CountryModels.CountryModel;
import com.freepadhe.GsonModel.CountryModels.DatumCountry;
import com.freepadhe.GsonModel.CountryModels.stateModel.Datumstate;
import com.freepadhe.GsonModel.CountryModels.stateModel.StateModel;
import com.freepadhe.GsonModel.ProflieData.ProfileData;
import com.freepadhe.GsonModel.citydata.CityModel;
import com.freepadhe.GsonModel.citydata.Datumcity;
import com.freepadhe.GsonModel.lmsData;
import com.freepadhe.R;
import com.freepadhe.WebService.RestApi;
import com.freepadhe.WebService.RestClient;
import com.freepadhe.databinding.ActivityCompleteProfileBinding;
import com.freepadhe.databinding.ActivityLoginsBinding;
import com.freepadhe.databinding.FragmentProfileBinding;
import com.freepadhe.utility.Connectivity;
import com.freepadhe.utility.ImageFilePath;
import com.freepadhe.utility.ProgressDialog;
import com.freepadhe.utility.SharePrefs;
import com.freepadhe.utility.ThemeClass;
import com.freepadhe.utility.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CompleteProfile extends AppCompatActivity {

    private static final String TAG = CompleteProfile.class.getSimpleName();
    private ActivityCompleteProfileBinding activityBinding;

    String emailMain = "youremail@gmail.com";

    private static final int SELECT_FILE = 11;
    final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1, MY_PERMISSIONS_REQUEST_CAMERA = 10;
    public static Uri imageUri = null;
    public static String imagepath="";
    String userChoosenTask;

    ProfileData mProfileData;

    CountrySpinnerAdapter mCountrySpinnerAdapter;
    StateSpinnerAdapter mStateSpinnerAdapter;
    List<Datumstate> Statelist;
    List<Datumcity> Citylist;
    List<DatumCountry> Countrylist;
    String statedata=null;
    String countrydata=null;
    String citydata=null;
    CitySpinnerAdapter mCitySpinnerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.changeStatuscolor(CompleteProfile.this);

        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_complete_profile);

        ThemeClass.changeButtonColor(activityBinding.completeBtnUpdate, CompleteProfile.this);

        TextView tv_header = (TextView) activityBinding.headerview.findViewById(R.id.tv_header);

        tv_header.setText(getString(R.string.complete));

        filldata();
        //getCitydata(CompleteProfile.this, (JSONArray) Citylist);

        ThemeClass.changeHeaderColor(activityBinding.headerview, CompleteProfile.this);

        ThemeClass.changeButtonColor(activityBinding.completeBtnUpdate, CompleteProfile.this);

        if (ContextCompat.checkSelfPermission(CompleteProfile.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ContextCompat.checkSelfPermission(CompleteProfile.this,
                    Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(CompleteProfile.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(CompleteProfile.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?

                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(CompleteProfile.this,
                        new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_CAMERA);

            }
        } else {
            //show camera
        }


        activityBinding.completeClickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        activityBinding.completeClickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        activityBinding.completeBtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (activityBinding.completeInfirstname.getText().toString().trim().length() == 0) {
                    Toasty.warning(CompleteProfile.this, getString(R.string.blank_firstname), Toast.LENGTH_SHORT).show();
                } else if (activityBinding.completeInmobileno.getText().toString().trim().length() == 0) {
                    Toasty.warning(CompleteProfile.this, getString(R.string.blank_moblieno), Toast.LENGTH_SHORT).show();
                } else if (activityBinding.completeInmobileno.getText().toString().trim().length() < 10) {
                    Toasty.warning(CompleteProfile.this, getString(R.string.moblieno_invalid), Toast.LENGTH_SHORT).show();
                } else {

                    if (Connectivity.isConnected(CompleteProfile.this)) {
                        if (activityBinding.completeInaddress.length()==0)
                        {
                            activityBinding.completeInaddress.setText(emailMain);
                        }
                        else
                        {
                            CallUpdateApi(CompleteProfile.this);
                        }

                    } else {

                        //show net not connected error
                        Toasty.warning(CompleteProfile.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

                    }

                }


            }
        });
    }


    private void selectImage() {
        final CharSequence[] items = {getString(R.string.takephoto), getString(R.string.fromgallery),
                getString(R.string.cancle)};
        AlertDialog.Builder builder = new AlertDialog.Builder(CompleteProfile.this);
        builder.setTitle(getString(R.string.addphoto));
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(getString(R.string.takephoto))) {
                    userChoosenTask = getString(R.string.takephoto);
                    cameraIntent();
                } else if (items[item].equals(getString(R.string.fromgallery))) {
                    userChoosenTask = getString(R.string.fromgallery);
                    galleryIntent();
                } else if (items[item].equals(getString(R.string.cancle))) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void galleryIntent() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent() {


        String Path = Environment.getExternalStorageDirectory().getPath().toString() + "/lsf/";

        File FPath = new File(Path);
        if (!FPath.exists()) {
            if (!FPath.mkdir()) {
                System.out.println("***Problem creating Image folder " + Path);
            }
        }

        String fileName = Calendar.getInstance().getTimeInMillis() + ".jpg";


        Log.d("path0", "" + Path + fileName);


        // Create parameters for Intent with filename

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put(MediaStore.Images.Media.DATA, Path + fileName);

        values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");

        // imageUri is the current activity attribute, define and save it for later usage

        imageUri = CompleteProfile.this.getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.putExtra(MediaStore.EXTRA_SCREEN_ORIENTATION, ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

    }

    public static RequestBody createRequestBody(@NonNull String s) {
        return RequestBody.create(
                MediaType.parse("multipart/form-data"), s);
    }


    private void CallUpdateApi(Context mcontext) {

        String firstname = activityBinding.completeInfirstname.getText().toString().trim();
        final String mobileno = activityBinding.completeInmobileno.getText().toString().trim();
        emailMain = activityBinding.completeInemailaddress.getText().toString().trim();
        final String address = activityBinding.completeInaddress.getText().toString().trim();
        //final String state = mbinding.instate.getText().toString().trim();
        //final String city = mbinding.incity.getText().toString().trim();
        final String zipcode = activityBinding.completeInzipcode.getText().toString().trim();

        ProgressDialog.showDialog(mcontext);
        RestApi mRestApi = RestClient.getClient(mcontext).create(RestApi.class);
        Map<String, RequestBody> mparam = new HashMap<String, RequestBody>();

        mparam.put("fullName", createRequestBody(firstname));
        mparam.put("address", createRequestBody(address));
        mparam.put("emailAddress", createRequestBody(emailMain));
        mparam.put("phoneNo", createRequestBody(mobileno));
        mparam.put("zipCode", createRequestBody(zipcode));

        if (countrydata != null)
        {
            mparam.put("country", createRequestBody(countrydata));
        }
        else {
            ProgressDialog.hideDialog();
            Toasty.warning(CompleteProfile.this, "Please select Country", Toast.LENGTH_SHORT).show();
            return;
        }

        if (statedata != null)
        {
            mparam.put("state", createRequestBody(statedata));
        }
        else {
            ProgressDialog.hideDialog();
            Toasty.warning(CompleteProfile.this, "Please select State", Toast.LENGTH_SHORT).show();
            return;
        }

        if (citydata != null)
        {
            mparam.put("city", createRequestBody(citydata));
        }
        else {
            ProgressDialog.hideDialog();
            Toasty.warning(CompleteProfile.this, "Please select City", Toast.LENGTH_SHORT).show();
            return;
        }


        MultipartBody.Part imagenPerfil = null;
        RequestBody requestFile = null;
        if (imagepath.length() > 0) {
            //show blank username alert
            File file = new File(imagepath);
            requestFile =
                    RequestBody.create(MediaType.parse("multipart/form-data"), file);
            //MultipartBody.Part is used to send also the actual file name
            imagenPerfil = MultipartBody.Part.createFormData("profilePhoto", file.getName(), requestFile);
        }

        Observable<ProfileData> mdata = mRestApi.updateProfile(mparam, imagenPerfil);
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProfileData>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ProfileData value) {
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {
                            Toasty.success(CompleteProfile.this, value.getMessage(), Toast.LENGTH_SHORT).show();
                            getProfile(CompleteProfile.this, true);
                            Intent intent = new Intent(CompleteProfile.this,MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else if (value != null) {
                            Toasty.error(CompleteProfile.this, value.getMessage(), Toast.LENGTH_SHORT).show();

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

    private void filldata() {

        if (Connectivity.isConnected(CompleteProfile.this)) {
            getProfile(CompleteProfile.this, false);

        } else {
            //show net not connected error
            Toasty.warning(CompleteProfile.this, getString(R.string.net_disconnected), Toast.LENGTH_SHORT).show();

        }

    }


    private void getProfile(final Context context, final boolean isfinish) {

        ProgressDialog.showDialog(context);
        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();
        mparam.put("userId", SharePrefs.getUserdetail(CompleteProfile.this).getUser().getId());

        Observable<ProfileData> mdata = mRestApi.getProfile(mparam);
        Log.d("data", mdata.toString());

        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProfileData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(ProfileData value) {
                        Log.d("value", "" + value);
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {
                            mProfileData = value;
                            //String city = value.getData().getCity();
                            //Toast.makeText(context, ""+city, Toast.LENGTH_SHORT).show();
                            lmsData mUserDetails = SharePrefs.getUserdetail(CompleteProfile.this);
                            mUserDetails.setUser(value.getData());
                            SharePrefs.saveUserDetail(CompleteProfile.this, mUserDetails);

                            Log.d("data:", value.getData().getFullName());

                            if (isfinish) {
                                (CompleteProfile.this).onBackPressed();
                                return;
                            }
                            activityBinding.completeInfirstname.setText(value.getData().getFullName());
                            //Toasty.error(getActivity(), ""+value.getData().getFullName(), Toast.LENGTH_SHORT).show();
                            activityBinding.completeInemailaddress.setText(value.getData().getEmailAddress());
                            //Toast.makeText(context, ""+mbinding.inemailaddress, Toast.LENGTH_SHORT).show();
                            activityBinding.completeInmobileno.setText(value.getData().getPhoneNo());
                            getCountydata(context);
                            activityBinding.completeInzipcode.setText(value.getData().getZipCode());
                            activityBinding.completeInaddress.setText(value.getData().getAddress());

                            Glide.with(CompleteProfile.this)
                                    .load(value.getData().getProfilePhoto()).dontAnimate()
                                    .placeholder(R.drawable.appstore)
                                    .into(activityBinding.completeProfileImage);


                        } else if (value != null) {
                            Toasty.error(CompleteProfile.this, value.getMessage(), Toast.LENGTH_SHORT).show();

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

    boolean isfirstimecountry = true;

    private void getCountydata(final Context context) {

        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();


        Observable<CountryModel> mdata = mRestApi.getCountry();
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CountryModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CountryModel value) {
                        ProgressDialog.hideDialog();

                        if (value != null && value.getStatus() == 200) {
                            Countrylist = value.getData();
                            mCountrySpinnerAdapter = new CountrySpinnerAdapter(CompleteProfile.this, R.layout.spinner_rows, Countrylist);
                            activityBinding.completeSpCountry.setAdapter(mCountrySpinnerAdapter);

                            if (Countrylist != null && Countrylist.size() > 0 && mProfileData != null && mProfileData.getData().getCountry() != null && isfirstimecountry) {
                                for (int i = 0; i < Countrylist.size(); i++) {
                                    if (Countrylist.get(i).getId().equalsIgnoreCase(mProfileData.getData().getCountry())) {
                                        activityBinding.completeSpCountry.setSelection(i + 1);
                                        countrydata = Countrylist.get(i).getId();
                                        break;
                                    }
                                }

                                activityBinding.completeSpCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if (isfirstimecountry == true) {
                                            isfirstimecountry = false;

                                            return;
                                        }
                                        if (position > -1) {
                                            countrydata = Countrylist.get(position).getId();
                                            getStatedata(CompleteProfile.this, countrydata);
                                        } else {
                                            countrydata = null;
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }


                        } else if (value != null) {
                            Toasty.error(CompleteProfile.this, value.getMessage(), Toast.LENGTH_SHORT).show();

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

    boolean isfirsttime_state = true;

    private void getStatedata(final Context context, String countryid) {

        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);

        Observable<StateModel> mdata = mRestApi.getState(countryid, "1");
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<StateModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(StateModel value) {
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {
                            Statelist = value.getData();
                            mStateSpinnerAdapter = new StateSpinnerAdapter(CompleteProfile.this, R.layout.spinner_rows, Statelist);
                            activityBinding.completeSpState.setAdapter(mStateSpinnerAdapter);


                            if (isfirsttime_state) {


                                if (Statelist != null && Statelist.size() > 0 && mProfileData != null && mProfileData.getData().getState() != null) {
                                    for (int i = 0; i < Statelist.size(); i++) {

                                        if (Statelist.get(i).getId().equalsIgnoreCase(mProfileData.getData().getState())) {
                                            activityBinding.completeSpState.setSelection(i + 1);
                                            statedata = Statelist.get(i).getId();

                                            break;
                                        }
                                    }
                                }

                                activityBinding.completeSpState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        if (isfirsttime_state) {
                                            isfirsttime_state = false;

                                            return;
                                        }

                                        if (position > -1) {
                                            statedata = Statelist.get(position).getId();
                                            getCitydata(CompleteProfile.this,statedata);
                                            /*JSONArray marray;

                                            marray = new JSONArray();

                                            JSONObject mobject = new JSONObject();
                                            try {
                                                mobject.put("name", Statelist.get(position).getName());
                                                mobject.put("stateId", Statelist.get(position).getId());
                                                mobject.put("status", Statelist.get(position).getStatus());
                                                marray.put(mobject);


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }*/


                                        } else {
                                            statedata = null;
                                        }
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });

                            }


                        } else if (value != null) {
                            Toasty.error(CompleteProfile.this, value.getMessage(), Toast.LENGTH_SHORT).show();

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

    boolean isfirsttime_city = true;

    private void getCitydata(final Context context, String Id) {

        RestApi mRestApi = RestClient.getClient(context).create(RestApi.class);
        HashMap<String, String> mparam = new HashMap<String, String>();

        Observable<CityModel> mdata = mRestApi.getCity(Id,"1");
        mdata.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CityModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CityModel value) {
                        ProgressDialog.hideDialog();
                        if (value != null && value.getStatus() == 200) {

                            Citylist = value.getData();
                            mCitySpinnerAdapter = new CitySpinnerAdapter(CompleteProfile.this, R.layout.spinner_rows, Citylist);
                            activityBinding.completeSpCity.setAdapter(mCitySpinnerAdapter);
                            if (Citylist != null && Citylist.size() > 0 && mProfileData != null && mProfileData.getData().getCity() != null && isfirsttime_city) {
                                for (int i = 0; i < Citylist.size(); i++) {

                                    if (Citylist.get(i).getId().equalsIgnoreCase(mProfileData.getData().getCity())) {
                                        activityBinding.completeSpCity.setSelection(i + 1);
                                        break;
                                    }
                                }
                                isfirsttime_city = false;
                            }


                            activityBinding.completeSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    if (position > -1) {
                                        citydata = Citylist.get(position).getId();
                                    } else {
                                        citydata = null;
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });


                        } else if (value != null) {
                            Toasty.error(CompleteProfile.this, value.getMessage(), Toast.LENGTH_SHORT).show();

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {

            if (resultCode == CompleteProfile.this.RESULT_OK && imageUri != null) {
                imagepath = convertImageUriToFile(imageUri, CompleteProfile.this);

                try {
                    System.gc();

                    Bitmap bitmap = BitmapFactory.decodeFile(imagepath);


                    ExifInterface ei = new ExifInterface(imagepath);
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    Bitmap rotatedBitmap = null;
                    switch (orientation) {

                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotatedBitmap = rotateImage(bitmap, 90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotatedBitmap = rotateImage(bitmap, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotatedBitmap = rotateImage(bitmap, 270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            rotatedBitmap = bitmap;
                    }

                    activityBinding.completeProfileImage.setImageBitmap(rotatedBitmap);

                    activityBinding.completeProfileImage.setVisibility(View.VISIBLE);

                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else if (resultCode == CompleteProfile.this.RESULT_CANCELED) {

                Toast.makeText(CompleteProfile.this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            } else {

                Toast.makeText(CompleteProfile.this, " Picture was not taken ", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == SELECT_FILE) {


            imageUri = data.getData();


            imagepath = ImageFilePath.getPath(CompleteProfile.this, data.getData());

            Bitmap bm = null;
            if (data != null) {
                try {
                    System.gc();

                    Bitmap bitmap = BitmapFactory.decodeFile(imagepath);

                    ExifInterface ei = new ExifInterface(imagepath);
                    int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED);

                    Bitmap rotatedBitmap = null;
                    switch (orientation) {

                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotatedBitmap = rotateImage(bitmap, 90);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotatedBitmap = rotateImage(bitmap, 180);
                            break;

                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotatedBitmap = rotateImage(bitmap, 270);
                            break;

                        case ExifInterface.ORIENTATION_NORMAL:
                        default:
                            rotatedBitmap = bitmap;
                    }

                    activityBinding.completeProfileImage.setImageBitmap(rotatedBitmap);
                    activityBinding.completeProfileImage.setVisibility(View.VISIBLE);

                    System.gc();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    public static String convertImageUriToFile(Uri imageUri, FragmentActivity activity) {

        Cursor cursor = null;
        int imageID = 0;
        String Path = "";
        try {

            /*********** Which columns values want to get *******/
            String[] proj = {
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.ImageColumns.ORIENTATION
            };

            cursor = activity.getContentResolver().query(

                    imageUri,         //  Get data for specific image URI
                    proj,             //  Which columns to return
                    null,             //  WHERE clause; which rows to return (all rows)
                    null,             //  WHERE clause selection arguments (none)
                    null              //  Order-by clause (ascending by name)

            );

            //  Get Query Data

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            //int orientation_ColumnIndex = cursor.
            //    getColumnIndexOrThrow(MediaStore.Images.ImageColumns.ORIENTATION);

            int size = cursor.getCount();

            /*******  If size is 0, there are no images on the SD Card. *****/

            if (size == 0) {

                Toast.makeText(activity, " No Image", Toast.LENGTH_SHORT).show();

            } else {

                int thumbID = 0;
                if (cursor.moveToFirst()) {

                    /**************** Captured image details ************/

                    /*****  Used to show image on view in LoadImagesFromSDCard class ******/
                    imageID = cursor.getInt(columnIndex);

                    thumbID = cursor.getInt(columnIndexThumb);

                    Path = cursor.getString(file_ColumnIndex);
                    Log.d("path1", "" + Path);

                    //String orientation =  cursor.getString(orientation_ColumnIndex);
                    String CapturedImageDetails = " CapturedImageDetails : \n\n"
                            + " ImageID :" + imageID + "\n"
                            + " ThumbID :" + thumbID + "\n"
                            + " Path :" + Path + "\n";

                    Log.d("path2", Path);

                    // Show Captured Image detail on activity
                    // Toast.makeText(activity, CapturedImageDetails, Toast.LENGTH_SHORT).show();


                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // Return Captured Image ImageID ( By this ImageID Image will load from sdcard )

        return Path;
    }

}