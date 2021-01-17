class tests {

    fun testCreating(map: SpecialHashMap) {
        for (e in map) {
            println(e)
        }
        println()
    }

    fun testSorting(map: SpecialHashMap) {
        var sorted = map.toSortedSet()
        for (e in sorted) {
            println(e)
        }
        println()
    }

    fun testILoc(result: Int, map: SpecialHashMap, index: Int): Boolean {
        return result == map.iloc[index]
    }

    fun testPLoc(condition: String, map: SpecialHashMap, result: Map<String, Int>): Boolean {
        return result == map.ploc(condition)
    }
}