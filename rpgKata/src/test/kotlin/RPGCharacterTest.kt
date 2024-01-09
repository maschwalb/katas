import core.domain.character.RPGCharacter
import core.domain.character.RPGObject
import core.domain.factions.Faction
import core.domain.health.Health
import core.domain.level.Level
import core.domain.position.Position
import core.domain.range.RangeType
import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

@Suppress("DANGEROUS_CHARACTERS")
class RPGCharacterTest {
    private lateinit var someCharacter: RPGCharacter
    private lateinit var otherCharacter: RPGCharacter
    private lateinit var someObject: RPGObject

    @Test
    fun `character should start with 1000 health`() {
        whenCharacterIsCreated()

        thenCharacterHasHealth(someCharacter, 1000.0)
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

        whenSomeAttacksOther(100.0)

        thenCharacterHasHealth(otherCharacter, 900.0)
    }

    @Test
    fun `character can kill another`() {
        givenSomeCharacter()
        givenOtherCharacter()

        whenSomeAttacksOther(1500.0)

        thenCharacterIsAlive(otherCharacter, false)
    }

    @Test
    fun `character can not have negative health`() {
        givenSomeCharacter()
        givenOtherCharacter()

        whenSomeAttacksOther(1500.0)

        thenCharacterHasHealth(otherCharacter, 0.0)
    }

    @Test
    fun `character can heal itself`() {
        givenSomeCharacter()
        givenOtherCharacterWith(Health(800.0), position = Position(3))

        whenOtherHealsItself()

        thenCharacterHasHealth(otherCharacter, 900.0)
    }

    @Test
    fun `character can not heal a dead character`() {
        givenSomeCharacter()
        givenOtherCharacterWith(Health(0.0), position = Position(3))

        whenSomeHealsOther(100.0)

        thenCharacterHasHealth(otherCharacter, 0.0)
    }

    @Test
    fun `character can not have more than 1000 health`() {
        givenSomeCharacter()
        givenOtherCharacter()

        whenSomeHealsOther(100.0)

        thenCharacterHasHealth(otherCharacter, 1000.0)
    }

    @Test
    fun `character can not deal damage to itself`() {
        givenSomeCharacter()

        whenSomeAttacksItself()

        thenCharacterHasHealth(someCharacter, 1000.0)
    }

    @Test
    fun `character can not heal others`() {
        givenSomeCharacter()
        givenOtherCharacterWith(Health(800.0), position = Position(3))

        whenSomeHealsOther(100.0)

        thenCharacterHasHealth(otherCharacter, 800.0)
    }

    @Test
    fun `damage is increased in 50% when character is 5 levels above other`() {
        givenSomeCharacterWith(initialLevel = Level(6), position = Position(0))
        givenOtherCharacterWith(initialLevel = Level(1))

        whenSomeAttacksOther(100.0)

        thenCharacterHasHealth(otherCharacter, 850.0)
    }

    @Test
    fun `damage is reduced in 50% when character is 5 levels below other`() {
        givenSomeCharacterWith(initialLevel = Level(1), position = Position(0))
        givenOtherCharacterWith(initialLevel = Level(6))

        whenSomeAttacksOther(100.0)

        thenCharacterHasHealth(otherCharacter, 950.0)
    }

    @Test
    fun `melee characters can only attack with range of 2 units`() {
        givenSomeCharacterWith(rangeType = RangeType.MELEE, position = Position(0))
        givenOtherCharacterWith(position = Position(3))

        whenSomeAttacksOther(100.0)

        thenCharacterHasHealth(otherCharacter, 1000.0)
    }

    @Test
    fun `ranged characters can only attack with range of 20 units`() {
        givenSomeCharacterWith(rangeType = RangeType.RANGED, position = Position(0))
        givenOtherCharacterWith(position = Position(21))

        whenSomeAttacksOther(100.0)

        thenCharacterHasHealth(otherCharacter, 1000.0)
    }

    @Test
    fun `character can join a faction`() {
        givenSomeCharacter()
        val faction = Faction("faction")

        whenCharacterJoinsFaction(someCharacter, faction)

        thenCharacterBelongsToFaction(someCharacter, faction)
    }

