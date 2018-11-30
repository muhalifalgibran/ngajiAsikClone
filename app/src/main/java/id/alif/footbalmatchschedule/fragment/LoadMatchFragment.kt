package id.alif.footbalmatchschedule.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.widget.SearchView
import id.alif.footbalmatchschedule.R.layout.*
import kotlinx.android.synthetic.main.activity_main.*
import id.alif.footbalmatchschedule.R.menu.*
import id.alif.footbalmatchschedule.Adapter.*
import id.alif.footbalmatchschedule.R.id.*
import id.alif.footbalmatchschedule.R.menu.search
import id.alif.footbalmatchschedule.model.LastMatchTeam

class LoadMatchFragment: Fragment() {

    private var lastMatch: MutableList<LastMatchTeam> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(activity_main, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragmentAdapter = FragmentAdapter(childFragmentManager)
        viewpager_main.adapter = fragmentAdapter

        tabs_main.setupWithViewPager(viewpager_main)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(search,menu)
        val searchItem = menu.findItem(menu_search)
        if (searchItem != null){
            val searchView = searchItem.actionView as SearchView
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }

    }

}