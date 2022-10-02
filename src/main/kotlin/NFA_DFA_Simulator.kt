package main

class MoveRule(val oldState: Int, val symbol: Int, val newState: Int)

class NFA_DFA(
    val numberOfStates: Int,
    val alphabetSize: Int,
    val startStates: Set<Int>,
    val finalStates: Set<Int>,
    val movingRules: Set<MoveRule>
) {
    private var currentStates: Set<Int>

    init {
        currentStates = startStates
    }

    private fun move(char: Int) {
        currentStates = currentStates.flatMap { currentState ->
            movingRules.filter { it.oldState == currentState && it.symbol == char }.map { it.newState }
        }.toSet()
    }

    fun accept(string: String): Boolean {
        for (char in string) {
            move(char.digitToInt())
        }
        return currentStates.intersect(finalStates).isNotEmpty()
    }
}