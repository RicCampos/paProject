import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class JSONFileTest {

    @Test
    fun serialize() {
        val expected = "\"Test\": {\n" +
                "\t\"Nome\": \"Joaquim\",\n" +
                "\t\"Idade\": 55,\n" +
                "\"Object\": {\n" +
                "\t\"s\": \"testVal\",\n" +
                "\t\"n\": 10\n" +
                "},\n" +
                "\"Aluno\": {\n" +
                "\t\"Nome\": \"Ronaldo\",\n" +
                "\t\"Idade\": 16,\n" +
                "\t\"Lista\": [\"coisa1\",\"coisa2\",\"coisa3\"]\n" +
                "}"

        var json = JSONFile()

        val oString = JSONString("s","testVal")
        val oNumber = JSONNumber("n",10)
        val l = mutableListOf<JSONElement>()
        l.add(oString)
        l.add(oNumber)
        val oObj = JSONObject("Object", l)
        val ly = mutableListOf<JSONElement>()
        ly.add(JSONString("Nome", "Joaquim"))
        ly.add(JSONNumber("Idade", 55))
        ly.add(oObj)
        val o = JSONObject("Test",ly)

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

        assertEquals(expected,json.serialize())
    }

    @Test
    fun searchByPropName() {
        assertEquals(4,5)
    }
}