import java.io.File
import java.net.ServerSocket
import java.net.SocketTimeoutException

class Server(private val port: Int) {
    private val serverSocket = ServerSocket(port)

    fun start() {
        val interpreter = Interpreter()
        Logger.attach(ConsoleHandler())
        Logger.attach(FileHandler(File("D:\\server.log")))

        while (true) {
            Logger.info("Wait for new connection")
            val clientSocket = serverSocket.accept()
            clientSocket.soTimeout = 30000
            Logger.info("Process new client ${clientSocket.localAddress}")

            val inStream = clientSocket.getInputStream().bufferedReader()
            val outStream = clientSocket.getOutputStream().bufferedWriter()
            while (!clientSocket.isOutputShutdown) {
                try {
                    Logger.info("Wait for message")
                    val data = inStream.readLine()
                    if (data != null) {
                        Logger.info(data)
                        var result = interpreter.interpret(Parser(Lexer(data)).expr())
                        outStream.write("Result = $result\n")
                        outStream.flush()

                    } else {
                        outStream.write("error\n")
                        outStream.flush()

                        Logger.info("Client closed connection")
                        break
                    }
                } catch (e: SocketTimeoutException) {
                    Logger.warning("Disconnect client due timeout")
                    clientSocket.close()
                    break
                }
            }

        }
    }
}


fun main(args: Array<String>) {
    Server(5555).start()
}