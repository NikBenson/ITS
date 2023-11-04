package de.whs.ni37900.its.dsa

fun main() {
    val dsa = DigitalSignatureAlgorithm()
    println("parameters: %s".format(dsa.parameters))
    println("key pair: %s".format(dsa.keyPair))
    println()

    "Hello World!".let { message ->
        print("message: ")
        println(message)

        print("signature: ")
        val signature = message.sign(dsa)
        println(signature)

        print("verified: ")
        val verified = signature.verify(message, dsa)
        println(verified)
        println()
    }

    "Test123".let { message ->
        print("message: ")
        println(message)

        print("signature: ")
        val signature = message.sign(dsa)
        println(signature)

        print("verified: ")
        val verified = signature.verify(message, dsa)
        println(verified)
        println()
    }

    "lorem ipsum dolor sit amet".let { message ->
        print("message: ")
        println(message)

        print("signature: ")
        val signature = message.sign(dsa)
        println(signature)

        print("verified: ")
        val verified = signature.verify(message, dsa)
        println(verified)
        println()
    }
}
