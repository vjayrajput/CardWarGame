package com.app.war.common.general.extensions

fun Boolean?.orFalse(): Boolean = this ?: false
