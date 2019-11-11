package bok.artenes.todoapp.common

interface Observable<T> {

    fun observe(observer: Observer<T>)

    fun ignore(observer: Observer<T>)

}