package main

class Point{
    var x: Int by StorageDelegate("x", 0)
    var y: Int by StorageDelegate("y", 0)
    override fun toString(): String {
        return "(%s, %s)".format(x, y)
    }

}

fun main(args: Array<String>) {
    val p: Point = Point()
    println(p)
    p.x = 10
    p.y = 20
    println(p)
}