    @Test
    fun `character can leave a faction`() {
        val faction = Faction("faction")
        givenSomeCharacterWith(factions = listOf(faction))

        whenCharacterLeavesFaction(someCharacter, faction)

        thenCharacterDoesntBelongToFaction(someCharacter, faction)
    }

    @Test
    fun `characters in the same faction are allies`() {
        val faction = Faction("faction")
        givenSomeCharacterWith(factions = listOf(faction))
        givenOtherCharacterWith(factions = listOf(faction))

        thenCharactersAreAllies(someCharacter, otherCharacter)
    }

    @Test
    fun `allies cannot deal damage to each other`() {
        val faction = Faction("faction")
        givenSomeCharacterWith(factions = listOf(faction))
        givenOtherCharacterWith(factions = listOf(faction))

        whenSomeAttacksOther(100.0)

        thenCharacterHasHealth(otherCharacter, 1000.0)
    }

    @Test
    fun `characters can heal allies`() {
        val faction = Faction("faction")
        givenSomeCharacterWith(factions = listOf(faction))
        givenOtherCharacterWith(factions = listOf(faction), initialHealth = Health(800.0))

        whenSomeHealsOther(100.0)

        thenCharacterHasHealth(otherCharacter, 900.0)
    }

    private fun thenCharactersAreAllies(someCharacter: RPGCharacter, otherCharacter: RPGCharacter) {
        assertTrue(someCharacter.isAlly(otherCharacter))
    }

    private fun thenCharacterDoesntBelongToFaction(character: RPGCharacter, faction: Faction) {
        assertFalse(character.belongsToFaction(faction))
    }

    private fun whenCharacterLeavesFaction(character: RPGCharacter, faction: Faction) {
        character.leaveFaction(faction)
    }

    private fun givenSomeCharacter() {
        whenCharacterIsCreated()
    }

    private fun givenSomeCharacterWith(
        initialHealth: Health = Health(1000.0),
        initialLevel: Level = Level(1),
        rangeType: RangeType = RangeType.MELEE,
        position: Position = Position(0),
        factions: List<Faction> = emptyList()
    ) {
        someCharacter = RPGCharacter(initialHealth, initialLevel, rangeType, position, factions)
    }

    private fun givenOtherCharacter() {
        otherCharacter = RPGCharacter()
    }

    private fun givenOtherCharacterWith(
        initialHealth: Health = Health(1000.0),
        initialLevel: Level = Level(1),
        position: Position = Position(0),
        factions: List<Faction> = emptyList()
    ) {
        otherCharacter = RPGCharacter(initialHealth, initialLevel, RangeType.MELEE, position, factions)
    }

    private fun whenCharacterIsCreated() {
        someCharacter = RPGCharacter()
    }

    private fun whenSomeAttacksOther(damage: Double) {
        someCharacter.attack(otherCharacter, damage)
    }

    private fun whenSomeAttacksItself() {
        someCharacter.attack(someCharacter, 100.0)
    }

    private fun whenSomeHealsOther(healing: Double) {
        someCharacter.heal(otherCharacter, healing)
    }

    private fun whenOtherHealsItself() {
        otherCharacter.heal(otherCharacter, 100.0)
    }

    private fun whenCharacterJoinsFaction(rpgCharacter: RPGCharacter, faction: Faction) {
        rpgCharacter.joinFaction(faction)
    }

    private fun thenCharacterHasHealth(character: RPGCharacter, expected: Double) {
        assertEquals(Health(expected), character.getHealth())
    }

    private fun thenCharacterHasLevelOne() {
        assertEquals(1, someCharacter.getLevel().value)
    }

    private fun thenCharacterIsAlive(character: RPGCharacter, expected: Boolean) {
        assertEquals(expected, character.isAlive())
    }

    private fun thenCharacterBelongsToFaction(rpgCharacter: RPGCharacter, faction: Faction) {
        assertTrue(rpgCharacter.belongsToFaction(faction))
    }
}
