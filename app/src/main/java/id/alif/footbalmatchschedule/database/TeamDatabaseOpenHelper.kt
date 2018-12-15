package id.alif.footbalmatchschedule.database


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class TeamDatabaseOpenHelper(ctx: Context): ManagedSQLiteOpenHelper(ctx, "favoriteTeamDet.db", null, 1) {
    companion object {
        private var instance: TeamDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) = instance ?: TeamDatabaseOpenHelper(ctx)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true,
            FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
            FavoriteTeam.TEAM_NAME to TEXT,
            FavoriteTeam.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true)
    }
}

val Context.databaseTeam: TeamDatabaseOpenHelper
    get() = TeamDatabaseOpenHelper.getInstance(applicationContext)