package net.waymire.palaver.authorization

import mu.KLogger
import mu.KotlinLogging
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty
import kotlin.reflect.full.companionObject

interface Logging

inline fun <T : Any> getClassForLogging(javaClass: Class<T>): Class<*> {
    return javaClass.enclosingClass?.takeIf {
        it.kotlin.companionObject?.java == javaClass
    } ?: javaClass
}

inline fun <reified T : Logging> T.logger(): KLogger = KotlinLogging.logger {}

class LoggerDelegate<in R: Any> : ReadOnlyProperty<R, KLogger> {
    override fun getValue(thisRef: R, property: KProperty<*>): KLogger = KotlinLogging.logger(getClassForLogging(thisRef.javaClass).name)
}
