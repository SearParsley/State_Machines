fun main() {

    val strategy = IntegerStrategy()
    val validator = StringValidator(strategy)

    val input = readln().trim()
    val result = validator.isValid(input)

    println(result)
}
