package com.dherediat97.adoptapet

import com.dherediat97.adoptapet.presentation.MainActivity
import dagger.Component


@Component
interface ApplicationComponent {
    fun inject(activity: MainActivity)
}

