package core.domain.character

import core.domain.factions.Faction
import core.domain.health.Health
import core.domain.level.Level
import core.domain.position.Position

class RPGObject : RPGEntity {
    override fun getHealth(): Health {
        TODO("Not yet implemented")
    }

    override fun getLevel(): Level {
        TODO("Not yet implemented")
    }

    override fun getPosition(): Position {
        TODO("Not yet implemented")
    }

    override fun isAlive(): Boolean {
        TODO("Not yet implemented")
    }

    override fun attack(other: RPGEntity, damage: Double) {
        TODO("Not yet implemented")
    }

    override fun heal(other: RPGEntity, healing: Double) {
        TODO("Not yet implemented")
    }

    override fun receiveAttack(damage: Double) {
        TODO("Not yet implemented")
    }

    override fun receiveHealing(healing: Double) {
        TODO("Not yet implemented")
    }

    override fun joinFaction(faction: Faction) {
        TODO("Not yet implemented")
    }

    override fun belongsToFaction(faction: Faction): Boolean {
        TODO("Not yet implemented")
    }

    override fun leaveFaction(faction: Faction) {
        TODO("Not yet implemented")
    }

    override fun isAlly(other: RPGEntity): Boolean {
        TODO("Not yet implemented")
    }
}