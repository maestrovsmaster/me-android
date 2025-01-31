package io.forus.me.android.presentation.view.screens.welcome

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.view.activity.BaseActivity
import io.forus.me.android.presentation.view.component.pinlock.IndicatorDots
import io.forus.me.android.presentation.view.screens.account.newaccount.NewAccountActivity
import io.forus.me.android.presentation.view.screens.lock.PinLockActivity
import io.forus.me.android.presentation.view.screens.welcome.adapter.SectionPagerAdapter
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity() {

    private var mustGoToLogin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        if (savedInstanceState == null) {
            mustGoToLogin = intent.getBooleanExtra(GO_TO_LOGIN, false)
        }

        val adapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = adapter

        Log.d("MDSASDADS", "${viewPager.itemDecorationCount}   ${adapter.itemCount}")
        val dotsIndicator = findViewById<me.relex.circleindicator.CircleIndicator3>(R.id.indicator)
        dotsIndicator.setViewPager(viewPager)


        backBt.setOnClickListener {
            if (viewPager.currentItem == 0) {
                exitWelcome()
            }else{
                val nextItem = viewPager.currentItem - 1
                if (nextItem >= 0) {
                    viewPager.currentItem = nextItem
                }
            }
        }


        btNext.setOnClickListener {
            if (viewPager.currentItem < 4) {
                val nextItem = viewPager.currentItem + 1
                if (nextItem < viewPager.adapter?.itemCount ?: 0) {
                    viewPager.currentItem = nextItem
                }
            } else {
                exitWelcome()
            }
        }

        btSkipTutorial.setOnClickListener {
            exitWelcome()
        }

        btSkip.setOnClickListener {
            exitWelcome()
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                btNext.text = if (position == 4) {
                    getString(R.string.close)
                } else {
                    getString(R.string.next_step)
                }


                if (position == 0) {
                    btSkipTutorial.apply {
                        visibility = View.VISIBLE
                        alpha = 0.0f
                        animate()
                            .setDuration(200)
                            .alpha(1.0f)
                            .start()
                    }
                } else {
                    btSkipTutorial.animate()
                        .setDuration(200)
                        .alpha(0.0f)
                        .withEndAction {
                            btSkipTutorial.visibility = View.GONE
                        }
                        .start()
                }


                if (mustGoToLogin) {
                    backBt.apply {
                        when (position) {
                            0 -> animate()
                                .setDuration(200)
                                .alpha(0.0f)
                                .withEndAction { visibility = View.GONE }
                                .start()
                            1 -> {
                                visibility = View.VISIBLE
                                alpha = 0.0f
                                animate()
                                    .setDuration(200)
                                    .alpha(1.0f)
                                    .start()
                            }
                        }
                    }
                } else {
                    backBt.visibility = View.VISIBLE
                }


                if (position == 0) {
                    btSkip.animate()
                        .setDuration(200)
                        .alpha(0.0f)
                        .withEndAction {
                            btSkip.visibility = View.GONE
                        }
                        .start()


                } else if (position == 1) {
                    btSkip.apply {
                        visibility = View.VISIBLE
                        alpha = 0.0f
                        animate()
                            .setDuration(200)
                            .alpha(1.0f)
                            .start()
                    }
                }


            }

        })

    }

    private fun exitWelcome() {
        if (mustGoToLogin) {
            this.navigator.navigateToLoginSignUp(this)
        }
        finish()
    }


    companion object {
        private const val GO_TO_LOGIN = "GO_TO_LOGIN"
        fun getCallingIntent(context: Context, goToLogin: Boolean) =
            Intent(context, WelcomeActivity::class.java).apply {
                putExtra(GO_TO_LOGIN, goToLogin)
            }
    }
}