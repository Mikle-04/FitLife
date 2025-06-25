package com.example.core.presentatin.mvi

interface MviReducer<S : MviState, A : MviAction> {
    fun reduce(state: S, action: A): S
}