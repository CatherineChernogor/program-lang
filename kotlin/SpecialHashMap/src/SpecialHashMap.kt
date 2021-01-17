import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap as HashMap

class SpecialHashMapException(message: String) : Exception(message)

open class SpecialHashMap : HashMap<String, Int>() {

    var iloc: ArrayList<Int> = arrayListOf()
    var ploc: Ploc = Ploc()

    inner class Ploc {

        operator fun get(condition: String): MutableMap<String, Int> {
            val parsedConditions = getParsedConditions(condition)
            val sortedBySizeAndValueMap = sortBySizeAndValue(parsedConditions.size, this@SpecialHashMap)

            var resultMap: MutableMap<String, Int> = mutableMapOf()

            for (e in sortedBySizeAndValueMap) {

                val keys: List<String> = e.key.replace(" ", "").replace(")", "").replace("(", "").split(',')

                if (checkKeyByCondition(parsedConditions, keys))
                    resultMap.put(e.key, e.value)

            }
            return resultMap
        }
    }

    fun toSortedSet(): SortedMap<String, Int> {
        val sorted = this.toSortedMap()
        return sorted
    }

    override fun put(key: String, value: Int): Int? {
        var r = super.put(key, value)

        var sorted = this.toSortedSet()
        iloc = arrayListOf()
        for (e in sorted) {
            iloc.add(e.value)
        }
        return r
    }
}


fun getParsedConditions(condition: String): java.util.ArrayList<Pair<String, Int>> {
    val signs = arrayOf('>', '<', '=')

    val trimmedCondition = condition.replace(" ", "")
    var separator = ','
    for (e: Char in trimmedCondition) {
        if (e !in '0'..'9' && e !in signs) separator = e
    }
    val conditions: List<String> = trimmedCondition.split(separator)

    var preparedCndts: java.util.ArrayList<Pair<String, Int>> = arrayListOf()

    for ((i, c) in conditions.withIndex()) {

        var sign = ""
        var number = ""

        for (s in c) {
            when (s) {
                in signs -> sign += s
                in '0'..'9' -> number += s
                else -> throw SpecialHashMapException("Condition contains not valid symbol $s")
            }
        }
        preparedCndts.add(i, Pair(sign, number.toInt()))
    }
    return preparedCndts
}

fun sortBySizeAndValue(conditionSize: Int, map: java.util.HashMap<String, Int>): MutableMap<String, Int> {
    val sizePreparedMap: MutableMap<String, Int> = mutableMapOf()

    for (e in map) {
        var size = e.key.replace(" ", "").split(',').size
        if (size == conditionSize) {
            var isLetter = false
            for (s: Char in e.key) {
                if (s in 'a'..'z' || s in 'A'..'Z') isLetter = true
            }
            if (!isLetter) sizePreparedMap.put(e.key, e.value)
        }
    }
    return sizePreparedMap
}

fun checkKeyByCondition(conditions: java.util.ArrayList<Pair<String, Int>>, keys: List<String>): Boolean {

    for (i in 0 until conditions.size) {
        val key: Int = keys[i].toInt()
        val condition = conditions[i]

        //print("\t" + condition + " " + key+" ")

        when (condition.first) {
            ">" -> if (key > condition.second) continue else return false
            "<" -> if (key < condition.second) continue else return false
            ">=" -> if (key >= condition.second) continue else return false
            "<=" -> if (key > condition.second) continue else return false
            "<>" -> if (key != condition.second) continue else return false
            "=" -> if (key == condition.second) continue else return false
        }
    }
    return true
}
