package kz.kakainchik.catgenerator.vm

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private val _operationError = MutableLiveData<Throwable>()

    val operationError: MediatorLiveData<Throwable> = MediatorLiveData()
    val isProcessing = MutableLiveData<Boolean>()

    init {
        operationError.addSource(_operationError) {
            operationError.postValue(it)
            isProcessing.value = false
        }
    }

    protected fun handleError(t: Throwable) {
        _operationError.postValue(t)
    }
}