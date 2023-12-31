package alejo.meli.core.data

import alejo.meli.core.domain.DomainException

class DomainErrorFactory {

    fun build(error: Throwable): DomainException {
        return when {
            error is DomainException -> error
            error.isConnectionError() -> DomainException("ERROR CONNECTION")
            else -> handleUnrecognizedError(error)
        }
    }

    private fun handleUnrecognizedError(error: Throwable): DomainException {
        return DomainException(error.localizedMessage ?: "ERROR NOT FOUND")
    }
}
