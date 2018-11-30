package id.alif.footbalmatchschedule.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import id.alif.footbalmatchschedule.fragment.FavoriteFragment
import id.alif.footbalmatchschedule.fragment.LastMatchFragment
import id.alif.footbalmatchschedule.fragment.NextMatchFragment
import id.alif.footbalmatchschedule.fragment.OverviewFragment

class DetailAdapter(fm: FragmentManager):  FragmentPagerAdapter(fm) {


    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Overview"
            else -> {
                return "Favorites"
            }
        }
    }

    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> {
                OverviewFragment()
            }
            else -> {
                return FavoriteFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }
}