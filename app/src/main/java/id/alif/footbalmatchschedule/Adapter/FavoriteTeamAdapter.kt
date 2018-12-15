package id.alif.footbalmatchschedule.Adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.database.Favorite
import id.alif.footbalmatchschedule.database.FavoriteTeam
import id.alif.footbalmatchschedule.fragment.TeamsFragment
import org.jetbrains.anko.AnkoContext
import org.jetbrains.anko.find

class FavoriteTeamAdapter(private val favoriteTeam: List<FavoriteTeam> , private val listener: (FavoriteTeam) -> Unit )
    :RecyclerView.Adapter<FavoriteTeamAdapter.FavoriteAdapterHolder>(){

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): FavoriteAdapterHolder {
        return FavoriteAdapterHolder(
             TeamUI().createView(
                AnkoContext.create(p0.context,p0)
            )
        )
    }

    override fun getItemCount(): Int {
        return favoriteTeam.size
    }

    override fun onBindViewHolder(p0: FavoriteAdapterHolder, p1: Int) {
        p0.bindItem(favoriteTeam[p1],listener)
    }


    class FavoriteAdapterHolder(view: View) : RecyclerView.ViewHolder(view){
        private val teamBdg: ImageView = view.find(R.id.team_badge)
        private val teamName: TextView = view.find(R.id.team_name)


        fun bindItem(favorite: FavoriteTeam, listener: (FavoriteTeam) -> Unit){

            Picasso.get()
                .load(favorite.teamBadge)
                .into(teamBdg)
            teamName.text = favorite.teamName


        }
    }
}