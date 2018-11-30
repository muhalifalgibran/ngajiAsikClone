package id.alif.footbalmatchschedule.Adapter



import android.content.Intent
import android.provider.CalendarContract
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.R.color.colorPrimary
import id.alif.footbalmatchschedule.model.LastMatchTeam
import id.alif.footbalmatchschedule.R.id.*
import org.jetbrains.anko.*
import java.text.SimpleDateFormat
import java.util.*


class LastMatchAdapter(private val lastMatch: List<LastMatchTeam>, private val listener: (LastMatchTeam) -> Unit,
                       var stat: String? )
    : RecyclerView.Adapter<LastMatchAdapter.LastMatchHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LastMatchHolder {
        return LastMatchHolder(
            SomeActivity().createView(
                AnkoContext.create(p0.context, p0)
            )
        )
    }

    override fun getItemCount(): Int = lastMatch.size

    override fun onBindViewHolder(p0: LastMatchHolder, p1: Int) {
        p0.bindItem(lastMatch[p1], listener, stat)
    }

    class SomeActivity : AnkoComponent<ViewGroup> {
        lateinit var imagelay: ImageView
        override fun createView(ui: AnkoContext<ViewGroup>): View {
            return with(ui) {
                linearLayout {
                    id = R.id.layoutnya
                    lparams(width = matchParent)
                    padding = dip(16)
                    gravity = Gravity.CENTER
                    orientation = LinearLayout.VERTICAL
                    linearLayout {
                        gravity = Gravity.CENTER
                        orientation = LinearLayout.VERTICAL
                        padding = dip(10)
                        setBackgroundResource(R.drawable.border)
                        backgroundColor = R.color.colorPrimaryDark
                        linearLayout {
                            gravity = Gravity.CENTER
                            orientation = LinearLayout.HORIZONTAL
                            textView("Sat, 22 Sep 2018") {
                                id = eventDate //Ids.date_match
                                textAlignment = View.TEXT_ALIGNMENT_CENTER
                                textColor = colorPrimary
                            }.lparams(width = matchParent, height = wrapContent)
                            imagelay = imageView {
                                id = R.id.imageReminder
                                setImageDrawable(resources.getDrawable(R.drawable.ic_notifications))
                                setColorFilter(
                                    ContextCompat.getColor(context, colorPrimary),
                                    android.graphics.PorterDuff.Mode.MULTIPLY
                                )
                            }.lparams(height = dip(30), width = dip(30)) {
                                gravity = Gravity.RIGHT
                                leftMargin = dip(-20)
                            }
                        }.lparams(width = matchParent, height = wrapContent)

                        linearLayout {
                            gravity = Gravity.CENTER
                            orientation = LinearLayout.HORIZONTAL

                            textView("Chelsea1") {
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
        val ADD_12HOUR: Long = 43200000

        private val homeTeam: TextView = view.find(R.id.clubHome)
        private val awayTeam: TextView = view.find(R.id.away1)
        private val homeScore: TextView = view.find(R.id.homeScore1)
        private val awayScore: TextView = view.find(R.id.awayScore1)
        private val dates: TextView = view.find(R.id.eventDate)
        private val imgNot: ImageView = view.find(R.id.imageReminder)

        //val time = getCurrentDateTime()


        fun bindItem(lastMatchTeam: LastMatchTeam, listener: (LastMatchTeam) -> Unit, stat: String?) {
            homeTeam.text = lastMatchTeam.strHomeTeam
            awayTeam.text = lastMatchTeam.strAwayTeam
            homeScore.text = lastMatchTeam.intHomeScore
            awayScore.text = lastMatchTeam.intAwayScore
            val time: String? = lastMatchTeam.strTime
            val timeDate: String? = lastMatchTeam.dateEvent

            dates.text = lastMatchTeam.dateEvent + " | Jam: " + addTime(time)

//            val originalFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
//            val originalFormatDate = SimpleDateFormat("yyyy-MM-dd")
//            val date = originalFormat.parse(time).time
//            val date1 = originalFormatDate.parse(timeDate).time
//            val init = date1+date+ADD_12HOUR
//


            if (stat == "last")
                imgNot.visibility = View.INVISIBLE

            itemView.setOnClickListener {
                listener(lastMatchTeam)
            }

            imgNot.setOnClickListener {
              calendarEvent(lastMatchTeam.strEvent, addGmt(time,timeDate))
            }

        }

        fun addGmt(dt: String?, dt2: String?): Long{
            val originalFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val originalFormatDate = SimpleDateFormat("yyyy-MM-dd")
            val date = originalFormat.parse(dt).time
            val date1 = originalFormatDate.parse(dt2).time
            val init = date1+date+ADD_12HOUR

            return init
        }

        fun calendarEvent(title: String?, date: Long?){
            val intent: Intent = Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, date )
            itemView.context.startActivity(intent)
        }

        fun addTime(time: String?): String{
            try {
                val originalFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
                originalFormat.timeZone = TimeZone.getTimeZone("UTC")
                val targetFormat = SimpleDateFormat("HH:mm")
                val date = originalFormat.parse(time)
                return targetFormat.format(date).toString()

            }catch (e: Exception){
                e.printStackTrace()
            }

            return ""
//
//            val formatter = SimpleDateFormat("HH:mm")
//            formatter.timeZone = TimeZone.getTimeZone("UTC")
//            val has = formatter.parse(time)
//            val format: String? = SimpleDateFormat("HH:mm", Locale.getDefault()).format(has)
//            if (format== null){
//                return has.toString()
//            }else{
//                return format
//            }
        }

    }
}
