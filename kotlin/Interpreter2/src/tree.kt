import java.util.ArrayList

abstract class Node

interface NodeVisitor {
    fun visit(node: Node): Double
}

//***********************ENTITIES***************************

class Number(val token: Token) : Node() {
    override fun toString(): String {
        return "Number ($token)"
    }
}

class Variable(var token: Token) : Node() {
    override fun toString(): String {
        return "Variable ($token)"
    }
}

class End(var token: Token) : Node() {
    override fun toString(): String {
        return "End ($token)"
    }
}

class Exit() : Node() {
    override fun toString(): String {
        return "Exit ()"
    }
}

//***********************OPERATORS***************************

class Assigner(var variable: Variable, var node: Node) : Node() {
    override fun toString(): String {
        return "Assigner ($variable, $node)"
    }
}

class UnaryOp(val op: Token, val expr: Node) : Node() {
    override fun toString(): String {
        return "UnaryOp${op.value} ($expr)"
    }
}

class BinOp(val left: Node, val op: Token, val right: Node) : Node() {
    override fun toString(): String {
        return "BinOp${op.value} ($left, $right)"
    }
}

class Wrapper : Node() { // STATEMENT_LIST

    var expressions: ArrayList<Node> = arrayListOf()
    var currentExpressionId = 0

    override fun toString(): String {
        return "Wrapper ($expressions)"
    }

    fun addExpression(node: Node) {
        expressions.add(node)
    }

    fun getNext(): Node? {
        currentExpressionId += 1

        if (currentExpressionId != expressions.size + 1)
            return expressions[currentExpressionId - 1]

        return null
    }
}

//     STORAGE
class VariableTable() : HashMap<Variable, Double>() {
    override fun toString(): String {
        var result: String = "VariableTable:\n"
        if (this.size > 0)
            for (e in this) {
                result += "\t[${e.key}] = ${e.value}\n"
            }
        else result += "EMPTY"
        return result
    }
}
