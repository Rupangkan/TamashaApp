package com.example.tamashaapp.model

data class Employees(
    var `data`: List<Data> = emptyList(),
    var message: String = "",
    var status: String = "",
    var loading: Boolean = false
)