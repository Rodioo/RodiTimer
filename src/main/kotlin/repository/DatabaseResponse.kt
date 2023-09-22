package repository

enum class DatabaseResponseType {
    SUCCESS,
    FAILED
}

class DatabaseResponse<T>(
    private val type: DatabaseResponseType,
    val data: T,
    val exceptionMessage: String? = null
) {
    val isSuccessful : Boolean
        get() = type == DatabaseResponseType.SUCCESS

    val isFailed : Boolean
        get() = type == DatabaseResponseType.FAILED
}