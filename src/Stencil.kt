package main

class Stencil(val array: ArrayList<Double>, val nIterations: Int, val nThreads: Int) {
    fun process(): ArrayList<Double>{
        val len = array.size
        var pasteArray = array
        var newArray = ArrayList<Double>(len)
        for(i in 0 until nIterations){
            newArray.clear()
            for(j in 0 until len){
                if(j == 0 || j == (len-1)){
                    newArray.add(pasteArray[j])
                }else{
                    val avg = (pasteArray[j-1] + pasteArray[j+1])/2
                    newArray.add(avg)
                }
            }
            pasteArray = newArray.clone() as ArrayList<Double>
        }
        return(newArray)
    }
}