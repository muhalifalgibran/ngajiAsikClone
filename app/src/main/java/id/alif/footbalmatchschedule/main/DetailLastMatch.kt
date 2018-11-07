package id.alif.footbalmatchschedule.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.model.*
import id.alif.footbalmatchschedule.presenter.DetailMatchPresenter
import kotlinx.android.synthetic.main.detail_last_match.*

class DetailLastMatch : AppCompatActivity(){

    private var detailM: List<DetailMatch> = mutableListOf()
    private var homeBadge: List<HomeBadge> = mutableListOf()
    private var awayBadge: List<AwayBadge> = mutableListOf()

    private lateinit var presenter: DetailMatchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_last_match)


        // my_child_toolbar is defined in the layout file
        setSupportActionBar(findViewById(R.id.upBar))

        // Get a support ActionBar corresponding to this toolbar and enable the Up button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val strEvent: String  = intent.getStringExtra("strEvent")
        val idHomeTeam: String  = intent.getStringExtra("idHomeTeam")
        val idAwayTeam: String  = intent.getStringExtra("idAwayTeam")

        val request = ApiRepository()
        val gson = Gson()

        presenter = DetailMatchPresenter(this, request, gson)

        presenter.getDetailMatch(strEvent,idHomeTeam,idAwayTeam)

        bindItem(detailM,homeBadge,awayBadge)

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
    }


}
