import java.io.File
import java.math.BigInteger

class Card(data: String) {
    val id = cardIdRegex.find(data)!!.destructured.component1().toInt()
    val winningNumbers = data.split(":")[1].split("|")[0].trim().split(" ").filter { it.length > 0 }.map { it.toInt() }
    val numbersYouHave = data.split("|")[1].trim().split(" ").filter { it.length > 0 }.map { it.toInt() }

    fun getExtraCardIds(): List<Int> {
        val count = getMatchingNumberCount()
        return if (count == 0) listOf() else (id+1..id+getMatchingNumberCount()).toList()
    }

    private fun getMatchingNumberCount(): Int {
        return winningNumbers.filter { numbersYouHave.contains(it) }.size
    }

    companion object {
        val cardIdRegex = "Card\\s+(\\d+):".toRegex()
    }
}

var cardCounts = mutableMapOf<Int, Int>()
var value = 0
File("input.txt").bufferedReader().useLines { lines ->
    lines.forEach {
        val card = Card(it)
        cardCounts[card.id] = cardCounts.getOrElse(card.id, { 0 }) + 1
        card.getExtraCardIds().forEach({
            cardCounts[it] = cardCounts.getOrElse(it, { 0 }) + cardCounts[card.id]!!
        })
        value += cardCounts[card.id]!!
    }
}

println(value)