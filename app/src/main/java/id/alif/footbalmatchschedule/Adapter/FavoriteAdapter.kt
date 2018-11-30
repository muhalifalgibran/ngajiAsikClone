package id.alif.footbalmatchschedule.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.database.Favorite
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class FavoriteAdapter(private val favorite: List<Favorite>, private val listener: (Favorite) -> Unit )
    :RecyclerView.Adapter<FavoriteAdapter.FavoriteAdapterHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FavoriteAdapterHolder {
         return FavoriteAdapterHolder(
             LastMatchAdapter.SomeActivity().createView(
                 AnkoContext.create(p0.context, p0)
             )
         )
    }

    override fun getItemCount(): Int {
        return favorite.size
    }

    override fun onBindViewHolder(p0: FavoriteAdapterHolder, p1: Int) {
        p0.bindItem(favorite[p1], listener)
    }


    class FavoriteAdapterHolder(view: View) : RecyclerView.ViewHolder(view){
        private val homeTeam: TextView = view.find(R.id.clubHome)
        private val awayTeam: TextView = view.find(R.id.away1)
        private val homeScore: TextView = view.find(R.id.homeScore1)
        private val awayScore: TextView = view.find(R.id.awayScore1)
        private val date: TextView = view.find(R.id.eventDate)

        fun bindItem(favorite: Favorite, listener: (Favorite) -> Unit){

            homeTeam.text = favorite.strHomeTeam
            awayTeam.text = favorite.strAwayTeam
            homeScore.text = favorite.intHomeScore
            awayScore.text = favorite.intAwayScore
            date.text = favorite.strDate

            itemView.setOnClickListener {
                listener(favorite)
            }

        }
    }
}