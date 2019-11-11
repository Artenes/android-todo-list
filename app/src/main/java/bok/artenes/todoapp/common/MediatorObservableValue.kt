package bok.artenes.todoapp.common

/**
 * An observable value that mutates the value of another observable.
 */
class MediatorObservableValue<I, O>(
    private val input: ObservableValue<I>,
    private val mutator: Mutator<I, O>
) : Observable<O> {

    private val listeners: MutableList<Observer<O>> = mutableListOf()

    private val observer = object : Observer<I> {
        override fun accept(value: I) {
            listeners.forEach { it.accept(mutator.mutate(value)) }
        }
    }

    init {
        input.observe(observer)
    }

    override fun observe(observer: Observer<O>) {
        listeners.add(observer)
        observer.accept(mutator.mutate(input.value))
    }

    override fun ignore(observer: Observer<O>) {
        listeners.remove(observer)
    }

    interface Mutator<I, O> {
        fun mutate(input: I): O
    }

}