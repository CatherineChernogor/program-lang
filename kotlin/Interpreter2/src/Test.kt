class Test {
    val test1 = "BEGIN\n" +
            "END."
    val test2 = "BEGIN\n" +
            "\tx:= 2 + 3 * (2 + 3);\n" +
            "        y:= 2 / 2 - 2 + 3 * ((1 + 1) + (1 + 1));\n" +
            "END."
    val test3 = "BEGIN\n" +
            "    y: = 2;\n" +
            "    BEGIN\n" +
            "        a := 3;\n" +
            "        a := a;\n" +
            "        b := 10 + a + 10 * y / 4;\n" +
            "        c := a - b\n" +
            "    END;\n" +
            "    x := 11;\n" +
            "END."

    fun checkTest(text: String) {
        val parser = Parser(Lexer(text))
        val tree = parser.parse()
        val interpreter = Interpreter(false)

        println("$text\n")
        interpreter.interpret(tree)
        println("\n----------------------------------------------------------\n")
    }
}