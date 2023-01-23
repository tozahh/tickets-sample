package com.it4pro.ticketservice.configuration;

import com.it4pro.ticketservice.tickets.features.nodclient.ExternalSystemAuthorizationInterceptor;
import com.it4pro.ticketservice.tickets.features.nodclient.ExternalSystemClient;
import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
@RequiredArgsConstructor
class ClientsConfiguration {

    private final ConfigurationParameters configurationParameters;

    @Bean
    public ExternalSystemClient extSystemClient() {

        ConfigurationParameters.Nod nodConfig = configurationParameters.getNod();
        return getRetrofit(nodConfig, new ExternalSystemAuthorizationInterceptor(nodConfig.getClientId(), nodConfig.getClientSecret())).create(ExternalSystemClient.class);
    }

    private Retrofit getRetrofit(ConfigurationParameters.RestConfiguration config, Interceptor... interceptors) {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
            .connectTimeout(config.getConnectTimeout().toMillis(), TimeUnit.MILLISECONDS)
            .writeTimeout(config.getWriteTimeout().toMillis(), TimeUnit.MILLISECONDS)
            .readTimeout(config.getReadTimeout().toMillis(), TimeUnit.MILLISECONDS)
            .followRedirects(false);

        if (interceptors != null) {
            Arrays.asList(interceptors).forEach(interceptor -> okHttpClientBuilder.addInterceptor(interceptor));
        }
        return new Retrofit.Builder()
            .client(okHttpClientBuilder.build())
            .baseUrl(config.getUri().toString())
            .build();
    }

}
