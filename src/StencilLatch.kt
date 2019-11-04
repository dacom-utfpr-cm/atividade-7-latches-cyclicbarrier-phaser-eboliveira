package main

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Semaphore
import kotlin.math.floor

class StencilLatch(val array: ArrayList<Double>, val nIterations: Int, val nThreads: Int) {
    val wlock = Semaphore(1)

    fun process(): ArrayList<Double> {
        if(nThreads > array.size || nThreads == 1){
            throw Exception("Invalid number of threads")
        }
        val len = array.size
        val numThreads = nThreads.toDouble()
        var newArray = Array<Double>(len) { 0.0 }
        var pasteArray = array
        newArray[0] = pasteArray[0]
        val thread_len_array = floor((len - 2) / numThreads) //primeiro e última posição serão tratadas diferentes
        for(iteration in 0 until nIterations){
            val latch = CountDownLatch(nThreads)
            for(i in 0 until nThreads){
                var initPosition:Int = (thread_len_array*i).toInt()

                var endPosition:Int
                if(i == nThreads-1){
                    endPosition = len
                }else{
                    endPosition = (thread_len_array*(i+1)).toInt() + 2
                }
                Thread {
                    val resultStencil = stencil(pasteArray.subList(initPosition, endPosition), latch)
                    wlock.acquire()
                    var i = initPosition
                    for(result in resultStencil){
                        newArray[i+1] = result
                        i += 1
                    }
                    wlock.release()
                }.start()
            }
            wlock.acquire()
            newArray[len-1] = pasteArray[len-1]
            wlock.release()
            latch.await()
            pasteArray = ArrayList<Double>(newArray.asList())
        }
        val newArrayList = ArrayList<Double>(newArray.asList())
        return(newArrayList)
    }

    fun stencil(array: MutableList<Double>, latch: CountDownLatch): ArrayList<Double>{
        val len = array.size
        var pasteArray = array
        var newArray = ArrayList<Double>()
        newArray.clear()
        for(i in 1 until len-1){ //não processa as bordas, serão processadas por outra thread
            val avg = (pasteArray[i-1] + pasteArray[i+1])/2
            newArray.add(avg)
        }
        latch.countDown()
        return newArray
    }
}