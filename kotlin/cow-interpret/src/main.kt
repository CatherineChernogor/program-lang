import java.io.File
import java.io.FileNotFoundException
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

const val ZERO = (0).toChar()

fun readCOW(path: String): String {
    val file = File(path)
    val lines = file.readLines()
    return lines.joinToString(" ")
}

fun getLoopBlocks(source: ArrayList<String>): HashMap<Int, Int> {
    val blocks = HashMap<Int, Int>()
    val stack = mutableListOf<Int>()
    for ((i, char) in source.withIndex()) {
        if (char == "MOO") {
            stack.add(i)
        }
        if (char == "moo") {
            blocks[i] = stack[stack.lastIndex]
            blocks[stack.removeAt(stack.lastIndex)] = i
        }
    }
    return blocks
}

fun eval(source: String) {

    val src = ArrayList<String>(source.split(' '))
    val buffer = Array(2000) { _ -> ZERO }
    var ptr = 0
    var i = 0

    val blocks = getLoopBlocks(src)
    while (i < src.size) {
        when (src[i]) {

            "moO" -> ptr++                                  //следующая ячейка
            "mOo" -> ptr--                                  //предыдущая ячейка
            "MoO" -> buffer[ptr] = buffer[ptr] + 1          //значение текущей ячейки увеличить на 1
            "MOo" -> buffer[ptr] = buffer[ptr] - 1          //значение текущей ячейки уменьшить на 1
            "OOM" -> print(buffer[ptr].toInt())             //вывод значения текущей ячейки
            "Moo" -> {                                      //если значение в ячейке равно 0, то ввести с клавиатуры, если значение не 0, то вывести на экран
                if (buffer[ptr] == ZERO) {
                    print(">>> ")
                    buffer[ptr] = readLine()?.toCharArray()?.get(0)!!
                } else {
                    print(buffer[ptr])
                }
            }
            "MOO" -> if (buffer[ptr] == ZERO) i = blocks[i]!!           //начало цикла
            "moo" -> if (buffer[ptr] != ZERO) i = blocks[i]!!           //конец цикла
            else -> {
                if (i + 1 < src.size && src[i + 1] == ":") println()
                print(src[i] + " ")
            }
        }
        i++
    }
}

fun main(args: Array<String>) {
    //{'MOO', 'MOo', 'MoO', 'Moo',        'mOo', 'moO', 'moo'} hello.cow
    //{'MOO', 'MOo', 'MoO', 'Moo', 'OOM', 'mOo', 'moO', 'moo'} fib.cow
    try {
        val source = readCOW("fib.cow").replace("\\s+".toRegex(), " ").trim()
        eval(source)
        println("\n\nDone")
    } catch (expression: FileNotFoundException) {
        println("Filename is incorrect")
    }
}