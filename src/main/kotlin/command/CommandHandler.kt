package command

import domain.MusicCollection
import domain.MusicComposition
import domain.datagen.beautify
import domain.datagen.dataLocation
import domain.datagen.toJson
import java.io.File
import kotlin.system.exitProcess

interface CommandHandler {
    fun handle(mc: MusicCollection): String
}

class ListCommandHandler : CommandHandler {
    override fun handle(mc: MusicCollection): String {
        val str = StringBuilder()
        str.append("All compositions in catalog: \n")
        mc.musicComps.forEach { str.append(it.beautify()).append("\n") }
        return str.toString()
    }
}

class SearchCommandHandler : CommandHandler {
    override fun handle(mc: MusicCollection): String {
        val str = StringBuilder()
        println("Input the part of the name to find composition in the catalog:")
        val term = readLine()!!

        mc.musicComps.filter { smartFilter(it, term) }
            .forEach { str.append(it.beautify()).append("\n") }
        return str.toString()
    }

    private fun smartFilter(item: MusicComposition, term: String): Boolean {
        return item.author.contains(term)
                || item.name.contains(term)
    }
}

class AddCommandHandler : CommandHandler {
    override fun handle(mc: MusicCollection): String {
        println("Input author's name:")
        val author = readLine()!!
        println("Input composition's name:")
        val name = readLine()!!
        val item = MusicComposition(author, name)

        val current = mc.musicComps
        current.add(item)
        mc.musicComps = current
        mc.save()

        return "'${item.beautify()}' successfully added to the collection.\n"
    }
}

class DelCommandHandler : CommandHandler {
    override fun handle(mc: MusicCollection): String {
        println("Input the full name of the composition to remove [Author-Composition]:")
        val fullName = readLine()!!.replace("\\s+", "").split("-")
        val author = fullName[0]
        val name = fullName[1]
        return if (mc.musicComps.find { it.author == author && it.name == name } != null) {
            mc.musicComps.removeIf { it.author == author && it.name == name }
            mc.save()
            "'${MusicComposition(author, name).beautify()}' successfully deleted to the collection.\n"
        } else
            "${MusicComposition(author, name).beautify()} not found.\n"
    }
}

class QuitCommandHandler : CommandHandler {
    override fun handle(mc: MusicCollection): String {
        exitProcess(1)
    }
}

class HelpCommandHandler : CommandHandler {
    override fun handle(mc: MusicCollection): String {
        return helpMessage()
    }

    private fun helpMessage() =
        """
            Available commands:
                [list] - Show compositions in collection
                [search] - Search composition in collection
                [add] - Add composition to collection
                [del] - Delete composition from collection
                [help] - Show this message 
                [quit] - Quit from current session
        """.trimIndent()
}

private fun MusicCollection.save() = File(dataLocation()).writeText(this.toJson())
