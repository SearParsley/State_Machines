class EmailStrategy : ValidationStrategy {
    override val startState = EmailStartState()
    override fun preconditionsMet(input: String): Boolean = !input.contains(" ")
}

class EmailStartState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in ".@" -> InvalidState()
            else -> EmailPartOneState()
        }
    }
}

class EmailPartOneState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "@" -> EmailAtSymbolState()
            else -> this
        }
    }
}

class EmailAtSymbolState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in ".@" -> InvalidState()
            else -> EmailPartTwoState()
        }
    }
}

class EmailPartTwoState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "." -> EmailPeriodState()
            in "@" -> InvalidState()
            else -> this
        }
    }
}

class EmailPeriodState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in ".@" -> InvalidState()
            else -> EmailAcceptState()
        }
    }
}

class EmailAcceptState : AcceptState() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in ".@" -> InvalidState()
            else -> this
        }
    }
}