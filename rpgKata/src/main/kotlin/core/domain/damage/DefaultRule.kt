package core.domain.damage

class DefaultRule(): DamageMultiplierRule(nextRule = null) {
    override fun apply(): Double {
        return 1.0
    }
}