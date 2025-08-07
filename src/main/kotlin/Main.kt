fun main() {
    val defaultStrategy = IntegerStrategy()
    val validator = StringValidator(defaultStrategy)

    while (true) {
        try {
            println("Choose a validation strategy (empty string to exit):")
            println("    1. Integer (default)")
            println("    2. Floating Point")
            println("    3. Binary")
            println("    4. Email")
            println("    5. Complex Password")

            val input = readln()
            if (input.isEmpty()) break

            val strategy = when (input) {
                in "1" -> IntegerStrategy()
                in "2" -> FloatingPointStrategy()
                in "3" -> BinaryStrategy()
                in "4" -> EmailStrategy()
                in "5" -> ComplexPasswordStrategy()
                else -> IntegerStrategy()
            }

            validator.setStrategy(strategy)

            val strategyName = when (input) {
                in "1" -> "Integer"
                in "2" -> "Floating Point"
                in "3" -> "Binary"
                in "4" -> "Email"
                in "5" -> "Complex Password"
                else -> "Integer"
            }

            println("Validator using $strategyName strategy.")

        } catch (e: Error) {
            println("Invalid input, defaulting to Integer strategy.")
            validator.setStrategy(IntegerStrategy())
        }

        while (true) {
            println("Enter string (empty string to go back):")
            val input = readln()
            if (input.isEmpty()) break
            val result = validator.isValid(input)
            println(result)
        }
    }
}
