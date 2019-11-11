package bok.artenes.todoapp.common

/**
 * An observer of a [Observable].
 * We do not use java's [java.util.Observer] because it does not uses generic types.
 */
interface Observer<T> {

    fun accept(value: T)

}