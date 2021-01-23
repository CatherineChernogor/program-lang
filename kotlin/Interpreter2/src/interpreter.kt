class InterpreterException(message: String) : Exception(message)

fun Boolean.ifTrue(action: () -> Unit) {
    if (this) {
        action()
    }
}

class Interpreter(val verbose: Boolean = false) : NodeVisitor {

    private var variableTable: VariableTable = VariableTable()

    private fun print(s: String) = verbose.ifTrue { println(s) }

    fun visitWrapper(node: Node) {
        val wrapper: Wrapper = node as Wrapper
        var wrapperNode: Node? = wrapper.getNext()
        while (wrapperNode != null) {

            when (wrapperNode) {
                is Wrapper -> visitWrapper(wrapperNode)
                is Assigner -> visitAssign(wrapperNode)
                is End -> return
                is Exit -> visitExit()
                else -> wrapperNode?.let { visit(it) }
            }
            wrapperNode = wrapper.getNext()
        }
    }

    private fun visitNumber(node: Node): Double {
        val number = node as Number
        //print(number)

        return number.token.value.toDouble()
    }

    private fun visitBinOp(node: Node): Double {
        val operator = node as BinOp
        //print(operator)

        when (operator.op.type) {
            TokenType.PLUS -> return visit(operator.left) + visit(operator.right)
            TokenType.MINUS -> return visit(operator.left) - visit(operator.right)
            TokenType.MUL -> return visit(operator.left) * visit(operator.right)
            TokenType.DIV -> return visit(operator.left) / visit(operator.right)

        }
        throw  InterpreterException("invalid BinOp $operator")
    }

    private fun visitUnaryOp(node: Node): Double {
        val operator = node as UnaryOp
        // print(operator)

        when (operator.op.type) {
            TokenType.PLUS -> return +visit(operator.expr)
            TokenType.MINUS -> return -visit(operator.expr)
        }
        throw  InterpreterException("invalid UnaryOp $operator")
    }

    private fun visitExit() {
        print(variableTable)
    }

    private fun visitAssign(node: Node) {
        if (node::class == Assigner::class) {
            val assigner: Assigner = node as Assigner
            val value = visit(assigner.node)
            variableTable[assigner.variable] = value
        }
    }

    override fun visit(node: Node): Double {
        when (node) {
            is Number -> return visitNumber(node)
            is BinOp -> return visitBinOp(node)
            is UnaryOp -> return visitUnaryOp(node)
            is Variable -> {
                if (!variableTable.containsKey(node.token.value))
                    throw InterpreterException("Unknown variable!")

                return variableTable.get(node.token.value)!!
            }
        }
        throw InterpreterException("invalid node")
    }

    fun interpret(tree: Node) {
        visitWrapper(tree)
    }
}

fun main(args: Array<String>) {
    val parser = Parser(
        Lexer(
            "BEGIN" +
//                    "a:=4+5;" +
//                    "b:=2-3*5;"+
//                    "x:= 2 + 3 * (2 / 4);" +
                    "y:=  2 - 2 + 3 ;" +
//                    "y1:=  2 / 2 * 3 ;" +
//                    "y2:=  2 - 2 * 3 + 4 / 2;" +
                    "END."
        )
    )
    val tree = parser.parse()
    val interpreter = Interpreter(false)
    interpreter.interpret(tree!!)
}

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// mistake when * and / are together (that's note for me)
// mistake when + and - are together (that's note for me)
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
//variables belongs to third case
