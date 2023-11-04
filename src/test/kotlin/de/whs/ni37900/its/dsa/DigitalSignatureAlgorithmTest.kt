package de.whs.ni37900.its.dsa

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.math.BigInteger

class DigitalSignatureAlgorithmTest {

    @Test
    fun sign() {
        val dsa = DigitalSignatureAlgorithm()
        assertTrue("Hello World!".sign(dsa).verify("Hello World!", dsa))
    }

    @Test
    fun verify() {
        val dsa = DigitalSignatureAlgorithm()
        assertFalse(
            DigitalSignatureAlgorithmSignature(
                BigInteger.valueOf(100L),
                BigInteger.valueOf(100L),
            ).verify("Hello World!", dsa)
        )
    }
}