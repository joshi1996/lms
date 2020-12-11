package com.freepadhe.WebService;

import com.freepadhe.GsonModel.Advertisment.AdvertismentModel;
import com.freepadhe.GsonModel.BasicModel;
import com.freepadhe.GsonModel.CountryModels.CountryModel;
import com.freepadhe.GsonModel.CountryModels.stateModel.StateModel;
import com.freepadhe.GsonModel.CourelistModel;
import com.freepadhe.GsonModel.EarningModels.EarningModel;
import com.freepadhe.GsonModel.ForgetPassword.ForgotPassword;
import com.freepadhe.GsonModel.InviteModel.InviteModel;
import com.freepadhe.GsonModel.Job1.Job1Model;
import com.freepadhe.GsonModel.Job2.Job2Model;
import com.freepadhe.GsonModel.JobDetailModels.JobDetailModel;
import com.freepadhe.GsonModel.LiveVideo.LiveVideoModel;
import com.freepadhe.GsonModel.LmsLogindata;

import com.freepadhe.GsonModel.ProflieData.ProfileData;
import com.freepadhe.GsonModel.ResetPasswordModel.ResetPasswordDataModel;
import com.freepadhe.GsonModel.ReviewsModel.ReviewModel;
import com.freepadhe.GsonModel.SubmitReviews.SubmitReviewModel;
import com.freepadhe.GsonModel.TopicModel.LessionModel;
import com.freepadhe.GsonModel.UserCourse.UsercourseModel;
import com.freepadhe.GsonModel.UsercourseAdd.AddUsercourseModel;
import com.freepadhe.GsonModel.citydata.CityModel;
import com.freepadhe.GsonModel.coursedetail.CouresubModel;
import com.freepadhe.GsonModel.lms_OtpVerify;
import com.freepadhe.GsonModel.lms_signup;
import com.freepadhe.GsonModel.settingdata.SettingModel;

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

    //public String BaseUrl = "http://18.191.163.138:5000/v1/";

    public String BaseUrl = "http://13.233.230.23:3000/v1/";

    @GET("settings")
    Observable<SettingModel> Getsettings();

    @POST("register")
    @FormUrlEncoded
    Observable<lms_signup> getSignup(@FieldMap Map<String, String> body);

    @POST("review")
    @FormUrlEncoded
    Observable<SubmitReviewModel> getReviews(@FieldMap Map<String, String> body);

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

    @POST("password/reset")
    @FormUrlEncoded
    Observable<ResetPasswordDataModel> resetPassword(@FieldMap Map<String, String> body);

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
    Observable<ProfileData> getProfile(@QueryMap Map<String,String> body);

    @Multipart
    @POST("profile/update")
    Observable<ProfileData> updateProfile(@PartMap Map<String, RequestBody> body,@Part MultipartBody.Part profilePhoto);

    @GET("countries")
    Observable<CountryModel> getCountry();

    @GET("countries/{countryid}/states?status")
    Observable<StateModel> getState(@Path("countryid") String id,@Query("status") String status);

    @GET("states/{stateid}/cities?status")
    Observable<CityModel> getCity(@Path("stateid") String id,@Query("status") String status);

    @GET("users-by-code/{usercode}")
    Observable<InviteModel> invitlist(@Path("usercode") String usercode, @Query("contentPlanType") String contentPlanType);

    @GET("user-earning/{id}")
    Observable<EarningModel> earningData(@Path("id") String id);

    @GET("courses/{id}")
    Observable<Job1Model> job1(@Path("id") String id);

    @GET("courses/{id}")
    Observable<Job2Model> job2(@Path("id") String id);

    @GET("course-details/{id}")
    Observable<JobDetailModel> jobSublist(@Path("id") String id);

    @GET("review")
    Observable<ReviewModel> reviewShow(@Query("status") String status,
                                       @Query("courseId") String course);

    @GET("course-content")
    Observable<LiveVideoModel> liveVideo(@Query("status") String status,
                                         @Query("courseId") String course);

}
