package com.studywithme.app.present.models

import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.studywithme.app.business.providers.IGlideProvider
import com.studywithme.app.business.providers.IUserProvider
import com.studywithme.app.business.providers.Result
import com.studywithme.app.objects.AbstractUser
import com.studywithme.app.objects.user.User
import com.studywithme.app.present.State
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserProfileViewModel : ViewModel(), KoinComponent {
    private val handler = Handler(Looper.getMainLooper())
    private val provider by inject<IUserProvider>()
    private val state = MutableLiveData<State<AbstractUser>>()

    fun getState(): LiveData<State<AbstractUser>> = state

    fun getUser(query: Int) {
        postponedQuery(query)
    }

    fun putUser(query: User) {
        postponedQuery(query)
    }

    private fun postponedQuery(query: Int) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(delayInMillis = 500) {
            makeRequest(query)
        }
    }

    private fun postponedQuery(query: User) {
        handler.removeCallbacksAndMessages(null)
        handler.postDelayed(delayInMillis = 500) {
            makeRequest(query)
        }
    }

    private fun makeRequest(query: Int) {
        state.postValue(State.Pending())

        provider.getUserById(query) {
            val newState = when (it) {
                is Result.Success -> State.Success(it.data)
                is Result.Fail -> State.Fail(it.error)
            }

            state.postValue(newState)
        }
    }

    private fun makeRequest(query: User) {
        state.postValue(State.Pending())

        provider.postUser(query) {
            val newState = when (it) {
                is Result.Success -> State.Success(it.data)
                is Result.Fail -> State.Fail(it.error)
            }

            state.postValue(newState)
        }
    }
}
