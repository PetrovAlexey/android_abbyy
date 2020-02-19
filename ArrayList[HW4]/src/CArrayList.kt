import java.lang.IndexOutOfBoundsException
import kotlin.arrayOf as arrayOf

class CArrayList<T> {

    private var resizeFactor: Int = 2
    private var size: Int = 0
    private var bufferSize: Int = 2
    private var elementData: Array<Any?> = Array(bufferSize) { _ -> 0}

    private fun tryExtend() {
        if (size + 1 >= bufferSize) {
            elementData = elementData.copyOf(bufferSize * resizeFactor)
            bufferSize *= resizeFactor
        }
    }

    operator fun get(index : Int) : T {
        return elementData[index] as T
    }


    fun add(element: T) {
        tryExtend()
        elementData[size] = element!!
        size ++
    }

    fun insert(element: T, index: Int) {
        if (index > size || index < 0) {
            throw IndexOutOfBoundsException()
        }

        tryExtend()

        var i = size
        while (i >= index) {
            elementData[i+1] = elementData[i]
            i --
        }

        elementData[index] = element!!
        size ++
    }

    fun delete(index: Int) {
        if (index > size || index < 0) {
            throw IndexOutOfBoundsException()
        }

        var i = index + 1
        while (i <= size) {
            elementData[i-1] = elementData[i]
            i ++
        }

        size --
    }

}