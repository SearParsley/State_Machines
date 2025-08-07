import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@DisplayName("ComplexPasswordStrategy Unit Tests")
class ComplexPasswordStrategyTest {
    private val complexPasswordStrategy = ComplexPasswordStrategy()
    private val validator = StringValidator(complexPasswordStrategy)

    @Test
    fun `should return true for valid complex passwords`() {
        assertTrue(validator.isValid("Password!1"), "A valid password should be accepted.")
        assertTrue(validator.isValid("!Password1"), "A valid password starting with a special char should be accepted.")
        assertTrue(validator.isValid("myP@ssword"), "A valid password with mixed chars should be accepted.")
    }

    @Test
    fun `should return false for passwords shorter than 8 characters`() {
        assertFalse(validator.isValid("Pass!1"), "A password shorter than 8 chars is invalid.")
    }

    @Test
    fun `should return false for passwords without a capital letter`() {
        assertFalse(validator.isValid("password!1"), "A password without a capital letter is invalid.")
    }

    @Test
    fun `should return false for passwords without a special character`() {
        assertFalse(validator.isValid("Password123"), "A password without a special character is invalid.")
    }

    @Test
    fun `should return false for passwords with a trailing special character`() {
        assertFalse(validator.isValid("Password!!!"), "A password without a special character is invalid.")
        assertFalse(validator.isValid("P@ssword!"), "A password without a special character is invalid.")
    }

    @Test
    fun `should return false for passwords with only one requirement met`() {
        assertFalse(validator.isValid("password"), "A password with no special or capital chars is invalid.")
        assertFalse(validator.isValid("PASSWORD"), "A password with only capital letters is invalid.")
        assertFalse(validator.isValid("!@#$%&*("), "A password with only special chars is invalid.")
    }
}