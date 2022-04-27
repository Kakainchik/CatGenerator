package kz.kakainchik.catgenerator.vm

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.kakainchik.catgenerator.util.OperationErrorType

abstract class BaseViewModel : ViewModel() {
    private val _operationError = MutableLiveData<OperationErrorType>()

    val operationError: MediatorLiveData<OperationErrorType> = MediatorLiveData()
    val isProcessing = MutableLiveData<Boolean>()

    init {
        operationError.addSource(_operationError) {
            operationError.postValue(it)
            isProcessing.value = false
        }
    }

    protected fun handleError(t: OperationErrorType) {
        _operationError.postValue(t)
    }
}