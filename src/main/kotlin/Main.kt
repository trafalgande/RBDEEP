import command.CommandResolver
import domain.datagen.dataLocation
import domain.datagen.fromJson

fun main(args: Array<String>) {
    val data = dataLocation().fromJson()
    while (true) {
        print(">\t")
        val input = readLine() ?: break
        if (input == "quit") break
        println(CommandResolver(input).resolve().handle(data))
    }
}