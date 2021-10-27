package com.example.todoapplication.feature_note.domain.util

sealed class TodoNoteOrder (val orderType: OrderType){
    class Title(orderType: OrderType): TodoNoteOrder(orderType)
    class Date(orderType: OrderType): TodoNoteOrder(orderType)
    class Color(orderType: OrderType): TodoNoteOrder(orderType)

    fun copy(orderType: OrderType): TodoNoteOrder{
        return when(this){
            is Title-> Title(orderType)
            is Date-> Date(orderType)
            is Color-> Color(orderType)
        }
    }
}
