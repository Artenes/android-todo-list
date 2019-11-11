package bok.artenes.todoapp.common

interface Observer<T> {

    fun accept(value: T)

}