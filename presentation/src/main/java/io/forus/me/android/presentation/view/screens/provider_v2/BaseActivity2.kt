package io.forus.me.android.presentation.view.screens.provider_v2

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseActivity2 :  AppCompatActivity(){


    fun replaceFragment(containerViewId: Int, fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment)
            .commit()
    }
}