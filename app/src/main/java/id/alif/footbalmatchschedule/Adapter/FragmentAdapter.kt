package id.alif.footbalmatchschedule.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import id.alif.footbalmatchschedule.fragment.FavoriteFragment
import id.alif.footbalmatchschedule.fragment.LastMatchFragment
import id.alif.footbalmatchschedule.fragment.NextMatchFragment

class FragmentAdapter(fm: FragmentManager): FragmentPagerAdapter(fm) {

    override fun getPageTitle(position: Int): CharSequence? {
      return when (position) {
           0 -> "Last Matches"
           1 -> "Next Matches"
          else -> {
              return "Favorites"
          }
       }
    }

    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> {
                LastMatchFragment()
            }
            1 -> {
                NextMatchFragment()
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