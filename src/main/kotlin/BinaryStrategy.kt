class BinaryStrategy : ValidationStrategy {
    override val machine = BinaryStateMachine()
}

class BinaryStateMachine : StateMachine(BinaryStartState()) {
    override fun getResult(): Boolean = currentState is BinaryAcceptState
}

class BinaryStartState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "1" -> BinaryAcceptState()
            else -> InvalidState()
        }
    }
}

class BinaryInternalState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "1" -> BinaryAcceptState()
            in "0" -> BinaryInternalState()
            else -> InvalidState()
        }
    }
}

class BinaryAcceptState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "0" -> BinaryInternalState()
            in "1" -> BinaryAcceptState()
            else -> InvalidState()
        }
    }
}