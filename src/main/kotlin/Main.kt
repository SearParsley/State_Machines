fun main() {
    val strategy = IntegerStrategy()
    val validator = StringValidator(strategy)

    println("Using integer strategy, enter string:")
    val input = readln().trim()
    val result = validator.isValid(input)

    println(result)
}
