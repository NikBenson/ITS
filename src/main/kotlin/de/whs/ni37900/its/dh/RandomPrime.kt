package de.whs.ni37900.its.dh

import java.util.concurrent.ThreadLocalRandom
import kotlin.math.pow
import kotlin.random.Random

fun Random.nextPrime(): Long {
    return RandomPrime.default.nextPrime()
}

class RandomPrime(private val biggestSmallNumber: Long = 1_000, private val rabinMillerRepetitions: Int = 20) {
    companion object {
        val default: RandomPrime by lazy { RandomPrime() }
    }

    private val random: ThreadLocalRandom = ThreadLocalRandom.current()

    private val smallPrimes: List<Long> by lazy { sieveOfEratosthenes(biggestSmallNumber) }

    fun nextPrime(): Long {
        var candidate = nextCandidate()

        while (!highLevelPrimalityTest(candidate))
            candidate = nextCandidate()

        return candidate
    }

    private fun nextCandidate(): Long {
        var number = nextRandom()

        while (!lowLevelPrimalityTest(number))
            number = nextRandom()

        return number
    }

    private fun nextRandom(): Long {
        return (1L shl Random.nextInt(10, Long.SIZE_BITS / 2)) - 1L
    }


    private fun lowLevelPrimalityTest(n: Long): Boolean {
        return smallPrimes.all { n % it != 0L }
    }

    private fun sieveOfEratosthenes(n: Long): List<Long> {
        val candidates: MutableMap<Long, Boolean> = (2L..n).associateWith { true }.toMutableMap()

        var p: Long = 2
        while (p * p <= n) {
            if (candidates[p]!!) {
                var i = p * p
                while (i < n) {
                    candidates[i] = false
                    i += p
                }
            }

            p++
        }

        return candidates.filter { it.value }.map { it.key }
    }

    private fun highLevelPrimalityTest(n: Long): Boolean {
        var maxDivisionsByTwo: Int = 0
        var evenComponent: Long = n - 1L

        while (evenComponent % 2 == 0L) {
            evenComponent = evenComponent shr 1
            maxDivisionsByTwo += 1
        }

        return (1..rabinMillerRepetitions).all { rabinMillerPrimalityTest(n, evenComponent, maxDivisionsByTwo) }
    }

    private fun rabinMillerPrimalityTest(n: Long, evenComponent: Long, maxDivisionsByTwo: Int): Boolean {
        return !trialComposite(random.nextLong(2, n + 1L), evenComponent, n, maxDivisionsByTwo)
    }

    private fun trialComposite(
        roundTester: Long, evenComponent: Long,
        millerRabinCandidate: Long, maxDivisionsByTwo: Int
    ): Boolean {
        if (expmod(roundTester, evenComponent, millerRabinCandidate) == 1L) return false
        for (i in 0..<maxDivisionsByTwo) {
            if (expmod(
                    roundTester, (1 shl i) * evenComponent,
                    millerRabinCandidate
                ) == millerRabinCandidate - 1
            ) return false
        }
        return true
    }

    private fun expmod(base: Long, exp: Long, mod: Long): Long {
        if (exp == 0L) return 1
        return if (exp % 2 == 0L) {
            expmod(base, exp / 2L, mod).toDouble().pow(2.0).toLong() % mod
        } else {
            base * expmod(base, exp - 1, mod) % mod
        }
    }
}