package com.lms.WebService;



import com.lms.GsonModel.Advertisment.AdvertismentModel;
import com.lms.GsonModel.BasicModel;
import com.lms.GsonModel.CountryModels.CountryModel;
import com.lms.GsonModel.CountryModels.stateModel.StateModel;
import com.lms.GsonModel.CourelistModel;
import com.lms.GsonModel.ForgetPassword.ForgotPassword;
import com.lms.GsonModel.LmsLogindata;

import com.lms.GsonModel.ProflieData.ProfileData;
import com.lms.GsonModel.TopicModel.LessionModel;
import com.lms.GsonModel.UserCourse.UsercourseModel;
import com.lms.GsonModel.UsercourseAdd.AddUsercourseModel;
import com.lms.GsonModel.citydata.CityModel;
import com.lms.GsonModel.coursedetail.CouresubModel;
import com.lms.GsonModel.lms_OtpVerify;
import com.lms.GsonModel.lms_signup;
import com.lms.GsonModel.settingdata.SettingModel;

import org.json.JSONObject;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RestApi {

    public String BaseUrl = "http://18.191.163.138:5000/v1/";

    @GET("settings")
    Observable<SettingModel> Getsettings();

    @POST("register")
    @FormUrlEncoded
    Observable<lms_signup> getSignup(@FieldMap Map<String, String> body);


    @POST("verify")
    @FormUrlEncoded
    Observable<lms_OtpVerify> verifyOtp(@FieldMap Map<String, String> body);


    @POST("login")
    @FormUrlEncoded
    Observable<LmsLogindata> getLogin(@FieldMap Map<String, String> body);


    @POST("resend-otp")
    @FormUrlEncoded
    Observable<ForgotPassword> resendOtp(@FieldMap Map<String, String> body);


    @POST("password/forgot")
    @FormUrlEncoded
    Observable<ForgotPassword> forgotPassword(@FieldMap Map<String, String> body);


    @POST("password/change")
    @FormUrlEncoded
    Observable<BasicModel> changePassword(@FieldMap Map<String, String> body);


    @GET("courses")
    Observable<CourelistModel> courselist(@QueryMap Map<String, String> body);

    @GET("user_courses")
    Observable<UsercourseModel> UsercourseList(@QueryMap Map<String, String> body);


    @POST("user_courses")
    @FormUrlEncoded
    Observable<AddUsercourseModel>AddUsercourse(@FieldMap Map<String, String> body);



    @GET("course-details/{id}")
    Observable<CouresubModel> coursesublist(@Path("id") String id);

    @GET("advertisement")
    Observable<AdvertismentModel> advertisement();

    @GET("subject-detail/{id}")
    Observable<LessionModel> Topiclist(@Path("id") String id);




    @GET("profile")
    Observable<ProfileData> getProfile();

    @Multipart
    @POST("profile/update")
    Observable<ProfileData> updateProfile(@PartMap Map<String, RequestBody> body,@Part MultipartBody.Part profilePhoto);


    @GET("countries")
    Observable<CountryModel> getCountry();

    @GET("countries/{countryid}/states?status")
    Observable<StateModel> getState(@Path("countryid") String id,@Query("status") String status);

    @GET("cities")
    Observable<CityModel> getCity();




}
