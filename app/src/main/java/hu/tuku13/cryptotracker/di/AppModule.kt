package hu.tuku13.cryptotracker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hu.tuku13.cryptotracker.BaseApplication
import hu.tuku13.cryptotracker.database.getDatabase
import hu.tuku13.cryptotracker.repository.CoinRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideRandomString() : String {
        return "ASDASDASD"
    }

    @Singleton
    @Provides
    fun provideRepository(@ApplicationContext app: Context) : CoinRepository {
        return CoinRepository(getDatabase(app), app)
    }
}