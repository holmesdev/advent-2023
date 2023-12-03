import java.io.File

val gameIdRegex = Regex("Game (\\d+):")
val redRegex = "(\\d+) red".toRegex()
val greenRegex = "(\\d+) green".toRegex()
val blueRegex = "(\\d+) blue".toRegex()

fun parseGame(game: String): Map<String, Int> {
    val id = gameIdRegex.find(game)!!.destructured.component1().toInt()
    val red = redRegex.findAll(game).fold(0, { acc, matchResult -> Math.max(acc, matchResult.destructured.component1().toInt()) })
    val green = greenRegex.findAll(game).fold(0, { acc, matchResult -> Math.max(acc, matchResult.destructured.component1().toInt()) })
    val blue = blueRegex.findAll(game).fold(0, { acc, matchResult -> Math.max(acc, matchResult.destructured.component1().toInt()) })
    return mapOf(
        "id" to id,
        "red" to red,
        "blue" to blue,
        "green" to green
    )
}

fun gamePower(game: Map<String, Int>): Int {
    return game["red"]!! * game["green"]!! * game["blue"]!!
}

var value = 0
File("input.txt").bufferedReader().useLines { lines ->
    lines.forEach {
        val game = parseGame(it)
        value += gamePower(game)
    }
}
println(value)