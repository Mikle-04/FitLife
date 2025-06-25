package com.example.core.presentatin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.presentatin.mvi.MviAction
import com.example.core.presentatin.mvi.MviIntent
import com.example.core.presentatin.mvi.MviReducer
import com.example.core.presentatin.mvi.MviState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseMviViewModel<
        I : MviIntent,
        S : MviState,
        A : MviAction>(
    initialState: S,
    private val reducer: MviReducer<S, A>
        ): ViewModel() {

    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()

    private val intents = Channel<I>(Channel.UNLIMITED)

    init {
        // обрабатываем каждое Intent через handleIntent
        intents
            .receiveAsFlow()
            .onEach { handleIntent(it) }
            .launchIn(viewModelScope)
    }

    /** Вызывай из UI, чтобы отправить Intent */
    fun sendIntent(intent: I) {
        intents.trySend(intent)
    }

    /** Обработка Intent должна быть реализована в потомке */
    protected abstract fun handleIntent(intent: I)

    /** Диспатчим внутренний Action и получаем новый state */
    protected fun dispatch(action: A) {
        val newState = reducer.reduce(_state.value, action)
        _state.value = newState
    }
}