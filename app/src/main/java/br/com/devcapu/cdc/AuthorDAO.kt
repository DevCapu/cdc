package br.com.devcapu.cdc

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface AuthorDAO {

    @Insert
    fun save(author: Author)
}