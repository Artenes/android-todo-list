package bok.artenes.todoapp.common

/**
 * An observable value that mutates the value of another observable.
 * The problem is that I have an observable value that needs to be transformed
 * before being served to the client code. So I need to observer for this input,
 * whenever it changes I transform the value, generating an output, then I deliver
 * it to the client code. In the end he doesn't even know that the transformation happened.
 */
class MediatorObservableValue<I, O>(
    private val input: ObservableValue<I>,
    private val mutator: Mutator<I, O>
) : Observable<O> {

    private val listeners: MutableList<Observer<O>> = mutableListOf()

    private val observer = object : Observer<I> {
        override fun accept(value: I) {
            //whenever the input changes, mutates it, then notify all the observers
            listeners.forEach { it.accept(mutator.mutate(value)) }
        }
    }

    init {
        input.observe(observer)
    }

    override fun observe(observer: Observer<O>) {
        listeners.add(observer)
        //needs to mutate the value again here to deliver the value as soon as the observers attaches
        observer.accept(mutator.mutate(input.value))
    }

    override fun ignore(observer: Observer<O>) {
        listeners.remove(observer)
    }

    /**
     * TODO-TIL If we want to use java's functional interfaces we need to target at least version 24 of the android API.
     * A functional interface that receives an input type I and mutates it to
     * an output type O.
     */
    interface Mutator<I, O> {
        fun mutate(input: I): O
    }

}