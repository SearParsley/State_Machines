interface ValidationStrategy {
    val machine: StateMachine
    fun preconditionsMet(input: String): Boolean = true
}

abstract class StateMachine(val startState: State) {
    protected var currentState: State = startState

    fun processSymbol(symbol: String) {
        currentState = currentState.processSymbol(symbol)
    }

    abstract fun getResult(): Boolean
    fun reset() { currentState = startState }
}

abstract class State {
    abstract fun processSymbol(symbol: String): State
}

class InvalidState : State() {
    override fun processSymbol(symbol: String): State = this
}