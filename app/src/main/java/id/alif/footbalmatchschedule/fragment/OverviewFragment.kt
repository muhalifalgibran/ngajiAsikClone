package id.alif.footbalmatchschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.alif.footbalmatchschedule.R.layout.overview_detail
import kotlinx.android.synthetic.main.overview_detail.*

class OverviewFragment: Fragment(){

    companion object {
        fun newInstance(text: String?) : OverviewFragment{
            val fragment = OverviewFragment()
            val sess = Bundle()
            sess.putString("overview", text)
            fragment.arguments = sess
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(overview_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        deskripsi.text = arguments?.getString("overview")
    }

}