package com.example.compradecartera.presentation.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compradecartera.domain.model.UserResponse
import com.example.compradecartera.domain.usecase.user.UserUseCase
import kotlinx.coroutines.launch

internal class DashBoardViewModel(
    private val getUserUseCase: UserUseCase
): ViewModel() {

    private val userMutableLiveData = MutableLiveData<UserResponse>()
    val userLiveData: LiveData<UserResponse> = userMutableLiveData

    fun getUser(name: String) {
        viewModelScope.launch {
            runCatching {
                val user = getUserUseCase.invoke(name)
                userMutableLiveData.value = user
            }.getOrElse {
                // emitir un eror y mostrarlo
            }
        }
    }

}