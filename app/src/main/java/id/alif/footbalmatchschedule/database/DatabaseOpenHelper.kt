package id.alif.footbalmatchschedule.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class DatabaseOpenHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx, "FavoriteTeam.db",null, 1) {
    companion object {
        private var instance: DatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) = instance ?: DatabaseOpenHelper(ctx)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Favorite.TABLE_FAVORITE, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.ID_EVENT to TEXT,
            Favorite.STRHOMETEAM to TEXT,
            Favorite.STRAWAYTEAM to TEXT,
            Favorite.IDHOMETEAM to TEXT,
            Favorite.IDAWAYTEAM to TEXT,
            Favorite.INTHOMESCORE to TEXT,
            Favorite.INTAWAYSCORE to TEXT,
            Favorite.STRDATE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favorite.TABLE_FAVORITE, true)
    }
}

val Context.database: DatabaseOpenHelper
    get() = DatabaseOpenHelper.getInstance(applicationContext)