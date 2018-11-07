package id.alif.footbalmatchschedule.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.google.gson.Gson
import com.google.gson.JsonObject
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.model.LastMatchTeam
import id.alif.footbalmatchschedule.presenter.LastMatchPresenter
import id.alif.footbalmatchschedule.util.invisible
import id.alif.footbalmatchschedule.util.visible
import org.jetbrains.anko.ctx
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh
class LastMatchFragment: Fragment(),LastMatchView {

    private var lastMatch: MutableList<LastMatchTeam> = mutableListOf()
    private lateinit var adapter: LastMatchAdapter
    private lateinit var presenter: LastMatchPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var lastMatchList: RecyclerView
    private lateinit var leagueName: String

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.last_match, container, false)

        lastMatchList = view.findViewById<RecyclerView>(R.id.recyclerLastMatch).apply {
            layoutManager = LinearLayoutManager(ctx)
        }

        val listener: (LastMatchTeam) -> Unit = {
            startActivity(intentFor<DetailLastMatch>(
                "strEvent" to it.teamIdLast.toString(),
                "idHomeTeam" to it.idHomeTeam.toString(),
                "idAwayTeam" to it.idAwayTeam.toString()))
        }

        adapter = LastMatchAdapter(lastMatch, listener)
        lastMatchList.adapter = adapter


        val request = ApiRepository()
        val gson = Gson()

        progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        presenter = LastMatchPresenter(this, request, gson)

        swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipe)

        presenter.getLastMatch("4328")

        swipeRefresh.onRefresh {
            presenter.getLastMatch("4328")
        }

        return view
    }


    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showTeamList(data: List<LastMatchTeam>) {
        swipeRefresh.isRefreshing = false
        lastMatch.clear()
        lastMatch.addAll(data)
        adapter.notifyDataSetChanged()
    }

}