package de.whs.ni37900.its.dsa

fun String.sign(dsa: DigitalSignatureAlgorithm): DigitalSignatureAlgorithmSignature {
    return dsa.sign(toByteArray())
}