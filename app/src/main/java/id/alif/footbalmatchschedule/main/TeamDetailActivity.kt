package id.alif.footbalmatchschedule.main

import android.database.sqlite.SQLiteConstraintException
import android.graphics.Color
import android.os.Bundle
import android.support.design.R
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.appcompat.R.attr.actionBarSize
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView

import id.alif.footbalmatchschedule.R.drawable.ic_add_to_fav
import id.alif.footbalmatchschedule.R.drawable.ic_added_to_fav
import id.alif.footbalmatchschedule.R.id.add_to_favorite
import id.alif.footbalmatchschedule.R.menu.*
import id.alif.footbalmatchschedule.R.menu.detail_menu
import id.alif.footbalmatchschedule.R.layout.overview_detail
import id.alif.footbalmatchschedule.api.ApiRepository
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.alif.footbalmatchschedule.R.color.*
import id.alif.footbalmatchschedule.database.databaseTeam
import id.alif.footbalmatchschedule.R.color.colorPrimary
import id.alif.footbalmatchschedule.R.id.toolbarDetail
import id.alif.footbalmatchschedule.model.Team
import id.alif.footbalmatchschedule.presenter.TeamDetailPresenter
import id.alif.footbalmatchschedule.util.invisible
import id.alif.footbalmatchschedule.util.visible
import id.alif.footbalmatchschedule.database.FavoriteTeam
import org.jetbrains.anko.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import id.alif.footbalmatchschedule.R.layout.overview_detail
import kotlinx.android.synthetic.main.overview_detail.*


class TeamDetailActivity : AppCompatActivity(), TeamsView {

    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout

    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var teamBadge: ImageView
    private lateinit var teamName: TextView
    private lateinit var teamFormedYear: TextView
    private lateinit var teamStadium: TextView
    private lateinit var teamDescription: TextView

    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: Team
    private lateinit var id: String

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(detail_menu, menu)
        menuItem = menu
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home ->{
                finish()
                true
            }
           add_to_favorite -> {
                if (isFavorite) removeFromFavorite() else addToFavorite()

                isFavorite = !isFavorite
                setFavorite()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        teams = Team(data[0].teamId,
            data[0].teamName,
            data[0].teamBadge)
        swipeRefresh.isRefreshing = false
        Picasso.get().load(data[0].teamBadge).into(teamBadge)
        teamName.text = data[0].teamName
        teamDescription.text = data[0].teamDescription
        teamFormedYear.text = data[0].teamFormedYear
        teamStadium.text = data[0].teamStadium
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = intent
        id = intent.getStringExtra("id")

        supportActionBar?.title = "Team Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        linearLayout {
            backgroundColor = Color.CYAN
            orientation = LinearLayout.VERTICAL
            lparams(width = matchParent, height = wrapContent)

            toolbar{
                backgroundColor = colorPrimary
                id = toolbarDetail
            }.lparams(width= matchParent, height = dip(40))

            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorCrd,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                scrollView {
                    isVerticalScrollBarEnabled = false
                    relativeLayout {
                        lparams(width = matchParent, height = wrapContent)

                        linearLayout{

                            lparams(width = matchParent, height = wrapContent)
                            padding = dip(10)
                            orientation = LinearLayout.VERTICAL
                            gravity = Gravity.CENTER_HORIZONTAL

                            teamBadge =  imageView {}.lparams(height = dip(75))

                            teamName = textView{
                                this.gravity = Gravity.CENTER
                                textSize = 20f
                                textColor = ContextCompat.getColor(context, colorAccent)
                            }.lparams{
                                topMargin = dip(5)
                            }

                            teamFormedYear = textView{
                                this.gravity = Gravity.CENTER
                            }

                            teamStadium = textView{
                                this.gravity = Gravity.CENTER
                                textColor = ContextCompat.getColor(context, colorPrimaryDark)
                            }

                            teamDescription = textView{
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                            }.lparams{
                                topMargin = dip(20)
                            }
                        }
                        progressBar = progressBar {
                        }.lparams {
                            centerHorizontally()
                        }
                    }
                }
            }
        }


        val request = ApiRepository()
        val gson = Gson()
        favoriteState()
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)

        swipeRefresh.onRefresh {
            presenter.getTeamDetail(id)
        }

    }

    private fun favoriteState(){
        databaseTeam.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
                .whereArgs("(TEAM_ID = {id})",
                    "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun addToFavorite(){
        try {
            databaseTeam.use {
                insert(FavoriteTeam.TABLE_FAVORITE_TEAM,
                    FavoriteTeam.TEAM_ID to teams.teamId,
                    FavoriteTeam.TEAM_NAME to teams.teamName,
                    FavoriteTeam.TEAM_BADGE to teams.teamBadge)
            }
            swipeRefresh.snackbar("Added to favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar( e.localizedMessage).show()
        }
    }

    private fun removeFromFavorite(){
        try {
            databaseTeam.use {
                delete(
                    FavoriteTeam.TABLE_FAVORITE_TEAM, "(TEAM_ID = {id})",
                    "id" to id
                )
            }
            swipeRefresh.snackbar("Removed to favorite").show()
        } catch (e: SQLiteConstraintException){
            swipeRefresh.snackbar(e.localizedMessage).show()
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_added_to_fav)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, ic_add_to_fav)
    }
}

