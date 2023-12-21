package core.domain.character

import core.domain.factions.Faction
import core.domain.health.Health
import core.domain.level.Level
import core.domain.position.Position
import core.domain.range.RangeType

class RPGCharacter(
    private var _health: Health = Health(),
    private val _level: Level = Level(),
    rangeType: RangeType = RangeType.MELEE,
    private val _position: Position = Position(),
    private var factions: List<Faction> = emptyList<Faction>().toMutableList()
) : RPGEntity {
    private val _range = rangeType.getRange()

    override fun getHealth(): Health = _health.copy()
    override fun getLevel(): Level = _level.copy()
    override fun getPosition(): Position = _position.copy()
    override fun isAlive(): Boolean = _health.isAlive

    override fun attack(other: RPGEntity, damage: Double) {
        if (cantAttack(other)) return
        other.receiveAttack(damage * _level.damageMultiplier(other.getLevel()))
    }

    override fun heal(other: RPGEntity, healing: Double) {
        if (cantHeal(other)) return
        other.receiveHealing(healing)
    }

    override fun receiveAttack(damage: Double) {
        _health = _health.damage(damage)
    }

    override fun receiveHealing(healing: Double) {
        _health = _health.heal(healing)
    }

    override fun joinFaction(faction: Faction) {
        factions += faction
    }

    override fun belongsToFaction(faction: Faction): Boolean {
        return factions.contains(faction)
    }

    override fun leaveFaction(faction: Faction) {
        factions -= faction
    }

    override fun isAlly(other: RPGEntity): Boolean {
        return factions.any { other.belongsToFaction(it) }
    }

    private fun amSelf(other: RPGEntity) = this === other
    private fun cantAttack(other: RPGEntity) = amSelf(other) || isOutOfRange(other) || isAlly(other)
    private fun isOutOfRange(other: RPGEntity) = other.getPosition().value - _position.value >= _range.value
    private fun cantHeal(other: RPGEntity) = !other.isAlive() || (!amSelf(other) && !isAlly(other))
}