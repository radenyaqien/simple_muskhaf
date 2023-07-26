package com.radenyaqien.muskhaf

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import okhttp3.Interceptor
import okhttp3.OkHttpClient


class MainApp : Application(), ImageLoaderFactory {

    private val interceptor = Interceptor { chain ->
        val originalResponse = chain.proceed(chain.request())
        originalResponse.newBuilder()
            .header("Cache-Control", "max-age=60")
            .build()
    }
    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder().addInterceptor(
                    interceptor
                ).build()
            }
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.25)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()
    }
}