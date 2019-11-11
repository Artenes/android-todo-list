package bok.artenes.todoapp.common

/**
 * An observable value.
 * We do not use java's [java.util.Observable] because it does not uses generic types.
 */
interface Observable<T> {

    /**
     * Start observing any changes in the value.
     * When the observer is registered, it will immediately receive
     * the current value if available.
     */
    fun observe(observer: Observer<T>)

    /**
     * Removes the given observer.
     */
    fun ignore(observer: Observer<T>)

}