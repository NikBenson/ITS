package de.whs.ni37900.its.dh

fun interface Combinable<in T, out U> {
    operator fun plus(other: T): U
}