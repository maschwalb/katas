package core.domain.damage

object DamageMultiplierRuleFactory {
    val rules by lazy {
        FiveLevelsBelowRule(
            FiveLevelsAboveRule(
                DefaultRule()
            )
        )
    }
}