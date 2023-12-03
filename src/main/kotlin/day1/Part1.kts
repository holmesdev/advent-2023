import java.io.File

fun getValue(input: String): Int {
    val nums = input.filter { it.isDigit() }.map { it.toString().toInt() }
    return (nums[0].toString() + nums[nums.size - 1].toString()).toInt()
}

var value = 0
File("input.txt").bufferedReader().useLines { lines ->
    lines.forEach {
        value += getValue(it)
    }
}
println(value)