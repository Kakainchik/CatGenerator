package kz.kakainchik.catgenerator.util

sealed class ApiResponse<out S> {
    data class Success<S>(val body: S) : ApiResponse<S>()
    data class Error(val type: OperationErrorType) : ApiResponse<Nothing>()
}