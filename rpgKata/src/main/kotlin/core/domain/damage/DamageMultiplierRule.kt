package core.domain.damage

import core.domain.level.Level

interface DamageMultiplierRule {
    fun apply(attackerLevel: Level, defenderLevel: Level): Double
}