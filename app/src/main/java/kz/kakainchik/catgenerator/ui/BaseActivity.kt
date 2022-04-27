package kz.kakainchik.catgenerator.ui

import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import io.ktor.client.plugins.*
import kz.kakainchik.catgenerator.R
import kz.kakainchik.catgenerator.vm.BaseViewModel
import java.net.UnknownHostException

abstract class BaseActivity : AppCompatActivity() {
    protected abstract val viewModel: BaseViewModel

    protected abstract fun onDataProcessing()
    protected abstract fun onDataProcessingCompleted()

    protected fun observeProcessingStatus() {
        viewModel.isProcessing.observe(this, Observer {
            if(it) onDataProcessing()
            else onDataProcessingCompleted()
        })
    }

    protected open fun observeError(layout: View) {
        viewModel.operationError.observe(this, Observer {
            when(it) {
                is UnknownHostException -> showSnackbar(layout, R.string.internet_connection_error)
                is ClientRequestException -> showSnackbar(layout, R.string.client_request_error)
                is ServerResponseException -> showSnackbar(layout, R.string.server_response_error)
            }
        })
    }

    protected open fun showSnackbar(layout: View, @StringRes resId: Int) {
        Snackbar.make(layout, resId, Snackbar.LENGTH_SHORT).show()
    }
}