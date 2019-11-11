package bok.artenes.todoapp.common

import kotlin.properties.Delegates

/**
 * An observable value that can be edited.
 */
open class ObservableValue<T>(initialValue: T) : Observable<T> {

    private val listeners: MutableList<Observer<T>> = mutableListOf()

    var value: T by Delegates.observable(initialValue) {
        prop, old, new -> listeners.forEach { it.accept(new) }
    }

    override fun observe(observer: Observer<T>) {
        listeners.add(observer)
        observer.accept(value)
    }

    override fun ignore(observer: Observer<T>) {
        listeners.remove(observer)
    }

}