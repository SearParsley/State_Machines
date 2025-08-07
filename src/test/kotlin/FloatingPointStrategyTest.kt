import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@DisplayName("FloatingPointStrategy Unit Tests")
class FloatingPointStrategyTest {
    private val floatingPointStrategy = FloatingPointStrategy()
    private val validator = StringValidator(floatingPointStrategy)

    @Test
    fun `should return true for valid floating point numbers`() {
        assertTrue(validator.isValid("1.23"), "Standard float should be valid.")
        assertTrue(validator.isValid("0.5"), "Float starting with zero should be valid.")
        assertTrue(validator.isValid(".5"), "Float starting with a decimal point should be valid.")
        assertTrue(validator.isValid("123.456"), "Longer float should be valid.")
    }

    @Test
    fun `should return false for integers`() {
        // This strategy requires a decimal part to be considered a valid float.
        assertFalse(validator.isValid("123"), "An integer without a decimal part is not a valid float.")
        assertFalse(validator.isValid("0"), "A single zero is not a valid float.")
    }

    @Test
    fun `should return false for invalid formats`() {
        assertFalse(validator.isValid("1.2.3"), "Multiple decimal points are invalid.")
        assertFalse(validator.isValid("1."), "Ending with a decimal point is invalid.")
        assertFalse(validator.isValid("."), "A single decimal point is invalid.")
        assertFalse(validator.isValid("01.2"), "Leading zeros before the decimal are invalid.")
    }

    @Test
    fun `should return false for non-digit characters`() {
        assertFalse(validator.isValid("a1.2"), "Letters pre-decimal are invalid.")
        assertFalse(validator.isValid("1a.2"), "Letters pre-decimal are invalid.")
        assertFalse(validator.isValid("1.a2"), "Letters post-decimal are invalid.")
        assertFalse(validator.isValid("1.2-3"), "Symbols are invalid.")
    }

    @Test
    fun `should return false for empty or blank strings`() {
        assertFalse(validator.isValid(""), "An empty string is not a valid float.")
        assertFalse(validator.isValid(" "), "A blank string is not a valid float.")
    }
}