package io.forus.me.android.presentation.view.component

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.extension.setVisible


open class BaseTitleBar : FrameLayout {

    protected var mRootView: View? = null



    protected var tvTitle: TextView? = null
    protected var ivBackCard: CardView? = null
    protected var divider: View? = null


    internal var title: String? = null
        set(value) {
            field = value
            initUI()
        }

    internal var showDivider: Boolean = false
        set(value) {
            field = value
            initUI()
        }


    internal var tintColor: ColorStateList? = null

    internal var textColor: ColorStateList? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.BaseTitleBar, 0, 0)
        title = ta.getString(R.styleable.BaseTitleBar_title)

        showDivider = ta.getBoolean(R.styleable.BaseTitleBar_showDivider,false)






        ta.recycle()

        init(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    open val layoutID: Int = R.layout.component_base_title_bar

    protected open fun init(context: Context) {

        foregroundGravity = Gravity.CENTER_VERTICAL

        val inflater = LayoutInflater.from(context)
        mRootView = inflater.inflate(layoutID, this)
        prepareItems()

        //DUMMY DATA
    }

    open fun prepareItems() {
        mRootView = mRootView!!.findViewById(R.id.clTopBar)
        tvTitle = mRootView!!.findViewById(R.id.tvTitle)
        ivBackCard = mRootView!!.findViewById(R.id.ivBackCard)
        divider = mRootView!!.findViewById(R.id.divider)


        initUI()
    }


    internal fun initUI() {
        tvTitle?.text = title
        divider?.setVisible(showDivider)

    }


    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        mRootView?.visibility = visibility
    }

    override fun setOnClickListener(l: OnClickListener?) {
        mRootView?.setOnClickListener {
            l?.onClick(it)
        }
    }

     public fun setOnBack(l: OnClickListener?) {
         ivBackCard?.setOnClickListener {
             l?.onClick(it)
         }
    }


}


