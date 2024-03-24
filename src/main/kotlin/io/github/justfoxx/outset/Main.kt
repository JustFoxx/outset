@file:JvmName("Main")

package io.github.justfoxx.outset

import net.minecraft.util.Identifier
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

const val MOD_ID = "outsets"

fun init() {
    getLogger().info("Hello World!")
    Events()
}

fun KClass<*>.getLogger(): Logger {
    return LoggerFactory.getLogger("$MOD_ID/${this.simpleName}")
}

fun getLogger(): Logger {
    return LoggerFactory.getLogger(MOD_ID)
}

fun String.getId() = Identifier(MOD_ID, this)
