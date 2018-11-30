package id.alif.footbalmatchschedule.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class TabDetailAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm){

    private val title: ArrayList<String> = ArrayList()
    private val fragments: ArrayList<Fragment> = ArrayList()

    fun add(fragment: Fragment, title1:String){
        title.add(title1)
        fragments.add(fragment)
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }

}