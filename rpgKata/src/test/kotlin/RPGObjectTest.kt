import core.domain.character.RPGCharacter
import core.domain.character.RPGObject
import core.domain.health.Health
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RPGObjectTest {
    private lateinit var someCharacter: RPGCharacter
    private lateinit var someObject: RPGObject

    @Test
    fun `characters can attack objects`() {
        givenSomeCharacter()
        givenAnObject()

        whenSomeAttacksObject(100.0)

        thenObjectHasHealth(Health(900.0))
    }

    @Test
    fun `objects can be destroyed`() {
        givenSomeCharacter()
        givenAnObject()

        whenSomeAttacksObject(1000.0)

        thenObjectIsDestroyed()
    }

    private fun givenSomeCharacter() {
        someCharacter = RPGCharacter()
    }

    private fun whenSomeAttacksObject(damage: Double) {
        someCharacter.attack(someObject, damage)
    }

    private fun givenAnObject() {
        someObject = RPGObject()
    }

    private fun thenObjectHasHealth(health: Health) {
        Assertions.assertEquals(health, someObject.getHealth())
    }

    private fun thenObjectIsDestroyed() {
        Assertions.assertTrue(someObject.isDestroyed())
    }
}