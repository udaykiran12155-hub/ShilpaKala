package com.example.shilpakalashowcase

import androidx.lifecycle.ViewModel
import com.example.shilpakalashowcase.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _currentUser = MutableStateFlow<UserProfile?>(null)
    val currentUser: StateFlow<UserProfile?> = _currentUser.asStateFlow()

    private val _orderHistory = MutableStateFlow<List<OrderHistory>>(emptyList())
    val orderHistory: StateFlow<List<OrderHistory>> = _orderHistory.asStateFlow()

    private val _settings = MutableStateFlow(AppSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    init {
        // Mocking a logged-in user for development
        _currentUser.value = UserProfile(
            uid = "user_123",
            name = "Arshid Ahmad Malik",
            email = "malikarshid01430@gmail.com",
            role = UserRole.BUYER,
            phone = "9682397579",
            bio = "Art enthusiast exploring traditional stone carvings."
        )
        
        // Mocking history
        _orderHistory.value = listOf(
            OrderHistory("H1", "user_123", "1", "Stone Buddha", "Shilpi Arshid", 1200.0),
            OrderHistory("H2", "user_123", "2", "Wooden Elephant", "Shilpi Arshid", 450.0)
        )
    }

    fun logout() {
        _currentUser.value = null
    }

    fun login(email: String) {
        // Logic for login
        _currentUser.value = UserProfile(
            uid = "user_123",
            name = "Arshid Ahmad Malik",
            email = email,
            role = UserRole.BUYER
        )
    }

    fun updateProfile(profile: UserProfile) {
        _currentUser.value = profile
    }

    fun updateSettings(settings: AppSettings) {
        _settings.value = settings
    }
    
    fun switchAccount(role: UserRole) {
        _currentUser.value = _currentUser.value?.copy(role = role)
    }
}
