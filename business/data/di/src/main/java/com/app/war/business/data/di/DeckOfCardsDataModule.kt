package com.app.war.business.data.di

import com.app.war.business.data.main.BuildConfig
import com.app.war.business.data.main.datasource.DeckOfCardsRemoteDataSource
import com.app.war.business.data.main.datasource.remote.DeckOfCardsApiService
import com.app.war.business.data.main.datasource.remote.DeckOfCardsRemoteDataSourceImpl
import com.app.war.business.data.main.mapper.CardsMapper
import com.app.war.business.data.main.mapper.DeckMapper
import com.app.war.business.data.main.repository.DeckOfCardsRepositoryImpl
import com.app.war.business.domain.main.repository.DeckOfCardsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DeckOfCardsDataModule {
    private const val DEFAULT_TIMEOUT = 60L

    @Singleton
    @Provides
    @Named("deck_of_cards_base_url")
    fun provideApiBaseURL(): String {
        return BuildConfig.API_BASE_URL
    }

    /**
     * Provides LoggingInterceptor for api information
     */
    @Singleton
    @Provides
    @Named("deck_of_cards_logger_interceptor")
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    /**
     * Provides custom OkkHttp
     */
    @Singleton
    @Provides
    @Named("deck_of_cards_okhttp")
    fun provideOkHttpClient(
        @Named("deck_of_cards_logger_interceptor") loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        val okHttpClient = OkHttpClient().newBuilder()

        okHttpClient.callTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(loggingInterceptor)
        okHttpClient.build()
        return okHttpClient.build()
    }

    /**
     * Provides converter factory for retrofit
     */
    @Singleton
    @Provides
    @Named("deck_of_cards_gson_converted")
    fun provideConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    /**
     * Provides ApiServices client for Retrofit
     */
    @Singleton
    @Provides
    @Named("deck_of_cards_retrofit")
    fun provideRetrofitClient(
        @Named("deck_of_cards_base_url") baseUrl: String,
        @Named("deck_of_cards_okhttp") okHttpClient: OkHttpClient,
        @Named("deck_of_cards_gson_converted") converterFactory: Converter.Factory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    /**
     * Provides Api Service using retrofit
     */
    @Singleton
    @Provides
    @Named("deck_of_cards_api_service")
    fun provideDeckOfCardsApiService(@Named("deck_of_cards_retrofit") retrofit: Retrofit): DeckOfCardsApiService {
        return retrofit.create(DeckOfCardsApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideDeckOfCardsRepository(
        remoteDataSource: DeckOfCardsRemoteDataSource,
        deckMapper: DeckMapper,
        cardsMapper: CardsMapper,
    ): DeckOfCardsRepository {
        return DeckOfCardsRepositoryImpl(
            remoteDataSource = remoteDataSource,
            deckMapper = deckMapper,
            cardsMapper = cardsMapper
        )
    }

    @Singleton
    @Provides
    fun provideDeckOfCardsRemoteDataSource(
        @Named("deck_of_cards_api_service") deckOfCardsApiService: DeckOfCardsApiService,
    ): DeckOfCardsRemoteDataSource {
        return DeckOfCardsRemoteDataSourceImpl(
            apiService = deckOfCardsApiService,
        )
    }
}
