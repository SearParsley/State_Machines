fun main() {
    val strategy = EmailStrategy()
    val validator = StringValidator(strategy)

    while (true) {
        println("Using email strategy, enter string (empty string to exit):")
        val input = readln()
        if (input.isEmpty()) break
        val result = validator.isValid(input)
        println(result)
    }
}
