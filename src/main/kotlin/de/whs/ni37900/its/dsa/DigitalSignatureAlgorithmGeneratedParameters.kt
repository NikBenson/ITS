package de.whs.ni37900.its.dsa

import java.math.BigInteger

class DigitalSignatureAlgorithmGeneratedParameters(
    keyLength: UInt,
    n: UInt,
    override val hash: (ByteArray) -> BigInteger,
    override val q: BigInteger,
    override val p: BigInteger,
    val h: BigInteger,
    override val g: BigInteger,
) : DigitalSignatureAlgorithmFixedParameters(keyLength = keyLength, n = n, hash = hash),
    DigitalSignatureAlgorithmParameters {
    override fun toString(): String {
        return "{\n\tL='%s'\n\tN='%s'\n\tp='%s',\n\tq='%s',\n\th='%s',\n\tg='%s',\n}".format(keyLength, n, p, q, h, g)
    }
}