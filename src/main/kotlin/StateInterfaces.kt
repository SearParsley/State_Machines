interface ValidationStrategy {
    val machine: StateMachine
    fun preconditionsMet(input: String): Boolean = true
}

abstract class StateMachine {
    protected abstract var currentState: State

    fun processSymbol(symbol: String) {
        currentState = currentState.processSymbol(symbol)
    }

    fun getResult(): Boolean = currentState !is InvalidState
}

abstract class State {
    abstract fun processSymbol(symbol: String): State
}

class InvalidState : State() {
    override fun processSymbol(symbol: String): State = this
}