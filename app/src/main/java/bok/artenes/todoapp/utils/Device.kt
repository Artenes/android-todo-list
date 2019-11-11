package bok.artenes.todoapp.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Device utility methods to wrap some messy API from Android.
 */
object Device {

    /**
     * Hide the soft keyboard in the given view that has focus.
     */
    fun hideKeyboardOn(view: View) {
        (view.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }

}