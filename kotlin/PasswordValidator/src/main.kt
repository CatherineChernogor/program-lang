class ValidatorException(message: String) : Exception(message)

fun main() {
    println("\nEnter passwor, password must follows those rule:")
    println("\t- consist from 8-40 symbols")
    println("\t- at least one number")
    println("\t- lowercase and upppercase latin symbols")
    println("\t- special symbols, such as .,-+/!?*#$;:()")
    print(">> ")
    val password = readLine().toString()

    val validator = Validator(password)

    val errors = validator.validate()
    if (errors.isEmpty())
        println("All done")
    else {
        var errorList: String = ""
        for (e in errors){
            errorList += "\t"+e.value+"\n"
        }
        throw ValidatorException("Check those rules: \n " + errorList)
    }
}