package core.domain.damage

import core.domain.level.Level

class FiveLevelsAboveRule(private val rule: DamageMultiplierRule): DamageMultiplierRule {
    override fun apply(attackerLevel: Level, defenderLevel: Level): Double {
        return if (attackerIsFiveLevelsAbove(attackerLevel, defenderLevel)) {
            1.5
        } else {
            rule.apply(attackerLevel, defenderLevel)
        }
    }

    private fun attackerIsFiveLevelsAbove(attackerLevel: Level, defenderLevel: Level) = attackerLevel.value - defenderLevel.value >= 5
}