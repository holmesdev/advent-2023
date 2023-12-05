import java.io.File
import java.math.BigInteger

class Card(data: String) {
    val id = cardIdRegex.find(data)!!.destructured.component1().toInt()
    val winningNumbers = data.split(":")[1].split("|")[0].trim().split(" ").filter { it.length > 0 }.map { it.toInt() }
    val numbersYouHave = data.split("|")[1].trim().split(" ").filter { it.length > 0 }.map { it.toInt() }

    fun getPoints(): Int {
        val count = getMatchingNumbers().size
        return if(count == 0) 0 else (BigInteger.valueOf(2).pow(count - 1)).toInt()
    }

    private fun getMatchingNumbers(): List<Int> {
        return winningNumbers.filter { numbersYouHave.contains(it) }
    }

    companion object {
        val cardIdRegex = "Card\\s+(\\d+):".toRegex()
    }
}

var value = 0
File("input.txt").bufferedReader().useLines { lines ->
    lines.forEach {
        val card = Card(it)
        value += card.getPoints()
    }
}
println(value)