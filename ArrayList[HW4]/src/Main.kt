fun main() {
    var array : CArrayList<Int> = CArrayList()
    array.add(1)
    array.add(2)
    array.add(3)
    array.delete(0)
    array.delete(1)
    array.insert(4, 1)
    println(array[0])
    println(array[1])
}
