class EmailStrategy : ValidationStrategy {
    override val machine = EmailStateMachine()
}

class EmailStateMachine : StateMachine(EmailStartState()) {
    override fun getResult(): Boolean = currentState is EmailAcceptState
}

class EmailStartState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in ".@ " -> InvalidState()
            else -> EmailPartOneState()
        }
    }
}

class EmailPartOneState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "@" -> EmailAtSymbolState()
            in ". " -> InvalidState()
            else -> EmailPartOneState()
        }
    }
}

class EmailAtSymbolState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in ".@ " -> InvalidState()
            else -> EmailPartTwoState()
        }
    }
}

class EmailPartTwoState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "." -> EmailPeriodState()
            in "@ " -> InvalidState()
            else -> EmailPartTwoState()
        }
    }
}

class EmailPeriodState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in ".@ " -> InvalidState()
            else -> EmailAcceptState()
        }
    }
}

class EmailAcceptState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in ".@ " -> InvalidState()
            else -> EmailAcceptState()
        }
    }
}