package core.domain.position

data class Position(val value: Int = INITIAL_POSITION) {
    companion object {
        const val INITIAL_POSITION = 0
    }
}