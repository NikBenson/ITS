package de.whs.ni37900.its.dsa

import java.math.BigInteger

data class DigitalSignatureAlgorithmSignature(val r: BigInteger, val s: BigInteger) {
    fun verify(message: String, dsa: DigitalSignatureAlgorithm): Boolean {
        return dsa.verify(message.toByteArray(), this)
    }

    override fun toString(): String {
        return "{\n\tr='%s'\n\ts='%s'\n}".format(r, s)
    }
}