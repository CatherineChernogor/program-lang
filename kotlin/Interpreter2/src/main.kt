fun main(args: Array<String>) {
    var interpreter: Interpreter
    var parser: Parser
    var text: String


    while (true) {
        print("\n>>")
        text = readLine().toString()
//        println(":$text")

        if ((text == "exit") || (text.isEmpty())) break

        interpreter = Interpreter(false)
        parser = Parser(Lexer(text))
        try {
            interpreter.interpret(parser.parse()!!)
        } catch (e: InterpreterException) {
            System.err.println("\t $e")
        }
    }
    println("Done")
}
