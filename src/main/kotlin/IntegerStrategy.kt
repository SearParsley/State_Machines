class IntegerStrategy : ValidationStrategy {
    override val machine = IntegerStateMachine()
}

class IntegerStateMachine : StateMachine() {
    override var currentState: State = IntegerStartState()
}

class IntegerStartState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "123456789" -> IntegerAcceptState()
            else -> InvalidState()
        }
    }
}

class IntegerAcceptState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "0123456789" -> IntegerAcceptState()
            else -> InvalidState()
        }
    }
}