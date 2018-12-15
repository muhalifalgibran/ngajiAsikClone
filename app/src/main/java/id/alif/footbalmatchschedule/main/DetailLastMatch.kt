package id.alif.footbalmatchschedule.main


import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.R.drawable.ic_add_to_fav
import id.alif.footbalmatchschedule.R.drawable.ic_added_to_fav
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.model.*
import id.alif.footbalmatchschedule.presenter.DetailMatchPresenter
import kotlinx.android.synthetic.main.detail_last_match.*
import id.alif.footbalmatchschedule.R.id.add_to_favorite
import id.alif.footbalmatchschedule.database.database
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import id.alif.footbalmatchschedule.database.Favorite
import org.jetbrains.anko.ctx
import org.jetbrains.anko.support.v4.onRefresh


class DetailLastMatch : AppCompatActivity(){

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var swipeRefresh: SwipeRefreshLayout


    private var detailM: List<DetailMatch> = mutableListOf()
    private var homeBadge: List<HomeBadge> = mutableListOf()
    private var awayBadge: List<AwayBadge> = mutableListOf()

    private lateinit var presenter: DetailMatchPresenter
    private lateinit var teams: DetailMatch
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_last_match)

        setSupportActionBar(findViewById(R.id.upBar))
        supportActionBar?.title = "Match Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val strEvent: String  = intent.getStringExtra("strEvent")
        val idHomeTeam: String  = intent.getStringExtra("idHomeTeam")
        val idAwayTeam: String  = intent.getStringExtra("idAwayTeam")

        id = strEvent
        swipeRefresh = findViewById<SwipeRefreshLayout>(R.id.swipeDetail)
        swipeRefresh.isRefreshing = true

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailMatchPresenter(this, request, gson)

        presenter.getDetailMatch(strEvent,idHomeTeam,idAwayTeam)

        bindItem(detailM,homeBadge,awayBadge)

        swipeRefresh.onRefresh {
            bindItem(detailM,homeBadge,awayBadge)
            swipeRefresh.isRefreshing = false
        }

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

    private fun addToFavorite(){
        try {
            database.use {
                insert(Favorite.TABLE_FAVORITE,
                    Favorite.ID_EVENT to detailM.firstOrNull()?.teamIdLast,
                    Favorite.STRHOMETEAM to detailM.firstOrNull()?.strHomeTeam,
                    Favorite.STRAWAYTEAM to detailM.firstOrNull()?.strAwayTeam,
                    Favorite.IDHOMETEAM to detailM.firstOrNull()?.idHomeTeam,
                    Favorite.IDAWAYTEAM to detailM.firstOrNull()?.idAwayTeam,
                    Favorite.INTAWAYSCORE to detailM.firstOrNull()?.intAwayScore.toString(),
                    Favorite.INTHOMESCORE to detailM.firstOrNull()?.intHomeScore.toString(),
                    Favorite.STRDATE to detailM.firstOrNull()?.strDate)
            }
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
            val result = select(Favorite.TABLE_FAVORITE)
                .whereArgs("(${Favorite.ID_EVENT} = {id})",
                    "id" to id)
            val favorite = result.parseList(classParser<Favorite>())
            if (!favorite.isEmpty()){
                isFavorite = true
            }
        }
    }

    private fun removeFromFavorite(){
        try {
            database.use {
                delete(
                    Favorite.TABLE_FAVORITE, "(${Favorite.ID_EVENT} = {id})",
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
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_fav)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_fav)
    }


    fun bindItem(item: List<DetailMatch>,homeBadge: List<HomeBadge>, awayBadge: List<AwayBadge>){
        dateTime.text = item.firstOrNull()?.strDate
        homeName.text = item.firstOrNull()?.strHomeTeam
        awayName.text = item.firstOrNull()?.strAwayTeam
        homeScore.text = item.firstOrNull()?.intHomeScore
        awayScore.text = item.firstOrNull()?.intAwayScore
        homeGoals.text = item.firstOrNull()?.strHomeGoalDetails
        awayGoals.text = item.firstOrNull()?.strAwayGoalDetails
        homeShots.text = item.firstOrNull()?.intHomeShots
        awayShots.text = item.firstOrNull()?.intAwayShots
        gkHome.text = item.firstOrNull()?.strHomeLineupGoalkeeper
        gkAway.text = item.firstOrNull()?.strAwayLineupGoalkeeper
        defHome.text = item.firstOrNull()?.strHomeLineupDefense
        defAway.text = item.firstOrNull()?.strAwayLineupDefense
        midHome.text = item.firstOrNull()?.strHomeLineupMidfield
        midAway.text = item.firstOrNull()?.strAwayLineupMidfield
        strikerHome.text = item.firstOrNull()?.strHomeLineupForward
        strikerAway.text = item.firstOrNull()?.strAwayLineupForward
        subHome.text = item.firstOrNull()?.strHomeLineupSubstitutes
        subAway.text = item.firstOrNull()?.strAwayLineupSubstitutes

        Picasso.get().load(homeBadge.firstOrNull()?.strTeamBadgeHome.toString()).into(homeLogo)
        Picasso.get().load(awayBadge.firstOrNull()?.strTeamBadgeAway.toString()).into(awayLogo)

        detailM = item
        swipeRefresh.isRefreshing = false

    }
}
