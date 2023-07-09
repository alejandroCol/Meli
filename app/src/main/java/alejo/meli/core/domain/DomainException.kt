package alejo.meli.core.domain

open class DomainException @JvmOverloads constructor(override val message: String? = null) : Throwable(message)
