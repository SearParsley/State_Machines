import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MockValidationStrategy(
    override val startState: State,
    private val preconditions: (String) -> Boolean = { true }
) : ValidationStrategy {
    override fun preconditionsMet(input: String): Boolean {
        return preconditions(input)
    }
}

class TestAcceptState : AcceptState() {
    override fun processSymbol(symbol: String): State = this
}

class TransitionState(private val transitions: Map<String, State>) : State() {
    private val invalidState = InvalidState()
    override fun processSymbol(symbol: String): State {
        return transitions[symbol] ?: invalidState
    }
}

@DisplayName("StringValidator Unit Tests")
class StringValidatorTest {
    private val finalState = TestAcceptState()
    private val startState = TransitionState(transitions = mapOf("a" to finalState))
    private val strategy = MockValidationStrategy(startState)

    @Test
    fun `isValid returns true for a valid string`() {
        val validator = StringValidator(strategy)
        val result = validator.isValid("a")
        assertTrue(result, "The string 'a' should be valid.")
    }

    @Test
    fun `isValid returns false for an invalid string`() {
        val validator = StringValidator(strategy)
        val result = validator.isValid("b")
        assertFalse(result, "The string 'b' should be invalid.")
    }

    @Test
    fun `isValid returns false if preconditions are not met`() {
        val failingPreconditionStrategy = MockValidationStrategy(startState) { input -> input.length > 3 }
        val validator = StringValidator(failingPreconditionStrategy)
        val result = validator.isValid("a")
        assertFalse(result, "Validation should fail if preconditions are not met.")
    }

    @Test
    fun `setStrategy updates the validation logic`() {
        val validator = StringValidator(strategy)
        val finalStateZ = TestAcceptState()
        val startStateZ = TransitionState(transitions = mapOf("z" to finalStateZ))
        val newStrategy = MockValidationStrategy(startStateZ)
        assertTrue(validator.isValid("a"), "Should be valid with the initial strategy.")
        validator.setStrategy(newStrategy)
        assertFalse(validator.isValid("a"), "String 'a' should be invalid with the new strategy.")
        assertTrue(validator.isValid("z"), "String 'z' should be valid with the new strategy.")
    }

    @Test
    fun `validator works correctly for multiple consecutive calls`() {
        val validator = StringValidator(strategy)
        assertTrue(validator.isValid("a"), "First call with 'a' should be valid.")
        assertFalse(validator.isValid("b"), "Second call with 'b' should be invalid.")
        assertTrue(validator.isValid("a"), "Third call with 'a' should also be valid, proving a reset.")
    }
}
