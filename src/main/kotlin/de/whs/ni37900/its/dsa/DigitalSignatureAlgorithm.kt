package de.whs.ni37900.its.dsa

import java.math.BigInteger
import java.util.*

class DigitalSignatureAlgorithm(
    parameters: DigitalSignatureAlgorithmFixedParameters = DigitalSignatureAlgorithmFixedParameters.original,
    private val random: Random = Random(),
) {
    val parameters: DigitalSignatureAlgorithmParameters
    val keyPair: DigitalSignatureAlgorithmKeyPair

    init {
        this.parameters = parameters.generate(random)
        keyPair = DigitalSignatureAlgorithmKeyPair.generateWith(this.parameters)
    }

    fun sign(
        message: ByteArray,
    ): DigitalSignatureAlgorithmSignature {
        var r: BigInteger
        var s: BigInteger
        do {
            val k = random.nextBigInteger(BigInteger.ONE, parameters.q)
            r = parameters.g.modPow(k, parameters.p) % parameters.q
            s =
                (k.modInverse(parameters.q) * ((parameters.hash(message) + keyPair.privateKey * r) % parameters.q)) % parameters.q
        } while (r == BigInteger.ZERO || s == BigInteger.ZERO)

        return DigitalSignatureAlgorithmSignature(r, s)
    }

    fun verify(
        message: ByteArray,
        signature: DigitalSignatureAlgorithmSignature,
    ): Boolean {
        if (!(signature.r > BigInteger.ZERO && signature.r < parameters.q && signature.s > BigInteger.ZERO && signature.s < parameters.q))
            return false

        val w = signature.s.modInverse(parameters.q)
        val u1 = (parameters.hash(message) * w) % parameters.q
        val u2 = (signature.r * w) % parameters.q
        val v = (parameters.g.modPow(u1, parameters.p) * keyPair.publicKey.modPow(
            u2,
            parameters.p
        )) % parameters.p % parameters.q

        return v == signature.r
    }
}