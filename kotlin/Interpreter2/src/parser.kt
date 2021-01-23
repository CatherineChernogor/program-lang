import java.util.*
import kotlin.collections.ArrayList

class ParserException(message: String) : Exception(message)


class Parser(private val lexer: Lexer) {

    private var currentToken: Token = lexer.nextToken()
    private var prevToken: Token = lexer.getToken()
    private lateinit var mainNode: Node
    private var prevWrapper: Wrapper = Wrapper()
    private var wrapperStack: Stack<Wrapper> = Stack()
    private lateinit var currentVar: Variable
//    private var varList: ArrayList<Variable> = arrayListOf()

    private fun checkTokenType(type: TokenType) {
        println(" type: $type $currentToken")
        if (currentToken.type == type) {
            prevToken = currentToken
            currentToken = lexer.nextToken()
        } else {
            throw ParserException("invalid token order")
        }
    }

    private fun factor(): Node {
        var token = currentToken
        if (TokenType.EOL == token.type) {
            checkTokenType(TokenType.EOL)
            token = currentToken
        }

        when (token.type) {

            TokenType.PLUS -> {
                checkTokenType(TokenType.PLUS)
                return UnaryOp(token, factor())
            }
            TokenType.MINUS -> {
                checkTokenType(TokenType.MINUS)
                return UnaryOp(token, factor())
            }
            TokenType.NUMBER -> {
                checkTokenType(TokenType.NUMBER)
                return Number(token)
            }
            TokenType.LPAREN -> {
                checkTokenType(TokenType.LPAREN)
                val result = expr()

                checkTokenType(TokenType.RPAREN)


                return result
            }

            TokenType.BEGIN -> {
                checkTokenType(TokenType.BEGIN)
                val result = expr()

                checkTokenType(TokenType.END)
                if (currentToken.type == TokenType.EOL)
                    checkTokenType(TokenType.EOL)

                return result
            }
            TokenType.END -> return End(token)
            TokenType.VAR -> {
                checkTokenType(TokenType.VAR)
                currentVar = Variable(token)
//                print("c_var")
                return currentVar
            }
            TokenType.ASSIGN -> {
                checkTokenType(TokenType.ASSIGN)
                var node = expr()
                return Assigner(currentVar, node)
            }
            TokenType.EXIT -> return Exit()
            TokenType.EOL ->{
                if (token.value==""){
                    throw ParserException("Syntax error, \".\" expected but nothing found")

                }
            }
        }
        throw ParserException("invalid factor $currentToken $token")
    }

    private fun term(): Node {
        var result = factor()
        val ops = arrayListOf(TokenType.DIV, TokenType.MUL)
        while (ops.contains(currentToken.type)) {
            val token = currentToken

            when (token.type) {
                TokenType.DIV -> checkTokenType(TokenType.DIV)
                TokenType.MUL -> checkTokenType(TokenType.MUL)
            }
            return BinOp(result, token, factor())
        }

        return result
    }

    fun expr(): Node {

        if (prevToken.type == TokenType.BEGIN && currentToken.type != TokenType.END) {
            formStatementList()
        }


        val ops = arrayListOf(TokenType.PLUS, TokenType.MINUS)
        var result = term()
        while (ops.contains(currentToken.type)) {
            val token = currentToken

            when (token.type) {
                TokenType.PLUS -> checkTokenType(TokenType.PLUS)
                TokenType.MINUS -> checkTokenType(TokenType.MINUS)
            }
            return BinOp(result, token, term())
        }

        return result
    }

    fun formStatementList(): Node {
        var wrapper: Wrapper = Wrapper()

        if (wrapper !in wrapperStack) {
            wrapperStack.push(wrapper)
        }
        do {

            var result = assignment()

            if (result is End && wrapperStack.size > 1) {
                var tempWrapper = wrapperStack.pop()
                var parentWrapper = wrapperStack.pop()
                parentWrapper.addExpression(tempWrapper)
                wrapperStack.push(parentWrapper)
            }
            wrapper.addExpression(result)
        } while (currentToken.type === TokenType.EOL /*&& currentToken.type !== TokenType.EXIT*/)

       // println(wrapper)
        mainNode = wrapper
        return wrapper
    }

    fun parse(): Node? {
        val result = formStatementList()

        return result
    }

    private fun assignment(): Node {
        var result = factor()
        val ops = arrayListOf(TokenType.ASSIGN, TokenType.VAR)

        while (ops.contains(currentToken.type)) {
            val token = currentToken

            when (token.type) {
                TokenType.ASSIGN -> {

                    //println(currentVar)
//                    if (!varList.contains(currentVar)) {//think about value of variable, where are they stored?
//                        varList.add(currentVar)
//                    }
                    checkTokenType(TokenType.ASSIGN)

                    result = Assigner(currentVar, expr())
                    return result
                }
                TokenType.VAR -> {
                    checkTokenType(TokenType.VAR)
                    currentVar = Variable(token)
//                    println("p_var")
                }
            }

        }
        return expr() //return END
    }

}


fun main(args: Array<String>) {
    val parser = Parser(
        Lexer(
            "BEGIN" +
//                    "a:=4+5;" +
//                    "b:=2-3*5;"+
                    "x:= 2 + 3 * (2 / 4);" +
//                    "y:=  10 - 2 + 3 ;" +
//                    "y1:=  2 / 2 * 3 ;" +
//                    "y2:=  2 - 2 * 3 + 4 / 2;" +
                    "END."
        )

    )
    print(parser.parse())


}