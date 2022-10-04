package main

fun minimizeDFA(dfa: NFA_DFA): NFA_DFA {
    var reachableStates = dfa.movingRules.filter {it.oldState != it.newState}.map { it.newState }.toMutableSet()

    dfa.startStates.forEach {
        if (!reachableStates.contains(it)) {
            reachableStates.add(it)
        }
    }

    reachableStates = reachableStates.sorted().toMutableSet()

    val classes: MutableSet<Set<Int>> = mutableSetOf()

    classes.add(reachableStates.intersect(dfa.finalStates))
    classes.add(reachableStates.filter { !dfa.finalStates.contains(it) }.toSet())

    var oldSize = 0
    var newSize = 2
    var checker = true

    while (oldSize != newSize) {
        for (i in 0 until classes.size) {
            val classR = classes.elementAt(i)

            if (checker) {
                for (symbol in 0 until dfa.alphabetSize) {
                    if (checker) {
                        val filterClassR = classR.flatMap { state ->
                            dfa.movingRules.filter { (it.oldState == state) and (it.symbol == symbol) }.map { it.newState }
                        }.toSet()

                        if (!classes.any { it.intersect(filterClassR).size == filterClassR.size }) {
                            val newClassR = mutableListOf<MutableSet<Int>>()

                            repeat(classes.size+1) {
                                newClassR.add(mutableSetOf())
                            }

                            classR.forEach { state ->
                                val newState = dfa.movingRules.filter {
                                    (it.oldState == state) and (it.symbol == symbol)
                                }.map { it.newState }

                                if (newState.size > 1) throw error("it isn't a dfa")

                                if (newState.size == 1) {
                                    var init = -1
                                    for (to_class in classes) {
                                        if (to_class.contains(newState[0])) {
                                            if (init != -1) throw error("algorithm work's incorrectly")
                                            init = classes.indexOf(to_class)
                                        }
                                    }
                                    newClassR[init].add(state)
                                } else {
                                    newClassR[classes.size].add(state)
                                }
                            }

                            classes.remove(classR)
                            classes.addAll(newClassR.filter { it.isNotEmpty() })

                            checker = false
                        }
                    }
                }
            }
        }
        checker = true
        oldSize = newSize
        newSize = classes.size
    }

    if (dfa.startStates.size != 1) throw error("wrong DFA, more/less than one start position")

    val startState = classes.filter { it.contains(dfa.startStates.first()) }

    if (startState.size != 1) throw error("wrong algorithm, one state not in one (more or less) classes")

    classes.forEach {
        if (dfa.finalStates.intersect(it).isNotEmpty()) {
            if (dfa.finalStates.intersect(it).size != it.size) {
                throw error("something went wrong")
            }
        }
    }

    val newMovingRules = mutableSetOf<MoveRule>()

    classes.forEach {
        val element = it.first()

        for (symbol in 0 until dfa.alphabetSize) {
            val newRule = dfa.movingRules.filter { it.oldState == element && it.symbol == symbol }

            if (newRule.size > 1) throw error("have problems with dfa or algorithm")

            if (newRule.isNotEmpty()) {
                val new = newRule.first().newState
                val state = classes.filter {it.contains(new)}

                if (state.size > 1) throw error("have problems with dfa or algorithm")

                newMovingRules.add(MoveRule(classes.indexOf(it), symbol, classes.indexOf(state.first())))
            }
        }
    }

    val finalStates = classes.filter { dfa.finalStates.intersect(it).isNotEmpty() }.map { classes.indexOf(it) }.toSet()
    val newDFA = NFA_DFA(classes.size, dfa.alphabetSize,  setOf(classes.indexOf(startState.first())), finalStates, newMovingRules)
    return newDFA
}