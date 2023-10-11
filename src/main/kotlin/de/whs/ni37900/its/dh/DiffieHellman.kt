package de.whs.ni37900.its.dh

import java.util.function.Supplier

open class DiffieHellman<PublicKey : Combinable<PrivateKey, CombinedKey>, PrivateKey : Combinable<PublicKey, CombinedKey>, CombinedKey : Combinable<Pair<PublicKey, PrivateKey>, Key>, Key, Message, EncodedMessage>(
    private val createPublicKey: Supplier<PublicKey>,
    private val createPrivateKey: Supplier<PrivateKey>,
    private val encodeMessage: (Message, Key) -> EncodedMessage,
    private val decodeMessage: (EncodedMessage, Key) -> Message,
) {
    private val alice = Participant()
    private val bob = Participant()

    private val participants: List<Participant>
        get() = listOf(alice, bob)

    fun execute(): Pair<Coder<Message, EncodedMessage>, Coder<Message, EncodedMessage>> {
        alice.generatePublicKey()

        for (participant in participants)
            participant.generatePrivateKey()

        alice.onReceiveCombinedKey(bob.combinedKey!!)
        bob.onReceiveCombinedKey(alice.combinedKey!!)

        return alice to bob
    }

    private fun publishPublicKey(key: PublicKey) {
        for (participant in participants)
            participant.onReceivePublicKey(key)
    }

    inner class Participant : Coder<Message, EncodedMessage> {
        var publicKey: PublicKey? = null
            private set

        private var privateKey: PrivateKey? = null
        private var key: Key? = null

        val combinedKey: CombinedKey?
            get() = publicKey?.let { publicKey -> privateKey?.let { privateKey -> publicKey + privateKey } }

        fun generatePublicKey() {
            publishPublicKey(createPublicKey.get())
        }

        fun generatePrivateKey() {
            privateKey = createPrivateKey.get()
        }

        fun onReceivePublicKey(key: PublicKey) {
            publicKey = key
        }

        fun onReceiveCombinedKey(key: CombinedKey) {
            this.key = key + (publicKey!! to privateKey!!)
        }

        override fun encode(message: Message): EncodedMessage {
            return encodeMessage(message, key!!)
        }

        override fun decode(message: EncodedMessage): Message {
            return decodeMessage(message, key!!)
        }
    }
}
