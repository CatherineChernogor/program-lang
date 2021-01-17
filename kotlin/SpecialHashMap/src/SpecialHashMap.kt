import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap as HashMap

class SpecialHashMap : HashMap<String, Int>() {

    var iloc: ArrayList<Int> = arrayListOf()


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

    fun ploc(condition: String): MutableMap<String, Int> {

        val parsedConditions = getParsedConditions(condition)
//        println("parsedConditions $parsedConditions")

        val sortedBySizeAndValueMap = sortBySizeAndValue(parsedConditions.size, this)
//        println("sortedBySizeMap $sortedBySizeMap")

        var resultMap: MutableMap<String, Int> = mutableMapOf()



        for (e in sortedBySizeAndValueMap) {

            val keys: List<String> = e.key.replace(" ", "").replace(")", "").replace("(", "").split(',')

            if (checkKeyByCondition(parsedConditions, keys)) {
//                println("sortedBySizeMapElement $e")
                resultMap.put(e.key, e.value)
            }
        }
        return resultMap
    }
}

fun getParsedConditions(condition: String): ArrayList<Pair<String, Int>> {
    val conditions: List<String> = condition.replace(" ", "").split(',')

    var preparedCndts: ArrayList<Pair<String, Int>> = arrayListOf()
    val signs = arrayOf('>', '<', '=')

    for ((i, c) in conditions.withIndex()) {

        var sign = ""
        var number = ""

        for (s in c) {
            if (s in signs) sign += s
            else number += s
        }
        preparedCndts.add(i, Pair(sign, number.toInt()))
    }
    return preparedCndts
}

fun sortBySizeAndValue(conditionSize: Int, map: HashMap<String, Int>): MutableMap<String, Int> {
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
    println(sizePreparedMap)
    return sizePreparedMap
}

fun checkKeyByCondition(conditions: ArrayList<Pair<String, Int>>, keys: List<String>): Boolean {

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

// выкидывать исключение если в условии есть буквы и/или оно задано неверно
// поменять разделитель с запятой на любой символ кроме <>=0123456789
// поменять доступ с поля на метод