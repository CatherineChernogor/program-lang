class InterpreterException(message: String) : Exception(message)

fun Boolean.ifTrue(action: () -> Unit) {
    if (this) {
        action()
    }
}

class Interpreter(val verbose: Boolean = false) : NodeVisitor {

    private var variableTable: VariableTable = VariableTable()

    private fun print(s: String) = verbose.ifTrue { println(s) }


    private fun visitNumber(node: Node): Double {
        val number = node as Number
        //print(number)

        return number.token.value.toDouble()
    }

    private fun visitBinOp(node: Node): Double {
        val operator = node as BinOp
        //print(operator)

        when (operator.op.type) {
            TokenType.PLUS -> return visit(operator.left) as Double + visit(operator.right) as Double
            TokenType.MINUS -> return visit(operator.left) as Double - visit(operator.right) as Double
            TokenType.MUL -> return visit(operator.left) as Double * visit(operator.right) as Double
            TokenType.DIV -> return visit(operator.left) as Double / visit(operator.right) as Double

        }
        throw  InterpreterException("invalid BinOp $operator")
    }

    private fun visitUnaryOp(node: Node): Double {
        val operator = node as UnaryOp
        // print(operator)

        when (operator.op.type) {
            TokenType.PLUS -> return +(visit(operator.expr) as Double)
            TokenType.MINUS -> return -(visit(operator.expr) as Double)
        }
        throw  InterpreterException("invalid UnaryOp $operator")
    }

    private fun visitVariable(node: Node): Any {
        val variable = node as Variable
        return variableTable[variable.token.value]
            ?: throw InterpreterException("Unknown variable ${variable.token.value}")
    }

    private fun visitWrapper(node: Wrapper): Any? {
        for (expression in node.expressions)
            visit(expression)
        return null
    }

    private fun visitAssign(node: Node): Any? {

        val assigner: Assigner = node as Assigner
        val value = visit(assigner.node)
        variableTable[assigner.variable.token.value] = value as Double
        return null
    }

    private fun variableTable(): Any? {
        print(variableTable)
        return null
    }

    override fun visit(node: Node): Any? {
        when (node) {
            is Number -> return visitNumber(node)
            is BinOp -> return visitBinOp(node)
            is UnaryOp -> return visitUnaryOp(node)
            is Variable -> return visitVariable(node)
            is Wrapper -> return visitWrapper(node)
            is Assigner -> return visitAssign(node)
            is Empty -> return variableTable()

        }
        throw InterpreterException("invalid node")
    }

    fun interpret(tree: Node) {
        visit(tree)
    }
}

fun main(args: Array<String>) {
    val parser = Parser(
        Lexer(
            "BEGIN\n" +
//                    "a:=5;" +
//                    "b : = a - 2 ;" +
//                    "x := 2 + 3 * (2 / 4);" +
                    "y:=4-1*2;" +
//                    "y1:=  2 / 2 * 3 ;" +
//                    "y2:=  2 - 2 * 3 + 4 / 2;" +
                    "END."
        )
    )
    val tree = parser.parse()
    println(tree)
    val interpreter = Interpreter(false)
    interpreter.interpret(tree)
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// mistake when * and / are together (that's note for me)
// mistake when + and - are together (that's note for me)
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
