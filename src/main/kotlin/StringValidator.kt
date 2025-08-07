class StringValidator(
    private var strategy: ValidationStrategy,
    private val createStateMachine: (State) -> StateMachine = ::createStateMachine
) {
    private val machine = createStateMachine(strategy.startState)

    fun setStrategy(strategy: ValidationStrategy) {
        this.strategy = strategy
        machine.resetWithStartState(strategy.startState)
    }

    fun isValid(input: String): Boolean {
        if (!strategy.preconditionsMet(input)) return false
        for (char in input) {
            val symbol = char.toString()
            machine.processSymbol(symbol)
        }
        val result = machine.getResult()
        machine.reset()
        return result
    }
}