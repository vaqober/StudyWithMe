package com.studywithme.app.present.models

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.studywithme.app.business.providers.IMessageProvider
import com.studywithme.app.business.providers.INetworkProvider
import com.studywithme.app.business.providers.Result
import com.studywithme.app.objects.AbstractMessage
import com.studywithme.app.objects.message.Message
import com.studywithme.app.present.State
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ChatViewModel : ViewModel(), KoinComponent {
    private val handler = Handler(Looper.getMainLooper())
    private val provider by inject<IMessageProvider>()
    private val providerNetwork by inject<INetworkProvider>()
    private val state = MutableLiveData<State<List<AbstractMessage>>>()
    private val statePost = MutableLiveData<State<AbstractMessage>>()

    fun getState(): LiveData<State<List<AbstractMessage>>> = state

    fun allMessages(roomId: Int, query: String) {
        postponedSearch(roomId, query)
    }

    fun postMessage(roomId: Int, message: Message) {
        postponedSearch(roomId, message)
    }

    private fun postponedSearch(roomId: Int, query: String) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(delayInMillis = 600) {
            if (providerNetwork.isConnected()) {
                makeRequest(roomId, query)
            } else {
                state.postValue(State.Fail(Throwable("miss internet")))
            }
        }
    }

    private fun postponedSearch(roomId: Int, message: Message) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(delayInMillis = 600) {
            if (providerNetwork.isConnected()) {
                makeRequest(roomId, message)
            } else {
                state.postValue(State.Fail(Throwable("miss internet")))
            }
        }
    }

    private fun makeRequest(roomId: Int, query: String) {
        provider.allMessages(roomId, query) {
            val newState = when (it) {
                is Result.Success -> State.Success(it.data)
                is Result.Fail -> State.Fail(it.error)
            }

            state.postValue(newState)
        }
    }

    private fun makeRequest(roomId: Int, message: Message) {
        provider.postMessage(roomId, message) {
            val newState = when (it) {
                is Result.Success -> State.Success(it.data)
                is Result.Fail -> State.Fail(it.error)
            }

            statePost.postValue(newState)
        }
    }
}
