package kz.kakainchik.catgenerator.util

import io.ktor.client.plugins.*
import java.net.UnknownHostException

fun Throwable.mapToError(): OperationErrorType =
    when(this) {
        is UnknownHostException -> OperationErrorType.CONNECTION_ERROR
        is ClientRequestException -> OperationErrorType.PROCESSING_ERROR
        is ServerResponseException -> OperationErrorType.RESPONSE_ERROR
        else -> OperationErrorType.CONNECTION_ERROR
    }