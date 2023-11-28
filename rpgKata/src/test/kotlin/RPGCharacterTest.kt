import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

/*
Iteration One
All Characters, when created, have:

Health, starting at 1000
Level, starting at 1
May be Alive or Dead, starting Alive (Alive may be a true/false)
Characters can Deal Damage to Characters.
 */


class RPGCharacterTest {

    private lateinit var someCharacter: RPGCharacter
    private lateinit var otherCharacter: RPGCharacter

    @Test
    fun `character should start with 1000 health`() {
        whenCharacterIsCreated()

        thenCharacterHasHealth(someCharacter, 1000)
    }

    @Test
    fun `character should start with level 1`() {
        whenCharacterIsCreated()

        thenCharacterHasLevelOne()
    }

    @Test
    fun `character should start alive`() {
        whenCharacterIsCreated()

        thenCharacterIsAlive(someCharacter, true)
    }

    @Test
    fun `character can deal damage to another`() {
        givenSomeCharacter()
        givenOtherCharacter()

        whenSomeAttacksOther(100)

        thenCharacterHasHealth(otherCharacter, 900)
    }

    @Test
    fun `character can kill another`() {
        givenSomeCharacter()
        givenOtherCharacter()

        whenSomeAttacksOther(1500)

        thenCharacterIsAlive(otherCharacter, false)
    }

    @Test
    fun `character can not have negative health`() {
        givenSomeCharacter()
        givenOtherCharacter()

        whenSomeAttacksOther(1500)

        thenCharacterHasHealth(otherCharacter, 0)
    }

    @Test
    fun `character can heal another`() {
        givenSomeCharacter()
        givenOtherCharacter()

        whenSomeAttacksOther(200)
        whenSomeHealsOther(100)

        thenCharacterHasHealth(otherCharacter, 900)
    }

    @Test
    fun `character can not heal a dead character`() {
        givenSomeCharacter()
        givenOtherCharacter()
        givenOtherIsDead()

        whenSomeHealsOther(100)

        thenCharacterHasHealth(otherCharacter, 0)
    }

    @Test
    fun `character can not have more than 1000 health`() {
        givenSomeCharacter()
        givenOtherCharacter()

        whenSomeHealsOther(100)

        thenCharacterHasHealth(otherCharacter, 1000)
    }

    private fun givenSomeCharacter() {
        whenCharacterIsCreated()
    }

    private fun givenOtherCharacter() {
        otherCharacter = RPGCharacter()
    }

    private fun givenOtherIsDead() {
        someCharacter.attack(otherCharacter, 1500)
    }

    private fun whenCharacterIsCreated() {
        someCharacter = RPGCharacter()
    }

    private fun whenSomeAttacksOther(damage: Int) {
        someCharacter.attack(otherCharacter, damage)
    }

    private fun whenSomeHealsOther(healing: Int) {
        someCharacter.heal(otherCharacter, healing)
    }

    private fun thenCharacterHasHealth(character: RPGCharacter, expected: Int) {
        assertEquals(expected, character.health)
    }

    private fun thenCharacterHasLevelOne() {
        assertEquals(1, someCharacter.level)
    }

    private fun thenCharacterIsAlive(character: RPGCharacter, expected: Boolean) {
        assertEquals(expected, character.isAlive)
    }
}
    