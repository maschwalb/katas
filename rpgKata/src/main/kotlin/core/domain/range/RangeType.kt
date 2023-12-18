package core.domain.range

enum class RangeType {
    MELEE,
    RANGED;

    fun getRange(): Range {
        return when (this) {
            MELEE -> Range(2)
            RANGED -> Range(20)
        }
    }
}