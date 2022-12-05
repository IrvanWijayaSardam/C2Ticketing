package com.ctwofinalproject.ticketing.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ctwofinalproject.ticketing.entity.RecentSearch

@Dao
interface RecentSearchDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRecentSearch(recentSearch: RecentSearch)

    @Query("SELECT * FROM RecentSearch")
    fun getAllRecentSearch() : LiveData<List<RecentSearch>>

    @Query("DELETE FROM RecentSearch")
    fun deleteAllRecentSearch()

}