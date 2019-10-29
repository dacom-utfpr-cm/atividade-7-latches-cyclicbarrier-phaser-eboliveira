package main

fun main(args: Array<String>){
    var array = arrayListOf(1.0, 2.0, 2.0, 1.0)
    println(Stencil(array, 3, 2).process())
}