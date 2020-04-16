package com.bigdata.base.request;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by 通子 on 2017/12/14.
 */

public class Request {
    private static Retrofit retrofit = null;
    public static String BASE_URL = "http://xywy.bigeshuju.com/index.php/api/App/";
//    public static String BASE_URL = "http://39.104.69.20/append/";

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).build();
        }
        return retrofit;
    }

    public interface RegistRequest {
        @POST("login?")
        Call<ResponseBody> call(@Query("request") String request);
    }

    public interface LoginRequest {
        @POST("login.php?")
        Call<CommonBean> call(@Query("user_tel") String user_tel, @Query("user_password") String user_password);
    }

    public interface ForgetRequest {
        @POST("forget.php?")
        Call<CommonBean> call(@Query("user_tel") String user_tel, @Query("user_password") String user_password);
    }

    public interface FixPassRequest {
        @POST("pwd.php?")
        Call<CommonBean> call(@Query("user_tel") String user_tel, @Query("user_password") String user_password);
    }

    public interface UploadImgRequest {
        @POST("upload.php?")
        Call<CommonBean> call(@Query("img") String img, @Query("user_id") String user_password);
    }

    public interface SetNikeNameRequest {
        @POST("edituser.php?")
        Call<CommonBean> call(@Query("username") String username, @Query("user_id") String user_id);
    }

    public interface MyAddressRequest {
        @POST("addresslist.php?")
        Call<CommonBean> call(@Query("username") String username, @Query("user_id") String user_id);
    }

    public interface EditAddressRequest {
        @POST("addressedit.php?")
        Call<CommonBean> call(@Query("username") String username, @Query("user_id") String user_id);
    }

    public interface AddAddressRequest {
        @POST("addressadd.php?")
        Call<CommonBean> call(@Query("username") String username, @Query("user_id") String user_id);
    }

    public interface DelAddressRequest {
        @POST("addressdel.php?")
        Call<CommonBean> call(@Query("username") String username, @Query("user_id") String user_id);
    }

    public interface MsgRequest {
        @POST("message.php?")
        Call<CommonBean> call(@Query("user_id") String user_id);
    }

    public interface MsgSysRequest {
        @POST("messagesys.php?")
        Call<CommonBean> call(@Query("user_id") String user_id, @Query("page") String page);
    }

    public interface MsgOrderRequest {
        @POST("messageord.php?")
        Call<CommonBean> call(@Query("user_id") String user_id, @Query("page") String page);
    }

    public interface CouponListRequest {
        @POST("fav.php?")
        Call<CommonBean> call(@Query("user_id") String user_id);
    }

    public interface BannnerRequest {
        @POST("layer.php?")
        Call<CommonBean> call();
    }

    public interface HotSpecialRequest {
        @POST("hosts.php?")
        Call<CommonBean> call();
    }

    public interface CateGoodsRequest {
        @POST("cate.php?")
        Call<CommonBean> call(@Query("status") String status, @Query("category_id") String category_id, @Query("page") String page);
    }

    public interface SearchRequest {
        @POST("search.php?")
        Call<CommonBean> call(@Query("keywords") String keywords);
    }

    public interface GoodsJsonRequest {
        @POST("show.php?")
        Call<CommonBean> call(@Query("shop_id") String shop_id,@Query("user_id") String user_id);
    }

    public interface GoodsCommRequest {
        @POST("comment.php?")
        Call<CommonBean> call(@Query("shop_id") String shop_id,@Query("page") String page);
    }

    public interface CollectContRequest {
        @POST("collect.php?")
        Call<CommonBean> call(@Query("shop_id") String shop_id,@Query("user_id") String user_id);
    }

    public interface CollectUserRequest {
        @POST("collectlist.php?")
        Call<CommonBean> call(@Query("page") String page,@Query("user_id") String user_id);
    }

}

