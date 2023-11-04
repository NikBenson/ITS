package de.whs.ni37900.its.dsa

import java.math.BigInteger

interface DigitalSignatureAlgorithmParameters {
    val p: BigInteger
    val q: BigInteger
    val g: BigInteger
    val hash: (ByteArray) -> BigInteger
}