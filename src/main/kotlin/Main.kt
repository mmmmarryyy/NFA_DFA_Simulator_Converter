package main
import java.io.*

fun main() {
    /*task4()
    task5()*/
    minimize()
}

fun task4() {
    val machine = readMachineFromFile()

    val inputString = "0000"

    print(machine.accept(inputString))
}

fun task5() {
    val machine = readMachineFromFile()
    val newMachine = convertNFAtoDFA(machine)
    val file = File("outputDFA.txt")

    file.writeText("${newMachine.numberOfStates}\n")

    file.appendText("${newMachine.alphabetSize}\n")

    newMachine.startStates.forEach {
        file.appendText("${it} ")
    }

    file.appendText("\n")

    newMachine.finalStates.forEach {
        file.appendText("${it} ")
    }

    file.appendText("\n")

    newMachine.movingRules.forEach {
        file.appendText("${it.oldState} ${it.symbol} ${it.newState}\n")
    }
}

fun minimize() {
    val machine = readMachineFromFile()
    val newMachine = minimizeDFA(machine)

    val file = File("outputDFA.txt")

    file.writeText("${newMachine.numberOfStates}\n")

    file.appendText("${newMachine.alphabetSize}\n")

    newMachine.startStates.forEach {
        file.appendText("${it} ")
    }

    file.appendText("\n")

    newMachine.finalStates.forEach {
        file.appendText("${it} ")
    }

    file.appendText("\n")

    newMachine.movingRules.forEach {
        file.appendText("${it.oldState} ${it.symbol} ${it.newState}\n")
    }
}

fun readMachineFromFile(): NFA_DFA {
    val file = BufferedReader(FileReader("inputNFADFA.txt"))
    val n = file.readLine()!!.toInt()
    val m = file.readLine()!!.toInt()

    val startStates = file.readLine()!!.split(" ").map { it.toInt() }.toSet()
    val finalStates = file.readLine()!!.split(" ").map { it.toInt() }.toSet()

    val lines = file.readLines()
    val rules = mutableSetOf<MoveRule>()
    for (i in 0 until lines.size) {
        val (oldState, symbol, newState) = lines[i].split(" ").map { it.toInt() }
        rules.add(MoveRule(oldState, symbol, newState))
    }

    return NFA_DFA(n, m, startStates, finalStates, rules)
}