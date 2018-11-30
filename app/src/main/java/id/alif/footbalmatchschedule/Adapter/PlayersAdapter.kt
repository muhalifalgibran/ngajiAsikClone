package id.alif.footbalmatchschedule.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.model.PlayersModel

class PlayersAdapter(private val context: Context, private val players: List<PlayersModel>,
                     private val listener: (PlayersModel) -> Unit)
    : RecyclerView.Adapter<PlayerViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PlayerViewHolder(LayoutInflater.from(context).inflate(R.layout.player_list_item, parent, false))

    override fun getItemCount(): Int = players.size

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bindItem(players[position], listener)
    }

}

class PlayerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val image = view.findViewById<ImageView>(R.id.playerPic)
    private val name = view.findViewById<TextView>(R.id.playerName)
    private val position = view.findViewById<TextView>(R.id.playerPos)

    fun bindItem(players: PlayersModel, listener: (PlayersModel) -> Unit) {
        Picasso.get().load(players.strCutout).into(image)
        name.text = players.strPlayer
        position.text = players.strPosition
        itemView.setOnClickListener {
            listener(players)
        }
    }
}