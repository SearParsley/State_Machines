class BinaryStrategy : ValidationStrategy {
    override val startState = BinaryStartState()
    override fun preconditionsMet(input: String): Boolean = !input.contains(" ")
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
            in "0" -> this
            in "1" -> BinaryAcceptState()
            else -> InvalidState()
        }
    }
}

class BinaryAcceptState : AcceptState() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "0" -> BinaryInternalState()
            in "1" -> this
            else -> InvalidState()
        }
    }
}