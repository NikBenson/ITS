package de.whs.ni37900.its.dsa

import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

val ByteArray.sha1: BigInteger
    get() {
        return try {
            val messageDigest = MessageDigest.getInstance("SHA-1")
            BigInteger(messageDigest.digest(this))
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }
    }
