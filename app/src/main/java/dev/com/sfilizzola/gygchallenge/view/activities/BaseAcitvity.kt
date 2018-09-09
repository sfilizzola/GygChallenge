package dev.com.sfilizzola.gygchallenge.view.activities

import android.graphics.Color
import android.support.design.widget.Snackbar
import android.view.View
import dagger.android.support.DaggerAppCompatActivity
import dev.com.sfilizzola.gygchallenge.R

open class BaseAcitvity : DaggerAppCompatActivity() {

    private var snackbar: Snackbar? = null

    protected fun displaySnackbarMessage(root: View, message: String, actionListener: View.OnClickListener?) {

        snackbar?.let {
            if(it.isShown) {
                it.dismiss()
            }
        }

        snackbar = Snackbar.make(root, message, Snackbar.LENGTH_LONG)
        snackbar?.setActionTextColor(Color.RED)?.setAction(R.string.retry_connection, actionListener)?.duration = Snackbar.LENGTH_INDEFINITE
        snackbar?.show()

    }
}