package com.curve.delivery.new_architecture.helper

sealed class EmpResource<out T> {
    data class Success<out T>(val value: T) : EmpResource<T>()
    data class Failure(val throwable: Throwable?) : EmpResource<Nothing>()
    object Loading : EmpResource<Nothing>()
//    object Empty : EmpResource<Nothing>()
}