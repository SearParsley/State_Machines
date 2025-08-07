fun main() {
    val strategy = FloatingPointStrategy()
    val validator = StringValidator(strategy)

    while (true) {
        println("Using floating point strategy, enter string (empty string to exit):")
        val input = readln().trim()
        if (input.isEmpty()) break
        val result = validator.isValid(input)
        println(result)
    }
}
