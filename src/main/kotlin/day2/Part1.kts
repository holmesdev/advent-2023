import java.io.File

val gameIdRegex = Regex("Game (\\d+):")
val redRegex = "(\\d+) red".toRegex()
val greenRegex = "(\\d+) green".toRegex()
val blueRegex = "(\\d+) blue".toRegex()

fun parseGameId(game: String): Int {
    println(game)
    val (id) = gameIdRegex.find(game)!!.destructured
    return id.toInt()
}

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

fun gameIsPossible(game: Map<String, Int>): Boolean {
    return game["red"]!! <= 12 && game["green"]!! <= 13 && game["blue"]!! <= 14
}

var value = 0
File("input.txt").bufferedReader().useLines { lines ->
    lines.forEach {
        val game = parseGame(it)
        if (gameIsPossible(game)) {
            value += game["id"]!!
        }
    }
}
println(value)