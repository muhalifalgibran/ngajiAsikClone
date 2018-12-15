package id.alif.footbalmatchschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.alif.footbalmatchschedule.Adapter.FavoriteMatchAdapter
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.database.Favorite
import id.alif.footbalmatchschedule.database.FavoriteTeam
import id.alif.footbalmatchschedule.database.database
import id.alif.footbalmatchschedule.main.DetailLastMatch
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.intentFor
import org.jetbrains.anko.support.v4.onRefresh
import id.alif.footbalmatchschedule.Adapter.FavoriteTeamAdapter
import id.alif.footbalmatchschedule.main.DetailTeamActivity

class FavTeamsFragment: Fragment() {
    private var favoritesTeam: MutableList<FavoriteTeam> = mutableListOf()
    private lateinit var adapter: FavoriteTeamAdapter
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var favList: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fav_match, container, false)

        favList = view.findViewById<RecyclerView>(R.id.recyclerFavMatchlist).apply {
            layoutManager = LinearLayoutManager(ctx)
        }

        val listener: (FavoriteTeam) -> Unit = {
            startActivity(intentFor<DetailTeamActivity>(
                "id" to it.teamId.toString()))
        }

        adapter = FavoriteTeamAdapter(favoritesTeam,listener)
        favList.adapter = adapter
        swipeRefresh = view.findViewById<SwipeRefreshLayout>(R.id.swipe)

        showFavorite()
        swipeRefresh.onRefresh {
            favoritesTeam.clear()
            showFavorite()
        }

        return view
    }

    private fun showFavorite(){
        context?.database?.use {
            swipeRefresh.isRefreshing = false
            val result = select(FavoriteTeam.TABLE_FAVORITE_TEAM)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            favoritesTeam.addAll(favorite)
            adapter.notifyDataSetChanged()
        }
    }
}