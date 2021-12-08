package com.studywithme.app.present.models

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.studywithme.app.business.providers.IUserProvider
import com.studywithme.app.business.providers.Result
import com.studywithme.app.objects.AbstractUser
import com.studywithme.app.objects.user.User
import com.studywithme.app.present.State
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UsersListViewModel : ViewModel(), KoinComponent {
    private val handler = Handler(Looper.getMainLooper())
    private val provider by inject<IUserProvider>()
    private val state = MutableLiveData<State<AbstractUser>>()

    var query = ""
        private set

    fun getState(): LiveData<State<AbstractUser>> = state

    fun postUser(user: User) {
        postponedQuery(user)
    }

    fun getUser(id: String) {
        postponedQuery(id)
    }

    private fun postponedQuery(id: String) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(delayInMillis = 600) {
            makeRequest(id)
        }
    }

    private fun makeRequest(id: String) {
        state.postValue(State.Pending())

        provider.getUser(id) {
            val newState = when (it) {
                is Result.Success -> State.Success(it.data)
                is Result.Fail -> State.Fail(it.error)
            }

            state.postValue(newState)
        }
    }

    private fun postponedQuery(user: User) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(delayInMillis = 600) {
            makeRequest(user)
        }
    }

    private fun makeRequest(user: User) {
        state.postValue(State.Pending())

        provider.postUser(user) {
            val newState = when (it) {
                is Result.Success -> State.Success(it.data)
                is Result.Fail -> State.Fail(it.error)
            }

            state.postValue(newState)
        }
    }
}
