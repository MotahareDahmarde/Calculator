package com.example.calculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {
    var state by mutableStateOf(CalculatorState())
        private set

    fun onAction (action : CalculatorAction){
        when(action){
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Clear -> state = CalculatorState()
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Calculate -> performCalculation()
            is CalculatorAction.Delete -> delete()
        }
    }

    private fun delete() {
        when{
            state.number2.isNotBlank() -> state = state.copy(
                number2 = state.number2.dropLast(1)
            )

            state.operation != null -> state = state.copy(
                operation = null
            )

            state.number1.isNotBlank() -> state = state.copy(
                number1 = state.number1.dropLast(1)
            )
        }
    }

    private fun performCalculation() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDouble()
        val result = when (state.operation){
            is CalculatorOperation.Add -> number1?.plus(number2)
            is CalculatorOperation.Substract -> number1?.minus(number2)
            is CalculatorOperation.Multiply -> number1?.times(number2)
            is CalculatorOperation.Divide -> number1?.div(number2)
            null -> return
        }

        if (number1 != null && number2 != null){
            state = state.copy(
                number1 = result.toString().take(15),
                number2 = "",
                operation = null
            )
        }
    }

    private fun enterOperation(operation: CalculatorOperation) {
        if (state.number1.isNotBlank()) {
            state = state.copy(
                operation = operation
            )
        }
    }

    private fun enterDecimal() {
        if (state.operation == null && !state.number1.contains(".")
            && state.number1.isNotBlank()){
            state = state.copy(
                number1 = state.number1 + "."
            )
            return
        }
        if (!state.number2.contains(".") && state.number2.isNotBlank()){
            state = state.copy(
                number2 = state.number2 + "."
            )
        }
    }

    companion object{
        private const val max_num_length = 8
    }
    private fun enterNumber(number: Int) {
        if(state.operation == null){
            if (state.number1.length >= max_num_length)
                return
            state = state.copy(
                number1 = state.number1+number
            )
            return
        }
        if (state.number2.length >= max_num_length){
            return
        }
        state = state.copy(
            number2 = state.number2 + number
        )
    }
}