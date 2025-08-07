class FloatingPointStrategy : ValidationStrategy {
    override val machine: StateMachine = FloatingPointStateMachine()
}

class FloatingPointStateMachine() : StateMachine(FloatingPointStartState()) {
    override fun getResult(): Boolean = currentState is FloatingPointAcceptState
}

class FloatingPointStartState() : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "123456789" -> FloatingPointPreDecimalState()
            in "0" -> FloatingPointLeadingZeroState()
            in "." -> FloatingPointDecimalState()
            else -> InvalidState()
        }
    }
}

class FloatingPointPreDecimalState() : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "0123456789" -> FloatingPointPreDecimalState()
            in "." -> FloatingPointDecimalState()
            else -> InvalidState()
        }
    }
}

class FloatingPointLeadingZeroState() : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "." -> FloatingPointDecimalState()
            else -> InvalidState()
        }
    }
}

class FloatingPointDecimalState() : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "0123456789" -> FloatingPointAcceptState()
            else -> InvalidState()
        }
    }
}

class FloatingPointAcceptState() : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in "0123456789" -> FloatingPointAcceptState()
            else -> InvalidState()
        }
    }
}