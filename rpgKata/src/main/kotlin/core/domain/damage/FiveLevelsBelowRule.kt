package core.domain.damage

import core.domain.level.Level

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