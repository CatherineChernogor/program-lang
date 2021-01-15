import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class RuleTest {
    var v = Validator("")
    // true - есть ошибка, false - нет ошибок

    @Test
    internal fun checkLenght1() {
        Assertions.assertEquals(false, v.lengthRule("password"))
    }

    @Test
    internal fun checkLenght2() {
        Assertions.assertEquals(true, v.lengthRule("pass"))
    }


    @Test
    internal fun checkNumber1() {
        Assertions.assertEquals(true, v.numberRule("password"))
    }

    @Test
    internal fun checkNumber2() {
        Assertions.assertEquals(false, v.numberRule("password23"))
    }

    @Test
    internal fun checkNumber3() {
        Assertions.assertEquals(false, v.numberRule("pas3sw4ord23"))
    }


    @Test
    internal fun checkCase1() {
        Assertions.assertEquals(true, v.lowerCaseRule("PASSWORD"))
    }

    @Test
    internal fun checkCase2() {
        Assertions.assertEquals(true, v.upperCaseRule("password"))
    }

    @Test
    internal fun checkCase3() {
        Assertions.assertEquals(false, v.lowerCaseRule("paSSword"))
    }

    @Test
    internal fun checkCase4() {
        Assertions.assertEquals(false, v.upperCaseRule("paSSword"))
    }


    @Test
    internal fun checkSymbol1() {
        Assertions.assertEquals(true, v.specialSymbolsRule("password"))
    }

    @Test
    internal fun checkSymbol2() {
        Assertions.assertEquals(false, v.specialSymbolsRule("paSSw#or*d"))
    }


    @Test
    internal fun checkCommon1() {
        Assertions.assertEquals(true, v.specialSymbolsRule("password"))
    }

    @Test
    internal fun checkCommon2() {
        Assertions.assertEquals(false, v.specialSymbolsRule("paSSw#or*d"))
    }

    @Test
    internal fun checkEntropy1() {
        Assertions.assertEquals(true, v.entropyRule("passpasspass"))
    }

    @Test
    internal fun checkEntropy2() {
        Assertions.assertEquals(false, v.entropyRule("paSSw#or*d"))
    }
}