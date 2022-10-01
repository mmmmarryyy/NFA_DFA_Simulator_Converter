package test
import main.*
import kotlin.test.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class TestsForTask4 {

    @Test
    fun test1() {
        val machine = NFA_DFA(3, 2, setOf(0), setOf(1), setOf(MoveRule(0,0,1), MoveRule(0,0,2), MoveRule(0,1,2), MoveRule(0,0,1), MoveRule(1,0,2), MoveRule(2,1,2), MoveRule(2, 0, 0)))
        val inputString = "0000"
        assertTrue(machine.accept(inputString))
    }

    @Test
    fun test2() {
        val machine = NFA_DFA(3, 2, setOf(0), setOf(1), setOf(MoveRule(0,0,1), MoveRule(0,0,0), MoveRule(1,0,1)))
        val inputString = "0000"
        assertTrue(machine.accept(inputString))
    }
}

internal class TestsForTask5 {

    @Test
    fun test1() {
        val machine = NFA_DFA(3, 2, setOf(0), setOf(1), setOf(MoveRule(0,0,1), MoveRule(0,0,2), MoveRule(0,1,2), MoveRule(0,0,1), MoveRule(1,0,2), MoveRule(2,1,2), MoveRule(2, 0, 0)))
        val newMachine = convertNFAtoDFA(machine)
        val defaultMachine = NFA_DFA(5, 2, setOf(0), setOf(1, 4), setOf(MoveRule(0,0,1), MoveRule(0,1,2), MoveRule(2, 0, 0), MoveRule(2,1,2), MoveRule(1,0,3), MoveRule(1,1,2),  MoveRule(3, 0, 4), MoveRule(3, 1, 2), MoveRule(4, 0, 4), MoveRule(4, 1, 2)))
        assertTrue(newMachine.numberOfStates == defaultMachine.numberOfStates)
        assertTrue(newMachine.alphabetSize == defaultMachine.alphabetSize)
        assertTrue(newMachine.startStates == defaultMachine.startStates)
        assertTrue(newMachine.finalStates == defaultMachine.finalStates)
        assertTrue(newMachine.movingRules.size == defaultMachine.movingRules.size)
        newMachine.movingRules.forEach {
            assertTrue(defaultMachine.movingRules.any { anotherIt ->
                anotherIt.oldState == it.oldState &&
                        anotherIt.symbol == it.symbol &&
                        anotherIt.newState == it.newState
            })
        }
    }
}