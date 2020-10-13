import java.lang.StringBuilder

fun isNumber(s: String): Boolean {
    if (s.isEmpty()) return false
    for (symbol in s) {
        if (!symbol.isDigit()) {
            return false
        }
    }
    return true
}

fun isOp(str: String): Boolean {
    val ops = arrayOf("+", "-", "*", "/")
    for (op in ops) {
        if (op.equals(str)) return true
    }
    return false
}

fun pop(s: MutableList<String>): String {

    var result = s.get(s.lastIndex)
    s.removeAt(s.lastIndex)
    return result
}

fun sum(first: String, second: String, sign: String): String {

    if (isOp(sign)) return "(" + first + sign + second + ")"
    else return "(" + sign + first + second + ")"
}

fun main(args: Array<String>) {

    print("Please input expression: ")
    var answer: String? = readLine()

    if (!answer.isNullOrEmpty()) {

        while (answer?.get(answer.length - 1)?.equals(' ')!!) {//remove extra spaces
            answer = answer?.substring(0, answer.lastIndex)
        }

        var parts = ArrayList<String>(answer?.split(' '))
        val stack = mutableListOf<String>()

        for (part in parts) {

            if (isOp(part)) {
                stack.add(part)
            } else if (isNumber(part)) {//if cur is number

                if (isOp(stack.get(stack.lastIndex))) {//and previous is op
                    stack.add(part)
                } else {//and previous is also number
                    var str = sum(pop(stack), part, pop(stack))
                    stack.add(str)
                }
            } else {
                println("Wrong expression. There're not recognized symbol ")
                return
            }
        }
        while (stack.size > 2) {
            var s = pop(stack)
            var f = pop(stack)
            var str = sum(f, s, pop(stack))
            stack.add(str)
        }


        if (stack.size > 1) println("Not enough operators or numbers to unpack")
        else if (stack.isEmpty()) println("Stack is empty")
        else println("Result: ${stack.removeAt(stack.lastIndex)}")

    } else {
        println("Expression is empty")
    }
}

