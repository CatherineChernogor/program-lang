class Lexer(val text: String) {
    private var pos: Int = 0
    private var currentChar: Char? = null
    private var buffer: String = ""
    private var secondaryBuffer: String = ""
    private var token: Token = Token(TokenType.BEGIN, "BEGIN")

    fun getToken(): Token {
        return token
    }

    init {
        currentChar = text[pos]
    }

    fun nextToken(): Token {
        var value: String
        var type: TokenType?

        while (currentChar != null) {
            if (currentChar!!.isWhitespace()) {
                skip()
                continue
            }
            if (currentChar!!.isDigit()) {
                return Token(TokenType.NUMBER, number())
            }

            type = null
            value = "$currentChar"

            when (currentChar) {

                '+' -> type = TokenType.PLUS
                '-' -> type = TokenType.MINUS
                '*' -> type = TokenType.MUL
                '/' -> type = TokenType.DIV
                '(' -> type = TokenType.LPAREN
                ')' -> type = TokenType.RPAREN
                ';' -> type = TokenType.EOL
                '.' -> type = TokenType.EXIT

                else -> {
                    var pair = collectChars(currentChar!!)
                    type = pair.first
                    value = pair.second
                }
            }

            type?.let {
                forward()
                return Token(it, value)
            }

            if (type == null) {
                forward()
                continue
            }

            throw InterpreterException("invalid token")
        }
        return Token(TokenType.EOL, "")
    }

    private fun collectChars(currentChar: Char): Pair<TokenType?, String> {

        if (secondaryBuffer.isNotEmpty()) {
            buffer = secondaryBuffer
            secondaryBuffer = ""
        }

        if (buffer.isNotBlank() && currentChar == ':') {
            secondaryBuffer = ":"
            return Pair(TokenType.VAR, buffer)
        }

        buffer += currentChar
        when (buffer) {
            "BEGIN" -> {
                buffer = ""
                return Pair(TokenType.BEGIN, "BEGIN")
            }
            "END" -> {
                buffer = ""
                return Pair(TokenType.END, "END")
            }
            ":=" -> {
                buffer = ""
                return Pair(TokenType.ASSIGN, ":=")
            }
        }

        return Pair(null, "")
    }

    private fun forward() {
        pos += 1
        if (pos > text.length - 1) {
            currentChar = null
        } else {
            currentChar = text[pos]
        }
    }

    private fun skip() {
        while ((currentChar != null) && currentChar!!.isWhitespace()) {
            forward()
        }
    }

    private fun number(): String {
        var result = arrayListOf<Char>()
        while ((currentChar != null) && (currentChar!!.isDigit() || currentChar == '.')) {
            result.add(currentChar!!)
            forward()
        }
        return result.joinToString("")
    }

}


fun main(args: Array<String>) { // problem with var in the middle of assignment
    val lexer = Lexer(
        "BEGIN\n " +
//                    "BEGIN\n " +
//                    "a : = 1 ;" +
//                    "BEGIN\n" +
//                    "e : = 1  ; " +
//                    "END;  " +
//                    "END;  " +
                "dd : = 1 + 3;  " +
                "BEGIN\n  " +
                "c : = 1 ;  " +
                "END; " +
                "END."
    )

    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
    println(lexer.nextToken())
}