package com.studywithme.app.present.models

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.studywithme.app.business.providers.INetworkProvider
import com.studywithme.app.business.providers.IUserProvider
import com.studywithme.app.business.providers.Result
import com.studywithme.app.objects.AbstractUser
import com.studywithme.app.present.State
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MembersListViewModel : ViewModel(), KoinComponent {
    private val handler = Handler(Looper.getMainLooper())
    private val provider by inject<IUserProvider>()
    private val providerNetwork by inject<INetworkProvider>()
    private val state = MutableLiveData<State<List<AbstractUser>>>()

    var query = ""
        private set

    fun getState(): LiveData<State<List<AbstractUser>>> = state

    fun getMembers(roomId: String) {
        postponedQuery(roomId)
    }

    fun getAllUsers() {
        postponedQuery()
    }

    private fun postponedQuery() {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(delayInMillis = 600) {
            if (providerNetwork.isConnected()) {
                makeRequest()
            } else {
                state.postValue(State.Fail(Throwable("miss internet")))
            }
        }
    }

    private fun postponedQuery(roomId: String) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(delayInMillis = 600) {
            if (providerNetwork.isConnected()) {
                makeRequest(roomId)
            } else {
                state.postValue(State.Fail(Throwable("miss internet")))
            }
        }
    }

    private fun makeRequest() {
        state.postValue(State.Pending())

        provider.getAllUsers {
            val newState = when (it) {
                is Result.Success -> State.Success(it.data)
                is Result.Fail -> State.Fail(it.error)
            }

            state.postValue(newState)
        }
    }

    private fun makeRequest(roomId: String) {
        state.postValue(State.Pending())

        provider.getMembers(roomId) {
            val newState = when (it) {
                is Result.Success -> State.Success(it.data)
                is Result.Fail -> State.Fail(it.error)
            }

            state.postValue(newState)
        }
    }
}
