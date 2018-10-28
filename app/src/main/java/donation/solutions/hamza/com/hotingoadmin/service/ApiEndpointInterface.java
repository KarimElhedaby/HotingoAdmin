package donation.solutions.hamza.com.hotingoadmin.service;

import java.util.ArrayList;
import java.util.List;

import donation.solutions.hamza.com.hotingoadmin.model.AddRoomResponse;
import donation.solutions.hamza.com.hotingoadmin.model.AddServiceResponse;
import donation.solutions.hamza.com.hotingoadmin.model.NotificationModel;
import donation.solutions.hamza.com.hotingoadmin.model.RoomModel;
import donation.solutions.hamza.com.hotingoadmin.model.RoomOrderModel;
import donation.solutions.hamza.com.hotingoadmin.model.ServiceOrderModel;
import donation.solutions.hamza.com.hotingoadmin.model.ServicesResponce;
import donation.solutions.hamza.com.hotingoadmin.model.User;
import donation.solutions.hamza.com.hotingoadmin.model.UserResponce;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiEndpointInterface {

    @GET("room/5ba8d96c78d83d0e8c8c5cf2/admin")
    Call<ArrayList<RoomOrderModel>> getRoomOrder();

    @GET("service/5ba8da8078d83d0e8c8c5cf4/admin")
    Call<ArrayList<ServiceOrderModel>> getServiceOrders();

    @PUT("service/5ba8da8078d83d0e8c8c5cf4/admin/{id}/accept")
    Call<ServiceOrderModel> acceptOrderService(@Path("id") String id);


    @PUT("service/5ba8da8078d83d0e8c8c5cf4/admin/{id}/cancle")
    Call<ServiceOrderModel> cancleOrderService(@Path("id") String id);


    @PUT("room/5ba8d96c78d83d0e8c8c5cf2/admin/{id}/accept")
    Call<RoomOrderModel> acceptOrderRoom(@Path("id") String id);


    @PUT("room/5ba8d96c78d83d0e8c8c5cf2/admin/{id}/accept")
    Call<RoomOrderModel> cancleOrderRoom(@Path("id") String id);


    @GET("room")
    Call<ArrayList<RoomModel>> getRooms();

    @Multipart
    @POST("room")
    Call<AddRoomResponse> addRoom(
            @Part("number") RequestBody number,
            @Part("price") RequestBody price,
            @Part("desc") RequestBody desc,
            @Part List<MultipartBody.Part> img);


    @Multipart
    @POST("service")
    Call<AddServiceResponse> addService(
            @Part("desc") RequestBody desc,
            @Part("name") RequestBody name,
            @Part List<MultipartBody.Part> img);

    @GET("service")
    Call<ArrayList<ServicesResponce>> getServices();

    @POST("login")
    Call<UserResponce> signIn(@Body User user);

    @GET("notification")
    Call<ArrayList<NotificationModel>> getNotification();

    @GET("unseen")
    Call<Integer> getNotificationCount();

    @GET("service")
    Call<ArrayList<ServicesResponce>> deleteRoom(@Path("id") String id);

    @FormUrlEncoded
    @PUT("service/{id}")
    Call<ServicesResponce> updateService(
            @Path("id") String id , @Field("desc") String desc , @Field("name")String name);


    @FormUrlEncoded
    @PUT("service/{id}")
    Call<Void> deleteService(
            @Path("id") String id , @Field("avaliable") String avaliable );


    @FormUrlEncoded
    @PUT("room/{id}")
    Call<RoomModel> updateRoom(
            @Path("id") String id , @Field("number") String number , @Field("price")int price
            , @Field("desc") String desc
    );


    @FormUrlEncoded
    @PUT("room/{id}")
    Call<Void> deleteRoom(
            @Path("id") String id , @Field("avaliable") String avaliable );



}
