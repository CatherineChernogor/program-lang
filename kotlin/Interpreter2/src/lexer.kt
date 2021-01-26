class Lexer(val text: String) {
    private var pos: Int = 0
    private var currentChar: Char? = null
    private var buffer: String = ""
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
            if (currentChar!!.isLetter()) {
                var pair = collectChars()
                type = pair.first
                value = pair.second

                type?.let {
                    forward()
                    return Token(it, value)
                }
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
                '.' -> {
                    type = TokenType.DOT
                    value = "."
                }
                '=' -> {
//                    var nextChar = '0'
//                    do {
//                        nextChar = text[pos + 1]
//                        //forward()
//                    } while (nextChar != '=')
                    type = TokenType.ASSIGN
                    value = ":="
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

    private fun collectChars(): Pair<TokenType?, String> {

        buffer = ""

        while (currentChar!!.isLetter()) {
            buffer += currentChar
            forward()
        }


        if (currentChar == '.' && buffer == "END") backward()

        return when (buffer) {
            "BEGIN" -> {
                Pair(TokenType.BEGIN, "BEGIN")
            }
            "END" -> {
                Pair(TokenType.END, "END")
            }
            else -> Pair(TokenType.ID, buffer)
        }
    }

    private fun forward() {
        pos += 1
        if (pos > text.length - 1) {
            currentChar = null
        } else {
            currentChar = text[pos]
        }
    }

    private fun backward() {
        pos -= 1
        currentChar = text[pos]
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
        "BEGIN\n" +
//                    "BEGIN\n " +
//                    "a : = 1 ;" +
//                    "BEGIN\n" +
//                    "e : = 1  ; " +
//                    "END;  " +
//                    "END;  " +
//                "dd : = 1;  " +
                "x:=2+3*(2/4);" +
//                "BEGIN\n  " +
//                "c : = 1 ;  " +
//                "END; " +
                "END."
    )
    for (i in 0..16)
        println(lexer.nextToken())

}