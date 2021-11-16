package com.studywithme.app.present.models

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.studywithme.app.business.providers.IRoomProvider
import com.studywithme.app.business.providers.Result
import com.studywithme.app.objects.AbstractRoom
import com.studywithme.app.present.State
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RoomListViewModel(private val internetCheck: InternetCheck) : ViewModel(), KoinComponent {
    private val handler = Handler(Looper.getMainLooper())
    private val provider by inject<IRoomProvider>()
    private val state = MutableLiveData<State<List<AbstractRoom>>>()

    var query = ""
        private set

    fun getState(): LiveData<State<List<AbstractRoom>>> = state

    fun findAll() {
        postponedQuery()
    }

    private fun postponedQuery() {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(delayInMillis = 600) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        if (internetCheck.isOnline()) {
            state.postValue(State.Pending())
        } else {
            state.postValue(State.Fail(Throwable("miss internet")))
            return
        }

        provider.findAll {
            val newState = when (it) {
                is Result.Success -> State.Success(it.data)
                is Result.Fail -> State.Fail(it.error)
            }

            state.postValue(newState)
        }
    }

    interface InternetCheck {
        fun isOnline(): Boolean
    }
}
