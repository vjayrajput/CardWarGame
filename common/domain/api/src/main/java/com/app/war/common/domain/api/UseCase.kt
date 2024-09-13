package com.app.war.common.domain.api

sealed interface UseCase {

    interface Suspending<R> : UseCase {
        suspend operator fun invoke(): R
    }

    interface SuspendingParameterized<T, R> : UseCase {
        suspend operator fun invoke(param: T): R
    }
}
