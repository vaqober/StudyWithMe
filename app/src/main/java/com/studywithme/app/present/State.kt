package com.studywithme.app.present

sealed interface State<TYPE> {
    data class Success<TYPE>(var data: TYPE): State<TYPE>
    data class Fail<TYPE>(var error: Throwable?): State<TYPE>
    class Pending<TYPE>: State<TYPE>
}