import java.io.File
import kotlin.math.log2

interface Rules {

    fun lengthRule(password: String): Boolean {
        if (password.length < 8 || password.length > 20) return true

        return false
    }

    fun numberRule(password: String): Boolean {
        for (p: Char in password) {
            if (p in '0'..'9') return false
        }
        return true
    }

    fun lowerCaseRule(password: String): Boolean {
        for (p: Char in password) {
            if (p in 'a'..'z') return false
        }
        return true
    }

    fun upperCaseRule(password: String): Boolean {
        for (p: Char in password) {
            if (p in 'A'..'Z') return false
        }
        return true
    }

    fun specialSymbolsRule(password: String): Boolean {
        for (p: Char in password) {
            if (p in '!'..'/' || p in ':'..'<' || p in '>'..'@' || p in '['..'`' || p in '{'..'~') return false
        }
        return true
    }

    fun commonRule(password: String): Boolean {
        val file = File("pswd-dict.txt")
        val lines = file.readLines()

        for (line in lines) {
            if (password == line) return true
        }
        return false
    }

    fun entropyRule(password: String): Boolean {
        val dict = HashMap<Char, Float>()

        for (p: Char in password) {
            if (dict.containsKey(p)) {
                dict.put(p, dict.getValue(p) + 1f)

            } else {
                dict.put(p, 1f)
            }
        }

        var H = 0f
        val N = dict.size

        for (d in dict) {
            H += (d.value / N) * log2(d.value / N)
        }

        H *= -1
        if (H > 3) return false
        return true
    }
}

