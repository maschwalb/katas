package core.domain.health

data class Health(val value: Double = INITIAL_HEALTH) {
    val isAlive: Boolean
        get() = value > 0.0

    fun damage(damage: Double): Health {
        return this.copy(value = capDamage(value - damage))
    }

    fun heal(healing: Double): Health {
        return this.copy(value = capHealing(value + healing))
    }

    private fun capDamage(value: Double): Double {
        if (isDamageCapped(value)) return 0.0
        return value
    }

    private fun isDamageCapped(value: Double): Boolean = value < 0

    private fun capHealing(value: Double): Double {
        if (isHealingCapped(value)) {
            return MAX_HEALTH
        }
        return value
    }

    private fun isHealingCapped(value: Double) = value > MAX_HEALTH

    companion object {
        const val INITIAL_HEALTH: Double = 1000.0
        const val MAX_HEALTH: Double = 1000.0
    }
}