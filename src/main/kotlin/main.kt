class JSONFile (){
    var objList: MutableList<JSONObject> = mutableListOf()

    //Adiciona um novo objecto ao JSONFile
    fun addObject(e: JSONObject){
        objList.add(e)
    }

    fun serialize(): String {

        //Cria as funções do visitor
            val v = object: Visitor {
                var tree = ""
                override fun visit(obj: JSONObject): String {
                    tree += obj.name + "\n{\n"

                    /*
                    obj.elements.forEach {
                        tree += visit(it)
                    }
                     */

                    return ""
                }
                override fun visit(elem: JSONElement): String {
                    tree += elem.name + ": "

                    if(elem is JSONString){
                        elem as JSONString
                        tree += "'" + elem.value + "'"
                    }
                    else if(elem is JSONNumber) {
                        elem as JSONNumber
                        tree += elem.value
                    }
                    else if(elem is JSONArray || elem is JSONEnum) {
                        elem as JSONArray
                        tree += "["
                        elem.value.forEach {
                            if(it is JSONObject){
                                tree += visit(it)
                            }
                            tree += "\"$it\","
                        }
                        tree = tree.substring(0,tree.length - 1) + "]"
                    }
                    else if(elem is JSONObject) {
                        tree += visit(elem)
                    }
                    else {
                        tree += "SHIIIT"
                    }
                    tree += ",\n"
                    return tree
                }
            }

        objList.forEach {
            it.accept(v)
            v.tree = v.tree.substring(0,v.tree.length - 2) + "\n},\n"
        }

        return v.tree.substring(0,v.tree.length - 2)
    }

    //Função de procura básica, devolve true se existe um objecto ou propriedade com o nome inserido ou false caso contrario
    fun search (name: String): Boolean{
        objList.forEach {
            if (it.name == name) {
                return true
            } else if (it is JSONElement) {
                var r = it.search(name)
                if (r)
                    return r
            }
        }
        return false
    }
}

abstract class JSONElement (val name: String) {
    abstract fun accept(v: Visitor)
}

class JSONObject(name: String, val elements: MutableList<JSONElement>) : JSONElement(name) {

    /*val nElements: MutableList<JSONElement> = mutableListOf()

    fun addElement(e: JSONElement){
        nElements.add(e)
    }*/

    //Função de procura básica, devolve true se existe um objecto ou propriedade com o nome inserido ou false caso contrario
    fun search (name: String): Boolean{
        elements.forEach {
            if (it.name == name) {
                return true
            } else if (it is JSONObject) {
                var r = it.search(name)
                if (r)
                    return r
            }
        }
        return false
    }

    override fun accept(v: Visitor) {
        v.visit(this)
        elements.forEach {
            it.accept(v)
        }
        /*
        var s = ""
        s += v.visit(this) + "\n"
        elements.forEach {
            it.accept(v)
        }
         */
    }

}

class JSONString (name: String, val value: String): JSONElement(name) {
    override fun accept(v: Visitor) {
        v.visit(this)
    }
}
class JSONNumber (name: String, val value: Int): JSONElement(name) {
    override fun accept(v: Visitor) {
        v.visit(this)
    }
}
class JSONArray (name: String, val value: MutableList<Any>): JSONElement(name) {
    override fun accept(v: Visitor) {
        v.visit(this)
    }
}
class JSONEnum (name: String, val value: MutableList<String>): JSONElement(name) {
    override fun accept(v: Visitor) {
        v.visit(this)
    }
}

interface Visitor {
    fun visit(obj: JSONObject): String = ""
    fun visit(elem: JSONElement): String = ""
}

/*
fun objToJson(o: Object): JSONElement {
    //Não sei se os objectos vêm já formados ou em campos
    //Método que passaria um objeto qualquer para um JSONElement
}
*/

fun main () {
    var json = JSONFile()

    val oString = JSONString("s","testVal")
    val oNumber = JSONNumber("n",10)
    val l = mutableListOf<JSONElement>()
    l.add(oString)
    l.add(oNumber)
    val o = JSONObject("Test",l)

    val yString = JSONString("Nome","Ronaldo")
    val yNumber = JSONNumber("Idade",16)
    val lt = mutableListOf<Any>()
    lt.add("coisa1")
    lt.add("coisa2")
    lt.add("coisa3")
    val yList = JSONArray("Lista", lt)
    val l2 = mutableListOf<JSONElement>()
    l2.add(yString)
    l2.add(yNumber)
    l2.add(yList)
    val y = JSONObject("Aluno",l2)

    json.addObject(o)
    json.addObject(y)

    println(json.serialize())

    /*
    val v = object: Visitor {
        var tree = ""
        override fun visit(obj: JSONObject): String {
            tree += obj.name
            return ""
        }
        override fun visit(elem: JSONElement): String {
            tree += elem.name
            return ""
        }
    }

    o.accept((v))
    println(v.tree)
     */

}
