class IntegerStrategy : ValidationStrategy {
    override val machine = IntegerStateMachine()
}

class IntegerStateMachine : StateMachine() {
    override var currentState: State = IntegerStartState()
    override var isInvalid = false

    override fun getResult(): Boolean {
        return currentState is IntegerAcceptState
    }

}

class IntegerStartState : State {
    override fun processSymbol(machine: StateMachine, symbol: String) {
        machine.currentState = when (symbol) {
            in "123456789" -> IntegerAcceptState()
            else -> InvalidState()
        }
    }
}

class IntegerAcceptState : State {
    override fun processSymbol(machine: StateMachine, symbol: String) {
        machine.currentState = when (symbol) {
            in "0123456789" -> IntegerAcceptState()
            else -> InvalidState()
        }
    }
}

class InvalidState : State {
    override fun processSymbol(machine: StateMachine, symbol: String) {
        machine.isInvalid = true
    }
}