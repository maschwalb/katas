package core.domain.character

import core.domain.factions.Faction
import core.domain.health.Health
import core.domain.level.Level
import core.domain.position.Position
import core.domain.range.RangeType

interface RPGCharacterComposition : RPGEntity, HealthedEntity, LivingEntity, LeveledEntity, AttackerEntity, HealerEntity, FactionedEntity, PositionedEntity, AttackableEntity, HealableEntity

class RPGCharacter(
    private var _health: Health = Health(),
    private val _level: Level = Level(),
    rangeType: RangeType = RangeType.MELEE,
    private val _position: Position = Position(),
    private var factions: List<Faction> = emptyList<Faction>().toMutableList()
) : RPGCharacterComposition {
    private val _range = rangeType.getRange()

    override fun getHealth(): Health = _health.copy()
    override fun getLevel(): Level = _level.copy()
    override fun attack(other: AttackableEntity, damage: Double) {
        if (cantAttack(other)) return
        other.receiveAttack(damage * damageMultiplier(other.getLevel()))
    }

    override fun getPosition(): Position = _position.copy()
    override fun isAlive(): Boolean = _health.hasHealth

    override fun damageMultiplier(targetLevel: Level): Double {
        return _level.damageMultiplier(targetLevel)
    }

    override fun heal(other: HealableEntity, healing: Double) {
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
        if (other !is FactionedEntity) return false // TODO: Puedo evitar este casteo?
        return factions.any { other.belongsToFaction(it) }
    }

    private fun amSelf(other: RPGEntity) = this === other
    private fun cantAttack(other: AttackableEntity) = amSelf(other) || isOutOfRange(other) || isAlly(other)
    private fun isOutOfRange(other: PositionedEntity) = other.getPosition().value - _position.value >= _range.value
    private fun cantHeal(other: HealableEntity) = !other.isAlive() || (!amSelf(other) && !isAlly(other))
}