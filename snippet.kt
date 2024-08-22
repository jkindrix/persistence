package com.luvncare.permissions

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

/**
 * Represents the various states related to permission requests in an application.
 */
sealed class PermissionState {
    data object Idle : PermissionState()
    data object Requesting : PermissionState()
    data object Granted : PermissionState()
    data object Denied : PermissionState()
    data object PermanentlyDenied : PermissionState()
    data object OneTimePermissionExpired : PermissionState()
    data object ForegroundPermissionGranted : PermissionState()
    data object Pending : PermissionState()
    data object Unknown : PermissionState()

    @Serializable
    data class SpecialPermission(val permissionName: String, val explanation: String? = null) : PermissionState()

    @Serializable
    data class BackgroundPermissionRequired(val permissionName: String) : PermissionState()

    @Serializable
    data class DynamicPermissionRequired(val permissionName: String) : PermissionState()

    @Serializable
    data class SystemPermission(val permissionName: String) : PermissionState()

    @Serializable
    data class Error(val message: String, @Contextual val cause: Throwable? = null) : PermissionState()

    companion object {
        fun fromJson(json: String): PermissionState {
            return try {
                Json.decodeFromString<PermissionState>(json)
            } catch (e: SerializationException) {
                Unknown
            }
        }
    }
}

// Extension function in a separate file
fun PermissionState.toJson(): String {
    return Json.encodeToString(this)
}
