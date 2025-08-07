import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

// A mock implementation of ValidationStrategy for controlling preconditions in tests.
class MockValidationStrategy(
    override val startState: State,
    private val preconditions: (String) -> Boolean = { true }
) : ValidationStrategy {
    override fun preconditionsMet(input: String): Boolean {
        return preconditions(input)
    }
}

// A concrete implementation of an accepting state for tests.
class TestAcceptState : AcceptState() {
    override fun processSymbol(symbol: String): State = this
}

// A state that transitions to another state based on input, or to an InvalidState.
class TransitionState(private val transitions: Map<String, State>) : State() {
    private val invalidState = InvalidState()
    override fun processSymbol(symbol: String): State {
        return transitions[symbol] ?: invalidState
    }
}


// --- Main Test Class ---
@DisplayName("StringValidator Unit Tests")
class StringValidatorTest {

    // Define a simple state machine for testing: accepts the single character "a"
    private val finalState = TestAcceptState()
    private val startState = TransitionState(transitions = mapOf("a" to finalState))
    private val strategy = MockValidationStrategy(startState)

    @Test
    fun `isValid returns true for a valid string`() {
        // Arrange
        val validator = StringValidator(strategy)

        // Act
        val result = validator.isValid("a")

        // Assert
        assertTrue(result, "The string 'a' should be valid.")
    }

    @Test
    fun `isValid returns false for an invalid string`() {
        // Arrange
        val validator = StringValidator(strategy)

        // Act
        val result = validator.isValid("b") // "b" leads to InvalidState

        // Assert
        assertFalse(result, "The string 'b' should be invalid.")
    }

    @Test
    fun `isValid returns false if preconditions are not met`() {
        // Arrange
        val failingPreconditionStrategy = MockValidationStrategy(startState) { input -> input.length > 3 }
        val validator = StringValidator(failingPreconditionStrategy)

        // Act
        val result = validator.isValid("a")

        // Assert
        assertFalse(result, "Validation should fail if preconditions are not met.")
    }

    @Test
    fun `setStrategy updates the validation logic`() {
        // Arrange
        // Initial strategy accepts "a"
        val validator = StringValidator(strategy)

        // New strategy accepts "z"
        val finalStateZ = TestAcceptState()
        val startStateZ = TransitionState(transitions = mapOf("z" to finalStateZ))
        val newStrategy = MockValidationStrategy(startStateZ)

        // Act & Assert - Test with the initial strategy
        assertTrue(validator.isValid("a"), "Should be valid with the initial strategy.")

        // Act & Assert - Change the strategy and test again
        validator.setStrategy(newStrategy)

        assertFalse(validator.isValid("a"), "String 'a' should be invalid with the new strategy.")
        assertTrue(validator.isValid("z"), "String 'z' should be valid with the new strategy.")
    }

    @Test
    fun `validator works correctly for multiple consecutive calls`() {
        // Arrange
        val validator = StringValidator(strategy)

        // Act & Assert - First call (valid)
        assertTrue(validator.isValid("a"), "First call with 'a' should be valid.")

        // Act & Assert - Second call (invalid)
        assertFalse(validator.isValid("b"), "Second call with 'b' should be invalid.")

        // Act & Assert - Third call (valid again, proving the machine was reset)
        assertTrue(validator.isValid("a"), "Third call with 'a' should also be valid, proving a reset.")
    }
}
