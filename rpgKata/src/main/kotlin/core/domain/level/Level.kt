package core.domain.level

import core.domain.damage.DamageMultiplierRuleFactory

data class Level(val value: Int = INITIAL_LEVEL) {
    companion object {
        const val INITIAL_LEVEL = 1
    }

    fun damageMultiplier(otherLevel: Level): Double {
        return DamageMultiplierRuleFactory.createRules(this, otherLevel).apply()
    }
}