package io.forus.me.android.presentation.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.SeekBar
import androidx.annotation.LayoutRes
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.adapters.SeekBarBindingAdapter



fun View.isVisible() = this.visibility == View.VISIBLE

fun View.visible() { this.visibility = View.VISIBLE }

fun View.invisible() { this.visibility = View.GONE }

fun View.setVisible(visible: Boolean) {
    if(visible){
        visible()
    }else{
        invisible()
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int): View =
        LayoutInflater.from(context).inflate(layoutRes, this, false)



internal infix fun SwitchCompat.onCheckedChanged(function: (CompoundButton, Boolean) -> Unit) {
    setOnCheckedChangeListener(function)
}

internal infix fun View.onClick(function: () -> Unit) {
    setOnClickListener { function() }
}

internal infix fun SeekBar.onProgressChanged(zoomUpdated: () -> Unit) {
    setOnSeekBarChangeListener(object : SeekBarBindingAdapter.OnProgressChanged,
        SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
            zoomUpdated()
        }

        override fun onStartTrackingTouch(p0: SeekBar?) {
            //NotNeed
        }

        override fun onStopTrackingTouch(p0: SeekBar?) {
            //NotNeed
        }

    })
}



