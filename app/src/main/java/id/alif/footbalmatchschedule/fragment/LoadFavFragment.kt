package id.alif.footbalmatchschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import id.alif.footbalmatchschedule.R.layout.*
import kotlinx.android.synthetic.main.activity_main.*
import id.alif.footbalmatchschedule.Adapter.FavAdapter

class LoadFavFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(activity_main, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragmentAdapter = FavAdapter(childFragmentManager)
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)
    }


}