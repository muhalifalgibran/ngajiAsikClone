package id.alif.footbalmatchschedule.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.ProgressBar
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.alif.footbalmatchschedule.Adapter.FragmentAdapter
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.fragment.LoadDetailFragment
import id.alif.footbalmatchschedule.fragment.LoadMatchFragment
import id.alif.footbalmatchschedule.fragment.OverviewFragment
import id.alif.footbalmatchschedule.model.Team
import id.alif.footbalmatchschedule.presenter.TeamDetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.Adapter.TabDetailAdapter
import id.alif.footbalmatchschedule.fragment.PlayerFragment

class DetailTeamActivity : AppCompatActivity(), TeamsView {

    private var menuItem: Menu? = null
    private lateinit var presenter: TeamDetailPresenter
    private lateinit var teams: Team
    private lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val request = ApiRepository()
        val gson = Gson()
        val intent = intent
        id = intent.getStringExtra("id")
        presenter = TeamDetailPresenter(this, request, gson)
        presenter.getTeamDetail(id)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        return true
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
       Picasso.get().load(data[0].teamBadge).into(team_badgeTeam)
        nameTeam.text = data[0].teamName
        year.text = data[0].teamFormedYear
        stadium.text = data[0].teamStadium
        tabDetail(data[0].teamDescription)
    }
}
