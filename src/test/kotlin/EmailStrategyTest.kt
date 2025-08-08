import org.junit.jupiter.api.DisplayName
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@DisplayName("EmailStrategy Unit Tests")
class EmailStrategyTest {
    private val emailStrategy = EmailStrategy()
    private val validator = StringValidator(emailStrategy)

    @Test
    fun `should return true for valid emails`() {
        assertTrue(validator.isValid("test@example.com"), "A standard email should be valid.")
        assertTrue(validator.isValid("test.test@example.com"), "A standard email should be valid.")
        assertTrue(validator.isValid("a@b.c"), "A minimal email should be valid.")
        assertTrue(validator.isValid("test@example.co"), "A two-letter TLD should be valid.")
    }

    @Test
    fun `should return false for invalid emails`() {
        assertFalse(validator.isValid("test"), "Email without @ and domain is invalid.")
        assertFalse(validator.isValid("test@"), "Email without domain is invalid.")
        assertFalse(validator.isValid("test@example"), "Email without TLD is invalid.")
        assertFalse(validator.isValid("test@example."), "Email ending in a period is invalid.")
        assertFalse(validator.isValid("test@example.com@"), "Email ending with @ is invalid.")
        assertFalse(validator.isValid("@example.com"), "Email starting with @ is invalid.")
        assertFalse(validator.isValid(".test@example.com"), "Email starting with a period is invalid.")
        assertFalse(validator.isValid("test@.example.com"), "Domain starting with a period is invalid.")
        assertFalse(validator.isValid("test@example..com"), "Domain with consecutive periods is invalid.")
        assertFalse(validator.isValid("test@@example.com"), "Email with consecutive @ is invalid.")
        assertFalse(validator.isValid("test@ex@ample.com"), "Email with several @ is invalid.")
    }

    @Test
    fun `should return false for emails with spaces due to precondition`() {
        assertFalse(validator.isValid("test@example.com "), "Email with a trailing space is invalid.")
        assertFalse(validator.isValid(" test@example.com"), "Email with a leading space is invalid.")
        assertFalse(validator.isValid("tes t@example.com"), "Email with an internal space is invalid.")
        assertFalse(validator.isValid("test @example.com"), "Email with an internal space is invalid.")
        assertFalse(validator.isValid("test@ example.com"), "Email with an internal space is invalid.")
        assertFalse(validator.isValid("test@ex ample.com"), "Email with an internal space is invalid.")
        assertFalse(validator.isValid("test@example .com"), "Email with an internal space is invalid.")
        assertFalse(validator.isValid("test@example. com"), "Email with an internal space is invalid.")
        assertFalse(validator.isValid("test@example.co m"), "Email with an internal space is invalid.")
    }
}