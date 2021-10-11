package command

class CommandResolver(var cmd: String) {
    fun resolve(): CommandHandler {
        return when (cmd) {
            "list" -> ListCommandHandler()
            "search" -> SearchCommandHandler()
            "add" -> AddCommandHandler()
            "del" -> DelCommandHandler()
            "quit" -> QuitCommandHandler()
            "help" -> HelpCommandHandler()
            else -> HelpCommandHandler()
        }
    }

}