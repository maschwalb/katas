fun main(args: Array<String>) {
    println("Hello World!")

    println("Program arguments: ${args.joinToString()}")
}

class RPGCharacterFactory {
    fun create(): RPGCharacter {
        return RPGCharacter()
    }

    fun createWithHealth(health: Health): RPGCharacter {
        return RPGCharacter(health)
    }
}

class RPGCharacter(
    private var _health: Health = Health(),
    private val _level: Level = Level()
) {

    val health: Health
        get() = _health.copy()

    val level: Level
        get() = _level.copy()

    val isAlive: Boolean
        get() = _health.isAlive

    fun attack(other: RPGCharacter, damage: Int) {
        if (canAttack(other)) return
        other.receiveAttack(damage)
    }

    fun heal(other: RPGCharacter, healing: Int) {
        if (canHeal(other)) return
        other.receiveHealing(healing)
    }

    fun receiveAttack(damage: Int) {
        _health = _health.damage(damage)
    }

    fun receiveHealing(healing: Int) {
        _health = _health.heal(healing)
    }

    private fun amSelf(other: RPGCharacter) = this === other

    private fun canAttack(other: RPGCharacter) = amSelf(other)

    private fun canHeal(other: RPGCharacter) = !other.isAlive || !amSelf(other)
}

data class Health(val value: Int = INITIAL_HEALTH) {
    val isAlive: Boolean
        get() = value > 0

    fun damage(damage: Int): Health {
        return this.copy(value = capDamage(value - damage))
    }

    fun heal(healing: Int): Health {
        return this.copy(value = capHealing(value + healing))
    }

    private fun capDamage(value: Int): Int {
        if (isDamageCapped(value)) return 0
        return value
    }

    private fun isDamageCapped(value: Int): Boolean = value < 0

    private fun capHealing(value: Int): Int {
        if (isHealingCapped(value)) {
            return MAX_HEALTH
        }
        return value
    }

    private fun isHealingCapped(value: Int) = value > MAX_HEALTH

    companion object {
        const val INITIAL_HEALTH = 1000
        const val MAX_HEALTH = 1000
    }
}

data class Level(val value: Int = INITIAL_LEVEL) {

    fun get(): Int = value

    companion object {
        const val INITIAL_LEVEL = 1
    }
}