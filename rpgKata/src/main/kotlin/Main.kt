fun main(args: Array<String>) {
    println("Hello World!")

    println("Program arguments: ${args.joinToString()}")
}

class RPGCharacterFactory {
    fun create(): RPGCharacter {
        return RPGCharacter()
    }

    fun createWithHealth(health: Health): RPGCharacter {
        return RPGCharacter(health)
    }
}

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
        if (canAttack(other)) return
        other.receiveAttack(damage * _level.damageMultiplier(other.level))
    }

    fun heal(other: RPGCharacter, healing: Double) {
        if (canHeal(other)) return
        other.receiveHealing(healing)
    }

    fun receiveAttack(damage: Double) {
        _health = _health.damage(damage)
    }

    fun receiveHealing(healing: Double) {
        _health = _health.heal(healing)
    }

    private fun amSelf(other: RPGCharacter) = this === other

    private fun canAttack(other: RPGCharacter) = amSelf(other)

    private fun canHeal(other: RPGCharacter) = !other.isAlive || !amSelf(other)
}

data class Health(val value: Double = INITIAL_HEALTH) {
    val isAlive: Boolean
        get() = value > 0.0

    fun damage(damage: Double): Health {
        return this.copy(value = capDamage(value - damage))
    }

    fun heal(healing: Double): Health {
        return this.copy(value = capHealing(value + healing))
    }

    private fun capDamage(value: Double): Double {
        if (isDamageCapped(value)) return 0.0
        return value
    }

    private fun isDamageCapped(value: Double): Boolean = value < 0

    private fun capHealing(value: Double): Double {
        if (isHealingCapped(value)) {
            return MAX_HEALTH
        }
        return value
    }

    private fun isHealingCapped(value: Double) = value > MAX_HEALTH

    companion object {
        const val INITIAL_HEALTH: Double = 1000.0
        const val MAX_HEALTH: Double = 1000.0
    }
}

data class Level(val value: Int = INITIAL_LEVEL) {
    companion object {
        const val INITIAL_LEVEL = 1
    }

    fun damageMultiplier(otherLevel: Level): Double {
        return DamageMultiplierRuleFactory.createRules(this, otherLevel).apply()
    }
}

class DamageMultiplierRuleFactory {
    companion object {
        fun createRules(attackerLevel: Level, defenderLevel: Level): DamageMultiplierRule {
            return FiveLevelsBelowRule(
                FiveLevelsAboveRule(
                    DefaultRule(),
                    attackerLevel,
                    defenderLevel
                ),
                attackerLevel,
                defenderLevel
            )
        }
    }
}

abstract class DamageMultiplierRule(nextRule: DamageMultiplierRule?) {
    abstract fun apply(): Double
}

class DefaultRule(): DamageMultiplierRule(nextRule = null) {
    override fun apply(): Double {
        return 1.0
    }
}

class FiveLevelsBelowRule(private val rule: DamageMultiplierRule, private val attackerLevel: Level, private val defenderLevel: Level): DamageMultiplierRule(rule) {
    override fun apply(): Double {
        return if (attackerIsFiveLevelsBelow()) {
            0.5
        } else {
            rule.apply()
        }
    }

    private fun attackerIsFiveLevelsBelow() = defenderLevel.value - attackerLevel.value >= 5
}

class FiveLevelsAboveRule(private val rule: DamageMultiplierRule, private val attackerLevel: Level, private val defenderLevel: Level): DamageMultiplierRule(rule) {
    override fun apply(): Double {
        return if (attackerIsFiveLevelsAbove()) {
            1.5
        } else {
            rule.apply()
        }
    }

    private fun attackerIsFiveLevelsAbove() = attackerLevel.value - defenderLevel.value >= 5
}