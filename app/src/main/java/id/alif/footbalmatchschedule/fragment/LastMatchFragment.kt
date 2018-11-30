package id.alif.footbalmatchschedule.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.*
import com.google.gson.Gson
import com.mancj.materialsearchbar.MaterialSearchBar
import id.alif.footbalmatchschedule.Adapter.LastMatchAdapter
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.model.LastMatchTeam
import id.alif.footbalmatchschedule.presenter.LastMatchPresenter
import id.alif.footbalmatchschedule.util.invisible
import id.alif.footbalmatchschedule.main.DetailLastMatch
import id.alif.footbalmatchschedule.main.LastMatchView
import id.alif.footbalmatchschedule.util.visible
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import id.alif.footbalmatchschedule.R.menu.search
import id.alif.footbalmatchschedule.R.id.menu_search
import org.jetbrains.anko.support.v4.onRefresh

class LastMatchFragment: Fragment(), LastMatchView {

    private var lastMatch: MutableList<LastMatchTeam> = mutableListOf()
    private lateinit var adapter: LastMatchAdapter
    private lateinit var presenter: LastMatchPresenter
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var lastMatchList: RecyclerView
    private lateinit var spinnerLast: Spinner
    private lateinit var leagueName: String

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(search,menu)
        val searchItem = menu?.findItem(menu_search)
        if (searchItem != null){
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }

            })
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.last_match, container, false)

        spinnerLast = view.find(R.id.spinnerLast)

        val spinnerItems = resources.getStringArray(R.array.league)
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        spinnerLast.adapter = spinnerAdapter

        lastMatchList = view.findViewById<RecyclerView>(R.id.recyclerLastMatchlist).apply {
            layoutManager = LinearLayoutManager(ctx)
        }

        val searchBar = view.findViewById(R.id.searchBar) as MaterialSearchBar

        val listener: (LastMatchTeam) -> Unit = {
            startActivity(intentFor<DetailLastMatch>(
                "strEvent" to it.teamIdLast.toString(),
                "idHomeTeam" to it.idHomeTeam.toString(),
                "idAwayTeam" to it.idAwayTeam.toString()))
        }

        adapter = LastMatchAdapter(lastMatch, listener, "last")

        lastMatchList.adapter = adapter

        val request = ApiRepository()
        val gson = Gson()

        progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        presenter = LastMatchPresenter(this, request, gson)

        swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipe)

        spinnerLast.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                presenter.getLastMatch("4328")
            }

            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                leagueName = spinnerLast.selectedItem.toString()
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
                presenter.getLastMatch(leagueName)
            }
        }


        swipeRefresh.onRefresh {
            swipeRefresh.isRefreshing = false
            presenter.getLastMatch(leagueName)
        }


        searchBar.addTextChangeListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                if(s != null){
                    val str = s.toString()
                    presenter.getLastMatchSearch(str)

                }else{
                    presenter.getLastMatch("4328")
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                if(s != null){
                    val str = s.toString()
                    presenter.getLastMatchSearch(str)

                }else{
                    presenter.getLastMatch("4328")
                }

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(s != null){
                    val str = s.toString()
                    presenter.getLastMatchSearch(str)

                }else{
                    presenter.getLastMatch("4328")
                }
            }
        })
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