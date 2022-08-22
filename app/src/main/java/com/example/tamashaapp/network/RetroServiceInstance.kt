package com.example.tamashaapp.network

import com.example.tamashaapp.model.Data
import com.example.tamashaapp.model.Employees
import retrofit2.Call
import retrofit2.http.GET

interface RetroServiceInstance {
    @GET("v1/employees")
    suspend fun getEmployeesFromApi(): Employees
}