import java.util.*

var data:LinkedList<LinkedList<String>>?=null
var probability:HashMap<String,LinkedList<HashMap<String,Double>>>?=null
var resultCount:HashMap<String,Int>?=null

fun main(){
    loadTrainingSet()
    countProbability()
    flatten()

    for(i in resultCount!!){
        println(i)
    }

    for(i in probability!!){
        println(i)
    }

    testFromFile()

    testFromConsole()
}


fun loadTrainingSet(){
    data =LinkedList<LinkedList<String>>()

    val inputStream = {}.javaClass.getResourceAsStream("trainingset.csv")
    inputStream.bufferedReader().forEachLine {
        val tmp:LinkedList<String> = LinkedList<String>()
        for(i in it.split(",")){
            tmp.add(i)
        }
        data?.add(tmp)
    }
}

fun countProbability() {
    probability = HashMap<String, LinkedList<HashMap<String, Double>>>()
    for (dataLine in data!!) {
        if (!probability!!.containsKey(dataLine.get(dataLine.size - 1))) {
            probability?.set(dataLine.get(dataLine.size - 1), LinkedList<HashMap<String, Double>>())
        }
    }

    resultCount = HashMap()

    for (output in probability!!.keys) {
        for (dataLine in data!!) {
            if (!resultCount!!.containsKey(dataLine.get(dataLine.size - 1))) {
                resultCount?.set(dataLine.get(dataLine.size - 1), 0)
            }
            if(dataLine.get(dataLine.size - 1).equals(output))
            resultCount?.set(dataLine.get(dataLine.size - 1),resultCount!!.get(dataLine.get(dataLine.size - 1))!!.plus(1))
        }


        val list = LinkedList<HashMap<String, Double>>()
        for (i in 0 until data!!.get(0).size - 1) {
            val quantity: HashMap<String, Int> = HashMap<String, Int>()

            for (dataLine in data!!) {
                val value = dataLine.get(i)

                if (!quantity.containsKey(value)) {
                    quantity.set(value, 0)
                }
                if (dataLine.get(dataLine.size - 1).equals(output))
                    quantity.set(value, quantity.get(value)!!.plus(1))
            }
            val probResult: HashMap<String, Double> = HashMap<String, Double>()

            for (value in quantity.keys) {
                probResult.set(value, (quantity.get(value)!!.toDouble() / resultCount?.get(output)!!))
            }
            list.add(probResult)
        }
        probability?.set(output, list)
    }
}

fun flatten() {
    for(group in probability!!.keys){
        for(column in probability!!.get(group)!!){
            for(probabilityValue in column){
                if(probabilityValue.value==0.0){
                    column.set(probabilityValue.key,(1.0/(resultCount!!.get(group)!!.toDouble()+column.size)))
                }
            }
        }
    }
}

fun testFromFile() {
    val inputStream = {}.javaClass.getResourceAsStream("testset.csv")
    inputStream.bufferedReader().forEachLine {
        test(it)
    }
}


fun test(input:String) {
    val testingData:ArrayList<String> = input.split(",").toList() as ArrayList<String>

    var bestGroup=""
    var bestValue=0.0

    println("\n"+testingData)

    for(groupName in probability!!.keys){
        var res:Double=(resultCount!!.get(groupName)!!.toDouble()/data!!.size.toDouble())
        for(i in 0 until testingData.size){

            res=res*( probability!!.get(groupName)!!.get(i)!!.get(testingData.get(i))!!)
        }

        if(res>bestValue){
            bestGroup=groupName
            bestValue=res
        }

        println("Wartosc dla "+groupName+": "+res)
    }
    println("Grupa \""+bestGroup+"\" ma wieksze prawdopodobienstwo")
}


fun testFromConsole() {
    while(true){
        println("\n\nEnter values:")
        val sc = Scanner(System.`in`)
        test(sc.nextLine())
    }
}
