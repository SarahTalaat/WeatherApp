package com.example.weatherapplication.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapplication.Model.AlertModel.MyApplicationAlertModel.Model_Time
import kotlinx.coroutines.flow.Flow

@Dao
interface ModelTimeDAOInterface {
    @Query("SELECT * FROM alert_table ")
    fun getAllStoredModelTime_InDAOInterface(): Flow<List<Model_Time>>

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    suspend fun insertModelTime_InDAOInterface (time: Model_Time) : Long

    @Delete
    suspend fun deleteModelTime_InDAOInterface(time: Model_Time): Int

}