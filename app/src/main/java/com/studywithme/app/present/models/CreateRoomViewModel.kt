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
import com.studywithme.app.objects.room.Room
import com.studywithme.app.present.State
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CreateRoomViewModel(private val internetCheck: InternetCheck) : ViewModel(), KoinComponent {
    private val handler = Handler(Looper.getMainLooper())
    private val provider by inject<IRoomProvider>()
    private val state = MutableLiveData<State<AbstractRoom>>()

    var query = ""
        private set

    fun getState(): LiveData<State<AbstractRoom>> = state

    fun postRoom(room: Room) {
        postponedQuery(room)
    }

    private fun postponedQuery(room: Room) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(delayInMillis = 600) {
            makeRequest(room)
        }
    }

    private fun makeRequest(room: Room) {
        if (internetCheck.isOnline()) {
            state.postValue(State.Pending())
        } else {
            state.postValue(State.Fail(Throwable("miss internet")))
            return
        }

        provider.postRoom(room) {
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
