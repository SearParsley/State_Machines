class ComplexPasswordStrategy : ValidationStrategy {
    override val startState = ComplexPasswordStartState()
    override fun preconditionsMet(input: String): Boolean = input.length >= 8
}

val capitalLetters = ('A'..'Z').joinToString("")
const val specialChars = "!@#$%&*"

class ComplexPasswordStartState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in specialChars -> ComplexPasswordSpecialCharState()
            in capitalLetters -> ComplexPasswordCapitalLetterState()
            else -> ComplexPasswordSimpleState()
        }
    }
}

class ComplexPasswordSimpleState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in specialChars -> ComplexPasswordSpecialCharState()
            in capitalLetters -> ComplexPasswordCapitalLetterState()
            else -> this
        }
    }
}

class ComplexPasswordCapitalLetterState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in specialChars -> ComplexPasswordTrailingSpecialCharState()
            else -> this
        }
    }
}

class ComplexPasswordSpecialCharState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in capitalLetters -> ComplexPasswordAcceptState()
            else -> this
        }
    }
}

class ComplexPasswordTrailingSpecialCharState : State() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in specialChars -> this
            else -> ComplexPasswordAcceptState()
        }
    }
}

class ComplexPasswordAcceptState : AcceptState() {
    override fun processSymbol(symbol: String): State {
        return when (symbol) {
            in specialChars -> ComplexPasswordTrailingSpecialCharState()
            else -> this
        }
    }
}