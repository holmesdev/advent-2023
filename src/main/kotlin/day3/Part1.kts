import java.io.File

val partNumberRegex = Regex("\\d+")
val partRegex = Regex("[^\\d\\.]")

fun getPartNumbers(line: String): List<Map<String, Any>> {
    return partNumberRegex.findAll(line).map { mapOf("partNumber" to it.value.toInt(), "range" to it.range) }.toList()
}

fun getParts(line: String): List<Int> {
    return partRegex.findAll(line).map { it.range.first }.toList()
}

fun isIncludedPartNumber(partNumber: Map<String, Any>, previousParts: List<Int>, currentParts: List<Int>, nextParts: List<Int>): Boolean {
    val partNumberRange = partNumber["range"] as IntRange
    return previousParts.any { it >= (partNumberRange.first - 1) && it <= (partNumberRange.last + 1) } ||
            currentParts.any { it >= (partNumberRange.first - 1) && it <= (partNumberRange.last + 1) } ||
        nextParts.any { it >= (partNumberRange.first - 1) && it <= (partNumberRange.last + 1) }
}

val partsMap = mutableListOf<List<Int>>()
val partNumbersMap = mutableListOf<List<Map<String, Any>>>()


for (y in 0 until partNumbersMap.size) {
    for(partNumber in partNumbersMap[y]) {
        if(isIncludedPartNumber(partNumber, partsMap.getOrElse(y - 1, { listOf() }), partsMap[y], partsMap.getOrElse(y + 1, { listOf() }))) {
            value += partNumber["partNumber"] as Int
        }
    }
}
println(value)