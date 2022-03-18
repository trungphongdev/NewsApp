package com.example.newsappmvvm.database

import android.content.Context
import androidx.room.*
import com.example.newsappmvvm.model.Article

@Database(entities = arrayOf(Article::class),version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao
    companion object {
        @Volatile
        private var INSTANCE: ArticleDatabase? = null
        fun getInstance(context: Context): ArticleDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    "db_articles"
                ).build()
                INSTANCE = instance
                instance
            }
        }

    }


}