package id.alif.footbalmatchschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import id.alif.footbalmatchschedule.main.PlayerView
import id.alif.footbalmatchschedule.model.PlayersModel
import id.alif.footbalmatchschedule.presenter.TeamDetailPresenter
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.support.v4.ctx
import id.alif.footbalmatchschedule.R.layout.player_list
import id.alif.footbalmatchschedule.api.ApiRepository
import kotlinx.android.synthetic.main.player_list.*
import id.alif.footbalmatchschedule.Adapter.PlayersAdapter
import id.alif.footbalmatchschedule.main.DetailPlayerActivity
import id.alif.footbalmatchschedule.main.HomeActivity
import id.alif.footbalmatchschedule.presenter.PlayersPresenter


class PlayerFragment: Fragment(), PlayerView {

    companion object {
        fun newInstance(text: String?) : PlayerFragment{
            val fragment = PlayerFragment()
            val sess = Bundle()
            sess.putString("id", text)
            fragment.arguments = sess
            return fragment
        }
    }

    override fun playersList(data: List<PlayersModel>) {
        players.clear()
        players.addAll(data)
        recyclerViewPlayer?.adapter?.notifyDataSetChanged()
    }

    private lateinit var presenter: PlayersPresenter
    private var players: MutableList<PlayersModel> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
      return inflater.inflate(player_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recyclerViewPlayer.layoutManager = LinearLayoutManager(ctx)
        recyclerViewPlayer.adapter = PlayersAdapter(ctx, players)
        {
            ctx.startActivity<DetailPlayerActivity>("namaPemain" to it.strPlayer)
        }

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayersPresenter(this, request, gson)
        presenter.getPlayers(arguments?.getString("id"))

    }

}