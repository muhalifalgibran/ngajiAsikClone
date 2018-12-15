package id.alif.footbalmatchschedule.main

import android.database.sqlite.SQLiteConstraintException
import android.os.Build.ID
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.fragment.OverviewFragment
import id.alif.footbalmatchschedule.model.Team
import id.alif.footbalmatchschedule.presenter.TeamDetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.Adapter.TabDetailAdapter
import id.alif.footbalmatchschedule.database.Favorite
import id.alif.footbalmatchschedule.database.database
import id.alif.footbalmatchschedule.fragment.PlayerFragment
import org.jetbrains.anko.ctx
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import id.alif.footbalmatchschedule.database.Favorite.Companion.TABLE_FAVORITE
import id.alif.footbalmatchschedule.database.FavoriteTeam
import id.alif.footbalmatchschedule.database.FavoriteTeam.Companion.TABLE_FAVORITE_TEAM
import id.alif.footbalmatchschedule.database.FavoriteTeam.Companion.TEAM_BADGE
import id.alif.footbalmatchschedule.database.FavoriteTeam.Companion.TEAM_ID
import id.alif.footbalmatchschedule.database.FavoriteTeam.Companion.TEAM_NAME
import id.alif.footbalmatchschedule.database.databaseTeam
import id.alif.footbalmatchschedule.model.DetailMatch

class DetailTeamActivity : AppCompatActivity(), TeamsView {

    private var menuItem: Menu? = null
    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: Team
    private var isFavorite: Boolean = false
    private lateinit var id: String
    private var detailM: List<Team> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val request = ApiRepository()
        val gson = Gson()
        val intent = intent
        id = intent.getStringExtra("id")
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)

        setSupportActionBar(findViewById(R.id.kembali))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        favoriteState()
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite ->{
                if(isFavorite) removeFromFavorite() else addToFavorite()
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun tabDetail(deskripsi: String?) {
        val adapter = TabDetailAdapter(supportFragmentManager)
        adapter.add(OverviewFragment.newInstance(deskripsi), "Overview")
        adapter.add(PlayerFragment.newInstance(teams.teamId), "Players")
        viewpager_main2.adapter = adapter
        tabs_main2.setupWithViewPager(viewpager_main2)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }
    override fun showTeamList(data: List<Team>) {
        teams = Team(data[0].teamId,
            data[0].teamDescription,
            data[0].teamName,
            data[0].teamBadge)
        supportActionBar?.title = data[0].teamName
        Picasso.get().load(data[0].teamBadge).into(team_badgeTeam)
        nameTeam.text = data[0].teamName
        year.text = data[0].teamFormedYear
        stadium.text = data[0].teamStadium
        tabDetail(data[0].teamDescription)

        detailM = data
    }


    private fun addToFavorite(){
        try {
            database.use {
                insert(
                    TABLE_FAVORITE_TEAM,
                    TEAM_ID to detailM.firstOrNull()?.teamId,
                    TEAM_NAME to detailM.firstOrNull()?.teamName,
                    TEAM_BADGE to detailM.firstOrNull()?.teamBadge)
            }

            Log.d("wkwkland12", detailM.firstOrNull()?.teamId)
            val builder = AlertDialog.Builder(ctx)
                .setTitle("Favorite")
                .setMessage("Added To Favorite")

            builder.create().show()
        } catch (e: SQLiteConstraintException){
            val builder = AlertDialog.Builder(ctx)
                .setTitle("Something Wrong...")
                .setMessage(e.localizedMessage)
            builder.create().show()
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs("(${FavoriteTeam.TEAM_ID} = {id})",
                    "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) {
                isFavorite = true
            }
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(
                  TABLE_FAVORITE_TEAM, "(${FavoriteTeam.TEAM_ID} = {id})",
                    "id" to id
                )
            }
            val builder = AlertDialog.Builder(ctx)
                .setTitle("Favorite")
                .setMessage("Removed to favorite")

            builder.create().show()
        } catch (e: SQLiteConstraintException){
            val builder = AlertDialog.Builder(ctx)
                .setTitle("Something Wrong...")
                .setMessage(e.localizedMessage)
            builder.create().show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
                R.drawable.ic_added_to_fav
            )
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this,
               R.drawable.ic_add_to_fav
            )
    }

}
