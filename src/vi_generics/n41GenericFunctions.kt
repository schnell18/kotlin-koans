package vi_generics

import util.TODO
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

fun task41(): Nothing = TODO(
    """
        Task41.
        Add a 'partitionTo' function that splits a collection into two collections according to a predicate.
        Uncomment the commented invocations of 'partitionTo' below and make them compile.

        There is a 'partition()' function in the standard library that always returns two newly created lists.
        You should write a function that splits the collection into two collections given as arguments.
        The signature of the 'toCollection()' function from the standard library may help you.
    """,
        references = { l: List<Int> ->
            l.partition { it > 0 }
            l.toCollection(HashSet<Int>())
        }
)

fun <T, C: MutableCollection<T>> Collection<T>.partitionTo(first: C, second: C, predicate: (T) -> Boolean): Pair<C, C> {
    for (s in this) {
        if (predicate(s)) {
            first.add(s)
        }
        else {
            second.add(s)
        }
    }
    return Pair(first, second)
}

fun List<String>.partitionWordsAndLines(): Pair<List<String>, List<String>> {
    fun partitionTo(list: ArrayList<String>, arrayList: ArrayList<String>, predicate: (String) -> Boolean): Pair<List<String>, List<String>> {
        for (s in this) {
           if (predicate(s)) {
               list.add(s)
           }
           else {
               arrayList.add(s)
           }
        }
        return Pair(list, arrayList)
    }
    return partitionTo(ArrayList(), ArrayList()) { s -> !s.contains(" ") }
}


fun Set<Char>.partitionLettersAndOtherSymbols(): Pair<Set<Char>, Set<Char>> {
    fun partitionTo(set1: HashSet<Char>, set2: HashSet<Char>, predicate: (Char) -> Boolean): Pair<Set<Char>, Set<Char>> {
        for (c in this) {
            if (predicate(c)) {
                set1.add(c)
            }
            else {
                set2.add(c)
            }
        }
        return Pair(set1, set2)
    }
    return partitionTo(HashSet(), HashSet()) { c -> c in 'a'..'z' || c in 'A'..'Z'}
}