import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@DisplayName("BinaryStrategy Unit Tests")
class BinaryStrategyTest {
    private val binaryStrategy = BinaryStrategy()
    private val validator = StringValidator(binaryStrategy)

    @Test
    fun `should return true for valid binary numbers that end in 1`() {
        assertTrue(validator.isValid("1"), "A single '1' should be valid.")
        assertTrue(validator.isValid("11"), "Multiple '1's should be valid.")
        assertTrue(validator.isValid("101"), "A valid binary number ending in '1' should be valid.")
        assertTrue(validator.isValid("10001"), "A valid binary number with multiple '0's ending in '1' should be valid.")
        assertTrue(validator.isValid("1110101"), "A longer valid binary number ending in '1' should be valid.")
    }

    @Test
    fun `should return false for binary numbers ending in 0`() {
        assertFalse(validator.isValid("10"), "A binary number ending in '0' is invalid by this strategy.")
        assertFalse(validator.isValid("110"), "A binary number ending in '0' is invalid by this strategy.")
    }

    @Test
    fun `should return false for binary numbers starting with 0`() {
        assertFalse(validator.isValid("0"), "A single '0' is invalid.")
        assertFalse(validator.isValid("01"), "A binary number cannot start with '0'.")
    }

    @Test
    fun `should return false for non-binary characters`() {
        assertFalse(validator.isValid("2"), "Non-binary digits are invalid.")
        assertFalse(validator.isValid("12"), "Non-binary digits are invalid.")
        assertFalse(validator.isValid("102"), "Non-binary digits are invalid.")
        assertFalse(validator.isValid("1012"), "Non-binary digits are invalid.")
        assertFalse(validator.isValid("a"), "Letters are invalid.")
    }

    @Test
    fun `should return false for empty or blank strings`() {
        assertFalse(validator.isValid(""), "An empty string is not a valid binary number.")
        assertFalse(validator.isValid(" "), "A blank string is not a valid binary number.")
    }
}