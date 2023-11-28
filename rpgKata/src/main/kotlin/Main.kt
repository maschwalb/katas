fun main(args: Array<String>) {
    println("Hello World!")

    println("Program arguments: ${args.joinToString()}")
}

class RPGCharacter {
    private val _health: Health = Health()
    private val _level: Level = Level()

    val health: Int
        get() = _health.get()

    val level: Int // TODO: No usar primitivos
        get() = _level.value

    val isAlive: Boolean
        get() = _health.isAlive

    fun attack(other: RPGCharacter, damage: Int) {
        other.receiveAttack(damage)
    }

    fun heal(other: RPGCharacter, healing: Int) {
        if (!other.isAlive) return
        other.receiveHealing(healing)
    }

    fun receiveAttack(damage: Int) {
        _health.damage(damage)
    }

    fun receiveHealing(healing: Int) {
        _health.heal(healing)
    }
}

class Health { // TODO: Considerar un value object (data class)
    private var value = INITIAL_HEALTH

    val isAlive: Boolean
        get() = value > 0

    fun get(): Int {
        return value
    }

    fun damage(damage: Int) {
        value -= damage
        capDamage()
    }

    fun heal(healing: Int) {
        value += healing
        capHealing()
    }

    private fun capDamage() {
        if (isDamageCapped()) {
            value = 0
        }
    }

    private fun isDamageCapped(): Boolean = value < 0

    private fun capHealing() {
        if (isHealingCapped()) {
            value = MAX_HEALTH
        }
    }

    private fun isHealingCapped() = value > MAX_HEALTH

    companion object {
        const val INITIAL_HEALTH = 1000
        const val MAX_HEALTH = 1000
    }
}

class Level {
    val value = INITIAL_LEVEL

    companion object {
        const val INITIAL_LEVEL = 1
    }
}