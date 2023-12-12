package core.domain.damage

import core.domain.level.Level

class DefaultRule: DamageMultiplierRule {
    override fun apply(attackerLevel: Level, defenderLevel: Level): Double {
        return 1.0
    }
}