// Nome: Eduardo Barbosa de Oliveira        Data: 04/11/2019
package main

fun main(args: Array<String>){
    val array = arrayListOf<Double>()
    for(i in 0 until 50){
        array.add(i.toDouble())
    }
    val result_latch = StencilLatch(array, 3, 8).process()
    val result_barrier = StencilCyclicBarrier(array, 3, 8).process()
    val result_phaser = StencilPhaser(array, 3, 8).process()
    println(result_latch)
    println(result_barrier)
    println(result_phaser)
}