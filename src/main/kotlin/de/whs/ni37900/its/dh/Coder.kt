package de.whs.ni37900.its.dh

interface Coder<Message, EncodedMessage> : Encoder<Message, EncodedMessage>, Decoder<Message, EncodedMessage>

fun interface Encoder<Message, EncodedMessage> {
    fun encode(message: Message): EncodedMessage
}

fun interface Decoder<Message, EncodedMessage> {
    fun decode(message: EncodedMessage): Message
}
