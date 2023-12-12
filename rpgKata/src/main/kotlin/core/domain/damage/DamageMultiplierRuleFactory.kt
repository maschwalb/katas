package core.domain.damage

import core.domain.level.Level

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