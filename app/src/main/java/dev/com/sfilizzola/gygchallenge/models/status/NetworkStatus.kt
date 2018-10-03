package dev.com.sfilizzola.gygchallenge.models.status

sealed class NetworkStatus {
    object Success: NetworkStatus()
    object Loading: NetworkStatus()
    data class Error(var error: String?) : NetworkStatus()
}