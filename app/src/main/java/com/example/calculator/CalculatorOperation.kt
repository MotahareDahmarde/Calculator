package com.example.calculator

sealed class CalculatorOperation(val symbol : String){
    object Add : CalculatorOperation("+")
    object Substract : CalculatorOperation("-")
    object Multiply : CalculatorOperation("X")
    object Divide : CalculatorOperation("/")
}
