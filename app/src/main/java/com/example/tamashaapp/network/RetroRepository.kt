package com.example.tamashaapp.network

import android.util.Log
import com.example.tamashaapp.common.Resource
import com.example.tamashaapp.model.Data
import com.example.tamashaapp.model.Employees
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class RetroRepository @Inject constructor(
    private val retroServiceInstance: RetroServiceInstance
) {
    fun makeApiCall(): Flow<Resource<Employees>> = flow {
        try {
            emit(Resource.Loading())
            val data = retroServiceInstance.getEmployeesFromApi()
            emit(Resource.Success(data))
            Log.d("Repository", "makeApiCall: $data")
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An Unexpected Error Occurred!"))
            e.printStackTrace()
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection."))
            e.printStackTrace()
        } catch (e: Exception) {
            emit(Resource.Error("Couldn't fetch the data"))
            e.printStackTrace()
        }
    }

}