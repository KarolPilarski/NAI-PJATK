import java.util.DoubleSummaryStatistics
import java.util.LinkedList

var k:Int?=null
var points:LinkedList<Point>?=null
var centroids:ArrayList<DoubleArray>?=null
var dimensionsAmount:Int?=null
var E:Double?=null

fun main(args: Array<String>){

    k=args[1].toInt()

    points=LinkedList<Point>()


    do {
        val inputStream = {}.javaClass.getResourceAsStream(args[0])
        inputStream.bufferedReader().forEachLine {
            var pointsDouble=ArrayList<Double>()
            for(i in it.split(";").toList()){
                pointsDouble.add(i.toDouble())
            }
            points!!.add(Point(pointsDouble))
            if(dimensionsAmount==null) dimensionsAmount=pointsDouble.size
        }
    }while(allTypesIncluded())


    centroids=ArrayList<DoubleArray>()
    countCentroids()


    while(countE()){
        pointToClosestCentroid()
        countCentroids()
    }

    println("\u001B[31m \nE nie uleglo zmianie")
    println("\u001B[31mKoniec")
}

fun allTypesIncluded():Boolean {
    var countArray:IntArray=IntArray(k!!)
    for(point in points!!){
        countArray[point.type!!]++
    }
    for(i in countArray) if(i==0) return true
    return false
}

fun countCentroids(){
    val sumArray=ArrayList<DoubleArray>()
    val countArray=DoubleArray(k!!)

    for(i in 0..k!!-1){
        sumArray.add(i, DoubleArray(k!!))
    }

    for(point in points!!){
        countArray[point.type!!]++

        for(i in 0..dimensionsAmount!!-1){
            (sumArray[point.type!!])[i]+=point.coordinates!![i]
        }
    }

    for(list in 0..k!!-1){
        for(array in 0..dimensionsAmount!!-1){
            (sumArray.get(list)).set(array,((sumArray.get(list)).get(array)/countArray.get(list)))
        }
    }
    centroids=sumArray
    println("\u001B[31mObliczone centroidy:")
    var count:Int=0
    for(i in centroids!!){
        print("\u001B[32m \tGrupa "+(count++)+":")
        for (j in i){
            print("\u001B[36m \t"+j)
        }
        println()
    }
}

fun pointToClosestCentroid(){
    //println("\u001B[31mPrzypisywanie punktow do grup:")
    var count:Int=1
    for(point in points!!){
        //rintln("\u001B[36m"+"\tOdleglosci dla punktu "+(count++))

        var closestCentroidId:Int=-1
        var closestCentroidDistance:Double=Double.MAX_VALUE

        for(centroidId in 0..k!!-1){
            var distanceToCentroid=0.0

            for(i in 0..dimensionsAmount!!-1){
                distanceToCentroid+=Math.pow((point.coordinates!!.get(i))-centroids!!.get(centroidId).get(i),2.0)
            }
            //distanceToCentroid=Math.pow(distanceToCentroid,0.5)

            if(closestCentroidDistance>distanceToCentroid){
                closestCentroidId=centroidId
                closestCentroidDistance=distanceToCentroid
            }

            //println("\u001B[32m"+"\t\t Od centroidu klasy "+centroidId+": "+distanceToCentroid)
        }
        point.type=closestCentroidId
        //println("\u001B[34m"+"\t\t Przypisano punkt do klasy "+closestCentroidId)
    }
}

fun countE():Boolean{
    var result:Double=0.0
    for(point in points!!){
        for(i in 0..dimensionsAmount!!-1) {
            result += Math.pow((point.coordinates!!.get(i)) - centroids!!.get(point.type!!).get(i), 2.0)
        }
    }
    println("\u001B[31mPoliczono E:"+result)
    if(E!=null){
        if(Math.abs(E!!-result)<0.000000001) return false
    }
    E=result
    return true
}


class Point(coordinatesInit:ArrayList<Double>){

    var coordinates:ArrayList<Double>?=null
        get(){
            return field
        }
        set(value){
            field=if (true) value else throw IllegalAccessError()
        }

    var type:Int?=null
        get(){
            return field
        }
        set(value){
            field=if (true) value else throw IllegalAccessError()
        }

    init{
        type=(Math.random()* k!!).toInt()
        coordinates=coordinatesInit
    }
}