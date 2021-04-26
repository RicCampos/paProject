enum class TypeEnum {
    STRING,ARRAY,INT,OBJECT,ENUM
}

class JSONFile (){
    var objList: MutableList<JSONObject> = mutableListOf()

    fun addObject(e: JSONObject){
        objList.add(e)
    }

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

abstract class JSONElement (val name: String, val type: TypeEnum) {
    abstract fun accept(v: Visitor)
}

class JSONObject
    (
    name: String,
    type: TypeEnum,
    val elements: MutableList<JSONElement>
    )
    : JSONElement(name,type) {

    /*val nElements: MutableList<JSONElement> = mutableListOf()

    fun addElement(e: JSONElement){
        nElements.add(e)
    }*/

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
        var s = ""
        s += v.visit(this) + "\n"
        elements.forEach {
            it.accept(v)
        }
    }

}

class JSONString (val name: String, val value: String)
class JSONNumber (val name: String, val value: Int)
class JSONArray (val name: String, val value: MutableList<Any>)
class JSONEnum (val name: String, val value: MutableList<String>)

interface Visitor {
    fun visit(obj: JSONObject): String = ""
    fun visit(elem: JSONElement): String = ""
}

fun main () {
    var json = JSONFile()

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

}
