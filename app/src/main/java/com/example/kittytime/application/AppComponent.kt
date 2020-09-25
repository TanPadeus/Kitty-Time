package com.example.kittytime.application

import android.content.Context
import com.example.kittytime.ui.CatFragment
import com.example.kittytime.ui.MainActivity
import com.example.kittytime.ui.StartFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(startFragment: StartFragment)
    fun inject(catFragment: CatFragment)
}