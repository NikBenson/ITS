package de.whs.ni37900.its.dsa

import java.math.BigInteger
import java.util.*


private val rabinMillerRepetitions: UInt = 20u

private val biggestSmallNumber = BigInteger.valueOf(1_000L)
private val smallPrimes: Set<BigInteger> by lazy { sieveOfEratosthenes(biggestSmallNumber) }

fun Random.nextBigPrime(
    from: BigInteger,
    until: BigInteger,
    nextBigInteger: (from: BigInteger, until: BigInteger) -> BigInteger = { from, until ->
        this.nextBigInteger(
            from,
            until
        )
    },
): BigInteger {
    val smallPrimes = smallPrimes.filter { it < from }.toSet()
    fun lowLevelPrimalityTest(n: BigInteger): Boolean {
        return smallPrimes.all { n % it != BigInteger.ZERO }
    }

    fun highLevelPrimalityTest(n: BigInteger): Boolean {
        var maxDivisionsByTwo = 0
        var evenComponent: BigInteger = n - BigInteger.ONE

        while (evenComponent % BigInteger.TWO == BigInteger.ZERO) {
            evenComponent = evenComponent shr 1
            maxDivisionsByTwo += 1
        }

        return (1u..rabinMillerRepetitions).all { rabinMillerPrimalityTest(n, evenComponent, maxDivisionsByTwo) }
    }

    var candidate: BigInteger
    do {
        candidate = nextBigInteger(from, until)
    } while (!(lowLevelPrimalityTest(candidate) && highLevelPrimalityTest(candidate)))
    return candidate
}

fun Random.nextBigInteger(from: BigInteger, until: BigInteger): BigInteger {
    var candidate: BigInteger
    do {
        candidate = BigInteger(until.bitLength(), this)
    } while (!(from <= candidate && candidate < until))
    return candidate
}

private fun sieveOfEratosthenes(n: BigInteger): Set<BigInteger> {
    val eliminated: MutableSet<BigInteger> = mutableSetOf()

    var p: BigInteger = BigInteger.TWO
    while (p * p <= n) {
        if (!eliminated.contains(p)) {
            var i = p * p
            while (i < n) {
                eliminated.add(i)
                i += p
            }
        }

        p++
    }

    var i = BigInteger.TWO
    val primes: MutableSet<BigInteger> = mutableSetOf()
    while (i <= n) {
        if (!eliminated.contains(i))
            primes.add(i)

        i++
    }

    return primes
}

private fun Random.rabinMillerPrimalityTest(n: BigInteger, evenComponent: BigInteger, maxDivisionsByTwo: Int): Boolean {
    return !trialComposite(nextBigInteger(BigInteger.TWO, n + BigInteger.ONE), evenComponent, n, maxDivisionsByTwo)
}

private fun trialComposite(
    roundTester: BigInteger, evenComponent: BigInteger,
    millerRabinCandidate: BigInteger, maxDivisionsByTwo: Int
): Boolean {
    if (roundTester.modPow(evenComponent, millerRabinCandidate) == BigInteger.ONE)
        return false

    for (i in 0..<maxDivisionsByTwo) {
        if (
            roundTester.modPow(
                (BigInteger.ONE shl i) * evenComponent,
                millerRabinCandidate
            ) == millerRabinCandidate - BigInteger.ONE
        ) return false
    }
    return true
}
