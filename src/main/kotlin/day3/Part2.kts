import java.io.File

val partNumberRegex = Regex("\\d+")
val partRegex = Regex("\\*")

data class PartNumber(val partNumber: Int, val range: IntRange)

fun getPartNumbers(line: String): List<PartNumber> {
    return partNumberRegex.findAll(line).map { PartNumber(it.value.toInt(), it.range) }.toList()
}

fun getParts(line: String): List<Int> {
    return partRegex.findAll(line).map { it.range.first }.toList()
}

fun isGear(part: Int, previousPartNumbers: List<PartNumber>, currentPartNumbers: List<PartNumber>, nextPartNumbers: List<PartNumber>): Boolean {
    val previousCount = previousPartNumbers.count { part >= (it.range.first - 1)  && part <= (it.range.last + 1) }
    val currentCount = currentPartNumbers.count { part >= (it.range.first - 1)  && part <= (it.range.last + 1) }
    val nextCount = nextPartNumbers.count { part >= (it.range.first - 1)  && part <= (it.range.last + 1) }
    println("$previousCount, $currentCount, $nextCount")
    return previousCount + currentCount + nextCount == 2
}

fun getGearRatio(part: Int, previousPartNumbers: List<PartNumber>, currentPartNumbers: List<PartNumber>, nextPartNumbers: List<PartNumber>): Int {
    val previousPartNumbers = previousPartNumbers.filter { part >= (it.range.first - 1)  && part <= (it.range.last + 1) }
    val currentPartNumbers = currentPartNumbers.filter { part >= (it.range.first - 1)  && part <= (it.range.last + 1) }
    val nextPartNumbers = nextPartNumbers.filter { part >= (it.range.first - 1)  && part <= (it.range.last + 1) }
    return (previousPartNumbers.fold(1, { acc, pn -> acc * pn.partNumber }) * currentPartNumbers.fold(1, { acc, pn -> acc * pn.partNumber }) * nextPartNumbers.fold(1, { acc, pn -> acc * pn.partNumber }))
}

val partsMap = mutableListOf<List<Int>>()
val partNumbersMap = mutableListOf<List<PartNumber>>()
var value = 0
File("input.txt").bufferedReader().useLines { lines ->
    lines.forEach {
        partsMap.add(getParts(it))
        partNumbersMap.add(getPartNumbers(it))
    }
}
for (y in 0 until partsMap.size) {
    for(part in partsMap[y]) {
        if(isGear(part, partNumbersMap.getOrElse(y - 1, { listOf() }), partNumbersMap[y], partNumbersMap.getOrElse(y + 1, { listOf() }))) {
            println("$part, $y")
            value += getGearRatio(part, partNumbersMap.getOrElse(y - 1, { listOf() }), partNumbersMap[y], partNumbersMap.getOrElse(y + 1, { listOf() }))
        }
    }
}
println(value)