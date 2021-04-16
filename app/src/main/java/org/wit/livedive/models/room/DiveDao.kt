package org.wit.livedive.models.room

import androidx.room.*
import org.wit.livedive.models.DiveModel

@Dao
interface DiveDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(dive: DiveModel)

    @Query("SELECT * FROM DiveModel")
    fun findAll(): List<DiveModel>

    @Query("select * from DiveModel where id = :id")
    fun findById(id:Long): DiveModel

    @Update
    fun update(dive:DiveModel)

    @Delete
    fun deleteDive(dive:DiveModel)
}