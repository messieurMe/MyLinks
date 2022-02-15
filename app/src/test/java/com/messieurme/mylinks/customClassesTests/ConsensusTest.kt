package com.messieurme.mylinks.customClassesTests

import com.messieurme.mylinks.custom.Consensus
import org.junit.Before
import org.junit.Test

class ConsensusTest {

    private val consensus: Consensus = Consensus(0)

    @Before
    fun clearConsensus() {
        consensus.changeSize(Math.random().times(10).toInt() + 1)
    }

    @Test
    fun `all true`() {
        for (i in consensus.checker.indices) {
            consensus.ok(i)
        }
        assert(consensus.consensus.value)
    }

    @Test
    fun `one changed value`() {
        for (i in consensus.checker.indices) {
            consensus.ok(i)
        }
        consensus.notOk(consensus.checker.size / 2)
        assert(!consensus.consensus.value)
    }

    @Test
    fun `random test`() {
        val checker = HashMap<Int, Boolean>()
        for (i in consensus.checker.indices) {
            checker[i] = false
        }

        val iterations = Math.random().times(50).toInt() * 100 + 1
        for (i in 0 until iterations) {
            val index = Math.random().times(consensus.checker.size).toInt()

            checker[index] = false
            consensus.notOk(index)
            assert(!consensus.consensus.value)

            checker[index] = true
            consensus.ok(index)
            assert(consensus.consensus.value == checker.all { (_, v) -> v })
        }
    }

}