package id.alif.footbalmatchschedule.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import id.alif.footbalmatchschedule.fragment.*

class FavAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Matches"
            else -> {
                return  "Teams"
            }
        }
    }

    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> {
                FavoriteFragment()
            }
            else -> {
                return FavTeamsFragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }



}