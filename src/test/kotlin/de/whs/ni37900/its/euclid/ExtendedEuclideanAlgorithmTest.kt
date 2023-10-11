package de.whs.ni37900.its.euclid

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class ExtendedEuclideanAlgorithmTest {

    @Test
    operator fun invoke() {
        validateResult(5u, ExtendedEuclideanAlgorithm(10u, 15u))
        validateResult(7u, ExtendedEuclideanAlgorithm(56u, 91u))
        validateResult(16u, ExtendedEuclideanAlgorithm(368u, 192u))
        validateResult(2u, ExtendedEuclideanAlgorithm(34u, 172u))
    }

    private fun validateResult(expected: UInt, actual: ExtendedEuclideanAlgorithm.Result) {
        assertEquals(expected, actual.gcd)
        assertEquals(actual.gcd.toInt(), actual.a.toInt() * actual.s + actual.b.toInt() * actual.t)
    }
}