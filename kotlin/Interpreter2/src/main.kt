fun main(args: Array<String>) {
    var interpreter: Interpreter
    var parser: Parser
    var text: String

    println("\n~~~Test mode~~~\n")

    var t: Test = Test()
    t.checkTest(t.test1)
    t.checkTest(t.test2)
    t.checkTest(t.test3)

    println("\n~~~Interactive mode~~~\n")
    while (true) {
        print("\nEnter code in ONE line e.g.:\n\tBEGIN x:= 2 + 3 * (2 + 3); y:= 2 / 2 - (1 + 1); END.\n>>")
        text = readLine().toString()

        if ((text == "exit") ||(text == "EXIT")|| (text.isEmpty())) break

        interpreter = Interpreter(false)
        parser = Parser(Lexer(text))
        try {
            interpreter.interpret(parser.parse())
        } catch (e: InterpreterException) {
            System.err.println("\t $e")
        }
    }
    println("Done")
}
