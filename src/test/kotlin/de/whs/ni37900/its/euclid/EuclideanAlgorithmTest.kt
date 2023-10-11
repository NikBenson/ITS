package de.whs.ni37900.its.euclid

import org.junit.jupiter.api.Assertions.*

class EuclideanAlgorithmTest {

    @org.junit.jupiter.api.Test
    operator fun invoke() {
        assertEquals(5u, EuclideanAlgorithm(10u, 15u))
        assertEquals(7u, EuclideanAlgorithm(56u, 91u))
        assertEquals(16u, EuclideanAlgorithm(368u, 192u))
        assertEquals(2u, EuclideanAlgorithm(34u, 172u))
    }
}