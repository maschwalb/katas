package core.domain.character

import core.domain.health.Health
import core.domain.level.Level

class RPGCharacter(
    private var _health: Health = Health(),
    private val _level: Level = Level()
) {

    val health: Health
        get() = _health.copy()

    val level: Level
        get() = _level.copy()

    val isAlive: Boolean
        get() = _health.isAlive

    fun attack(other: RPGCharacter, damage: Double) {
        if (cantAttack(other)) return
        other.receiveAttack(damage * _level.damageMultiplier(other.level))
    }

    fun heal(other: RPGCharacter, healing: Double) {
        if (cantHeal(other)) return
        other.receiveHealing(healing)
    }

    fun receiveAttack(damage: Double) {
        _health = _health.damage(damage)
    }

    fun receiveHealing(healing: Double) {
        _health = _health.heal(healing)
    }

    private fun amSelf(other: RPGCharacter) = this === other

    private fun cantAttack(other: RPGCharacter) = amSelf(other)

    private fun cantHeal(other: RPGCharacter) = !other.isAlive || !amSelf(other)
}