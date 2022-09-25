package main

class StatesDesigner {
    private val states = mutableSetOf<Set<Int>>()

    private fun addState(state: Set<Int>) {
        states.add(state)
    }

    fun haveState(state: Set<Int>): Boolean = states.contains(state)

    fun getIndexAndMaybeAdd(state: Set<Int>): Int {
        if (!haveState(state)) {
            addState(state)
        }
        return states.indexOf(state)
    }

    fun getStatesAmount(): Int = states.size

    fun findElements(setOfElements: Set<Int>) = states.filter { it.intersect(setOfElements).isNotEmpty() }.map { states.indexOf(it) }.toSet()
}

fun convertNFAtoDFA(NFA: NFA_DFA): NFA_DFA {
    val newStates = StatesDesigner()
    val stackOfSets = mutableSetOf(NFA.startStates)
    val newMovingRules = mutableSetOf<MoveRule>()
    while (stackOfSets.isNotEmpty()) {
        val currentState = stackOfSets.last()
        stackOfSets.remove(currentState)
        val currentStateIndex = newStates.getIndexAndMaybeAdd(currentState)
        for (symbol in 0 until NFA.alphabetSize) {
            val newState = currentState.flatMap { oldCounterState ->
                NFA.movingRules.filter {it.oldState == oldCounterState && it.symbol == symbol}.map {it.newState}
            }.toSet()
            if (newState.isNotEmpty()) {
                if (!newStates.haveState(newState)) stackOfSets.add(newState)
                val newStateIndex = newStates.getIndexAndMaybeAdd(newState)
                newMovingRules.add(MoveRule(currentStateIndex, symbol, newStateIndex))
            }
        }
    }

    var finalStates = setOf<Int>()
    if (newStates.haveState(NFA.finalStates)) {
        finalStates = setOf(newStates.getIndexAndMaybeAdd(NFA.finalStates))
    } else {
        finalStates = newStates.findElements(NFA.finalStates)
    }

    return NFA_DFA(
        newStates.getStatesAmount(),
        NFA.alphabetSize,
        setOf(newStates.getIndexAndMaybeAdd(NFA.startStates)),
        finalStates,
        newMovingRules.toSet()
    )
}