class Validator(val password: String) : Rules {

    fun validate(): MutableMap<String, String> {
        var errors: MutableMap<String, String> = mutableMapOf()

        if (this.lengthRule(password)) errors["lenght"] = "must be 8-20 symbols"
        if (this.numberRule(password)) errors["number"] = "must be at least 1 number"
        if (this.lowerCaseRule(password) || this.upperCaseRule(password)) errors["case"] = "must be at lowercase and upppercase latin symbols"
        if (this.specialSymbolsRule(password)) errors["symbol"] = "must be at least 1 special symbol"
        if (this.commonRule(password)) errors["common"] = "your password is one from the commons, consider rewriting"
        if (this.entropyRule(password)) errors["entropy"] = "there's low entropy in your password, consider rewriting"

        return errors
    }
}
