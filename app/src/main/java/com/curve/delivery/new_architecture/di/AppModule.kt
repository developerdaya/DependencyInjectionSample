package com.curve.delivery.new_architecture.di

import android.content.Context
import com.curve.delivery.new_architecture.helper.Network
import com.curve.delivery.new_architecture.helper.NetworkConnectivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class AppModule{

    @Provides
    @Singleton
    fun provideCoroutineContext():CoroutineContext{
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideNetworkConnectivity(@ApplicationContext context: Context):NetworkConnectivity{
        return Network(context)
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope():CoroutineScope{
        return CoroutineScope(SupervisorJob())
    }

    @Retention(AnnotationRetention.RUNTIME)
    @Qualifier
    annotation class ApplicationScope

}
