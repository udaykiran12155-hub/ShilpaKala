package com.example.shilpakalashowcase.data

enum class UserRole {
    ARTIST, BUYER
}

data class UserProfile(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val role: UserRole = UserRole.BUYER,
    val bio: String = "",
    val location: String = "",
    val specialization: String = "",
    val profileImage: String = "",
    val phone: String = "",
    val joinedDate: Long = System.currentTimeMillis()
)

data class Artwork(
    val id: String = "",
    val artistId: String = "",
    val artistName: String = "",
    val title: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val category: String = "",
    val style: String = "",
    val price: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis()
)

data class OrderHistory(
    val id: String = "",
    val buyerId: String = "",
    val artworkId: String = "",
    val artworkTitle: String = "",
    val artistName: String = "",
    val price: Double = 0.0,
    val status: String = "Inquiry Sent",
    val timestamp: Long = System.currentTimeMillis()
)

data class TimelineStep(
    val id: String = "",
    val artworkId: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val date: Long = System.currentTimeMillis()
)

data class HeritageStory(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val imageUrl: String = ""
)

data class AppSettings(
    val notificationsEnabled: Boolean = true,
    val language: String = "English",
    val themeMode: String = "System"
)
