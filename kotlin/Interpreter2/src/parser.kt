import java.util.*

class ParserException(message: String) : Exception(message)


class Parser(private val lexer: Lexer) {

    private var currentToken: Token = lexer.nextToken()
    private lateinit var currentVar: Variable

    private fun checkTokenType(type: TokenType) {
        println(" expected type: $type $currentToken")
        if (currentToken.type == type) {
            currentToken = lexer.nextToken()
        } else {
            throw ParserException("invalid token order")
        }
    }

    private fun factor(): Node {
        var token = currentToken

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
            TokenType.ID -> return variable()
        }
        throw ParserException("invalid factor, $currentToken $token")
    }

    private fun term(): Node {
        var left = factor()
        val ops = arrayListOf(TokenType.DIV, TokenType.MUL)

        while (ops.contains(currentToken.type)) {
            val token = currentToken

            when (token.type) {
                TokenType.DIV -> checkTokenType(TokenType.DIV)
                TokenType.MUL -> checkTokenType(TokenType.MUL)
            }
            return BinOp(left, token, factor())
        }
        return left
    }

    fun expr(): Node {
        var left = term()
        val ops = arrayListOf(TokenType.PLUS, TokenType.MINUS)

        while (ops.contains(currentToken.type)) {
            val token = currentToken

            when (token.type) {
                TokenType.PLUS -> checkTokenType(TokenType.PLUS)
                TokenType.MINUS -> checkTokenType(TokenType.MINUS)
            }
            return BinOp(left, token, term())
        }
        return left
    }

    private fun empty(): Node = Empty()

    private fun variable(): Node {

        currentVar = Variable(currentToken)
        checkTokenType(TokenType.ID)
        return currentVar
    }

    private fun assignment(): Node {
        val left = variable() as Variable
        checkTokenType(TokenType.ASSIGN)
        val right = expr()
        return Assigner(left, right)
    }

    private fun statement(): Node {
        return when (currentToken.type) {
            TokenType.BEGIN -> complexStatement()
            TokenType.ID -> assignment()
            else -> empty()
        }
    }

    private fun statementList(): List<Node> {
        val node = statement()
        val result = mutableListOf(node)
        while (currentToken.type == TokenType.EOL) {
            checkTokenType(TokenType.EOL)
            result += statement()
        }
        if (currentToken.type == TokenType.ID) {
            throw ParserException("Unexpected variable ${currentToken.value}")
        }
        return result
    }

    private fun complexStatement(): Node {
        checkTokenType(TokenType.BEGIN)
        val nodes = statementList()
        checkTokenType(TokenType.END)
        return Wrapper(nodes)
    }

    private fun program(): Node {
        val node = complexStatement()
        checkTokenType(TokenType.DOT)
        return node
    }

    fun parse(): Node {
        return program()
    }
}

fun main(args: Array<String>) {
    val parser = Parser(
        Lexer(
            "BEGIN\n" +
//                    "a : = 4 + 5;" +
//                    "b : = a - 2 * 3 ;" +
                    "x:=2+3-1;" +
//                    "y:=  10 - 2 + 3 ;" +
//                    "y1:=  2 / 2 * 3 ;" +
//                    "y2:=  2 - 2 * 3 + 4 / 2;" +
                    "END ."
        )

    )
    print(parser.parse())


}