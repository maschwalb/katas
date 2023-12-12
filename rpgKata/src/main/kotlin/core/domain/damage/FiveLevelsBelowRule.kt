package core.domain.damage

import core.domain.level.Level

class FiveLevelsBelowRule(private val rule: DamageMultiplierRule): DamageMultiplierRule {
    override fun apply(attackerLevel: Level, defenderLevel: Level): Double {
        return if (attackerIsFiveLevelsBelow(attackerLevel, defenderLevel)) {
            0.5
        } else {
            rule.apply(attackerLevel, defenderLevel)
        }
    }

    private fun attackerIsFiveLevelsBelow(attackerLevel: Level, defenderLevel: Level) = defenderLevel.value - attackerLevel.value >= 5
}