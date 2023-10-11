package de.whs.ni37900.its.dh


fun main() {
    val dh = FiniteFieldDiffieHellmann()
    val participants = dh.execute()
    val alice = participants.first
    val bob = participants.second

    println("alice: ${alice.encode(0)}, bob: ${bob.encode(0)}")
}
