import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@DisplayName("IntegerStrategy Unit Tests")
class IntegerStrategyTest {

    private val integerStrategy = IntegerStrategy()
    private val validator = StringValidator(integerStrategy)

    @Test
    fun `should return true for a single valid digit`() {
        assertTrue(validator.isValid("1"), "A single non-zero digit should be a valid integer.")
        assertTrue(validator.isValid("9"), "A single non-zero digit should be a valid integer.")
    }

    @Test
    fun `should return true for a multi-digit number`() {
        assertTrue(validator.isValid("10"), "A multi-digit number should be valid.")
        assertTrue(validator.isValid("1234567890"), "A long multi-digit number should be valid.")
    }

    @Test
    fun `should return false for a number starting with zero`() {
        assertFalse(validator.isValid("0"), "A single zero is not a valid integer in this strategy.")
        assertFalse(validator.isValid("01"), "A number starting with zero is not valid.")
    }

    @Test
    fun `should return false for non-digit characters`() {
        assertFalse(validator.isValid("a"), "A letter is not a valid integer.")
        assertFalse(validator.isValid("1a"), "An integer with a letter is not valid.")
        assertFalse(validator.isValid("1-2"), "An integer with a symbol is not valid.")
    }

    @Test
    fun `should return false for empty or blank strings`() {
        assertFalse(validator.isValid(""), "An empty string is not a valid integer.")
        assertFalse(validator.isValid(" "), "A blank string is not a valid integer.")
    }
}