package core.domain.damage

abstract class DamageMultiplierRule(nextRule: DamageMultiplierRule?) {
    abstract fun apply(): Double
}