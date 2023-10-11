package de.whs.ni37900.its.euclid

import kotlin.math.max
import kotlin.math.min

fun gcd(a: Int, b: Int) : Int {
    return ExtendedEuclideanAlgorithm(a.toUInt(), b.toUInt()).gcd.toInt()
}

object ExtendedEuclideanAlgorithm {
    operator fun invoke(a: UInt, b: UInt): Result {
        val dividend = max(a, b)
        val divisor = min(a, b)

        return execute(dividend, divisor, dividend, divisor, dividend, divisor, 1, 0, 0, 1)
    }

    private fun execute(
        a: UInt,
        b: UInt,
        dividend: UInt,
        divisor: UInt,
        prevRemainder: UInt,
        remainder: UInt,
        prevS: Int,
        s: Int,
        prevT: Int,
        t: Int,
    ): Result {
        val quotient: UInt = dividend / divisor

        val nextRemainder: UInt = prevRemainder - quotient * remainder
        val nextS: Int = prevS - quotient.toInt() * s
        val nextT: Int = prevT - quotient.toInt() * t

        return when (nextRemainder) {
            0u -> Result(a, b, remainder, s, t)
            else -> execute(a, b, divisor, nextRemainder, remainder, nextRemainder, s, nextS, t, nextT)
        }
    }

    data class Result(val a: UInt, val b: UInt, val gcd: UInt, val s: Int, val t: Int) {
        override fun toString(): String {
            return String.format(
                "gcd(%d,%d)=%d*%d%+d*%d=%d",
                a.toInt(),
                b.toInt(),
                s,
                a.toInt(),
                t,
                b.toInt(),
                gcd.toInt()
            )
        }
    }
}
