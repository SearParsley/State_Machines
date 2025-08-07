class EmailStrategy : ValidationStrategy {
    override val machine = EmailStateMachine()
    override fun preconditionsMet(input: String): Boolean = input.contains(" ")
}

class EmailStateMachine : StateMachine(EmailStartState())

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
            in "." -> InvalidState()
            else -> EmailPartOneState()
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
            else -> EmailPartTwoState()
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
            else -> EmailAcceptState()
        }
    }
}