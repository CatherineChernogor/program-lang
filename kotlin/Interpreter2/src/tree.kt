import java.util.ArrayList

abstract class Node

interface NodeVisitor {
    fun visit(node: Node): Any?
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

class Empty : Node() {
    override fun toString(): String {
        return "Empty ()"
    }
}

//***********************OPERATORS***************************

class Assigner(var variable: Variable, var node: Node) : Node() {
    override fun toString(): String {
        return "Assigner (\n\t$variable, \n\t$node\n)"
    }
}

class UnaryOp(val op: Token, val expr: Node) : Node() {
    override fun toString(): String {
        return "UnaryOp${op.value} (\n\t$expr\n)"
    }
}

class BinOp(val left: Node, val op: Token, val right: Node) : Node() {
    override fun toString(): String {
        return "BinOp${op.value} (\n\t$left, \n\t$right\n)"
    }
}

class Wrapper(val expressions: List<Node>) : Node() {
    override fun toString(): String {
        return "Wrapper (\n\t$expressions\n)"
    }
}

//***********************STORAGE***************************

class VariableTable() : HashMap<String, Double>() {
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
