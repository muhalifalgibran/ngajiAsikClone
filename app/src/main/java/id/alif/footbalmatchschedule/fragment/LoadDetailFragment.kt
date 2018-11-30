package id.alif.footbalmatchschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.SearchView
import id.alif.footbalmatchschedule.Adapter.FragmentAdapter
import id.alif.footbalmatchschedule.R
import id.alif.footbalmatchschedule.model.LastMatchTeam
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.activity_main.*
import id.alif.footbalmatchschedule.Adapter.DetailAdapter

class LoadDetailFragment: Fragment() {

    private var lastMatch: MutableList<LastMatchTeam> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.activity_detail_team, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragmentAdapter = DetailAdapter(childFragmentManager)
        viewpager_mainTeam.adapter = fragmentAdapter

        tabs_mainTeam.setupWithViewPager(viewpager_mainTeam)
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.search,menu)
//        val searchItem = menu.findItem(R.id.menu_search)
//        if (searchItem != null){
//            val searchView = searchItem.actionView as SearchView
//            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//                override fun onQueryTextSubmit(query: String?): Boolean {
//                    return false
//                }
//                override fun onQueryTextChange(newText: String?): Boolean {
//                    return false
//                }
//
//            })
//        }
//
//    }

}