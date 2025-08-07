class IntegerStrategy : ValidationStrategy {
    override val startState = IntegerStartState()
}

class IntegerStartState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "123456789" -> IntegerAcceptState()
            else -> InvalidState()
        }
    }
}

class IntegerAcceptState : AcceptState() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "0123456789" -> IntegerAcceptState()
            else -> InvalidState()
        }
    }
}