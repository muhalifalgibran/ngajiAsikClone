package id.alif.footbalmatchschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.gson.Gson
import com.mancj.materialsearchbar.MaterialSearchBar
import id.alif.footbalmatchschedule.Adapter.LastMatchAdapter
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.main.DetailLastMatch
import id.alif.footbalmatchschedule.main.LastMatchView
import id.alif.footbalmatchschedule.model.LastMatchTeam
import id.alif.footbalmatchschedule.presenter.NextMatchPresenter
import id.alif.footbalmatchschedule.util.invisible
import id.alif.footbalmatchschedule.util.visible
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh
import kotlinx.android.synthetic.main.next_match.*

class NextMatchFragment: Fragment(), LastMatchView {

    private var lastMatch: MutableList<LastMatchTeam> = mutableListOf()
    private lateinit var adapter: LastMatchAdapter
    private lateinit var presenter: NextMatchPresenter
    private lateinit var progressBar1: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var lastMatchList: RecyclerView
   // private lateinit var spinnerNext: Spinner
    private lateinit var leagueName: String
    private lateinit var notif: ImageView
    private var cekKlik: Boolean = false


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinnerNext.adapter = spinnerAdapter

        recyclerNextMatchlist.layoutManager = LinearLayoutManager(ctx)

        val listener: (LastMatchTeam) -> Unit = {
            startActivity(intentFor<DetailLastMatch>(
                "strEvent" to it.teamIdLast.toString(),
                "idHomeTeam" to it.idHomeTeam.toString(),
                "idAwayTeam" to it.idAwayTeam.toString()))
        }

        adapter = LastMatchAdapter(lastMatch, listener, "next")

        recyclerNextMatchlist.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        progressBar1 = progressBar

        presenter = NextMatchPresenter(this, request, gson)

        swipeRefresh = swipe

        spinnerNext.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                presenter.getNextMatch("4328")
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                leagueName = spinnerNext.selectedItem.toString()
                if (leagueName == "English Premier League"){
                    leagueName = "4328"
                }else if (leagueName == "German Bundesliga"){
                    leagueName = "4331"
                }else if (leagueName == "English League Championship"){
                    leagueName = "4329"
                }else if (leagueName == "Italian Serie A"){
                    leagueName = "4332"
                }else if (leagueName == "French Ligue 1"){
                    leagueName = "4334"
                }else if (leagueName == "Spanish La Liga"){
                    leagueName = "4335"
                }else{
                    leagueName = "4335"
                }
                presenter.getNextMatch(leagueName)
            }
        }

        swipe.onRefresh {
            presenter.getNextMatch(leagueName)
        }
        val searchBar = searchBar as MaterialSearchBar

        searchBar.addTextChangeListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s != null){
                    val str = s.toString()
                    presenter.getNextMatchSearch(str)

                }else{
                    presenter.getNextMatch("4328")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s != null){
                    val str = s.toString()
                    presenter.getNextMatchSearch(str)

                }else{

                    presenter.getNextMatch("4328")
                }

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s != null){
                    val str = s.toString()
                    presenter.getNextMatchSearch(str)

                }else{

                    presenter.getNextMatch("4328")
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.next_match, container, false)
        return view
    }




    override fun showLoading() {
        progressBar1.visible()
    }

    override fun hideLoading() {
        progressBar1.invisible()
    }

    override fun showTeamList(data: List<LastMatchTeam>) {
        swipeRefresh.isRefreshing = false
        lastMatch.clear()
        lastMatch.addAll(data)
        adapter.notifyDataSetChanged()
    }

}