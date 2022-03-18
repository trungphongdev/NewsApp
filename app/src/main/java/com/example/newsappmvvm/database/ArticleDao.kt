package com.example.newsappmvvm.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.newsappmvvm.model.Article

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Query("SELECT * FROM articles")
     fun getAllArticle(): LiveData<List<Article>>

     @Delete
     fun deleteArticle(article: Article)
}