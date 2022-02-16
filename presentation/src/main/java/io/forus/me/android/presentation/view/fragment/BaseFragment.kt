package io.forus.me.android.presentation.view.fragment


import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

import io.forus.me.android.presentation.R
import io.forus.me.android.presentation.helpers.Converter
import io.forus.me.android.presentation.interfaces.FragmentListener

import io.forus.me.android.presentation.navigation.Navigator
import kotlinx.android.synthetic.main.toolbar_view.*

//import io.forus.me.android.presentation.internal.di.HasComponent;

/**
 * Base [android.app.Fragment] class for every fragment in this application.
 */
abstract class BaseFragment : Fragment(), FragmentListener {

    protected var navigator: Navigator = Navigator()


    override fun getTitle() : String {
        return  ""
    }


    protected open val subviewFragment: BaseFragment?
        get() = null

    protected open val allowBack: Boolean
        get() = true

    protected val toolbar: androidx.appcompat.widget.Toolbar?
        get() = toolbar_view

    protected open val toolbarTitle: String
        get() = ""

    protected open val toolbarType: ToolbarLRFragment.ToolbarType
        get() = ToolbarLRFragment.ToolbarType.Regular


    protected fun setToolbarTitle(title: String){
        toolbar_title.text = title

    }



    private fun initSubView() {
        if (subviewFragment != null) {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.subview, subviewFragment!!)
                    ?.commit()
        }

    }




    private fun setActionBarActivity( _activity: AppCompatActivity){
//
//                final Drawable upArrow = getResources().getDrawable(R.drawable.);
//                upArrow.setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.SRC_ATOP);

        _activity.setSupportActionBar(toolbar)
        FragmentHelper.setHomeIconToolbar(_activity, toolbar, profile_button,allowBack)
    }

    /**
     * Shows a [android.widget.Toast] message.
     *
     * @param message An string representing a message to be shown.
     */
    protected fun showToastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }


    /**
     * Lifecycle init ui method.
     */
    protected open fun initUI(){
        setToolbarTitle(toolbarTitle)
        if (toolbarType == ToolbarLRFragment.ToolbarType.Small){
            toolbar_title.setPadding(toolbar_title.paddingLeft, Converter.convertDpToPixel(5f, requireContext().applicationContext), toolbar_title.paddingRight, 0)
        }


        val castActivity = requireActivity()
        when (castActivity){
            is AppCompatActivity -> setActionBarActivity(castActivity)

        }

        initSubView()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? =
            if (getLayoutID() > 0) inflater.inflate(getLayoutID(), container, false) else super.onCreateView(inflater, container, savedInstanceState)


    /**
     * Lifecycle init ui method.
     */
    protected open fun getLayoutID(): Int{
        return 0
    }
    //  /**
    //   * Gets a component for dependency injection by its validator.
    //   */
    //  @SuppressWarnings("unchecked")
    //  protected <C> C getComponent(Class<C> componentType) {
    //    return componentType.cast(((HasComponent<C>) getActivity()).getComponent());
    //  }
}
