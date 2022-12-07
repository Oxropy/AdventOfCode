package year2022

import AoCApp

object Day07 : AoCApp() {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = inputLines
        printPart(1, part1(input))
        printPart(2, part2(input))
    }

    private fun part1(input: List<String>): String {
        return calculateSizeWhenDirSizeMost100000(input).toString()
    }

    private fun part2(input: List<String>): String {
        TODO("Not yet implemented")
    }

    private fun calculateSizeWhenDirSizeMost100000(directory: Directory): Int {
        var nodesSize = 0
        for (node in directory.directories) {
            nodesSize += calculateSizeWhenDirSizeMost100000(node)
        }

        val size = directory.getSize()
        val overallSize = nodesSize + size
        if (overallSize <= 100_000) {
            return overallSize
        }

        return nodesSize
    }

    private fun processInput(input: List<String>): Directory {
        var currentDirectory = Directory("/", null)
        input.forEach { line ->
            currentDirectory = if (line.startsWith("$")) {
                parseCommand(line.drop(2), currentDirectory)
            } else {
                parseOutput(line, currentDirectory)
            }
        }

        return currentDirectory.getRoot()
    }

    private fun parseCommand(command: String, currentDirectory: Directory): Directory {
        val split = command.split(" ")
        return when (split[0]) {
            "cd" -> changeDir(split[1].trim(), currentDirectory)
            "ls" -> currentDirectory
            else -> unreachable()
        }
    }

    private fun changeDir(dir: String, currentDirectory: Directory): Directory {
        if (dir == "/") {
            return currentDirectory.getRoot()
        }

        if (dir == "..") {
            return currentDirectory.getParent()!!
        }

        return currentDirectory.changeTo(dir)
    }

    private fun parseOutput(output: String, currentDirectory: Directory): Directory {
        val split = output.split(" ")
        if (split[0] == "dir") {
            currentDirectory.addNode(split[1].trim())
        } else {
            val size = split[0].toInt()
            currentDirectory.addFile(split[1].trim(), size)
        }

        return currentDirectory
    }

    class Directory(private val name: String, private val parent: Directory?) {
        val directories: MutableList<Directory> = mutableListOf()
        private val files: MutableList<File> = mutableListOf()

        fun addNode(name: String) {
            directories.add(Directory(name, this))
        }

        fun addFile(name: String, size: Int) {
            files.add(File(name, size))
        }

        fun changeTo(dir: String): Directory = directories.first { it.name == dir }
        fun getFilesSize(): Int = files.sumOf { it.size }
        fun getNodesSize(): Int = directories.sumOf { it.getNodesSize() + it.getFilesSize() }
        fun getSize(): Int = getFilesSize() + getNodesSize()
        fun getParent(): Directory? = parent
        fun getRoot(): Directory {
            if (parent != null) {
                return parent.getRoot()
            }

            return this
        }
    }

    data class File(val name: String, val size: Int)
}
