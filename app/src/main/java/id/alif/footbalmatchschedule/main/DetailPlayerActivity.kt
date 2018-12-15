package id.alif.footbalmatchschedule.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.R.layout.activity_detail_player
import id.alif.footbalmatchschedule.api.ApiRepository
import id.alif.footbalmatchschedule.model.PlayersModel
import id.alif.footbalmatchschedule.presenter.DetailPlayerPresenter
import kotlinx.android.synthetic.main.activity_detail_player.*
import org.jetbrains.anko.image

class DetailPlayerActivity : AppCompatActivity(), PlayerView {


    private lateinit var id: String
    private lateinit var presenter: DetailPlayerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_detail_player)



        setSupportActionBar(findViewById(R.id.namaPemain))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val nama: String  = intent.getStringExtra("namaPemain")


        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPlayerPresenter(this, request, gson)
        presenter.getPlayerDetail(nama)


    }

    override fun playersList(data: List<PlayersModel>) {
       namaPemain.title = data[0].strPlayer
       berat.text = data[0].strWeight
       tinggi.text = data[0].strHeight
       tinggi.text = data[0].strHeight
        Picasso.get()
            .load(data[0].strThumb)
            .into(fotoPemain)
        deskPemain.text = data[0].strDescriptionEN
    }
}
