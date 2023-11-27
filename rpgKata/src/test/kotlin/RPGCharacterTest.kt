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

    lateinit var character: RPGCharacter

    @Test
    fun `character should start with 1000 health`() {
        whenCharacterIsCreated()

        thenCharacterHas1000Health()
    }

    @Test
    fun `character should start with level 1`() {
        whenCharacterIsCreated()

        thenCharacterHasLevelOne()
    }

    @Test
    fun `character should start alive`() {
        whenCharacterIsCreated()

        thenCharacterIsAlive()
    }

    @Test
    fun `character can deal damage to another`() {
        val attacker = RPGCharacter()
        val victim = RPGCharacter()

        attacker.dealDamage(victim)

        assertEquals(900, victim.health)
    }

    private fun whenCharacterIsCreated() {
        character = RPGCharacter()
    }

    private fun thenCharacterHas1000Health() {
        assertEquals(1000, character.health)
    }

    private fun thenCharacterHasLevelOne() {
        assertEquals(1, character.level)
    }

    private fun thenCharacterIsAlive() {
        assertTrue(character.isAlive)
    }
}
    