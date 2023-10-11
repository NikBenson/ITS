package de.whs.ni37900.its.dh

import kotlin.math.sqrt
import kotlin.random.Random


class FiniteFieldDiffieHellmann :
    DiffieHellman<FiniteFieldDiffieHellmanPublicKey, FiniteFieldDiffieHellmanPrivateKey, FiniteFieldDiffieHellmanCombinedKey, FiniteFieldDiffieHellmanKey, Long, Long>(
        createPublicKey = { Random.nextPrime().let { FiniteFieldDiffieHellmanPublicKey(p = it, g = primitiveRoot(it)) } },
        createPrivateKey = { FiniteFieldDiffieHellmanPrivateKey(Random.nextLong(0, 10)) },
        encodeMessage = { message, key -> message + key },
        decodeMessage = { message, key -> message - key })

class FiniteFieldDiffieHellmanPublicKey(val p: Long, val g: Long) :
    Combinable<FiniteFieldDiffieHellmanPrivateKey, FiniteFieldDiffieHellmanCombinedKey> {
    override fun plus(other: FiniteFieldDiffieHellmanPrivateKey): FiniteFieldDiffieHellmanCombinedKey {
        val publicKey = this
        val privateKey = other

        return FiniteFieldDiffieHellmanCombinedKey(publicKey.g.pow(privateKey.value).mod(publicKey.p))
    }
}

class FiniteFieldDiffieHellmanPrivateKey(val value: Long) :
    Combinable<FiniteFieldDiffieHellmanPublicKey, FiniteFieldDiffieHellmanCombinedKey> {
    override fun plus(other: FiniteFieldDiffieHellmanPublicKey): FiniteFieldDiffieHellmanCombinedKey {
        val publicKey = other
        val privateKey = this

        return FiniteFieldDiffieHellmanCombinedKey(publicKey.g.pow(privateKey.value).mod(publicKey.p))
    }
}

class FiniteFieldDiffieHellmanCombinedKey(val value: Long) :
    Combinable<Pair<FiniteFieldDiffieHellmanPublicKey, FiniteFieldDiffieHellmanPrivateKey>, FiniteFieldDiffieHellmanKey> {
    override fun plus(other: Pair<FiniteFieldDiffieHellmanPublicKey, FiniteFieldDiffieHellmanPrivateKey>): FiniteFieldDiffieHellmanKey {
        val publicKey = other.first
        val privateKey = other.second
        val combinedKey = this

        return combinedKey.value.pow(privateKey.value).mod(publicKey.p)
    }
}

typealias FiniteFieldDiffieHellmanKey = Long

fun Long.pow(exponent: Long): Long {
    require(exponent >= 0)

    if (exponent == 0L)
        return 1

    var res: Long = this

    for (i in 0..<exponent)
        res *= this

    return res
}

fun primitiveRoot(n: Long): Long {
    fun power(pX: Long, pY: Long, p: Long): Long
    {
        var x = pX
        var y = pY
        var res: Long = 1L

        x %= p

        while (y > 0)
        {
            // If y is odd, multiply x with result
            if (y % 2 == 1L)
            {
                res = (res * x) % p
            }

            // y must be even now
            y = y shr 1 // y = y/2
            x = (x * x) % p
        }
        return res;
    }

    // Utility function to store prime factors of a number
    fun findPrimefactors(s: MutableSet<Long>, pN: Long)
    {
        var n = pN
        // Print the number of 2s that divide n
        while (n % 2 == 0L)
        {
            s.add(2)
            n /= 2
        }

        // n must be odd at this point. So we can skip
        // one element (Note i = i +2)
        for (i in 3..<  sqrt(n.toDouble()).toLong() step 2)
        {
            // While i divides n, print i and divide n
            while (n % i == 0L)
            {
                s.add(i)
                n /= i
            }
        }

        // This condition is to handle the case when
        // n is a prime number greater than 2
        if (n > 2)
        {
            s.add(n)
        }
    }

    val s = mutableSetOf<Long>()

    // Find value of Euler Totient function of n
    // Since n is a prime number, the value of Euler
    // Totient function is n-1 as there are n-1
    // relatively prime numbers.

    // Find value of Euler Totient function of n
    // Since n is a prime number, the value of Euler
    // Totient function is n-1 as there are n-1
    // relatively prime numbers.
    val phi = n - 1

    // Find prime factors of phi and store in a set

    // Find prime factors of phi and store in a set
    findPrimefactors(s, phi)

    // Check for every number from 2 to phi

    // Check for every number from 2 to phi
    for (r in 2..phi) {
        // Iterate through all prime factors of phi.
        // and check if we found a power with value 1
        var flag = false
        for (a in s) {

            // Check if r^((phi)/primefactors) mod n
            // is 1 or not
            if (power(r, phi / a, n) == 1L) {
                flag = true
                break
            }
        }

        // If there was no power with value 1.
        if (!flag) {
            return r
        }
    }

    throw Exception("No Primitive Root found!")
}