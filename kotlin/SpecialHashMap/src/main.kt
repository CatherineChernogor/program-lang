fun main() {
    var t = tests()

    val map = SpecialHashMap()
    map["value1"] = 1
    map["value2"] = 2
    map["value3"] = 3
    map["1"] = 10
    map["2"] = 20
    map["3"] = 30
    map["1, 5"] = 100
    map["5, 5"] = 200
    map["10, 5"] = 300

//    t.testCreating(map)
//    t.testSorting(map)

    println(t.testILoc(10, map, 0))
    println(t.testILoc(300, map, 2))
    println(t.testILoc(200, map, 5))
    println(t.testILoc(3, map, 8))

//    println(map.iloc[0])  // >>> 10
//    println(map.iloc[2])  // >>> 300
//    println(map.iloc[5])  // >>> 200
//    println(map.iloc[8])  // >>> 3

    val map2 = SpecialHashMap()
    map2["value1"] = 1
    map2["value2"] = 2
    map2["value3"] = 3
    map2["1"] = 10
    map2["2"] = 20
    map2["3"] = 30
    map2["(1, 5)"] = 100
    map2["(5, 5)"] = 200
    map2["(10, 5)"] = 300
    map2["(1, 5, 3)"] = 400
    map2["(5, 5, 4)"] = 500
    map2["(10, 5, 5)"] = 600

    println(t.testPLoc(">=1", map2, mapOf("1" to 10, "2" to 20, "3" to 30)))
    println(t.testPLoc("<3", map2, mapOf("1" to 10, "2" to 20)))

    println(t.testPLoc(">0, >0", map2, mapOf("(1, 5)" to 100, "(5, 5)" to 200, "(10, 5)" to 300)))
    println(t.testPLoc(">=10^ >0", map2, mapOf("(10, 5)" to 300)))

    println(t.testPLoc("<5! >=5! >=3", map2, mapOf("(1, 5, 3)" to 400)))


//    println(map.ploc[">=1"]) // >>> {1=10, 2=20, 3=30}
//    println(map.ploc["<3"]) // >>> {1=10, 2=20}
//
//    println(map.ploc[">0, >0"]) // >>> {(1, 5)=100, (5, 5)=200, (10, 5)=300}
//    println(map.ploc[">=10, >0"]) // >>> {(10, 5)=300}
//
//    println(map.ploc["<5, >=5, >=3"]) // >>> {(1, 5, 3)=400}
}

