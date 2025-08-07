interface ValidationStrategy {
    val startState: State
    fun preconditionsMet(input: String): Boolean = true
}

fun createStateMachine(startState: State): StateMachine {
    return StateMachine(startState)
}

class StateMachine(private var startState: State) {
    private var currentState: State = startState

    fun processSymbol(symbol: String) { currentState = currentState.processSymbol(symbol) }
    fun getResult() = currentState is AcceptState
    fun reset() { currentState = startState }
    fun resetWithStartState(newStartState: State) {
        this.startState = newStartState
        this.currentState = newStartState
    }
}

abstract class State {
    abstract fun processSymbol(symbol: String): State
}

class InvalidState : State() {
    override fun processSymbol(symbol: String): State = this
}

abstract class AcceptState: State()