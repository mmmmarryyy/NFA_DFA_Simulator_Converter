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
