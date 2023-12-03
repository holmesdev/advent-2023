import java.io.File

val nums = listOf("one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "1", "2", "3", "4", "5", "6", "7", "8", "9")
var numRegex = Regex("one|two|three|four|five|six|seven|eight|nine|1|2|3|4|5|6|7|8|9")

val numMap = mapOf(
    "one" to "1",
    "two" to "2",
    "three" to "3",
    "four" to "4",
    "five" to "5",
    "six" to "6",
    "seven" to "7",
    "eight" to "8",
    "nine" to "9"
)

fun findFirstNumber(input: String): String {
    var match = numRegex.find(input, 0)!!
    return numMap[match.value] ?: match.value
}

fun findLastNumber(input: String): String {
    var match = numRegex.find(input, input.lastIndexOfAny(nums))!!
    return numMap[match.value] ?: match.value
}

var value = 0
File("input.txt").bufferedReader().useLines { lines ->
    lines.forEach {
        value += (findFirstNumber(it) + findLastNumber(it)).toInt()
    }
}
println(value)