package core.domain.damage

import core.domain.level.Level

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