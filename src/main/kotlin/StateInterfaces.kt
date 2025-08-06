abstract class StateMachine {
    abstract var currentState: State
    abstract var isInvalid: Boolean

    fun processSymbol(symbol: String) {
        if (isInvalid) return
        currentState.processSymbol(this, symbol)
    }

    abstract fun getResult(): Boolean
}

interface State {
    fun processSymbol(machine: StateMachine, symbol: String)
}

interface ValidationStrategy {
    val machine: StateMachine
    fun passesChecks(input: String): Boolean = true
}