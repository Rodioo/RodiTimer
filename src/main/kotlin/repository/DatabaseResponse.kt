package repository

enum class DatabaseResponseType {
    SUCCESS,
    NEEDS_CONFIRMATION,
    FAILED,
}

class DatabaseResponse<T>(
    private val type: DatabaseResponseType,
    val data: T,
    val additionalMessage: String? = null
) {
    val isSuccessful : Boolean
        get() = type == DatabaseResponseType.SUCCESS

    val needsConfirmation : Boolean
        get() = type == DatabaseResponseType.NEEDS_CONFIRMATION

    val isFailed : Boolean
        get() = type == DatabaseResponseType.FAILED
}