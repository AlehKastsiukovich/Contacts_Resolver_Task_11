package by.itacademy.training.contactsresolver.util

class Event<out T>(
    val status: Status,
    val data: T?,
    val message: String?
) {
    companion object {

        fun <T> success(data: T): Event<T> {
            return Event(Status.SUCCESS, data, null)
        }

        fun <T> loading(data: T?): Event<T> {
            return Event(Status.LOADING, data, null)
        }

        fun <T> error(data: T?, message: String?): Event<T> {
            return Event(Status.ERROR, data, message)
        }
    }
}

enum class Status {
    LOADING,
    SUCCESS,
    ERROR
}
