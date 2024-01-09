package core.domain.character

import core.domain.health.Health
import core.domain.level.Level
import core.domain.position.Position

interface RPGObjectComposition : AttackableEntity, DestroyableEntity

class RPGObject(private var health: Health = Health(), private var level: Level = Level(), private var position: Position = Position()) : RPGObjectComposition {
    override fun getHealth(): Health {
        return health.copy()
    }

    override fun getLevel(): Level {
        return level.copy()
    }

    override fun isDestroyed(): Boolean {
        return health.hasHealth.not()
    }

    override fun receiveAttack(damage: Double) {
        health = health.damage(damage)
    }

    override fun getPosition(): Position {
        return position.copy()
    }
}