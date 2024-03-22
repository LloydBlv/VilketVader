package com.example.data

import assertk.Assert
import assertk.all
import assertk.assertions.isEqualTo
import assertk.assertions.support.appendName
import kotlin.reflect.KProperty1


//fun <T: Any> Assert<T>.isEqualToIgnoringFields(other: T, vararg properties: KProperty1<T, Any?>) {
//    all {
//        other::class.members
//            .filterIsInstance<KProperty1<T, Any?>>()
//            .filter { it !in properties}
//            .forEach { prop: KProperty1<T, Any?> ->
//                transform(
//                    name = appendName(prop.name, separator = "."),
//                    transform = prop::get
//                )
//                    .isEqualTo(prop.get(other))
//            }
//    }
//}