package de.whs.ni37900.its.dsa

import java.math.BigInteger
import java.util.*

open class DigitalSignatureAlgorithmFixedParameters(
    val keyLength: UInt,
    val n: UInt,
    open val hash: (ByteArray) -> BigInteger,
) {
    companion object {
        val original = DigitalSignatureAlgorithmFixedParameters(keyLength = 1024u, n = 160u, hash = { it.sha1 })
    }

    fun generate(random: Random = Random()): DigitalSignatureAlgorithmGeneratedParameters {
        val q: BigInteger = random.nextBigPrime(BigInteger.TWO.pow((n - 1u).toInt()), BigInteger.TWO.pow(n.toInt()))
        val p: BigInteger = random.nextBigPrime(
            BigInteger.TWO.pow((keyLength - 1u).toInt()), BigInteger.TWO.pow(keyLength.toInt())
        ) { from, until ->
            q * random.nextBigInteger(from / q, until / q) + BigInteger.ONE
        }

        var h: BigInteger
        var g: BigInteger
        do {
            h = random.nextBigInteger(BigInteger.TWO, p - BigInteger.ONE)
            g = h.modPow((p - BigInteger.ONE) / q, p)
        } while (g == BigInteger.ONE)

        return DigitalSignatureAlgorithmGeneratedParameters(
            keyLength = keyLength,
            n = n,
            hash = hash,
            q = q,
            p = p,
            h = h,
            g = g,
        )
    }

    override fun toString(): String {
        return "{\n\tL='%s'\n\tN='%s',\n}".format(keyLength, n)
    }
}