package core.domain.character

import core.domain.factions.Faction
import core.domain.health.Health
import core.domain.level.Level
import core.domain.position.Position

interface RPGEntity {
    fun getHealth(): Health
    fun getLevel(): Level
    fun getPosition(): Position
    fun isAlive(): Boolean
    fun attack(other: RPGEntity, damage: Double)
    fun heal(other: RPGEntity, healing: Double)
    fun receiveAttack(damage: Double)
    fun receiveHealing(healing: Double)
    fun joinFaction(faction: Faction)
    fun belongsToFaction(faction: Faction): Boolean
    fun leaveFaction(faction: Faction)
    fun isAlly(other: RPGEntity): Boolean
}