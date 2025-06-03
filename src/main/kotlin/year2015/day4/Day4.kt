package me.grian.year2015.day4

import kotlinx.coroutines.*
import me.grian.Day
import me.grian.util.getInputText
import java.security.MessageDigest
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger

class Day4 : Day {
    override val input: String
        get() = getInputText(2015, 4)

    override fun partOne(): String = common(false)

    override fun partTwo(): String = common(true)

    private fun common(p2: Boolean): String {
        val goodNumber = AtomicInteger()
        val found = AtomicBoolean()

        runBlocking {
            withContext(Dispatchers.Default) {
                coroutineScope {
                    val numThreads = Runtime.getRuntime().availableProcessors()

                    repeat(numThreads) {
                        launch {
                            var i = it
                            // some weird quirK of MessageDigest prob but this is required for p1 to work lol so i cant
                            // get md in function, probably faster this way but i doubt it makes much of a diff..
                            val md = MessageDigest.getInstance("MD5")
                            while (!found.get()) {
                                val md5 = md5(md, "$input$i")!!
                                val byte: Byte = 0

                                if (md5[0] == byte && md5[1] == byte && ((p2 && md5[2] == byte) || (!p2 && md5[2].toInt() and 0xFF < 16))) {
                                    found.set(true)
                                    goodNumber.set(i)
                                    break
                                }
                                i += numThreads
                            }
                        }
                    }
                }
            }
        }


        return goodNumber.get().toString()
    }

    private fun md5(md: MessageDigest, input: String): ByteArray? {
        val digest = md.digest(input.toByteArray())
        return digest
    }
}