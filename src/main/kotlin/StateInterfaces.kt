interface ValidationStrategy {
    val machine: StateMachine
    fun preconditionsMet(input: String): Boolean = true
}

abstract class StateMachine {
    abstract var currentState: State
    abstract var isInvalid: Boolean

    fun processSymbol(symbol: String) {
        if (currentState is InvalidState) return
        currentState = currentState.processSymbol(symbol)
    }

    abstract fun getResult(): Boolean
}

interface State {
    fun processSymbol(symbol: String): State
}

class InvalidState : State {
    override fun processSymbol(symbol: String): State = this
}