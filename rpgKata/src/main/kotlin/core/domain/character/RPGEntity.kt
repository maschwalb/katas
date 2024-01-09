package core.domain.character

import core.domain.factions.Faction
import core.domain.health.Health
import core.domain.level.Level
import core.domain.position.Position

interface RPGEntity

interface PositionedEntity : RPGEntity {
    fun getPosition(): Position
}

interface HealthedEntity : RPGEntity {
    fun getHealth(): Health
}

interface LivingEntity : HealthedEntity {
    fun isAlive(): Boolean
}

interface LeveledEntity : RPGEntity {
    fun getLevel(): Level
}

interface DestroyableEntity : HealthedEntity {
    fun isDestroyed(): Boolean
}

interface AttackerEntity : RPGEntity {
    fun attack(other: AttackableEntity, damage: Double)
    fun damageMultiplier(targetLevel: Level): Double
}

interface HealerEntity : RPGEntity {
    fun heal(other: HealableEntity, healing: Double)
}

interface AttackableEntity : PositionedEntity, HealthedEntity, LeveledEntity { // TODO: Puedo evitar que tenga nivel? Lo necesito para calcular el multiplier
    fun receiveAttack(damage: Double)
}

interface HealableEntity : LivingEntity {
    fun receiveHealing(healing: Double)
}

interface FactionedEntity : RPGEntity {
    fun joinFaction(faction: Faction)
    fun belongsToFaction(faction: Faction): Boolean
    fun leaveFaction(faction: Faction)
    fun isAlly(other: RPGEntity): Boolean
}