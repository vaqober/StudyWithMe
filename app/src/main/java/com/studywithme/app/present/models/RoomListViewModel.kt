package com.studywithme.app.present.models

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.studywithme.app.business.providers.INetworkProvider
import com.studywithme.app.business.providers.IRoomProvider
import com.studywithme.app.business.providers.Result
import com.studywithme.app.objects.AbstractRoom
import com.studywithme.app.present.State
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RoomListViewModel : ViewModel(), KoinComponent {
    private val handler = Handler(Looper.getMainLooper())
    private val provider by inject<IRoomProvider>()
    private val providerNetwork by inject<INetworkProvider>()
    private val state = MutableLiveData<State<List<AbstractRoom>>>()

    fun getState(): LiveData<State<List<AbstractRoom>>> = state

    fun findRooms(query: String) {
        postponedQuery(query)
    }

    private fun postponedQuery(query: String) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(delayInMillis = 500) {
            if (providerNetwork.isConnected()) {
                makeRequest(query)
            } else {
                state.postValue(State.Fail(Throwable("miss internet")))
            }
        }
    }

    private fun makeRequest(query: String) {
        state.postValue(State.Pending())
        provider.findRooms(query) {
            val newState = when (it) {
                is Result.Success -> State.Success(it.data)
                is Result.Fail -> State.Fail(it.error)
            }

            state.postValue(newState)
        }
    }
}
