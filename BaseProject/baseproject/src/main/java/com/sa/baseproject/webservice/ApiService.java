package com.sa.baseproject.webservice;

import com.sa.baseproject.BuildConfig;

import java.io.IOException;
import java.security.KeyStore;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sa on 31/03/17.
 * <p>
 * Initialisation of Retrofit library with Okhttp3 and bind a base url.
 */

public class ApiService {

    private static final int DEFAULT_TIMEOUT = 50 * 1000;

    private ApiInterface apiInterface;
    private ApiInterface apiInterfaceSecure;

    public ApiService() {
        initDefaultRetrofitService();
    }

    private void initDefaultRetrofitService() {
        try {

            //set default time out
            final OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
//            builder.addInterceptor(new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request request = chain.request().newBuilder().
//                            addHeader(ApiConstant.HEADER_CONTENT_TYPE_NAME,
//                                    ApiConstant.HEADER_CONTENT_TYPE_VALUE).build();
//                    return chain.proceed(request);
//                }
//            });
            // enable logging
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstant.HTTP_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(builder.build())
                    .build();

            apiInterface = retrofit.create(ApiInterface.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSecureRetrofitService() {
        //set default time out
        try {
            final OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            // Install the all-trusting trust manager
//            final SSLContext sslContext = SSLContext.getInstance("TLS");
//            TLSSocketFactory tlsSocketFactory = new TLSSocketFactory();
            //builder.socketFactory(tlsSocketFactory);
            final TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            final TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
            }
            final X509TrustManager trustManager = (X509TrustManager) trustManagers[0];
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory, trustManager);
            //builder.followSslRedirects(true);
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder().
                            addHeader(ApiConstant.HEADER_CONTENT_TYPE_NAME,
                                    ApiConstant.HEADER_CONTENT_TYPE_VALUE).build();
                    return chain.proceed(request);
                }
            });
            // enable logging
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(logging);
            }
            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ApiConstant.HTTPS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(builder.build())
                    .build();

            apiInterfaceSecure = retrofit.create(ApiInterface.class);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public ApiInterface getApiInterface() {
        return apiInterface;
    }

    public ApiInterface getSecureNetworkApi() {
        return apiInterfaceSecure;
    }

}
