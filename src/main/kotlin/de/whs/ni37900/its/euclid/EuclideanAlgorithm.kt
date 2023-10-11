package de.whs.ni37900.its.euclid

import kotlin.math.max
import kotlin.math.min

object EuclideanAlgorithm {
    operator fun invoke(a: UInt, b: UInt): UInt {
        return execute(max(a, b), min(a, b))
    }

    private fun execute(dividend: UInt, divisor: UInt): UInt {
        return when(val remainder = dividend % divisor) {
            0u -> divisor
            else -> execute(divisor, remainder)
        }
    }
}