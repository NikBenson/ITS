package de.whs.ni37900.its.dh

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class FiniteFieldDiffieHellmannTest {
    @Test
    fun execute() {
        val dh = FiniteFieldDiffieHellmann()
        val participants = dh.execute()
        val alice = participants.first
        val bob = participants.second

        assertEquals(42L, bob.decode(alice.encode(42L)))
        assertEquals(17L, alice.decode(bob.encode(17L)))
    }
}