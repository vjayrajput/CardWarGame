package com.app.war.common.domain.api

interface Mapper<R, E> {
    fun mapTo(type: R?): E
}
