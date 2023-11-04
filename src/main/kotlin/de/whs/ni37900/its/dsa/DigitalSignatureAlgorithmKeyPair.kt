package de.whs.ni37900.its.dsa

import java.math.BigInteger
import java.util.*

data class DigitalSignatureAlgorithmKeyPair(val publicKey: BigInteger, val privateKey: BigInteger) {
    companion object {
        fun generateWith(
            parameters: DigitalSignatureAlgorithmParameters,
            random: Random = Random()
        ): DigitalSignatureAlgorithmKeyPair {
            val x: BigInteger = random.nextBigInteger(BigInteger.ONE, parameters.q)
            val y: BigInteger = parameters.g.modPow(x, parameters.p)
            return DigitalSignatureAlgorithmKeyPair(publicKey = y, privateKey = x)
        }
    }

    override fun toString(): String {
        return "{\n\tx='%s'\n\ty='%s',\n}".format(privateKey, publicKey)
    }
}