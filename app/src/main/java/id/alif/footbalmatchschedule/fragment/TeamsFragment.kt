package id.alif.footbalmatchschedule.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import id.alif.footbalmatchschedule.R.color.colorAccent
import id.alif.footbalmatchschedule.Adapter.TeamsAdapter
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView
import org.jetbrains.anko.support.v4.swipeRefreshLayout
import id.alif.footbalmatchschedule.R.array.league
import id.alif.footbalmatchschedule.model.Team
import id.alif.footbalmatchschedule.presenter.TeamsPresenter
import id.alif.footbalmatchschedule.util.invisible
import id.alif.footbalmatchschedule.util.visible
import org.jetbrains.anko.support.v4.ctx
import id.alif.footbalmatchschedule.api.*
import org.jetbrains.anko.support.v4.onRefresh
import id.alif.footbalmatchschedule.R.id.spinner2
import id.alif.footbalmatchschedule.R.id.list_itemR
import id.alif.footbalmatchschedule.R.id.cariTeam
import id.alif.footbalmatchschedule.main.DetailTeamActivity
import id.alif.footbalmatchschedule.main.TeamDetailActivity
import id.alif.footbalmatchschedule.main.TeamsView
import kotlinx.android.synthetic.main.last_match.view.*


class TeamsFragment : Fragment(),AnkoComponent<Context>, TeamsView {
    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamsPresenter
    private lateinit var adapter: TeamsAdapter
    private lateinit var spinner: Spinner
    private lateinit var listEvent: RecyclerView
    private lateinit var progressBar1: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var leagueName: String
    private lateinit var cari: SearchView

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val spinnerItems = resources.getStringArray(league)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinner.adapter = spinnerAdapter

        adapter = TeamsAdapter(teams) {
          context?.startActivity<DetailTeamActivity>("id" to "${it.teamId}")
        }
        listEvent.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamsPresenter(this, request, gson)

        cari.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getTeamSearch(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.getTeamSearch(newText)
                return true
            }

        })

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                leagueName = spinner.selectedItem.toString()
                presenter.getTeamList(leagueName)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

      presenter.getTeamList("English Premier League")

        swipeRefresh.onRefresh {
           presenter.getTeamList(leagueName)
        }
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return createView(AnkoContext.create(ctx))
    }

    override fun createView(ui: AnkoContext<Context>): View = with(ui){
        linearLayout {
            lparams (width = matchParent, height = wrapContent)
            orientation = LinearLayout.VERTICAL
            topPadding = dip(16)
            leftPadding = dip(16)
            rightPadding = dip(16)

            cari = searchView {
                queryHint = "Search.."
                id = cariTeam
                gravity = Gravity.RIGHT
            }.lparams(width= matchParent, height = wrapContent)

            spinner = spinner (){
                id = spinner2
            }
            swipeRefresh = swipeRefreshLayout {
                setColorSchemeResources(colorAccent,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light)

                relativeLayout{
                    lparams (width = matchParent, height = wrapContent)

                    listEvent = recyclerView {
                        id = list_itemR
                        lparams (width = matchParent, height = wrapContent)
                        layoutManager = LinearLayoutManager(ctx)
                    }

                    progressBar1 = progressBar {
                    }.lparams{
                        centerHorizontally()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar1.visible()
    }

    override fun hideLoading() {
        progressBar1.invisible()
    }

    override fun showTeamList(data: List<Team>) {
        swipeRefresh.isRefreshing = false
        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }
}