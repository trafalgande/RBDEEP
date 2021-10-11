import command.CommandResolver
import domain.utility.dataLocation
import domain.utility.fromJson

fun main(args: Array<String>) {
    val data = dataLocation().fromJson()
    while (true) {
        print(">\t")
        val input = readLine() ?: break
        if (input == "quit") break
        println(CommandResolver(input).resolve().handle(data))
    }
}