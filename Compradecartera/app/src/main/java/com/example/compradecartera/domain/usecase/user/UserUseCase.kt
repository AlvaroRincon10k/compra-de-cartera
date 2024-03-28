package com.example.compradecartera.domain.usecase.user

import com.example.compradecartera.domain.model.UserResponse
import com.example.compradecartera.domain.repository.UserRepository

class UserUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(name:String): UserResponse = userRepository.getUser(name)
}
