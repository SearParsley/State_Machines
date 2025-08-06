class StringValidator(
    private var strategy: ValidationStrategy
) {
    fun setStrategy(strategy: ValidationStrategy) {
        this.strategy = strategy
    }

    fun isValid(input: String): Boolean {
        val machine = strategy.machine
        if (!strategy.preconditionsMet(input)) return false
        for (char in input) {
            val symbol = char.toString()
            machine.processSymbol(symbol)
            if (machine.isInvalid) return false
        }
        return machine.getResult()
    }
}