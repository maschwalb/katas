package core.domain.level

import core.domain.damage.DamageMultiplierRuleFactory

data class Level(val value: Int = INITIAL_LEVEL) {
    fun damageMultiplier(otherLevel: Level): Double {
        return DamageMultiplierRuleFactory.rules.apply(this, otherLevel)
    }

    companion object {
        const val INITIAL_LEVEL = 1
    }
}