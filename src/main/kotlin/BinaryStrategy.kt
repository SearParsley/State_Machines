class BinaryStrategy : ValidationStrategy {
    override val machine = StateMachine(BinaryStartState())
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

class BinaryAcceptState : AcceptState() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "0" -> BinaryInternalState()
            in "1" -> BinaryAcceptState()
            else -> InvalidState()
        }
    }
}