package com.games.memorygame.injection.module;

import com.games.memorygame.BuildConfig;
import com.games.memorygame.network.MemoryGameService;

import java.io.File;
import java.util.Locale;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import timber.log.Timber;

import static java.lang.String.format;


@Module
public class NetworkModule {

    private File cacheFile;

    public NetworkModule(File cacheFile) {
        this.cacheFile = new File(cacheFile, "responses");
    }

    @Provides
    @Singleton
    Retrofit provideCall() {
        Cache cache = null;
        try {
            cache = new Cache(cacheFile, 10 * 1024 * 1024);
        } catch (Exception e) {
            Timber.e(e, e.getMessage());
        }
        String maxAge = format(Locale.getDefault(), "max-age=%d", BuildConfig.CACHE_TIME);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .header("Content-Type", "application/json")
                            .removeHeader("Pragma")
                            .header("Cache-Control", maxAge)
                            .build();

                    HttpUrl url = request.url().newBuilder()
                            .addQueryParameter("api_key", BuildConfig.API_KEY)
                            .addQueryParameter("format", "json")
                            .addQueryParameter("nojsoncallback", "1")
                            .build();
                    request = request.newBuilder().url(url).build();

                    okhttp3.Response response = chain.proceed(request);
                    response.cacheResponse();
                    return response;
                })
                .cache(cache)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public MemoryGameService providesMemoryService(Retrofit retrofit) {
        return retrofit.create(MemoryGameService.class);
    }
}