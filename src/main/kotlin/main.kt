enum class TypeEnum {
    STRING,ARRAY,INT,OBJECT,ENUM
}

class JSONFile (var objList: MutableList<JSONObject>){
    val nElements: MutableList<JSONObject> = mutableListOf()

    fun addElement(e: JSONObject){
        nElements.add(e)
    }

    fun search (name: String): Boolean{
        objList.forEach {
            if (it.name == name) {
                return true
            } else if (it is ) {
                var r = it.search(name)
                if (r)
                    return r
            }
        }
        return false
    }
}

class JSONObject (val name: String, val elements: MutableList<JSONElement>) {
    val nElements: MutableList<JSONElement> = mutableListOf()

    fun addElement(e: JSONElement){
        nElements.add(e)
    }

    fun search (name: String): Boolean{
        elements.forEach {
            if (it.name == name) {
                return true
            } else if (it is ) {
                var r = it.search(name)
                if (r)
                    return r
            }
        }
        return false
    }

    fun accept(v: Visitor) {
        var s = ""
        s += v.visit(this) + "\n"
        nElements.forEach {
            it.accept(v)
        }
    }

}

abstract class JSONElement (val name: String, val type: TypeEnum) {

    fun accept(v: Visitor) {
        v.visit((this))
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
