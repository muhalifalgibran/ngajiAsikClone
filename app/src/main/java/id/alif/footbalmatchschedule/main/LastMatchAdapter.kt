package id.alif.footbalmatchschedule.main



import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.R.color.colorPrimary
import id.alif.footbalmatchschedule.model.LastMatchTeam
import id.alif.footbalmatchschedule.R.id.*
import org.jetbrains.anko.*

class LastMatchAdapter(private val lastMatch: List<LastMatchTeam>, private val listener: (LastMatchTeam) -> Unit )
    : RecyclerView.Adapter<LastMatchAdapter.LastMatchHolder>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LastMatchHolder {
        return LastMatchHolder(LastMatchAdapter.SomeActivity().createView(AnkoContext.create(p0.context,p0)))
    }

    override fun getItemCount(): Int = lastMatch.size

    override fun onBindViewHolder(p0: LastMatchHolder, p1: Int) {
      p0.bindItem(lastMatch[p1],listener)
    }

    class SomeActivity : AnkoComponent<ViewGroup> {
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout{
                    lparams(width = matchParent)
                    padding = dip(16)
                    gravity = Gravity.CENTER
                    orientation = LinearLayout.VERTICAL
                  linearLayout{
                      gravity = Gravity.CENTER
                      orientation = LinearLayout.VERTICAL
                      padding = dip(10)
                      setBackgroundResource(R.drawable.border)
                    textView("Sat, 22 Sep 2018") {
                        id = eventDate //Ids.date_match
                        textAlignment = View.TEXT_ALIGNMENT_CENTER
                        textColor = colorPrimary
                    }.lparams(width = matchParent, height = wrapContent) {
                        topMargin = dip(10)
                    }
                    linearLayout {
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.HORIZONTAL

                        textView("Chelsea") {
                            id = clubHome
                            textSize = 15f
                            singleLine = true
                            gravity = Gravity.CENTER
                        }.lparams(width = dip(90), height = wrapContent) {
                            rightMargin = dip(15)

                        }
                        textView("0") {
                            id = homeScore1
                            textSize = 25f
                        }.lparams(width = wrapContent, height = wrapContent) {
                            gravity = Gravity.CENTER
                            rightMargin = dip(20)
                        }
                        textView("VS") {
                            id = textView1
                        }.lparams(width = wrapContent, height = wrapContent)
                        textView("0") {
                            gravity = Gravity.CENTER
                            id = awayScore1
                            textSize = 25f
                        }.lparams(width = wrapContent, height = matchParent) {
                            leftMargin = dip(20)
                        }
                        textView("Everton") {
                            gravity = Gravity.CENTER
                            id = away1
                            singleLine = true
                            textSize = 15f
                        }.lparams(width = dip(90), height = matchParent) {
                            leftMargin = dip(15)
                         }
                    }.lparams(width = matchParent, height = matchParent)
                 }.lparams(width = matchParent, height = wrapContent)
                }
            }
        }
    }


    class LastMatchHolder(view: View) : RecyclerView.ViewHolder(view) {

            private val homeTeam: TextView = view.find(R.id.clubHome)
            private val awayTeam: TextView = view.find(R.id.away1)
            private val homeScore: TextView = view.find(R.id.homeScore1)
            private val awayScore: TextView = view.find(R.id.awayScore1)
            private val date: TextView = view.find(R.id.eventDate)

        fun bindItem(lastMatchTeam: LastMatchTeam, listener: (LastMatchTeam) -> Unit) {
            homeTeam.text = lastMatchTeam.strHomeTeam
            awayTeam.text = lastMatchTeam.strAwayTeam
            homeScore.text = lastMatchTeam.intHomeScore
            awayScore.text = lastMatchTeam.intAwayScore
            date.text = lastMatchTeam.strDate

            itemView.setOnClickListener {
                listener(lastMatchTeam)
            }
        }
    }

}
