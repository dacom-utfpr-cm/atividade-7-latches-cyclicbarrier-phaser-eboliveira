package main

fun main(args: Array<String>){
    val array = arrayListOf<Double>()
    for(i in 0 until 50){
        array.add(i.toDouble())
    }
    val result = Stencil(array, 3, 2).process()
    println(result)
}