package com.wo.healthy.patient.network;

import android.net.Uri;

import com.google.gson.Gson;
import com.wo.healthy.patient.network.model.BaseModel;
import com.wo.healthy.patient.network.model.ErrorModel;
import com.wo.healthy.patient.network.request.SignUpRequest;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by wangnianqing on 2016/5/23.
 */
public final class ApiCenter {

    private interface Api {

        @POST("u/register.json")
        retrofit2.Call<BaseModel> regist(@Body SignUpRequest request);

    }
    private static final String BASE_URL = "http://123.56.28.111:8080/probe/";

    private final Api mApi;
    private final Retrofit mRetrofit;

    private final class CallBack<T extends BaseModel> implements Callback<T> {

        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (response.isSuccessful()) {
                response.body().setRequest(call.request());
                EventBus.getDefault().post(response.body());
            } else {
                ErrorModel error;
                try {
                    error = (ErrorModel) mRetrofit.responseBodyConverter(
                        ErrorModel.class, ErrorModel.class.getAnnotations())
                        .convert(response.errorBody());
                } catch (IOException e) {
                    error = new ErrorModel(-1, "Parse message error, http code:" + response.code());
                }
                error.setRequest(call.request());
                EventBus.getDefault().post(error);
            }
        }

        @Override
        public void onFailure(Call<T> call, Throwable t) {
            ErrorModel error = new ErrorModel(-2, "internal error:" + t.getMessage());
            error.setRequest(call.request());
            EventBus.getDefault().post(error);
        }
    }

    public ApiCenter() {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request request = addBasicParams(chain.request());
                return chain.proceed(request);
            }
            })
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .build();

        mRetrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .build();
        mApi = mRetrofit.create(Api.class);
    }

    private Request addBasicParams(Request original) {
        String originalUrl = original.url().toString();
        Map<String, String> parameters = new HashMap<>();
//        parameters.put("token", Config.getToken());
        Uri.Builder builder = Uri.parse(originalUrl).buildUpon();
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            builder.appendQueryParameter(param.getKey(), param.getValue());
        }

        Request.Builder requestBuilder = original.newBuilder()
            .url(builder.build().toString())
            .method(original.method(), original.body());
        return requestBuilder.build();
    }

    public Object regist(SignUpRequest request) {
        Call<BaseModel> call = mApi.regist(request);
        call.enqueue(new CallBack<>());
        return call.request();
    }
}
